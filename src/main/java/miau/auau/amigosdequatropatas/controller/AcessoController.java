package miau.auau.amigosdequatropatas.controller;

import miau.auau.amigosdequatropatas.dao.UsuarioDAO;
import miau.auau.amigosdequatropatas.entities.Usuario;
import miau.auau.amigosdequatropatas.security.JWTTokenProvider;
import miau.auau.amigosdequatropatas.util.Conexao;
import miau.auau.amigosdequatropatas.util.SingletonDB;

import java.util.List;

public class AcessoController
{
    public String autenticar(String email, String senha)
    {
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();
        Usuario usuario = new Usuario();
        Usuario novoUsuario;
        novoUsuario = usuario.consultarEmail(email, conexao);
        String token = null;
        if (novoUsuario != null)
        {
            if (novoUsuario.getSenha().equals(senha))
                token = JWTTokenProvider.getToken(novoUsuario.getNome(), novoUsuario.getPrivilegio(), novoUsuario.getCod());
        }

        return token;
    }

}
