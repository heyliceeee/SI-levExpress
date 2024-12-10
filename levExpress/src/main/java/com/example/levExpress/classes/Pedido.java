package com.example.levExpress.classes;

import java.time.LocalDate;

public class Pedido {
    private String titulo;
    private String descricao;
    private LocalDate dataResolucao;

    public Pedido() {}

    public Pedido(String titulo, String descricao, LocalDate dataResolucao) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataResolucao = dataResolucao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataResolucao() {
        return dataResolucao;
    }

    public void setDataResolucao(LocalDate dataResolucao) {
        this.dataResolucao = dataResolucao;
    }
}
