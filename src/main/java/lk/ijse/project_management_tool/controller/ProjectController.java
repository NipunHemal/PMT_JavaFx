package lk.ijse.project_management_tool.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import lk.ijse.project_management_tool.utils.DialogUtil;
import lk.ijse.project_management_tool.utils.NotificationUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProjectController  implements Initializable {
    public FlowPane fpnCardLoaderContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadProjects();
    }

    private void loadProjects() {
        for (int i = 0; i < 5; i++) {
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Component/ProjectCard.fxml"));
                VBox projectCard  = loader.load();
                fpnCardLoaderContainer.getChildren().add(projectCard);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void btnCreateProjectOnAction(ActionEvent actionEvent) {
        try{
            Parent customContent = FXMLLoader.load(getClass().getResource("/view/Component/Forms/AddProject.fxml"));
            DialogUtil.showCustom(null, null, customContent,
                    "Save", "Cancel",
                    () -> System.out.println("Save"),
                    () -> System.out.println("Cancel"));
        } catch (Exception e) {
            e.printStackTrace();
            NotificationUtils.showError("Error Load Dialog", e.getMessage());
        }
    }
}
