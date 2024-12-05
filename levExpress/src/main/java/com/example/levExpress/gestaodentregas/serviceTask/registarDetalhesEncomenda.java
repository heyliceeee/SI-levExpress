package com.example.levExpress.gestaodentregas.serviceTask;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
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

        // Carregar o ficheiro JSON existente
        File jsonFile = new File("BD.json");
        ObjectNode rootNode = null;

        if (jsonFile.exists() && jsonFile.length() > 0) {
            rootNode = (ObjectNode) objectMapper.readTree(jsonFile);
        }

        // Criar a seção "registar-detalhes-encomenda"
        ObjectNode registarNode = objectMapper.createObjectNode();
        registarNode.put("origem", (String) detalhesEncomenda.get("origem"));
        registarNode.put("destino", (String) detalhesEncomenda.get("destino"));
        registarNode.put("peso", (String) detalhesEncomenda.get("peso"));
        registarNode.put("dataInicio", (String) detalhesEncomenda.get("dataInicio"));
        registarNode.put("duracao", (String) detalhesEncomenda.get("duracao"));
        registarNode.put("foto", (String) detalhesEncomenda.get("foto"));
        registarNode.put("descricao", (String) detalhesEncomenda.get("descricao"));
        registarNode.put("estado", (String) detalhesEncomenda.get("estado"));

        // Criar o node para dimensões
        ObjectNode dimensaoNode = objectMapper.createObjectNode();
        dimensaoNode.put("altura", (String) detalhesEncomenda.get("dimensaoAltura"));
        dimensaoNode.put("largura", (String) detalhesEncomenda.get("dimensaoLargura"));
        dimensaoNode.put("profundidade", (String) detalhesEncomenda.get("dimensaoProfundidade"));

        // Adicionar o node de dimensões ao node principal
        registarNode.set("dimensao", dimensaoNode);

        // Atualizar o node principal
        rootNode.set("registar-detalhes-encomenda", registarNode);

        // Salvar o JSON atualizado no ficheiro
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, rootNode);

        // Exibir mensagem de sucesso
        System.out.println("JSON salvo no ficheiro: " + jsonFile.getAbsolutePath());
    }
}
