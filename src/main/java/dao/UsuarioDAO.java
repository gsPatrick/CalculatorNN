package dao;
import model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {
    private Connection connection;

    public UsuarioDAO() {
        connection = Conexao.getConnection();
    }

    public void adicionarUsuario(Usuario usuario) {
        try {
            String sql = "INSERT INTO usuario (email, senha, nome, apelido) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, usuario.getEmail());
            statement.setString(2, usuario.getSenha());
            statement.setString(3, usuario.getNome());
            statement.setString(4, usuario.getApelido());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Falha ao adicionar usuário no banco de dados");
        }
    }

    public boolean emailJaCadastrado(String email) {
        try {
            String sql = "SELECT COUNT(*) FROM usuario WHERE email = ?"; // Use "Email" com a primeira letra maiúscula
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0; // Retorna verdadeiro se o email já estiver cadastrado, falso caso contrário.
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Falha ao verificar email no banco de dados");
        }
        return false;
    }

    public boolean apelidoJaCadastrado(String apelido) {
        try {
            String sql = "SELECT COUNT(*) FROM usuario WHERE apelido = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, apelido);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0; // Retorna verdadeiro se o apelido já estiver cadastrado, falso caso contrário.
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Falha ao verificar apelido no banco de dados");
        }
        return false;
    }

    public int obterIdUsuarioPorEmail(String emailOuApelido) {
        String sql = "SELECT idusuario FROM usuario WHERE email = ? OR apelido = ?";
        int idUsuarioLogado = -1; // Valor padrão caso a consulta não encontre um usuário com o email ou apelido

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, emailOuApelido);
            statement.setString(2, emailOuApelido);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                idUsuarioLogado = resultSet.getInt("idusuario");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idUsuarioLogado;
    }
    ;


    public void inserirDadosSaude(int idUsuario, String sexo, int idade, double peso, double altura) {
        String sql = "INSERT INTO usuariosaude (idusuario, sexo, idade, peso, altura) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idUsuario);
            statement.setString(2, sexo);
            statement.setInt(3, idade);
            statement.setDouble(4, peso); // Use setDouble para valores decimais
            statement.setDouble(5, altura); // Use setDouble para valores decimais
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarSexo(int idUsuario, String novoSexo) {
        String sql = "UPDATE usuariosaude SET sexo = ? WHERE idusuario = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, novoSexo);
            statement.setInt(2, idUsuario);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarIdade(int idUsuario, int novaIdade) {
        String sql = "UPDATE usuariosaude SET idade = ? WHERE idusuario = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, novaIdade);
            statement.setInt(2, idUsuario);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarPeso(int idUsuario, double novoPeso) {
        String sql = "UPDATE usuariosaude SET peso = ? WHERE idusuario = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDouble(1, novoPeso);
            statement.setInt(2, idUsuario);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarAltura(int idUsuario, double novaAltura) {
        String sql = "UPDATE usuariosaude SET altura = ? WHERE idusuario = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDouble(1, novaAltura);
            statement.setInt(2, idUsuario);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarDadosSaude(int idUsuario, String sexo, int idade, double peso, double altura) {
        String sql = "UPDATE usuariosaude SET sexo = ?, idade = ?, peso = ?, altura = ? WHERE idusuario = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, sexo);
            statement.setInt(2, idade);
            statement.setDouble(3, peso);
            statement.setDouble(4, altura);
            statement.setInt(5, idUsuario);
            statement.executeUpdate();
            System.out.println("Dados de saúde atualizados com sucesso.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Falha na atualização dos dados de saúde.");
        }
    }

    public String obterEmailUsuario(int idUsuario) {
        String email = null;
        String sql = "SELECT email FROM usuario WHERE idusuario = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idUsuario);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                email = resultSet.getString("email");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return email;
    }

    public String obterNomeUsuario(int idUsuario) {
        String nome = null;
        String sql = "SELECT nome FROM usuario WHERE idusuario = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idUsuario);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                nome = resultSet.getString("nome");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nome;
    }

    public String obterApelidoUsuario(int idUsuario) {
        String apelido = null;
        String sql = "SELECT apelido FROM usuario WHERE idusuario = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idUsuario);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                apelido = resultSet.getString("apelido");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return apelido;
    }

    public void atualizarEmail(int idUsuario, String novoEmail) {
        String sql = "UPDATE usuario SET email = ? WHERE idusuario = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, novoEmail);
            statement.setInt(2, idUsuario);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarApelido(int idUsuario, String novoApelido) {
        String sql = "UPDATE usuario SET apelido = ? WHERE idusuario = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, novoApelido);
            statement.setInt(2, idUsuario);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarSenha(int idUsuario, String novaSenha) {
        String sql = "UPDATE usuario SET senha = ? WHERE idusuario = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, novaSenha);
            statement.setInt(2, idUsuario);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }






    public String obterSexoUsuario(int idUsuario) {
        String sexo = null;
        String sql = "SELECT sexo FROM usuariosaude WHERE idusuario = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idUsuario);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                sexo = resultSet.getString("sexo");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sexo;
    }

    public int obterIdadeUsuario(int idUsuario) {
        int idade = -1; // Valor padrão para indicar que não foi possível obter a idade

        String sql = "SELECT idade FROM usuariosaude WHERE idusuario = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idUsuario);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                idade = resultSet.getInt("idade");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idade;
    }

    public double obterPesoUsuario(int idUsuario) {
        double peso = -1.0; // Valor padrão para indicar que não foi possível obter o peso

        String sql = "SELECT peso FROM usuariosaude WHERE idusuario = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idUsuario);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                peso = resultSet.getDouble("peso");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return peso;
    }

    public double obterAlturaUsuario(int idUsuario) {
        double altura = -1.0; // Valor padrão para indicar que não foi possível obter a altura

        String sql = "SELECT altura FROM usuariosaude WHERE idusuario = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idUsuario);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                altura = resultSet.getDouble("altura");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return altura;
    }


    public Double obterBasalValue(int idUsuario) {
        String sql = "SELECT basal_value FROM calculo_basal WHERE idusuario = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idUsuario);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble("basal_value");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retorna nulo se não houver valor no banco de dados.
    }

    public void inserirBasal(int idUsuario, double basalValue) {
        String sql = "INSERT INTO calculo_basal (idusuario, basal_value) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idUsuario);
            statement.setDouble(2, basalValue);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void atualizarBasal(int idUsuario, double novoBasalValue) {
        String sql = "UPDATE calculo_basal SET basal_value = ? WHERE idusuario = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDouble(1, novoBasalValue);
            statement.setInt(2, idUsuario);
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Basal Value atualizado com sucesso.");
            } else {
                System.out.println("Não foi possível atualizar o Basal Value. O registro para o usuário não existe.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Double obterBasalMeta(int idUsuario) {
        String sql = "SELECT basal_meta FROM calculo_basal WHERE idusuario = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idUsuario);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble("basal_meta");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retorna nulo se não houver valor no banco de dados.
    }

    public boolean inserirBasalMeta(int idUsuario, double valor) {
        String sql = "UPDATE calculo_basal SET basal_meta = ? WHERE idusuario = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDouble(1, valor);
            statement.setInt(2, idUsuario);
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0; // Retorna verdadeiro se a atualização for bem-sucedida.
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public double obterProteinaUsuario(int idUsuario) {
        double proteina = -1.0; // Valor padrão para indicar que não foi possível obter o valor de proteína

        String sql = "SELECT proteina FROM calculo_basal WHERE idusuario = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idUsuario);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                proteina = resultSet.getDouble("proteina");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return proteina;
    }

    public double obterCarboidratoUsuario(int idUsuario) {
        double carboidrato = -1.0; // Valor padrão para indicar que não foi possível obter o valor de carboidrato

        String sql = "SELECT carboidrato FROM calculo_basal WHERE idusuario = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idUsuario);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                carboidrato = resultSet.getDouble("carboidrato");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return carboidrato;
    }

    public double obterGorduraUsuario(int idUsuario) {
        double gordura = -1.0; // Valor padrão para indicar que não foi possível obter o valor de gordura

        String sql = "SELECT gordura FROM calculo_basal WHERE idusuario = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idUsuario);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                gordura = resultSet.getDouble("gordura");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return gordura;
    }


    public boolean atualizarProteinaUsuario(int idUsuario, int valorProteina) {
        String sql = "UPDATE calculo_basal SET proteina = ? WHERE idusuario = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, valorProteina);
            statement.setInt(2, idUsuario);
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0; // Retorna verdadeiro se a atualização for bem-sucedida.
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean atualizarCarboidratoUsuario(int idUsuario, int valorCarboidrato) {
        String sql = "UPDATE calculo_basal SET carboidrato = ? WHERE idusuario = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, valorCarboidrato);
            statement.setInt(2, idUsuario);
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0; // Retorna verdadeiro se a atualização for bem-sucedida.
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean atualizarGorduraUsuario( int idUsuario, int valorGordura) {
        String sql = "UPDATE calculo_basal SET gordura = ? WHERE idusuario = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, valorGordura);
            statement.setInt(2, idUsuario);
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0; // Retorna verdadeiro se a atualização for bem-sucedida.
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    public String obterObjetivoUsuario(int idUsuario) {
        String objetivo = "Teste" ; // Valor padrão para indicar que não foi possível obter o valor do objetivo

        String sql = "SELECT objetivo FROM calculo_basal WHERE idusuario = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idUsuario);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                objetivo = resultSet.getString("objetivo");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return objetivo;
    }

    public boolean atualizarObjetivoUsuario(int idUsuario, String Objetivo) {
        String sql = "UPDATE calculo_basal SET objetivo = ? WHERE idusuario = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, Objetivo);
            statement.setInt(2, idUsuario);
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0; // Retorna verdadeiro se a atualização for bem-sucedida.
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean atualizarMetodoCarboidrato(int idUsuario, String carboidratoMetodo) {
        String sql = "UPDATE calculo_basal SET metodo_carboidrato = ? WHERE idusuario = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, carboidratoMetodo);
            statement.setInt(2, idUsuario);
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0; // Retorna verdadeiro se a atualização for bem-sucedida.
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public String obterMetodoCarboidrato(int idUsuario) {
        String sql = "SELECT metodo_carboidrato FROM calculo_basal WHERE idusuario = ?";
        String metodoCarboidrato = null;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idUsuario);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) { // Se houver resultado, pegue o valor.
                    metodoCarboidrato = resultSet.getString("metodo_carboidrato");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return metodoCarboidrato; // Retorna o método de carboidrato ou null se não for encontrado.
    }



    public boolean adicionarObjetivoUsuario(int idUsuario, String objetivo) {
        String sql = "INSERT INTO calculo_basal (idusuario, objetivo) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idUsuario);
            statement.setString(2, objetivo);
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0; // Retorna verdadeiro se a inserção for bem-sucedida.
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public double obterMetaPeso(int idUsuario) {
        double metaPeso = 0.0;
        String query = "SELECT meta_peso FROM calculo_basal WHERE idusuario = ?";

        try {
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, idUsuario);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        metaPeso = resultSet.getDouble("meta_peso");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao obter a meta de peso do usuário " + idUsuario);
        }

        return metaPeso;
    }


    // Função para atualizar o valor da coluna meta_peso
    public void atualizarMetaPeso(int idUsuario, double pesoDesejado) {
        String query = "UPDATE calculo_basal SET meta_peso = ? WHERE idusuario = ?";

        try {
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setDouble(1, pesoDesejado);
                preparedStatement.setInt(2, idUsuario);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao atualizar o valor da coluna 'meta_peso' no banco de dados.");
        }
    }





    public boolean autenticar(String emailOuApelido, String senha) {
        String sql = "SELECT email, apelido, senha FROM usuario WHERE email = ? OR apelido = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, emailOuApelido);
            statement.setString(2, emailOuApelido);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String emailArmazenado = resultSet.getString("email");
                String apelidoArmazenado = resultSet.getString("apelido");
                String senhaArmazenada = resultSet.getString("senha");

                if (senha.equals(senhaArmazenada)) {
                    return true;
                } else {
                    System.out.println("Senha incorreta. Tente novamente.");
                }
            } else {
                System.out.println("Email ou apelido não encontrado. Tente novamente.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}



