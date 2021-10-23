package br.senac.tads.pi4.projetoPI4.BD;

import java.sql.*;
import java.sql.DriverManager;

/**
 *
 * @author Gustavo Moraes
 */
public class ConexaoDB {

    public static String STATUS = "Não conectado";
    public static String DRIVER = "com.mysql.cj.jdbc.Driver";

    public static String SERVER = "localhost";
    public static String DATABASE = "devspace";

    public static String LOGIN = "root";
    public static String SENHA = "";

    public static String URL = "";

    public static Connection CONEXAO;

    public static Connection conectar() throws ClassNotFoundException, SQLException {

        URL = "jdbc:mysql://" + SERVER + ":3306/" + DATABASE + "?useTimezone=true&serverTimezone=UTC&useSSL=false";

        try {

            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, LOGIN, SENHA);

        } catch (ClassNotFoundException | SQLException e) {

            throw new RuntimeException(e);
        }
    }
}
