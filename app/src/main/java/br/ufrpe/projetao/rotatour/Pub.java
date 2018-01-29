package br.ufrpe.projetao.rotatour;

import android.graphics.Bitmap;

public class Pub {
    long id;
    String user;
    boolean liked;
    String data;
    String pub;
    String imagem;


    public Pub(long id, boolean liked, String user, String data, String pub, String imagem) {
        this.id = id;
        this.liked = liked;
        this.user = user;
        this.data = data;
        this.pub = pub;
        this.imagem = imagem;
    }

    public long getId() {
        return id;
    }

    public boolean isLiked() {
        return liked;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getPub() {
        return pub;
    }

    public void setPub(String pub) {
        this.pub = pub;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

}
