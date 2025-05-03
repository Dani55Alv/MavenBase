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

  @FXML
  private void initialize() {
    try {
      Connection conn = DatabaseConnector.conectar(); // <- Usamos la conexión centralizada
      jugadorDao = new JugadorDao(conn);

      // Verificar o crear la tabla si no existe
      String createTableQuery = "CREATE TABLE IF NOT EXISTS jugadores (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, puntos REAL)";
      try (Statement stmt = conn.createStatement()) {
        stmt.executeUpdate(createTableQuery);
        System.out.println("Tabla 'jugadores' verificada/creada correctamente.");
      } catch (SQLException e) {
        e.printStackTrace();
      }

      // Configurar las columnas
      colIdOnline.setCellValueFactory(new PropertyValueFactory<>("id"));
      colNombreOnline.setCellValueFactory(new PropertyValueFactory<>("nombre"));
      colPuntosOnline.setCellValueFactory(new PropertyValueFactory<>("puntos"));

      // Cargar los datos en la tabla
      colNombreOnline.setSortable(true);

      jugadoresList = FXCollections.observableArrayList(jugadorDao.obtenerJugadores_online());
      tablaJugadoresONLINE.setItems(jugadoresList);

      System.out.println("Tabla recargada");

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @FXML
  private void irACuartaVista() {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/cuarta.fxml"));
      Parent root = loader.load();
      Stage stage = new Stage();
      stage.setTitle("Vista Cuarta");
      stage.setScene(new Scene(root));
      stage.show();
    } catch (IOException e) {
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

  @FXML
  public void ordenarTablaPorNombre2() throws SQLException {
    // 1) Obtén la lista ordenada de la BD
    List<Jugador> ordenada = jugadorDao.obtenerJugadoresOrdenados_online();
    // 2) Crea el ObservableList y lo asignas a la tabla
    ObservableList<Jugador> obs = FXCollections.observableArrayList(ordenada);
    tablaJugadoresONLINE.setItems(obs);


    tablaJugadoresONLINE.getSortOrder().clear();
    tablaJugadoresONLINE.getSortOrder().add(colNombreOnline);
    tablaJugadoresONLINE.sort(); // <- fuerza el ordenamiento del control
    tablaJugadoresONLINE.refresh();

    // 3) Marca la columna como ordenada (opcional, mejora UX)
    tablaJugadoresONLINE.getSortOrder().setAll(colNombreOnline);
    // 4) Refresca la tabla
    tablaJugadoresONLINE.refresh();
  }

}
