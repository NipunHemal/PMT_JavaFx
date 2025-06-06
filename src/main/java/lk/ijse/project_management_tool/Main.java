package lk.ijse.project_management_tool;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lk.ijse.project_management_tool.db.DbConnector;
import lk.ijse.project_management_tool.utils.ReferenceUtils;

public class Main extends Application {
    public static void main(String[] args) {
        connectDatabase();
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        ReferenceUtils.primaryStage = stage;
       Parent parent = FXMLLoader.load(getClass().getResource("/view/Auth/Login.fxml"));
       Scene scene = new Scene(parent);
       stage.setScene(scene);
       stage.setTitle("Project Management Tool");
       stage.show();
    }

    public static void connectDatabase(){
        try{
            DbConnector.getInstance().getConnection();
            System.out.println("Connected successfully");
        } catch (Exception e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
            System.exit(1);
        }
    }
}