package ukma.speedyfix.security;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import ukma.speedyfix.domain.AuthenticatedUser;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityContextAccessor {

    private static AuthenticatedUser getAuthenticateUser() {
        return (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static String getBehalfOnEmail() {
        return getAuthenticateUser().getBehalfOnEmail();
    }

    public static String getRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<String> authorities  = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return authorities.getFirst();
    }
}