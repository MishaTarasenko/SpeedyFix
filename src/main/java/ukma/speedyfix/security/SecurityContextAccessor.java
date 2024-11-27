package ukma.speedyfix.security;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityContextAccessor {

    private static User getAuthenticateUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static String getBehalfOnEmail() {
        return getAuthenticateUser().getUsername();
    }

    public static String getRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<String> authorities  = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return authorities.getFirst();
    }
}