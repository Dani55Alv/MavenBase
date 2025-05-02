package com.base.Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import com.base.App;
import com.base.DAO.JugadorDao;
import com.base.Model.Jugador;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class CuartaController {

  
  @FXML
  private TableView<Jugador> tablaJugadoresONLINE;
  @FXML
  private TableColumn<Jugador, Integer> colIdOnline;
  @FXML
  private TableColumn<Jugador, String> colNombreOnline;
  @FXML
  private TableColumn<Jugador, Double> colPuntosOnline;

  
  public void actualizarTablaJugadores_bd() {
    try {
      List<Jugador> jugadores = jugadorDao.obtenerJugadores_online();
      ObservableList<Jugador> listaObservable = FXCollections.observableArrayList(jugadores);
      tablaJugadoresONLINE.setItems(listaObservable);
    } catch (SQLException e) {
      e.printStackTrace(); // Manejo básico del error
    }
  }
  // Método para cambiar de vista (Menú)
  @FXML
  private void switchToPrimary() throws IOException {
    App.setRoot("primary");
  }

  // Lista observable que se actualizará dinámicamente
  private ObservableList<Jugador> jugadoresList = FXCollections.observableArrayList();


  private JugadorDao jugadorDao;

  @FXML
  private void initialize() {
      try {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:jugadores.db");
        jugadorDao = new JugadorDao(conn);

        // Configurar las columnas
        colIdOnline.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombreOnline.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colPuntosOnline.setCellValueFactory(new PropertyValueFactory<>("puntos"));

        // Cargar los datos en la tabla
        ObservableList<Jugador> lista = FXCollections.observableArrayList(jugadorDao.obtenerJugadores_online());
        tablaJugadoresONLINE.setItems(lista);

    } catch (SQLException e) {
        e.printStackTrace();
    }
  }

  // Método para iniciar el juego ComeCocos
  @FXML
  private void iniciarComeCocos(Jugador jugador) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/come_cocos.fxml"));
      if (loader.getLocation() == null) {
        System.out.println("No se pudo cargar el archivo FXML. Revisa la ruta.");
        return;
      }
      Parent root = loader.load();

      // Obtener el controlador de la ventana de ComeCocos y pasar el jugador
      ComeCocosController controller = loader.getController();
      controller.setJugador(jugador); // Establecer el jugador en el controlador de ComeCocos

      // Crear una nueva ventana (Stage) y mostrarla
      Stage stage = new Stage();
      stage.setTitle("ComeCocos");
      stage.setScene(new Scene(root));
      stage.show();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // Método que se llamará para cargar los jugadores en la tabla
  public void cargarJugadores(ObservableList<Jugador> jugadores) {
    jugadoresList.setAll(jugadores); // Actualiza la lista de jugadores en la tabla
  }






}
