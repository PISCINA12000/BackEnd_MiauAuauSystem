<<<<<<<< HEAD:src/main/java/miau/auau/amigosdequatropatas/modelReserva/Agendamento.java
package miau.auau.amigosdequatropatas.modelReserva;
========
package miau.auau.amigosdequatropatas.entidades;
>>>>>>>> 712e201 (Implementação do esqueleto dos rests (Animal e TipoLancamento). Sem o Banco de Dados):src/main/java/miau/auau/amigosdequatropatas/entidades/Agendamento.java

public class Agendamento {
    private int codMedi; //codigo do medicamento
    private int codAnimal;
    private String periodo;

    // Construtores

    public Agendamento(int codMedi, int codAnimal, String periodo) {
        this.codMedi = codMedi;
        this.codAnimal = codAnimal;
        this.periodo = periodo;
    }
    public Agendamento() {
        this(0,0,"");
    }

    // Gets e Sets
    public int getCodMedi() {
        return codMedi;
    }

    public void setCodMedi(int codMedi) {
        this.codMedi = codMedi;
    }

    public int getCodAnimal() {
        return codAnimal;
    }

    public void setCodAnimal(int codAnimal) {
        this.codAnimal = codAnimal;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
}
