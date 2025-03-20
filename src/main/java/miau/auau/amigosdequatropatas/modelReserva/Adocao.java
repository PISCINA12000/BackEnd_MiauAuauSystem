<<<<<<<< HEAD:src/main/java/miau/auau/amigosdequatropatas/modelReserva/Adocao.java
package miau.auau.amigosdequatropatas.modelReserva;
========
package miau.auau.amigosdequatropatas.entidades;
>>>>>>>> 712e201 (Implementação do esqueleto dos rests (Animal e TipoLancamento). Sem o Banco de Dados):src/main/java/miau/auau/amigosdequatropatas/entidades/Adocao.java

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
