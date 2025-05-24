package lk.ijse.project_management_tool.controller;

import com.jfoenix.controls.JFXDialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import lk.ijse.project_management_tool.utils.AlertUtils;
import lk.ijse.project_management_tool.utils.DialogUtil;
import lk.ijse.project_management_tool.utils.NotificationUtils;
import lk.ijse.project_management_tool.utils.ReferenceUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public StackPane dialpgPane;

    public void btnNotifiOnClick(ActionEvent actionEvent) {
        NotificationUtils.showInfo("Info Notification", "This is an information notification message.");
        NotificationUtils.showWarn("Warning Notification", "This is a warning notification message.");
        NotificationUtils.showError("Error Notification", "This is an error notification message.");
        NotificationUtils.showSuccess("Success Notification", "This is a success notification message.");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ReferenceUtils.dialogPane = dialpgPane;
    }

    public void btnDialogOnClick(ActionEvent actionEvent) throws IOException {
//        DialogUtil.showConfirm(
//                "Delete Item",
//                "Are you sure you want to delete this item?",
//                () -> {
//                    // Confirmation logic
//                    System.out.println("Item deleted.");
//                    DialogUtil.showInfo("Deleted", "Item was successfully deleted.");
//                }
//        );

//        DialogUtil.showInfo("Error", "An error occurred.");
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/Component/Form.fxml"));
        DialogUtil.showCustom("Custom Dialog", "This is a custom dialog.",pane , ()-> System.out.println("clock conform"));
    }

    public void btnAlertOnClick(ActionEvent actionEvent) {
        AlertUtils.showInfo("Information", "This is an information message.");
        AlertUtils.showError("Error", "This is an error message.");
        AlertUtils.showWarning("Warning", "This is a warning message.");
        AlertUtils.showSuccess("Success", "This is a success message.");
//        String name = AlertUtils.showInputDialog("User Name", "Please enter your name:");
//        if (name != null && !name.isEmpty()) {
//            System.out.println("User entered: " + name);
//        } else {
//            System.out.println("User cancelled or left empty.");
//        }

    }
}
