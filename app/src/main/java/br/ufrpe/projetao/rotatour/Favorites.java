package br.ufrpe.projetao.rotatour;

/**
 * Created by Victor Alexandre on 2/12/2018.
 */

public class Favorites {

    private int id;
    private String name, description, created;

    public Favorites(String name, String description, String created, int id){
        this.name = name;
        this.description = description;
        this.created = created;
        this.id = id;

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
