package lk.ijse.project_management_tool.utils;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Optional;

public class AlertUtils {

    public static void showInfo(String title, String content) {
        showAlert("/images/icons/info.png", title, content);
    }

    public static void showWarning(String title, String content) {
        showAlert("/images/icons/warn.png", title, content);
    }

    public static void showError(String title, String content) {
        showAlert("/images/icons/error.png", title, content);
    }

    public static void showSuccess(String title, String content) {
        showAlert("/images/icons/success.png", title, content);
    }

    public static String showInputDialog(String title, String content) {
        return showTextInputAlert("/images/icons/info.png", title, content);
    }

    private static void showAlert(String iconPath, String title, String content) {
        Image image = new Image(AlertUtils.class.getResource(iconPath).toExternalForm());
        ImageView icon = new ImageView(image);
        icon.setFitWidth(50);
        icon.setFitHeight(50);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setGraphic(icon);
        alert.setContentText(content);

        // set header icon
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().clear();
        stage.getIcons().add(new Image(AlertUtils.class.getResource(iconPath).toExternalForm()));

        // Set custom stylesheet
        alert.getDialogPane().getStylesheets().add(
                AlertUtils.class.getResource("/css/util/alert.css").toExternalForm()
        );

        alert.showAndWait();
    }

    private static String showTextInputAlert(String iconPath, String title, String content) {
//        Image image = new Image(AlertUtils.class.getResource(iconPath).toExternalForm());
//        ImageView icon = new ImageView(image);
//        icon.setFitWidth(40);
//        icon.setFitHeight(40);

        TextField textField = new TextField();
        textField.setPrefWidth(300);
        textField.setPromptText("Enter here");

        VBox vbox = new VBox(10, new Label(content), textField);

        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle(title);
        alert.setHeaderText(null);
//        alert.setGraphic(icon);
        alert.getDialogPane().setContent(vbox);

        // set header icon
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().clear();
        stage.getIcons().add(new Image(AlertUtils.class.getResource("/images/icons/input.png").toExternalForm()));


        // Set style class for buttons
        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(okButton, cancelButton);

        // Set custom stylesheet
        alert.getDialogPane().getStylesheets().add(
                AlertUtils.class.getResource("/css/util/alert.css").toExternalForm()
        );

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == okButton) {
            return textField.getText().trim();
        }
        return null;
    }

    // show conform
    public static boolean showConform(String title, String message) {
        String iconPath = "/images/icons/warn.png";
        Image image = new Image(AlertUtils.class.getResource(iconPath).toExternalForm());
        ImageView icon = new ImageView(image);
        icon.setFitWidth(50);
        icon.setFitHeight(50);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setGraphic(icon);
        alert.setContentText(message);

        // set header icon
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().clear();
        stage.getIcons().add(new Image(AlertUtils.class.getResource(iconPath).toExternalForm()));

        // Set custom stylesheet
        alert.getDialogPane().getStylesheets().add(
                AlertUtils.class.getResource("/css/util/alert.css").toExternalForm()
        );

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
}

// example usage
// AlertUtils.showInfo("Title", "Content");
// AlertUtils.showWarning("Title", "Content");
// AlertUtils.showError("Title", "Content");
// AlertUtils.showSuccess("Title", "Content");

// String input = AlertUtils.showInputDialog("Title", "Content");
