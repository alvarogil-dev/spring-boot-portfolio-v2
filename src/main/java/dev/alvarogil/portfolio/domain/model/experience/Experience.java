package dev.alvarogil.portfolio.domain.model.experience;

import dev.alvarogil.portfolio.domain.model.Period;
import dev.alvarogil.portfolio.domain.model.role.Role;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Experience {
    private final UUID uuid;
    private final String companyName;
    private final String companyUrl;
    private final String companyAvatarUrl;
    private final List<Role> roles = new ArrayList<>();

    public Experience(String companyName, String companyUrl, String companyAvatarUrl, Role role) {
        this.uuid = UUID.randomUUID();

        if (role == null) throw new IllegalArgumentException("Role cannot be null");

        this.companyName = companyName;
        this.companyUrl = companyUrl;
        this.companyAvatarUrl = companyAvatarUrl;
        roles.add(role);
    }

    public UUID getUuid() { return uuid; }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyUrl() {
        return companyUrl;
    }

    public String getCompanyAvatarUrl() {
        return companyAvatarUrl;
    }

    public List<Role> getRoles() {
        return Collections.unmodifiableList(roles);
    }

    // domain logic

    public Period getPeriod() {
        return Period.cover(roles.stream()
                .map(Role::getPeriod)
                .toList());
    }

    public void addRole(Role role) {
        if (role == null) throw new IllegalArgumentException("Role cannot be null");

        roles.add(role);
        //TODO consider to order the roles by start date
    }
}
