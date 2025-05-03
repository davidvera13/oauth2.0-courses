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
@Table(name = "users")
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class UserEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 5605260522147928406L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private boolean accountExpired;
    private boolean accountLocked;
    private boolean credentialsExpired;
    private boolean disabled;

    @ManyToMany(cascade= { CascadeType.PERSIST }, fetch = FetchType.EAGER )
    @JoinTable(
            name="users_roles",
            joinColumns=@JoinColumn(name="user_id",referencedColumnName="id"),
            inverseJoinColumns=@JoinColumn(name="role_id",referencedColumnName="id"))
    private Collection<RoleEntity> roles;
}