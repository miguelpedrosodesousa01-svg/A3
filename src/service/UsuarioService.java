package service;

import model.Perfil;
import model.Usuario;
import util.ArquivoUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço para gerenciar usuários.
 */
public class UsuarioService {
    private List<Usuario> usuarios;

    /**
     * Construtor que carrega usuários do arquivo.
     */
    public UsuarioService() {
        this.usuarios = ArquivoUtil.carregarUsuarios();
    }

    /**
     * Cadastra um novo usuário.
     * @param usuario Usuário a ser cadastrado.
     * @throws IllegalArgumentException Se CPF já existir.
     */
    public void cadastrar(Usuario usuario) {
        if (buscarPorCpf(usuario.getCpf()) != null) {
            throw new IllegalArgumentException("CPF já cadastrado.");
        }
        usuarios.add(usuario);
        salvar();
    }

    /**
     * Lista todos os usuários.
     * @return Lista de usuários.
     */
    public List<Usuario> listar() {
        return new ArrayList<>(usuarios);
    }

    /**
     * Busca usuário por CPF.
     * @param cpf CPF do usuário.
     * @return Usuário encontrado ou null.
     */
    public Usuario buscarPorCpf(String cpf) {
        return usuarios.stream().filter(u -> u.getCpf().equals(cpf)).findFirst().orElse(null);
    }

    /**
     * Edita um usuário existente.
     * @param cpf CPF do usuário a editar.
     * @param novoUsuario Novos dados do usuário.
     * @throws IllegalArgumentException Se usuário não encontrado.
     */
    public void editar(String cpf, Usuario novoUsuario) {
        Usuario existente = buscarPorCpf(cpf);
        if (existente == null) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }
        usuarios.remove(existente);
        usuarios.add(novoUsuario);
        salvar();
    }

    /**
     * Remove um usuário.
     * @param cpf CPF do usuário a remover.
     * @throws IllegalArgumentException Se usuário não encontrado.
     */
    public void remover(String cpf) {
        Usuario usuario = buscarPorCpf(cpf);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }
        usuarios.remove(usuario);
        salvar();
    }

    /**
     * Lista usuários por perfil.
     * @param perfil Perfil desejado.
     * @return Lista de usuários com o perfil.
     */
    public List<Usuario> listarPorPerfil(Perfil perfil) {
        return usuarios.stream().filter(u -> u.getPerfil() == perfil).collect(Collectors.toList());
    }

    /**
     * Salva usuários no arquivo.
     */
    private void salvar() {
        ArquivoUtil.salvarUsuarios(usuarios);
    }
}