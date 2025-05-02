package com.base.Controller;

import java.io.IOException;

import com.base.App;
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

  

  // Método para cambiar de vista (Menú)
  @FXML
  private void switchToPrimary() throws IOException {
    App.setRoot("primary");
  }

  // Lista observable que se actualizará dinámicamente
  private ObservableList<Jugador> jugadoresList = FXCollections.observableArrayList();



  @FXML
  private void initialize() {
    // Configuración de las columnas de la tabla
    colIdOnline.setCellValueFactory(new PropertyValueFactory<>("id"));
    colNombreOnline.setCellValueFactory(new PropertyValueFactory<>("nombre"));
    colPuntosOnline.setCellValueFactory(new PropertyValueFactory<>("puntos"));

    // Vincular la lista a la tabla
    tablaJugadoresONLINE.setItems(jugadoresList);

    // Configura el listener para la selección del jugador
    tablaJugadoresONLINE.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
      if (newSelection != null) {
        Jugador jugadorSeleccionado = newSelection;
        System.out.println("Jugador seleccionado: " + jugadorSeleccionado.getNombre());

        // Llamar a iniciarComeCocos con el jugador seleccionado
        iniciarComeCocos(jugadorSeleccionado);
      }
    });
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
