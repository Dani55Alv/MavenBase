/**
 * Es el controlador del minijuego come_cocos
 * 
 * @author Daniel Alvarez Morales
 * @version 1.0
 * @since 2025
 * 
 */

package com.base.Controller;

import com.base.Model.Jugador;
import com.base.Model.Tablero;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.base.App;
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

    /**
     * Establece el jugador y configura el tablero del juego.
     * 
     * @param jugador El objeto Jugador que se va a establecer.
     */
    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
        this.tablero = new Tablero(5, 5);

        // Establecer el nombre del jugador en la etiqueta correspondiente
        labelNombreJugador.setText("Jugador: " + jugador.getNombre());

        // Establecer los puntos del jugador en la etiqueta correspondiente
        labelPuntos.setText("Puntos: " + jugador.getPuntos());

        // Iniciar el juego
        iniciarJuego();
    }

    /**
     * Inicializa el juego colocando al jugador en la posición inicial
     * y actualizando la vista del tablero.
     */
    private void iniciarJuego() {
        tablero.setCelda(posX, posY, 'C');
        actualizarVista();
    }

    /**
     * Verifica si el jugador ha encontrado un punto en la celda actual
     * y, en ese caso, incrementa sus puntos y actualiza la vista.
     */
    private void ganarPuntos() {

        if (tablero.getCelda(posX, posY) == '*') {
            jugador.setPuntos(jugador.getPuntos() + 1); // sumamos 1 punto
            labelPuntos.setText("Puntos: " + jugador.getPuntos()); // actualizamos la vista
        }

    }

    /**
     * Actualiza visualmente el tablero en la interfaz gráfica.
     * Limpia el grid y lo vuelve a construir con los valores actuales del tablero.
     */
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

    /**
     * Mueve al jugador una celda a la derecha si no ha llegado al borde del
     * tablero.
     * Actualiza la posición, suma puntos si corresponde, regenera puntos en el
     * tablero
     * y actualiza la vista.
     */
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

    /**
     * Mueve al jugador una celda a la izquierda si no ha llegado al borde del
     * tablero.
     * Actualiza la posición, suma puntos si corresponde, regenera puntos en el
     * tablero
     * y actualiza la vista.
     */
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

    /**
     * Mueve al jugador una celda hacia arriba si no ha llegado al borde superior
     * del tablero.
     * Actualiza la posición, suma puntos si corresponde, regenera puntos en el
     * tablero
     * y actualiza la vista.
     */
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

    /**
     * Mueve al jugador una celda hacia abajo si no ha llegado al borde inferior del
     * tablero.
     * Actualiza la posición, suma puntos si corresponde, regenera puntos en el
     * tablero
     * y actualiza la vista.
     */
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

    /**
     * Cierra la ventana actual de la aplicación y llama al método para obtener
     * los puntos y el ID del jugador.
     *
     * @throws IOException si ocurre un error al realizar operaciones de
     *                     entrada/salida.
     */
    @FXML
    private void cerrarVentana() throws IOException {
        cerrar_ventana.getScene().getWindow().hide();

        getPuntosEid();
    }

    /**
     * Obtiene una lista con el ID del jugador y sus puntos actuales.
     * Imprime información en consola para depuración.
     *
     * @return Una lista de objetos que contiene el ID y los puntos del jugador.
     */
    public List<Object> getPuntosEid() {
        List<Object> puntosEid = new ArrayList<>();
        puntosEid.add(jugador.getId());
        puntosEid.add(jugador.getPuntos());
        System.out.println("Puntos actualizados obtenidos al cerrar ventana");
        System.out.println(puntosEid.size());
        return puntosEid;
    }

}