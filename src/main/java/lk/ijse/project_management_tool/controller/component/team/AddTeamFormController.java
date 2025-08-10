package lk.ijse.project_management_tool.controller.component.team;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lk.ijse.project_management_tool.controller.TeamController;
import lk.ijse.project_management_tool.dto.TeamDto;
import lk.ijse.project_management_tool.model.TeamModel;
import lk.ijse.project_management_tool.utils.AlertUtils;
import lk.ijse.project_management_tool.utils.DialogUtil;
import lk.ijse.project_management_tool.utils.NotificationUtils;
import lk.ijse.project_management_tool.utils.ValidationUtils;

public class AddTeamFormController {

    public TextField nameField;
    public TextArea descriptionField;
    // reffarance
    TeamController teamController;
    TeamModel teamModel = new TeamModel();

    public void save() {
        String name = nameField.getText();
        String description = descriptionField.getText();

        boolean isValidName = ValidationUtils.validateInput(nameField, "empty");
        boolean isValidDescription = ValidationUtils.validateInput(descriptionField, "empty");

        if (!(isValidName && isValidDescription)) {
            NotificationUtils.showError("Validation Error", "Please fill all the fields correctly.");
            return;
        }

        TeamDto teamDto = new TeamDto(name, description);

        try{
            boolean isSave = teamModel.saveTeam(teamDto);
            if (isSave) {
                teamController.initialize(null, null);
                AlertUtils.showSuccess("Success", "Team added successfully.");
                DialogUtil.close();
            } else {
                AlertUtils.showError("Error", "Failed to add team.");
            }
        } catch (Exception e) {
            AlertUtils.showError("Error", "Failed to add team : "+e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void init(TeamController teamController) {
        this.teamController = teamController;
    }
}
