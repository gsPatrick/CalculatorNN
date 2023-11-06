package model;

import dao.UsuarioDAO;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import model.MenuPrincipal;

public class UsuarioCadastroLogin {
    private Scanner scanner = new Scanner(System.in);
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public void cadastrarUsuario(Usuario usuario) {
        String email = obterEmailValido();
        String senha = obterSenhaValida();
        String nome = obterNomeValido();
        String apelido = obterApelido();

        usuario.setEmail(email);
        usuario.setSenha(senha);
        usuario.setNome(nome);
        usuario.setApelido(apelido);

        usuarioDAO.adicionarUsuario(usuario);

        if (desejaFazerLogin()) {
            fazerLogin();
        } else {
            System.out.println("Saindo do ProjetoNN.");
            System.exit(0);
        }
    }

    private String obterEmailValido() {
        String emailPattern = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailPattern);

        while (true) {
            System.out.print("Qual é o seu email: ");
            String email = scanner.nextLine();

            Matcher matcher = pattern.matcher(email);
            if (matcher.matches()) {
                if (usuarioDAO.emailJaCadastrado(email)) {
                    System.out.println("Este email já está cadastrado. Tente novamente com outro email.");
                } else {
                    return email;
                }
            } else {
                System.out.println("Email em formato inválido. Tente novamente.");
            }
        }
    }

    private String obterSenhaValida() {
        String senhaPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[^a-zA-Z0-9]).{8,}$";
        Pattern senhaRegex = Pattern.compile(senhaPattern);

        while (true) {
            System.out.print("Qual é a sua senha: ");
            String senha = scanner.nextLine();

            Matcher matcher = senhaRegex.matcher(senha);
            if (matcher.matches()) {
                return senha;
            } else {
                System.out.println("Senha inválida. Tente novamente.");
            }
        }
    }

    private String obterNomeValido() {
        while (true) {
            System.out.print("Qual é o seu nome completo: ");
            String nome = scanner.nextLine();

            if (nome.matches("^[A-Za-z\\s]+$")) {
                return nome;
            } else {
                System.out.println("Nome em formato inválido. Tente novamente.");
            }
        }
    }

    private String obterApelido() {
        System.out.println("Deseja ter um apelido?");
        System.out.println("1 - Sim");
        System.out.println("2 - Não");

        int escolha = 0;
        try {
            escolha = Integer.parseInt(scanner.nextLine());
            if (escolha == 1) {
                while (true) {
                    System.out.print("Qual apelido você deseja ter: ");
                    String apelido = scanner.nextLine();
                    if (apelido.length() <= 16) {
                        if (usuarioDAO.apelidoJaCadastrado(apelido)) {
                            System.out.println("Este apelido já está cadastrado. Tente novamente com outro apelido.");
                        } else {
                            return apelido;
                        }
                    } else {
                        System.out.println("Apelido muito longo. Tente novamente.");
                    }
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Opção inválida. Não terá apelido.");
        }

        return null; // Sem apelido
    }


    private boolean desejaFazerLogin() {
        while (true) {
            System.out.println("Deseja:");
            System.out.println("1 - Login");
            System.out.println("2 - Sair");

            try {
                int escolha = Integer.parseInt(scanner.nextLine());
                if (escolha == 1) {
                    return true;
                } else if (escolha == 2) {
                    return false;
                } else {
                    System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    public void fazerLogin() {
        System.out.print("Digite o email ou apelido: ");
        String emailOuApelido = scanner.nextLine();

        int tentativasRestantes = 3; // Número de tentativas permitidas para a senha

        for (; tentativasRestantes > 0; tentativasRestantes--) {
            System.out.print("Digite a senha: ");
            String senha = scanner.nextLine();

            if (usuarioDAO.autenticar(emailOuApelido, senha)) {
                int idUsuarioLogado = usuarioDAO.obterIdUsuarioPorEmail(emailOuApelido);
                String emailUsuarioLogado = emailOuApelido;

                if (emailOuApelido.contains("@")) {
                    emailUsuarioLogado = emailOuApelido;
                }

                System.out.println("Login bem-sucedido");
                System.out.println("ID do usuário logado: " + idUsuarioLogado);
                System.out.println("Email do usuário logado: " + emailUsuarioLogado);

                MenuPrincipal menu = new MenuPrincipal(idUsuarioLogado, emailUsuarioLogado);
                menu.menuPrincipalOpcoes();

                // Aqui você pode executar outras ações com o usuário logado.
                return; // Sai da função após o login bem-sucedido.
            } else {
                System.out.println("Credenciais inválidas. Tentativas restantes: " + (tentativasRestantes - 1));
                if (tentativasRestantes > 1) {
                    System.out.println("Tente novamente.");
                }
            }
        }

        System.out.println("Você excedeu o número de tentativas permitidas. Tente novamente mais tarde.");
        scanner.close();
    }
}
