package com.example.levExpress.classes;

public class Cliente {
    private String telemovel;
    private String morada;
    private String cp; // Código postal
    private String cidade;
    private double mediaAvaliacao;
    private String estado;
    private int id;
    private String nome;
    private String email;
    private String foto;

    // Construtor vazio
    public Cliente() {
    }

    // Construtor completo
    public Cliente(String telemovel, String morada, String cp, String cidade, double mediaAvaliacao,
                   String estado, int id, String nome, String email, String foto) {
        this.telemovel = telemovel;
        this.morada = morada;
        this.cp = cp;
        this.cidade = cidade;
        this.mediaAvaliacao = mediaAvaliacao;
        this.estado = estado;
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.foto = foto;
    }

    // Getters e Setters
    public String getTelemovel() {
        return telemovel;
    }

    public void setTelemovel(String telemovel) {
        this.telemovel = telemovel;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public double getMediaAvaliacao() {
        return mediaAvaliacao;
    }

    public void setMediaAvaliacao(double mediaAvaliacao) {
        this.mediaAvaliacao = mediaAvaliacao;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    // Método toString para exibir os dados do cliente
    @Override
    public String toString() {
        return "Cliente{" +
                "telemovel='" + telemovel + '\'' +
                ", morada='" + morada + '\'' +
                ", cp='" + cp + '\'' +
                ", cidade='" + cidade + '\'' +
                ", mediaAvaliacao=" + mediaAvaliacao +
                ", estado='" + estado + '\'' +
                ", id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", foto='" + foto + '\'' +
                '}';
    }
}
