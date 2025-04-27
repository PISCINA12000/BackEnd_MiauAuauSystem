package miau.auau.amigosdequatropatas.entities;

import miau.auau.amigosdequatropatas.dao.AgendarMedicamentoDAO;
import miau.auau.amigosdequatropatas.util.Conexao;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AgendarMedicamento {

    private int codAgendarMedicamento;
    private TipoMedicamento medicamento; // código do medicamento
    private Animal animal;
    private String dataAplicacao;
    private Boolean status; // Campo de status (lido ou não)

    // Construtores
    public AgendarMedicamento(int codAgendarMedicamento, TipoMedicamento medicamento, Animal animal, String dataAplicacao, Boolean status) {
        this.codAgendarMedicamento = codAgendarMedicamento;
        this.medicamento = medicamento;
        this.animal = animal;
        this.dataAplicacao = dataAplicacao;
        this.status = status;
    }

    public AgendarMedicamento() {
        this(0, null, null, "", false); // Inicializa com status false (não lido)
    }

    // Getters e Setters
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public boolean incluir(Conexao conexao) {
        AgendarMedicamentoDAO agendarMedicamentoDAO = new AgendarMedicamentoDAO();
        return agendarMedicamentoDAO.gravar(this, conexao); // Grava no banco
    }

    public List<AgendarMedicamento> consultar(String filtro, Conexao conexao) {
        AgendarMedicamentoDAO agendarMedicamentoDAO = new AgendarMedicamentoDAO();
        return agendarMedicamentoDAO.get(filtro, conexao);
    }

    public AgendarMedicamento consultarID(int id, Conexao conexao) {
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