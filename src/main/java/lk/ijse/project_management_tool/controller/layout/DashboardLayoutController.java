package lk.ijse.project_management_tool.controller.layout;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import lk.ijse.project_management_tool.utils.DashboardNavigator;
import lk.ijse.project_management_tool.utils.NotificationUtils;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardLayoutController implements Initializable {
    public StackPane breadcrumbContainer;
    public AnchorPane dashboardContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DashboardNavigator.init(dashboardContainer, breadcrumbContainer);
        try {
            DashboardNavigator.navigate("Dashboard");
        } catch (Exception e) {
            NotificationUtils.showError("Error", e.getMessage());
            e.printStackTrace();
        }
    }

    public void btnDashboardOnAction(ActionEvent actionEvent) {
        DashboardNavigator.navigate("Dashboard");
    }

    public void btnProjectOnAction(ActionEvent actionEvent) {
        DashboardNavigator.navigate("Project");
    }

    public void btnProfileOnAction(ActionEvent actionEvent) {
        DashboardNavigator.navigate("Profile");
    }

    public void btnStaffOnAction(ActionEvent actionEvent) {
        DashboardNavigator.navigate("Staff");
    }

    public void btnTaskOnAction(ActionEvent actionEvent) {
        DashboardNavigator.navigate("Task");
    }

    public void btnTeamOnAction(ActionEvent actionEvent) {
        DashboardNavigator.navigate("Team");
    }
}