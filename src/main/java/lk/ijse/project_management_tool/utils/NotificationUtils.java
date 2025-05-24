package lk.ijse.project_management_tool.utils;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class NotificationUtils {

    private static void showNotification(String title, String message, String iconPath) {
        Image image = new Image(NotificationUtils.class.getResource(iconPath).toExternalForm());
        ImageView icon = new ImageView(image);
        icon.setFitWidth(32);
        icon.setFitHeight(32);

        Notifications notification = Notifications.create()
                .title(title)
                .text(message)
                .graphic(icon)
                .hideAfter(Duration.seconds(3))
                .position(Pos.BOTTOM_RIGHT);
        notification.show();
    }

    public static void showSuccess(String title, String message) {
        showNotification(title, message, "/images/icons/success.png");
    }

    public static void showError(String title, String message) {
        showNotification(title, message, "/images/icons/error.png");
    }

    public static void showInfo(String title, String message) {
        showNotification(title, message, "/images/icons/info.png");
    }

    public static void showWarn(String title, String message) {
        showNotification(title, message, "/images/icons/warn.png");
    }
}

// Example usage

//NotificationUtils.showSuccess("Success", "Operation completed successfully.");
//NotificationUtils.showError("Error", "An error occurred.");
//NotificationUtils.showInfo("Info", "Information message.");
//NotificationUtils.showWarn("Warning", "Warning message.");
