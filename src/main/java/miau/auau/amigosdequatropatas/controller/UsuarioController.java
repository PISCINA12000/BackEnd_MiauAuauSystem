package miau.auau.amigosdequatropatas.controller;

import miau.auau.amigosdequatropatas.entities.Usuario;
import miau.auau.amigosdequatropatas.util.Conexao;
import miau.auau.amigosdequatropatas.util.SingletonDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class UsuarioController {

    @Autowired
    private Usuario usuarioModel;

    public boolean onGravar(Usuario usuario) {
        if (validar(usuario))
            return usuarioModel.incluir(usuario);
        else
            return false;
    }

    public boolean onDelete(int id) {
        if (usuarioModel.consultarID(id) != null) // achou tipo de lançamento
        {
            return usuarioModel.excluir(usuarioModel.consultarID(id));
        } else
            return false;
    }

    public Usuario onBuscarId(int id) {
        if (usuarioModel.consultarID(id) != null) // achou tipo de lançamento
        {
            return usuarioModel.consultarID(id);
        } else
            return null;
    }

    public List<Map<String, Object>> onBuscar(String filtro) {
        //criar conexao
        SingletonDB singletonDB = new SingletonDB();
        Conexao conexao = new Conexao();
        List<Usuario> listaUsers;

        //mandar para a model
        listaUsers = usuarioModel.consultar(filtro, conexao);
        if (!listaUsers.isEmpty()) {

        }
    }

    public boolean onAlterar(Usuario usuario) {
        if (validarAlterar(usuario)) {
            return usuarioModel.alterar(usuario);
        } else
            return false;
    }

    public boolean validar(Usuario usuario) {
        return usuario != null && !usuario.getNome().isEmpty();
    }

    public boolean validarAlterar(Usuario usuario) {
        return usuario != null && usuario.getCod() > 0 && !usuario.getNome().isEmpty();
    }
}
