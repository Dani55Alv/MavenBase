package com.base.Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import com.base.App;
import com.base.DAO.JugadorDao;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.base.Model.Jugador;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class QuintaController {

  @FXML
  private void switchToPrimary() throws IOException {
    App.setRoot("primary");
  }

  // Este método se llaará para pasar la instancia de CuartaController

  private JugadorDao jugadorDao;

  public void initialize() {
    try {
      Connection connection = DriverManager.getConnection(
          "jdbc:sqlite:C:/Users/daniy/OneDrive/Escritorio/visualStudioClases/MavenBase/base.db");
      jugadorDao = new JugadorDao(connection);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  // Definición de los campos de texto en la vista FXML
  @FXML
  private TextField nombreField; // Campo para ingresar el nombre del jugador

  @FXML
  private TextField idField; // Campo para ingresar el ID del jugador

  // Definición de los botones
  @FXML
  private Button Actualizar;

  @FXML
  private Button Agregar;

  @FXML
  private Button Eliminar;

  // Definicion de las pistas de texto
  @FXML
  private TextField nombreFieldAgregar;

  @FXML
  private TextField nombreFieldActualizar;

  @FXML
  private TextField idFieldActualizar;

  @FXML
  private TextField idFieldEliminar;

  @FXML
  private void insertarJugador_evento() {
    String nombreTexto = nombreFieldAgregar.getText();

    // Validación antes de intentar insertar
    if (nombreTexto.equals("")) {
      Alert alerta = new Alert(AlertType.INFORMATION);
      alerta.setTitle("Error");
      alerta.setHeaderText("Alerta:");
      alerta.setContentText("No puedes crear jugadores con nombres vacíos");
      alerta.showAndWait();
    } else {
      Jugador jugador = new Jugador(nombreTexto);

      try {
       boolean exito;
       exito= jugadorDao.insertarJugador_online(jugador); // Inserción
     
       if (!exito) {
         Alert alerta2 = new Alert(AlertType.INFORMATION);
         alerta2.setTitle("Error");
         alerta2.setHeaderText("Alerta");
         alerta2.setContentText("No se pueden agregar mas de 12 jugadores.");
         alerta2.showAndWait();
       }

        // Aquí podrías llamar a actualizarTablaJugadores_bd si lo tienes
      } catch (SQLException e) {
        // Mostrar un mensaje de error si falla la inserción
        Alert errorAlert = new Alert(AlertType.ERROR);
        errorAlert.setTitle("Error en la base de datos");
        errorAlert.setHeaderText("No se pudo agregar el jugador");
        errorAlert.setContentText("Revisa la conexión o los datos.");
        errorAlert.showAndWait();
        e.printStackTrace();
      }
    }

    nombreFieldAgregar.clear(); // Limpiar el campo después de todo
  }

  @FXML
  private void eliminarJugador_online_evento() {
    try {
      Integer id = Integer.parseInt(idFieldEliminar.getText()); // Obtener el ID desde un TextField

      // Llamamos al método para eliminar el jugador online
      boolean exito;
      exito = jugadorDao.eliminarJugador_online(id);

      if (!exito) {
        // Si el ID no es un número válido
        Alert alerta = new Alert(AlertType.INFORMATION);
        alerta.setTitle("Error");
        alerta.setHeaderText("Alerta");
        alerta.setContentText("No existe el jugadon con \"" + id + "\"");
        alerta.showAndWait();
      }
    } catch (NumberFormatException e) {
      // Si el ID no es un número válido
      Alert alerta = new Alert(AlertType.INFORMATION);
      alerta.setTitle("Error");
      alerta.setHeaderText("Alerta");
      alerta.setContentText("El campo ID no es válido");
      alerta.showAndWait();

    } catch (SQLException e) {
      // Si ocurre un error al interactuar con la base de datos
      System.out.println("Error de consulta SQL");
      e.printStackTrace();

      Alert alerta = new Alert(AlertType.ERROR);
      alerta.setTitle("Error de base de datos");
      alerta.setHeaderText("Alerta");
      alerta.setContentText("No se pudo eliminar el jugador debido a un error de base de datos.");
      alerta.showAndWait();

    } finally {
      idFieldEliminar.clear(); // Limpia el campo de texto
    }
  }

  @FXML
  private void actualizarJugador_online_evento() {
    try {
      String idTexto = idFieldActualizar.getText();
      String nombre = nombreFieldActualizar.getText();

      if (idTexto.trim().equals("") || nombre.trim().equals("")) {
        Alert alerta = new Alert(AlertType.INFORMATION);
        alerta.setTitle("Error");
        alerta.setHeaderText("Alerta:");
        alerta.setContentText("No puedes actualizar con campos vacíos.");
        alerta.showAndWait();
        return;
      }

      Integer id = Integer.parseInt(idTexto);

      Jugador jugador_Buscar = new Jugador();
      jugador_Buscar.setId(id);
      jugador_Buscar.setNombre(nombre);
      boolean exito = true;
      exito = jugadorDao.actualizarJugador_online(jugador_Buscar);

      if (!exito) {
        Alert alerta = new Alert(AlertType.INFORMATION);
        alerta.setTitle("Error");
        alerta.setHeaderText("Alerta:");
        alerta.setContentText("No se encontró un jugador con el ID: " + id);
        alerta.showAndWait();
      }

    } catch (NumberFormatException e) {
      Alert alerta = new Alert(AlertType.INFORMATION);
      alerta.setTitle("Error de formato");
      alerta.setHeaderText("ID inválido");
      alerta.setContentText("Debes ingresar un número válido como ID.");
      alerta.showAndWait();
    } catch (SQLException e) {
      Alert alerta = new Alert(AlertType.INFORMATION);
      alerta.setTitle("Error de base de datos");
      alerta.setHeaderText("No se pudo actualizar el jugador");
      alerta.setContentText("Detalles: " + e.getMessage());
      alerta.showAndWait();
    } finally {
      idFieldActualizar.clear();
      nombreFieldActualizar.clear();
    }
  }

  @FXML
  private Button ORN_online;

  @FXML
  private void ordenarNombreAlfabeticamente_online_evento() {
    CuartaController.opcionOrdenacion = 1;
    System.out.println("Se ha marcado cargarOrdenado = 1. Al entrar a la vista se ordenará por nombre.");
  }

  @FXML
  private Button ORP_online;

  @FXML
  private void ordenarNombrePuntos_online_evento() {
    CuartaController.opcionOrdenacion = 2;
    System.out.println("Se ha marcado cargarOrdenado = 2. Al entrar a la vista se ordenará por puntos.");
  }
}