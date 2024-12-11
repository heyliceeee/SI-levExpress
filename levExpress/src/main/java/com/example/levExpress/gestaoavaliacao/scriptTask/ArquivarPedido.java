package com.example.levExpress.classes;

import java.time.LocalDate;

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

    public void arquivarPedido(ClassificarPedido pedido) {
        if (podeArquivar()) {
            System.out.println("O pedido '" + pedido.getTitulo() + "' foi arquivado com sucesso.");
        } else {
            System.out.println("O pedido '" + pedido.getTitulo() + "' não pode ser arquivado. Confirmação pendente.");
        }
    }
}
