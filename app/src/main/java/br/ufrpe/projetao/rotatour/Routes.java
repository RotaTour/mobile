package br.ufrpe.projetao.rotatour;

/**
 * Created by Victor Alexandre on 1/15/2018.
 */

public class Routes {
    private int id;
    private String name, description, created;


  /*  public Routes(int id, String name, String description, String created){
        this.name = name;
        this.id = id;
        this.description = description;
        this.created = created;

    }*/
    public Routes(String name, String description, String created, int id){
        this.name = name;
        this.description = description;
        this.created = created;
        this.id = id;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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


    public int get(int position) {
        return position;
    }
}
