package com.example.levExpress.gestaodentregas.sendTask;

import com.example.levExpress.Email;
import com.example.levExpress.classes.Cliente;
import com.example.levExpress.classes.Encomenda;
import com.example.levExpress.classes.Entregador;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.event.CaretListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

@Component
public class notificarCliente {
    File jsonFile = new File("BD.json"); // Caminho do ficheiro JSON
    ObjectMapper objectMapper = new ObjectMapper(); // Criar ObjectMapper para manipular o JSON


    @JobWorker(type = "notificar-cliente", fetchAllVariables = true)
    public void handleJob(final JobClient client, final ActivatedJob job) throws IOException {

        //informacao da encomenda
        Encomenda encomenda = lerEncomendaJSON();

        //informacao do cliente
        Cliente cliente = lerClienteJSON();

        enviarNotificacaoCliente(encomenda, cliente);
    }

    private void enviarNotificacaoCliente(Encomenda encomenda, Cliente cliente){



        String subject = "\uD83D\uDE9A Sem Propostas para a sua Encomenda";

        String messageBody =
                        "Olá " + cliente.getNome() + ",\n\n" +
                        "Esperamos que esteja bem.\n\n" +
                        "Lamentamos informar que, infelizmente, nenhum entregador demonstrou interesse em realizar a entrega da sua encomenda com os detalhes fornecidos:\n\n" +
                        "📍 Origem:\n" + encomenda.getOrigem() + "\n\n" +
                        "📍 Destino:\n" + encomenda.getDestino() + "\n\n" +
                        "📦 Descrição:\n" + encomenda.getDescricao() + "\n\n" +
                        "⚖️ Peso:\n" + encomenda.getPeso() + "\n\n" +
                        "📐 Dimensões:\n" +
                        "Altura: " + encomenda.getDimensao().getAltura() + ", Largura: " + encomenda.getDimensao().getLargura() + ", Profundidade: " + encomenda.getDimensao().getProfundidade() + "\n\n" +
                        "⏰ Data de Disponibilidade:\n" + encomenda.getDataInicio() + "\n\n" +
                        "Recomendamos tentar novamente, ajustando as condições da entrega, como o valor oferecido ou o prazo estimado, para aumentar as chances de atrair entregadores.\n\n" +
                        "Estamos aqui para ajudar caso precise de assistência ou orientações sobre como prosseguir.\n\n" +
                        "Agradecemos pela sua compreensão e estamos à disposição para qualquer dúvida.\n\n" +
                        "Cumprimentos,\n" +
                        "Alice Dias\n" +
                        "Gestão de Entregas - LevExpress\n" +
                        "alicedias@levexpress.com";

        Email.enviarEmail(subject, messageBody, cliente.getEmail());
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
