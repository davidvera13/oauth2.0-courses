package eu.dreamlabs.spi;

import eu.dreamlabs.spi.domains.User;
import eu.dreamlabs.spi.domains.VerifyPasswordResponse;
import org.keycloak.broker.provider.util.SimpleHttp;
import org.keycloak.models.KeycloakSession;
import org.jboss.logging.Logger;


public class UsersApiService {
    private static final Logger LOGGER = Logger.getLogger(UsersApiService.class);
    private final KeycloakSession session;

    public UsersApiService(KeycloakSession session) {
        this.session = session;
    }

    public User getUserByUsername(String username) {
        User user = null;
        try {
            user = SimpleHttp.doGet("http://localhost:8099/users/" + username, session).asJson(User.class);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }

        return user;
    }

    public VerifyPasswordResponse verifyPassword(String username, String password) {
        SimpleHttp simpleHttp = SimpleHttp.doPost("http://localhost:8099/users/" + username + "/verify-password", session);
        // include password as form data
        simpleHttp.param("password", password);
        // add header of needed
        simpleHttp.header("Content-Type", "plain/text");
        try {
            return simpleHttp.asJson(VerifyPasswordResponse.class);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
