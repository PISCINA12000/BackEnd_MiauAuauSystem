package miau.auau.amigosdequatropatas.entities;

import miau.auau.amigosdequatropatas.dao.AgendarMedicamentoDAO;
import miau.auau.amigosdequatropatas.dao.LancamentoDAO;
import miau.auau.amigosdequatropatas.util.Conexao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AgendarMedicamento {

    @Autowired
    private AgendarMedicamentoDAO agendarMedicamentoDAO;

    //campos da tabela no banco de dados
    private int codAgendarMedicamento;
    private int codMedicamento; // código do medicamento
    private int  codAnimal;
    private String dataAplicacao;
    private Boolean status; // Campo de status (lido ou não)

    // Construtores
    public AgendarMedicamento(int codAgendarMedicamento, int codMedicamento, int codAnimal, String dataAplicacao, Boolean status) {
        this.codAgendarMedicamento = codAgendarMedicamento;
        this.codMedicamento = codMedicamento;
        this.codAnimal = codAnimal;
        this.dataAplicacao = dataAplicacao;
        this.status = status;
        if(agendarMedicamentoDAO == null)
            agendarMedicamentoDAO = new AgendarMedicamentoDAO();
    }

    public AgendarMedicamento() {
        this(0, 0, 0, "", false); // Inicializa com status false (não lido)
    }

    // Getters e Setters


    public int getCodAgendarMedicamento() {
        return codAgendarMedicamento;
    }

    public void setCodAgendarMedicamento(int codAgendarMedicamento) {
        this.codAgendarMedicamento = codAgendarMedicamento;
    }

    public int getCodMedicamento() {
        return codMedicamento;
    }

    public void setCodMedicamento(int codMedicamento) {
        this.codMedicamento = codMedicamento;
    }

    public int getCodAnimal() {
        return codAnimal;
    }

    public void setCodAnimal(int codAnimal) {
        this.codAnimal = codAnimal;
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

    //CRUD
    public boolean incluir(Conexao conexao) {
        return agendarMedicamentoDAO.gravar(this, conexao); // Grava no banco
    }

    public List<AgendarMedicamento> consultar(String filtro, Conexao conexao) {
        return agendarMedicamentoDAO.get(filtro, conexao);
    }

    public AgendarMedicamento consultarID(int id, Conexao conexao) {
        return agendarMedicamentoDAO.get(id, conexao);
    }

    public boolean excluir(Conexao conexao) {
        return agendarMedicamentoDAO.apagar(this, conexao);
    }

    public boolean alterar(Conexao conexao) {
        return agendarMedicamentoDAO.alterar(this, conexao);
    }

    public List<AgendarMedicamento> consultarIdAnimal(int animalId, Conexao conexao) {
        return agendarMedicamentoDAO.getIdAnimal(animalId, conexao);
    }
}