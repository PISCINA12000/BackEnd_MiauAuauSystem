package miau.auau.amigosdequatropatas.controller;

import miau.auau.amigosdequatropatas.dao.AnimalDAO;
import miau.auau.amigosdequatropatas.dao.TipoMedicamentoDAO;
import miau.auau.amigosdequatropatas.entities.AgendarMedicamento;
import miau.auau.amigosdequatropatas.entities.Animal;
import miau.auau.amigosdequatropatas.entities.TipoMedicamento;
import miau.auau.amigosdequatropatas.util.Conexao;
import miau.auau.amigosdequatropatas.util.SingletonDB;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AgendarMedicamentoController {

    public boolean onGravar(Map<String, Object> json) {
        System.out.println(json);
        if (validar(json)) {
            // Criar a conexão para o banco de dados
            SingletonDB singletonDB = SingletonDB.getInstance();
            Conexao conexao = singletonDB.getConexao();

            AgendarMedicamento agendarMedicamento = new AgendarMedicamento();

            AnimalDAO animalDAO = new AnimalDAO();
            Animal animal = animalDAO.get((int) json.get("animal"), conexao);

            TipoMedicamentoDAO tipoMedicamentoDAO = new TipoMedicamentoDAO();
            TipoMedicamento tipoMedicamento = tipoMedicamentoDAO.get((int) json.get("medicamento"), conexao);

            String dataAplicacao = json.get("dataAplicacao").toString();

            Boolean status = (Boolean) json.get("status");

            agendarMedicamento.setAnimal(animal);
            agendarMedicamento.setMedicamento(tipoMedicamento);
            agendarMedicamento.setDataAplicacao(dataAplicacao);
            agendarMedicamento.setStatus(status);  // Atribuindo o status

            if (agendarMedicamento.incluir(conexao)) {
                return true;
            }

            return false;
        }
        return false;
    }


    public List<Map<String, Object>> onBuscar(String filtro) {
        // Criando a conexão
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        // Criando a lista que conterá os JSON's
        AgendarMedicamento agendarMedicamento = new AgendarMedicamento();
        List<AgendarMedicamento> lista = agendarMedicamento.consultar(filtro, conexao);

        if (lista != null) {
            List<Map<String, Object>> listaJson = new ArrayList<>();
            for (int i = 0; i < lista.size(); i++) {
                AgendarMedicamento am = lista.get(i);

                Map<String, Object> jsonAnimal = new HashMap<>();
                jsonAnimal.put("codAnimal", am.getAnimal().getCodAnimal());
                jsonAnimal.put("nome", am.getAnimal().getNome());
                jsonAnimal.put("sexo", am.getAnimal().getSexo());
                jsonAnimal.put("raca", am.getAnimal().getRaca());
                jsonAnimal.put("dataNascimento", am.getAnimal().getDataNascimento());
                jsonAnimal.put("peso", am.getAnimal().getPeso());
                jsonAnimal.put("castrado", am.getAnimal().getCastrado());
                jsonAnimal.put("adotado", am.getAnimal().getAdotado());
                jsonAnimal.put("especie", am.getAnimal().getEspecie());
                jsonAnimal.put("cor", am.getAnimal().getCor());
                jsonAnimal.put("imagemBase64", am.getAnimal().getImagemBase64());

                Map<String, Object> jsonTipoMedicamento = new HashMap<>();
                jsonTipoMedicamento.put("codTipoMedicamento", am.getMedicamento().getCod());
                jsonTipoMedicamento.put("nome", am.getMedicamento().getNome());
                jsonTipoMedicamento.put("forma", am.getMedicamento().getFormaFarmaceutica());
                jsonTipoMedicamento.put("descricao", am.getMedicamento().getDescricao());

                // Adicionando o status no JSON
                Map<String, Object> jsonAgendarMedicamento = new HashMap<>();
                jsonAgendarMedicamento.put("codAgendarMedicamento", am.getCodAgendarMedicamento());
                jsonAgendarMedicamento.put("animal", jsonAnimal);
                jsonAgendarMedicamento.put("medicamento", jsonTipoMedicamento);
                jsonAgendarMedicamento.put("dataAplicacao", am.getDataAplicacao());
                jsonAgendarMedicamento.put("status", am.getStatus());  // Adicionando o campo de status

                listaJson.add(jsonAgendarMedicamento);
            }
            return listaJson;
        } else {
            return null;
        }
    }


    public boolean onDelete(int id) {
        // Criando a conexão
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        // Consultando o agendamento do medicamento pelo ID
        AgendarMedicamento agendarMedicamento = new AgendarMedicamento();
        agendarMedicamento = agendarMedicamento.consultarID(id, conexao);
        // Se o agendamento for encontrada, exclui; caso contrário, retorna false
        if (agendarMedicamento != null)
            return agendarMedicamento.excluir(conexao);
        return false;
    }

    public Map<String, Object> onBuscarId(int id) {
        // Criando a conexão
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        // Consultando o agendamento pelo ID
        AgendarMedicamento agendarMedicamento = new AgendarMedicamento();
        agendarMedicamento = agendarMedicamento.consultarID(id, conexao);

        if (agendarMedicamento != null) {
            Map<String, Object> jsonAnimal = new HashMap<>();
            jsonAnimal.put("codAnimal", agendarMedicamento.getAnimal().getCodAnimal());
            jsonAnimal.put("nome", agendarMedicamento.getAnimal().getNome());
            jsonAnimal.put("sexo", agendarMedicamento.getAnimal().getSexo());
            jsonAnimal.put("raca", agendarMedicamento.getAnimal().getRaca());
            jsonAnimal.put("dataNascimento", agendarMedicamento.getAnimal().getDataNascimento());
            jsonAnimal.put("peso", agendarMedicamento.getAnimal().getPeso());
            jsonAnimal.put("castrado", agendarMedicamento.getAnimal().getCastrado());
            jsonAnimal.put("adotado", agendarMedicamento.getAnimal().getAdotado());
            jsonAnimal.put("especie", agendarMedicamento.getAnimal().getEspecie());
            jsonAnimal.put("cor", agendarMedicamento.getAnimal().getCor());
            jsonAnimal.put("imagemBase64", agendarMedicamento.getAnimal().getImagemBase64());

            Map<String, Object> jsonTipoMedicamento = new HashMap<>();
            jsonTipoMedicamento.put("codTipoMedicamento", agendarMedicamento.getMedicamento().getCod());
            jsonTipoMedicamento.put("nome", agendarMedicamento.getMedicamento().getNome());
            jsonTipoMedicamento.put("forma", agendarMedicamento.getMedicamento().getFormaFarmaceutica());
            jsonTipoMedicamento.put("descricao", agendarMedicamento.getMedicamento().getDescricao());

            Map<String, Object> jsonAgendarMedicamento = new HashMap<>();
            jsonAgendarMedicamento.put("codAgendarMedicamento", agendarMedicamento.getCodAgendarMedicamento());
            jsonAgendarMedicamento.put("animal", jsonAnimal);
            jsonAgendarMedicamento.put("medicamento", jsonTipoMedicamento);
            jsonAgendarMedicamento.put("dataAplicacao", agendarMedicamento.getDataAplicacao());
            jsonAgendarMedicamento.put("status", agendarMedicamento.getStatus());  // Adicionando o campo de status

            return jsonAgendarMedicamento;
        }
        return null;
    }



    public boolean onAlterar(Map<String, Object> json) {
        // Criando a conexão
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        if (validarAlterar(json)) {
            AgendarMedicamento agendarMedicamento = new AgendarMedicamento();
            AnimalDAO animalDAO = new AnimalDAO();
            TipoMedicamentoDAO tipoMedicamentoDAO = new TipoMedicamentoDAO();

            Animal animal = animalDAO.get((int) json.get("animal"), conexao);
            TipoMedicamento tipoMedicamento = tipoMedicamentoDAO.get((int) json.get("medicamento"), conexao);

            agendarMedicamento.setCodAgendarMedicamento((int) json.get("codAgendarMedicamento"));
            agendarMedicamento.setAnimal(animal);
            agendarMedicamento.setMedicamento(tipoMedicamento);
            agendarMedicamento.setDataAplicacao(json.get("dataAplicacao").toString());

            Boolean status =(Boolean) json.get("status");

            agendarMedicamento.setStatus(status);  // Atribuindo o status

            return agendarMedicamento.alterar(conexao);
        }
        return false;
    }


    private boolean validar(Map<String, Object> json) {
        //retorna verdade se todas as informações forem válidas
        if (json != null && json.containsKey("medicamento") && json.containsKey("animal") && json.containsKey("dataAplicacao")) {
            SingletonDB singletonDB = SingletonDB.getInstance();
            Conexao conexao = singletonDB.getConexao();

            // Verificar se os objetos existem no banco
            AnimalDAO animalDAO = new AnimalDAO();
            TipoMedicamentoDAO tipoMedicamentoDAO = new TipoMedicamentoDAO();

            Animal animal;
            TipoMedicamento tipoMedicamento;
            animal = animalDAO.get((int) json.get("animal"), conexao);
            tipoMedicamento = tipoMedicamentoDAO.get((int) json.get("medicamento"), conexao);
            if (tipoMedicamento != null && animal != null)
                return true;
        }

        return false;
    }

    private boolean validarAlterar(Map<String, Object> json) {
        //retorna verdade se todas as informações forem válidas
        return validar(json) && json.containsKey("codAgendarMedicamento");
    }
}
