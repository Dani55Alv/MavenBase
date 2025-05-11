/**
 * Es el controlador del menu principal
 * 
 * @author Daniel Alvarez Morales
 * @version 1.0
 * @since 2025
 * 
 */

package com.base.Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.base.App;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import util.DatabaseConnector;

public class PrimaryController {

    /**
     * Cambia a la vista secundaria.
     * 
     * Este método invoca el método {@link App#showSecondary()} para cambiar la
     * vista
     * a la correspondiente vista secundaria. Se espera que la vista secundaria esté
     * configurada en el archivo de configuración de la aplicación.
     * 
     * @throws IOException Si ocurre un error de entrada/salida al cargar la vista
     *                     secundaria.
     */
    @FXML
    private void switchToSecondary() throws IOException {
        App.showSecondary();
    }

    /**
     * Cambia a la vista terciaria.
     * 
     * Este método invoca el método {@link App#showTertiary()} para cambiar la vista
     * a la correspondiente vista terciaria. Se espera que la vista terciaria esté
     * configurada en el archivo de configuración de la aplicación.
     * 
     * @throws IOException Si ocurre un error de entrada/salida al cargar la vista
     *                     terciaria.
     */
    @FXML
    private void switchTothirdy() throws IOException {
        App.showTertiary();
    }

    /**
     * Cambia a la vista cuarta.
     * 
     * Este método invoca el método {@link App#showCuarta()} para cambiar la vista
     * a la correspondiente vista cuarta. Se espera que la vista cuarta esté
     * configurada en el archivo de configuración de la aplicación.
     * 
     * @throws IOException Si ocurre un error de entrada/salida al cargar la vista
     *                     cuarta.
     */
    @FXML
    private void switchToCuarta() throws IOException {
        App.showCuarta();
    }

    /**
     * Cambia a la vista quinta.
     * 
     * Este método invoca el método {@link App#showQuinta()} para cambiar la vista
     * a la correspondiente vista quinta. Se espera que la vista quinta esté
     * configurada en el archivo de configuración de la aplicación.
     * 
     * @throws IOException Si ocurre un error de entrada/salida al cargar la vista
     *                     quinta.
     */
    @FXML
    private void switchToQuinta() throws IOException {
        App.showQuinta();
    }

    @FXML
    private Button primaryButton;

    // conectando a la base de datos

    /**
     * Cambia a la cuarta vista después de verificar o crear la tabla 'jugadores' en
     * la base de datos.
     * 
     * Este método realiza las siguientes acciones:
     * 
     * Crea la tabla 'jugadores' si no existe, con columnas para ID, nombre y
     * puntos.
     * Verifica la existencia de la tabla 'jugadores' consultando la base de
     * datos.
     * Si la tabla se verifica correctamente, cambia a la cuarta vista.
     * 
     * 
     * Si ocurre un error al conectar con la base de datos o al cambiar la vista, se
     * captura y muestra el
     * detalle del error en la consola.
     * 
     * @throws SQLException Si ocurre un error de consulta SQL durante la creación o
     *                      verificación de la tabla.
     * @throws IOException  Si ocurre un error de entrada/salida al cambiar la
     *                      vista.
     */
    @FXML
    private void irACuartaVista() {
        String sql = "CREATE TABLE IF NOT EXISTS jugadores (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT NOT NULL, puntos INTEGER NOT NULL)";
        try (Connection conn = DatabaseConnector.conectar()) {
            // Verificar si la conexión fue exitosa
            if (conn == null) {
                System.err.println("No se pudo establecer conexión a la base de datos.");
                return;
            }

            // Ejecutar la creación de la tabla
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.execute();
                System.out.println("Tabla jugadores verificada/creada correctamente.");
            }

            // Verificación adicional: consultar la base de datos para asegurarse de que la
            // tabla existe
            String checkTableSql = "SELECT name FROM sqlite_master WHERE type='table' AND name='jugadores';";
            try (PreparedStatement pstmtCheck = conn.prepareStatement(checkTableSql)) {
                ResultSet rs = pstmtCheck.executeQuery();
                if (rs.next()) {
                    System.out.println("La tabla 'jugadores' existe en la base de datos.");
                } else {
                    System.out.println("La tabla 'jugadores' no existe.");
                }
            }

            // Cambiar la vista dentro de la misma ventana
            App.setRoot("cuarta");

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

}