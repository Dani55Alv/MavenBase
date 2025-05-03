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

    public void agregarJugador(String nombre) {

        if (nombre.equals("")) {
            System.out.println("Vacio, por lo tanto no permitido");
        } else {
            Jugador jugador = new Jugador(nombre);
            System.out.println("Dado de alta con exito al usuario " + jugador);

            listaJugadores.add(jugador);
        }
    }

    public void eliminarJugador(Integer busquedaId) {
        String usuario = "";
        boolean elimimacionExitosa = false;

        Iterator<Jugador> iteradorJugador = listaJugadores.iterator();

        while (iteradorJugador.hasNext()) {
            Jugador jugador = iteradorJugador.next();

            if (jugador.getId() == (busquedaId)) {
                usuario = jugador.toString();
                iteradorJugador.remove();
                elimimacionExitosa = true;
            }
        }

        if (elimimacionExitosa == true) {
            System.out.println("Eliminacion del jugador ");
            System.out.println(usuario);
            System.out.println("con exito");
        } else {
            System.out.println("Jugador con dni: " + busquedaId + " no encontrado");

        }

    }

    public void actualizarJugador(Integer busquedaId, String nombre) {
        String usuario = "";
        String usuarioActualizado = "";

        boolean actualizacionExitosa = false;
        for (Jugador jugador : listaJugadores) {
            if (jugador.getId() == (busquedaId)) {
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
        } else {
            System.out.println("Jugador con dni: " + busquedaId + " no encontrado");

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

    // Método para insertar un jugador
    public void insertarJugador_online(Jugador jugador) throws SQLException {
        String query = "INSERT INTO jugadores (nombre, puntos) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, jugador.getNombre());
            stmt.setDouble(2, jugador.getPuntos());

            // Ejecutar la inserción
            stmt.executeUpdate();
            System.out.println("Jugador insertado");

            try (Statement idStmt = connection.createStatement();
                    ResultSet rs = idStmt.executeQuery("SELECT last_insert_rowid()")) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    jugador.setId(id);
                    System.out.println("Jugador insertado con id (manual): " + id);
                }
            }

        }
    }

    // Método para actualizar los puntos de un jugador
    public void actualizarJugador_online(Jugador jugador) throws SQLException {
        String query = "UPDATE jugadores SET nombre = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDouble(1, jugador.getId());
            stmt.setString(2, jugador.getNombre());
            stmt.executeUpdate();
        }
    }


    // Método para eliminar un jugador por su ID
    public void eliminarJugador_online(int id) throws SQLException {
        String query = "DELETE FROM jugadores WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id); // Establecer el ID del jugador a eliminar
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Jugador con ID " + id + " eliminado correctamente.");
            } else {
                System.out.println("No se encontró un jugador con el ID " + id);
            }
        }
    }

}
