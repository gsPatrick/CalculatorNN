package model;
import dao.Conexao;
import dao.UsuarioDAO;
import java.sql.PreparedStatement;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Scanner;
import model.UsuarioCadastroLogin;
public class MenuPrincipal {
    Scanner scanner = new Scanner(System.in); // Scanner
    private int idUsuarioLogado;
    private String emailUsuarioLogado;
    private UsuarioDAO usuarioDAO;
    private Double basalValue; // Variável para armazenar o valor basal


    public MenuPrincipal(int idUsuarioLogado, String emailUsuarioLogado) {
        this.idUsuarioLogado = idUsuarioLogado;
        this.emailUsuarioLogado = emailUsuarioLogado;
        this.usuarioDAO = new UsuarioDAO(); // Crie uma instância de UsuarioDAO
    }

    public void dados() {
        Scanner scanner = new Scanner(System.in);
        String sexo = coletarSexo(scanner);
        int idade = coletarIdade(scanner);
        double peso = coletarPeso(scanner);
        double altura = coletarAltura(scanner);

        System.out.println("Dados coletados com sucesso");

        UsuarioDAO usuarioDAO = new UsuarioDAO(); // Crie uma instância da classe UsuarioDAO
        usuarioDAO.inserirDadosSaude(idUsuarioLogado, sexo, idade, peso, altura);
    }

    private String coletarSexo(Scanner scanner) {
        while (true) {
            System.out.println("Qual é o seu sexo?");
            System.out.println("1 - Masculino");
            System.out.println("2 - Feminino");

            try {
                int escolha = Integer.parseInt(scanner.nextLine());
                if (escolha == 1) {
                    return "Masculino";
                } else if (escolha == 2) {
                    return "Feminino";
                } else {
                    System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private int coletarIdade(Scanner scanner) {
        while (true) {
            System.out.print("Qual é a sua idade? ");
            try {
                int idade = Integer.parseInt(scanner.nextLine());
                return idade;
            } catch (NumberFormatException e) {
                System.out.println("Idade inválida. Tente novamente.");
            }
        }
    }

    private double coletarPeso(Scanner scanner) {
        while (true) {
            System.out.print("Qual é o seu peso? ");
            try {
                double peso = Double.parseDouble(scanner.nextLine());
                return peso;
            } catch (NumberFormatException e) {
                System.out.println("Peso inválido. Tente novamente.");
            }
        }
    }

    private double coletarAltura(Scanner scanner) {
        while (true) {
            System.out.print("Qual é a sua altura? ");
            try {
                double altura = Double.parseDouble(scanner.nextLine());
                return altura;
            } catch (NumberFormatException e) {
                System.out.println("Altura inválida. Tente novamente.");
            }
        }

        // Insira os dados de saúde usando a instância da classe UsuarioDAO
    }


    public void atualizarSexo() {

        System.out.print("Qual é o seu novo sexo?");
        System.out.println("1 - Masculino");
        System.out.println("2 - Feminino");
        int escolha = Integer.parseInt(scanner.nextLine());
        if (escolha == 1) {
            usuarioDAO.atualizarSexo(idUsuarioLogado, "Masculino");
            System.out.println("Sexo atualizado com sucesso.");
        } else if (escolha == 2) {
            usuarioDAO.atualizarSexo(idUsuarioLogado, "Feminino");
            //  System.out.println("Sexo atualizado com sucesso.");
        } else {
            System.out.println("Opção inválida. Sexo não foi atualizado.");
        }
    }

    public void atualizarIdade() {
        System.out.print("Qual é a sua nova idade? ");
        int novaIdade = Integer.parseInt(scanner.nextLine());
        usuarioDAO.atualizarIdade(idUsuarioLogado, novaIdade);
        //System.out.println("Idade atualizada com sucesso.");
    }

    public void atualizarPeso() {
        System.out.print("Qual é o seu novo peso? ");
        double novoPeso = Double.parseDouble(scanner.nextLine());
        usuarioDAO.atualizarPeso(idUsuarioLogado, novoPeso);
        //  System.out.println("Peso atualizado com sucesso.");
    }

    public void atualizarAltura() {
        System.out.print("Qual é a sua nova altura? ");
        double novaAltura = Double.parseDouble(scanner.nextLine());
        usuarioDAO.atualizarAltura(idUsuarioLogado, novaAltura);
        //    System.out.println("Altura atualizada com sucesso.");
    }

    public void atualizarGeral() {

            System.out.print("Qual é o seu novo sexo? (1 - Masculino / 2 - Feminino): ");
            int escolhaSexo = Integer.parseInt(scanner.nextLine());
            String novoSexo = escolhaSexo == 1 ? "Masculino" : "Feminino";

            System.out.print("Qual é a sua nova idade? ");
            int novaIdade = Integer.parseInt(scanner.nextLine());

            System.out.print("Qual é o seu novo peso? ");
            double novoPeso = Double.parseDouble(scanner.nextLine());

            System.out.print("Qual é a sua nova altura? ");
            double novaAltura = Double.parseDouble(scanner.nextLine());

            usuarioDAO.atualizarSexo(idUsuarioLogado, novoSexo);
            usuarioDAO.atualizarIdade(idUsuarioLogado, novaIdade);
            usuarioDAO.atualizarPeso(idUsuarioLogado, novoPeso);
            usuarioDAO.atualizarAltura(idUsuarioLogado, novaAltura);

            System.out.println("Dados atualizados com sucesso");
        }


    public double calcularBasal(String sexo, int idade, double peso, double altura) {
        double basalValue = 0.0;

        // Defina os fatores com base no nível de atividade física
        double fatorAtividade = 1.2; // Nível padrão: Sedentário

        try {
            System.out.println("Escolha o seu nível de atividade física:");
            System.out.println("1 - Sedentário: 1.2");
            System.out.println("2 - Pouco ativo: 1.5");
            System.out.println("3 - Ativo: 1.8");
            System.out.println("4 - Muito ativo: 2.1");

            int escolhaNivelAtividade = Integer.parseInt(scanner.nextLine());

            switch (escolhaNivelAtividade) {
                case 1:
                    fatorAtividade = 1.2;
                    break;
                case 2:
                    fatorAtividade = 1.5;
                    break;
                case 3:
                    fatorAtividade = 1.8;
                    break;
                case 4:
                    fatorAtividade = 2.1;
                    break;
                default:
                    System.out.println("Opção inválida. Usando o nível de atividade padrão (Sedentário).");
                    break;
            }
        } catch (NumberFormatException e) {
            System.out.println("Opção inválida. Usando o nível de atividade padrão (Sedentário).");
        }

        // Realize o cálculo do Mifflin-St Jeor com base na resposta do usuário
        if (sexo.equalsIgnoreCase("Masculino")) {
            basalValue = (10 * peso + 6.25 * altura - 5 * idade) + 5;
        } else if (sexo.equalsIgnoreCase("Feminino")) {
            basalValue = (10 * peso + 6.25 * altura - 5 * idade) - 151;
        }

        // Aplique o fator de atividade
        basalValue *= fatorAtividade;

        this.basalValue = basalValue;

        // Armazene o valor basal no banco de dados
        usuarioDAO.inserirBasal(idUsuarioLogado, basalValue);

        return basalValue;
    }


    public void calculadoraBasal() {
        // Recupere o basal_value do banco de dados
        Double basalValue = usuarioDAO.obterBasalValue(idUsuarioLogado);

        if (basalValue == null) {
            String sexo = coletarSexo(scanner);
            int idade = coletarIdade(scanner);
            double peso = coletarPeso(scanner);
            double altura = coletarAltura(scanner);

            double novoBasalValue = calcularBasal(sexo, idade, peso, altura);

            // Aqui, se o valor basal não existir no banco de dados, você pode inseri-lo
            usuarioDAO.inserirBasal(idUsuarioLogado, novoBasalValue);
        } else {
            boolean sair = false;

            while (!sair) {
                System.out.println("Escolha uma opção:");
                System.out.println("1 - Ver Basal");
                System.out.println("2 - Refazer Basal");
                System.out.println("0 - Voltar");

                try {
                    int opcao = Integer.parseInt(scanner.nextLine());

                    switch (opcao) {
                        case 1:
                            // Implemente a opção para "Ver Basal" aqui
                            System.out.println("Basal Value: " + basalValue);
                            break;
                        case 2:
                            // Implemente a opção para "Refazer Basal" aqui
                            // Certifique-se de que o usuário possa refazer o cálculo do Basal se desejar.
                            String novoSexo = coletarSexo(scanner);
                            int novaIdade = coletarIdade(scanner);
                            double novoPeso = coletarPeso(scanner);
                            double novaAltura = coletarAltura(scanner);

                            double novoBasal = calcularBasal(novoSexo, novaIdade, novoPeso, novaAltura);
                            // Aqui, você atualiza o valor basal existente no banco de dados
                            usuarioDAO.atualizarBasal(idUsuarioLogado, novoBasal);
                            System.out.println("Basal recalculado com sucesso.");
                            break;
                        case 0:
                            dentroMenuFerramentas();
                            break;
                        default:
                            System.out.println("Opção inválida. Escolha 1 para 'Ver Basal', 2 para 'Refazer Basal' ou 0 para 'Sair'.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Erro: Insira um número válido (1, 2 ou 0).");
                }
            }
        }
        }


    public void dentroMenuFerramentas() {

        boolean sair = false;

        while (!sair) {
            System.out.println("Escolha uma opção:");
            System.out.println("1 - Calculadora Basal");
            System.out.println("2 - Divisor de Macronutrientes");
            System.out.println("3 - Dieta");
            System.out.println("0 - Voltar");

            try {
                int opcao = Integer.parseInt(scanner.nextLine());

                switch (opcao) {
                    case 1:
                        calculadoraBasal();
                        break;
                    case 2:
                        // Implemente a opção "Divisor de Macronutrientes" aqui
                        System.out.println("Opção 'Divisor de Macronutrientes' selecionada.");
                        break;
                    case 3:
                        // Implemente a opção "Dieta" aqui
                        System.out.println("Opção 'Dieta' selecionada.");
                        break;
                    case 0:
                        menuPrincipalOpcoes();
                        break;
                    default:
                        System.out.println("Opção inválida. Escolha um número de 1 a 4.");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Insira um número válido (1, 2, 3, ou 4).");
            }
        }
    }

        public void verDados() {

            String nome = usuarioDAO.obterNomeUsuario(idUsuarioLogado);
            String apelido = usuarioDAO.obterApelidoUsuario(idUsuarioLogado);
            String email = usuarioDAO.obterEmailUsuario(idUsuarioLogado);
            String sexo = usuarioDAO.obterSexoUsuario(idUsuarioLogado);
            int idade = usuarioDAO.obterIdadeUsuario(idUsuarioLogado);
            double peso = usuarioDAO.obterPesoUsuario(idUsuarioLogado);
            double altura = usuarioDAO.obterAlturaUsuario(idUsuarioLogado);
            Double basalValue = usuarioDAO.obterBasalValue(idUsuarioLogado);

            System.out.println("ID do usuário logado: " + idUsuarioLogado);
            System.out.println("Nome: " + nome);
            System.out.println("Apelido: " + (apelido != null ? apelido : "N/A"));
            System.out.println("Email: " + email);
            System.out.println("Sexo: " + (sexo != null ? sexo : "N/A"));
            System.out.println("Idade: " + (idade >= 0 ? idade : "N/A"));
            System.out.println("Peso: " + (peso >= 0 ? peso : "N/A"));
            System.out.println("Altura: " + (altura >= 0 ? altura : "N/A"));
            System.out.println("Basal Value: " + (basalValue != null ? basalValue : "N/A"));
            opcoesAtualizarDados();



        }

    public void voltarParaVerOsDados() {

        int escolha;
        while (true) {
            try {
                System.out.println("Escolha uma opção:");
                System.out.println("1 - Mudar outro dado");
                System.out.println("2 - Voltar");

                escolha = Integer.parseInt(scanner.nextLine());

                if (escolha == 1) {
                    mudarDadosManualmente();
                } else if (escolha == 2) {
                    verDados(); // Chama a função para ver os dados
                } else {
                    System.out.println("Opção inválida. Por favor, escolha uma opção válida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Por favor, digite 1 ou 2.");
            }
        }


    }

    public void opcoesAtualizarDados() {
        while (true) {
            System.out.println("Escolha uma opção:");
            System.out.println("1 - Mudar dados manualmente");
            System.out.println("0 - Voltar");

            try {
                int escolha = Integer.parseInt(scanner.nextLine());

                switch (escolha) {
                    case 1:
                        mudarDadosManualmente();
                        break;
                    case 0:
                        dentroPerfil();
                        return; // Encerra a função opcoesAtualizarDados
                    default:
                        System.out.println("Opção inválida. Por favor, escolha 1 ou 2.");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Por favor, digite 1 ou 2.");
            }
        }
    }



    public void mudarDadosManualmente() {

        int escolha;

        while (true) {
            try {
                System.out.println("Escolha o que deseja atualizar:");
                System.out.println("1 - Email");
                System.out.println("2 - Senha");
                System.out.println("3 - Apelido");
                System.out.println("4 - Sexo");
                System.out.println("5 - Idade");
                System.out.println("6 - Peso");
                System.out.println("7 - Altura");
                System.out.println("8 - Basal Value");
                System.out.println("0 - Voltar");

                escolha = Integer.parseInt(scanner.nextLine());

                if (escolha >= 0 && escolha <= 8) {
                    break; // Saia do loop se a escolha for válida
                } else {
                    System.out.println("Opção inválida. Por favor, escolha uma opção válida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Por favor, digite um número válido (0 a 8).");
            }
        }

        switch (escolha) {
            case 0:
                verDados();
                break;
            case 1:
                System.out.print("Novo email: ");
                String novoEmail = scanner.nextLine();
                usuarioDAO.atualizarEmail(idUsuarioLogado, novoEmail);
                System.out.println("Email atualizado com sucesso.");
                voltarParaVerOsDados();
                break;
            case 2:
                System.out.print("Nova senha: ");
                String novaSenha = scanner.nextLine();
                usuarioDAO.atualizarSenha(idUsuarioLogado, novaSenha);
                System.out.println("Senha atualizada com sucesso.");
                voltarParaVerOsDados();
                break;
            case 3:
                System.out.print("Novo apelido: ");
                String novoApelido = scanner.nextLine();
                usuarioDAO.atualizarApelido(idUsuarioLogado, novoApelido);
                System.out.println("Apelido atualizado com sucesso.");
                voltarParaVerOsDados();
                break;
            case 4:
                System.out.println("Qual é o seu novo sexo?");
                System.out.println("1 - Masculino");
                System.out.println("2 - Feminino");
                int escolhaNovoSexo = Integer.parseInt(scanner.nextLine());
                if (escolhaNovoSexo == 1) {
                    usuarioDAO.atualizarSexo(idUsuarioLogado, "Masculino");
                    System.out.println("Sexo atualizado com sucesso.");
                } else if (escolhaNovoSexo == 2) {
                    usuarioDAO.atualizarSexo(idUsuarioLogado, "Feminino");
                    //  System.out.println("Sexo atualizado com sucesso.");
                } else {
                    System.out.println("Opção inválida. Sexo não foi atualizado.");
                }

                break;
            case 5:
                System.out.print("Nova idade: ");
                int novaIdade = Integer.parseInt(scanner.nextLine());
                usuarioDAO.atualizarIdade(idUsuarioLogado, novaIdade);
                System.out.println("Idade atualizada com sucesso.");
                voltarParaVerOsDados();
                break;
            case 6:
                System.out.print("Novo peso: ");
                double novoPeso = Double.parseDouble(scanner.nextLine());
                usuarioDAO.atualizarPeso(idUsuarioLogado, novoPeso);
                System.out.println("Peso atualizado com sucesso.");
                voltarParaVerOsDados();
                break;
            case 7:
                System.out.print("Nova altura: ");
                double novaAltura = Double.parseDouble(scanner.nextLine());
                usuarioDAO.atualizarAltura(idUsuarioLogado, novaAltura);
                System.out.println("Altura atualizada com sucesso.");
                voltarParaVerOsDados();
                break;
            case 8:
                System.out.print("Novo Basal Value: ");
                double novoBasalValue = Double.parseDouble(scanner.nextLine());
                usuarioDAO.atualizarBasal(idUsuarioLogado, novoBasalValue);
                System.out.println("Basal Value atualizado com sucesso.");
                voltarParaVerOsDados();
                break;
            default:
                System.out.println("Opção inválida.");
        }

    }


        public void dentroPerfil() {
        int opcao;

        do {
            try {
                System.out.println("Escolha uma opção:");
                System.out.println("1 - Ver dados");
                System.out.println("2 - Refazer dados");
                System.out.println("3 - Voltar");
                System.out.print("Digite o número da opção desejada: ");
                opcao = Integer.parseInt(scanner.nextLine());

                switch (opcao) {
                    case 1:

                        verDados();

                        if (basalValue != null) {
                            System.out.println("Basal: " + basalValue);
                        } else {
                            System.out.println("Basal: Dado não encontrado");
                        }

                        break;
                    case 2:
                        atualizarGeral();
                        break;
                    case 3:
                        menuPrincipalOpcoes();
                        break;
                    default:
                        System.out.println("Opção inválida. Por favor, escolha 1, 2 ou 3.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Por favor, digite um número válido (1, 2 ou 3).");
                opcao = 0; // Defina opcao como 0 para continuar o loop.
            }
        } while (opcao < 1 || opcao > 3);

            dentroPerfil();
    }



    public void menuPrincipalOpcoes() {

        while (true) {
            System.out.println("Escolha uma opção:");
            System.out.println("1 - Perfil");
            System.out.println("2 - Ferramentas");
            System.out.println("3 - Social");
            System.out.println("0 - Sair");

            try {
                int opcao = Integer.parseInt(scanner.nextLine());

                switch (opcao) {
                    case 1:
                        dentroPerfil();
                        System.out.println("Opção de Perfil selecionada");
                        break;
                    case 2:
                        dentroMenuFerramentas();
                        System.out.println("Opção de Ferramentas selecionada");
                        break;
                    case 3:
                        // Função futura aqui para Social
                        System.out.println("Opção de Social selecionada");
                        break;
                    case 0:
                        System.out.println("Saindo do programa.");
                        System.exit(0);
                    default:
                        System.out.println("Opção inválida. Escolha um número de 1 a 3 ou 0 para sair.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Insira um número válido.");
            }
        }
    }
}

