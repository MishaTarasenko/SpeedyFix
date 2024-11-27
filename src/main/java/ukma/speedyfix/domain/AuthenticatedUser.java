package ukma.speedyfix.domain;

import lombok.Data;

@Data
public class AuthenticatedUser {

    private final String behalfOnEmail;
}