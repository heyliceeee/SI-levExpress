package com.example.levExpress.classes;

import java.time.LocalDate;

public class ClassificarPedido {
    private String titulo;
    private String descricao;
    private LocalDate dataResolucao;
    private int classificacao;

    public ClassificarPedido() {}

    public ClassificarPedido(String titulo, String descricao, LocalDate dataResolucao, int classificacao) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataResolucao = dataResolucao;
        this.classificacao = classificacao;
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

    public int getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(int classificacao) {
        this.classificacao = classificacao;
    }
}
