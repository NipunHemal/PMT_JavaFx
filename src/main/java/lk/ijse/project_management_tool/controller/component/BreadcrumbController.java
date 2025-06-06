package lk.ijse.project_management_tool.controller.component;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lk.ijse.project_management_tool.utils.NotificationUtils;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class BreadcrumbController implements Initializable {
    public Label txtBreadcrumbTitle;
    public Label txtNowDate;
    public JFXButton btnBack;
    public JFXButton btnHome;
    private Runnable run;

    private Timeline timeline;

    public void btnBackOnAction(ActionEvent actionEvent) {
        if (run != null) {
            run.run();
        }
    }

    public void init(Runnable run, String title) {
        this.run = run;
        txtBreadcrumbTitle.setText(title);
        if (run != null) {
            Image image = new Image(NotificationUtils.class.getResource("/images/icons/arrow-right.png").toExternalForm());
            ImageView icon = new ImageView(image);
            icon.setFitWidth(15);
            icon.setFitHeight(15);
            icon.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            btnBack.setGraphic(icon);
        } else {
            Image image = new Image(NotificationUtils.class.getResource("/images/icons/home.png").toExternalForm());
            ImageView icon = new ImageView(image);
            icon.setFitWidth(15);
            icon.setFitHeight(15);
            icon.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            btnBack.setGraphic(icon);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtBreadcrumbTitle.setText("Dashboard");
        Runnable run = null;

        // Start live time updater
        startLiveTimeUpdater();
    }

    private void startLiveTimeUpdater() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        timeline = new Timeline(new KeyFrame(javafx.util.Duration.seconds(1), e -> {
            txtNowDate.setText(LocalDateTime.now().format(formatter));
        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        // Optional: stop timeline when controller is no longer used to prevent memory leaks
    }

    // Call this method to stop the clock (optional)
    public void stopLiveTimeUpdater() {
        if (timeline != null) {
            timeline.stop();
        }
    }
}