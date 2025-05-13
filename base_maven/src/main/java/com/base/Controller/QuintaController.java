/**
 * Es el controlador de la opciones online
 * 
 * @author Daniel Alvarez Morales
 * @version 1.0
 * @since 2025
 * 
 */

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
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.base.Model.Jugador;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class QuintaController {

  
  /**
   * Este método cambia la vista actual a la vista principal (primary).
   * Se invoca cuando se desea navegar a la pantalla principal de la aplicación.
   * 
   * @throws IOException Si ocurre un error al cargar la vista primaria.
   */
  @FXML
  private void switchToPrimary() throws IOException {
    App.setRoot("primary");
  }


  private JugadorDao jugadorDao;
  

  /**
   * Inicializa la conexión con la base de datos SQLite y crea una instancia de
   * {@link JugadorDao}.
   * Intenta establecer una conexión con la base de datos ubicada en la ruta
   * especificada y
   * asigna la conexión a la instancia de {@link JugadorDao}.
   * 
   * @throws SQLException Si ocurre un error al intentar establecer la conexión
   *                      con la base de datos.
   */
  @FXML
  private void initialize() {
    try {
      Connection connection = DriverManager.getConnection(
          "jdbc:sqlite:C:/Users/daniy/OneDrive/Escritorio/visualStudioClases/MavenBase/base.db");
      jugadorDao = new JugadorDao(connection);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  //Definicion de las pistas
  @FXML
  private TextField nombreField; 

  @FXML
  private TextField idField; 

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

  /**
   * Evento que maneja la inserción de un nuevo jugador.
   * 
   * Obtiene el nombre del jugador desde el campo de texto, valida que no esté
   * vacío y
   * luego intenta insertar el jugador en la base de datos a través del método
   * {@link JugadorDao#insertarJugador_online(Jugador)}. Si la inserción es
   * exitosa,
   * se actualiza la lista de jugadores. Si no se puede agregar más de 12
   * jugadores o
   * si ocurre un error en la base de datos, muestra una alerta con el mensaje
   * correspondiente.
   * 
   * @throws SQLException Si ocurre un error al intentar insertar el jugador en la
   *                      base de datos.
   */
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
        exito = jugadorDao.insertarJugador_online(jugador); // Inserción

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

  /**
   * Evento que maneja la eliminación de un jugador en línea.
   * 
   * Obtiene el ID del jugador desde el campo de texto, intenta eliminarlo de la
   * base de datos
   * a través del método {@link JugadorDao#eliminarJugador_online(Integer)}, y
   * muestra una alerta
   * si el jugador no existe o si hay un error al realizar la eliminación.
   * Si el ID no es un número válido o si ocurre un error en la base de datos,
   * también se muestra
   * una alerta con el mensaje correspondiente.
   * 
   * @throws SQLException Si ocurre un error al interactuar con la base de datos
   *                      durante la eliminación.
   */
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
        alerta.setContentText("No existe el jugador con ID: \"" + id + "\"");
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

  /**
   * Evento que maneja la actualización de un jugador en línea.
   * 
   * Obtiene el ID y el nombre desde los campos de texto y valida que no estén
   * vacíos. Si los campos
   * están vacíos, se muestra una alerta indicando que no se pueden actualizar
   * jugadores con campos vacíos.
   * Luego, intenta actualizar al jugador en la base de datos utilizando el método
   * {@link JugadorDao#actualizarJugador_online(Jugador)}.
   * Si no se encuentra un jugador con el ID proporcionado, se muestra una alerta.
   * Si el ID ingresado no es válido o si ocurre un error en la base de datos,
   * también se muestran alertas con el mensaje correspondiente.
   * 
   * @throws SQLException Si ocurre un error al interactuar con la base de datos
   *                      durante la actualización del jugador.
   */
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

  // NumberBinding

  @FXML
  private Button ORN_online;
  // Emitir binding

  /**
   * Evento que maneja la ordenación de jugadores por nombre de manera online.
   * 
   * Este método marca la opción de ordenación por nombre al establecer el valor
   * de
   * {@link CuartaController#opcionOrdenacion} a 1. De este modo, cuando se cargue
   * la vista
   * relacionada, los jugadores serán ordenados alfabéticamente por su nombre.
   * Además, imprime un mensaje en consola indicando que la opción de ordenación
   * ha sido seleccionada.
   * 
   * @see CuartaController#opcionOrdenacion
   */
  @FXML
  private void ordenarNombreAlfabeticamente_online_evento() {
    CuartaController.opcionOrdenacion.set(1);

    System.out.println("Se ha marcado cargarOrdenado = 1. Al entrar a la vista se ordenará por nombre.");
  }

  @FXML
  private Button ORP_online;

  /**
   * Evento que maneja la ordenación de jugadores por puntos de manera online.
   * 
   * Este método marca la opción de ordenación por puntos al establecer el valor
   * de
   * {@link CuartaController#opcionOrdenacion} a 2. De este modo, cuando se cargue
   * la vista
   * relacionada, los jugadores serán ordenados por sus puntos.
   * Además, imprime un mensaje en consola indicando que la opción de ordenación
   * ha sido seleccionada.
   * 
   * @see CuartaController#opcionOrdenacion
   */
  @FXML
  private void ordenarNombrePuntos_online_evento() {
    CuartaController.opcionOrdenacion.set(2);
    System.out.println("Se ha marcado cargarOrdenado = 2. Al entrar a la vista se ordenará por puntos.");
  }

}