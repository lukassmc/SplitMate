package ar.com.splitmate.servicios;

import ar.com.splitmate.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Service
public class JWTServiceImp implements JWTService{
    private final String KEY_AUTHORIZATION= "authorization";


    @Value("${jwt.secret.password}")
    private String secretPassword;

    @Override
    public String buildToken(User usuario){

        return JWT.create().withKeyId("splitmate" + usuario.getId())
                .withExpiresAt(Instant.now().plusSeconds(180))
                .withClaim("authorization", obtenerPermisosUsuario(usuario))
                .sign(Algorithm.HMAC512(this.secretPassword)) ;

    };

    private List<String> obtenerPermisosUsuario(User usuario){
        List<String> permisos = new ArrayList<String>();

        for (GrantedAuthority auth : usuario.collectAuthorities()){

            permisos.add(auth.getAuthority());
        }

        return permisos;
    }

    @Override
    public Authentication buildAuthentication(String token){

        DecodedJWT decodedToken= JWT.decode(token);

        String id = decodedToken.getKeyId();
        Claim claim = decodedToken.getClaim(KEY_AUTHORIZATION);


        List<String> permisos=  claim.asList(String.class);

        Authentication authentication = new UsernamePasswordAuthenticationToken(id, id, convertirPermisos(permisos));

        return authentication;
    }


    public List<GrantedAuthority> convertirPermisos(List<String> permisos){
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        for (String permiso : permisos){
            authorities.add(new SimpleGrantedAuthority(permiso));
        }
        return authorities;
    }

    @Override
    public boolean isTokenValid(String token){
        DecodedJWT decodedToken = JWT.decode(token);

        Instant instant = decodedToken.getExpiresAtAsInstant();

        return Instant.now().isBefore(instant);
    };
}
