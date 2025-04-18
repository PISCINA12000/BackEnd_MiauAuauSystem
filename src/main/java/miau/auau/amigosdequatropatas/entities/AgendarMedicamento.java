package miau.auau.amigosdequatropatas.entities;

import miau.auau.amigosdequatropatas.dao.AgendarMedicamentoDAO;
import miau.auau.amigosdequatropatas.util.Conexao;

import java.util.List;

public class AgendarMedicamento {
    private int codAgendarMedicamento;

    private TipoMedicamento medicamento; //codigo do medicamento
    private Animal animal;

    private int Intervalo; //de quanto em quanto tempo
    private String Formato; //dia ou hora

    private int Periodo; //durante quanto tempo

    // Construtores


    public AgendarMedicamento(int codAgendarMedicamento, TipoMedicamento medicamento, Animal animal, int intervalo, String formato, int periodo) {
        this.codAgendarMedicamento = codAgendarMedicamento;
        this.medicamento = medicamento;
        this.animal = animal;
        Intervalo = intervalo;
        Formato = formato;
        Periodo = periodo;
    }

    public AgendarMedicamento() {
        this(0,null,null,0,"",0);
    }

    // Gets e Sets


    public int getCodAgendarMedicamento() {
        return codAgendarMedicamento;
    }

    public void setCodAgendarMedicamento(int codAgendarMedicamento) {
        this.codAgendarMedicamento = codAgendarMedicamento;
    }

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

    public boolean incluir(Conexao conexao) {

        AgendarMedicamentoDAO agendarMedicamentoDAO = new AgendarMedicamentoDAO();
        return agendarMedicamentoDAO.gravar(this, conexao); // grava no banco
    }

    public List<AgendarMedicamento> consultar(String filtro, Conexao conexao) {

        AgendarMedicamentoDAO agendarMedicamentoDAO = new AgendarMedicamentoDAO();
        return agendarMedicamentoDAO.get(filtro, conexao);
    }

    public AgendarMedicamento consultarID(int id, Conexao conexao)
    {
        AgendarMedicamentoDAO agendarMedicamentoDAO = new AgendarMedicamentoDAO();
        return agendarMedicamentoDAO.get(id, conexao);
    }

    public boolean excluir(Conexao conexao) {
        AgendarMedicamentoDAO agendarMedicamentoDAO = new AgendarMedicamentoDAO();
        return agendarMedicamentoDAO.apagar(this, conexao);
    }
    public boolean alterar(Conexao conexao) {
        AgendarMedicamentoDAO agendarMedicamentoDAO = new AgendarMedicamentoDAO();
        return agendarMedicamentoDAO.alterar(this, conexao);
    }
}
