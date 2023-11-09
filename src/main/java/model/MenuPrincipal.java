package model;
import dao.Conexao;
import dao.UsuarioDAO;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Scanner;
import model.UsuarioCadastroLogin;
import java.util.InputMismatchException;

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
        this.usuarioDAO = usuarioDAO;

    }

    public MenuPrincipal(UsuarioDAO usuarioDAO, Scanner scanner) {
        this.usuarioDAO = usuarioDAO;
        this.scanner = scanner;
    }

    // Funções relacionadas a coleta:

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

    //Funções relacionadas a atualização:

    public void atualizarEmail() {
        // A mesma lógica que está na classe UsuarioCadastroLogin para validar o email
        String emailPattern = "coloque-aqui-o-seu-pattern-de-email";
        Pattern pattern = Pattern.compile(emailPattern);

        while (true) {
            System.out.print("Qual é o seu novo email: ");
            String email = scanner.nextLine();

            Matcher matcher = pattern.matcher(email);
            if (matcher.matches()) {
                if (usuarioDAO.emailJaCadastrado(email)) {
                    System.out.println("Este email já está cadastrado. Tente novamente com outro email.");
                } else {
                    // Atualizar o email no banco de dados ou onde quer que esteja sendo armazenado
                    break;
                }
            } else {
                System.out.println("Email em formato inválido. Tente novamente.");
            }
        }
        voltarParaVerOsDados(); // Função para voltar
    }

    public void atualizarSenha() {
        // A mesma lógica que está na classe UsuarioCadastroLogin para validar a senha
        String senhaPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[^a-zA-Z0-9]).{8,}$";
        Pattern senhaRegex = Pattern.compile(senhaPattern);

        while (true) {
            System.out.println("(A senha deve conter no mínimo: Duas letras(uma maiúscula), um caractere especial e 6 dígitos numéricos)");
            System.out.print("Qual é a sua nova senha: ");
            String senha = scanner.nextLine();

            Matcher matcher = senhaRegex.matcher(senha);
            if (matcher.matches()) {
                // Atualizar a senha no banco de dados ou onde quer que esteja sendo armazenado
                break;
            } else {
                System.out.println("Senha inválida. Tente novamente.");
            }
        }
        voltarParaVerOsDados(); // Função para voltar
    }

    public void atualizarApelido() {
        // A mesma lógica que está na classe UsuarioCadastroLogin para validar o apelido
        System.out.println("(Ao atualizar um apelido, você pode optar por usá-lo para fazer o login, em vez do e-mail)");
        System.out.println("Deseja atualizar o apelido?");
        System.out.println("1 - Sim");
        System.out.println("2 - Não");

        int escolha = 0;
        try {
            escolha = Integer.parseInt(scanner.nextLine());
            if (escolha == 1) {
                while (true) {
                    System.out.print("Qual novo apelido você deseja: ");
                    String apelido = scanner.nextLine();
                    if (apelido.length() <= 16) {
                        if (usuarioDAO.apelidoJaCadastrado(apelido)) {
                            System.out.println("Este apelido já está cadastrado. Tente novamente com outro apelido.");
                        } else {
                            // Atualizar o apelido no banco de dados ou onde quer que esteja sendo armazenado
                            break;
                        }
                    } else {
                        System.out.println("Apelido muito longo. Tente novamente.");
                    }
                }

                voltarParaVerOsDados(); // Função para voltar
            }
        } catch (NumberFormatException e) {
            System.out.println("Opção inválida. Não terá apelido atualizado.");
        }
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

        System.out.println("Qual é o seu novo sexo?");
        System.out.println("1 - Masculino");
        System.out.println("2 - Feminino");
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


    // Funções dos sub-menus:

    // Sub menu Ferramentas:

    public double calcularBasal() {
        // Obter os dados do usuário já salvos no banco de dados
        String sexo = usuarioDAO.obterSexoUsuario(idUsuarioLogado);
        int idade = usuarioDAO.obterIdadeUsuario(idUsuarioLogado);
        double peso = usuarioDAO.obterPesoUsuario(idUsuarioLogado);
        double altura = usuarioDAO.obterAlturaUsuario(idUsuarioLogado);

        // Pedir ao usuário para escolher o nível de atividade física
        double fatorAtividade = obterFatorAtividade();

        // Agora você tem todos os dados necessários para calcular o TMB
        return calcularBasalComDados(sexo, idade, peso, altura, fatorAtividade);
    }

    private double calcularBasalComDados(String sexo, int idade, double peso, double altura, double fatorAtividade) {
        double basalValue;
        if (sexo.equalsIgnoreCase("Masculino")) {
            basalValue = (10 * peso + 6.25 * altura - 5 * idade) + 5;
        } else {
            basalValue = (10 * peso + 6.25 * altura - 5 * idade) - 161;
        }
        basalValue *= fatorAtividade;

        // Opcional: Atualizar o valor basal no banco de dados
        usuarioDAO.atualizarBasal(idUsuarioLogado, basalValue);

        return basalValue;
    }

    private double obterFatorAtividade() {
        System.out.println("Escolha o seu nível de atividade física:");
        System.out.println("1 - Sedentário: pouco ou nenhum exercício");
        System.out.println("2 - Levemente ativo: exercício leve 1 a 3 dias por semana");
        System.out.println("3 - Moderadamente ativo: exercício moderado 3 a 5 dias por semana");
        System.out.println("4 - Muito ativo: exercício pesado 6 a 7 dias por semana");
        System.out.println("5 - Super ativo: exercício muito pesado e trabalho físico ou treino 2 vezes por dia");
        double fatorAtividade = 1.2; // valor padrão para sedentário
        try {
            int escolhaNivelAtividade = Integer.parseInt(scanner.nextLine());
            switch (escolhaNivelAtividade) {
                case 1: fatorAtividade = 1.2; break;
                case 2: fatorAtividade = 1.375; break;
                case 3: fatorAtividade = 1.55; break;
                case 4: fatorAtividade = 1.725; break;
                case 5: fatorAtividade = 1.9; break;
                default: System.out.println("Opção inválida. Usando o nível de atividade padrão (Sedentário).");
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Usando o nível de atividade padrão (Sedentário).");
        }
        return fatorAtividade;
    }



    public void calculadoraBasal() {
        // Recupere o basal_value do banco de dados
        Double basalValue = usuarioDAO.obterBasalValue(idUsuarioLogado);

        if (basalValue == null) {
            // Como basalValue é null, calcular o Basal automaticamente com dados existentes
            basalValue = calcularBasal();
            System.out.println("Basal calculado automaticamente com sucesso: " + basalValue);
        }

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
                        // Mostrar o valor basal atual
                        System.out.println("Valor Basal atual: " + basalValue);
                        break;
                    case 2:
                        // Refazer o cálculo do Basal
                        basalValue = calcularBasal(); // Recalcula com dados atuais e fator de atividade
                        System.out.println("Basal recalculado com sucesso: " + basalValue);
                        break;
                    case 0:
                        // Voltar ao menu anterior
                        sair = true;
                        dentroMenuFerramentas(); // Supondo que esta função retorna ao menu anterior
                        break;
                    default:
                        System.out.println("Opção inválida. Por favor, escolha 1 para 'Ver Basal', 2 para 'Refazer Basal' ou 0 para 'Voltar'.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Por favor, insira um número válido (1, 2 ou 0).");
            }
        }
    }

    public void metaPeso() {
        double pesoDesejado = 0.0;

        while (true) {
            System.out.print("Qual é o peso que você deseja alcançar? ");
            if (scanner.hasNextDouble()) {
                pesoDesejado = scanner.nextDouble();
                scanner.nextLine(); // Consumir a quebra de linha após o número
                break; // Sair do loop se o número for válido
            } else {
                System.out.println("Por favor, insira um número válido.");
                scanner.next(); // Limpar a entrada inválida
            }
        }

        System.out.println("Você deseja alcançar o peso de " + pesoDesejado + " kg.");

        usuarioDAO.atualizarMetaPeso(idUsuarioLogado, pesoDesejado);

        menuPrincipalOpcoes(); // Certifique-se de que este método não esteja causando o erro de buffer
    }

    public void moderateCarb(double basalValueNovo) {

        double proteina = (basalValue * 0.3) / 4; // 30% das calorias da proteína
        double gordura = (basalValue * 0.35) / 9; // 35% das calorias da gordura
        double carboidrato = (basalValue * 0.35) / 4; // 35% das calorias do carboidrato

        int proteinaInt = (int) Math.round(proteina); // Convertendo para inteiro
        int gorduraInt = (int) Math.round(gordura); // Convertendo para inteiro
        int carboidratoInt = (int) Math.round(carboidrato); // Convertendo para inteiro

        System.out.println("Moderate Carb:");
        System.out.println("Proteína: " + proteinaInt + " gramas");
        System.out.println("Gordura: " + gorduraInt + " gramas");
        System.out.println("Carboidrato: " + carboidratoInt + " gramas");

        // Agora que você tem os valores de proteína, gordura e carboidrato, atualize no banco de dados.
        usuarioDAO.atualizarProteinaUsuario(idUsuarioLogado, proteinaInt);
        usuarioDAO.atualizarGorduraUsuario(idUsuarioLogado, gorduraInt);
        usuarioDAO.atualizarCarboidratoUsuario(idUsuarioLogado, carboidratoInt);
        metaPeso();

    }

    public void lowerCarb(double basalValueNovo) {
        double proteina = (basalValue * 0.4) / 4; // 30% das calorias da proteína
        double gordura = (basalValue * 0.4) / 9; // 35% das calorias da gordura
        double carboidrato = (basalValue * 0.2) / 4; // 35% das calorias do carboidrato

        int proteinaInt = (int) Math.round(proteina); // Convertendo para inteiro
        int gorduraInt = (int) Math.round(gordura); // Convertendo para inteiro
        int carboidratoInt = (int) Math.round(carboidrato); // Convertendo para inteiro

        System.out.println("Moderate Carb:");
        System.out.println("Proteína: " + proteinaInt + " gramas");
        System.out.println("Gordura: " + gorduraInt + " gramas");
        System.out.println("Carboidrato: " + carboidratoInt + " gramas");

        // Agora que você tem os valores de proteína, gordura e carboidrato, atualize no banco de dados.
        usuarioDAO.atualizarProteinaUsuario(idUsuarioLogado, proteinaInt);
        usuarioDAO.atualizarGorduraUsuario(idUsuarioLogado, gorduraInt);
        usuarioDAO.atualizarCarboidratoUsuario(idUsuarioLogado, carboidratoInt);

        metaPeso();


    }

    public void higherCarb(double basalValueNovo) {
        double proteina = (basalValue * 0.3) / 4; // 30% das calorias da proteína
        double gordura = (basalValue * 0.20) / 9; // 35% das calorias da gordura
        double carboidrato = (basalValue * 0.50) / 4; // 35% das calorias do carboidrato

        int proteinaInt = (int) Math.round(proteina); // Convertendo para inteiro
        int gorduraInt = (int) Math.round(gordura); // Convertendo para inteiro
        int carboidratoInt = (int) Math.round(carboidrato); // Convertendo para inteiro

        System.out.println("Moderate Carb:");
        System.out.println("Proteína: " + proteinaInt + " gramas");
        System.out.println("Gordura: " + gorduraInt + " gramas");
        System.out.println("Carboidrato: " + carboidratoInt + " gramas");

        // Agora que você tem os valores de proteína, gordura e carboidrato, atualize no banco de dados.
        usuarioDAO.atualizarProteinaUsuario(idUsuarioLogado, proteinaInt);
        usuarioDAO.atualizarGorduraUsuario(idUsuarioLogado, gorduraInt);
        usuarioDAO.atualizarCarboidratoUsuario(idUsuarioLogado, carboidratoInt);
        metaPeso();

    }


    public void Cutting(double basalValueNovo, int idUsuarioLogado) {
        Scanner scanner = new Scanner(System.in);
        double resultado = basalValue - 500;

        UsuarioDAO usuarioDAO = new UsuarioDAO(); // Crie uma instância de UsuarioDAO
        usuarioDAO.inserirBasalMeta(idUsuarioLogado, resultado); // Chame a função inserirBasalMeta na instância

        int escolha = 0;
        do {
            System.out.println("Após o cálculo, o valor é: " + resultado);
            System.out.println("Escolha uma opção:");
            System.out.println("1 - Low carb");
            System.out.println("2 - Moderate Carb");
            System.out.println("3 - High carb");
            System.out.println("0 - Voltar");
            System.out.print("Digite o número da sua escolha: ");

            try {
                escolha = Integer.parseInt(scanner.nextLine());
                switch (escolha) {
                    case 1:
                        usuarioDAO.atualizarMetodoCarboidrato(idUsuarioLogado, "Low carb");
                        lowerCarb(basalValue);
                        break;
                    case 2:
                        usuarioDAO.atualizarMetodoCarboidrato(idUsuarioLogado, "Moderate Carb");
                        moderateCarb(basalValue);
                        break;
                    case 3:
                        usuarioDAO.atualizarMetodoCarboidrato(idUsuarioLogado, "High carb");
                        higherCarb(basalValue);
                        break;
                    case 0:
                        metaFitness();
                        break;
                    default:
                        System.out.println("Escolha inválida. Digite um número de 0 a 3.");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número de 0 a 3.");
            }
        } while (escolha != 0);
    }


    public void Bulking(double basalValueNovo, int idUsuarioLogado) {
        Scanner scanner = new Scanner(System.in);
        double resultado = basalValue + 500;

        UsuarioDAO usuarioDAO = new UsuarioDAO(); // Crie uma instância de UsuarioDAO
        usuarioDAO.inserirBasalMeta(idUsuarioLogado, resultado); // Chame a função inserirBasalMeta na instância


        int escolha = 0;
        do {
            System.out.println("Após o cálculo, o valor é: " + resultado);
            System.out.println("Escolha uma opção:");
            System.out.println("1 - Low carb");
            System.out.println("2 - Moderate Carb");
            System.out.println("3 - High carb");
            System.out.println("0 - Voltar");
            System.out.print("Digite o número da sua escolha: ");

            try {
                escolha = Integer.parseInt(scanner.nextLine());
                switch (escolha) {
                    case 1:
                        usuarioDAO.atualizarMetodoCarboidrato(idUsuarioLogado, "Low carb");
                        lowerCarb(basalValue);
                        break;
                    case 2:
                        usuarioDAO.atualizarMetodoCarboidrato(idUsuarioLogado, "Moderate Carb");
                        moderateCarb(basalValue);
                        break;
                    case 3:
                        usuarioDAO.atualizarMetodoCarboidrato(idUsuarioLogado, "High carb");
                        higherCarb(basalValue);
                        break;
                    case 0:
                        metaFitness();
                        break;
                    default:
                        System.out.println("Escolha inválida. Digite um número de 0 a 3.");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número de 0 a 3.");
            }
        } while (escolha != 0);
    }

    public void Mantain() {

        int escolha = 0;
        do {
            System.out.println("O valor é: " + basalValue);
            System.out.println("Escolha uma opção:");
            System.out.println("1 - Low carb");
            System.out.println("2 - Moderate Carb");
            System.out.println("3 - High carb");
            System.out.println("0 - Voltar");
            System.out.print("Digite o número da sua escolha: ");

            try {
                escolha = Integer.parseInt(scanner.nextLine());
                switch (escolha) {
                    case 1:
                        usuarioDAO.atualizarMetodoCarboidrato(idUsuarioLogado, "Low carb");
                        lowerCarb(basalValue);
                        break;
                    case 2:
                        usuarioDAO.atualizarMetodoCarboidrato(idUsuarioLogado, "Moderate Carb");
                        moderateCarb(basalValue);
                        break;
                    case 3:
                        usuarioDAO.atualizarMetodoCarboidrato(idUsuarioLogado, "High carb");
                        higherCarb(basalValue);
                        break;
                    case 0:
                        metaFitness();
                        break;
                    default:
                        System.out.println("Escolha inválida. Digite um número de 0 a 3.");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número de 0 a 3.");
            }
        } while (escolha != 0);

    }

    public void metaFitness() {
        basalValue = usuarioDAO.obterBasalValue(idUsuarioLogado); // Substitua pela forma como você obtém o valor.

        int escolha = 0;
        String objetivo = ""; // Inicialize a variável objetivo aqui

        do {
            System.out.println("Qual é a sua meta?");
            System.out.println("1 - Cutting");
            System.out.println("2 - Bulking");
            System.out.println("3 - Maintain");
            System.out.println("0 - Voltar");

            System.out.print("Digite o número da sua escolha: ");

            try {
                escolha = Integer.parseInt(scanner.nextLine());

                if (escolha < 1 || escolha > 4) {
                    System.out.println("Escolha inválida. Digite um número de 1 a 4.");
                } else {
                    switch (escolha) {
                        case 1:
                            objetivo = "Cutting";
                            break;
                        case 2:
                            objetivo = "Bulking";
                            Bulking(basalValue, idUsuarioLogado);
                            break;
                        case 3:
                            objetivo = "Maintain";
                            break;
                        case 0:
                            return; // Retorne para encerrar a função se escolha for 4
                    }

                    if (escolha >= 1 && escolha <= 3) {
                        System.out.println(objetivo);

                        // Atualize o objetivo no banco de dados
                        usuarioDAO.atualizarObjetivoUsuario(idUsuarioLogado, objetivo);
                        System.out.println("Objetivo atualizado no banco de dados: " + objetivo);

                        // Chame as funções correspondentes ao objetivo
                        if (escolha == 1) {
                            Cutting(basalValue, idUsuarioLogado);
                        } else if (escolha == 2) {
                            Bulking(basalValue, idUsuarioLogado);
                        } else if (escolha == 3) {
                            Mantain();
                        }
                    }

                    break; // Saia do loop se a escolha for válida.
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, insira um número válido.");
            }
        } while (true);
    }


    public void Divisor() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1 - Definir meta ");
            System.out.println("0 - Voltar");
            System.out.print("Digite o número da sua escolha: ");

            try {
                int escolha = Integer.parseInt(scanner.nextLine());

                switch (escolha) {
                    case 1:
                        metaFitness(); // Chama a função metaFitness()
                        break;
                    case 0:
                        dentroMenuFerramentas();
                        break;
                    default:
                        System.out.println("Escolha inválida. Digite 0 ou 1.");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite 0 ou 1.");
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
                        Divisor();
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

    //Sub Menu Perfil:

    public void dados() {
        String sexo = "";
        int idade = 0;
        double peso = 0.0;
        double altura = 0.0;

        while (true) {
            System.out.println("Qual é o seu sexo?");
            System.out.println("1 - Masculino");
            System.out.println("2 - Feminino");
            System.out.print("Digite a opção desejada: ");

            try {
                int opcao = scanner.nextInt();
                scanner.nextLine(); // Limpa a quebra de linha após o número

                if (opcao == 1) {
                    sexo = "Masculino";
                    break;
                } else if (opcao == 2) {
                    sexo = "Feminino";
                    break;
                } else {
                    System.out.println("Opção inválida. Por favor, escolha 1 ou 2.");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número (1 ou 2).");
                scanner.nextLine(); // Limpa a entrada inválida
            }
        }

        while (true) {
            System.out.print("Qual é a sua idade? ");

            try {
                idade = scanner.nextInt();
                scanner.nextLine(); // Limpa a quebra de linha após o número
                break;
            } catch (java.util.InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número inteiro para a idade.");
                scanner.nextLine(); // Limpa a entrada inválida
            }
        }

        while (true) {
            System.out.print("Qual é o seu peso? ");

            try {
                peso = scanner.nextDouble();
                scanner.nextLine(); // Limpa a quebra de linha após o número
                break;
            } catch (java.util.InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número para o peso.");
                scanner.nextLine(); // Limpa a entrada inválida
            }
        }

        while (true) {
            System.out.print("Qual é a sua altura? ");

            try {
                altura = scanner.nextDouble();
                scanner.nextLine(); // Limpa a quebra de linha após o número

                System.out.println("Dados coletados com sucesso");
                break;
            } catch (java.util.InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número para a altura.");
                scanner.nextLine(); // Limpa a entrada inválida
            }
        }

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarioDAO.inserirDadosSaude(idUsuarioLogado, sexo, idade, peso, altura);
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

        int escolha = 0;

        do {
            System.out.println("Escolha uma opção:");
            System.out.println("1 - Mudar dados manualmente");
            System.out.println("2 - Refazer dados");
            System.out.println("0 - Voltar");
            System.out.print("Digite o número da sua escolha: ");

            try {
                escolha = Integer.parseInt(scanner.nextLine());

                if (escolha == 1) {
                    mudarDadosManualmente();
                } else if (escolha == 2) {
                    atualizarGeral();

                } else if (escolha == 0) {
                    dentroPerfil();
                } else {
                    System.out.println("Escolha inválida. Digite 1,2 ou 3.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite 1 ou 2.");
            }
        } while (escolha != 1 && escolha != 3);
    }


    public void campanha() {

        int escolha = 0;

        do {
            System.out.println("Escolha uma opção:");
            System.out.println("1 - Ver meus objetivos"); // Chamar função aqui
            System.out.println("0 - Voltar"); // Chamar função aqui
            System.out.print("Digite o número da sua escolha: ");

            try {
                escolha = Integer.parseInt(scanner.nextLine());

                if (escolha == 1) {
                    verObjetivos();
                } else if (escolha == 0) {
                    dentroPerfil();
                } else {
                    System.out.println("Escolha inválida. Digite 1 ou 2.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite 1 ou 2.");
            }
        } while (escolha != 1 && escolha != 2);
    }

    public void dentroPerfil() {
        int opcao;

        do {
            try {
                System.out.println("Escolha uma opção:");
                System.out.println("1 - Ver dados");
                System.out.println("2 - Campanha");
                System.out.println("3 - Criar dados");
                System.out.println("0 - Voltar");
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
                        campanha();
                        break;
                    case 3:
                        dados();
                        break;

                    case 0 :
                        menuPrincipalOpcoes();
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

    public void voltarParaVerOsDados() {
        int escolha;
        while (true) {
            try {
                System.out.println("Escolha uma opção:");
                System.out.println("1 - Mudar outro dado");
                System.out.println("0 - Voltar");

                escolha = Integer.parseInt(scanner.nextLine());

                if (escolha == 1) {
                    mudarDadosManualmente();
                } else if (escolha == 0) {
                    verDados(); // Chama a função para ver os dados
                } else {
                    System.out.println("Opção inválida. Por favor, escolha uma opção válida.");
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
                atualizarEmail();
                break;
            case 2:
                atualizarSenha();
                break;
            case 3:
                atualizarApelido();
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

    public void verObjetivos() {

        String objetivo = usuarioDAO.obterObjetivoUsuario(idUsuarioLogado);
        double basal_meta = usuarioDAO.obterBasalMeta(idUsuarioLogado);
        double metaPeso = usuarioDAO.obterMetaPeso(idUsuarioLogado);


        // Obter os valores de proteína, gordura e carboidrato como números inteiros
        int proteinaInt = (int) Math.round(usuarioDAO.obterProteinaUsuario(idUsuarioLogado));
        int gorduraInt = (int) Math.round(usuarioDAO.obterGorduraUsuario(idUsuarioLogado));
        int basal_metaINT = (int) Math.round(usuarioDAO.obterBasalMeta(idUsuarioLogado));
        int carboidratoInt = (int) Math.round(usuarioDAO.obterCarboidratoUsuario(idUsuarioLogado));
        int metaPesoINT = (int) Math.round(usuarioDAO.obterMetaPeso(idUsuarioLogado));

        System.out.println("Seu objetivo atual é de: " + objetivo);
        System.out.println("Sua meta de peso é: " + metaPesoINT + " Kg");
        System.out.println("Seu basal atual para seu objetivo é: " + basal_metaINT + " Calorias");
        System.out.println("Você escolheu seguir a divisão de macro da forma: +  Lowcarb"  );
        System.out.println("Para obter sucesso no seu objetivo siga essa divisão de macronutrientes: ");
        System.out.println("Consumir: " + proteinaInt + " gramas de proteina");
        System.out.println("Consumir: " + gorduraInt + " gramas de gordura");
        System.out.println("Consumir: " + carboidratoInt + " gramas de carboidrato");


        int opcao;
        do {
            System.out.println("0 - Voltar");
            try {
                opcao = scanner.nextInt();
                switch (opcao) {
                    case 0:
                        // Logica para voltar
                        System.out.println("Voltando...");
                        return; // Isso vai sair do método dentroPerfil
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                scanner.next(); // Isso é necessário para consumir a entrada errada e evitar um loop infinito
            }
        } while (true); // O loop vai continuar até que o usuário digite 0 para voltar
    }

    //Menu Principal

    public void menuPrincipalOpcoes() {

        while (true) {
            System.out.println("Escolha uma opção:");
            System.out.println("1 - Perfil");
            System.out.println("2 - Ferramentas");
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

