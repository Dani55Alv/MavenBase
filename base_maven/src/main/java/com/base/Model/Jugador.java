
/**
 * Es el modelo jugador donde se guardan su comportamiento y atributos.
 * 
 * @author Daniel Alvarez Morales
 * @version 1.0
 * @since 2025
 * 
 */

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
    /**
     * Constructor de la clase Jugador. Crea un nuevo jugador con un nombre,
     * asigna un ID único y establece los puntos iniciales a cero.
     * 
     * El ID del jugador se genera automáticamente mediante un contador estático
     * que incrementa con cada nuevo jugador creado. El contador asegura que
     * cada jugador tenga un ID único dentro de la aplicación.
     * 
     * @param nombre El nombre del jugador. Este parámetro se usa para asignar
     *               el nombre al nuevo jugador.
     */
    public Jugador(String nombre) {
        this.id = contadorId;
        this.nombre = nombre;
        this.puntos = 0;
        contadorId++; // Incrementa el contador para el siguiente jugador
    }

    // constructor vacio para poder trabajar con sets
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

    /**
     * Establece el ID del jugador.
     * 
     * @param id El ID a asignar al jugador.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtiene el ID del jugador.
     * 
     * @return El ID del jugador.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Obtiene el nombre del jugador.
     * 
     * @return El nombre del jugador.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del jugador.
     * 
     * @param nombre El nombre a asignar al jugador.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene los puntos del jugador.
     * 
     * @return Los puntos del jugador.
     */
    public double getPuntos() {
        return puntos;
    }

    /**
     * Establece los puntos del jugador.
     * 
     * @param puntos Los puntos a asignar al jugador.
     */
    public void setPuntos(double puntos) {
        this.puntos = puntos;
    }

    /**
     * Compara si dos objetos son iguales, comparando su ID.
     * 
     * @param id_objeto El objeto con el que comparar.
     * @return true si los objetos son iguales (tienen el mismo ID), false en caso
     *         contrario.
     */
    @Override
    public boolean equals(Object id_objeto) {
        if (this == id_objeto)
            return true;
        if (!(id_objeto instanceof Jugador))
            return false;
        Jugador jugador = (Jugador) id_objeto;
        return id == jugador.id;
    }

    /**
     * Genera un valor hash basado en el ID del jugador, utilizado en colecciones
     * como HashSet y HashMap.
     * 
     * @return Un valor hash para el jugador.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

/**
 * Devuelve una representación en cadena del jugador.
 * 
 * @return Una cadena con los detalles del jugador (ID, nombre, puntos).
 */
@Override
public String toString() {
    return "Jugador{" +
            "id=" + id +
            ", nombre='" + nombre + '\'' +
            ", puntos=" + puntos +
            '}';
}
}