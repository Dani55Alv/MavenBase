
/**
 * Es la clase principal donde se desarrolla la aplicacion javaFX.
 * 
 * @author Daniel Alvarez Morales
 * @version 1.0
 * @since 2025
 * 
 */

package com.base;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

import com.base.Controller.CuartaController;
import com.base.Controller.SecondaryController;
import com.base.Controller.TertiaryController;
import com.base.Controller.QuintaController;

public class App extends Application {

    private static Scene scene;

    // Guardamos las raíces
    private static Parent secondaryRoot;
    private static Parent tertiaryRoot;
    private static Parent cuartaRoot;
    private static Parent quintaRoot;

    // Guardamos los controladores
    private static SecondaryController secondaryController;
    private static TertiaryController tertiaryController;
    private static CuartaController cuartaController;
    private static QuintaController quintaController;

    /**
     * Inicializa y muestra la ventana principal de la aplicación JavaFX.
     *
     * Este método realiza las siguientes acciones:
     * - Carga las vistas FXML: secondary.fxml, tertiary.fxml, cuarta.fxml y
     * quinta.fxml.
     * - Obtiene los controladores correspondientes a cada una de esas vistas.
     * - Establece la comunicación entre controladores (por ejemplo, el controlador
     * de tertiary
     * recibe una referencia al de secondary).
     * - Carga la vista principal primary.fxml en una escena de 640x480 píxeles.
     * - Aplica la hoja de estilos Menu.css a la escena.
     * - Muestra la ventana principal con el título "Come_cocos".
     *
     * @param stage la ventana principal proporcionada por JavaFX.
     * @throws IOException si ocurre un error al cargar los archivos FXML.
     */
    @Override
    public void start(Stage stage) throws IOException {

        // Cargar secondary.fxml
        FXMLLoader loaderSecondary = new FXMLLoader(getClass().getResource("/Vistas/secondary.fxml"));
        secondaryRoot = loaderSecondary.load();
        secondaryController = loaderSecondary.getController();

        // Cargar tertiary.fxml
        FXMLLoader loaderTertiary = new FXMLLoader(getClass().getResource("/Vistas/tertiary.fxml"));
        tertiaryRoot = loaderTertiary.load();
        tertiaryController = loaderTertiary.getController();

        // Cargar cuarta.fxml
        FXMLLoader loaderCuarta = new FXMLLoader(getClass().getResource("/Vistas/cuarta.fxml"));
        cuartaRoot = loaderCuarta.load();
        cuartaController = loaderCuarta.getController();

        // Cargar quinta.fxml
        FXMLLoader loaderQuinta = new FXMLLoader(getClass().getResource("/Vistas/quinta.fxml"));
        quintaRoot = loaderQuinta.load();
        quintaController = loaderQuinta.getController();

        // Enlazar controladores
        tertiaryController.setSecondaryController(secondaryController);

        // Escena inicial
        scene = new Scene(loadFXML("primary"), 640, 480);
        // CSS menú
        scene.getStylesheets().add(getClass().getResource("/css/Menu.css").toExternalForm());

        stage.setTitle("Come_cocos");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Carga un archivo FXML y devuelve el objeto raíz de la vista.
     * 
     * Este método busca y carga un archivo FXML desde la carpeta de vistas del
     * proyecto.
     * Si el archivo no se encuentra, se lanza una excepción
     * {@link IllegalStateException}.
     * Una vez cargado el archivo FXML, se crea el objeto raíz correspondiente para
     * ser utilizado en la interfaz de usuario.
     * 
     * @param fxml El nombre del archivo FXML que se desea cargar (sin la extensión
     *             ".fxml").
     * @return Un objeto de tipo {@link Parent} que representa el nodo raíz de la
     *         vista cargada.
     * @throws IOException           Si ocurre un error al cargasr el archivo FXML.
     * @throws IllegalStateException Si el archivo FXML no se encuentra en la
     *                               ubicación esperada.
     */
    private static Parent loadFXML(String fxml) throws IOException {
        URL fxmlLocation = App.class.getResource("/Vistas/" + fxml + ".fxml");
        if (fxmlLocation == null) {
            throw new IllegalStateException("No se encontró el archivo FXML: " + fxml);
        }
        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        return loader.load();
    }

    /**
     * Establece la raíz de la escena a partir de un archivo FXML.
     * 
     * Este método carga un archivo FXML usando el método {@link loadFXML} y
     * establece el nodo raíz de la escena
     * con el contenido cargado del archivo. Esto permite cambiar la interfaz de
     * usuario cargando una nueva vista
     * desde un archivo FXML en tiempo de ejecución.
     * 
     * @param fxml El nombre del archivo FXML que se desea cargar (sin la extensión
     *             ".fxml").
     * @throws IOException Si ocurre un error al cargar el archivo FXML o al
     *                     establecer la raíz de la escena.
     */
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    // Métodos para cambiar vistas sin recargar
    /**
     * Muestra la vista secundaria cambiando la raíz de la escena.
     * 
     * Este método cambia la raíz de la escena a un nodo predefinido, que representa
     * la vista secundaria de la aplicación. Esto permite mostrar una interfaz
     * diferente
     * o una nueva pantalla sin necesidad de crear una nueva escena completa.
     * 
     * @throws IllegalStateException Si no se ha inicializado correctamente la raíz
     *                               secundaria.
     */
    public static void showSecondary() {
        scene.setRoot(secondaryRoot);
    }

    /**
     * Muestra la vista terciaria cambiando la raíz de la escena.
     * 
     * Este método cambia la raíz de la escena a un nodo predefinido, que representa
     * la vista terciaria de la aplicación. Esto permite mostrar una interfaz
     * diferente
     * o una nueva pantalla sin necesidad de crear una nueva escena completa.
     * 
     * @throws IllegalStateException Si no se ha inicializado correctamente la raíz
     *                               terciaria.
     */
    public static void showTertiary() {
        scene.setRoot(tertiaryRoot);
    }

    /**
     * Muestra la vista cuarta cambiando la raíz de la escena.
     * 
     * Este método cambia la raíz de la escena a un nodo predefinido, que representa
     * la vista cuarta de la aplicación. Permite mostrar una nueva pantalla o
     * interfaz
     * diferente sin necesidad de crear una nueva escena completa.
     * 
     * @throws IllegalStateException Si no se ha inicializado correctamente la raíz
     *                               cuarta.
     */
    public static void showCuarta() {
        scene.setRoot(cuartaRoot);
    }

    /**
     * Muestra la vista quinta cambiando la raíz de la escena.
     * 
     * Este método cambia la raíz de la escena a un nodo predefinido, que representa
     * la vista quinta de la aplicación. Permite mostrar una nueva pantalla o
     * interfaz
     * sin necesidad de crear una nueva escena completa.
     * 
     * @throws IllegalStateException Si no se ha inicializado correctamente la raíz
     *                               quinta.
     */
    public static void showQuinta() {
        scene.setRoot(quintaRoot);
    }

    public static void main(String[] args) {
        launch();
    }
}
