import java.util.InputMismatchException;
import java.util.Scanner;
import dao.UsuarioDAO;
import dao.Conexao;
import model.Usuario;
import model.UsuarioCadastroLogin;

public class Main {

    public static void main(String[] args) {

        UsuarioCadastroLogin cadastroLogin = new UsuarioCadastroLogin(); // Cria instancia do objeto UsuaroCadastroLogin
        Usuario usuario = new Usuario(); // Crie um objeto Usuario
        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        while (true) {
            System.out.println("Escolha uma opção:");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Fazer Login");
            System.out.println("3 - Sair");

            try {
                choice = Integer.parseInt(scanner.nextLine());

                if (choice < 1 || choice > 3) {
                    System.out.println("Opção inválida. Tente novamente.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Tente novamente.");
            }
        }

        switch (choice) {
            case 1:
                cadastroLogin.cadastrarUsuario(usuario); // Função para cadastrar
                break;
            case 2:
                cadastroLogin.fazerLogin(); // Função para fazer login
                break;
            case 3:
                System.out.println("Saindo do ProjetoNN.");
                System.exit(0);
                break;
        }
    }
}
