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

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

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

        //notificarEntregadores(encomenda, entregadores);
    }

    private void enviarNotificacaoEntregador(Encomenda encomenda, Entregador entregador){
        String subject = "\uD83D\uDE9A Nova Encomenda Disponível para Entrega – Envie Sua Proposta!";

        String messageBody =
                "Olá " + entregador.getNome() + ",\n\n"+
                "Estamos felizes em informá-lo sobre uma nova oportunidade de entrega disponível na sua área. Aqui estão os detalhes da encomenda:\n\n" +
                "📍 Origem:\n" + encomenda.getOrigem() + "\n\n" +
                "📍 Destino:\n" + encomenda.getDestino() + "\n\n" +
                "📦 Descrição:\n" + encomenda.getDestino() + "\n\n" +
                "⚖️ Peso:\n" + encomenda.getPeso() + "\n\n" +
                "📐 Dimensões:\n" +
                "Altura: " + encomenda.getDimensao().getAltura() + ", Largura: " + encomenda.getDimensao().getLargura() + ", Profundidade: " + encomenda.getDimensao().getProfundidade() + "\n\n" +
                "⏰ Data de Disponibilidade:\n" + encomenda.getDataInicio() + "\n\n" +
                "Se está interessado em realizar esta entrega, envie a sua proposta com os seguintes detalhes:\n" +
                "- Valor pelo serviço\n" +
                "- Meio de transporte (moto, carro, bicicleta, etc.)\n" +
                "- Tempo estimado para a entrega\n\n" +
                "Como Enviar a Proposta:\n" +
                "Responda a este e-mail com as informações pedidas.\n\n" +
                "O cliente escolherá a melhor proposta com base no preço, tempo estimado e meio de transporte.\n\n" +
                "🚀 Seja rápido! Quanto antes enviar a proposta, maior será a sua chance de ser selecionado.\n\n" +
                "Agradecemos pela sua colaboração e estamos à disposição para quaisquer dúvidas.\n\n" +
                "Cumprimentos,\n" +
                "Alice Dias\n" +
                "Gestão de Entregas - LevExpress\n" +
                "alicedias@levexpress.com";

        // Configurações para o servidor de e-mail (ajuste conforme necessário)
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Credenciais de e-mail
        String username = "noreply.tecourses@gmail.com";
        String password = "ypla lbis djic pulw";

        // Cria uma sessão com autenticação
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Cria uma mensagem de e-mail
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(entregador.getEmail()));
            message.setSubject(subject);
            message.setText(messageBody);

            // Envia a mensagem
            Transport.send(message);

            System.out.println("E-mail enviado com sucesso para: " + entregador.getEmail());

        } catch (MessagingException e) {
            throw new RuntimeException("Erro ao enviar e-mail", e);
        }
    }

    private void notificarEntregadores(Encomenda encomenda, List<Entregador> entregadores){

        //notificar todos os entregadores mais proximos e disponiveis
        for(Entregador entregador : entregadores){
            enviarNotificacaoEntregador(encomenda, entregador);
        }
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
