package br.ufrpe.projetao.rotatour;

import android.graphics.Bitmap;

public class Pub {
    String user;
    String data;
    String pub;
    Bitmap imagem;


    public Pub(String user, String data, String pub, Bitmap imagem) {

        this.user = user;
        this.data = data;
        this.pub = pub;
        this.imagem = imagem;
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

    public Bitmap getImagem() {
        return imagem;
    }

    public void setImagem(Bitmap imagem) {
        this.imagem = imagem;
    }

}
