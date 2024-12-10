package com.example.levExpress.gestaodentregas.sendTask;

import com.example.levExpress.gestaodentregas.Email;
import com.example.levExpress.classes.Cliente;
import com.example.levExpress.classes.Encomenda;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class NotificarStatusEncomenda {

    File jsonFile = new File("D:\\githubProjects\\SI-levExpress\\levExpress\\src\\main\\java\\com\\example\\levExpress\\gestaodentregas\\BD.json"); // Caminho do ficheiro JSON
    ObjectMapper objectMapper = new ObjectMapper(); // Criar ObjectMapper para manipular o JSON


    @JobWorker(type = "notificar-status-encomenda", fetchAllVariables = true)
    public void handleJob(final JobClient client, final ActivatedJob job) throws IOException {

        //informacao da encomenda
        Encomenda encomenda = lerEncomendaJSON();

        //informacao do cliente
        Cliente cliente = lerClienteJSON();

        enviarNotificacaoCliente(encomenda, cliente);

        //atualizar estado encomenda para "entregue"
        exportarFicheiroJSON(encomenda);
    }

    private void exportarFicheiroJSON(Encomenda encomenda) throws IOException {

        // Criar o ObjectMapper para JSON
        ObjectMapper objectMapper = new ObjectMapper();

        // Carregar o ficheiro JSON existente
        File jsonFile = new File("D:\\githubProjects\\SI-levExpress\\levExpress\\src\\main\\java\\com\\example\\levExpress\\gestaodentregas\\BD.json");
        ObjectNode rootNode = null;

        if (jsonFile.exists() && jsonFile.length() > 0) {
            rootNode = (ObjectNode) objectMapper.readTree(jsonFile);
        }

        // Criar a seção "registar-detalhes-encomenda"
        ObjectNode registarNode = objectMapper.createObjectNode();
        registarNode.put("origem", encomenda.getOrigem());
        registarNode.put("destino", encomenda.getDestino());
        registarNode.put("peso", encomenda.getPeso());
        registarNode.put("dataInicio", encomenda.getDataInicio());
        registarNode.put("duracao", encomenda.getDuracao());
        registarNode.put("foto", encomenda.getFoto());
        registarNode.put("descricao", encomenda.getDescricao());
        registarNode.put("estado", "entregue");

        // Criar o node para dimensões
        ObjectNode dimensaoNode = objectMapper.createObjectNode();
        dimensaoNode.put("altura", encomenda.getDimensao().getAltura());
        dimensaoNode.put("largura", encomenda.getDimensao().getLargura());
        dimensaoNode.put("profundidade", encomenda.getDimensao().getProfundidade());

        // Adicionar o node de dimensões ao node principal
        registarNode.set("dimensao", dimensaoNode);

        // Atualizar o node principal
        rootNode.set("registar-detalhes-encomenda", registarNode);

        // Salvar o JSON atualizado no ficheiro
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, rootNode);

        // Exibir mensagem de sucesso
        System.out.println("(notificar-status-encomenda) JSON salvo no ficheiro: " + jsonFile.getAbsolutePath());
    }


    private void enviarNotificacaoCliente(Encomenda encomenda, Cliente cliente){
        String subject = "\uD83D\uDE9A Atualização do Estado da Sua Encomenda";

        String messageBody =
                        "Olá "+cliente.getNome()+",\n" +
                        "\n" +
                        "Gostaríamos de informar que o estado da sua encomenda foi atualizado:\n" +
                        "\n" +
                        "\uD83D\uDCE6 Estado: Em Transito ("+encomenda.getDataInicio()+")\n" +
                        "\uD83D\uDCE6 Estado Atual: Entregue \n" +

                        "\n" +
                        "Detalhes da Encomenda:\n" +
                        "- Origem: "+encomenda.getOrigem()+"\n" +
                        "- Destino: "+encomenda.getDestino()+"\n" +
                        "- Descrição: "+encomenda.getDescricao()+"\n" +
                        "\n" +
                        "Agradecemos por utilizar o nosso serviço. Se tiver alguma dúvida, estamos à disposição para ajudar." +
                        "Cumprimentos,\n" +
                        "Alice Dias\n" +
                        "Gestão de Entregas - LevExpress\n" +
                        "alicedias@levexpress.com";

        Email.enviarEmail(subject, messageBody, cliente.getEmail());

        System.out.println("(notificar-status-encomenda) enviar email");
    }

    public Cliente lerClienteJSON() throws IOException {

        try {
            // Ler o conteúdo de "notificar-cliente" e mapear para a classe
            Cliente detalhesCliente = objectMapper.readTree(jsonFile)
                    .path("notificar-cliente")
                    .traverse(objectMapper)
                    .readValueAs(Cliente.class);

            System.out.println("(notificar-status-encomenda) ler cliente JSON");

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

            System.out.println("(notificar-status-encomenda) ler encomenda JSON");

            return  detalhesEncomenda;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
