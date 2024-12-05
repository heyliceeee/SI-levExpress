package com.example.levExpress.classes;

public class Dimensao {
    private String altura;
    private String largura;
    private String profundidade;

    // Getters e Setters
    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public String getLargura() {
        return largura;
    }

    public void setLargura(String largura) {
        this.largura = largura;
    }

    public String getProfundidade() {
        return profundidade;
    }

    public void setProfundidade(String profundidade) {
        this.profundidade = profundidade;
    }

    @Override
    public String toString() {
        return "Dimensao{" +
                "altura='" + altura + '\'' +
                ", largura='" + largura + '\'' +
                ", profundidade='" + profundidade + '\'' +
                '}';
    }
}
