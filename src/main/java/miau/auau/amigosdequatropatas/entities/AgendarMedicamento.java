package miau.auau.amigosdequatropatas.entities;

import miau.auau.amigosdequatropatas.dao.AgendarMedicamentoDAO;
import miau.auau.amigosdequatropatas.util.Conexao;

import java.util.List;

public class AgendarMedicamento {
    private int codAgendarMedicamento;

    private TipoMedicamento medicamento; //codigo do medicamento
    private Animal animal;

    private String dataAplicacao;

    // Construtores


    public AgendarMedicamento(int codAgendarMedicamento, TipoMedicamento medicamento, Animal animal, String dataAplicacao) {
        this.codAgendarMedicamento = codAgendarMedicamento;
        this.medicamento = medicamento;
        this.animal = animal;
        this.dataAplicacao = dataAplicacao;
    }

    public AgendarMedicamento() {
        this(0,null,null,"");
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

    public String getDataAplicacao() {
        return dataAplicacao;
    }

    public void setDataAplicacao(String dataAplicacao) {
        this.dataAplicacao = dataAplicacao;
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
