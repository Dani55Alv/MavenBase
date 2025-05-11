/**
 * Módulo que configura las dependencias necesarias para la aplicación,
 * incluyendo JavaFX y la conexión a una base de datos SQLite.
 * 
 * @author Daniel Alvarez Morales
 * @version 1.0
 * @since 2025
 * 
 *        Este módulo exporta los paquetes necesarios para la interfaz gráfica
 *        (JavaFX)
 *        y el acceso a la base de datos (SQLite), y abre los paquetes de los
 *        controladores
 *        y modelos para la interacción con FXML.
 */
module com.base {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires java.sql;
    requires javafx.graphics; // Para conectar con la bd

    opens com.base.Controller to javafx.fxml; // <- Esto permite acceso reflexivo desde FXML
    opens com.base.Model to javafx.base;

    exports com.base;
}

// Cada una de las vistas tinene su controller