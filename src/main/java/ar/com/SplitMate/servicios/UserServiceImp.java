
package ar.com.splitmate.servicios;

import ar.com.splitmate.User;
import ar.com.splitmate.repositorios.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements UserService{
    @Autowired
    private UserRepository repository;
    
    @Override
    public User guardarUsuario(User user){
        this.repository.save(user);
        return user;
    }
    
    @Override
    public User buscarPorId(Long Id){
        return repository.findById(Id).orElseThrow(()-> new RuntimeException("Usuario no encontrado")) ;
        
    }

    @Override
    public User buscarPorUsername(String username){
        return repository.findByUsername(username);
    }

    @Override
    public List<User> listAll(){
        return repository.findAll();
    }

    @Override
    public User getUsuarioLogueado(){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth.getName() == null) {
            throw  new NullPointerException("Username is null.");
        }
        String username = auth.getName();

        return repository.findByUsername(username);
    };
}
