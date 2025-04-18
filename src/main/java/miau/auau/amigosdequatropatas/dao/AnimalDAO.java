package miau.auau.amigosdequatropatas.dao;

import miau.auau.amigosdequatropatas.entities.Animal;
import miau.auau.amigosdequatropatas.util.Conexao;
import miau.auau.amigosdequatropatas.util.IDAL;
import org.springframework.stereotype.Component;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Component
public class AnimalDAO implements IDAL<Animal> {

    @Override
    public boolean gravar(Animal entidade, Conexao conexao) {
        String sql = """
                INSERT INTO animal (ani_nome, ani_sexo, ani_raca, ani_idade, ani_peso, ani_castrado, ani_adotado, ani_imagem)
                VALUES ('#1', '#2', '#3', #4, #5, '#6', '#7', '#8')
                """;
        sql = sql.replace("#1", entidade.getNome())
                .replace("#2", entidade.getSexo())
                .replace("#3", entidade.getRaca())
                .replace("#4", "" + entidade.getIdade())
                .replace("#5", "" + entidade.getPeso())
                .replace("#6", entidade.getCastrado())
                .replace("#7", entidade.getAdotado())
                .replace("#8", entidade.getImagemBase64());
        return conexao.manipular(sql);
    }

    @Override
    public boolean alterar(Animal entidade, Conexao conexao) {
        String sql = """
                UPDATE animal
                SET ani_nome = '#1', ani_sexo = '#2', ani_raca = '#3', ani_idade = #4, ani_peso = #5, 
                    ani_castrado = '#6', ani_adotado = '#7', ani_imagem = '#8'
                WHERE ani_id = #9
                """;
        sql = sql.replace("#1", entidade.getNome())
                .replace("#2", entidade.getSexo())
                .replace("#3", entidade.getRaca())
                .replace("#4", "" + entidade.getIdade())
                .replace("#5", "" + entidade.getPeso())
                .replace("#6", entidade.getCastrado())
                .replace("#7", entidade.getAdotado())
                .replace("#8", entidade.getImagemBase64())
                .replace("#9", "" + entidade.getCodAnimal());
        return conexao.manipular(sql);
    }

    @Override
    public boolean apagar(Animal entidade, Conexao conexao) {
        String sql = "DELETE FROM animal WHERE ani_id = " + entidade.getCodAnimal();
        return conexao.manipular(sql);
    }

    @Override
    public Animal get(int id, Conexao conexao) {
        Animal animal = null;
        String sql = "SELECT * FROM animal WHERE ani_id = " + id;
        ResultSet resultSet = conexao.consultar(sql);
        try {
            if (resultSet.next()) {
                animal = new Animal(
                        id,
                        resultSet.getString("ani_nome"),
                        resultSet.getString("ani_sexo"),
                        resultSet.getString("ani_raca"),
                        resultSet.getInt("ani_idade"),
                        resultSet.getDouble("ani_peso"),
                        resultSet.getString("ani_castrado"),
                        resultSet.getString("ani_adotado"),
                        resultSet.getString("ani_imagem")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return animal;
    }

    @Override
    public List<Animal> get(String filtro, Conexao conexao) {
        List<Animal> lista = new ArrayList<>();
        String sql = "SELECT * FROM animal";
        if (!filtro.isEmpty() && !filtro.equals(" "))
        {
            sql += " WHERE ani_nome ILIKE '%" + filtro + "%'";
        }
        sql += " ORDER BY ani_nome";
        System.out.println("SQL gerado: " + sql);
        ResultSet resultSet = conexao.consultar(sql);
        try {
            while (resultSet.next()) {
                lista.add(new Animal(
                        resultSet.getInt("ani_id"),
                        resultSet.getString("ani_nome"),
                        resultSet.getString("ani_sexo"), // Pegando o char corretamente
                        resultSet.getString("ani_raca"),
                        resultSet.getInt("ani_idade"),
                        resultSet.getDouble("ani_peso"),
                        resultSet.getString("ani_castrado"),
                        resultSet.getString("ani_adotado"),
                        resultSet.getString("ani_imagem")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}
