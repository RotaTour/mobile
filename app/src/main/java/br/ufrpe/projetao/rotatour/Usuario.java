package br.ufrpe.projetao.rotatour;

/**
 * Created by ikaro on 23/11/2017.
 */

public class Usuario {
    private int id;
    private String username, email, gender;

    public Usuario(int id, String username, String email, String gender) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }
}
