package ar.com.splitmate.config.filters;

import ar.com.splitmate.servicios.JWTService;
import ar.com.splitmate.servicios.JWTServiceImp;
import com.fasterxml.jackson.core.SerializableString;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (existeToken(request)){

           String token= fetchToken(request);

           if (jwtService.isTokenValid(token)) {
               Authentication auth = jwtService.buildAuthentication(token);

               SecurityContext context = SecurityContextHolder.getContext();

               context.setAuthentication(auth);
           } else {
               SecurityContextHolder.clearContext();
           }
        }

        filterChain.doFilter(request,response);
    }

    public boolean existeToken(HttpServletRequest request){
        String value = request.getHeader(HttpHeaders.AUTHORIZATION);
        return (value != null && value.startsWith(JWTService.PREFIX_TOKEN));
     };

    public String fetchToken(HttpServletRequest request){
        String value = request.getHeader(HttpHeaders.AUTHORIZATION);
        //Sabemos q el token va a venir con el prefijo, un espacion y el token "Bearer 12345687" //

        String[] segmentos = value.split(" ");

        return segmentos[1];
    }


}
