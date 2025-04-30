package eu.dreamlabs.oauth.entities.users;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "authorities")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class AuthorityEntity  implements Serializable {

    @Serial
    private static final long serialVersionUID = 5605260522147928904L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable=false, length=20)
    private String name;

    @ManyToMany(mappedBy="authorities")
    private Collection<RoleEntity> roles;
}