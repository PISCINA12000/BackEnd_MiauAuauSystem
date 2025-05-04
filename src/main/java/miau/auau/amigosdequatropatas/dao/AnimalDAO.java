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
                INSERT INTO animal (ani_nome, ani_sexo, ani_raca, ani_peso, ani_castrado, ani_adotado, ani_imagem, ani_dtnasc, ani_cor, ani_especie)
                VALUES ('{#1}', '{#2}', '{#3}', {#4}, '{#5}', '{#6}', '{#7}', '{#8}', '{#10}', '{#11}')
                """;
        sql = sql.replace("{#1}", entidade.getNome())
                .replace("{#2}", entidade.getSexo())
                .replace("{#3}", entidade.getRaca())
                .replace("{#4}", "" + entidade.getPeso())
                .replace("{#5}", entidade.getCastrado())
                .replace("{#6}", entidade.getAdotado())
                .replace("{#7}", entidade.getImagemBase64())
                .replace("{#8}", entidade.getDataNascimento())
                .replace("{#10}", entidade.getCor())
                .replace("{#11}", entidade.getEspecie());

        return conexao.manipular(sql);
    }

    @Override
    public boolean alterar(Animal entidade, Conexao conexao) {
        String sql = """
                UPDATE animal
                SET ani_nome = '{#1}', ani_sexo = '{#2}', ani_raca = '{#3}', ani_peso = {#4}, 
                    ani_castrado = '{#5}', ani_adotado = '{#6}', ani_imagem = '{#7}', ani_dtnasc = '{#8}',
                    ani_cor = '{#10}', ani_especie = '{#11}'
                WHERE ani_id = {#9}
                """;
        sql = sql.replace("{#1}", entidade.getNome())
                .replace("{#2}", entidade.getSexo())
                .replace("{#3}", entidade.getRaca())
                .replace("{#4}", "" + entidade.getPeso())
                .replace("{#5}", entidade.getCastrado())
                .replace("{#6}", entidade.getAdotado())
                .replace("{#7}", entidade.getImagemBase64())
                .replace("{#8}", entidade.getDataNascimento())
                .replace("{#9}", "" + entidade.getCodAnimal())
                .replace("{#10}", entidade.getCor())
                .replace("{#11}", entidade.getEspecie());
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
                        resultSet.getString("ani_dtnasc"),
                        resultSet.getDouble("ani_peso"),
                        resultSet.getString("ani_castrado"),
                        resultSet.getString("ani_adotado"),
                        resultSet.getString("ani_imagem"),
                        resultSet.getString("ani_cor"),
                        resultSet.getString("ani_especie")
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
                        resultSet.getString("ani_dtnasc"),
                        resultSet.getDouble("ani_peso"),
                        resultSet.getString("ani_castrado"),
                        resultSet.getString("ani_adotado"),
                        resultSet.getString("ani_imagem"),
                        resultSet.getString("ani_cor"),
                        resultSet.getString("ani_especie")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
    public List<Animal> getFiltro(String filtro, Conexao conexao)
    {
        List<Animal> lista = new ArrayList<>();
        String aux = "";
        String sql = "SELECT * FROM animal";
        String filtroEspecie = "";
        String filtroRaca = "";
        String filtroCor = "";
        String filtroSexo = "";
        if (!filtro.isEmpty() && !filtro.equals(" "))
        {
            int i = 0;
            while(i < filtro.length() && filtro.charAt(i) != ' ')
            {
                filtroCor += filtro.charAt(i);
                i++;
            }
            i++;
            while (i < filtro.length() && filtro.charAt(i) != ' ')
            {
                filtroEspecie += filtro.charAt(i);
                i++;
            }
            i++;
            while (i < filtro.length() && filtro.charAt(i) != ' ')
            {
                filtroSexo += filtro.charAt(i);
                i++;
            }
            i++;
            while (i < filtro.length())
            {
                filtroRaca += filtro.charAt(i);
                i++;
            }
            if (!filtroCor.isEmpty())
            {
                aux = aux + " ani_cor = '"+filtroCor+"'";
            }

            if (!filtroEspecie.isEmpty())
            {
                if (!aux.isEmpty())
                {
                    aux = aux + " AND " + "ani_especie = '"+filtroEspecie+"'";
                }
                else
                {
                    aux = aux + " ani_especie = '"+filtroEspecie+"'";
                }
            }

            if (!filtroSexo.isEmpty())
            {
                if (!aux.isEmpty())
                {
                    aux = aux + " AND " + "ani_sexo = '"+filtroSexo+"'";
                }
                else
                {
                    aux = aux + " ani_sexo = '"+filtroSexo+"'";
                }
            }
            if (!filtroRaca.isEmpty())
            {
                if (!aux.isEmpty())
                {
                    aux = aux + " AND " + "ani_raca = '"+filtroRaca+"'";
                }
                else
                {
                    aux = aux + " ani_raca = '"+filtroRaca+"'";
                }
            }

            sql = sql + " WHERE" + aux;
        }
        sql += " ORDER BY ani_nome";
        System.out.println("SQL gerado: " + sql);
        ResultSet resultSet = conexao.consultar(sql);
        try {
            if (resultSet != null)
            {
                while (resultSet.next()) {
                lista.add(new Animal(
                        resultSet.getInt("ani_id"),
                        resultSet.getString("ani_nome"),
                        resultSet.getString("ani_sexo"),
                        resultSet.getString("ani_raca"),
                        resultSet.getString("ani_dtnasc"),
                        resultSet.getDouble("ani_peso"),
                        resultSet.getString("ani_castrado"),
                        resultSet.getString("ani_adotado"),
                        resultSet.getString("ani_imagem"),
                        resultSet.getString("ani_cor"),
                        resultSet.getString("ani_especie")
                    ));
                }
            }
            else
            {
                lista = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;

    }
    public List<String> getCor(Conexao conexao)
    {
        List<String> lista = new ArrayList<>();
        String sql;
        sql = "SELECT DISTINCT ani_cor FROM animal ORDER BY ani_cor";
        ResultSet resultSet = conexao.consultar(sql);
        try {
            while (resultSet.next()) {
                lista.add(resultSet.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<String> getRaca(Conexao conexao)
    {
        List<String> lista = new ArrayList<>();
        String sql;
        sql = "SELECT DISTINCT ani_raca FROM animal ORDER BY ani_raca";
        ResultSet resultSet = conexao.consultar(sql);
        try {
            while (resultSet.next()) {
                lista.add(resultSet.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}
