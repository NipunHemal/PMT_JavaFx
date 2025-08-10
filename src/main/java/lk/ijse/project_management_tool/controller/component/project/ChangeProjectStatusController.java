package lk.ijse.project_management_tool.controller.component.project;

import javafx.scene.control.ComboBox;
import lk.ijse.project_management_tool.controller.ProjectController;
import lk.ijse.project_management_tool.dto.ProjectDto;
import lk.ijse.project_management_tool.model.ProjectModel;
import lk.ijse.project_management_tool.utils.AlertUtils;
import lk.ijse.project_management_tool.utils.DialogUtil;
import lk.ijse.project_management_tool.utils.ValidationUtils;

public class ChangeProjectStatusController {
    public ComboBox status;
    ProjectDto projectDto;
    ProjectController projectController;

    // reference
    ProjectModel projectModel = new ProjectModel();

    public void save(){
        String save = status.getValue() != null ? status.getValue().toString() : "";

        boolean isStatus = ValidationUtils.validateInput(status, "empty");

        if (!isStatus) {
            AlertUtils.showError("Error", "Please select a status.");
            return;
        }

        if (projectDto == null) {
            AlertUtils.showError("Error", "team is not initialized.");
            return;
        }

        try{
            boolean isUpdate = projectModel.updateProjectStatus(Integer.parseInt(projectDto.getProjectId().toString()), save);
            if (isUpdate) {
                projectController.initialize(null, null);
                DialogUtil.close();
                AlertUtils.showSuccess("Success", "Project status updated successfully.");
            } else {
                AlertUtils.showError("Error", "Failed to update project status.");
            }
        } catch (Exception e) {
            AlertUtils.showError("Error", "Failed to update project status : "+e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void init(ProjectController projectController, ProjectDto projectDto) {
        this.projectController = projectController;
        this.projectDto = projectDto;
    }
}
