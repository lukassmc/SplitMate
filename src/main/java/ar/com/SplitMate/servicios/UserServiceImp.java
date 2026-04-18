
package ar.com.splitmate.servicios;

import ar.com.splitmate.User;
import ar.com.splitmate.repositorios.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService{
    @Autowired
    private UserRepository repository;
    
    @Override
    public void guardarUsuario(User user){
        this.repository.save(user);
    }
    
    @Override
    public User buscarPorId(Long Id){
        return repository.findById(Id).orElseThrow(()-> new RuntimeException("Usuario no encontrado")) ;
        
    }
}
