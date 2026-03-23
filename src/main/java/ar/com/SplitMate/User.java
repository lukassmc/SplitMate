
package ar.com.SplitMate;

public class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
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
}
