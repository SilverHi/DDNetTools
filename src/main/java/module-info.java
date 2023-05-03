module com.silver.ddnetbindtools {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.silver.ddnetbindtools to javafx.fxml;
    exports com.silver.ddnetbindtools;
    exports com.silver.ddnetbindtools.controller;
    opens com.silver.ddnetbindtools.controller to javafx.fxml;
    exports com.silver.ddnetbindtools.utils;
    opens com.silver.ddnetbindtools.utils to javafx.fxml;
    exports com.silver.ddnetbindtools.enums;
    opens com.silver.ddnetbindtools.enums to javafx.fxml;
}