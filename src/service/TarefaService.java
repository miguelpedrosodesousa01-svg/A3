package service;

import model.Projeto;
import model.StatusTarefa;
import model.Tarefa;
import model.Usuario;
import util.ArquivoUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço para gerenciar tarefas.
 */
public class TarefaService {
    private List<Tarefa> tarefas;
    private List<Usuario> usuarios;
    private List<Projeto> projetos;

    /**
     * Construtor que carrega tarefas do arquivo.
     * @param usuarios Lista de usuários para associações.
     * @param projetos Lista de projetos para associações.
     */
    public TarefaService(List<Usuario> usuarios, List<Projeto> projetos) {
        this.usuarios = usuarios;
        this.projetos = projetos;
        this.tarefas = ArquivoUtil.carregarTarefas(usuarios, projetos);
    }

    /**
     * Cria uma nova tarefa.
     * @param tarefa Tarefa a ser criada.
     */
    public void criar(Tarefa tarefa) {
        tarefas.add(tarefa);
        salvar();
    }

    /**
     * Lista todas as tarefas.
     * @return Lista de tarefas.
     */
    public List<Tarefa> listar() {
        return new ArrayList<>(tarefas);
    }

    /**
     * Altera o status de uma tarefa.
     * @param nome Nome da tarefa.
     * @param status Novo status.
     * @throws IllegalArgumentException Se tarefa não encontrada.
     */
    public void alterarStatus(String nome, StatusTarefa status) {
        Tarefa tarefa = buscarPorNome(nome);
        if (tarefa == null) {
            throw new IllegalArgumentException("Tarefa não encontrada.");
        }
        tarefa.setStatus(status);
        salvar();
    }

    /**
     * Busca tarefa por nome.
     * @param nome Nome da tarefa.
     * @return Tarefa encontrada ou null.
     */
    public Tarefa buscarPorNome(String nome) {
        return tarefas.stream().filter(t -> t.getNome().equalsIgnoreCase(nome)).findFirst().orElse(null);
    }

    /**
     * Busca tarefas por projeto.
     * @param projeto Projeto.
     * @return Lista de tarefas do projeto.
     */
    public List<Tarefa> buscarPorProjeto(Projeto projeto) {
        return tarefas.stream().filter(t -> t.getProjeto() != null && t.getProjeto().equals(projeto)).collect(Collectors.toList());
    }

    /**
     * Busca tarefas por responsável.
     * @param responsavel Responsável.
     * @return Lista de tarefas do responsável.
     */
    public List<Tarefa> buscarPorResponsavel(Usuario responsavel) {
        return tarefas.stream().filter(t -> t.getResponsavel() != null && t.getResponsavel().equals(responsavel)).collect(Collectors.toList());
    }

    /**
     * Salva tarefas no arquivo.
     */
    private void salvar() {
        ArquivoUtil.salvarTarefas(tarefas, usuarios, projetos);
    }
}