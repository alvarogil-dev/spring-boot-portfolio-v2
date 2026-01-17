package dev.alvarogil.portfolio.application.service;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import dev.alvarogil.portfolio.domain.model.Period;
import dev.alvarogil.portfolio.domain.model.education.Education;
import dev.alvarogil.portfolio.domain.model.experience.Experience;
import dev.alvarogil.portfolio.domain.model.language.Language;
import dev.alvarogil.portfolio.domain.model.profile.Profile;
import dev.alvarogil.portfolio.domain.model.profile.ProfileTranslation;
import dev.alvarogil.portfolio.domain.model.role.Role;
import dev.alvarogil.portfolio.domain.model.role.RoleTranslation;
import dev.alvarogil.portfolio.domain.port.in.GenerateCvPdfUseCase;
import dev.alvarogil.portfolio.domain.port.in.GetProfileUseCase;
import dev.alvarogil.portfolio.domain.port.in.ListEducationUseCase;
import dev.alvarogil.portfolio.domain.port.in.ListExperiencesUseCase;
import dev.alvarogil.portfolio.domain.port.in.ListLanguagesUseCase;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

@Service
public class GenerateCvPdfService implements GenerateCvPdfUseCase {

    private final GetProfileUseCase getProfileUseCase;
    private final ListExperiencesUseCase listExperiencesUseCase;
    private final ListLanguagesUseCase listLanguagesUseCase;
    private final ListEducationUseCase listEducationUseCase;

    public GenerateCvPdfService(
            GetProfileUseCase getProfileUseCase,
            ListExperiencesUseCase listExperiencesUseCase,
            ListLanguagesUseCase listLanguagesUseCase,
            ListEducationUseCase listEducationUseCase
    ) {
        this.getProfileUseCase = getProfileUseCase;
        this.listExperiencesUseCase = listExperiencesUseCase;
        this.listLanguagesUseCase = listLanguagesUseCase;
        this.listEducationUseCase = listEducationUseCase;
    }

    @Override
    public byte[] execute(Locale locale) {
        Locale effectiveLocale = locale != null ? locale : Locale.ENGLISH;

        Profile profile = getProfileUseCase.execute();
        ProfileTranslation profileTranslation = profile.getTranslationOrFallback(effectiveLocale.getLanguage());
        List<Language> languages = listLanguagesUseCase.execute();
        List<Education> educationEntries = listEducationUseCase.execute();
        List<Experience> experiences = listExperiencesUseCase.execute();

        String html = buildHtml(
                profile,
                profileTranslation,
                languages,
                educationEntries,
                experiences,
                effectiveLocale
        );
        return renderPdf(html);
    }

    private byte[] renderPdf(String html) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withHtmlContent(html, null);
            builder.toStream(outputStream);
            builder.run();
            return outputStream.toByteArray();
        } catch (Exception ex) {
            throw new IllegalStateException("Unable to generate CV PDF", ex);
        }
    }

    private String buildHtml(
            Profile profile,
            ProfileTranslation profileTranslation,
            List<Language> languages,
            List<Education> educationEntries,
            List<Experience> experiences,
            Locale locale
    ) {
        StringBuilder html = new StringBuilder();
        html.append("""
                <!DOCTYPE html>
                <html lang="%s">
                <head>
                    <meta charset="UTF-8" />
                    <style>
                        @page { size: A4; margin: 36px 40px; }
                        body { font-family: "Helvetica", "Arial", sans-serif; font-size: 12px; color: #1d1d1d; }
                        .header { width: 100%%; display: table; }
                        .header-left { display: table-cell; vertical-align: top; width: 70%%; }
                        .header-right { display: table-cell; vertical-align: top; text-align: right; width: 30%%; }
                        .name { color: #c1121f; font-size: 24px; font-weight: 700; margin: 0 0 4px 0; }
                        .title { color: #c1121f; font-size: 14px; font-weight: 700; margin: 0 0 8px 0; }
                        .contact { line-height: 1.4; margin-bottom: 8px; }
                        .summary { margin-top: 6px; color: #333; }
                        .avatar { width: 120px; height: auto; border-radius: 4px; }
                        .section-title { color: #c1121f; font-size: 12px; font-weight: 700; letter-spacing: 1px;
                                         margin-top: 14px; margin-bottom: 6px; text-transform: uppercase; }
                        .section-divider { border-top: 1px solid #1d1d1d; margin: 6px 0 8px 0; }
                        .experience-table { width: 100%%; border-collapse: collapse; }
                        .experience-period { width: 22%%; vertical-align: top; padding-right: 8px; color: #444; font-size: 11px; }
                        .experience-role { vertical-align: top; padding-bottom: 12px; }
                        .role-title { font-weight: 700; }
                        .role-company { font-weight: 700; margin-top: 2px; }
                        .role-summary { margin-top: 2px; }
                        .skills-table { width: 100%%; border-collapse: collapse; }
                        .skills-table th { text-align: left; font-weight: 700; font-size: 11px; border-bottom: 1px solid #1d1d1d; padding-bottom: 4px; }
                        .skills-table td { padding: 2px 0; vertical-align: top; }
                        .education-table { width: 100%%; border-collapse: collapse; }
                        .education-year { width: 14%%; vertical-align: top; padding-right: 8px; color: #444; }
                        .education-entry { padding-bottom: 10px; }
                        ul { margin: 4px 0 0 18px; padding: 0; }
                    </style>
                </head>
                <body>
                """.formatted(escapeHtml(locale.getLanguage())));

        html.append("<div class=\"header\">");
        html.append("<div class=\"header-left\">");
        html.append("<div class=\"name\">").append(escapeHtml(profile.getName())).append("</div>");
        html.append("<div class=\"title\">").append(escapeHtml(profileTranslation.title())).append("</div>");
        html.append("<div class=\"contact\">")
                .append(escapeHtml(profile.getLocation())).append("<br/>")
                .append(escapeHtml(profile.getPhone())).append("<br/>")
                .append(escapeHtml(profile.getEmail()))
                .append("</div>");
        html.append("<div class=\"summary\">").append(escapeHtml(profileTranslation.summary())).append("</div>");
        html.append("</div>");
        html.append("<div class=\"header-right\">")
                .append("<img class=\"avatar\" src=\"").append(escapeHtml(profile.getAvatarUrl())).append("\" />")
                .append("</div>");
        html.append("</div>");

        html.append("<div class=\"section-title\">").append(sectionTitle("Idiomas", "Languages", locale)).append("</div>");
        html.append("<ul>");
        for (Language language : languages) {
            html.append("<li>").append(escapeHtml(language.getDescription())).append("</li>");
        }
        html.append("</ul>");

        html.append("<div class=\"section-title\">")
                .append(sectionTitle("Actividades laborales", "Experience", locale))
                .append("</div>");
        html.append("<table class=\"experience-table\">");
        experiences.stream()
                .sorted(Comparator.comparing((Experience exp) -> exp.getPeriod().start()).reversed())
                .forEach(experience -> {
                    List<Role> roles = experience.getRoles();
                    roles.stream()
                            .sorted(Comparator.comparing((Role role) -> role.getPeriod().start()).reversed())
                            .forEach(role -> appendRoleRow(html, role, locale));
                });
        html.append("</table>");

        html.append("<div class=\"section-title\">")
                .append(sectionTitle("Educación", "Education", locale))
                .append("</div>");
        html.append("<table class=\"education-table\">");
        for (Education education : educationEntries) {
            html.append("<tr>");
            html.append("<td class=\"education-year\">").append(escapeHtml(education.getYear())).append("</td>");
            html.append("<td class=\"education-entry\">");
            html.append("<div class=\"role-title\">").append(escapeHtml(education.getTitle())).append("</div>");
            html.append("<div class=\"role-company\">")
                    .append(escapeHtml(education.getInstitution()))
                    .append(" - ")
                    .append(escapeHtml(education.getLocation()))
                    .append("</div>");
            if (!education.getAchievements().isEmpty()) {
                html.append("<ul>");
                for (String achievement : education.getAchievements()) {
                    html.append("<li>").append(escapeHtml(achievement)).append("</li>");
                }
                html.append("</ul>");
            }
            html.append("</td>");
            html.append("</tr>");
        }
        html.append("</table>");

        html.append("</body></html>");
        return html.toString();
    }

    private void appendRoleRow(StringBuilder html, Role role, Locale locale) {
        RoleTranslation translation = role.getTranslationOrFallback(locale.getLanguage());
        Period period = role.getPeriod();
        html.append("<tr>");
        html.append("<td class=\"experience-period\">")
                .append(escapeHtml(period.toFormattedString(locale)))
                .append("</td>");
        html.append("<td class=\"experience-role\">");
        html.append("<div class=\"role-title\">").append(escapeHtml(translation.title())).append("</div>");
        html.append("<div class=\"role-company\">").append(escapeHtml(translation.shortSummary())).append("</div>");
        html.append("<div class=\"role-summary\">").append(escapeHtml(translation.longSummary())).append("</div>");
        html.append("</td>");
        html.append("</tr>");
    }

    private String sectionTitle(String spanish, String english, Locale locale) {
        return locale.getLanguage().equalsIgnoreCase("es") ? escapeHtml(spanish) : escapeHtml(english);
    }

    private String escapeHtml(String value) {
        if (value == null) {
            return "";
        }
        return value
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;");
    }
}
