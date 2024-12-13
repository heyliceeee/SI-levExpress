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
public class ReceberPedidoApoio {

    @JobWorker(type = "receber-pedido-apoio", fetchAllVariables = true)
    public void handle(final JobClient client, final ActivatedJob job) {
        try {
            Map<String, Object> detalhesPedido = job.getVariablesAsMap();
            exportarFicheiroJSON(detalhesPedido);
        } catch (Exception e) {
            System.err.println("Erro receber-pedido-apoio -> " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void exportarFicheiroJSON(Map<String, Object> detalhesPedido) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File jsonFile = new File("D:\\githubProjects\\SI-levExpress\\levExpress\\src\\main\\java\\com\\example\\levExpress\\gestaoavaliacao\\pedidosApoio.json");
        ObjectNode rootNode = jsonFile.exists() && jsonFile.length() > 0
                ? (ObjectNode) objectMapper.readTree(jsonFile)
                : objectMapper.createObjectNode();

        ObjectNode pedidoNode = objectMapper.createObjectNode();
        pedidoNode.put("cliente", (String) detalhesPedido.get("cliente"));
        pedidoNode.put("tipoProblema", (String) detalhesPedido.get("tipoProblema"));
        pedidoNode.put("detalhes", (String) detalhesPedido.get("detalhes"));
        pedidoNode.put("dataCriacao", (String) detalhesPedido.get("dataCriacao"));
        pedidoNode.put("status", "recebido");

        rootNode.set("pedido-apoio", pedidoNode);

        objectMapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, rootNode);
        System.out.println("Pedido de apoio registrado: " + jsonFile.getAbsolutePath());
    }
}

