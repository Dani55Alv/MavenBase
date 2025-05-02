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

    // Guardamos las raíces y controladores
    private static Parent secondaryRoot;
    private static Parent tertiaryRoot;
    private static Parent quintaRoot;

    private static SecondaryController secondaryController;
    private static TertiaryController tertiaryController;
    private static Parent cuartaRoot;
    private static QuintaController quintaController;
    private static CuartaController cuartaController;

    @Override
    public void start(Stage stage) throws IOException {

        // Cargar secondary.fxml
        FXMLLoader loaderSecondary = new FXMLLoader(App.class.getResource("/Vistas/secondary.fxml"));
        secondaryRoot = loaderSecondary.load();
        secondaryController = loaderSecondary.getController();

        // Cargar tertiary.fxml
        FXMLLoader loaderTertiary = new FXMLLoader(App.class.getResource("/Vistas/tertiary.fxml"));
        tertiaryRoot = loaderTertiary.load();
        tertiaryController = loaderTertiary.getController();

        // Cargar cuarta.fxml
        FXMLLoader loaderCuarta = new FXMLLoader(App.class.getResource("/Vistas/cuarta.fxml"));
        cuartaRoot = loaderCuarta.load();
        cuartaController = loaderCuarta.getController();

        // Cargar quinta.fxml
        FXMLLoader loaderQuinta = new FXMLLoader(App.class.getResource("/Vistas/quinta.fxml"));
        quintaRoot = loaderQuinta.load();
        quintaController = loaderQuinta.getController();

        // Enlazar los controladores
        tertiaryController.setSecondaryController(secondaryController);

        // Cargar la escena inicial
        scene = new Scene(loadFXML("/Vistas/primary"), 640, 480);
        stage.setTitle("Come_cocos");
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML("/Vistas/" + fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        URL fxmlLocation = App.class.getResource(fxml + ".fxml");
        if (fxmlLocation == null) {
            throw new IllegalStateException("No se encontró el archivo FXML: " + fxml + ".fxml");
        }
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
        return fxmlLoader.load();
    }

    // NUEVOS MÉTODOS para cambiar a secondary y tertiary sin recargar
    public static void showSecondary() {
        scene.setRoot(secondaryRoot);
    }

    public static void showTertiary() {
        scene.setRoot(tertiaryRoot);
    }

    // Mas pasos de vistas sin recargar
    public static void showCuarta() {
        scene.setRoot(cuartaRoot);
    }

    public static void showQuinta() {
        scene.setRoot(quintaRoot);
    }

    public static void main(String[] args) {
        launch();
    }
}
