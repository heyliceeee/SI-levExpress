package com.example.levExpress.classes;

public class Entregador {
    private String nome;
    private String email;
    private String cidade;
    private String foto;
    private double estrelas;

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public double getEstrelas() {
        return estrelas;
    }

    public void setEstrelas(double estrelas) {
        this.estrelas = estrelas;
    }

    @Override
    public String toString() {
        return "Entregador{" +
                "nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", cidade='" + cidade + '\'' +
                ", foto='" + foto + '\'' +
                ", estrelas=" + estrelas +
                '}';
    }
}
