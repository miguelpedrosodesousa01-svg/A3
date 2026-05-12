package util;

import model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe utilitária para salvar e carregar dados em arquivos TXT.
 */
public class ArquivoUtil {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Salva a lista de usuários em arquivo.
     * @param usuarios Lista de usuários.
     */
    public static void salvarUsuarios(List<Usuario> usuarios) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("usuarios.txt"))) {
            for (Usuario u : usuarios) {
                writer.println(u.getNome() + ";" + u.getCpf() + ";" + u.getEmail() + ";" + u.getCargo() + ";" + u.getLogin() + ";" + u.getSenha() + ";" + u.getPerfil());
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar usuários: " + e.getMessage());
        }
    }

    /**
     * Carrega a lista de usuários do arquivo.
     * @return Lista de usuários.
     */
    public static List<Usuario> carregarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("usuarios.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 7) {
                    Usuario u = new Usuario(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], Perfil.valueOf(parts[6]));
                    usuarios.add(u);
                }
            }
        } catch (IOException e) {
            // Arquivo não existe ou erro, retorna lista vazia
        }
        return usuarios;
    }

    /**
     * Salva a lista de projetos em arquivo.
     * @param projetos Lista de projetos.
     * @param usuarios Lista de usuários para referência.
     * @param equipes Lista de equipes para referência.
     */
    public static void salvarProjetos(List<Projeto> projetos, List<Usuario> usuarios, List<Equipe> equipes) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("projetos.txt"))) {
            for (Projeto p : projetos) {
                String gerenteCpf = p.getGerente() != null ? p.getGerente().getCpf() : "";
                String equipeNome = p.getEquipe() != null ? p.getEquipe().getNome() : "";
                writer.println(p.getNome() + ";" + p.getDescricao() + ";" + p.getDataInicio().format(DATE_FORMAT) + ";" +
                        p.getDataTerminoPrevista().format(DATE_FORMAT) + ";" + p.getStatus() + ";" + gerenteCpf + ";" + equipeNome);
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar projetos: " + e.getMessage());
        }
    }

    /**
     * Carrega a lista de projetos do arquivo.
     * @param usuarios Lista de usuários para associar gerentes.
     * @param equipes Lista de equipes para associar equipes.
     * @return Lista de projetos.
     */
    public static List<Projeto> carregarProjetos(List<Usuario> usuarios, List<Equipe> equipes) {
        List<Projeto> projetos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("projetos.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 7) {
                    Usuario gerente = null;
                    if (!parts[5].isEmpty()) {
                        for (Usuario u : usuarios) {
                            if (u.getCpf().equals(parts[5])) {
                                gerente = u;
                                break;
                            }
                        }
                    }
                    Equipe equipe = null;
                    if (!parts[6].isEmpty()) {
                        for (Equipe e : equipes) {
                            if (e.getNome().equals(parts[6])) {
                                equipe = e;
                                break;
                            }
                        }
                    }
                    Projeto p = new Projeto(parts[0], parts[1], LocalDate.parse(parts[2], DATE_FORMAT),
                            LocalDate.parse(parts[3], DATE_FORMAT), StatusProjeto.valueOf(parts[4]), gerente, equipe);
                    projetos.add(p);
                }
            }
        } catch (IOException e) {
            // Arquivo não existe
        }
        return projetos;
    }

    /**
     * Salva a lista de equipes em arquivo.
     * @param equipes Lista de equipes.
     * @param usuarios Lista de usuários para referência.
     */
    public static void salvarEquipes(List<Equipe> equipes, List<Usuario> usuarios) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("equipes.txt"))) {
            for (Equipe e : equipes) {
                StringBuilder membros = new StringBuilder();
                for (Usuario u : e.getMembros()) {
                    if (membros.length() > 0) membros.append(",");
                    membros.append(u.getCpf());
                }
                writer.println(e.getNome() + ";" + e.getDescricao() + ";" + membros.toString());
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar equipes: " + e.getMessage());
        }
    }

    /**
     * Carrega a lista de equipes do arquivo.
     * @param usuarios Lista de usuários para associar membros.
     * @return Lista de equipes.
     */
    public static List<Equipe> carregarEquipes(List<Usuario> usuarios) {
        List<Equipe> equipes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("equipes.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= 2) {
                    Equipe e = new Equipe(parts[0], parts[1]);
                    if (parts.length == 3 && !parts[2].isEmpty()) {
                        String[] cpfs = parts[2].split(",");
                        for (String cpf : cpfs) {
                            for (Usuario u : usuarios) {
                                if (u.getCpf().equals(cpf)) {
                                    e.adicionarMembro(u);
                                    break;
                                }
                            }
                        }
                    }
                    equipes.add(e);
                }
            }
        } catch (IOException e) {
            // Arquivo não existe
        }
        return equipes;
    }

    /**
     * Salva a lista de tarefas em arquivo.
     * @param tarefas Lista de tarefas.
     * @param usuarios Lista de usuários para referência.
     * @param projetos Lista de projetos para referência.
     */
    public static void salvarTarefas(List<Tarefa> tarefas, List<Usuario> usuarios, List<Projeto> projetos) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("tarefas.txt"))) {
            for (Tarefa t : tarefas) {
                String respCpf = t.getResponsavel() != null ? t.getResponsavel().getCpf() : "";
                String projNome = t.getProjeto() != null ? t.getProjeto().getNome() : "";
                writer.println(t.getNome() + ";" + t.getDescricao() + ";" + respCpf + ";" + projNome + ";" +
                        t.getDataLimite().format(DATE_FORMAT) + ";" + t.getStatus());
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar tarefas: " + e.getMessage());
        }
    }

    /**
     * Carrega a lista de tarefas do arquivo.
     * @param usuarios Lista de usuários para associar responsáveis.
     * @param projetos Lista de projetos para associar projetos.
     * @return Lista de tarefas.
     */
    public static List<Tarefa> carregarTarefas(List<Usuario> usuarios, List<Projeto> projetos) {
        List<Tarefa> tarefas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("tarefas.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 6) {
                    Usuario responsavel = null;
                    if (!parts[2].isEmpty()) {
                        for (Usuario u : usuarios) {
                            if (u.getCpf().equals(parts[2])) {
                                responsavel = u;
                                break;
                            }
                        }
                    }
                    Projeto projeto = null;
                    if (!parts[3].isEmpty()) {
                        for (Projeto p : projetos) {
                            if (p.getNome().equals(parts[3])) {
                                projeto = p;
                                break;
                            }
                        }
                    }
                    Tarefa t = new Tarefa(parts[0], parts[1], responsavel, projeto, LocalDate.parse(parts[4], DATE_FORMAT), StatusTarefa.valueOf(parts[5]));
                    tarefas.add(t);
                }
            }
        } catch (IOException e) {
            // Arquivo não existe
        }
        return tarefas;
    }
}