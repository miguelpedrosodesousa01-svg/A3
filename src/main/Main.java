package main;

import model.*;
import service.*;
import util.ArquivoUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/**
 * Classe principal do sistema de gestão de projetos e equipes.
 */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static UsuarioService usuarioService;
    private static ProjetoService projetoService;
    private static EquipeService equipeService;
    private static TarefaService tarefaService;

    public static void main(String[] args) {
        // Inicializar serviços
        usuarioService = new UsuarioService();
        List<Usuario> usuarios = usuarioService.listar();
        List<Equipe> equipes = ArquivoUtil.carregarEquipes(usuarios);
        projetoService = new ProjetoService(usuarios, equipes);
        equipeService = new EquipeService(usuarios, projetoService.listar());
        tarefaService = new TarefaService(usuarios, projetoService.listar());

        boolean running = true;
        while (running) {
            exibirMenuPrincipal();
            int opcao = lerInteiro("Escolha uma opção: ");
            switch (opcao) {
                case 1 -> menuUsuarios();
                case 2 -> menuProjetos();
                case 3 -> menuEquipes();
                case 4 -> menuTarefas();
                case 5 -> menuRelatorios();
                case 0 -> running = false;
                default -> System.out.println("Opção inválida!");
            }
        }
        System.out.println("Sistema encerrado.");
    }

    private static void exibirMenuPrincipal() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("SISTEMA DE GESTÃO DE PROJETOS E EQUIPES");
        System.out.println("=".repeat(50));
        System.out.println("1. Gerenciar Usuários");
        System.out.println("2. Gerenciar Projetos");
        System.out.println("3. Gerenciar Equipes");
        System.out.println("4. Gerenciar Tarefas");
        System.out.println("5. Relatórios");
        System.out.println("0. Sair");
        System.out.println("=".repeat(50));
    }

    private static void menuUsuarios() {
        boolean back = false;
        while (!back) {
            System.out.println("\n" + "-".repeat(30));
            System.out.println("MENU USUÁRIOS");
            System.out.println("-".repeat(30));
            System.out.println("1. Cadastrar Usuário");
            System.out.println("2. Listar Usuários");
            System.out.println("3. Buscar Usuário por CPF");
            System.out.println("4. Editar Usuário");
            System.out.println("5. Remover Usuário");
            System.out.println("6. Listar Usuários por Perfil");
            System.out.println("0. Voltar");
            System.out.println("-".repeat(30));
            int opcao = lerInteiro("Escolha uma opção: ");
            switch (opcao) {
                case 1 -> cadastrarUsuario();
                case 2 -> listarUsuarios();
                case 3 -> buscarUsuarioPorCpf();
                case 4 -> editarUsuario();
                case 5 -> removerUsuario();
                case 6 -> listarUsuariosPorPerfil();
                case 0 -> back = true;
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private static void cadastrarUsuario() {
        try {
            System.out.println("\nCadastrar Usuário:");
            String nome = lerString("Nome completo: ");
            String cpf = lerString("CPF: ");
            String email = lerString("E-mail: ");
            String cargo = lerString("Cargo: ");
            String login = lerString("Login: ");
            String senha = lerString("Senha: ");
            Perfil perfil = escolherPerfil();
            Usuario usuario = new Usuario(nome, cpf, email, cargo, login, senha, perfil);
            usuarioService.cadastrar(usuario);
            System.out.println("Usuário cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listar();
        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado.");
        } else {
            System.out.println("\nUsuários:");
            for (Usuario u : usuarios) {
                System.out.println(u);
            }
        }
    }

    private static void buscarUsuarioPorCpf() {
        String cpf = lerString("CPF: ");
        Usuario usuario = usuarioService.buscarPorCpf(cpf);
        if (usuario != null) {
            System.out.println("Usuário encontrado: " + usuario);
        } else {
            System.out.println("Usuário não encontrado.");
        }
    }

    private static void editarUsuario() {
        try {
            String cpf = lerString("CPF do usuário a editar: ");
            Usuario existente = usuarioService.buscarPorCpf(cpf);
            if (existente == null) {
                System.out.println("Usuário não encontrado.");
                return;
            }
            System.out.println("Dados atuais: " + existente);
            String nome = lerString("Novo nome (ou enter para manter): ");
            if (!nome.isEmpty()) existente.setNome(nome);
            String email = lerString("Novo e-mail: ");
            if (!email.isEmpty()) existente.setEmail(email);
            String cargo = lerString("Novo cargo: ");
            if (!cargo.isEmpty()) existente.setCargo(cargo);
            String login = lerString("Novo login: ");
            if (!login.isEmpty()) existente.setLogin(login);
            String senha = lerString("Nova senha: ");
            if (!senha.isEmpty()) existente.setSenha(senha);
            Perfil perfil = escolherPerfil();
            existente.setPerfil(perfil);
            usuarioService.editar(cpf, existente);
            System.out.println("Usuário editado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void removerUsuario() {
        try {
            String cpf = lerString("CPF do usuário a remover: ");
            usuarioService.remover(cpf);
            System.out.println("Usuário removido com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void listarUsuariosPorPerfil() {
        Perfil perfil = escolherPerfil();
        List<Usuario> usuarios = usuarioService.listarPorPerfil(perfil);
        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário com esse perfil.");
        } else {
            System.out.println("\nUsuários com perfil " + perfil + ":");
            for (Usuario u : usuarios) {
                System.out.println(u);
            }
        }
    }

    private static Perfil escolherPerfil() {
        System.out.println("Perfis:");
        System.out.println("1. ADMINISTRADOR");
        System.out.println("2. GERENTE");
        System.out.println("3. COLABORADOR");
        int op = lerInteiro("Escolha: ");
        return switch (op) {
            case 1 -> Perfil.ADMINISTRADOR;
            case 2 -> Perfil.GERENTE;
            case 3 -> Perfil.COLABORADOR;
            default -> {
                System.out.println("Inválido, assumindo COLABORADOR.");
                yield Perfil.COLABORADOR;
            }
        };
    }

    private static void menuProjetos() {
        boolean back = false;
        while (!back) {
            System.out.println("\n" + "-".repeat(30));
            System.out.println("MENU PROJETOS");
            System.out.println("-".repeat(30));
            System.out.println("1. Cadastrar Projeto");
            System.out.println("2. Listar Projetos");
            System.out.println("3. Buscar Projeto por Nome");
            System.out.println("4. Editar Projeto");
            System.out.println("5. Remover Projeto");
            System.out.println("6. Alterar Status");
            System.out.println("7. Mostrar Gerente");
            System.out.println("0. Voltar");
            System.out.println("-".repeat(30));
            int opcao = lerInteiro("Escolha uma opção: ");
            switch (opcao) {
                case 1 -> cadastrarProjeto();
                case 2 -> listarProjetos();
                case 3 -> buscarProjetoPorNome();
                case 4 -> editarProjeto();
                case 5 -> removerProjeto();
                case 6 -> alterarStatusProjeto();
                case 7 -> mostrarGerenteProjeto();
                case 0 -> back = true;
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private static void cadastrarProjeto() {
        try {
            System.out.println("\nCadastrar Projeto:");
            String nome = lerString("Nome: ");
            String descricao = lerString("Descrição: ");
            LocalDate dataInicio = lerData("Data de início (dd/MM/yyyy): ");
            LocalDate dataTermino = lerData("Data de término prevista (dd/MM/yyyy): ");
            StatusProjeto status = StatusProjeto.PLANEJADO;
            Usuario gerente = escolherUsuario("Gerente responsável: ");
            Equipe equipe = escolherEquipe("Equipe associada (opcional): ");
            Projeto projeto = new Projeto(nome, descricao, dataInicio, dataTermino, status, gerente, equipe);
            projetoService.cadastrar(projeto);
            System.out.println("Projeto cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void listarProjetos() {
        List<Projeto> projetos = projetoService.listar();
        if (projetos.isEmpty()) {
            System.out.println("Nenhum projeto cadastrado.");
        } else {
            System.out.println("\nProjetos:");
            for (Projeto p : projetos) {
                System.out.println(p);
            }
        }
    }

    private static void buscarProjetoPorNome() {
        String nome = lerString("Nome: ");
        Projeto projeto = projetoService.buscarPorNome(nome);
        if (projeto != null) {
            System.out.println("Projeto encontrado: " + projeto);
        } else {
            System.out.println("Projeto não encontrado.");
        }
    }

    private static void editarProjeto() {
        try {
            String nome = lerString("Nome do projeto a editar: ");
            Projeto existente = projetoService.buscarPorNome(nome);
            if (existente == null) {
                System.out.println("Projeto não encontrado.");
                return;
            }
            System.out.println("Dados atuais: " + existente);
            String novoNome = lerString("Novo nome: ");
            if (!novoNome.isEmpty()) existente.setNome(novoNome);
            String descricao = lerString("Nova descrição: ");
            if (!descricao.isEmpty()) existente.setDescricao(descricao);
            LocalDate dataInicio = lerDataOpcional("Nova data de início (dd/MM/yyyy ou enter): ");
            if (dataInicio != null) existente.setDataInicio(dataInicio);
            LocalDate dataTermino = lerDataOpcional("Nova data de término (dd/MM/yyyy ou enter): ");
            if (dataTermino != null) existente.setDataTerminoPrevista(dataTermino);
            Usuario gerente = escolherUsuarioOpcional("Novo gerente: ");
            if (gerente != null) existente.setGerente(gerente);
            Equipe equipe = escolherEquipeOpcional("Nova equipe: ");
            if (equipe != null) existente.setEquipe(equipe);
            projetoService.editar(nome, existente);
            System.out.println("Projeto editado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void removerProjeto() {
        try {
            String nome = lerString("Nome do projeto a remover: ");
            projetoService.remover(nome);
            System.out.println("Projeto removido com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void alterarStatusProjeto() {
        try {
            String nome = lerString("Nome do projeto: ");
            StatusProjeto status = escolherStatusProjeto();
            projetoService.alterarStatus(nome, status);
            System.out.println("Status alterado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void mostrarGerenteProjeto() {
        String nome = lerString("Nome do projeto: ");
        Usuario gerente = projetoService.mostrarGerente(nome);
        if (gerente != null) {
            System.out.println("Gerente: " + gerente);
        } else {
            System.out.println("Projeto não encontrado ou sem gerente.");
        }
    }

    private static StatusProjeto escolherStatusProjeto() {
        System.out.println("Status:");
        System.out.println("1. PLANEJADO");
        System.out.println("2. EM_ANDAMENTO");
        System.out.println("3. CONCLUIDO");
        System.out.println("4. CANCELADO");
        int op = lerInteiro("Escolha: ");
        return switch (op) {
            case 1 -> StatusProjeto.PLANEJADO;
            case 2 -> StatusProjeto.EM_ANDAMENTO;
            case 3 -> StatusProjeto.CONCLUIDO;
            case 4 -> StatusProjeto.CANCELADO;
            default -> {
                System.out.println("Inválido, assumindo PLANEJADO.");
                yield StatusProjeto.PLANEJADO;
            }
        };
    }

    private static void menuEquipes() {
        boolean back = false;
        while (!back) {
            System.out.println("\n" + "-".repeat(30));
            System.out.println("MENU EQUIPES");
            System.out.println("-".repeat(30));
            System.out.println("1. Cadastrar Equipe");
            System.out.println("2. Listar Equipes");
            System.out.println("3. Adicionar Membro");
            System.out.println("4. Remover Membro");
            System.out.println("5. Associar Equipe ao Projeto");
            System.out.println("6. Mostrar Membros");
            System.out.println("0. Voltar");
            System.out.println("-".repeat(30));
            int opcao = lerInteiro("Escolha uma opção: ");
            switch (opcao) {
                case 1 -> cadastrarEquipe();
                case 2 -> listarEquipes();
                case 3 -> adicionarMembroEquipe();
                case 4 -> removerMembroEquipe();
                case 5 -> associarEquipeAoProjeto();
                case 6 -> mostrarMembrosEquipe();
                case 0 -> back = true;
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private static void cadastrarEquipe() {
        try {
            System.out.println("\nCadastrar Equipe:");
            String nome = lerString("Nome: ");
            String descricao = lerString("Descrição: ");
            Equipe equipe = new Equipe(nome, descricao);
            equipeService.cadastrar(equipe);
            System.out.println("Equipe cadastrada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void listarEquipes() {
        List<Equipe> equipes = equipeService.listar();
        if (equipes.isEmpty()) {
            System.out.println("Nenhuma equipe cadastrada.");
        } else {
            System.out.println("\nEquipes:");
            for (Equipe e : equipes) {
                System.out.println(e);
            }
        }
    }

    private static void adicionarMembroEquipe() {
        try {
            String nomeEquipe = lerString("Nome da equipe: ");
            Usuario usuario = escolherUsuario("Usuário a adicionar: ");
            equipeService.adicionarMembro(nomeEquipe, usuario);
            System.out.println("Membro adicionado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void removerMembroEquipe() {
        try {
            String nomeEquipe = lerString("Nome da equipe: ");
            Usuario usuario = escolherUsuario("Usuário a remover: ");
            equipeService.removerMembro(nomeEquipe, usuario);
            System.out.println("Membro removido com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void associarEquipeAoProjeto() {
        try {
            String nomeEquipe = lerString("Nome da equipe: ");
            Projeto projeto = escolherProjeto("Projeto a associar: ");
            equipeService.associarAoProjeto(nomeEquipe, projeto);
            System.out.println("Equipe associada ao projeto com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void mostrarMembrosEquipe() {
        String nomeEquipe = lerString("Nome da equipe: ");
        List<Usuario> membros = equipeService.mostrarMembros(nomeEquipe);
        if (membros.isEmpty()) {
            System.out.println("Equipe não encontrada ou sem membros.");
        } else {
            System.out.println("Membros:");
            for (Usuario u : membros) {
                System.out.println(u);
            }
        }
    }

    private static void menuTarefas() {
        boolean back = false;
        while (!back) {
            System.out.println("\n" + "-".repeat(30));
            System.out.println("MENU TAREFAS");
            System.out.println("-".repeat(30));
            System.out.println("1. Criar Tarefa");
            System.out.println("2. Listar Tarefas");
            System.out.println("3. Alterar Status");
            System.out.println("4. Buscar por Projeto");
            System.out.println("5. Buscar por Responsável");
            System.out.println("0. Voltar");
            System.out.println("-".repeat(30));
            int opcao = lerInteiro("Escolha uma opção: ");
            switch (opcao) {
                case 1 -> criarTarefa();
                case 2 -> listarTarefas();
                case 3 -> alterarStatusTarefa();
                case 4 -> buscarTarefasPorProjeto();
                case 5 -> buscarTarefasPorResponsavel();
                case 0 -> back = true;
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private static void criarTarefa() {
        try {
            System.out.println("\nCriar Tarefa:");
            String nome = lerString("Nome: ");
            String descricao = lerString("Descrição: ");
            Usuario responsavel = escolherUsuario("Responsável: ");
            Projeto projeto = escolherProjeto("Projeto relacionado: ");
            LocalDate dataLimite = lerData("Data limite (dd/MM/yyyy): ");
            StatusTarefa status = StatusTarefa.PENDENTE;
            Tarefa tarefa = new Tarefa(nome, descricao, responsavel, projeto, dataLimite, status);
            tarefaService.criar(tarefa);
            System.out.println("Tarefa criada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void listarTarefas() {
        List<Tarefa> tarefas = tarefaService.listar();
        if (tarefas.isEmpty()) {
            System.out.println("Nenhuma tarefa cadastrada.");
        } else {
            System.out.println("\nTarefas:");
            for (Tarefa t : tarefas) {
                System.out.println(t);
            }
        }
    }

    private static void alterarStatusTarefa() {
        try {
            String nome = lerString("Nome da tarefa: ");
            StatusTarefa status = escolherStatusTarefa();
            tarefaService.alterarStatus(nome, status);
            System.out.println("Status alterado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void buscarTarefasPorProjeto() {
        Projeto projeto = escolherProjeto("Projeto: ");
        List<Tarefa> tarefas = tarefaService.buscarPorProjeto(projeto);
        if (tarefas.isEmpty()) {
            System.out.println("Nenhuma tarefa para este projeto.");
        } else {
            System.out.println("Tarefas:");
            for (Tarefa t : tarefas) {
                System.out.println(t);
            }
        }
    }

    private static void buscarTarefasPorResponsavel() {
        Usuario responsavel = escolherUsuario("Responsável: ");
        List<Tarefa> tarefas = tarefaService.buscarPorResponsavel(responsavel);
        if (tarefas.isEmpty()) {
            System.out.println("Nenhuma tarefa para este responsável.");
        } else {
            System.out.println("Tarefas:");
            for (Tarefa t : tarefas) {
                System.out.println(t);
            }
        }
    }

    private static StatusTarefa escolherStatusTarefa() {
        System.out.println("Status:");
        System.out.println("1. PENDENTE");
        System.out.println("2. EM_ANDAMENTO");
        System.out.println("3. CONCLUIDA");
        int op = lerInteiro("Escolha: ");
        return switch (op) {
            case 1 -> StatusTarefa.PENDENTE;
            case 2 -> StatusTarefa.EM_ANDAMENTO;
            case 3 -> StatusTarefa.CONCLUIDA;
            default -> {
                System.out.println("Inválido, assumindo PENDENTE.");
                yield StatusTarefa.PENDENTE;
            }
        };
    }

    private static void menuRelatorios() {
        boolean back = false;
        while (!back) {
            System.out.println("\n" + "-".repeat(30));
            System.out.println("MENU RELATÓRIOS");
            System.out.println("-".repeat(30));
            System.out.println("1. Quantidade de Usuários");
            System.out.println("2. Quantidade de Projetos");
            System.out.println("3. Quantidade de Equipes");
            System.out.println("4. Quantidade de Tarefas");
            System.out.println("5. Projetos Concluídos");
            System.out.println("6. Projetos em Andamento");
            System.out.println("7. Tarefas Pendentes");
            System.out.println("8. Usuários por Perfil");
            System.out.println("0. Voltar");
            System.out.println("-".repeat(30));
            int opcao = lerInteiro("Escolha uma opção: ");
            switch (opcao) {
                case 1 -> System.out.println("Quantidade de usuários: " + usuarioService.listar().size());
                case 2 -> System.out.println("Quantidade de projetos: " + projetoService.listar().size());
                case 3 -> System.out.println("Quantidade de equipes: " + equipeService.listar().size());
                case 4 -> System.out.println("Quantidade de tarefas: " + tarefaService.listar().size());
                case 5 -> {
                    long count = projetoService.listar().stream().filter(p -> p.getStatus() == StatusProjeto.CONCLUIDO).count();
                    System.out.println("Projetos concluídos: " + count);
                }
                case 6 -> {
                    long count = projetoService.listar().stream().filter(p -> p.getStatus() == StatusProjeto.EM_ANDAMENTO).count();
                    System.out.println("Projetos em andamento: " + count);
                }
                case 7 -> {
                    long count = tarefaService.listar().stream().filter(t -> t.getStatus() == StatusTarefa.PENDENTE).count();
                    System.out.println("Tarefas pendentes: " + count);
                }
                case 8 -> {
                    System.out.println("Usuários por perfil:");
                    for (Perfil p : Perfil.values()) {
                        long count = usuarioService.listarPorPerfil(p).size();
                        System.out.println(p + ": " + count);
                    }
                }
                case 0 -> back = true;
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    // Métodos auxiliares
    private static String lerString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int lerInteiro(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int valor = Integer.parseInt(scanner.nextLine().trim());
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número.");
            }
        }
    }

    private static LocalDate lerData(String prompt) {
        while (true) {
            try {
                String input = lerString(prompt);
                return LocalDate.parse(input, DATE_FORMAT);
            } catch (DateTimeParseException e) {
                System.out.println("Data inválida. Use o formato dd/MM/yyyy.");
            }
        }
    }

    private static LocalDate lerDataOpcional(String prompt) {
        String input = lerString(prompt);
        if (input.isEmpty()) return null;
        try {
            return LocalDate.parse(input, DATE_FORMAT);
        } catch (DateTimeParseException e) {
            System.out.println("Data inválida, mantendo atual.");
            return null;
        }
    }

    private static Usuario escolherUsuario(String prompt) {
        System.out.println(prompt);
        List<Usuario> usuarios = usuarioService.listar();
        for (int i = 0; i < usuarios.size(); i++) {
            System.out.println((i + 1) + ". " + usuarios.get(i).getNome());
        }
        int idx = lerInteiro("Escolha o número: ") - 1;
        if (idx >= 0 && idx < usuarios.size()) {
            return usuarios.get(idx);
        } else {
            System.out.println("Inválido, assumindo primeiro.");
            return usuarios.isEmpty() ? null : usuarios.get(0);
        }
    }

    private static Usuario escolherUsuarioOpcional(String prompt) {
        String resp = lerString(prompt + " (enter para nenhum): ");
        if (resp.isEmpty()) return null;
        return escolherUsuario(prompt);
    }

    private static Equipe escolherEquipe(String prompt) {
        System.out.println(prompt);
        List<Equipe> equipes = equipeService.listar();
        for (int i = 0; i < equipes.size(); i++) {
            System.out.println((i + 1) + ". " + equipes.get(i).getNome());
        }
        int idx = lerInteiro("Escolha o número: ") - 1;
        if (idx >= 0 && idx < equipes.size()) {
            return equipes.get(idx);
        } else {
            System.out.println("Inválido, assumindo primeira.");
            return equipes.isEmpty() ? null : equipes.get(0);
        }
    }

    private static Equipe escolherEquipeOpcional(String prompt) {
        String resp = lerString(prompt + " (enter para nenhuma): ");
        if (resp.isEmpty()) return null;
        return escolherEquipe(prompt);
    }

    private static Projeto escolherProjeto(String prompt) {
        System.out.println(prompt);
        List<Projeto> projetos = projetoService.listar();
        for (int i = 0; i < projetos.size(); i++) {
            System.out.println((i + 1) + ". " + projetos.get(i).getNome());
        }
        int idx = lerInteiro("Escolha o número: ") - 1;
        if (idx >= 0 && idx < projetos.size()) {
            return projetos.get(idx);
        } else {
            System.out.println("Inválido, assumindo primeiro.");
            return projetos.isEmpty() ? null : projetos.get(0);
        }
    }
}