package self.pj.blogger.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(generator = "user_id_gen")
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "username")

    private String username;

    @Column(name = "password")
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(
            mappedBy = "user",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true)
    @JsonManagedReference
    private List<Post> posts = new LinkedList<>();

    public User(String username, String password, Role role)
    {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public User(long id, String username, String password)
    {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public void addNewPost(Post p)
    {
        p.setUser(this);
        posts.add(p);
    }
}
