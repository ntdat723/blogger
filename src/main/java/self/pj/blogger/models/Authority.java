package self.pj.blogger.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Data
public class Authority {
    @Id
    @GeneratedValue(generator = "auth_id_gen")
    @Setter(AccessLevel.NONE)
    private Long id;

    private String authName;

    public Authority(String name)
    {
        this.authName = name;
    }
}
