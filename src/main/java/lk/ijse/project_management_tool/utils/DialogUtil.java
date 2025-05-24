package lk.ijse.project_management_tool.utils;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class DialogUtil {

    public enum DialogType {
        INFO, ERROR, CONFIRM, CUSTOM
    }

    public static void showDialog(
            DialogType type,
            String title,
            String message,
            String confirmText,
            String cancelText,
            Runnable onConfirm,
            Runnable onCancel,
            Node graphic
    ) {
        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Label(title));
        layout.setBody(new Text(message));
        if (graphic != null) layout.setBody(graphic); // ✅ FIXED

        JFXDialog dialog = new JFXDialog(ReferenceUtils.dialogPane, layout, JFXDialog.DialogTransition.CENTER);

        JFXButton confirmBtn = new JFXButton(confirmText != null ? confirmText : "OK");
        confirmBtn.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        confirmBtn.getStyleClass().add("dialog-accept");
        confirmBtn.setOnAction(e -> {
            if (onConfirm != null) onConfirm.run();
            dialog.close();
        });

        HBox actions = new HBox(10);
        actions.setAlignment(Pos.CENTER_RIGHT);
        actions.getChildren().add(confirmBtn);

        if (type == DialogType.CONFIRM && cancelText != null) {
            JFXButton cancelBtn = new JFXButton(cancelText);
            cancelBtn.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
            cancelBtn.getStyleClass().add("dialog-cancel");
            cancelBtn.setOnAction(e -> {
                if (onCancel != null) onCancel.run();
                dialog.close();
            });
            actions.getChildren().add(cancelBtn);
        }

        layout.setActions(actions);
        dialog.setContent(layout);
        dialog.toFront();
        dialog.show();
    }

    // Shortcut for simple info dialog
    public static void showInfo(String title, String message) {
        showDialog( DialogType.INFO, title, message, "OK", null, null, null, null);
    }

    // Shortcut for error dialog
    public static void showError(String title, String message) {
        showDialog(DialogType.ERROR, title, message, "Close", null, null, null, null);
    }

    // Shortcut for confirmation dialog
    public static void showConfirm(String title, String message, Runnable onConfirm) {
        showDialog( DialogType.CONFIRM, title, message, "Yes", "No", onConfirm, null, null);
    }

    public static void showCustom(String title, String message,Node graphic , Runnable onConfirm) {
        showDialog( DialogType.CONFIRM, title, message, "Submit", "Close", onConfirm, null, graphic);
    }
}

// Sample usage

