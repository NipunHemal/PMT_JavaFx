package lk.ijse.project_management_tool.controller.component;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lk.ijse.project_management_tool.controller.ProjectController;
import lk.ijse.project_management_tool.dto.ProjectDto;
import lk.ijse.project_management_tool.dto.TeamDto;
import lk.ijse.project_management_tool.model.ProjectModel;
import lk.ijse.project_management_tool.model.TeamModel;
import lk.ijse.project_management_tool.utils.AlertUtils;
import lk.ijse.project_management_tool.utils.DialogUtil;
import lk.ijse.project_management_tool.utils.NotificationUtils;
import lk.ijse.project_management_tool.utils.ValidationUtils;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddProjectFormController implements Initializable {
    public TextField txtNameField;
    public DatePicker txtStartDate;
    public DatePicker txtEndDate;
    public TextArea txtDescription;
    public TextField txtDuration;
    public ComboBox combSelectTesm;

    private ProjectController projectController;

    // refaranses
    TeamModel teamModel = new TeamModel();
    ProjectModel projectModel = new ProjectModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTeams();
    }

    public void loadTeams(){
        try{
            ArrayList<TeamDto> teams = teamModel.getAllTeams();
            combSelectTesm.getItems().addAll(teams);

            combSelectTesm.setCellFactory(listView -> new ListCell<TeamDto>() {
                protected void updateItem(TeamDto team, boolean empty) {
                    super.updateItem(team, empty);
                    if (empty || team == null) {
                        setText(null);
                    } else {
                        setText(team.getName()); // Only show name
                    }
                }
            });

            combSelectTesm.setButtonCell(new ListCell<TeamDto>() {
                protected void updateItem(TeamDto team, boolean empty) {
                    super.updateItem(team, empty);
                    if (empty || team == null) {
                        setText(null);
                    } else {
                        setText(team.getName());
                    }
                }
            });

        } catch (Exception e) {
            NotificationUtils.showError("Error", e.getMessage());
        }
    }

    public void saveProject() {
        try {

        String name = txtNameField.getText();
        String description = txtDescription.getText();
        String startDate = txtStartDate.getValue() != null ? txtStartDate.getValue().toString() : "";
        String endDate = txtEndDate.getValue() != null ? txtEndDate.getValue().toString() : "";
        String duration = txtDuration.getText();
        int teamId = combSelectTesm.getValue() != null ? ((TeamDto) combSelectTesm.getValue()).getTeamId() : 0;

        boolean isValidName = ValidationUtils.validateInput(txtNameField, "empty");
        boolean isValidDescription = ValidationUtils.validateInput(txtDescription, "empty");
        boolean isValidStartDate = ValidationUtils.validateInput(txtStartDate, "empty");
        boolean isValidEndDate = ValidationUtils.validateInput(txtEndDate, "empty");
        boolean isValidDuration = ValidationUtils.validateInput(txtDuration, "empty");
        boolean isValidTeam = ValidationUtils.validateInput(combSelectTesm, "empty");

        if (!(isValidName && isValidDescription && isValidStartDate && isValidEndDate && isValidDuration && isValidTeam)) {
            AlertUtils.showError("Error", "Please fill in all the required fields.");
            return;
        }

        // Save the project to the database using the ProjectModel class.
        ProjectDto project = new ProjectDto(name, description, startDate, endDate, duration,teamId);

            boolean isSave = projectModel.saveProject(project);
            if (isSave){
                if (projectController != null) projectController.initialize(null, null);
                AlertUtils.showSuccess("Success", "Project added successfully.");
                DialogUtil.close();
            } else{
                AlertUtils.showError("Error", "Failed to add project.");
            }
        } catch (SQLException e){
            AlertUtils.showError("Error", "Error in SQL" +e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            NotificationUtils.showError("Error", e.getMessage());
        }
    }

    public void init(ProjectController projectController) {
        this.projectController = projectController;
    }
}
