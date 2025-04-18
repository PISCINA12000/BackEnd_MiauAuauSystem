package miau.auau.amigosdequatropatas.modelReserva;

public class Prontuario {
    private int codAnimal;
    private int codLanc;
    private String descricao;
    private String medicamento;

    // Construtores
    public Prontuario(int codAnimal, int codLanc, String descricao, String medicamento) {
        this.codAnimal = codAnimal;
        this.codLanc = codLanc;
        this.descricao = descricao;
        this.medicamento = medicamento;
    }
    public Prontuario() {
        this(0,0,"","");
    }

    // Gets e Sets
    public int getCodAnimal() {
        return codAnimal;
    }

    public void setCodAnimal(int codAnimal) {
        this.codAnimal = codAnimal;
    }

    public int getCodLanc() {
        return codLanc;
    }

    public void setCodLanc(int codLanc) {
        this.codLanc = codLanc;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
    }
}
