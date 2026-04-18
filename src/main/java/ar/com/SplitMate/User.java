
package ar.com.splitmate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table( name = "user")
public class User extends Persistible{

    
    @Column( name = "username")
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    public User() {};
    
    public boolean login(String username, String password){
            if(username.length() < 3 && password.length() < 3){
                System.out.println("Las credenciales no cumplen los requisitos de longitud.");  
            }
            
           if(username.equals(this.username) && password.equals(this.password)){
                System.out.println("Sesión iniciada correctamente.");
                        return true;
            } else {
            return false;
                    }
            
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
