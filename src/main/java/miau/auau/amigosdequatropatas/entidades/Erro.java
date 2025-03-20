package miau.auau.amigosdequatropatas.entidades;

public class Erro {
    private String mensagem;

    // Construtores
    public Erro(String mensagem) {
        this.mensagem = mensagem;
    }
    public Erro () {
        this("");
    }

    // Gets e Sets
    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
