package com.example.levExpress.classes;

public class Encomenda {
    private String origem;
    private String destino;
    private String peso;
    private String dataInicio;
    private String duracao;
    private String foto;
    private String descricao;
    private String estado;
    private Dimensao dimensao;


    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Dimensao getDimensao() {
        return dimensao;
    }

    public void setDimensao(Dimensao dimensao) {
        this.dimensao = dimensao;
    }

    @Override
    public String toString() {
        return "Encomenda{" +
                "origem='" + origem + '\'' +
                ", destino='" + destino + '\'' +
                ", peso='" + peso + '\'' +
                ", dataInicio='" + dataInicio + '\'' +
                ", duracao='" + duracao + '\'' +
                ", foto='" + foto + '\'' +
                ", descricao='" + descricao + '\'' +
                ", estado='" + estado + '\'' +
                ", dimensao=" + dimensao +
                '}';
    }
}
