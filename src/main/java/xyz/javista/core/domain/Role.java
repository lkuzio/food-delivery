package xyz.javista.core.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
public class Role extends AuditBase implements Serializable {

    @NotNull
    @Enumerated(EnumType.STRING)
    private RoleType name;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    public RoleType getName() {
        return name;
    }

    public void setName(RoleType name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
