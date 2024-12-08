package com.example.levExpress.gestaodentregas.scriptTask;

import com.example.levExpress.classes.Cliente;
import com.example.levExpress.classes.Encomenda;
import com.example.levExpress.classes.Entregador;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class arquivarProcesso {

    ObjectMapper objectMapper = new ObjectMapper(); // Criar ObjectMapper para manipular o JSON

    File jsonFile = new File("D:\\githubProjects\\SI-levExpress\\levExpress\\src\\main\\java\\com\\example\\levExpress\\gestaodentregas\\BD.json"); // Caminho do ficheiro JSON

    Gson gson = new Gson();

    @JobWorker(type = "arquivar-processo", fetchAllVariables = true)
    public void handle(final JobClient client, final ActivatedJob job) {
        try {
            Map<String, Object> input = job.getVariablesAsMap();
            exportarFicheiroJSON(input);
        }
        catch (Exception e){
            System.err.println("Erro arquivar-processo -> " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void exportarFicheiroJSON(Map<String, Object> input) throws IOException {
        // Criar o ObjectMapper para JSON
        ObjectMapper objectMapper = new ObjectMapper();


        //encomenda
        Encomenda encomenda = lerEncomendaJSON();
        ObjectNode rootNode = null;

        if (jsonFile.exists() && jsonFile.length() > 0) {
            rootNode = (ObjectNode) objectMapper.readTree(jsonFile);
        }

        ObjectNode registarNode = objectMapper.createObjectNode();
        registarNode.put("origem", encomenda.getOrigem());
        registarNode.put("destino", encomenda.getDestino());
        registarNode.put("peso", encomenda.getPeso());
        registarNode.put("dataInicio", encomenda.getDataInicio());
        registarNode.put("duracao", encomenda.getDuracao());
        registarNode.put("foto", encomenda.getFoto());
        registarNode.put("descricao", encomenda.getDescricao());
        registarNode.put("estado", encomenda.getEstado());

        ObjectNode dimensaoNode = objectMapper.createObjectNode();
        dimensaoNode.put("altura", encomenda.getDimensao().getAltura());
        dimensaoNode.put("largura", encomenda.getDimensao().getLargura());
        dimensaoNode.put("profundidade", encomenda.getDimensao().getProfundidade());

        registarNode.set("dimensao", dimensaoNode);

        rootNode.set("proposta", registarNode);
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("Processo.json"), rootNode);


        //cliente
        Cliente cliente = lerClienteJSON();
        rootNode = null;

        if (jsonFile.exists() && jsonFile.length() > 0) {
            rootNode = (ObjectNode) objectMapper.readTree(jsonFile);
        }

        registarNode = objectMapper.createObjectNode();
        registarNode.put("telemovel", cliente.getTelemovel());
        registarNode.put("morada", cliente.getMorada());
        registarNode.put("cp", cliente.getCp());
        registarNode.put("cidade", cliente.getCidade());
        registarNode.put("mediaAvaliacao", cliente.getMediaAvaliacao());
        registarNode.put("estado", cliente.getEstado());
        registarNode.put("id", cliente.getId());
        registarNode.put("nome", cliente.getNome());
        registarNode.put("email", cliente.getEmail());
        registarNode.put("foto", cliente.getFoto());

        rootNode.set("cliente", registarNode);
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, rootNode);


        //entregador
        List<Entregador> entregadores = lerEntregadoresJSON();

        if (jsonFile.exists() && jsonFile.length() > 0) {
            rootNode = (ObjectNode) objectMapper.readTree(jsonFile);
        }

        registarNode = objectMapper.createObjectNode();
        registarNode.put("nome", entregadores.get(0).getNome());
        registarNode.put("email", entregadores.get(0).getEmail());
        registarNode.put("cidade", entregadores.get(0).getCidade());
        registarNode.put("foto", entregadores.get(0).getFoto());
        registarNode.put("estrelas", (int) entregadores.get(0).getEstrelas());

        rootNode.set("entregador", registarNode);
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, rootNode);


        //proposta
        rootNode = null;

        if (jsonFile.exists() && jsonFile.length() > 0) {
            rootNode = (ObjectNode) objectMapper.readTree(jsonFile);
        }

        registarNode = objectMapper.createObjectNode();
        registarNode.put("valor", (String) input.get("valor"));
        registarNode.put("veiculo", (String) input.get("veiculo"));

        rootNode.set("proposta", registarNode);
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, rootNode);


        //feedback
        rootNode = null;

        if (jsonFile.exists() && jsonFile.length() > 0) {
            rootNode = (ObjectNode) objectMapper.readTree(jsonFile);
        }

        registarNode = objectMapper.createObjectNode();
        registarNode.put("estrela", (int) input.get("estrela"));
        registarNode.put("comentario", (String) input.get("comentario"));
        registarNode.put("foto", (String) input.get("foto"));

        rootNode.set("feedback", registarNode);
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, rootNode);
    }


    private List<Entregador> lerEntregadoresJSON() throws IOException {

        try {
            // Ler o conteúdo de "notificar-entregadores-disponiveis"
            JsonNode rootNode = objectMapper.readTree(jsonFile);
            JsonNode entregadoresNode = rootNode.path("notificar-entregadores-disponiveis");

            // Mapear para uma lista de objetos Entregador
            return objectMapper.readValue(entregadoresNode.traverse(), new TypeReference<List<Entregador>>() {});

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Cliente lerClienteJSON() throws IOException {

        try {
            // Ler o conteúdo de "notificar-cliente" e mapear para a classe
            Cliente detalhesCliente = objectMapper.readTree(jsonFile)
                    .path("notificar-cliente")
                    .traverse(objectMapper)
                    .readValueAs(Cliente.class);

            return detalhesCliente;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Encomenda lerEncomendaJSON() throws IOException {

        try {
            // Ler o conteúdo de "registar-detalhes-encomenda" e mapear para a classe
            Encomenda detalhesEncomenda = objectMapper.readTree(jsonFile)
                    .path("registar-detalhes-encomenda")
                    .traverse(objectMapper)
                    .readValueAs(Encomenda.class);

            return  detalhesEncomenda;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
