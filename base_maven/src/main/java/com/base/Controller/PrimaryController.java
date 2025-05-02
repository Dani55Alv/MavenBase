package com.base.Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
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

//conectando a la base de datos

@FXML
    private void inicializarBaseDeDatos() {
	String sql = "CREATE TABLE IF NOT EXISTS jugadores (nombre TEXT NOT NULL,puntos INTEGER NOT NULL)";
	try (Connection conn = DatabaseConnector.conectar();
		PreparedStatement pstmt = conn.prepareStatement(sql)) {
			//pstmt.execute(sql);
            pstmt.execute();
         
         //Mostrar verificacion y cargando vista
            System.out.println("Tabla jugadores verificada/creada correctamente.");

            // Cambiar a la cuarta vista después de crear/verificar la tabla
            App.showCuarta();

	} catch (SQLException e) {
		e.printStackTrace();
	}
}



}



