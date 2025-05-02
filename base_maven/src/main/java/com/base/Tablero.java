package com.base;

public class Tablero {
    private char[][] tablero;

    public Tablero(int filas, int columnas) {
        tablero = new char[filas][columnas];
        inicializarTablero();
    }

    private void inicializarTablero() {
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                tablero[i][j] = '-'; // valor por defecto
            }
        }
    }

    // Método para obtener el valor en la posición (fila, columna)
    public char getCelda(int fila, int columna) {
        return tablero[fila][columna];
    }

    // Método para cambiar el valor en la posición (fila, columna)
    public void setCelda(int fila, int columna, char valor) {
        tablero[fila][columna] = valor;
    }

    public void mostrarTablero() {
        for (char[] fila : tablero) {
            for (char celda : fila) {
                System.out.print(celda + " ");
            }
            System.out.println();
        }
    }

    // Puedes añadir más métodos como colocarPieza(), mover(), etc.
}
