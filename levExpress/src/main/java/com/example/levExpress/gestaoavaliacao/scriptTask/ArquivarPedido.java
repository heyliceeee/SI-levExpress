package com.example.levExpress.gestaoavaliacao.scriptTask;

import com.example.levExpress.classes.Pedido;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Map;

@Component
public class ArquivarPedido {

    private boolean problemaResolvido;
    private boolean confirmacaoCliente;

    public ArquivarPedido() {}

    public ArquivarPedido(boolean problemaResolvido, boolean confirmacaoCliente) {
        this.problemaResolvido = problemaResolvido;
        this.confirmacaoCliente = confirmacaoCliente;
    }

    public boolean isProblemaResolvido() {
        return problemaResolvido;
    }

    public void setProblemaResolvido(boolean problemaResolvido) {
        this.problemaResolvido = problemaResolvido;
    }

    public boolean isConfirmacaoCliente() {
        return confirmacaoCliente;
    }

    public void setConfirmacaoCliente(boolean confirmacaoCliente) {
        this.confirmacaoCliente = confirmacaoCliente;
    }

    public boolean podeArquivar() {
        return problemaResolvido && confirmacaoCliente;
    }

    public void arquivarPedido(Pedido pedido) {
        if (podeArquivar()) {
            System.out.println("O pedido '" + pedido.getTitulo() + "' foi arquivado com sucesso.");
        } else {
            System.out.println("O pedido '" + pedido.getTitulo() + "' não pode ser arquivado. Confirmação pendente.");
        }
    }

    @JobWorker(type = "arquivar-pedido", fetchAllVariables = true)
    public void handle(final JobClient client, final ActivatedJob job) {
        setConfirmacaoCliente(true);
        setProblemaResolvido(true);

        arquivarPedido(new Pedido("titulo", "descricao", LocalDate.now(), 0));
    }
}
