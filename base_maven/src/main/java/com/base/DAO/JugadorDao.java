
/**
 * Es la clase principal donde se gestionan los datos como actualizaciones y demas.
 * 
 * @author Daniel Alvarez Morales
 * @version 1.0
 * @since 2025
 * 
 */

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

    /**
     * Guarda la lista de jugadores en un archivo utilizando serialización de
     * objetos.
     *
     * @param ruta    La ruta del directorio donde se guardará el archivo.
     * @param fichero El nombre del archivo donde se guardarán los datos.
     * @throws Exception Puede lanzar una excepción general al intentar acceder al
     *                   sistema de archivos.
     */
    public void grabar_datos(String ruta, String fichero) throws Exception {
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

    /**
     * Establece la lista de jugadores.
     * 
     * @param listaJugadores La lista de objetos Jugador a establecer.
     * @throws Exception Si ocurre un error al establecer la lista de jugadores.
     */
    public void setListaJugadores(List<Jugador> listaJugadores) throws Exception {
        this.listaJugadores = listaJugadores;
    }

    /**
     * Recupera la lista de jugadores desde un archivo utilizando deserialización de
     * objetos.
     * 
     * @param ruta    La ruta del directorio donde se encuentra el archivo.
     * @param fichero El nombre del archivo desde el cual se recuperarán los datos.
     * @return Una lista de objetos Jugador recuperados desde el archivo.
     * @throws Exception Si ocurre un error al intentar leer el archivo o al
     *                   deserializar los datos.
     */
    public List<Jugador> recuperar_datos(String ruta, String fichero) {
        Path rutaDef = Paths.get(ruta, fichero);
        List<Jugador> listaRecuperar = new ArrayList<>();
        try {

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

    /**
     * Agrega un nuevo jugador a la lista de jugadores, si cumple con las
     * condiciones.
     * 
     * @param nombre El nombre del jugador a agregar.
     * @return `true` si el jugador se agregó con éxito, `false` si no se pudo
     *         agregar.
     */
    public boolean agregarJugador(String nombre) {

        if (nombre.equals("")) {
            System.out.println("Vacio, por lo tanto no permitido");
            return false;
        } else {

            if (listaJugadores.size() >= 4) {
                System.out.println("No se puede agregar mas de 4 jugadores");
                return false;
            } else {
                Jugador jugador = new Jugador(nombre);
                System.out.println("Dado de alta con exito al usuario " + jugador);

                listaJugadores.add(jugador);
                return true;
            }
        }
    }

    /**
     * Elimina un jugador de la lista según su ID.
     * 
     * @param busquedaId El ID del jugador a eliminar.
     * @return `true` si el jugador fue eliminado con éxito, `false` si no se
     *         encontró el jugador.
     */
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

    /**
     * Actualiza el nombre de un jugador en la lista según su ID.
     * 
     * @param busquedaId El ID del jugador a actualizar.
     * @param nombre     El nuevo nombre para el jugador.
     * @return `true` si el jugador fue actualizado correctamente, `false` si no se
     *         encontró el jugador.
     */
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

    /**
     * Actualiza los puntos de un jugador en la lista según su ID.
     * 
     * @param busquedaId El ID del jugador cuyo puntaje se actualizará.
     * @param puntos     El nuevo valor de puntos para el jugador.
     */
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

    /**
     * Ordena la lista de jugadores alfabéticamente según su nombre.
     * Si no hay jugadores en la lista, muestra un mensaje indicando que no hay
     * jugadores que mostrar.
     */
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

    /**
     * Ordena la lista de jugadores por puntos en orden ascendente.
     * Si no hay jugadores en la lista, muestra un mensaje indicando que no hay
     * jugadores que mostrar.
     */
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

    /**
     * Obtiene la lista de jugadores.
     * 
     * @return La lista de jugadores.
     */
    public List<Jugador> getListaJugadores() {
        return listaJugadores;
    }

    // BASE DE DATOS

    private Connection connection;

    /**
     * Constructor de la clase JugadorDao que inicializa la conexión a la base de
     * datos.
     * 
     * @param connection La conexión a la base de datos que se usará en este objeto.
     */
    public JugadorDao(Connection connection) {
        this.connection = connection;
    }

    // Método para obtener todos los jugadores
    /**
     * Obtiene la lista de jugadores desde la base de datos.
     * 
     * Este método ejecuta una consulta SQL para recuperar todos los jugadores de la
     * tabla "jugadores"
     * y los agrega a una lista. Cada jugador se crea con los datos recuperados de
     * la base de datos.
     * 
     * @return Una lista de objetos Jugador con los datos obtenidos de la base de
     *         datos.
     * @throws SQLException Si ocurre un error al ejecutar la consulta SQL o al
     *                      procesar los resultados.
     */
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

    /**
     * Inserta un nuevo jugador en la base de datos si no hay más de 12 jugadores.
     * 
     * Este método primero verifica cuántos jugadores existen en la base de datos.
     * Si ya hay 12 jugadores o más,
     * no se inserta el nuevo jugador. Si hay espacio, el jugador se inserta en la
     * base de datos y se asigna un ID generado
     * automáticamente por la base de datos. El ID generado se asigna al objeto
     * jugador.
     * 
     * @param jugador El objeto Jugador que se va a insertar en la base de datos.
     * @return true si el jugador fue insertado con éxito; false si no se pudo
     *         insertar.
     * @throws SQLException Si ocurre un error al ejecutar las consultas SQL.
     */
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
    /**
     * Actualiza el nombre de un jugador en la base de datos según su ID.
     * 
     * Este método realiza una actualización en la base de datos, cambiando el
     * nombre del jugador que corresponde al ID
     * proporcionado. Si se actualiza correctamente, devuelve true. Si no se
     * encuentra el jugador con el ID especificado,
     * o si no se realizaron cambios, devuelve false.
     * 
     * @param jugador El objeto Jugador que contiene el ID y el nuevo nombre a
     *                actualizar en la base de datos.
     * @return true si el jugador fue actualizado correctamente; false si no se
     *         encontró un jugador con el ID o si no se realizaron cambios.
     * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
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

    /**
     * Actualiza los puntos de un jugador en la base de datos según su ID.
     * 
     * Este método realiza una actualización en la base de datos, cambiando los
     * puntos del jugador correspondiente al ID
     * proporcionado. Si se actualiza correctamente, muestra un mensaje de éxito. Si
     * no se encuentra el jugador con el ID
     * especificado, o si no se realizaron cambios, muestra un mensaje de error.
     * 
     * @param jugador El objeto Jugador que contiene el ID y los nuevos puntos a
     *                actualizar en la base de datos.
     * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
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
    /**
     * Elimina un jugador de la base de datos según su ID.
     * 
     * Este método ejecuta una consulta SQL para eliminar al jugador cuyo ID es
     * proporcionado. Si el jugador es encontrado
     * y eliminado correctamente, se devuelve `true` y se muestra un mensaje de
     * éxito. Si no se encuentra un jugador con el
     * ID especificado, se devuelve `false` y se muestra un mensaje indicando que el
     * jugador no fue encontrado.
     * 
     * @param id El ID del jugador que se desea eliminar de la base de datos.
     * @return `true` si el jugador fue eliminado correctamente, `false` si no se
     *         encontró el jugador con el ID especificado.
     * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
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

    /**
     * Obtiene y muestra los jugadores ordenados por nombre de manera ascendente.
     * 
     * Este método ejecuta una consulta SQL que selecciona todos los jugadores de la
     * base de datos y los ordena
     * por el campo `nombre` de manera ascendente. Los resultados se imprimen por
     * consola, mostrando el ID y el nombre
     * de cada jugador. Si es necesario, se pueden obtener y mostrar más campos de
     * la tabla de jugadores.
     * 
     * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
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

    /**
     * Obtiene y muestra los jugadores ordenados por puntos de manera ascendente.
     * 
     * Este método ejecuta una consulta SQL que selecciona todos los jugadores de la
     * base de datos y los ordena
     * por el campo `puntos` de manera ascendente. Los resultados se imprimen por
     * consola, mostrando el ID y el nombre
     * de cada jugador. Si es necesario, se pueden obtener y mostrar más campos de
     * la tabla de jugadores.
     * 
     * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    public void ordenadosPorPuntos_online() throws SQLException {
        String query = "SELECT * FROM jugadores ORDER BY puntos ASC"; // Ordenar por puntos de manera ascendente

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

   

    /**
     * Obtiene una lista de jugadores ordenados alfabéticamente por nombre.
     * 
     * Este método ejecuta una consulta SQL para seleccionar todos los jugadores de
     * la base de datos,
     * ordenados por su nombre de manera ascendente. Cada jugador es añadido a una
     * lista de objetos `Jugador`
     * que se devuelve al final del método.
     * 
     * @return Una lista de objetos `Jugador` ordenados por nombre.
     * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
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

    /**
     * Obtiene una lista de jugadores ordenados por puntos de manera ascendente.
     * 
     * Este método ejecuta una consulta SQL para seleccionar todos los jugadores de
     * la base de datos,
     * ordenados por su puntuación (campo `puntos`) de manera ascendente. Cada
     * jugador es añadido a una lista de objetos `Jugador`
     * que se devuelve al final del método.
     * 
     * @return Una lista de objetos `Jugador` ordenados por puntos.
     * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
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