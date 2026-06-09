package ar.com.splitmate.api.rest;


import ar.com.splitmate.forms.LoginForm;
import ar.com.splitmate.User;
import ar.com.splitmate.servicios.JWTService;
import ar.com.splitmate.servicios.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoginRestService {

    public static final String API_LOGIN_URL = "/api/login";

    @Autowired
    private UserService userService;
    @Autowired
    private JWTService jwtService;

    @PostMapping(value = API_LOGIN_URL, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> login (@RequestBody LoginForm formulario){

        String token = null;
        ResponseEntity<String> resultado = null;

        User user = this.userService.buscarPorUsername(formulario.getUsername());
        if (user == null){
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            token = this.jwtService.buildToken(user);
            resultado = ResponseEntity.ok(token);

        }

        return resultado;

    }
}
