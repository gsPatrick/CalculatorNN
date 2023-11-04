package dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import model.Usuario;
import model.UsuarioCadastroLogin;

public class Conexao {
    private static final String URL = "jdbc:postgresql://localhost:5432/projetonn";
    private static final String USER = "postgres";
    private static final String PASSWORD = "projetonnnutri";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Falha na conexão com o banco de dados");
        }
    }
}
