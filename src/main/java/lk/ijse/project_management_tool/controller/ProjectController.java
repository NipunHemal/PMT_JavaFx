package lk.ijse.project_management_tool.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import lk.ijse.project_management_tool.controller.component.AddProjectFormController;
import lk.ijse.project_management_tool.controller.component.ProjectCardController;
import lk.ijse.project_management_tool.dto.ProjectDto;
import lk.ijse.project_management_tool.model.ProjectModel;
import lk.ijse.project_management_tool.utils.DialogUtil;
import lk.ijse.project_management_tool.utils.NotificationUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProjectController  implements Initializable {
    public FlowPane fpnCardLoaderContainer;

    // model reference
    ProjectModel projectModel  = new ProjectModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadProjects();
    }

    private void loadProjects() {
            try{
                fpnCardLoaderContainer.getChildren().clear();
                ArrayList<ProjectDto> projects = projectModel.getAllProjects();
                for (ProjectDto project : projects) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Component/ProjectCard.fxml"));
                    VBox projectCard  = loader.load();
                    loader.<ProjectCardController>getController().init(project,this);
                    fpnCardLoaderContainer.getChildren().add(projectCard);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public void btnCreateProjectOnAction(ActionEvent actionEvent) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Component/Forms/AddProject.fxml"));
            Parent customContent = loader.load();
            loader.<AddProjectFormController>getController().init(this);
            DialogUtil.showCustom(null, null, customContent,
                    "Save", "Cancel",
                    () -> loader.<AddProjectFormController>getController().saveProject(),
                    null);
        } catch (Exception e) {
            e.printStackTrace();
            NotificationUtils.showError("Error Load Dialog", e.getMessage());
        }
    }

}
