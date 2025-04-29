package miau.auau.amigosdequatropatas.controller;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import miau.auau.amigosdequatropatas.dao.AnimalDAO;
import miau.auau.amigosdequatropatas.dao.UsuarioDAO;
import miau.auau.amigosdequatropatas.entities.Adocao;
import miau.auau.amigosdequatropatas.entities.Animal;
import miau.auau.amigosdequatropatas.entities.Usuario;
import miau.auau.amigosdequatropatas.util.Conexao;
import miau.auau.amigosdequatropatas.util.SingletonDB;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class AdocaoController {

    public boolean onGravar(Map<String, Object> json) {
        System.out.println(json);
        if (validar(json)) {
            //criar aqui a conexão para o banco de dados
            SingletonDB singletonDB = SingletonDB.getInstance();
            Conexao conexao = singletonDB.getConexao();

            Adocao adocao = new Adocao();
            AnimalDAO animalDAO = new AnimalDAO();
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            Animal animal;
            Usuario usuario;

            animal = animalDAO.get((int) json.get("animal"), conexao);
            usuario = usuarioDAO.get((int) json.get("usuario"), conexao);
            String data = json.get("data").toString();
            String status = json.get("status").toString();

            adocao.setData(data);
            adocao.setStatus(status);
            adocao.setAnimal(animal);
            adocao.setUsuario(usuario);

            if (adocao.incluir(conexao))
            {

                animal.setAdotado("Sim");
                return animal.alterar(conexao);
                // commit; finalizar transação e desconectar

            }

            //se chegou até aqui é porque algo deu errado
            // rollback; finalizar t. desconecta;
            return false;
        }
        return false;
    }

    public List<Map<String, Object>> onBuscar(String filtro) {
        //criando a conexão
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        //criando a lista que conterá os JSON's
        Adocao adocao = new Adocao();
        List<Adocao> lista = adocao.consultar(filtro, conexao);

        //verificação de a minha lista JSON está vazia ou não
        if (lista!=null) {
            //crio uma lista json contendo os animais que retornaram no meu consultar
            List<Map<String, Object>> listaJson = new ArrayList<>();
            for (int i=0; i<lista.size(); i++) {

                Adocao a = lista.get(i);
                // dados animal
                Map<String, Object> jsonAnimal = new HashMap<>();
                jsonAnimal.put("codAnimal", a.getAnimal().getCodAnimal());
                jsonAnimal.put("nome", a.getAnimal().getNome());
                jsonAnimal.put("sexo", a.getAnimal().getSexo());
                jsonAnimal.put("raca", a.getAnimal().getRaca());
                jsonAnimal.put("dataNascimento", a.getAnimal().getDataNascimento());
                jsonAnimal.put("peso", a.getAnimal().getPeso());
                jsonAnimal.put("castrado", a.getAnimal().getCastrado());
                jsonAnimal.put("adotado", a.getAnimal().getAdotado());
                jsonAnimal.put("especie", a.getAnimal().getEspecie());
                jsonAnimal.put("cor", a.getAnimal().getCor());
                jsonAnimal.put("imagemBase64", a.getAnimal().getImagemBase64());

                Map<String, Object> jsonUsuario = new HashMap<>();
                jsonUsuario.put("codUsuario", a.getUsuario().getCod());
                jsonUsuario.put("nome", a.getUsuario().getNome());
                jsonUsuario.put("email", a.getUsuario().getEmail());
                jsonUsuario.put("telefone", a.getUsuario().getTelefone());
                jsonUsuario.put("cpf", a.getUsuario().getCpf());
                jsonUsuario.put("sexo", a.getUsuario().getSexo());
                jsonUsuario.put("privilegio", a.getUsuario().getPrivilegio());
                jsonUsuario.put("cep", a.getUsuario().getCep());
                jsonUsuario.put("bairro", a.getUsuario().getBairro());
                jsonUsuario.put("numero", a.getUsuario().getNumero());
                jsonUsuario.put("rua", a.getUsuario().getRua());
                jsonUsuario.put("cidade", a.getUsuario().getCidade());
                jsonUsuario.put("estado", a.getUsuario().getEstado());

                Map<String, Object> jsonAdocao = new HashMap<>();
                jsonAdocao.put("codAdocao", a.getCodAdocao());
                jsonAdocao.put("data", a.getData());
                jsonAdocao.put("status", a.getStatus());
                jsonAdocao.put("animal", jsonAnimal);
                jsonAdocao.put("usuario", jsonUsuario);
                listaJson.add(jsonAdocao);
            }
            return listaJson;
        }
        else
            return null;
    }

    public boolean onDelete(int id) {
        // Criando a conexão
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        // Consultando a adocao pelo ID
        Adocao adocao = new Adocao();
        adocao = adocao.consultarID(id, conexao);
        // Se a adocao for encontrada, exclui; caso contrário, retorna false
        if (adocao != null)
        {
            Animal animal = adocao.getAnimal();
            animal.setAdotado("Não");
            if(adocao.excluir(conexao))
            {
                return animal.alterar(conexao);
            }

        }
        return false;
    }


    public Map<String, Object> onBuscarId(int id) {
        // Criando a conexão
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        // Consultando a adocao pelo ID
        Adocao adocao = new Adocao();
        adocao = adocao.consultarID(id, conexao);

        // Retornando os dados da adocao, se encontrado
        if (adocao != null) {

            Map<String, Object> jsonAnimal = new HashMap<>();
            jsonAnimal.put("codAnimal", adocao.getAnimal().getCodAnimal());
            jsonAnimal.put("nome", adocao.getAnimal().getNome());
            jsonAnimal.put("sexo", adocao.getAnimal().getSexo());
            jsonAnimal.put("raca", adocao.getAnimal().getRaca());
            jsonAnimal.put("dataNascimento", adocao.getAnimal().getDataNascimento());
            jsonAnimal.put("peso", adocao.getAnimal().getPeso());
            jsonAnimal.put("castrado", adocao.getAnimal().getCastrado());
            jsonAnimal.put("adotado", adocao.getAnimal().getAdotado());
            jsonAnimal.put("especie", adocao.getAnimal().getEspecie());
            jsonAnimal.put("cor", adocao.getAnimal().getCor());
            jsonAnimal.put("imagemBase64", adocao.getAnimal().getImagemBase64());

            Map<String, Object> jsonUsuario = new HashMap<>();
            jsonUsuario.put("codUsuario", adocao.getUsuario().getCod());
            jsonUsuario.put("nome", adocao.getUsuario().getNome());
            jsonUsuario.put("email", adocao.getUsuario().getEmail());
            jsonUsuario.put("telefone", adocao.getUsuario().getTelefone());
            jsonUsuario.put("cpf", adocao.getUsuario().getCpf());
            jsonUsuario.put("sexo", adocao.getUsuario().getSexo());
            jsonUsuario.put("privilegio", adocao.getUsuario().getPrivilegio());
            jsonUsuario.put("cep", adocao.getUsuario().getCep());
            jsonUsuario.put("bairro", adocao.getUsuario().getBairro());
            jsonUsuario.put("numero", adocao.getUsuario().getNumero());
            jsonUsuario.put("rua", adocao.getUsuario().getRua());
            jsonUsuario.put("cidade", adocao.getUsuario().getCidade());
            jsonUsuario.put("estado", adocao.getUsuario().getEstado());

            Map<String, Object> jsonAdocao = new HashMap<>();
            jsonAdocao.put("codAdocao", adocao.getCodAdocao());
            jsonAdocao.put("data", adocao.getData());
            jsonAdocao.put("status", adocao.getStatus());
            jsonAdocao.put("animal", jsonAnimal);
            jsonAdocao.put("usuario", jsonUsuario);

            return jsonAdocao;
        }

        // Retorna null se a adocao não for encontrado
        return null;
    }
    public boolean onAlterar(Map<String, Object> json) {
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        if (validarAlterar(json))
        {
            AnimalDAO animalDAO = new AnimalDAO();
            UsuarioDAO usuarioDAO = new UsuarioDAO();

            Adocao adocaoNovo = new Adocao();
            Adocao adocao = new Adocao();

            Adocao adocaoAntigo;
            Animal animalAntigo = new Animal();
            Animal animalNovo;
            Usuario usuario;

            // Recupera os dados antigos e novos
            adocaoAntigo = adocao.consultarID((int) json.get("codAdocao"), conexao);
            animalNovo = animalDAO.get((int) json.get("animal"), conexao);
            usuario = usuarioDAO.get((int) json.get("usuario"), conexao);

            // Preenche o novo objeto de adoção
            adocaoNovo.setCodAdocao((int) json.get("codAdocao"));
            adocaoNovo.setUsuario(usuario);
            adocaoNovo.setAnimal(animalNovo);
            adocaoNovo.setData(json.get("data").toString());
            adocaoNovo.setStatus(json.get("status").toString());

            if (adocaoNovo.alterar(conexao))
            {
                // Se o animal foi trocado na alteração
                if (adocaoAntigo.getAnimal().getCodAnimal() != animalNovo.getCodAnimal())
                {
                    animalAntigo = animalAntigo.consultarID(adocaoAntigo.getAnimal().getCodAnimal(), conexao);
                    animalAntigo.setAdotado("Não");
                    animalAntigo.alterar(conexao);

                    animalNovo.setAdotado("Sim");
                    animalNovo.alterar(conexao);
                }
                return true;
            }
        }

        return false;
    }

    public boolean validar(Map<String, Object> json) {
        //retorna verdade se todas as informações forem válidas
        if (json != null && json.containsKey("data") && json.containsKey("usuario") && json.containsKey("animal"))
        {
            SingletonDB singletonDB = SingletonDB.getInstance();
            Conexao conexao = singletonDB.getConexao();

            // Verificar se os objetos existem no banco
            AnimalDAO animalDAO = new AnimalDAO();
            UsuarioDAO usuarioDAO = new UsuarioDAO();

            Animal animal;
            Usuario usuario;
            animal = animalDAO.get((int) json.get("animal"), conexao);
            usuario = usuarioDAO.get((int) json.get("usuario"), conexao);
            if (usuario != null && animal != null && animal.getAdotado().equals("Não"))
                return true;
        }

        return false;
    }
    public boolean validarAlterar(Map<String, Object> json)
    {

        if (json != null && json.containsKey("data") && json.containsKey("usuario") && json.containsKey("animal") && json.containsKey("codAdocao"))
        {

            SingletonDB singletonDB = SingletonDB.getInstance();
            Conexao conexao = singletonDB.getConexao();
            Adocao adocao = new Adocao().consultarID((int) json.get("codAdocao"), conexao);

            if (adocao != null)
            {
                AnimalDAO animalDAO = new AnimalDAO();
                UsuarioDAO usuarioDAO = new UsuarioDAO();

                Animal animal = animalDAO.get((int) json.get("animal"), conexao);
                Usuario usuario = usuarioDAO.get((int) json.get("usuario"), conexao);
                if (usuario != null && animal != null)
                    return true;

            }
        }

        return false;
    }

    public byte[] onGerarPdf(Map<String, Object> json) throws IOException
    {
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();
        Adocao adocao = new Adocao();

        adocao = adocao.consultarID((int) json.get("codAdocao"), conexao);
        InputStream template = getClass().getResourceAsStream("/termo_de_adocao.pdf");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (adocao != null)
        {
            PdfReader reader = new PdfReader(template);
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdfDoc = new PdfDocument(reader, writer);
            PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);

            LocalDate dataAtual = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dataFormatada = dataAtual.format(formatter);

            LocalDate dataNascimento = LocalDate.parse(adocao.getAnimal().getDataNascimento());
            String dataNascimentoFormatada = dataNascimento.format(formatter);

            form.getField("NOME").setValue(adocao.getUsuario().getNome());
            form.getField("CPF").setValue(adocao.getUsuario().getCpf());
            form.getField("ENDERECO").setValue(adocao.getUsuario().getRua());
            form.getField("NUMERO").setValue(adocao.getUsuario().getNumero());
            form.getField("BAIRRO").setValue(adocao.getUsuario().getBairro());
            form.getField("CIDADE").setValue(adocao.getUsuario().getCidade()); // ainda não implementado em usuario
            form.getField("ESTADO").setValue(adocao.getUsuario().getEstado()); // ainda não implementando em usuario
            form.getField("CEP").setValue(adocao.getUsuario().getCep());
            form.getField("TELEFONE").setValue(adocao.getUsuario().getTelefone());
            form.getField("EMAIL").setValue(adocao.getUsuario().getEmail());
            form.getField("DATA").setValue(dataFormatada);
            form.getField("RACA").setValue(adocao.getAnimal().getRaca());
            form.getField("DATANASC").setValue(dataNascimentoFormatada);
            form.getField("SEXO").setValue(adocao.getAnimal().getSexo());
            form.getField("ESPECIE").setValue(adocao.getAnimal().getEspecie());
            form.getField("COR").setValue(adocao.getAnimal().getCor());
            form.getField("ID").setValue(""+adocao.getAnimal().getCodAnimal());

            form.flattenFields(); // torna somente leitura
            pdfDoc.close();

        }
        return baos.toByteArray();
    }
}
