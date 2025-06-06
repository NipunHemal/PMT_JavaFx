package lk.ijse.project_management_tool.controller.component;

import javafx.scene.control.ComboBox;
import lk.ijse.project_management_tool.controller.TeamController;
import lk.ijse.project_management_tool.dto.TeamDto;
import lk.ijse.project_management_tool.model.TeamModel;
import lk.ijse.project_management_tool.utils.AlertUtils;
import lk.ijse.project_management_tool.utils.DialogUtil;
import lk.ijse.project_management_tool.utils.ValidationUtils;

public class ChangeTeamStatusFormController {
    public ComboBox status;
    TeamDto teamDto;
    TeamController teamController;

    // reference
    TeamModel teamModel = new TeamModel();

    public void save(){
        String save = status.getValue() != null ? status.getValue().toString() : "";

        boolean isStatus = ValidationUtils.validateInput(status, "empty");

        if (!isStatus) {
            teamController.initialize(null, null);
            AlertUtils.showError("Error", "Please select a status.");
            return;
        }

        if (teamDto == null) {
            AlertUtils.showError("Error", "team is not initialized.");
            return;
        }

        try{
            boolean isUpdate = teamModel.updateTeamStatus(teamDto.getTeamId(), save);
            if (isUpdate) {
                teamController.initialize(null, null);
                AlertUtils.showSuccess("Success", "Team status updated successfully.");
                DialogUtil.close();
            } else {
                AlertUtils.showError("Error", "Failed to update team status.");
            }
        } catch (Exception e) {
            AlertUtils.showError("Error", "Failed to update team status : "+e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void init(TeamController teamController, TeamDto teamDto) {
        this.teamController = teamController;
        this.teamDto = teamDto;
    }
}
