package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Classe que representa uma equipe no sistema de gestão de projetos e equipes.
 */
public class Equipe {
    private String nome;
    private String descricao;
    private List<Usuario> membros;

    /**
     * Construtor padrão.
     */
    public Equipe() {
        this.membros = new ArrayList<>();
    }

    /**
     * Construtor com parâmetros.
     * @param nome Nome da equipe.
     * @param descricao Descrição da equipe.
     */
    public Equipe(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
        this.membros = new ArrayList<>();
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

    public List<Usuario> getMembros() {
        return membros;
    }

    public void setMembros(List<Usuario> membros) {
        this.membros = membros;
    }

    /**
     * Adiciona um membro à equipe.
     * @param usuario Usuário a ser adicionado.
     */
    public void adicionarMembro(Usuario usuario) {
        if (!membros.contains(usuario)) {
            membros.add(usuario);
        }
    }

    /**
     * Remove um membro da equipe.
     * @param usuario Usuário a ser removido.
     */
    public void removerMembro(Usuario usuario) {
        membros.remove(usuario);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Equipe equipe = (Equipe) obj;
        return Objects.equals(nome, equipe.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome);
    }

    @Override
    public String toString() {
        return "Equipe{" +
                "nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", membros=" + membros.size() +
                '}';
    }
}