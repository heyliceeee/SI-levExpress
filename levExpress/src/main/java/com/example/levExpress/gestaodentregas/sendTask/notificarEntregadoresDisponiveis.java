package com.example.levExpress.gestaodentregas.sendTask;

import com.example.levExpress.classes.Encomenda;
import com.example.levExpress.classes.Entregador;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class notificarEntregadoresDisponiveis {
    File jsonFile = new File("BD.json"); // Caminho do ficheiro JSON
    ObjectMapper objectMapper = new ObjectMapper(); // Criar ObjectMapper para manipular o JSON


    @JobWorker(type = "notificar-entregadores-disponiveis", fetchAllVariables = true)
    public void handleJob(final JobClient client, final ActivatedJob job) throws IOException {

        //informacao da encomenda
        Encomenda encomenda = lerEncomendaJSON();

        //todos os entregadores
        List<Entregador> entregadores = lerEntregadoresJSON();

        //TODO: enviar email

        System.out.println("ENCOMENDA: "+encomenda);
        System.out.println("ENTREGADORES: "+entregadores);
    }

    private Encomenda lerEncomendaJSON() throws IOException {

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
}
