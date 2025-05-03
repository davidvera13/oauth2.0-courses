package eu.dreamlabs.oauth.federation;


import eu.dreamlabs.oauth.entities.users.GoogleUserEntity;
import eu.dreamlabs.oauth.repositories.GoogleUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor
public final class UserRepositoryOAuth2UserHandler
        implements Consumer<OAuth2User> {
    private final GoogleUserRepository googleUserRepository;


    @Override
    public void accept(OAuth2User user) {
        // Capture user in a local data store on first authentication
        if (this.googleUserRepository.findByEmail(user.getName()).isEmpty()) {
            GoogleUserEntity googleUser = GoogleUserEntity.fromOauth2User(user);
            log.info(googleUser.toString());
            this.googleUserRepository.save(googleUser);
        } else {
            log.info("Welcome {}", user.getAttributes().get("given_name"));
        }
    }


    static class UserRepository {
        private final Map<String, OAuth2User> userCache =
                new ConcurrentHashMap<>();

        public OAuth2User findByName(String name) {
            return this.userCache.get(name);
        }

        public void save(OAuth2User oauth2User) {
            this.userCache.put(oauth2User.getName(), oauth2User);
        }

    }

}