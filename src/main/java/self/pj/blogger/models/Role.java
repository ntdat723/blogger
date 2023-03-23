package self.pj.blogger.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@Data
public class Role {
    @Id
    @GeneratedValue(generator = "role_id_gen")
    private Long id;

    @Column(nullable = false)
    @NotNull
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_auth",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "auth_id"))
    private Set<Authority> authorities = new HashSet<>();

    public Role(String name)
    {
        this.name = name;
    }
    public Role(String name, Set<Authority> authorities)
    {
        this.name = name;
        this.authorities = authorities;
    }
}
