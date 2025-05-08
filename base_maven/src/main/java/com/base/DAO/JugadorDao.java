package com.base.DAO;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.base.Model.Jugador;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class JugadorDao {

    public List<Jugador> listaJugadores;

    public JugadorDao(String nombreServidor) {
        this.listaJugadores = new ArrayList<>();
    }

    public void grabar_datos(String ruta, String fichero) {
        Path rutaDef = Paths.get(ruta, fichero);

        try {
            OutputStream archivoSalida = Files.newOutputStream(rutaDef);

            ObjectOutputStream flujoSalida = new ObjectOutputStream(archivoSalida);

            flujoSalida.writeObject(listaJugadores);

            System.out.println("Guardado con exito");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setListaJugadores(List<Jugador> listaJugadores) {
        this.listaJugadores = listaJugadores;
    }

    public List<Jugador> recuperar_datos(String ruta, String fichero) {
        Path rutaDef = Paths.get(ruta, fichero);
        List<Jugador> listaRecuperar = new ArrayList<>();
        try {

            // Escribir y leer desde estructura (arraylist,linked,map...) no directamente
            // sin estructua.
            // No tiene porque se siempre en memoria pero en ese caso si.

            InputStream archivoEntrada = Files.newInputStream(rutaDef);

            ObjectInputStream flujoEntrada = new ObjectInputStream(archivoEntrada);

            listaRecuperar = (List<Jugador>) flujoEntrada.readObject(); // Esto requiere excepción general

            for (Jugador jugador : listaRecuperar) {
                System.out.println(jugador);
            }
            System.out.println("recuperado con exito");
            return listaRecuperar;
        } catch (Exception e) {

            e.printStackTrace();
            return listaRecuperar;

        }
    }

    public boolean agregarJugador(String nombre) {

        if (nombre.equals("")) {
            System.out.println("Vacio, por lo tanto no permitido");
            return false;
        } else {

            if (listaJugadores.size()>=4) {
                System.out.println("No se puede agregar mas de 4 jugadores");
                return false;
            }else{
            Jugador jugador = new Jugador(nombre);
            System.out.println("Dado de alta con exito al usuario " + jugador);

            listaJugadores.add(jugador);
            return true;
               }
        }
    }

    public boolean eliminarJugador(Integer busquedaId) {
        System.out.println("Jugadores en la lista antes de eliminar:");
        for (Jugador jugador : listaJugadores) {
            System.out.println(jugador); // Aquí sí imprime
        }

        boolean eliminado = false;

        Iterator<Jugador> iteradorJugador = listaJugadores.iterator();
        while (iteradorJugador.hasNext()) {
            Jugador jugador = iteradorJugador.next();
            System.out.println("Comparando: jugador.id=" + jugador.getId() + " con buscado=" + busquedaId);

            if (jugador.getId().equals(busquedaId)) {

                iteradorJugador.remove();
                eliminado = true;
                System.out.println("Jugador eliminado: " + jugador);
                return eliminado;

            }
        }

        if (!eliminado) {
            System.out.println("Jugador con id: " + busquedaId + " no eliminado");

            return eliminado;

        }

        System.out.println("Jugadores en la lista después de eliminar:");
        for (Jugador jugador : listaJugadores) {
            System.out.println(jugador);
        }
        return eliminado;

    }

    public boolean actualizarJugador(Integer busquedaId, String nombre) {
        String usuario = "";
        String usuarioActualizado = "";

        boolean actualizacionExitosa = false;
        for (Jugador jugador : listaJugadores) {
            if (jugador.getId().equals(busquedaId)) {
                usuario = jugador.toString();

                jugador.setNombre(nombre);

                usuarioActualizado = jugador.toString();
                actualizacionExitosa = true;

            }
        }
        if (actualizacionExitosa == true) {
            System.out.println("Actualizacion del usuario ");
            System.out.println(usuario);
            System.out.println("a");
            System.out.println(usuarioActualizado);
            System.out.println("con exito");
            return actualizacionExitosa;

        } else {
            System.out.println("Jugador con id: " + busquedaId + " no encontrado");
            return actualizacionExitosa;

        }

    }

    public void actualizarJugadorPuntos(Integer busquedaId, double puntos) {
        String usuario = "";
        String usuarioActualizado = "";

        boolean actualizacionExitosa = false;
        for (Jugador jugador : listaJugadores) {
            if (jugador.getId().equals(busquedaId)) {
                usuario = jugador.toString();

                jugador.setPuntos(puntos);

                usuarioActualizado = jugador.toString();
                actualizacionExitosa = true;

            }
        }
        if (actualizacionExitosa == true) {
            System.out.println("Actualizacion del usuario ");
            System.out.println(usuario);
            System.out.println("a puntos: ");
            System.out.println(usuarioActualizado);
            System.out.println("con exito");
        } else {
            System.out.println("Jugador con id: " + busquedaId + " no encontrado");

        }

    }

    public void ordenarNombreAlfabeticamente() {
        boolean noHayJugadores = true;
        for (Jugador jugador : listaJugadores) {
            noHayJugadores = false;
        }

        if (noHayJugadores) {
            System.out.println("NO HAY JUGADORES QUE MOSTRAR");
        } else {
            Comparator<Jugador> comparadorNombre = Comparator.comparing(Jugador::getNombre);
            listaJugadores.sort(comparadorNombre);

            for (Jugador jugadores : listaJugadores) {
                System.out.println(jugadores);
            }

        }
    }

    public void ordenarNombrePuntos() {
        boolean noHayJugadores = true;
        for (Jugador jugador : listaJugadores) {
            noHayJugadores = false;
        }

        if (noHayJugadores) {
            System.out.println("NO HAY JUGADORES QUE MOSTRAR");
        } else {
            Comparator<Jugador> comparadorPuntos = Comparator.comparing(Jugador::getPuntos);
            listaJugadores.sort(comparadorPuntos);

            for (Jugador jugadores : listaJugadores) {
                System.out.println(jugadores);
            }

        }
    }

    public List<Jugador> getListaJugadores() {
        return listaJugadores;
    }

    // BASE DE DATOS

    private Connection connection;

    public JugadorDao(Connection connection) {
        this.connection = connection;
    }

    // Método para obtener todos los jugadores
    public List<Jugador> obtenerJugadores_online() throws SQLException {

        List<Jugador> jugadores = new ArrayList<>();
        String query = "SELECT * FROM jugadores"; // Ajusta esto según tu tabla
        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Jugador jugador = new Jugador(); // Recuperas los atributos
                jugador.setId(rs.getInt("id"));
                jugador.setNombre(rs.getString("nombre"));
                jugador.setPuntos(rs.getDouble("puntos"));
                jugadores.add(jugador);
            }
        }
        System.out.println("Jugadores obtenidos");

        return jugadores;

    }

    public boolean insertarJugador_online(Jugador jugador) throws SQLException {
        // Verificamos cuántos jugadores hay ya en la base de datos
        String countQuery = "SELECT COUNT(*) FROM jugadores";
        try (Statement countStmt = connection.createStatement();
                ResultSet countRs = countStmt.executeQuery(countQuery)) {

            if (countRs.next()) {
                int cantidadJugadores = countRs.getInt(1);
                if (cantidadJugadores >= 12) {
                    System.out.println("No se puede insertar: ya hay 12 jugadores o más.");
                    return false;
                }
            }
        }

        // Insertamos el jugador si hay espacio
        String insertQuery = "INSERT INTO jugadores (nombre, puntos) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(insertQuery)) {
            stmt.setString(1, jugador.getNombre());
            stmt.setDouble(2, jugador.getPuntos());

            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas > 0) {
                // Usamos SELECT last_insert_rowid(); para obtener el ID en SQLite
                try (Statement stmt2 = connection.createStatement();
                        ResultSet rs = stmt2.executeQuery("SELECT last_insert_rowid();")) {
                    if (rs.next()) {
                        int idGenerado = rs.getInt(1);
                        jugador.setId(idGenerado);
                        System.out.println("Jugador insertado con ID real: " + idGenerado);
                    }
                }
                return true;
            }
        }

        return false; // En caso de que no se insertara por alguna razón
    }

    // Método para actualizar el nombre de un jugador
    // Método para actualizar el nombre de un jugador
    public boolean actualizarJugador_online(Jugador jugador) throws SQLException {
        System.out.println("Actualizando jugador con ID: " + jugador.getId()); // Depuración
        String query = "UPDATE jugadores SET nombre = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, jugador.getNombre());
            stmt.setInt(2, jugador.getId());

            // Verificación adicional antes de ejecutar
            System.out.println("ID del jugador en el PreparedStatement: " + jugador.getId());

            int rowsAffected = stmt.executeUpdate();

            System.out.println("Filas afectadas: " + rowsAffected);
            if (rowsAffected > 0) {
                System.out.println("Jugador actualizado correctamente.");
                return true;
            } else {
                System.out.println("No se encontró un jugador con el ID: " + jugador.getId());
                return false;
            }
        }
    }

    public void actualizarJugadorPuntos_online(Jugador jugador) throws SQLException {
        System.out.println("Actualizando jugador con ID: " + jugador.getId()); // Depuración
        String query = "UPDATE jugadores SET puntos = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDouble(1, jugador.getPuntos());
            stmt.setInt(2, jugador.getId());

            // Verificación adicional antes de ejecutar
            System.out.println("ID del jugador en el PreparedStatement: " + jugador.getId());

            int rowsAffected = stmt.executeUpdate();

            System.out.println("Filas afectadas: " + rowsAffected);
            if (rowsAffected > 0) {
                System.out.println("Jugador actualizado correctamente.");
            } else {
                System.out.println("No se encontró un jugador con el ID: " + jugador.getId());
            }
        }
    }

    // Método para eliminar un jugador por su ID
    public boolean eliminarJugador_online(int id) throws SQLException {
        String query = "DELETE FROM jugadores WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id); // Establecer el ID del jugador a eliminar
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Jugador con ID " + id + " eliminado correctamente.");
                return true;
            } else {
                System.out.println("No se encontró un jugador con el ID " + id);
                return false;
            }
        }
    }

    // Método para obtener los jugadores ordenados por nombre
    public void ordenadosPorNombre_online() throws SQLException {
        String query = "SELECT * FROM jugadores ORDER BY nombre ASC"; // Ordenar por nombre de manera ascendente

        try (PreparedStatement stmt = connection.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                // Aquí puedes obtener más campos si los necesitas, por ejemplo:
                // String otroCampo = rs.getString("campo");

                System.out.println("ID: " + id + ", Nombre: " + nombre);
            }
        }

    }

    // Método para obtener los jugadores ordenados por puntos
    public void ordenadosPorPuntos_online() throws SQLException {
        String query = "SELECT * FROM jugadores ORDER BY puntos ASC"; // Ordenar por nombre de manera ascendente

        try (PreparedStatement stmt = connection.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                // Aquí puedes obtener más campos si los necesitas, por ejemplo:
                // String otroCampo = rs.getString("campo");

                System.out.println("ID: " + id + ", Nombre: " + nombre);

            }
        }

    }

    public List<Jugador> getJugadores() throws SQLException {
        List<Jugador> jugadores = new ArrayList<>();
        String query = "SELECT * FROM jugadores";
        try (PreparedStatement stmt = connection.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {

                Jugador jugador = new Jugador();

                jugador.setId(rs.getInt("id"));
                jugador.setNombre(rs.getString("nombre"));
                jugadores.add(jugador);
            }
        }
        return jugadores;
    }

    public List<Jugador> obtenerJugadoresNombresOrdenados_online() throws SQLException {
        String sql = "SELECT * FROM jugadores ORDER BY nombre ASC";
        List<Jugador> lista = new ArrayList<>();
        try (Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Jugador j = new Jugador(); // usa el constructor vacío
                j.setId(rs.getInt("id")); // asigna el id
                j.setNombre(rs.getString("nombre")); // asigna el nombre
                j.setPuntos(rs.getDouble("puntos")); // asigna los puntos
                lista.add(j);
            }
        }
        return lista;
    }

    public List<Jugador> obtenerJugadoresPuntosOrdenados_online() throws SQLException {
        String sql = "SELECT * FROM jugadores ORDER BY puntos ASC";
        List<Jugador> lista = new ArrayList<>();
        try (Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Jugador j = new Jugador(); // usa el constructor vacío
                j.setId(rs.getInt("id")); // asigna el id
                j.setNombre(rs.getString("nombre")); // asigna el nombre
                j.setPuntos(rs.getDouble("puntos")); // asigna los puntos
                lista.add(j);
            }
        }
        return lista;
    }

}