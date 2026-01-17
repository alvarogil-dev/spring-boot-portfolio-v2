package dev.alvarogil.portfolio.infrastructure.persistence;

import dev.alvarogil.portfolio.domain.model.Period;
import dev.alvarogil.portfolio.domain.model.experience.Experience;
import dev.alvarogil.portfolio.domain.model.role.Achievement;
import dev.alvarogil.portfolio.domain.model.role.AchievementTranslation;
import dev.alvarogil.portfolio.domain.model.role.Role;
import dev.alvarogil.portfolio.domain.model.role.RoleTranslation;
import dev.alvarogil.portfolio.domain.model.role.Technology;
import dev.alvarogil.portfolio.domain.port.out.ExperienceRepository;
import org.springframework.stereotype.Repository;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class InMemoryExperienceRepository implements ExperienceRepository {

    private final List<Experience> experiences = new CopyOnWriteArrayList<>(); //avoid ConcurrentModificationException

    public InMemoryExperienceRepository() {
        Experience erni = new Experience(
                "ERNI Consulting",
                "https://www.erni-consulting.com/en/",
                "https://www.betterask.erni/wp-content/uploads/2023/09/ERNI_logo_color-2048x532.png",
                roleWithTranslations(
                        new Period(YearMonth.of(2022, 3), null),
                        "Software Architect - DevOps Lead",
                        "ERNI Consulting - Hybrid, Barcelona",
                        "Technologies used: Microsoft Azure, Jenkins, Ansible, Terraform, Docker, PowerShell, Bash, " +
                                "JFrog, SonarQube, Azure Container Registry.",
                        "Software Architect - DevOps Lead",
                        "ERNI Consulting - Híbrido, Barcelona",
                        "Tecnologías usadas: Microsoft Azure, Jenkins, Ansible, Terraform, Docker, PowerShell, Bash, " +
                                "JFrog, SonarQube, Azure Container Registry."
                )
        );
        erni.addRole(roleWithTranslations(
                new Period(YearMonth.of(2022, 3), null),
                "Software Architect - Team and Tech Lead",
                "ERNI Consulting - Hybrid, Barcelona",
                "Technologies used: Spring Boot, Kafka Streams, RabbitMQ, STOMP, Kubernetes, Jenkins, Docker, " +
                        "Git, Swagger, IntelliJ, MongoDB, NodeJS, React, Jira.",
                "Software Architect - Team and Tech Lead",
                "ERNI Consulting - Híbrido, Barcelona",
                "Tecnologías usadas: Spring Boot, Kafka Streams, RabbitMQ, STOMP, Kubernetes, Jenkins, Docker, " +
                        "Git, Swagger, IntelliJ, MongoDB, NodeJS, React, Jira."
        ));
        experiences.add(erni);

        Experience nttData = new Experience(
                "NTT DATA (Everis)",
                "https://es.nttdata.com/",
                "",
                roleWithTranslations(
                        new Period(YearMonth.of(2021, 8), YearMonth.of(2022, 3)),
                        "Software Architect - Team and Tech Lead",
                        "NTT DATA (Everis) - Remote, Madrid",
                        "Technologies used: AWS (CodeStar, CodePipeline, CodeCommit, Cognito, others), Spring Boot, " +
                                "Kafka Streams, RabbitMQ, STOMP, Kubernetes, Jenkins, Docker, Git, Swagger, IntelliJ, " +
                                "MongoDB, NodeJS, React, Jira.",
                        "Software Architect - Team and Tech Lead",
                        "NTT DATA (Everis) - Remoto, Madrid",
                        "Tecnologías usadas: AWS (CodeStar, CodePipeline, CodeCommit, Cognito, otros), Spring Boot, " +
                                "Kafka Streams, RabbitMQ, STOMP, Kubernetes, Jenkins, Docker, Git, Swagger, IntelliJ, " +
                                "MongoDB, NodeJS, React, Jira."
                )
        );
        experiences.add(nttData);

        Experience kubikData = new Experience(
                "Kubik Data (startup)",
                "https://www.kubikdata.com/",
                "",
                roleWithTranslations(
                        new Period(YearMonth.of(2020, 1), YearMonth.of(2021, 8)),
                        "Senior Backend Developer",
                        "Kubik Data (startup) - Gran Via de les Corts Catalanes, Barcelona",
                        "Technologies used: AWS (CodeStar, CodePipeline, CodeCommit, Cognito, others), Spring Boot, " +
                                "Apache Kafka, Apache Camel, Docker, Git, Swagger, IntelliJ, PostgreSQL, Jira, " +
                                "Confluence.",
                        "Senior Backend Developer",
                        "Kubik Data (startup) - Gran Via de les Corts Catalanes, Barcelona",
                        "Tecnologías usadas: AWS (CodeStar, CodePipeline, CodeCommit, Cognito, otros), Spring Boot, " +
                                "Apache Kafka, Apache Camel, Docker, Git, Swagger, IntelliJ, PostgreSQL, Jira, " +
                                "Confluence."
                )
        );
        experiences.add(kubikData);

        Experience bitendian = new Experience(
                "Bitendian (consultoría)",
                "",
                "",
                roleWithTranslations(
                        new Period(YearMonth.of(2017, 5), YearMonth.of(2020, 1)),
                        "Developer",
                        "Bitendian (consultancy) - Marina, Barcelona",
                        "Technologies used: Apache, PHP, AngularJS, Docker, Git, HTML, Bootstrap, CSS, JavaScript, " +
                                "NodeJS, MySQL, Postman. Also used previous technologies.",
                        "Desarrollador",
                        "Bitendian (consultoría) - Marina, Barcelona",
                        "Tecnologías usadas: Apache, PHP, AngularJS, Docker, Git, HTML, Bootstrap, CSS, Javascript, " +
                                "NodeJS, MySQL, Postman. Usadas también las tecnologías anteriores."
                )
        );
        experiences.add(bitendian);
        Role role = new Role(new Period(YearMonth.of(2022, 3), null));
        role.addTranslation(roleTranslation);
        role.addTranslation(roleTranslationSpanish);
        role.addTechnology(new Technology("Spring Boot", "https://spring.io/projects/spring-boot"));
        role.addTechnology(new Technology("PostgreSQL", "https://www.postgresql.org/"));

        Achievement achievement = new Achievement();
        achievement.addTranslation(new AchievementTranslation(
                "en",
                "Reduced deployment time",
                "Automated the CI/CD pipeline to cut deployment time by 40%."
        ));
        achievement.addTranslation(new AchievementTranslation(
                "es",
                "Reducción del tiempo de despliegue",
                "Automatización del pipeline CI/CD para reducir el tiempo de despliegue en un 40%."
        ));
        role.addAchievement(achievement);

        Experience labsDivision = new Experience(
                "Labs Division (consultoría)",
                "",
                "",
                roleWithTranslations(
                        new Period(YearMonth.of(2017, 6), YearMonth.of(2019, 5)),
                        "Developer",
                        "Labs Division (consultancy) - Cornellà de Llobregat",
                        "Technologies used: Java, Maven, SVN, JBoss, WebLogic, Tomcat, Oracle Developer, MySQL, " +
                                "Hibernate, Spring, Spring Boot, Angular, Eclipse, Visual Studio Code, SoapUI.",
                        "Desarrollador",
                        "Labs Division (consultoría) - Cornellà de Llobregat",
                        "Tecnologías usadas: Java, Maven, SVN, JBoss, WebLogic, Tomcat, Oracle Developer, MySQL, " +
                                "Hibernate, Spring, Spring Boot, Angular, Eclipse, Visual Studio Code, SoapUI."
                )
        );
        experiences.add(labsDivision);

        Experience tutoring = new Experience(
                "Profesor particular",
                "",
                "",
                roleWithTranslations(
                        new Period(YearMonth.of(2016, 1), YearMonth.of(2017, 12)),
                        "Programming tutor",
                        "Private tutoring",
                        "C# tutoring for telecommunications engineering students (two students during four months).",
                        "Profesor particular de programación",
                        "Clases particulares",
                        "Clases de programación en C# para estudiantes de ingeniería de telecomunicaciones (dos " +
                                "alumnos durante cuatro meses)."
                )
        );
        experiences.add(tutoring);
    }

    private static Role roleWithTranslations(
            Period period,
            String titleEn,
            String shortSummaryEn,
            String longSummaryEn,
            String titleEs,
            String shortSummaryEs,
            String longSummaryEs
    ) {
        Role role = new Role(period);
        role.addTranslation(new RoleTranslation("en", titleEn, shortSummaryEn, longSummaryEn));
        role.addTranslation(new RoleTranslation("es", titleEs, shortSummaryEs, longSummaryEs));
        return role;
    }

    @Override
    public Experience save(Experience experience) {
        experiences.add(experience);
        return experience;
    }

    @Override
    public List<Experience> findAll() {
        return List.copyOf(experiences); //immutable experiences list
    }

    @Override
    public Optional<Experience> findById(UUID id) {
        return experiences.stream()
                .filter(experience -> experience.getUuid().equals(id))
                .findFirst();
    }
}
