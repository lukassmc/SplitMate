package ar.com.splitmate.config;

import ar.com.splitmate.config.filters.JWTAuthorizationFilter;
import ar.com.splitmate.enums.Permisos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JWTAuthorizationFilter jwtFilter;

    @Bean
    public SecurityFilterChain apiFilterCHain(HttpSecurity http){


        try {
            return http.securityMatcher("/api/**")
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .authorizeHttpRequests(request -> request.requestMatchers("/api/login").permitAll().anyRequest().authenticated())
                    .csrf(csrf -> csrf.disable())
                    .addFilterAfter(jwtFilter, BasicAuthenticationFilter.class)
                    .build()
            ;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    };
    @Bean
    public SecurityFilterChain webFilterCHain(HttpSecurity http){

        try {
            return  http.securityMatcher("/**")
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                    .authorizeHttpRequests(auth -> auth.requestMatchers("/sec/**").hasAnyRole(Permisos.ADMINISTRADOR.name(), Permisos.USUARIO.name())
                            .requestMatchers("/sec/admin/**").hasAnyRole(Permisos.ADMINISTRADOR.name())
                            .anyRequest().permitAll())
                            .addFilterAfter(jwtFilter, BasicAuthenticationFilter.class)

                            .formLogin(form -> form.disable()).build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    };
}
