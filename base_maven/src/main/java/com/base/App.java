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
        quintaController.setCuartaController(cuartaController);
        tertiaryController.setSecondaryController(secondaryController);

        // Escena inicial
        scene = new Scene(loadFXML("primary"), 640, 480);
        stage.setTitle("Come_cocos");
        stage.setScene(scene);
        stage.show();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        URL fxmlLocation = App.class.getResource("/Vistas/" + fxml + ".fxml");
        if (fxmlLocation == null) {
            throw new IllegalStateException("No se encontró el archivo FXML: " + fxml);
        }
        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        return loader.load();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    // Métodos para cambiar vistas sin recargar
    public static void showSecondary() {
        scene.setRoot(secondaryRoot);
    }

    public static void showTertiary() {
        scene.setRoot(tertiaryRoot);
    }

    public static void showCuarta() {
        scene.setRoot(cuartaRoot);
    }

    public static void showQuinta() {
        scene.setRoot(quintaRoot);
    }

    // Nuevo getter para el controlador de Cuarta
    public static CuartaController getCuartaController() {
        return cuartaController;
    }

    public static void main(String[] args) {
        launch();
    }
}
