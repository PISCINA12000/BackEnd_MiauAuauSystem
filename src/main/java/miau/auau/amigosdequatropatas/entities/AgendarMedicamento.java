package miau.auau.amigosdequatropatas.entities;

public class AgendarMedicamento {
    private TipoMedicamento medicamento; //codigo do medicamento
    private Animal animal;

    private int Intervalo; //de quanto em quanto tempo
    private String Formato; //dia ou hora

    private int Periodo; //durante quanto tempo

    // Construtores


    public AgendarMedicamento(TipoMedicamento medicamento, Animal animal, int intervalo, String formato, int periodo) {
        this.medicamento = medicamento;
        this.animal = animal;
        Intervalo = intervalo;
        Formato = formato;
        Periodo = periodo;
    }

    public AgendarMedicamento() {
        this(null,null,0,"",0);
    }

    // Gets e Sets

    public TipoMedicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(TipoMedicamento medicamento) {
        this.medicamento = medicamento;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public int getIntervalo() {
        return Intervalo;
    }

    public void setIntervalo(int intervalo) {
        Intervalo = intervalo;
    }

    public String getFormato() {
        return Formato;
    }

    public void setFormato(String formato) {
        Formato = formato;
    }

    public int getPeriodo() {
        return Periodo;
    }

    public void setPeriodo(int periodo) {
        Periodo = periodo;
    }
}
