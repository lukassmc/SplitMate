
package ar.com.splitmate.servicios;

import ar.com.splitmate.User;

import java.util.List;


public interface UserService {
       
    public User guardarUsuario(User user);
    
    public User buscarPorId(Long id);

    User buscarPorUsername(String username);

    List<User> listAll();
}
