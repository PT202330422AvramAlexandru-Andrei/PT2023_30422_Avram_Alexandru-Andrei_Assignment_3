module OrdersManagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires java.logging;
    requires java.xml;
    requires java.base;
    requires java.compiler;
    requires java.datatransfer;

    opens start to javafx.fxml;
    opens presentation to javafx.fxml;
    exports start to javafx.graphics;
    exports presentation to javafx.fxml;
}