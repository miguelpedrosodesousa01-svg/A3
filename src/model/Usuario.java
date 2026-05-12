package model;

import java.util.Objects;

/**
 * Classe que representa um usuário no sistema de gestão de projetos e equipes.
 */
public class Usuario {
    private String nome;
    private String cpf;
    private String email;
    private String cargo;
    private String login;
    private String senha;
    private Perfil perfil;

    /**
     * Construtor padrão.
     */
    public Usuario() {}

    /**
     * Construtor com parâmetros.
     * @param nome Nome completo do usuário.
     * @param cpf CPF do usuário.
     * @param email E-mail do usuário.
     * @param cargo Cargo do usuário.
     * @param login Login do usuário.
     * @param senha Senha do usuário.
     * @param perfil Perfil do usuário.
     */
    public Usuario(String nome, String cpf, String email, String cargo, String login, String senha, Perfil perfil) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.cargo = cargo;
        this.login = login;
        this.senha = senha;
        this.perfil = perfil;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Usuario usuario = (Usuario) obj;
        return Objects.equals(cpf, usuario.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", email='" + email + '\'' +
                ", cargo='" + cargo + '\'' +
                ", login='" + login + '\'' +
                ", perfil=" + perfil +
                '}';
    }
}