
/**
 * Es el modelo donde se guardan el comportamient y atributos del tablero
 * 
 * @author Daniel Alvarez Morales
 * @version 1.0
 * @since 2025
 * 
 */
package com.base.Model;

public class Tablero {
    private char[][] tablero;

    /**
     * Constructor que crea un tablero de tamaño filas x columnas.
     * 
     * @param filas    Número de filas del tablero.
     * @param columnas Número de columnas del tablero.
     * 
     *                 Inicializa la matriz del tablero con el tamaño especificado y
     *                 llama al
     *                 método {@link #inicializarTablero()} para llenarlo con
     *                 valores predeterminados.
     */
    public Tablero(int filas, int columnas) {
        tablero = new char[filas][columnas];
        inicializarTablero();
    }

    /**
     * Genera puntos en el tablero.
     * 
     * Este método coloca un número específico de puntos ('*') en posiciones
     * aleatorias
     * dentro del tablero. Un punto solo se colocará en una celda vacía ('-'). El
     * proceso
     * se repite hasta que se hayan generado la cantidad de puntos solicitada.
     * 
     * @param cantidad El número de puntos a generar en el tablero.
     */
    public void generarPuntos(int cantidad) {
        int generados = 0;
        while (generados < cantidad) {
            int fila = (int) (Math.random() * tablero.length);
            int columna = (int) (Math.random() * tablero[0].length);
            if (tablero[fila][columna] == '-') {
                tablero[fila][columna] = '*'; // símbolo del punto
                generados++;
            }
        }
    }

    /**
     * Inicializa el tablero con valores predeterminados.
     * 
     * Este método recorre todo el tablero y asigna el valor predeterminado ('-') a
     * cada celda, lo que indica que la celda está vacía. Luego, genera una cantidad
     * de puntos en posiciones aleatorias en el tablero, utilizando el método
     * {@link #generarPuntos(int)}.
     */
    private void inicializarTablero() {
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                tablero[i][j] = '-'; // valor por defecto
            }
        }
        generarPuntos(5); // genera 5 puntos en el tablero
    }

    /**
     * Obtiene el valor de la celda en una posición específica del tablero.
     * 
     * Este método devuelve el valor almacenado en la celda correspondiente a la
     * fila y columna indicadas del tablero.
     *
     * @param fila    La fila en la que se encuentra la celda.
     * @param columna La columna en la que se encuentra la celda.
     * @return El valor de la celda en la posición especificada.
     */
    public char getCelda(int fila, int columna) {
        return tablero[fila][columna];
    }

    /**
     * Cambia el valor de la celda en una posición específica del tablero.
     * 
     * Este método permite modificar el valor almacenado en la celda correspondiente
     * a la fila y columna indicadas en el tablero.
     *
     * @param fila    La fila en la que se encuentra la celda a modificar.
     * @param columna La columna en la que se encuentra la celda a modificar.
     * @param valor   El nuevo valor que se desea asignar a la celda.
     */
    public void setCelda(int fila, int columna, char valor) {
        tablero[fila][columna] = valor;
    }

    /**
     * Muestra el estado actual del tablero en la consola.
     * 
     * Este método imprime el tablero completo fila por fila en la consola,
     * mostrando
     * los valores de cada celda separados por un espacio. Al final de cada fila,
     * se realiza un salto de línea para representar el tablero de manera
     * estructurada.
     */
    public void mostrarTablero() {
        for (char[] fila : tablero) {
            for (char celda : fila) {
                System.out.print(celda + " ");
            }
            System.out.println();
        }
    }



    /**
     * Cuenta la cantidad de puntos restantes en el tablero.
     * 
     * Este método recorre todo el tablero y cuenta cuántos símbolos de puntos ('*')
     * hay en las celdas. El contador se incrementa cada vez que se encuentra un
     * punto, y al final el método retorna la cantidad total de puntos restantes.
     * 
     * @return El número de puntos ('*') restantes en el tablero.
     */
    public int contarPuntosRestantes() {
        int contador = 0;
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                if (tablero[i][j] == '*') {
                    contador++;
                }
            }
        }
        return contador;
    }
    

    /**
     * Regenera puntos en el tablero si no quedan puntos restantes.
     * 
     * Este método verifica si el tablero ya no tiene puntos (es decir, si el
     * contador
     * de puntos restantes es cero). Si es así, llama al método
     * {@link #generarPuntos(int)}
     * para generar una nueva cantidad de puntos especificada por el parámetro
     * `cantidad`.
     * 
     * @param cantidad La cantidad de puntos a generar si no quedan puntos restantes
     *                 en el tablero.
     */
    public void regenerarPuntos(int cantidad) {
        if (contarPuntosRestantes() == 0) {
            generarPuntos(cantidad);
        }
    }

}
