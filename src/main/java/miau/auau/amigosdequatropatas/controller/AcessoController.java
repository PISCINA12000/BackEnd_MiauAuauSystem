package miau.auau.amigosdequatropatas.controller;

import miau.auau.amigosdequatropatas.entities.Usuario;
import miau.auau.amigosdequatropatas.security.JWTTokenProvider;
import miau.auau.amigosdequatropatas.util.Conexao;
import miau.auau.amigosdequatropatas.util.SingletonDB;

import java.util.List;

public class AcessoController
{
    public String autenticar(String nome, String senha)
    {
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();
        Usuario usuario = new Usuario();
        List<Usuario> usuarioList = usuario.consultar(nome, conexao);
        String token = null;
        if (usuarioList.size() > 0)
        {
            usuario = usuarioList.get(0);
            if (usuario.getSenha().equals(senha))
                token = JWTTokenProvider.getToken(nome, usuario.getPrivilegio(), usuario.getCod());
        }

        return token;
    }

}
