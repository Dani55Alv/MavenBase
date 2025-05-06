package com.base.Controller;

import java.io.IOException;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SecondaryController {

  @FXML
  private TableView<Jugador> tablaJugadores;
  @FXML
  private TableColumn<Jugador, Integer> colId;
  @FXML
  private TableColumn<Jugador, String> colNombre;
  @FXML
  private TableColumn<Jugador, Double> colPuntos;

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
    colId.setCellValueFactory(new PropertyValueFactory<>("id"));
    colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
    colPuntos.setCellValueFactory(new PropertyValueFactory<>("puntos"));

    // Vincular la lista a la tabla
    tablaJugadores.setItems(jugadoresList);

    // Configura el listener para la selección del jugador
    tablaJugadores.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
      if (newSelection != null) {
        Jugador jugadorSeleccionado = newSelection;
        System.out.println("Jugador seleccionado: " + jugadorSeleccionado.getNombre());

        // Llamar a iniciarComeCocos con el jugador seleccionado
        iniciarComeCocos(jugadorSeleccionado);
      }
      
    });

  }



   public void actualizarPuntosDesdeJuego(ComeCocosController controladorJuego) {
        List<Object> datos = controladorJuego.getPuntosEid();

        Integer id = (Integer) datos.get(0);
        Double nuevosPuntos = (Double) datos.get(1);
JugadorDao jugadorDao = new JugadorDao("Dao");
jugadorDao.setListaJugadores(jugadoresList);
       jugadorDao.actualizarJugadorPuntos(id, nuevosPuntos);
System.out.println("Se actualizaron los puntos");
        // Si estás usando TableView y quieres forzar actualización:
    tablaJugadores.refresh(); // si la tabla se llama así
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
      // ⚠️ Aquí agregamos la lógica para actualizar los puntos al cerrar la ventana
      stage.setOnHidden(event -> {
        actualizarPuntosDesdeJuego(controller);
      });
      stage.show();
actualizarPuntosDesdeJuego(controller);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // Método que se llamará para cargar los jugadores en la tabla
  public void cargarJugadores(ObservableList<Jugador> jugadores) {
    jugadoresList.setAll(jugadores); // Actualiza la lista de jugadores en la tabla
  }



  
}