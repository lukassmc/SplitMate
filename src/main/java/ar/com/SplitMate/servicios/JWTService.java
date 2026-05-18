package ar.com.splitmate.servicios;

import ar.com.splitmate.User;
import org.springframework.security.core.Authentication;

public interface JWTService {

    public String PREFIX_TOKEN = "Bearer";
    public String buildToken(User usuario);

    Authentication buildAuthentication(String token);

    public boolean isTokenValid(String token);
}
