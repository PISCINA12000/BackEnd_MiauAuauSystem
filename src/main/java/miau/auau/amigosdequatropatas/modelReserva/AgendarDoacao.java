package miau.auau.amigosdequatropatas.modelReserva;

public class AgendarDoacao {
    private int cod;
    private double valor;

    // Construtores
    public AgendarDoacao(int cod, double valor) {
        this.cod = cod;
        this.valor = valor;
    }
    public AgendarDoacao() {
        this(0,0);
    }

    // Gets e Sets
    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
