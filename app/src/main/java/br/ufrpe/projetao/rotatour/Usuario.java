package br.ufrpe.projetao.rotatour;

/**
 * Created by ikaro on 23/11/2017.
 */

public class Usuario {
    private String email, password, token;

    public Usuario(String email, String password, String token) {
        this.email = email;
        this.password = password;
        this.token = token;
    }


    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }

    public String getPassword() {
        return password;
    }
}
