package miau.auau.amigosdequatropatas.entities;

import miau.auau.amigosdequatropatas.dao.UsuarioDAO;
import miau.auau.amigosdequatropatas.util.Conexao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class Usuario {

    @Autowired
    private UsuarioDAO usuarioDAL;

    private int cod;
    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private String cpf;
    private String privilegio;
    private String sexo;
    private String cep;
    private String rua;
    private String bairro;
    private String numero;

    // Construtores
    public Usuario(int cod, String nome, String email, String senha, String telefone, String cpf, String privilegio, String sexo, String cep, String rua, String bairro, String numero) {
        this.cod = cod;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
        this.cpf = cpf;
        this.privilegio = privilegio;
        this.sexo = sexo;
        this.cep = cep;
        this.rua = rua;
        this.bairro = bairro;
        this.numero = numero;
    }

    public Usuario() {
        this(0, "", "", "", "", "", "", "", "", "", "", "");
    }

    // Gets e Sets
    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPrivilegio() {
        return privilegio;
    }

    public void setPrivilegio(String privilegio) {
        this.privilegio = privilegio;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public boolean incluir(Conexao conexao) {
        return usuarioDAL.gravar(this, conexao);
    }

    public boolean excluir(Conexao conexao) {
        return usuarioDAL.apagar(this, conexao);
    }

    public Usuario consultarID(int id, Conexao conexao) {
        return usuarioDAL.get(id, conexao);
    }

    public List<Usuario> consultar(String filtro, Conexao conexao) {
        return usuarioDAL.get(filtro, conexao);
    }

    public boolean alterar(Conexao conexao) {
        return usuarioDAL.alterar(this, conexao);
    }
}
