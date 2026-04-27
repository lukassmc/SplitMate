package ar.com.splitmate.api.rest;

import ar.com.splitmate.User;
import ar.com.splitmate.servicios.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
public class UserRestController {
    @Autowired
    private UserService userService;

    @GetMapping(value ="/api/users",consumes = MediaType.APPLICATION_JSON_VALUE ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> list(){
        List<User> users = this.userService.listAll();

        return ResponseEntity.ok(users);
    };
}
