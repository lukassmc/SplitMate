
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
/*
    Perfecto. Vamos por partes para lo que quiero hacer. Ahora mismo mi proyecto ya cumple con el circuito principal con front. Crea un usuario, crea un grupo, agrega un usuario al grupo y el User pasa a ser GroupMember, y por último se crea un gasto.

Cual es el problema? Que el flujo está mal a este punto del proyecto, ya no deberia crearse un usuario, sino que ya debería de implementar un login, register, y al ya tener un usuario, este pueda crear un grupo, y crear su gasto.

La cosa es que, este es un proyecto para un curso intensivo, por lo cual tampoco me puedo mandar a hacer todo de una, tengo que seguir un ritmo. Lo que te pegué es la totalidad de las clases, actualmente tengo vistas hasta spring web 2 y tengo que ver Apis rest. Mas al final esta la clase para el login con api rest, por lo tanto no se si me estaré adelantando, o si se puede hacer un login simple sin usar api rest por el momento.



Sacando ese factor, mi idea es esa, que como en cualquier web normal, entre el usuario, y pueda crear su grupo, e invitar a mas usuarios(mas adelante), mi profesor me dijo que en vez de invitaciones, en la sección de los grupos te deje ver TODOS los grupos, y de ahi elegir al cual deseas unirte, pero no me parece buena idea pensando en el futuro donde hayan muchisimos grupos. Para mí deberia ser, o crear un grupo, o que se te invite a un grupo mediante una invitación por link.

Además, ahora mismo, el paso 3, agregar un usuario a un grupo, nisiquiera se hace por el front, hay que hacerlo por la consola, y uno personalmente le tiene que pasar la ID tanto del usuario como del grupo de la bd, osea, completamente mal. Quiero reformular todo eso a como te expliqué                                                                                                                                                                                                                                                                          */
