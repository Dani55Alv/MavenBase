package com.base.Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import com.base.App;
import com.base.DAO.JugadorDao;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.base.Model.Jugador;

public class QuintaController {
 

  @FXML
  private void switchToPrimary() throws IOException {
    App.setRoot("primary");
  }

  // Este método se llaará para pasar la instancia de CuartaController

  @FXML
  private Button GUARDAR;

  @FXML
  private Button RECUPERAR_Online;
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

  @FXML
  private void guardar() {
    // Llamamos al método guardar_datos() de Jugador_Dao

    String ruta = "C:/Users/daniy/OneDrive/Escritorio/visualStudioClases/MavenBase/base_maven/src/main/resources/Ficheros_binarios/";
    String fichero = "informacion.bin";
    jugadorDao.grabar_datos(ruta, fichero);
    ;
  }

  @FXML
  private void obtenerJugadores_online_evento() {
    try {
      jugadorDao.obtenerJugadores_online();

    } catch (SQLException e) {
    }
  }

  private SecondaryController secondaryController;

  public void setSecondaryController(SecondaryController secondaryController) {
    this.secondaryController = secondaryController;
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

    Jugador nombre = new Jugador(nombreFieldAgregar.getText()); // Obtener el nombre desde un TextField

    try {
      jugadorDao.insertarJugador_online(nombre); // Usamos el Servidor directamente

      // Llamar a actualizarTablaJugadores_bd() de CuartaController

    } catch (SQLException e) {
    }
    nombreFieldAgregar.clear();
  }

  @FXML
  private void eliminarJugador_online_evento() {
    Integer id = Integer.parseInt(idFieldEliminar.getText()); // Obtener el ID desde un TextField
    try {
      jugadorDao.eliminarJugador_online(id); // Usamos el Servidor directamente

    } catch (SQLException e) {

    }

    idFieldEliminar.clear(); // Limpia el campo de texto

  }

  @FXML
  private void actualizarJugador_evento() {
    Integer id = Integer.parseInt(idFieldActualizar.getText()); // Obtener el ID desde un TextField
    String nombre = nombreFieldActualizar.getText(); // Obtener el nuevo nombre desde un TextField+

    Jugador jugador_Buscar = new Jugador(nombre); // Obtener el nombre desde un TextField
    try {
      jugadorDao.actualizarJugador_online(jugador_Buscar);

    } catch (SQLException e) {
    }
    idFieldActualizar.clear();
    nombreFieldActualizar.clear();
  }

  @FXML
  private Button ORN;

  @FXML
  private void ordenarNombreAlfabeticamente() {

    jugadorDao.ordenarNombreAlfabeticamente();
  }

}
