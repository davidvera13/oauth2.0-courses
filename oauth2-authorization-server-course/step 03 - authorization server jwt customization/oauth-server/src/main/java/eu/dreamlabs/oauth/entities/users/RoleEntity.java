package eu.dreamlabs.oauth.entities.users;

import eu.dreamlabs.oauth.shared.enums.RoleName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name="roles")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class RoleEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 5605260522147928803L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RoleName name;

    @ManyToMany(mappedBy="roles")
    private Collection<UserEntity> users;

    @ManyToMany(cascade= { CascadeType.PERSIST }, fetch = FetchType.EAGER )
    @JoinTable(name="roles_authorities",
            joinColumns=@JoinColumn(name="roles_id",referencedColumnName="id"),
            inverseJoinColumns=@JoinColumn(name="authorities_id",referencedColumnName="id"))
    private Collection<AuthorityEntity> authorities;


}