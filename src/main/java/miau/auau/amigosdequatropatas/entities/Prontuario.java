package miau.auau.amigosdequatropatas.entities;



import miau.auau.amigosdequatropatas.dao.ProntuarioDAO;
import miau.auau.amigosdequatropatas.util.Conexao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class Prontuario {

    @Autowired
    private ProntuarioDAO prontuarioDAO;

    //campos da tabela no banco de dados
    private int cod;
    private int codAnimal;
    private String data;
    private String tipoRegistro;
    private String observacao;
    private byte[] PDF;

    //construtores
    public Prontuario(int cod, int codAnimal, String data, String tipoRegistro, String observacao, byte[] PDF) {
        this.cod = cod;
        this.codAnimal = codAnimal;
        this.data = data;
        this.tipoRegistro = tipoRegistro;
        this.observacao = observacao;
        this.PDF = PDF;
        if(prontuarioDAO == null)
            prontuarioDAO = new ProntuarioDAO();
    }
    public Prontuario(){this(0,0,"","","", null);}

    //GETS E SETS


    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public int getCodAnimal() {
        return codAnimal;
    }

    public void setCodAnimal(int codAnimal) {
        this.codAnimal = codAnimal;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTipoRegistro() {
        return tipoRegistro;
    }

    public void setTipoRegistro(String tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public byte[] getPDF() {
        return PDF;
    }

    public void setPDF(byte[] PDF) {
        this.PDF = PDF;
    }



    //CRUD

    //aqui retorna com filtro(pelo tipo de registro) ou sem filtro,  ordenado pela data
    public List<Prontuario> consultar(String filtro, Conexao conexao) {
        return prontuarioDAO.get(filtro, conexao);
    }

    public List<Prontuario> consultarPorData(String dataIni, String dataFim, Conexao conexao) {
        return prontuarioDAO.getByData(dataIni, dataFim, conexao);
    }

    //id do registro do prontuario
    public Prontuario consultarID(int id, Conexao conexao) {
        return prontuarioDAO.get(id, conexao);
    }


    //consulta ID animal
    public List<Prontuario> consultarPorIdAnimal(int id, Conexao conexao) {
        return prontuarioDAO.getIdAnimal(id, conexao);
    }



    public boolean incluir(Conexao conexao) {
        return prontuarioDAO.gravar(this, conexao); // grava no banco
    }

    public boolean excluir(Conexao conexao) {
        return prontuarioDAO.apagar(this, conexao);
    }

    public boolean alterar(Conexao conexao) {
        return prontuarioDAO.alterar(this, conexao);
    }
}
