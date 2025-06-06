module lk.ijse.project_management_tool {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires static lombok;
    requires org.controlsfx.controls;
    requires com.jfoenix;
    requires jdk.compiler;


    opens lk.ijse.project_management_tool.controller to javafx.fxml;
    opens lk.ijse.project_management_tool.controller.auth to javafx.fxml;
    opens lk.ijse.project_management_tool.controller.layout to javafx.fxml;
    opens lk.ijse.project_management_tool.controller.component to javafx.fxml;
    exports lk.ijse.project_management_tool;
}