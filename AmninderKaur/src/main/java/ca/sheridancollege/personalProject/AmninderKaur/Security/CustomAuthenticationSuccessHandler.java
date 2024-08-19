package ca.sheridancollege.personalProject.AmninderKaur.Security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        // Store the username in session
        String username = authentication.getName();
        request.getSession().setAttribute("username", username);

        // Get the roles of the authenticated user
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        // Redirect based on roles
        boolean isAdmin = authorities.stream()
                                     .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
        boolean isUser = authorities.stream()
                                    .anyMatch(authority -> authority.getAuthority().equals("ROLE_USER"));

        if (isAdmin) {
            response.sendRedirect(request.getContextPath() + "/admin");
        } else if (isUser) {
            response.sendRedirect(request.getContextPath() + "/user");
        } else {
            response.sendRedirect(request.getContextPath() + "/");
        }
    }
}
