package eu.dreamlabs.oauth.entities.users;

import eu.dreamlabs.oauth.entities.users.RoleEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "google_users")
@Builder
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class GoogleUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String name;
    private String givenName;
    private String familyName;
    private String pictureUrl;
    // TODO add roles

    public static GoogleUserEntity fromOauth2User(OAuth2User user){
        return GoogleUserEntity.builder()
                .email(user.getName())
                .name(user.getAttributes().get("name").toString())
                .givenName(user.getAttributes().get("given_name").toString())
                .familyName(user.getAttributes().get("family_name").toString())
                .pictureUrl(user.getAttributes().get("picture").toString())
                .build();
    }

    @Override
    public String toString() {
        return "GoogleUser{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", givenName='" + givenName + '\'' +
                ", familyName='" + familyName + '\'' +
                ", pictureUrl='" + pictureUrl + '\'' +
                '}';
    }
}
