package miau.auau.amigosdequatropatas.modelReserva;

public class Adocao {
    private int codAnimal;
    private int codUsuario;
    private String data;

    // Construtores

    public Adocao(int codAnimal, int codUsuario, String data) {
        this.codAnimal = codAnimal;
        this.codUsuario = codUsuario;
        this.data = data;
    }
    public Adocao() {
        this(0,0,"");
    }

    // Gets e Sets
    public int getCodAnimal() {
        return codAnimal;
    }

    public void setCodAnimal(int codAnimal) {
        this.codAnimal = codAnimal;
    }

    public int getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(int codUsuario) {
        this.codUsuario = codUsuario;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
