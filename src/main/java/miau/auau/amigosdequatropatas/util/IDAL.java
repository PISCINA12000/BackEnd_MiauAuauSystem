package miau.auau.amigosdequatropatas.util;

import java.util.List;

public interface IDAL<T>{ // tipo generico
    boolean gravar(T entidade, Conexao conexao);
    boolean alterar(T entidade, Conexao conexao);
    boolean apagar(T entidade, Conexao conexao);
    T get(int id, Conexao conexao);
    List<T> get(String filtro, Conexao conexao);
}
