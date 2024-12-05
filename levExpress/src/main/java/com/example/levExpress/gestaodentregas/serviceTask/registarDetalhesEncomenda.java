package com.example.levExpress.gestaodentregas.serviceTask;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class registarDetalhesEncomenda {

    Map<String, Object> detalhesEncomenda;
    Gson gson = new Gson(); //converter as strings JSON em objetos


    @JobWorker(type = "registar-detalhes-encomenda", fetchAllVariables = true)
    public void handle(final JobClient client, final ActivatedJob job) {
        try {
            Map<String, Object> detalhesEncomenda = job.getVariablesAsMap();
            exportarFicheiroJSON(detalhesEncomenda);
        }
        catch (Exception e){
            System.err.println("Erro registar-detalhes-encomenda -> " + e.getMessage());
            e.printStackTrace();
        }
    }



    public void exportarFicheiroJSON(Map<String, Object> detalhesEncomenda) throws IOException {

        // Criar o ObjectMapper para JSON
        ObjectMapper objectMapper = new ObjectMapper();

        // Criar o node principal para o JSON
        ObjectNode rootNode = objectMapper.createObjectNode();

        // Preencher os dados principais
        rootNode.put("origem", (String) detalhesEncomenda.get("origem"));
        rootNode.put("destino", (String) detalhesEncomenda.get("destino"));
        rootNode.put("peso", (String) detalhesEncomenda.get("peso"));
        rootNode.put("dataInicio", (String) detalhesEncomenda.get("dataInicio"));
        rootNode.put("duracao", (String) detalhesEncomenda.get("duracao"));
        rootNode.put("foto", (String) detalhesEncomenda.get("foto"));
        rootNode.put("descricao", (String) detalhesEncomenda.get("descricao"));

        // Criar o nó para dimensões
        ObjectNode dimensaoNode = objectMapper.createObjectNode();
        dimensaoNode.put("altura", (String) detalhesEncomenda.get("dimensaoAltura"));
        dimensaoNode.put("largura", (String) detalhesEncomenda.get("dimensaoLargura"));
        dimensaoNode.put("profundidade", (String) detalhesEncomenda.get("dimensaoProfundidade"));

        // Adicionar o nó de dimensões ao nó principal
        rootNode.set("dimensao", dimensaoNode);

        // Criar o JSON final
        ObjectNode finalJson = objectMapper.createObjectNode();
        finalJson.set("registar-detalhes-encomenda", rootNode);

        // Escrever o JSON no ficheiro
        File jsonFile = new File("registar-detalhes-encomenda.json");
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, finalJson);

        // Exibir mensagem de sucesso
        System.out.println("JSON salvo no ficheiro: " + jsonFile.getAbsolutePath());
    }
}
