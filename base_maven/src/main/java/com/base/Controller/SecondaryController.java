
/**
 * Es el controlador de la tabla offline
 * 
 * @author Daniel Alvarez Morales
 * @version 1.0
 * @since 2025
 * 
 */
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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

  /**
   * Cambia la vista actual a la vista principal.
   * Este método se utiliza para cambiar a la pantalla principal de la aplicación.
   * 
   * Se produce un cambio de vista utilizando el método estático setRoot de la
   * clase App.
   * Puede lanzar una excepción de tipo IOException si ocurre un error al intentar
   * cargar la nueva vista.
   * 
   * @throws IOException Si ocurre un error al cargar la vista principal.
   */
  @FXML
  private void switchToPrimary() throws IOException {
    App.setRoot("primary");
  }

  // Lista observable que se actualizará dinámicamente
  private ObservableList<Jugador> jugadoresList = FXCollections.observableArrayList();

  /**
   * Inicializa la tabla "offline" de jugadores y configura los elementos de la vista.
   * Este método se ejecuta al inicializar el controlador y se utiliza para
   * configurar la tabla
   * con los datos de los jugadores, incluyendo la asignación de las columnas con
   * las propiedades correspondientes
   * de los objetos Jugador.
   * 
   * También se configura un listener para la selección de un jugador en la tabla,
   * lo que permite realizar una acción (como iniciar un juego) cuando un jugador
   * es seleccionado.
   * 
   * Además, se vincula la lista de jugadores (jugadoresList) a la tabla,
   * lo que asegura que los datos se muestren correctamente en la interfaz de
   * usuario.
   */
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

  /**
   * Actualiza los puntos de un jugador en la lista de jugadores desde el juego.
   * Este método recibe los puntos y el ID de un jugador desde el controlador del
   * juego y actualiza
   * la información del jugador en la lista interna.
   * 
   * @param controladorJuego El controlador del juego que contiene los datos de
   *                         los puntos y el ID del jugador.
   *                         Se espera que el controlador proporcione los datos
   *                         necesarios a través del método
   *                         getPuntosEid(), donde el primer valor es el ID del
   *                         jugador y el segundo son los nuevos puntos.
   */
  public void actualizarPuntosDesdeJuego(ComeCocosController controladorJuego) {
    List<Object> datos = controladorJuego.getPuntosEid();

    Integer id = (Integer) datos.get(0); // Extraemos el ID del jugador
    Double nuevosPuntos = (Double) datos.get(1); // Extraemos los nuevos puntos del jugador
    JugadorDao jugadorDao = new JugadorDao("Dao");

    try {
      // Asigna la lista de jugadores al DAO
      jugadorDao.setListaJugadores(jugadoresList);
    } catch (Exception e) {
      e.printStackTrace();
    }

    // Actualiza los puntos del jugador en la base de datos
    jugadorDao.actualizarJugadorPuntos(id, nuevosPuntos);
    System.out.println("Se actualizaron los puntos");

    // Si estás usando TableView y quieres forzar actualización:
    tablaJugadores.refresh(); // Si la tabla se llama así
  }

  /**
   * Inicia el juego ComeCocos en una nueva ventana (Stage) y asigna el jugador
   * que inicia el juego.
   * Este método carga la vista FXML del juego, establece el jugador en el
   * controlador correspondiente,
   * y muestra la ventana de juego. También asegura que los puntos del jugador se
   * actualicen cuando
   * la ventana de juego se cierra.
   * 
   * @param jugador El jugador que iniciará el juego. Este objeto contiene la
   *                información del jugador
   *                que se utilizará durante el juego.
   */
  @FXML
  private void iniciarComeCocos(Jugador jugador) {
    try {
      // Cargar la vista FXML
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

      // Crear la escena
      Scene scene = new Scene(root);

      // Añadir el CSS
      scene.getStylesheets().add(getClass().getResource("/css/Come_cocos.css").toExternalForm());

      // Establecer la escena y mostrar la ventana
      stage.setScene(scene);

      // Deshabilitar la opción de redimensionar la ventana
      stage.setResizable(false);

      // ⚠️ Aquí agregamos la lógica para actualizar los puntos al cerrar la ventana
      stage.setOnHidden(event -> {
        actualizarPuntosDesdeJuego(controller); // Actualizar los puntos cuando la ventana se cierra
      });
      stage.show();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Este método carga los jugadores en la tabla actualizando la lista interna de
   * jugadores.
   * Se llama desde el controlador TertiaryController a través de la instancia de
   * SecondaryController,
   * lo que permite que la lista de jugadores se pase y se muestre en la vista
   * correspondiente.
   * 
   * @param jugadores Lista de jugadores a cargar en la tabla. Esta lista es de
   *                  tipo ObservableList
   *                  para permitir una actualización dinámica en la interfaz de
   *                  usuario.
   */
  public void cargarJugadores(ObservableList<Jugador> jugadores) {
    jugadoresList.setAll(jugadores); // Actualiza la lista de jugadores en la tabla en tertiaryController
  }

}