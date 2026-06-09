
package ar.com.splitmate;

import ar.com.splitmate.enums.Permisos;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table( name = "user")
public class User extends Persistible {

    
    @Column( name = "username")
    private String username;

    @Enumerated(EnumType.ORDINAL)
    @ElementCollection(targetClass = Permisos.class)
    @CollectionTable(name = "PERMISOS_USUARIOS", joinColumns = @JoinColumn(name = "USUARIO_ID"))
    @Column( name = "PERMISO_ID")
    private List<Permisos> permisos;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.permisos = new ArrayList<Permisos>();
        this.permisos.add(Permisos.USUARIO);
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

    public void turnToAdmin(){
        this.permisos.add(Permisos.ADMINISTRADOR);
    }

    public List<GrantedAuthority> collectAuthorities(){
        List<GrantedAuthority> credentials = new ArrayList<GrantedAuthority>();
        System.out.println(this.permisos);

        for (Permisos permiso : this.permisos){
            System.out.println("Este permiso es :" + permiso);
            credentials.add(new SimpleGrantedAuthority(permiso.securityName()));
        }

        System.out.println(credentials);
        return credentials;
    }

    public List<Permisos> getPermisos() {
        return permisos;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
