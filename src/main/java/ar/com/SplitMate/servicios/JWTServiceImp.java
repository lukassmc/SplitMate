package ar.com.splitmate.servicios;

import ar.com.splitmate.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(JWTServiceImp.class);
    private final String KEY_AUTHORIZATION= "authorization";

    @Value("${jwt.secret.password}")
    private String secretPassword;

    @Override
    public String buildToken(User usuario){

        return JWT.create().withKeyId("splitmate" + usuario.getId())
                .withExpiresAt(Instant.now().plusSeconds(180))
                .withClaim("authorization", obtenerPermisosUsuario(usuario))
                .withSubject(usuario.getUsername())
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


        DecodedJWT decodedToken= getVerifier().verify(token);

        String username = decodedToken.getSubject();


        Claim claim = decodedToken.getClaim(KEY_AUTHORIZATION);


        List<String> permisos=  claim.asList(String.class);

        Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, convertirPermisos(permisos));

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
    public boolean isTokenValid(String token) {
        try {
            JWTVerifier verifier = getVerifier();
            verifier.verify(token);
            return true;

        } catch (JWTVerificationException e) {
            return false;
        }
    };

    @Override
    public JWTVerifier getVerifier(){
        Algorithm algorithm = buildAlgorithm(secretPassword);
        return JWT.require(algorithm).build();
    };


    @Override
    public Algorithm buildAlgorithm(String secretPassword){
        return  Algorithm.HMAC512(secretPassword);
    };
}
