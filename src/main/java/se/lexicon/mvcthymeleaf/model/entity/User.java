package se.lexicon.mvcthymeleaf.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

@Entity
@Table(name = "users")
public class User
{
    @Id
    private String username;
    @Column(nullable = false)
    private String password;
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.PERSIST})
    private Set<Role> roles;
    private boolean expired;

    public void addRole(Role role)
    {
        if (role == null) throw new IllegalArgumentException("Role was null");
        if (roles == null) roles = new HashSet<>();

        roles.add(role);
    }

    public void removeRole(Role role)
    {
        if (role == null) throw new IllegalArgumentException("Role was null");
        if (roles != null) roles.remove(role);
    }
}
