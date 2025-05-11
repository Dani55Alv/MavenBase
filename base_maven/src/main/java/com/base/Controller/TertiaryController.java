/**
 * Es el controlador de la opciones offline
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

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.base.Model.Jugador;

public class TertiaryController {

    /**
     * Cambia la vista actual a la vista primaria.
     * 
     * @throws IOException si ocurre un error al intentar cargar la vista primaria.
     */
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    @FXML
    private Button GUARDAR;

    @FXML
    private Button RECUPERAR;

    // Instancia el dao (se inicializa aquí)
    private JugadorDao jugadorDao;

    
    /**
     * Constructor de TertiaryController.
     * Inicializa el objeto `jugadorDao` con el nombre del servidor
     * "Servidor_principal".
     */
    public TertiaryController() {
        this.jugadorDao = new JugadorDao("Servidor_principal"); // Aquí se inicializa el servidor
    }

    /**
     * Guarda los datos de los jugadores en un archivo binario en la ubicación
     * especificada.
     * Utiliza el método `grabar_datos` de `JugadorDao` para realizar la operación.
     * 
     * @throws Exception si ocurre algún error al guardar los datos o al seleccionar
     *                   el archivo.
     */
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

    /**
     * Recupera los datos de los jugadores desde un archivo binario y actualiza la
     * lista interna de jugadores.
     * Este método carga los jugadores desde el archivo especificado, actualiza el
     * contador estático de ID
     * y actualiza la estructura dinámica de jugadores en el objeto `jugadorDao`.
     * Luego, carga los jugadores en la tabla de la vista secundaria.
     * 
     * @throws Exception si ocurre un error al recuperar los datos desde el archivo.
     */
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

    /**
     * Establece el controlador de la vista secundaria.
     * 
     * @param secondaryController el controlador de la vista secundaria que se
     *                            asignará.
     */
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

    /**
     * Agrega un nuevo jugador al sistema si el nombre proporcionado no está vacío.
     * Si el nombre es vacío, muestra una alerta. Si no se puede agregar más de 4
     * jugadores,
     * muestra otra alerta informando de la restricción.
     * 
     * Este método utiliza el objeto `jugadorDao` para intentar agregar el jugador
     * al sistema.
     * Después de realizar la operación, limpia el campo de texto para ingresar un
     * nuevo nombre.
     * 
     * @see JugadorDao#agregarJugador(String) método utilizado para agregar el
     *      jugador.
     */
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

    /**
     * Elimina un jugador del sistema basado en el ID proporcionado en el campo de
     * texto.
     * Si el campo de ID está vacío, muestra una alerta indicando que el campo es
     * obligatorio.
     * Si el ID ingresado no es válido o el jugador no existe, muestra una alerta
     * con el mensaje correspondiente.
     * 
     * El método trata de convertir el ID ingresado a un número entero. Si no se
     * puede convertir (por ejemplo, si se ingresa texto en lugar de un número),
     * captura la excepción y muestra un mensaje de error.
     * 
     * Después de intentar eliminar al jugador, limpia el campo de texto para un
     * nuevo ingreso.
     * 
     * @see JugadorDao#eliminarJugador(Integer) método utilizado para eliminar el
     *      jugador.
     */
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
                alerta.setContentText("No existe el jugador con \"" + id + "\"");
                alerta.showAndWait();
            }

            idFieldEliminar.clear();

        } catch (NumberFormatException e) {
            System.out.println("ID no válido: " + e.getMessage());
        }
    }

    /**
     * Actualiza el nombre de un jugador en el sistema basado en el ID proporcionado
     * en el campo de texto.
     * Si el nombre está vacío, muestra una alerta indicando que el campo nombre es
     * obligatorio.
     * Si el ID proporcionado no existe en el sistema, muestra una alerta con el
     * mensaje correspondiente.
     * 
     * El método intenta convertir el ID ingresado en un número entero. Si el ID no
     * es válido (por ejemplo, si el campo contiene texto en lugar de un número), se
     * captura la excepción y se muestra una alerta informando que el ID debe ser un
     * número entero.
     * 
     * Después de intentar actualizar el jugador, limpia los campos de texto para
     * nuevos ingresos.
     * 
     * @see JugadorDao#actualizarJugador(Integer, String) método utilizado para
     *      actualizar el nombre del jugador.
     */
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

       /**
     * Ordena la lista de jugadores por nombre en orden alfabético.
     * Este método invoca el método de la clase JugadorDao para realizar la operación de ordenación
     * sobre la lista de jugadores almacenada en el objeto JugadorDao.
     * 
     * El orden alfabético se aplica a los nombres de los jugadores, y la lista se reorganiza según el orden de los caracteres
     * en el nombre de cada jugador.
     */
    @FXML
    private void ordenarNombreAlfabeticamente() {
        jugadorDao.ordenarNombreAlfabeticamente();
    }



    @FXML
    private Button ORP;

    /**
     * Ordena la lista de jugadores por puntos en orden ascendente.
     * Este método invoca el método de la clase JugadorDao para realizar la
     * operación de ordenación
     * sobre la lista de jugadores almacenada en el objeto JugadorDao.
     * 
     * El orden se aplica en función de los puntos de cada jugador, de menor a
     * mayor.
     */
    @FXML
    private void ordenarNombrePuntos() {
        jugadorDao.ordenarNombrePuntos();
    }

}
