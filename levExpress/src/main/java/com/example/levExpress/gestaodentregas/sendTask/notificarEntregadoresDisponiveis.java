package com.example.levExpress.gestaodentregas.sendTask;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.stereotype.Component;

@Component
public class notificarEntregadoresDisponiveis {
    @JobWorker(type = "notificar-entregadores-disponiveis", fetchAllVariables = true)
    public void handleJob(final JobClient client, final ActivatedJob job) {

        //TODO: buscar a informacao da encomenda ao ficheiro json

        //TODO: como input
    }
}
