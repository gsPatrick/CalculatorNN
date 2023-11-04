package model;

public class Usuario {

    private String email;
    private String senha;
    private String nome;
    private String apelido;

    public Usuario() {
        // Construtor padrão vazio
    }

    public Usuario(String email, String senha, String nome, String apelido) {
        this.email = email;
        this.senha = senha;
        this.nome = nome;
        this.apelido = apelido;
    }

    // Getters e Setters

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }
}
