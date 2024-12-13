package com.example.levExpress.gestaoavaliacao.sendTask;

import com.example.levExpress.gestaodentregas.Email;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.IOException;
import com.example.levExpress.classes.*;

@Component
public class NotificarClienteGestaoAvaliacao {

    File jsonFile = new File("D:\\dadosSistema\\pedidosApoio.json"); // Caminho do ficheiro JSON
    ObjectMapper objectMapper = new ObjectMapper(); // Criar ObjectMapper para manipular o JSON

    @JobWorker(type = "notificar-cliente-gestaoavaliacao", fetchAllVariables = true)
    public void handleJob(final JobClient client, final ActivatedJob job) throws IOException {

        // Informações do pedido e cliente
        Pedido pedido = lerPedidoJSON();
        Cliente cliente = lerClienteJSON();

        enviarNotificacaoCliente(pedido, cliente);
    }

    private void enviarNotificacaoCliente(Pedido pedido, Cliente cliente) {
        // Assunto do e-mail
        String subject = "\u2709 Pedido Resolvido com Sucesso!";

        // Corpo do e-mail
        String messageBody =
                "Olá " + cliente.getNome() + ",\n\n" +
                "É com grande satisfação que informamos que o seu pedido de apoio foi resolvido com sucesso!\n\n" +
                "Aqui estão os detalhes do seu pedido:\n\n" +
                "\uD83D\uDCCD Título do Pedido: " + pedido.getTitulo() + "\n" +
                "\uD83D\uDCCB Descrição: " + pedido.getDescricao() + "\n" +
                "⏰ Data de Resolução: " + pedido.getDataResolucao() + "\n\n" +
                "Caso tenha dúvidas ou precise de mais informações, não hesite em entrar em contato conosco.\n\n" +
                "Agradecemos por usar nossos serviços e esperamos continuar a ajudá-lo no futuro.\n\n" +
                "Cumprimentos,\n" +
                "Equipe de Gestão de Apoio\n";

        // Enviar o e-mail
        Email.enviarEmail(subject, messageBody, cliente.getEmail());
    }

    public Cliente lerClienteJSON() throws IOException {
        try {
            // Ler o conteúdo de "notificar-cliente" e mapear para a classe Cliente
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

    public Pedido lerPedidoJSON() throws IOException {
        try {
            // Ler o conteúdo de "resolucao-pedido" e mapear para a classe Pedido
            Pedido detalhesPedido = objectMapper.readTree(jsonFile)
                    .path("resolucao-pedido")
                    .traverse(objectMapper)
                    .readValueAs(Pedido.class);

            return detalhesPedido;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

