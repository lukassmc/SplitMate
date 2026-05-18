package ar.com.splitmate.enums;

public enum Permisos {
    ADMINISTRADOR,
    USUARIO;

    public String securityName(){
        return "ROLE_" + name();

    }
}
