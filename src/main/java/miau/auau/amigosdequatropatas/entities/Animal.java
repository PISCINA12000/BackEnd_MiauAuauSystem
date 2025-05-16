package miau.auau.amigosdequatropatas.entities;

import miau.auau.amigosdequatropatas.dao.AnimalDAO;
import miau.auau.amigosdequatropatas.util.Conexao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
public class Animal {

    @Autowired
    private AnimalDAO animalDAL;

    private int codAnimal;
    private String nome;
    private String sexo;
    private String raca;
    private String dataNascimento;
    private double peso;
    private String castrado;
    private String adotado;
    private String imagemBase64;
    private String cor;
    private String especie;
    // tratar foto depois

    // Construtores
    public Animal(int codAnimal, String nome, String sexo, String raca, String dataNascimento, double peso, String castrado, String adotado, String imagemBase64, String cor, String especie) {
        this.codAnimal = codAnimal;
        this.nome = nome;
        this.sexo = sexo;
        this.raca = raca;
        this.dataNascimento = dataNascimento;
        this.peso = peso;
        this.castrado = castrado;
        this.adotado = adotado;
        this.imagemBase64 = imagemBase64;
        if(animalDAL==null)
            animalDAL = new AnimalDAO();
        this.cor = cor;
        this.especie = especie;
    }

    public Animal() {
        this(0, "", "", "", "", 0, "", "", "", "", "");
    }

    // Gets e Sets --------------------------------------------------------------------
    public int getCodAnimal() {
        return codAnimal;
    }

    public void setCodAnimal(int codAnimal) {
        this.codAnimal = codAnimal;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getCastrado() {
        return castrado;
    }

    public void setCastrado(String castrado) {
        this.castrado = castrado;
    }

    public String getAdotado() {
        return adotado;
    }

    public void setAdotado(String adotado) {
        this.adotado = adotado;
    }

    public String getImagemBase64() {
        return imagemBase64;
    }

    public void setImagemBase64(String imagemBase64) {
        this.imagemBase64 = imagemBase64;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    // CRUD --------------------------------------------------------------------------
    public boolean incluir(Conexao conexao) {
        return animalDAL.gravar(this, conexao); // grava no banco
    }

    public boolean excluir(Conexao conexao) {
        return animalDAL.apagar(this, conexao);
    }

    public Animal consultarID(int id, Conexao conexao) {
        return animalDAL.get(id, conexao);
    }

    public List<String> consultarCor(Conexao conexao) {
        return animalDAL.getCor(conexao);
    }
    public List<String> consultarRaca(Conexao conexao) {
        return animalDAL.getRaca(conexao);
    }

    public List<Animal> consultar(String filtro, Conexao conexao) {
        return animalDAL.get(filtro, conexao);
    }

    public boolean alterar(Conexao conexao) {
        return animalDAL.alterar(this, conexao);
    }

    public List<Animal> consultarFiltro(String filtro, Conexao conexao)
    {
        return animalDAL.getFiltro(filtro, conexao);
    }
}
