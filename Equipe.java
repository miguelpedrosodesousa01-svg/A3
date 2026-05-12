package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa uma equipe do projeto.
 */
public class Equipe {
    private String nome;
    private String descricao;
    private List<Usuario> membros;
    private Projeto projetoAssociado;

    public Equipe() {
        this.membros = new ArrayList<>();
    }

    public Equipe(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
        this.membros = new ArrayList<>();
    }

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

    public Projeto getProjetoAssociado() {
        return projetoAssociado;
    }

    public void setProjetoAssociado(Projeto projetoAssociado) {
        this.projetoAssociado = projetoAssociado;
    }

    public boolean adicionarMembro(Usuario usuario) {
        if (usuario != null && !membros.contains(usuario)) {
            membros.add(usuario);
            return true;
        }
        return false;
    }

    public boolean removerMembro(Usuario usuario) {
        return membros.remove(usuario);
    }

    public String toLinha() {
        StringBuilder membrosCpf = new StringBuilder();
        for (int i = 0; i < membros.size(); i++) {
            membrosCpf.append(membros.get(i).getCpf());
            if (i < membros.size() - 1) {
                membrosCpf.append(",");
            }
        }
        String projetoNome = projetoAssociado != null ? projetoAssociado.getNome() : "";
        return limpar(nome) + ";" + limpar(descricao) + ";" + limpar(projetoNome) + ";" + membrosCpf;
    }

    private String limpar(String valor) {
        return valor == null ? "" : valor.replace(";", ",").replace("\n", " ").replace("\r", " ");
    }

    @Override
    public String toString() {
        String projeto = projetoAssociado != null ? projetoAssociado.getNome() : "Nenhum projeto associado";
        return "Equipe: " + nome + " | Projeto: " + projeto + "\nDescrição: " + descricao + "\nMembros: " + membros.size();
    }
}
