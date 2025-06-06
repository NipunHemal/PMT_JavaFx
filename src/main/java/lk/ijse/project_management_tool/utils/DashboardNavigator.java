package lk.ijse.project_management_tool.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import lk.ijse.project_management_tool.controller.component.BreadcrumbController;
import org.controlsfx.control.Notifications;

import java.io.IOException;


public class DashboardNavigator {
    public static AnchorPane dashboardContainer;
    public static StackPane breadcrumbContainer;

    public static void init(AnchorPane dashboardContainer ,StackPane breadcrumbContainer) {
        DashboardNavigator.dashboardContainer = dashboardContainer;
        DashboardNavigator.breadcrumbContainer = breadcrumbContainer;
    }

    public static void navigate(String path) {
           try {
                if (dashboardContainer == null && breadcrumbContainer == null) {
                    NotificationUtils.showError("Error", "Dashboard container or breadcrumb container is null.");
                    return;
                }
                navigator("/view/pages/" + path + ".fxml");
                if (path.equals("Dashboard")) loadBreadcrumb(null, "Dashboard");
                else loadBreadcrumb(() -> DashboardNavigator.navigate("Dashboard"), path);
           } catch (Exception e) {
               NotificationUtils.showError("Error", e.getMessage());
               e.printStackTrace();
           }
    }

    private static void navigator(String fxml) throws IOException {
            dashboardContainer.getChildren().clear();
            AnchorPane pane  = FXMLLoader.load(DashboardNavigator.class.getResource(fxml));

//            pane.prefWidthProperty().bind(dashboardContainer.widthProperty());
//            pane.prefHeightProperty().bind(dashboardContainer.heightProperty());

            pane.prefWidthProperty().unbind();
            pane.prefHeightProperty().unbind();

            AnchorPane.setTopAnchor(pane, 0.0);
            AnchorPane.setBottomAnchor(pane, 0.0);
            AnchorPane.setLeftAnchor(pane, 0.0);
            AnchorPane.setRightAnchor(pane, 0.0);

            System.out.println("pane width: " + pane.getWidth());
            System.out.println("dashboardContainer width: " + dashboardContainer.getWidth());

            dashboardContainer.getChildren().add(pane);
    }

    private static void loadBreadcrumb(Runnable run, String title) throws IOException {
            breadcrumbContainer.getChildren().clear();
            FXMLLoader loader  = new FXMLLoader(DashboardNavigator.class.getResource("/view/Component/Breadcrumb.fxml"));
            Parent pane  = loader.load();

            BreadcrumbController breadcrumbController = loader.getController();
            breadcrumbController.init(run, title);

            breadcrumbContainer.getChildren().add(pane);
    }
}