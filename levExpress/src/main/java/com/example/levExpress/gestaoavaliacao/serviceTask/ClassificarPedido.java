package com.example.levExpress.gestaoavaliacao.serviceTask;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Component
public class ClassificarPedido {

    @JobWorker(type = "classificar-pedido", fetchAllVariables = true)
    public void handle(final JobClient client, final ActivatedJob job) {
        try {
            Map<String, Object> detalhesPedido = job.getVariablesAsMap();
            atualizarClassificacao(detalhesPedido);
        } catch (Exception e) {
            System.err.println("Erro classificar-pedido -> " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void atualizarClassificacao(Map<String, Object> detalhesPedido) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File jsonFile = new File("D:\\githubProjects\\SI-levExpress\\levExpress\\src\\main\\java\\com\\example\\levExpress\\gestaoavaliacao\\pedidosApoio.json");
        ObjectNode rootNode = (ObjectNode) objectMapper.readTree(jsonFile);

        // Obter o pedido existente
        ObjectNode pedidoNode = (ObjectNode) rootNode.get("pedido-apoio");

        // Atualizar classificação e status
        pedidoNode.put("classificacao", (String) detalhesPedido.get("classificacao"));
        pedidoNode.put("status", "classificado");

        // Atualizar o arquivo JSON
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, rootNode);
        System.out.println("Pedido classificado: " + jsonFile.getAbsolutePath());
    }
}

