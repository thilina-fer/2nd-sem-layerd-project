
module lk.ijse.layerd_project_2nd_sem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires static lombok;
    requires java.desktop;
    requires jakarta.mail;

    opens lk.ijse.layerd_project_2nd_sem to javafx.fxml;
    opens lk.ijse.layerd_project_2nd_sem.controller to javafx.fxml;

    // Open to both javafx.fxml and javafx.base

    opens lk.ijse.layerd_project_2nd_sem.view to javafx.fxml, javafx.base;

    exports lk.ijse.layerd_project_2nd_sem;
    exports lk.ijse.layerd_project_2nd_sem.controller;
}
