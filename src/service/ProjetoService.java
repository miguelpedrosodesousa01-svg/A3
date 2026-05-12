package service;

import model.Equipe;
import model.Projeto;
import model.StatusProjeto;
import model.Usuario;
import util.ArquivoUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço para gerenciar projetos.
 */
public class ProjetoService {
    private List<Projeto> projetos;
    private List<Usuario> usuarios;
    private List<Equipe> equipes;

    /**
     * Construtor que carrega projetos do arquivo.
     * @param usuarios Lista de usuários para associações.
     * @param equipes Lista de equipes para associações.
     */
    public ProjetoService(List<Usuario> usuarios, List<Equipe> equipes) {
        this.usuarios = usuarios;
        this.equipes = equipes;
        this.projetos = ArquivoUtil.carregarProjetos(usuarios, equipes);
    }

    /**
     * Cadastra um novo projeto.
     * @param projeto Projeto a ser cadastrado.
     */
    public void cadastrar(Projeto projeto) {
        projetos.add(projeto);
        salvar();
    }

    /**
     * Lista todos os projetos.
     * @return Lista de projetos.
     */
    public List<Projeto> listar() {
        return new ArrayList<>(projetos);
    }

    /**
     * Busca projeto por nome.
     * @param nome Nome do projeto.
     * @return Projeto encontrado ou null.
     */
    public Projeto buscarPorNome(String nome) {
        return projetos.stream().filter(p -> p.getNome().equalsIgnoreCase(nome)).findFirst().orElse(null);
    }

    /**
     * Edita um projeto existente.
     * @param nome Nome do projeto a editar.
     * @param novoProjeto Novos dados do projeto.
     * @throws IllegalArgumentException Se projeto não encontrado.
     */
    public void editar(String nome, Projeto novoProjeto) {
        Projeto existente = buscarPorNome(nome);
        if (existente == null) {
            throw new IllegalArgumentException("Projeto não encontrado.");
        }
        projetos.remove(existente);
        projetos.add(novoProjeto);
        salvar();
    }

    /**
     * Remove um projeto.
     * @param nome Nome do projeto a remover.
     * @throws IllegalArgumentException Se projeto não encontrado.
     */
    public void remover(String nome) {
        Projeto projeto = buscarPorNome(nome);
        if (projeto == null) {
            throw new IllegalArgumentException("Projeto não encontrado.");
        }
        projetos.remove(projeto);
        salvar();
    }

    /**
     * Altera o status de um projeto.
     * @param nome Nome do projeto.
     * @param status Novo status.
     * @throws IllegalArgumentException Se projeto não encontrado.
     */
    public void alterarStatus(String nome, StatusProjeto status) {
        Projeto projeto = buscarPorNome(nome);
        if (projeto == null) {
            throw new IllegalArgumentException("Projeto não encontrado.");
        }
        projeto.setStatus(status);
        salvar();
    }

    /**
     * Mostra o gerente responsável por um projeto.
     * @param nome Nome do projeto.
     * @return Gerente ou null.
     */
    public Usuario mostrarGerente(String nome) {
        Projeto projeto = buscarPorNome(nome);
        return projeto != null ? projeto.getGerente() : null;
    }

    /**
     * Salva projetos no arquivo.
     */
    private void salvar() {
        ArquivoUtil.salvarProjetos(projetos, usuarios, equipes);
    }
}