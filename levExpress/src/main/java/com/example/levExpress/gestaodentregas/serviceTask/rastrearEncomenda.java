package com.example.levExpress.gestaodentregas.serviceTask;

import com.google.gson.Gson;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class rastrearEncomenda {

    List<Map<String, Object>> outputList = new ArrayList<>();
    Gson gson = new Gson(); //converter as strings JSON em objetos


    @JobWorker(type = "rastrear-encomenda", fetchAllVariables = true)
    public void handle(final JobClient client, final ActivatedJob job) {
        try {
            System.out.println("recolher e transportar encomenda -> INPUT output: "+ outputList +"\n");

            Map<String, Object> variables = job.getVariablesAsMap();
        }
        catch (Exception e){
            System.err.println("Erro rastrear-encomenda -> " + e.getMessage());
            e.printStackTrace();
        }
    }
}
