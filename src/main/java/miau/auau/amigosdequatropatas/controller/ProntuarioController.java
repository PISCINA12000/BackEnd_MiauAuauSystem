package miau.auau.amigosdequatropatas.controller;


import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import miau.auau.amigosdequatropatas.entities.*;
import miau.auau.amigosdequatropatas.util.Conexao;
import miau.auau.amigosdequatropatas.util.SingletonDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

@Component
public class ProntuarioController {

    @Autowired
    private Prontuario prontuario;

    //filtrar registro pelo tipo do registro ou nao e ordena pela data com filtro ou n
    public List<Map<String, Object>> onBuscar(String filtro) {
        //criando a conexão
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        List<Prontuario> prontuarios = prontuario.consultar(filtro, conexao);
        if (prontuarios != null && !prontuarios.isEmpty()) {
            List<Map<String, Object>> lista = new ArrayList<>();
            for (int i = 0; i < prontuarios.size(); i++) {
                Map<String, Object> json = new HashMap<>();
                json.put("cod", prontuarios.get(i).getCod());


                Animal animal = new Animal();
                animal = animal.consultarID(prontuarios.get(i).getCodAnimal(), conexao);
                json.put("animal", animal);


                json.put("data", prontuarios.get(i).getData());


                json.put("tipoRegistro", prontuarios.get(i).getTipoRegistro());
                json.put("observacao", prontuarios.get(i).getObservacao());
                json.put("arquivo", prontuarios.get(i).getPDF());

                lista.add(json);
            }
            return lista;
        }
        return null;
    }

    //retorna registros de um periodo
    public List<Map<String, Object>> onBuscarData(String dataIni, String dataFim) {
        //criando a conexão
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        List<Prontuario> prontuarios = prontuario.consultarPorData(dataIni, dataFim, conexao);
        if (prontuarios != null && !prontuarios.isEmpty()) {
            List<Map<String, Object>> lista = new ArrayList<>();
            for (int i = 0; i < prontuarios.size(); i++) {
                Map<String, Object> json = new HashMap<>();
                json.put("cod", prontuarios.get(i).getCod());


                Animal animal = new Animal();
                animal = animal.consultarID(prontuarios.get(i).getCodAnimal(), conexao);
                json.put("animal", animal);


                json.put("data", prontuarios.get(i).getData());


                json.put("tipoRegistro", prontuarios.get(i).getTipoRegistro());
                json.put("observacao", prontuarios.get(i).getObservacao());
                json.put("arquivo", prontuarios.get(i).getPDF());

                lista.add(json);
            }
            return lista;
        }
        return null;
    }

    //retorna todos os registros de um animal
    public List<Map<String, Object>> onBuscarPorIdAnimal(int id) {
        //criando a conexão
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        List<Prontuario> prontuarios = prontuario.consultarPorIdAnimal(id, conexao);

        if (prontuarios != null /*&& !prontuarios.isEmpty()*/) {
            List<Map<String, Object>> lista = new ArrayList<>();
            for (int i = 0; i < prontuarios.size(); i++) {
                Map<String, Object> json = new HashMap<>();
                json.put("cod", prontuarios.get(i).getCod());


                Animal animal = new Animal();
                animal = animal.consultarID(prontuarios.get(i).getCodAnimal(), conexao);
                json.put("animal", animal);


                json.put("data", prontuarios.get(i).getData());


                json.put("tipoRegistro", prontuarios.get(i).getTipoRegistro());
                json.put("observacao", prontuarios.get(i).getObservacao());
                json.put("arquivo", prontuarios.get(i).getPDF());

                lista.add(json);
            }
            return lista;
        }
        return null;

    }

    public Map<String, Object> onBuscarId(int id) {
        //criando a conexão
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        Prontuario pron = prontuario.consultarID(id, conexao);
        if (pron != null) {
            Map<String, Object> json = new HashMap<>();
            json.put("cod", pron.getCod());

            //tratar animal
            Animal animal = new Animal();
            animal = animal.consultarID(pron.getCodAnimal(), conexao);
            json.put("animal", animal);

            json.put("data", pron.getData());

            json.put("tipoRegistro", pron.getTipoRegistro());
            json.put("observacao", pron.getObservacao());
            json.put("arquivo", pron.getPDF());

            return json;
        }
        return null;
    }


    public byte[] onBuscarPDF(int id) {
        //criando a conexão
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        Prontuario pron = prontuario.consultarID(id, conexao);
        if (pron != null) {
            return pron.getPDF();
        }
        return null;
    }


    public boolean onGravar(Map<String, Object> json) {
        //criando a conexão
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        //onde vou setar minhas informações seguindo as regras de negócios
        if (validar(json)) {
            //prontuario.setCod(Integer.parseInt(json.get("cod").toString()));

            prontuario.setCodAnimal(Integer.parseInt(json.get("codAnimal").toString()));

            prontuario.setData(json.get("data").toString());
            prontuario.setTipoRegistro(json.get("tipoRegistro").toString());
            prontuario.setObservacao(json.get("observacao").toString());

            prontuario.setPDF((byte[]) json.get("arquivo"));
        } else
            return false;

        //se chegou até aqui está tudo certo
        if (prontuario.incluir(conexao))
            return true;
        return false;
    }

    public boolean onDelete(int id) {
        //criando a conexão
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        Prontuario pron = prontuario.consultarID(id, conexao);
        if (pron != null)
            return pron.excluir(conexao);
        return false;
    }

    public boolean onAtualizar(Map<String, Object> json) {
        //criando a conexão
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        //onde vou setar minhas informações seguindo as regras de negócios
        if (validarAtualizar(json)) {
            prontuario.setCod(Integer.parseInt(json.get("cod").toString()));

            prontuario.setCodAnimal(Integer.parseInt(json.get("codAnimal").toString()));

            prontuario.setData(json.get("data").toString());
            prontuario.setTipoRegistro(json.get("tipoRegistro").toString());
            prontuario.setObservacao(json.get("observacao").toString());

            prontuario.setPDF((byte[]) json.get("arquivo"));
        } else
            return false;

        //se chegou até aqui está tudo certo
        if (prontuario.alterar(conexao))
            return true;
        return false;
    }

    public boolean validar(Map<String, Object> json) {
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        //instanciar modelo Animal
        Animal animal = new Animal();


        //nesse return eu realizo 4 consultas para saber se de fato todos os códigos são válidos
        return animal.consultarID(Integer.parseInt(json.get("codAnimal").toString()), conexao) != null &&
                !json.get("data").toString().isEmpty() &&
                !json.get("tipoRegistro").toString().isEmpty() &&
                !json.get("observacao").toString().isEmpty();
    }

    public boolean validarAtualizar(Map<String, Object> json) {
        return Integer.parseInt(json.get("cod").toString()) > 0 && validar(json);
    }


    public byte[] onGerarPdf(Map<String, Object> json) throws IOException {
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        Animal animal = new Animal().consultarID(Integer.parseInt(json.get("codAnimal").toString()), conexao);

        if (animal == null)
            return null;
        else{
            int idAnimal = animal.getCodAnimal();

            // Consulta os dados
            List<AgendarMedicamento> agendamentos = new AgendarMedicamento().consultarIdAnimal(idAnimal, conexao);
            List<Prontuario> registros = new Prontuario().consultarPorIdAnimal(idAnimal, conexao);
            List<Lancamento> lancamentos = new Lancamento().consultarAnimal(idAnimal, conexao);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document doc = new Document(pdfDoc);

            doc.add(new Paragraph("PRONTUÁRIO DO ANIMAL").setBold().setFontSize(18).setTextAlignment(TextAlignment.CENTER));
            doc.add(new Paragraph("\n"));

            // Se desejar adicionar a imagem do animal (base64)
            if (animal.getImagemBase64() != null && !animal.getImagemBase64().isEmpty()) {
                byte[] imageBytes = Base64.getDecoder().decode(animal.getImagemBase64());
                ImageData imageData = ImageDataFactory.create(imageBytes);
                Image image = new Image(imageData).scaleToFit(120, 120);
                doc.add(image);
                doc.add(new Paragraph("\n"));
            }

            String dataFormatada;

            Table animalInfoTable = new Table(UnitValue.createPercentArray(new float[]{2, 4, 2, 4})).useAllAvailableWidth();

            animalInfoTable.addCell(new Cell().add(new Paragraph("Nome:").setBold()).setBorder(Border.NO_BORDER));
            animalInfoTable.addCell(new Cell().add(new Paragraph(animal.getNome())).setBorder(Border.NO_BORDER));
            animalInfoTable.addCell(new Cell().add(new Paragraph("Raça:").setBold()).setBorder(Border.NO_BORDER));
            animalInfoTable.addCell(new Cell().add(new Paragraph(animal.getRaca())).setBorder(Border.NO_BORDER));

            animalInfoTable.addCell(new Cell().add(new Paragraph("Nascimento:").setBold()).setBorder(Border.NO_BORDER));
            dataFormatada = LocalDate.parse(animal.getDataNascimento()).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            animalInfoTable.addCell(new Cell().add(new Paragraph(dataFormatada)).setBorder(Border.NO_BORDER));
            animalInfoTable.addCell(new Cell().add(new Paragraph("Sexo:").setBold()).setBorder(Border.NO_BORDER));
            animalInfoTable.addCell(new Cell().add(new Paragraph(animal.getSexo())).setBorder(Border.NO_BORDER));

            animalInfoTable.addCell(new Cell().add(new Paragraph("Peso:").setBold()).setBorder(Border.NO_BORDER));
            animalInfoTable.addCell(new Cell().add(new Paragraph(animal.getPeso() + " kg")).setBorder(Border.NO_BORDER));
            animalInfoTable.addCell(new Cell().add(new Paragraph("Cor:").setBold()).setBorder(Border.NO_BORDER));
            animalInfoTable.addCell(new Cell().add(new Paragraph(animal.getCor())).setBorder(Border.NO_BORDER));

            animalInfoTable.addCell(new Cell().add(new Paragraph("Espécie:").setBold()).setBorder(Border.NO_BORDER));
            animalInfoTable.addCell(new Cell().add(new Paragraph(animal.getEspecie())).setBorder(Border.NO_BORDER));
            animalInfoTable.addCell(new Cell().add(new Paragraph("Castrado:").setBold()).setBorder(Border.NO_BORDER));
            animalInfoTable.addCell(new Cell().add(new Paragraph(animal.getCastrado())).setBorder(Border.NO_BORDER));

            animalInfoTable.addCell(new Cell().add(new Paragraph("Adotado:").setBold()).setBorder(Border.NO_BORDER));
            animalInfoTable.addCell(new Cell().add(new Paragraph(animal.getAdotado())).setBorder(Border.NO_BORDER));

            // Adiciona ao documento
            doc.add(animalInfoTable);
            doc.add(new Paragraph("\n"));


            // Agendamentos
            if (!agendamentos.isEmpty()) {
                doc.add(new Paragraph("Agendamentos de Medicamentos").setBold().setFontSize(14));
                Table table = new Table(new float[]{5, 3}).setWidth(UnitValue.createPercentValue(100));
                table.addHeaderCell("Nome do Medicamento");
                table.addHeaderCell("Data de Aplicação");

                for (AgendarMedicamento ag : agendamentos) {
                    table.addCell(ag.getMedicamento().getNome());

                    dataFormatada = LocalDate.parse(ag.getDataAplicacao()).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    table.addCell(dataFormatada);
                }
                doc.add(table);
                doc.add(new Paragraph("\n"));
            }

            // Lançamentos
            if (!lancamentos.isEmpty()) {
                doc.add(new Paragraph("Lançamentos").setBold().setFontSize(14));
                Table table = new Table(new float[]{3, 6, 3}).setWidth(UnitValue.createPercentValue(100));
                table.addHeaderCell("Data");
                table.addHeaderCell("Descrição");
                table.addHeaderCell("Valor");

                for (Lancamento lan : lancamentos) {

                    dataFormatada = LocalDate.parse(lan.getData()).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    table.addCell(dataFormatada);

                    table.addCell(lan.getDescricao());
                    table.addCell(String.format("R$ %.2f", lan.getValor()));
                }
                doc.add(table);
                doc.add(new Paragraph("\n"));
            }

            // Registros de prontuário
            if (!registros.isEmpty()) {
                doc.add(new Paragraph("Registros do Prontuário").setBold().setFontSize(14));

                for (Prontuario reg : registros) {
                    dataFormatada = LocalDate.parse(reg.getData()).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                    // Criar conteúdo do registro como Paragraphs separados
                    Paragraph data = new Paragraph()
                            .add(new Text("Data: ").setBold())
                            .add(dataFormatada);

                    Paragraph tipo = new Paragraph()
                            .add(new Text("Tipo: ").setBold())
                            .add(reg.getTipoRegistro());

                    Paragraph observacao = new Paragraph()
                            .add(new Text("Observação: ").setBold())
                            .add(reg.getObservacao());

                    // Tabela com 1 coluna e 1 célula para conter todos os parágrafos
                    Table caixa = new Table(1)
                            .setWidth(UnitValue.createPercentValue(100))
                            .setMarginBottom(10); // Espaço entre blocos

                    Cell cell = new Cell()
                            .add(data)
                            .add(tipo)
                            .add(observacao)
                            .setBorder(new SolidBorder(ColorConstants.BLACK, 1))
                            .setPadding(5); // Espaço interno

                    caixa.addCell(cell);
                    doc.add(caixa);
                }
            }

            doc.close();
            return baos.toByteArray();
        }
    }
}
