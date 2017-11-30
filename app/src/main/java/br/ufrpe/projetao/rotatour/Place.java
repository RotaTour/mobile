package br.ufrpe.projetao.rotatour;

public class Place {
    String cardName;
    String atividade;
    int imageResourceId;

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
    public int getImageResourceId() {
        return imageResourceId;
    }
    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }
}
