module lk.ijse.project_management_tool {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires static lombok;
    requires org.controlsfx.controls;
    requires com.jfoenix;


    opens lk.ijse.project_management_tool.controller to javafx.fxml;
    exports lk.ijse.project_management_tool;
}