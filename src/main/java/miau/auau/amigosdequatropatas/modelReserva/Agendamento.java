package miau.auau.amigosdequatropatas.modelReserva;

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
