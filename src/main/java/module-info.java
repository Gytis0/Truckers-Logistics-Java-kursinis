module trucks.truckers4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires mysql.connector.java;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.persistence;


    opens trucks.truckers4 to javafx.fxml;
    exports trucks.truckers4;

    exports trucks.truckers4.Model to org.hibernate.orm.core;
    opens trucks.truckers4.Model to org.hibernate.orm.core;

    exports trucks.truckers4.Controllers;
    opens trucks.truckers4.Controllers to javafx.fxml;

    exports trucks.truckers4.utils;
    opens trucks.truckers4.utils to javafx.fxml;
}