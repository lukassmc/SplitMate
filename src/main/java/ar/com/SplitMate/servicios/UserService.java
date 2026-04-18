
package ar.com.splitmate.servicios;

import ar.com.splitmate.User;


public interface UserService {
       
    public void guardarUsuario(User user);
    
    public User buscarPorId(Long id);
}
