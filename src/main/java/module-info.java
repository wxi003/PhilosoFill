/**
 * module-info of the package org.western
 */
module org.western {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens org.western.frontend to javafx.fxml;
    opens org.western.backend to com.google.gson;
    exports org.western.frontend;
    exports org.western.backend;
}