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