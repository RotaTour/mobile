package br.ufrpe.projetao.rotatour;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class Local {
    String cardName;
    String atividade;
    String googlePlaceId;
    Bitmap imagem;
    int resourceId;

    public Local(String cardName, String atividade, String googlePlaceId, Bitmap imagem) {
        this.cardName = cardName;
        this.atividade = atividade;
        this.googlePlaceId = googlePlaceId;
        this.imagem = imagem;
    }

    public Local(){
    }


    public int getImageResourceId(){
        return this.resourceId;
    }
    public void setImageResourceId(int resourceId){
        this.resourceId = resourceId;
    }
    public String getAtividade(){
        return atividade;
    }
    public void setAtividade(String atv){
        atividade=atv;
    }
    public String getCardName() {
        return cardName;
    }
    public void setCardName(String cardName) {
        this.cardName = cardName;
    }
    public Bitmap getImagem() {
        return imagem;
    }
    public void setImagem(Bitmap imagem) {
        this.imagem = imagem;
    }

    public String getGooglePlaceId() {
        return googlePlaceId;
    }

    public void setGooglePlaceId(String googlePlaceId) {
        this.googlePlaceId = googlePlaceId;
    }
}
