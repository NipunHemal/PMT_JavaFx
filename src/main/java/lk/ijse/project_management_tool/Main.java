package lk.ijse.project_management_tool;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lk.ijse.project_management_tool.utils.ReferenceUtils;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        ReferenceUtils.primaryStage = stage;
       Parent parent = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
       Scene scene = new Scene(parent);
       stage.setScene(scene);
       stage.setTitle("Project Management Tool");
       stage.show();
    }
}