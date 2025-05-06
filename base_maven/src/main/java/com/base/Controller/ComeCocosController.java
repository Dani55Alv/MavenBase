package com.base.Controller;

import com.base.Model.Jugador;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.base.App;
import com.base.Tablero;
import com.base.DAO.JugadorDao;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

public class ComeCocosController {

    private Tablero tablero;
    private Jugador jugador;
    private int posX = 0;
    private int posY = 0;

    @FXML
    private GridPane gridTablero;

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
        this.tablero = new Tablero(5, 5);

        labelNombreJugador.setText("Jugador: " + jugador.getNombre());
        labelPuntos.setText("Puntos: " + jugador.getPuntos());
        iniciarJuego();
    }

    private void iniciarJuego() {
        tablero.setCelda(posX, posY, 'C');
        actualizarVista();
    }

    private void ganarPuntos() {

        if (tablero.getCelda(posX, posY) == '*') {
            jugador.setPuntos(jugador.getPuntos() + 1); // sumamos 1 punto
            labelPuntos.setText("Puntos: " + jugador.getPuntos()); // actualizamos la vista
        }

    }

    private void actualizarVista() {
        gridTablero.getChildren().clear();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                char valor = tablero.getCelda(i, j);
                Label label = new Label(String.valueOf(valor));
                label.setMinSize(30, 30);
                label.setStyle("-fx-border-color: black; -fx-alignment: center;");
                gridTablero.add(label, j, i);
            }
        }
    }

    @FXML
    private void moverDerecha() {
        if (posY < 4) {
            tablero.setCelda(posX, posY, '-');
            posY++;
            ganarPuntos();

            // Llamamos al método de la vista para que muestre el spray
            // vista.mostrarSpray(posX, posY);

            tablero.setCelda(posX, posY, 'C');
            tablero.regenerarPuntos(5);
            actualizarVista();
        }
    }

    @FXML
    private void moverIzquierda() {
        if (posY > 0) {
            tablero.setCelda(posX, posY, '-');
            posY--;
            ganarPuntos();

            tablero.setCelda(posX, posY, 'C');
            tablero.regenerarPuntos(5);

            actualizarVista();
        }
    }

    @FXML
    private void moverArriba() {
        if (posX > 0) {
            tablero.setCelda(posX, posY, '-');
            posX--;
            ganarPuntos();

            tablero.setCelda(posX, posY, 'C');
            tablero.regenerarPuntos(5);

            actualizarVista();
        }

    }

    @FXML
    private void moverAbajo() {
        if (posX < 4) {
            tablero.setCelda(posX, posY, '-');
            posX++;
            ganarPuntos();

            tablero.setCelda(posX, posY, 'C');

            tablero.regenerarPuntos(5);

            actualizarVista();
        }
    }

    // Muestra el nombre y los puntos

    @FXML
    private Label labelNombreJugador;

    @FXML
    private Label labelPuntos;

    @FXML
    private Button cerrar_ventana;

    @FXML
    private void cerrarVentana() throws IOException {
        cerrar_ventana.getScene().getWindow().hide();

        getPuntosEid();
    }

    public List<Object> getPuntosEid() {
        List<Object> puntosEid = new ArrayList<>();
        puntosEid.add(jugador.getId());
        puntosEid.add(jugador.getPuntos());
        System.out.println("Puntos actualizados obtenidos al cerrar ventana");
        System.out.println(puntosEid.size());
        return puntosEid;
    }

}