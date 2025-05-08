package com.base.Controller;

import java.io.IOException;
import java.util.List;

import com.base.App;
import com.base.DAO.JugadorDao;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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

        String ruta = "C:/Users/daniy/OneDrive/Escritorio/visualStudioClases/MavenBase/base_maven/src/main/resources/Ficheros_binarios/";
        String fichero = "informacion.bin";
        try {
            jugadorDao.grabar_datos(ruta, fichero);

        } catch (Exception e) {
System.out.println("Error al guardar o seleccionar fichero");
e.printStackTrace();
        }
        
    }

    @FXML
    private void recuperar() {
        String ruta = "C:/Users/daniy/OneDrive/Escritorio/visualStudioClases/MavenBase/base_maven/src/main/resources/Ficheros_binarios/";
        String fichero = "informacion.bin";

        // Recuperamos los datos desde el fichero
        List<Jugador> jugadoresRecuperados = jugadorDao.recuperar_datos(ruta, fichero);

        // Verificamos cuántos jugadores se recuperaron
        System.out.println("Jugadores recuperados: " + jugadoresRecuperados.size());

        // Actualizamos el contador estático de ID en Jugador
        Jugador.actualizarContadorId(jugadoresRecuperados);

        // Actualizamos la estructura dinámica interna
        jugadorDao.getListaJugadores().clear();
        jugadorDao.getListaJugadores().addAll(jugadoresRecuperados);

        System.out.println("Lista dinámica contiene: " + jugadorDao.getListaJugadores().size() + " jugadores");
        System.out.println("La lista original");
        for (Jugador jugador : jugadoresRecuperados) {
            System.out.println(jugador);
        }
        // Creamos una ObservableList para la TableView
        ObservableList<Jugador> jugadoresList = FXCollections.observableArrayList(jugadoresRecuperados);

        // Cargamos los jugadores en la tabla (SecondaryController)
        secondaryController.cargarJugadores(jugadoresList);
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

        Alert alerta = new Alert(AlertType.INFORMATION);
        alerta.setTitle("Error");
        alerta.setHeaderText("Alerta");
        alerta.setContentText("No puedes crear jugadores con nombres vacios");

        String nombre = nombreFieldAgregar.getText(); // Obtener el nombre desde un TextField

        if (nombre.equals("")) {
            alerta.showAndWait();
        } else {
            boolean exito;

            exito = jugadorDao.agregarJugador(nombre); // Usamos el Servidor directamente

            if (!exito) {
                Alert alerta2 = new Alert(AlertType.INFORMATION);
                alerta2.setTitle("Error");
                alerta2.setHeaderText("Alerta");
                alerta2.setContentText("No puedes crear mas de 4 jugadores");
                alerta2.showAndWait();

            }
        }
        nombreFieldAgregar.clear();

    }

    @FXML
    private void eliminarJugador() {
        try {
            String input = idFieldEliminar.getText().trim();
            if (input.isEmpty() || input.trim().equals("")) {
                System.out.println("Campo de ID vacío");

                Alert alerta = new Alert(AlertType.INFORMATION);
                alerta.setTitle("Error");
                alerta.setHeaderText("Alerta");
                alerta.setContentText("Campo de ID vacío");
                alerta.showAndWait();

                return;
            }

            Integer id = Integer.parseInt(input);
            System.out.println("ID ingresado desde TextField: " + id);
            boolean exito;
            exito = jugadorDao.eliminarJugador(id);

            if (!exito) {
                // Si el ID no es un número válido
                Alert alerta = new Alert(AlertType.INFORMATION);
                alerta.setTitle("Error");
                alerta.setHeaderText("Alerta");
                alerta.setContentText("No existe el jugadon con \"" + id + "\"");
                alerta.showAndWait();
            }

            idFieldEliminar.clear();

        } catch (NumberFormatException e) {
            System.out.println("ID no válido: " + e.getMessage());

        }
    }

    @FXML
    private void actualizarJugador() {

        try {
            Integer id = Integer.parseInt(idFieldActualizar.getText().trim());
            String nombre = nombreFieldActualizar.getText().trim();

            if (nombre.equals("")) {
                Alert alerta = new Alert(AlertType.INFORMATION);
                alerta.setTitle("Error");
                alerta.setHeaderText("Alerta");
                alerta.setContentText("El campo nombre está vacío.");
                alerta.showAndWait();
            } else {
                boolean exito = true;

                exito = jugadorDao.actualizarJugador(id, nombre);
                if (!exito) {
                    Alert alerta = new Alert(AlertType.INFORMATION);
                    alerta.setTitle("Error");
                    alerta.setHeaderText("Alerta:");
                    alerta.setContentText("No se encontró un jugador con el ID: " + id);
                    alerta.showAndWait();

                }

            }

            idFieldActualizar.clear();
            nombreFieldActualizar.clear();

        } catch (NumberFormatException e) {
            Alert alerta = new Alert(AlertType.INFORMATION);
            alerta.setTitle("Error");
            alerta.setHeaderText("Alerta");
            alerta.setContentText("Debes introducir un número entero válido en el campo ID.");
            alerta.showAndWait();
        }
    }

    @FXML
    private Button ORN;

    @FXML
    private void ordenarNombreAlfabeticamente() {

        jugadorDao.ordenarNombreAlfabeticamente();
    }

    @FXML
    private Button ORP;

    @FXML
    private void ordenarNombrePuntos() {

        jugadorDao.ordenarNombrePuntos();
    }
}
