/**
 * Es el controlador de la tabla online
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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.base.App;
import com.base.DAO.JugadorDao;
import com.base.Model.Jugador;

import javafx.beans.property.SimpleIntegerProperty;
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
import util.DatabaseConnector;

public class CuartaController {

  @FXML
  private TableColumn<Jugador, String> nombreColumn;
  @FXML
  private TableView<Jugador> tablaJugadoresONLINE;
  @FXML
  private TableColumn<Jugador, Integer> colIdOnline;
  @FXML
  private TableColumn<Jugador, String> colNombreOnline;
  @FXML
  private TableColumn<Jugador, Double> colPuntosOnline;

  /**
   * Ordena la tabla de jugadores por el nombre de manera ascendente.
   * 
   * Este método realiza lo siguiente:
   * 1. Configura la columna 'nombre' para ser ordenable.
   * 2. Ordena la lista de jugadores en la tabla por el nombre utilizando un
   * comparador.
   * 3. Recarga los elementos de la tabla con la lista ordenada.
   * 4. Marca visualmente la columna 'nombre' como la columna ordenada.
   * 5. Forza una actualización visual de la tabla para reflejar el nuevo orden.
   * 
   * El método es útil para asegurar que la tabla siempre se muestre ordenada por
   * nombre en orden ascendente
   * y se actualice visualmente en la interfaz de usuario.
   */
  @FXML
  public void ordenarTablaPorNombre() {
    // Asegúrate de que la columna esté configurada para ser ordenable
    colNombreOnline.setSortable(true);

    // Ordenar la lista de jugadores por nombre
    jugadoresList.sort(Comparator.comparing(Jugador::getNombre)); // Ordena directamente la lista observable

    // Recargar los elementos de la tabla
    tablaJugadoresONLINE.setItems(jugadoresList); // Vuelve a asignar la lista ordenada

    // Visualmente marcar la columna como ordenada
    tablaJugadoresONLINE.getSortOrder().clear(); // Limpiar cualquier columna previamente ordenada
    tablaJugadoresONLINE.getSortOrder().add(colNombreOnline); // Establecer la columna como ordenada

    // Forzar actualización visual de la tabla
    tablaJugadoresONLINE.refresh();

    System.out.println("Online ordenado");
  }

  /**
   * Actualiza la tabla de jugadores en la interfaz de usuario con los datos
   * obtenidos de la base de datos.
   * 
   * Este método realiza lo siguiente:
   * 1. Obtiene la lista de jugadores actualizada desde la base de datos
   * utilizando el método
   * `obtenerJugadores_online` de `jugadorDao`.
   * 2. Convierte la lista de jugadores en un objeto `ObservableList`, lo que
   * permite la actualización dinámica
   * de la tabla.
   * 3. Establece los jugadores obtenidos como los nuevos elementos en la tabla
   * `tablaJugadoresONLINE`.
   * 4. Si ocurre un error durante el proceso de consulta a la base de datos, se
   * captura y se imprime la excepción.
   * 
   * Este método se usa para reflejar los cambios en la base de datos y mostrar
   * los datos actualizados en la UI.
   */
  public void actualizarTablaJugadores_bd() {
    try {
      // Obtén los jugadores actualizados
      List<Jugador> jugadores = jugadorDao.obtenerJugadores_online();
      ObservableList<Jugador> listaObservable = FXCollections.observableArrayList(jugadores);

      // Establece los nuevos elementos en la tabla
      tablaJugadoresONLINE.setItems(listaObservable);
      System.out.println("Tabla actualizada.");
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

  // public static int opcionOrdenacion = 0;
  static SimpleIntegerProperty opcionOrdenacion = new SimpleIntegerProperty(0);

  /**
   * Método que se ejecuta al inicializar la vista.
   * 
   * Establece la conexión con la base de datos, asegura que la tabla 'jugadores'
   * existe
   * o la crea si no es así, configura las columnas de la tabla en la interfaz, y
   * carga los
   * jugadores desde la base de datos con la ordenación seleccionada.
   * 
   * Este método también configura un listener en la tabla para que, al
   * seleccionar un jugador,
   * se inicie el juego con ese jugador.
   * 
   * Las posibles opciones de ordenación son:
   * 0 - Sin ordenación específica.
   * 1 - Ordenación por nombre.
   * 2 - Ordenación por puntos.
   */
  @FXML
  private void initialize() {
    try {
      Connection conn = DatabaseConnector.conectar();
      jugadorDao = new JugadorDao(conn);

      // Crear tabla si no existe
      String createTableQuery = "CREATE TABLE IF NOT EXISTS jugadores (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, puntos REAL)";
      try (Statement stmt = conn.createStatement()) {
        stmt.executeUpdate(createTableQuery);
        System.out.println("Tabla 'jugadores' verificada/creada correctamente.");
      }

      // Configurar columnas
      colIdOnline.setCellValueFactory(new PropertyValueFactory<>("id"));
      colNombreOnline.setCellValueFactory(new PropertyValueFactory<>("nombre"));
      colPuntosOnline.setCellValueFactory(new PropertyValueFactory<>("puntos"));
      colNombreOnline.setSortable(true);

      // Manejo de la opción de ordenación
      switch (opcionOrdenacion.get()) {
        case 0:
          System.out.println(opcionOrdenacion.get() + "  ");
          jugadoresList = FXCollections.observableArrayList(jugadorDao.obtenerJugadores_online());
          break;

        case 1:
          System.out.println(opcionOrdenacion.get() + "  ");
          jugadoresList = FXCollections.observableArrayList(jugadorDao.obtenerJugadoresNombresOrdenados_online());
          break;

        case 2:
          System.out.println(opcionOrdenacion.get() + "  ");
          jugadoresList = FXCollections.observableArrayList(jugadorDao.obtenerJugadoresPuntosOrdenados_online());
          break;

        default:
          break;
      }

      // Asignar los jugadores a la tabla
      tablaJugadoresONLINE.setItems(jugadoresList);
      System.out.println("Tabla cargada");

      // Configurar el listener para la selección de un jugador
      tablaJugadoresONLINE.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
        if (newSelection != null) {
          Jugador jugadorSeleccionado = newSelection;
          System.out.println("Jugador seleccionado: " + jugadorSeleccionado.getNombre());

          // Llamar a iniciarComeCocos con el jugador seleccionado
          iniciarComeCocos(jugadorSeleccionado);
        }
      });
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Este método se encarga de cambiar a la vista "Cuarta".
   * Carga el archivo FXML correspondiente, crea una nueva ventana (Stage),
   * establece la escena con el contenido de la vista y la muestra al usuario.
   */
  @FXML
  private void irACuartaVista() {
    try {
      // Cargar el archivo FXML de la vista "Cuarta"
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/cuarta.fxml"));

      // Cargar la raíz del archivo FXML
      Parent root = loader.load();

      // Crear una nueva instancia de Stage (ventana)
      Stage stage = new Stage();

      // Establecer el título de la ventana
      stage.setTitle("Vista Cuarta");

      // Crear una nueva escena con el contenido de la vista FXML y asignarla al Stage
      stage.setScene(new Scene(root));

      // Mostrar la ventana
      stage.show();
    } catch (IOException e) {
      // Manejar excepciones si ocurre un error al cargar el archivo FXML
      e.printStackTrace();
    }
  }

  /**
   * Este método se encarga de iniciar la ventana de la vista "ComeCocos" y
   * pasarle el jugador seleccionado.
   * Carga el archivo FXML correspondiente, obtiene el controlador de la vista y
   * establece el jugador en él.
   * Luego, crea una nueva ventana (Stage) para mostrar la interfaz y aplica un
   * estilo CSS.
   * También se asegura de actualizar los puntos del jugador cuando se cierra la
   * ventana.
   *
   * @param jugador El jugador que se va a pasar a la vista ComeCocos.
   */
  @FXML
  private void iniciarComeCocos(Jugador jugador) {
    try {
      // Cargar el archivo FXML de la vista "ComeCocos"
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/come_cocos.fxml"));
      Parent root = loader.load();

      // Obtener el controlador de la ventana de ComeCocos
      ComeCocosController controller = loader.getController();
      // Establecer el jugador en el controlador de ComeCocos
      controller.setJugador(jugador);

      // Crear una nueva ventana (Stage)
      Stage stage = new Stage();
      // Establecer el título de la ventana
      stage.setTitle("ComeCocos");

      // Crear la escena y establecer el archivo FXML como contenido
      Scene scene = new Scene(root);

      // Añadir el CSS para personalizar la apariencia
      scene.getStylesheets().add(getClass().getResource("/css/Come_cocos.css").toExternalForm());

      // Establecer la escena y mostrar la ventana
      stage.setScene(scene);

      // Deshabilitar la opción de redimensionar la ventana
      stage.setResizable(false);

      // Actualizar los puntos del jugador al cerrar la ventana
      stage.setOnHidden(event -> {
        actualizarPuntosDesdeJuego(controller);
      });

      // Mostrar la ventana
      stage.show();
    } catch (IOException e) {
      // Manejar excepciones si ocurre un error al cargar el archivo FXML
      e.printStackTrace();
    }
  }

 
  /**
   * Este método ordena la tabla de jugadores por nombre de manera ascendente.
   * Utiliza el DAO para obtener la lista de jugadores ordenados por nombre y
   * luego actualiza
   * la tabla con la lista ordenada. Finalmente, marca la columna de nombre como
   * la ordenada y
   * refresca la vista de la tabla.
   *
   * @throws SQLException Si ocurre un error al realizar la consulta de jugadores
   *                      a la base de datos.
   */
  @FXML
  public void ordenarTablaPorNombre2() throws SQLException {
    // Obtener la lista de jugadores ordenados por nombre desde el DAO
    List<Jugador> ordenada = jugadorDao.obtenerJugadoresNombresOrdenados_online();

    // Convertir la lista de jugadores a una lista observable para que la tabla se
    // actualice automáticamente
    ObservableList<Jugador> obs = FXCollections.observableArrayList(ordenada);

    // Establecer la lista ordenada como la fuente de datos de la tabla
    tablaJugadoresONLINE.setItems(obs);

    // Establecer la columna de nombre como la que se va a ordenar visualmente
    tablaJugadoresONLINE.getSortOrder().setAll(colNombreOnline);

    // Realizar el ordenamiento de la tabla
    tablaJugadoresONLINE.sort();

    // Refrescar la tabla para asegurarse de que se muestren los cambios
    tablaJugadoresONLINE.refresh();

    // Mensaje de confirmación en la consola
    System.out.println("Tabla ordenada y refrescada correctamente.");
  }

  /**
   * Carga la lista de jugadores ordenados por nombre desde la base de datos y
   * actualiza
   * la tabla con estos datos. Establece la columna de nombre como la que se va a
   * ordenar
   * visualmente y realiza el ordenamiento en la tabla.
   *
   * Este método no invoca el método `initialize` ni ningún otro método genérico.
   *
   * @throws SQLException Si ocurre un error al consultar los jugadores ordenados
   *                      por nombre en la base de datos.
   */
  public void cargarJugadoresOrdenados() throws SQLException {
    // Obtener la lista de jugadores ordenados por nombre desde el DAO
    List<Jugador> ordenada = jugadorDao.obtenerJugadoresNombresOrdenados_online();

    // Convertir la lista de jugadores en un ObservableList para que se actualice la
    // tabla
    ObservableList<Jugador> obs = FXCollections.observableArrayList(ordenada);

    // Establecer la lista ordenada como los elementos visibles de la tabla
    tablaJugadoresONLINE.setItems(obs);

    // Establecer la columna de nombre como la que se va a ordenar visualmente
    tablaJugadoresONLINE.getSortOrder().setAll(colNombreOnline);

    // Realizar el ordenamiento de la tabla
    tablaJugadoresONLINE.sort();
  }

  /**
   * Actualiza los puntos de un jugador en la base de datos después de un juego de
   * ComeCocos.
   * Obtiene los datos del controlador del juego, como el ID del jugador y los
   * nuevos puntos,
   * busca al jugador correspondiente en la lista de jugadores y actualiza sus
   * puntos en la base de datos.
   * 
   * @param controladorJuego El controlador de la vista de ComeCocos que contiene
   *                         los datos del juego, incluidos el ID del jugador y
   *                         los nuevos puntos.
   */
  public void actualizarPuntosDesdeJuego(ComeCocosController controladorJuego) {
    // Obtener los datos del controlador de ComeCocos
    List<Object> datos = controladorJuego.getPuntosEid();

    // Extraer el ID del jugador y los nuevos puntos desde los datos obtenidos
    Integer id = (Integer) datos.get(0);
    Double nuevosPuntos = (Double) datos.get(1);

    // Conectar a la base de datos
    Connection conn = DatabaseConnector.conectar();
    JugadorDao jugadorDao = new JugadorDao(conn);

    try {
      // Establecer la lista de jugadores en el DAO
      jugadorDao.setListaJugadores(jugadoresList);
    } catch (Exception e) {
      e.printStackTrace();
    }

    // Buscar el jugador correspondiente en la lista
    Jugador jugador = new Jugador();
    for (Jugador jugadores : jugadoresList) {
      if (jugadores.getId().equals(id)) {
        jugador = jugadores;
      }
    }

    // Establecer los nuevos puntos para el jugador
    jugador.setPuntos(nuevosPuntos);

    try {
      // Actualizar los puntos del jugador en la base de datos
      jugadorDao.actualizarJugadorPuntos_online(jugador);
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Error consulta actualizar puntos");
    }

    System.out.println("Se actualizaron los puntos");

    // Forzar la actualización de la tabla si estás usando TableView
    tablaJugadoresONLINE.refresh();
  }

}