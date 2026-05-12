package model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Classe que representa uma tarefa no sistema de gestão de projetos e equipes.
 */
public class Tarefa {
    private String nome;
    private String descricao;
    private Usuario responsavel;
    private Projeto projeto;
    private LocalDate dataLimite;
    private StatusTarefa status;

    /**
     * Construtor padrão.
     */
    public Tarefa() {}

    /**
     * Construtor com parâmetros.
     * @param nome Nome da tarefa.
     * @param descricao Descrição da tarefa.
     * @param responsavel Responsável pela tarefa.
     * @param projeto Projeto relacionado.
     * @param dataLimite Data limite.
     * @param status Status da tarefa.
     */
    public Tarefa(String nome, String descricao, Usuario responsavel, Projeto projeto, LocalDate dataLimite, StatusTarefa status) {
        this.nome = nome;
        this.descricao = descricao;
        this.responsavel = responsavel;
        this.projeto = projeto;
        this.dataLimite = dataLimite;
        this.status = status;
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

    public Usuario getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Usuario responsavel) {
        this.responsavel = responsavel;
    }

    public Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

    public LocalDate getDataLimite() {
        return dataLimite;
    }

    public void setDataLimite(LocalDate dataLimite) {
        this.dataLimite = dataLimite;
    }

    public StatusTarefa getStatus() {
        return status;
    }

    public void setStatus(StatusTarefa status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Tarefa tarefa = (Tarefa) obj;
        return Objects.equals(nome, tarefa.nome) && Objects.equals(projeto, tarefa.projeto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, projeto);
    }

    @Override
    public String toString() {
        return "Tarefa{" +
                "nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", responsavel=" + (responsavel != null ? responsavel.getNome() : "Nenhum") +
                ", projeto=" + (projeto != null ? projeto.getNome() : "Nenhum") +
                ", dataLimite=" + dataLimite +
                ", status=" + status +
                '}';
    }
}