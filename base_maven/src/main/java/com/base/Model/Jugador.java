package com.base.Model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class Jugador implements Serializable {
    private static final long serialVersionUID = -4442119677483243372L; // SerialVersionUID único para evitar erroes de
                                                                        // serializacion

    private static Integer contadorId = 0; // contador global para IDs

    private Integer id; // id único para cada jugador
    private String nombre;
    private double puntos;

    // Inicializamos un nuevo jugador sin puntos.
    public Jugador(String nombre) {
        this.id = contadorId;
        this.nombre = nombre;
        this.puntos = 0;
        contadorId++;

    }

    // constructor vacio
    public Jugador() {
    }

    // Constructor que acepta nombre y puntos para la base de datos
    public Jugador(String nombre, double puntos) {
        this.nombre = nombre;
        this.puntos = puntos;
    }

    /**
     * Actualiza el contadorId global basado en la lista actual de jugadores.
     * Este método asegura que el próximo jugador tenga un ID único y secuencial.
     *
     * Si la lista está vacía, reinicia el contador a 1001.
     * Si hay jugadores, encuentra el ID más alto y establece el contador al
     * siguiente número.
     */
    public static void actualizarContadorId(List<Jugador> jugadores) {
        // Estático: para sincronizar el contador con la lista de jugadores recuperados

        // Si la lista está vacía, volvemos al valor inicial del contador
        if (jugadores == null || jugadores.isEmpty()) {
            contadorId = 0;
        } else {
            int maxId = jugadores.get(0).id; // Asumimos que hay al menos un jugador
            // Recorremos la lista para encontrar el ID más alto
            for (int i = 1; i < jugadores.size(); i++) {
                Jugador jugador = jugadores.get(i);
                if (jugador.id > maxId) {
                    maxId = jugador.id;
                }
            }
            // Establecemos el contador al siguiente valor disponible
            contadorId = maxId + 1;
        }
    }

    public void setId(Integer id) {
       // System.out.println("ID recibido en el setter: " + id);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPuntos() {
        return puntos;
    }

    public void setPuntos(double puntos) {
        this.puntos = puntos;
    }

    @Override
    public boolean equals(Object id_objeto) {
        if (this == id_objeto)
            return true;
        if (!(id_objeto instanceof Jugador))
            return false;
        Jugador jugador = (Jugador) id_objeto;
        return id == jugador.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Jugador{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", puntos=" + puntos +
                '}';
    }

}

// Druve manager
// inicializar base de datos y luego en seguido cargalo (todo en el constructor)
// desde los controller se llama los dao los dao modifica los datos y tal
// (ordena).