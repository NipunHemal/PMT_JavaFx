package lk.ijse.project_management_tool.controller.auth;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import lk.ijse.project_management_tool.utils.ReferenceUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public StackPane dialpgPane;
    public AnchorPane mainContainer;
    public TextField txtUserName;
    public PasswordField txtPassword;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ReferenceUtils.dialogPane = dialpgPane;
    }

    public void btnLogin(ActionEvent actionEvent) {
        navigator("/view/layout/DashboardLayout.fxml");
    }

    void navigator(String fxml) {
        try {
            mainContainer.getChildren().clear();
            AnchorPane pane  = FXMLLoader.load(getClass().getResource(fxml));

            mainContainer.prefWidthProperty().bind(dialpgPane.widthProperty());
            mainContainer.prefHeightProperty().bind(dialpgPane.heightProperty());

            pane.prefWidthProperty().bind(dialpgPane.widthProperty());
            pane.prefHeightProperty().bind(dialpgPane.heightProperty());


            mainContainer.getChildren().add(pane);
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

}
