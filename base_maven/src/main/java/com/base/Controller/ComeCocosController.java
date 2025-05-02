package com.base.Controller;

import com.base.Model.Jugador;
import com.base.Tablero;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

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
        iniciarJuego();
    }

    private void iniciarJuego() {
        tablero.setCelda(posX, posY, 'C');
        actualizarVista();
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
            tablero.setCelda(posX, posY, 'C');
            actualizarVista();
        }
    }

    @FXML
    private void moverIzquierda() {
        if (posY > 0) {
            tablero.setCelda(posX, posY, '-');
            posY--;
            tablero.setCelda(posX, posY, 'C');
            actualizarVista();
        }
    }

    @FXML
    private void moverArriba() {
        if (posX > 0) {
            tablero.setCelda(posX, posY, '-');
            posX--;
            tablero.setCelda(posX, posY, 'C');
            actualizarVista();
        }
    }

    @FXML
    private void moverAbajo() {
        if (posX < 4) {
            tablero.setCelda(posX, posY, '-');
            posX++;
            tablero.setCelda(posX, posY, 'C');
            actualizarVista();
        }
    }
}
