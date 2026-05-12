package service;

import model.Equipe;
import model.Projeto;
import model.Usuario;
import util.ArquivoUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Serviço para gerenciar equipes.
 */
public class EquipeService {
    private List<Equipe> equipes;
    private List<Usuario> usuarios;
    private List<Projeto> projetos;

    /**
     * Construtor que carrega equipes do arquivo.
     * @param usuarios Lista de usuários para associações.
     * @param projetos Lista de projetos para associações.
     */
    public EquipeService(List<Usuario> usuarios, List<Projeto> projetos) {
        this.usuarios = usuarios;
        this.projetos = projetos;
        this.equipes = ArquivoUtil.carregarEquipes(usuarios);
    }

    /**
     * Cadastra uma nova equipe.
     * @param equipe Equipe a ser cadastrada.
     */
    public void cadastrar(Equipe equipe) {
        equipes.add(equipe);
        salvar();
    }

    /**
     * Lista todas as equipes.
     * @return Lista de equipes.
     */
    public List<Equipe> listar() {
        return new ArrayList<>(equipes);
    }

    /**
     * Busca equipe por nome.
     * @param nome Nome da equipe.
     * @return Equipe encontrada ou null.
     */
    public Equipe buscarPorNome(String nome) {
        return equipes.stream().filter(e -> e.getNome().equalsIgnoreCase(nome)).findFirst().orElse(null);
    }

    /**
     * Adiciona um membro à equipe.
     * @param nomeEquipe Nome da equipe.
     * @param usuario Usuário a adicionar.
     * @throws IllegalArgumentException Se equipe não encontrada.
     */
    public void adicionarMembro(String nomeEquipe, Usuario usuario) {
        Equipe equipe = buscarPorNome(nomeEquipe);
        if (equipe == null) {
            throw new IllegalArgumentException("Equipe não encontrada.");
        }
        equipe.adicionarMembro(usuario);
        salvar();
    }

    /**
     * Remove um membro da equipe.
     * @param nomeEquipe Nome da equipe.
     * @param usuario Usuário a remover.
     * @throws IllegalArgumentException Se equipe não encontrada.
     */
    public void removerMembro(String nomeEquipe, Usuario usuario) {
        Equipe equipe = buscarPorNome(nomeEquipe);
        if (equipe == null) {
            throw new IllegalArgumentException("Equipe não encontrada.");
        }
        equipe.removerMembro(usuario);
        salvar();
    }

    /**
     * Associa equipe a um projeto.
     * @param nomeEquipe Nome da equipe.
     * @param projeto Projeto a associar.
     * @throws IllegalArgumentException Se equipe não encontrada.
     */
    public void associarAoProjeto(String nomeEquipe, Projeto projeto) {
        Equipe equipe = buscarPorNome(nomeEquipe);
        if (equipe == null) {
            throw new IllegalArgumentException("Equipe não encontrada.");
        }
        projeto.setEquipe(equipe);
        // Salvar projetos também, mas como ProjetoService salva, talvez notificar. Por simplicidade, assumir que ProjetoService salva quando necessário.
    }

    /**
     * Mostra membros da equipe.
     * @param nomeEquipe Nome da equipe.
     * @return Lista de membros.
     */
    public List<Usuario> mostrarMembros(String nomeEquipe) {
        Equipe equipe = buscarPorNome(nomeEquipe);
        return equipe != null ? equipe.getMembros() : new ArrayList<>();
    }

    /**
     * Salva equipes no arquivo.
     */
    private void salvar() {
        ArquivoUtil.salvarEquipes(equipes, usuarios);
    }
}