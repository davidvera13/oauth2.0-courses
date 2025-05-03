package eu.dreamlabs.oauth.configs;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    private final Map<String, String> clientLogoutUrls = Map.of(
            "client", "http://localhost:5200/logout",
            "gateway", "http://127.0.0.1:8765/logged-out"
    );

    @Override
    public void onLogoutSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException {
        String clientId = request.getParameter("client_id");
        // dirty way..
        if (clientId == null)
            clientId = "client";
        String redirectUrl = clientLogoutUrls.getOrDefault(clientId, "http://localhost:5200/logout");
        response.sendRedirect(redirectUrl);
    }
}
