
/**
 * Es la clase que permite la conexion a la base de datos SQLITE
 * 
 * @author Daniel Alvarez Morales
 * @version 1.0
 * @since 2025
 * 
 */

package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnector {
    //private static final String URL = "jdbc:sqlite:base.db";
    private static final String URL = "jdbc:sqlite:C:/Users/daniy/OneDrive/Escritorio/visualStudioClases/MavenBase/base.db";
    
    /**
     * Establece una conexión a la base de datos SQLite utilizando la URL
     * predefinida.
     * 
     * Este método intenta conectarse a la base de datos utilizando la URL
     * especificada. Si la conexión es exitosa,
     * devuelve un objeto {@link Connection} que permite interactuar con la base de
     * datos. En caso de error,
     * se imprime un mensaje de error y el método devuelve {@code null}.
     * 
     * @return Un objeto {@link Connection} que representa la conexión a la base de
     *         datos, o {@code null} si la conexión falla.
     */
    public static Connection conectar() {
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.err.println("Error al conectar a SQLite: " + e.getMessage());
            return null;
        }
    }

}