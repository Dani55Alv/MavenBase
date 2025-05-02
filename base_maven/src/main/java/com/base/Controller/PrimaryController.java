package com.base.Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.base.App;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import util.DatabaseConnector;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.showSecondary();
    }

    @FXML
    private void switchTothirdy() throws IOException {
        App.showTertiary();
    }

    @FXML
    private void switchToCuarta() throws IOException {
        App.showCuarta();
    }

    @FXML
    private void switchToQuinta() throws IOException {

        App.showQuinta();

    }

    @FXML
    private Button primaryButton;

    // conectando a la base de datos

    @FXML
    private void inicializarBaseDeDatos_Boton_online() {
        String sql = "CREATE TABLE IF NOT EXISTS jugadores (nombre TEXT NOT NULL, puntos INTEGER NOT NULL)";
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

            // Cambiar a la cuarta vista después de crear/verificar la tabla
            App.showCuarta();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
