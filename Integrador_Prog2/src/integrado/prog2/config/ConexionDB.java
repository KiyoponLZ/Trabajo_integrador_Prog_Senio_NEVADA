package integrado.prog2.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

    // Acá le pasamos la dirección exacta de la base que armamos en Workbench
    private static final String URL = "jdbc:mysql://localhost:3306/pedidos_db";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // En XAMPP viene vacía por defecto

    public static Connection getConexion() {
        Connection conexion = null;
        try {
            // Intentamos enchufar el cable
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("¡Conexión a MySQL exitosa!");
        } catch (SQLException e) {
            // Si algo falla (XAMPP apagado, mal el nombre, etc), nos avisa acá
            System.out.println("Upa, error al conectar: " + e.getMessage());
        }
        return conexion;
    }
}