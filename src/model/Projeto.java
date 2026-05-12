package model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Classe que representa um projeto no sistema de gestão de projetos e equipes.
 */
public class Projeto {
    private String nome;
    private String descricao;
    private LocalDate dataInicio;
    private LocalDate dataTerminoPrevista;
    private StatusProjeto status;
    private Usuario gerente;
    private Equipe equipe;

    /**
     * Construtor padrão.
     */
    public Projeto() {}

    /**
     * Construtor com parâmetros.
     * @param nome Nome do projeto.
     * @param descricao Descrição do projeto.
     * @param dataInicio Data de início.
     * @param dataTerminoPrevista Data de término prevista.
     * @param status Status do projeto.
     * @param gerente Gerente responsável.
     * @param equipe Equipe associada.
     */
    public Projeto(String nome, String descricao, LocalDate dataInicio, LocalDate dataTerminoPrevista, StatusProjeto status, Usuario gerente, Equipe equipe) {
        this.nome = nome;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataTerminoPrevista = dataTerminoPrevista;
        this.status = status;
        this.gerente = gerente;
        this.equipe = equipe;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataTerminoPrevista() {
        return dataTerminoPrevista;
    }

    public void setDataTerminoPrevista(LocalDate dataTerminoPrevista) {
        this.dataTerminoPrevista = dataTerminoPrevista;
    }

    public StatusProjeto getStatus() {
        return status;
    }

    public void setStatus(StatusProjeto status) {
        this.status = status;
    }

    public Usuario getGerente() {
        return gerente;
    }

    public void setGerente(Usuario gerente) {
        this.gerente = gerente;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Projeto projeto = (Projeto) obj;
        return Objects.equals(nome, projeto.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome);
    }

    @Override
    public String toString() {
        return "Projeto{" +
                "nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", dataInicio=" + dataInicio +
                ", dataTerminoPrevista=" + dataTerminoPrevista +
                ", status=" + status +
                ", gerente=" + (gerente != null ? gerente.getNome() : "Nenhum") +
                ", equipe=" + (equipe != null ? equipe.getNome() : "Nenhuma") +
                '}';
    }
}