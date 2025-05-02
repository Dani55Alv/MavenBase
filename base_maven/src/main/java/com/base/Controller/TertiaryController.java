package com.base.Controller;

import java.io.IOException;
import java.util.List;

import com.base.App;
import com.base.DAO.JugadorDao;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.base.Model.Jugador;

public class TertiaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    @FXML
    private Button GUARDAR;

    @FXML
    private Button RECUPERAR;

    // Instancia de Servidor (se inicializa aquí)
    private JugadorDao jugadorDao;

    // Constructor donde inicializas el objeto servidor y pasas al DAO
    public TertiaryController() {
        this.jugadorDao = new JugadorDao("Servidor_principal"); // Aquí se inicializa el servidor
    }

    @FXML
    private void guardar() {
        // Llamamos al método guardar_datos() de Jugador_Dao

        String ruta ="C:/Users/daniy/OneDrive/Escritorio/visualStudioClases/MavenBase/base_maven/src/main/resources/Ficheros_binarios/";
        String fichero="informacion.bin";
        jugadorDao.grabar_datos(ruta, fichero);;
    }

    @FXML
    private void recuperar() {
        String ruta = "C:/Users/daniy/OneDrive/Escritorio/visualStudioClases/MavenBase/base_maven/src/main/resources/Ficheros_binarios/";
        String fichero = "informacion.bin";
        // Llamamos al método recuperar_datos() de Jugador_Dao
        List<Jugador> jugadoresRecuperados = jugadorDao.recuperar_datos(ruta, fichero); // Asumimos que este método devuelve una
                                                                           // lista de jugadores
        Jugador.actualizarContadorId(jugadoresRecuperados); // <- Aquí evitamos la repeticion de id's de juadores

        // Convertimos la lista en un ObservableList
        ObservableList<Jugador> jugadoresList = FXCollections.observableArrayList(jugadoresRecuperados);

        // Llamamos al método cargarJugadores de SecondaryController para actualizar la
        // tabla
        secondaryController.cargarJugadores(jugadoresList);

        // La esctructura dinamica recarga tambien los datos
        jugadorDao.getListaJugadores().clear();
        jugadorDao.getListaJugadores().addAll(jugadoresList);
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
    private void agregarJugador() {
        String nombre = nombreFieldAgregar.getText(); // Obtener el nombre desde un TextField
        jugadorDao.agregarJugador(nombre); // Usamos el Servidor directamente
        nombreFieldAgregar.clear();
    }

    @FXML
    private void eliminarJugador() {
        Integer id = Integer.parseInt(idFieldEliminar.getText()); // Obtener el ID desde un TextField
        jugadorDao.eliminarJugador(id); // Usamos el Servidor directamente
        idFieldEliminar.clear(); // Limpia el campo de texto

    }

    @FXML
    private void actualizarJugador() {
        Integer id = Integer.parseInt(idFieldActualizar.getText()); // Obtener el ID desde un TextField
        String nombre = nombreFieldActualizar.getText(); // Obtener el nuevo nombre desde un TextField
        jugadorDao.actualizarJugador(id, nombre); // Usamos el Servidor directamente
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
