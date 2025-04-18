package miau.auau.amigosdequatropatas.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import miau.auau.amigosdequatropatas.dao.AnimalDAO;
import miau.auau.amigosdequatropatas.dao.TipoMedicamentoDAO;

import miau.auau.amigosdequatropatas.entities.AgendarMedicamento;
import miau.auau.amigosdequatropatas.entities.Animal;
import miau.auau.amigosdequatropatas.entities.TipoMedicamento;

import miau.auau.amigosdequatropatas.util.Conexao;
import miau.auau.amigosdequatropatas.util.SingletonDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AgendarMedicamentoController {

    private static final Logger log = LoggerFactory.getLogger(AgendarMedicamentoController.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    public boolean onGravar(Map<String, Object> json) {
        System.out.println(json);
        if (validar(json)) {
            //criar aqui a conexão para o banco de dados
            SingletonDB singletonDB = SingletonDB.getInstance();
            Conexao conexao = singletonDB.getConexao();


            AgendarMedicamento agendarMedicamento = new AgendarMedicamento();

            AnimalDAO animalDAO = new AnimalDAO();
            Animal animal = animalDAO.get((int) json.get("animal"), conexao);

            TipoMedicamentoDAO tipoMedicamentoDAO = new TipoMedicamentoDAO();
            TipoMedicamento tipoMedicamento = tipoMedicamentoDAO.get((int) json.get("medicamento"), conexao);

            int intervalo = (int) json.get("intervalo");
            String formato = json.get("formato").toString();
            int periodo = (int) json.get("periodo");

            agendarMedicamento.setAnimal(animal);
            agendarMedicamento.setMedicamento(tipoMedicamento);
            agendarMedicamento.setIntervalo(intervalo);
            agendarMedicamento.setFormato(formato);
            agendarMedicamento.setPeriodo(periodo);

            if (agendarMedicamento.incluir(conexao)) {
                return true;
            }

            return false;
        }
        return false;
    }

    public List<Map<String, Object>> onBuscar(String filtro) {
        //criando a conexão
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        //criando a lista que conterá os JSON's
        AgendarMedicamento agendarMedicamento = new AgendarMedicamento();
        List<AgendarMedicamento> lista = agendarMedicamento.consultar(filtro, conexao);

        //verificação de a minha lista JSON está vazia ou não
        if (lista != null) {
            //crio uma lista json contendo os animais que retornaram no meu consultar
            List<Map<String, Object>> listaJson = new ArrayList<>();
            for (int i=0;i<lista.size();i++) {

                AgendarMedicamento am =lista.get(i);

                // dados animal
                Map<String, Object> jsonAnimal = new HashMap<>();
                jsonAnimal.put("codAnimal", am.getAnimal().getCodAnimal());
                jsonAnimal.put("nome", am.getAnimal().getNome());
                jsonAnimal.put("sexo", am.getAnimal().getSexo());
                jsonAnimal.put("raca", am.getAnimal().getRaca());
                jsonAnimal.put("idade", am.getAnimal().getIdade());
                jsonAnimal.put("peso", am.getAnimal().getPeso());
                jsonAnimal.put("castrado", am.getAnimal().getCastrado());
                jsonAnimal.put("adotado", am.getAnimal().getAdotado());
                jsonAnimal.put("imagemBase64", am.getAnimal().getImagemBase64());

                //dados medicamento
                Map<String, Object> jsonTipoMedicamento = new HashMap<>();
                jsonTipoMedicamento.put("codTipoMedicamento", am.getMedicamento().getCod());
                jsonTipoMedicamento.put("nome", am.getMedicamento().getNome());
                jsonTipoMedicamento.put("forma",am.getMedicamento().getFormaFarmaceutica());
                jsonTipoMedicamento.put("descricao",am.getMedicamento().getDescricao());

                Map<String, Object> jsonAgendarMedicamento = new HashMap<>();
                jsonAgendarMedicamento.put("codAgendarMedicamento", am.getCodAgendarMedicamento());
                jsonAgendarMedicamento.put("intervalo", am.getIntervalo());
                jsonAgendarMedicamento.put("formato", am.getFormato());
                jsonAgendarMedicamento.put("periodo", am.getPeriodo());
                jsonAgendarMedicamento.put("animal", jsonAnimal);
                jsonAgendarMedicamento.put("medicamento", jsonTipoMedicamento);

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


        // Consultando a agendamento pelo ID
        AgendarMedicamento agendarMedicamento = new AgendarMedicamento();
        agendarMedicamento = agendarMedicamento.consultarID(id, conexao);

        if (agendarMedicamento != null) {

            Map<String, Object> jsonAnimal = new HashMap<>();
            jsonAnimal.put("codAnimal", agendarMedicamento.getAnimal().getCodAnimal());
            jsonAnimal.put("nome", agendarMedicamento.getAnimal().getNome());
            jsonAnimal.put("sexo", agendarMedicamento.getAnimal().getSexo());
            jsonAnimal.put("raca", agendarMedicamento.getAnimal().getRaca());
            jsonAnimal.put("idade", agendarMedicamento.getAnimal().getIdade());
            jsonAnimal.put("peso", agendarMedicamento.getAnimal().getPeso());
            jsonAnimal.put("castrado", agendarMedicamento.getAnimal().getCastrado());
            jsonAnimal.put("adotado", agendarMedicamento.getAnimal().getAdotado());
            jsonAnimal.put("imagemBase64", agendarMedicamento.getAnimal().getImagemBase64());

            Map<String, Object> jsonTipoMedicamento = new HashMap<>();
            jsonTipoMedicamento.put("codTipoMedicamento", agendarMedicamento.getMedicamento().getCod());
            jsonTipoMedicamento.put("nome", agendarMedicamento.getMedicamento().getNome());
            jsonTipoMedicamento.put("forma",agendarMedicamento.getMedicamento().getFormaFarmaceutica());
            jsonTipoMedicamento.put("descricao",agendarMedicamento.getMedicamento().getDescricao());

            Map<String, Object> jsonAgendarMedicamento = new HashMap<>();
            jsonAgendarMedicamento.put("codAgendarMedicamento", agendarMedicamento.getCodAgendarMedicamento());
            jsonAgendarMedicamento.put("intervalo", agendarMedicamento.getIntervalo());
            jsonAgendarMedicamento.put("formato", agendarMedicamento.getFormato());
            jsonAgendarMedicamento.put("periodo", agendarMedicamento.getPeriodo());
            jsonAgendarMedicamento.put("animal", jsonAnimal);
            jsonAgendarMedicamento.put("medicamento", jsonTipoMedicamento);

            return jsonAgendarMedicamento;
        }
        // Retorna null se o agendamento não for encontrado
        return null;
    }


    public boolean onAlterar(Map<String, Object> json) {
        //criando a conexão
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
            agendarMedicamento.setIntervalo((int) json.get("intervalo"));
            agendarMedicamento.setFormato(json.get("formato").toString());
            agendarMedicamento.setPeriodo((int) json.get("periodo"));

            return agendarMedicamento.alterar(conexao);
        }
        return false;
    }

    private boolean validar(Map<String, Object> json) {
        //retorna verdade se todas as informações forem válidas
        if (json != null && json.containsKey("intervalo") && json.containsKey("medicamento") && json.containsKey("animal") && json.containsKey("formato") && json.containsKey("periodo"))
        {
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
