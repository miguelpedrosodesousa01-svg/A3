# Sistema de Gestão de Projetos e Equipes

Este é um sistema completo de gestão de projetos e equipes desenvolvido em Java 17, utilizando programação orientada a objetos, console para interface e persistência em arquivos TXT.

## Funcionalidades

- **Usuários**: Cadastro, listagem, busca, edição, remoção e listagem por perfil (Administrador, Gerente, Colaborador).
- **Projetos**: Cadastro, listagem, busca, edição, remoção, alteração de status e associação com gerente e equipe.
- **Equipes**: Cadastro, listagem, adição/remoção de membros e associação a projetos.
- **Tarefas**: Criação, listagem, alteração de status, busca por projeto ou responsável.
- **Relatórios**: Estatísticas sobre usuários, projetos, equipes, tarefas, status, etc.

## Estrutura do Projeto

- `src/main/Main.java`: Classe principal com menus e interface.
- `src/model/`: Classes de modelo (Usuario, Projeto, Equipe, Tarefa, enums).
- `src/service/`: Serviços para lógica de negócio.
- `src/util/ArquivoUtil.java`: Utilitários para persistência.

## Como Executar

1. Certifique-se de ter Java 17+ instalado.
2. Navegue até a pasta `src`.
3. Compile: `javac main/Main.java model/*.java service/*.java util/*.java`
4. Execute: `java main.Main`

## Persistência

Os dados são salvos automaticamente em arquivos TXT na raiz do projeto:
- `usuarios.txt`
- `projetos.txt`
- `equipes.txt`
- `tarefas.txt`

## Tratamento de Exceções

O sistema trata entradas inválidas, campos vazios, duplicatas e erros de arquivo.

## Autor

Sistema desenvolvido para fins acadêmicos.