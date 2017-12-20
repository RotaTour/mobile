package br.ufrpe.projetao.rotatour;

public class Local {
    String cardName;
    String atividade;
    int imageResourceId;

    public Local(String cardName, String atividade, int imageResourceId) {
        this.cardName = cardName;
        this.atividade = atividade;
        this.imageResourceId = imageResourceId;
    }

    public Local(){

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
    public int getImageResourceId() {
        return imageResourceId;
    }
    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }
}
