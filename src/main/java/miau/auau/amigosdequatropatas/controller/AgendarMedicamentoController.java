package miau.auau.amigosdequatropatas.controller;


import miau.auau.amigosdequatropatas.entities.AgendarMedicamento;
import miau.auau.amigosdequatropatas.entities.Animal;
import miau.auau.amigosdequatropatas.entities.TipoMedicamento;
import miau.auau.amigosdequatropatas.util.Conexao;
import miau.auau.amigosdequatropatas.util.SingletonDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AgendarMedicamentoController {

    @Autowired
    private AgendarMedicamento agendarMedicamento;


    public List<Map<String, Object>> onBuscarIdAnimal(int animalId) {
        // Criando a conexão
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        // Criando a lista que conterá os JSON's
        List<AgendarMedicamento> agendamentos = agendarMedicamento.consultarIdAnimal(animalId, conexao);

        //System.out.println(">>> Agendamentos retornados: " + agendamentos.size());

        if (agendamentos != null /*&& !agendamentos.isEmpty()*/) {
            List<Map<String, Object>> lista = new ArrayList<>();
            for (int i = 0; i < agendamentos.size(); i++) {
                Map<String, Object> json = new HashMap<>();

                json.put("codAgendarMedicamento", agendamentos.get(i).getCodAgendarMedicamento());

                Animal animal = new Animal();
                animal = animal.consultarID(agendamentos.get(i).getAnimal().getCodAnimal(), conexao);
                json.put("animal", animal);


                TipoMedicamento tipoMed = new TipoMedicamento();
                tipoMed = tipoMed.consultarID(agendamentos.get(i).getMedicamento().getCod(), conexao);
                json.put("medicamento",tipoMed);

                json.put("dataAplicacao", agendamentos.get(i).getDataAplicacao());
                json.put("status", agendamentos.get(i).getStatus());  // Adicionando o campo de status

                lista.add(json);
            }
            return lista;
        }
        return null;
    }

    public List<Map<String, Object>> onBuscar(String filtro) {
        // Criando a conexão
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        // Criando a lista que conterá os JSON's
        List<AgendarMedicamento> agendamentos = agendarMedicamento.consultar(filtro, conexao);

        //System.out.println(">>> Agendamentos retornados: " + agendamentos.size());

        if (agendamentos != null /*&& !agendamentos.isEmpty()*/) {
            List<Map<String, Object>> lista = new ArrayList<>();
            for (int i = 0; i < agendamentos.size(); i++) {
                Map<String, Object> json = new HashMap<>();

                json.put("codAgendarMedicamento", agendamentos.get(i).getCodAgendarMedicamento());

                Animal animal = new Animal();
                animal = animal.consultarID(agendamentos.get(i).getAnimal().getCodAnimal(), conexao);
                json.put("animal", animal);


                TipoMedicamento tipoMed = new TipoMedicamento();
                tipoMed = tipoMed.consultarID(agendamentos.get(i).getMedicamento().getCod(), conexao);
                json.put("medicamento",tipoMed);

                json.put("dataAplicacao", agendamentos.get(i).getDataAplicacao());
                json.put("status", agendamentos.get(i).getStatus());  // Adicionando o campo de status

                lista.add(json);
            }
            return lista;
        }
        return null;
    }


    public Map<String, Object> onBuscarId(int id) {
        // Criando a conexão
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        AgendarMedicamento agendarMed= agendarMedicamento.consultarID(id, conexao);
        if (agendarMed != null) {
            Map<String, Object> json = new HashMap<>();
            json.put("codAgendarMedicamento", agendarMed.getCodAgendarMedicamento());

            Animal animal = new Animal();
            animal = animal.consultarID(agendarMed.getAnimal().getCodAnimal(), conexao);
            json.put("animal", animal);

            TipoMedicamento tipoMed = new TipoMedicamento();
            tipoMed = tipoMed.consultarID(agendarMed.getMedicamento().getCod(), conexao);
            json.put("medicamento",tipoMed);


            json.put("dataAplicacao", agendarMed.getDataAplicacao());
            json.put("status", agendarMed.getStatus());

            return json;
        }
        return null;
    }


    public boolean onGravar(Map<String, Object> json) {
        // Criar a conexão para o banco de dados
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        //onde vou setar minhas informações seguindo as regras de negócios
        if (validar(json)) {

            Animal animal = new Animal();
            TipoMedicamento medicamento = new TipoMedicamento();

            animal = animal.consultarID((int) json.get("animal"),conexao);
            medicamento = medicamento.consultarID((int) json.get("medicamento"),conexao);

            agendarMedicamento.setAnimal(animal);
            agendarMedicamento.setMedicamento(medicamento);

            agendarMedicamento.setDataAplicacao(json.get("dataAplicacao").toString());
            agendarMedicamento.setStatus((Boolean) json.get("status"));

        }else
            return false;


        //se chegou até aqui está tudo certo
        if (agendarMedicamento.incluir(conexao))
            return true;
        return false;
    }


    public boolean onDelete(int id) {
        // Criando a conexão
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        //System.out.println("Tentando excluir agendamento com ID: " + id);
        AgendarMedicamento agendarMed = agendarMedicamento.consultarID(id, conexao);
        // Se o agendamento for encontrada, exclui; caso contrário, retorna false
        if (agendarMed != null) {
            //System.out.println("Agendamento encontrado: " + agendarMed.getCodAgendarMedicamento());
            return agendarMed.excluir(conexao);
        }
        return false;
    }





    public boolean onAlterar(Map<String, Object> json) {
        // Criando a conexão
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        if (validarAlterar(json)) {

            Animal animal = new Animal();
            TipoMedicamento medicamento = new TipoMedicamento();

            animal = animal.consultarID((int) json.get("animal"),conexao);
            medicamento = medicamento.consultarID((int) json.get("medicamento"),conexao);

            agendarMedicamento.setCodAgendarMedicamento(Integer.parseInt(json.get("codAgendarMedicamento").toString()));
            //pega só cod do animal  e medicamento no json
            agendarMedicamento.setAnimal(animal);
            agendarMedicamento.setMedicamento(medicamento);

            agendarMedicamento.setDataAplicacao(json.get("dataAplicacao").toString());
            agendarMedicamento.setStatus((Boolean) json.get("status"));

            return agendarMedicamento.alterar(conexao);
        }
        return false;
    }


    private boolean validar(Map<String, Object> json) {
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        //instanciar modelo de animal e tipoMedicamento
        Animal animal = new Animal();
        TipoMedicamento tipoMedicamento = new TipoMedicamento();

        //nesse return eu realizo 3 consultas para saber se de fato todos os codigos sao validos
        return tipoMedicamento.consultarID(Integer.parseInt(json.get("medicamento").toString()),conexao) != null &&
                 animal.consultarID(Integer.parseInt(json.get("animal").toString()),conexao) != null;

    }

    private boolean validarAlterar(Map<String, Object> json) {
        //retorna verdade se todas as informações forem válidas
        return validar(json) && json.containsKey("codAgendarMedicamento");
    }
}
