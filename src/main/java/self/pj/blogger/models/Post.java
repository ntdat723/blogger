package self.pj.blogger.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@NoArgsConstructor
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(generator = "post_id_gen")
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "content")
    private String content;

    //@Column(name = "user")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private User user;
    //@Column(name = "like")
    //private long likes;

    //@Column(name = "dislike")
    //private long dislikes;

    public Post(String content)
    {
        this.content = content;
    }
}
