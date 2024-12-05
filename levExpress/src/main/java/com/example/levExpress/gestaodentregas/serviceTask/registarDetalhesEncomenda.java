package com.example.levExpress.gestaodentregas.serviceTask;

import com.google.gson.Gson;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.stereotype.Component;

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
            Map<String, Object> variables = job.getVariablesAsMap();

            //TODO: resolver este erro
            detalhesEncomenda = (Map<String, Object>) variables.get("registar-detalhes-encomenda");

            // Exibir os detalhes no console
            /*if (detalhesEncomenda != null) {
                System.out.println("Origem: " + detalhesEncomenda.get("origem"));
                System.out.println("Destino: " + detalhesEncomenda.get("destino"));
                System.out.println("Peso: " + detalhesEncomenda.get("peso"));
                System.out.println("Dimensões: " + detalhesEncomenda.get("dimensao"));
                System.out.println("Data de Início: " + detalhesEncomenda.get("dataInicio"));
                System.out.println("Duração: " + detalhesEncomenda.get("duracao"));
                System.out.println("Foto: " + detalhesEncomenda.get("foto"));
                System.out.println("Descrição: " + detalhesEncomenda.get("descricao"));
            } else {
                System.out.println("Variável 'registar-detalhes-encomenda' não encontrada!");
            }*/

            System.out.println("criar pedido de entrega -> INPUT output: "+ detalhesEncomenda +"\n");
        }
        catch (Exception e){
            System.err.println("Erro rastrear-encomenda -> " + e.getMessage());
            e.printStackTrace();
        }
    }
}
