package lk.ijse.project_management_tool.controller.component;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import lk.ijse.project_management_tool.controller.TeamController;
import lk.ijse.project_management_tool.dto.EmployeeDto;
import lk.ijse.project_management_tool.dto.TeamDto;
import lk.ijse.project_management_tool.model.EmployeeModel;
import lk.ijse.project_management_tool.utils.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddTeamToEmployee implements Initializable {
    public ComboBox combEmployee;
    TeamDto teamDto;
    TeamController teamController;

    EmployeeModel employeeModel = new EmployeeModel();

    public void loadEmployee(){
        try {
            ArrayList<EmployeeDto> employeeDtos =employeeModel.getAllEmployees();
            combEmployee.getItems().addAll(employeeDtos);

            combEmployee.setCellFactory(listView -> new ListCell<EmployeeDto>() {
                protected void updateItem(EmployeeDto dto, boolean empty) {
                    super.updateItem(dto, empty);
                    if (empty || dto == null) {
                        setText(null);
                    } else {
                        setText(dto.getName()); // Only show name
                    }
                }
            });

            combEmployee.setButtonCell(new ListCell<EmployeeDto>() {
                protected void updateItem(EmployeeDto dto, boolean empty) {
                    super.updateItem(dto, empty);
                    if (empty || dto == null) {
                        setText(null);
                    } else {
                        setText(dto.getName());
                    }
                }
            });
        } catch (Exception e) {
            NotificationUtils.showError("Error","Something went wrong : "+e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadEmployee();
    }

    public void init(TeamController teamController, TeamDto teamDto) {
        this.teamController = teamController;
        this.teamDto = teamDto;
    }

    public void save(){
        EmployeeDto employeeDto = combEmployee.getValue() != null ? (EmployeeDto) combEmployee.getValue() : null;//combTeam.getValue().toString();

        String employeeId = employeeDto.getEmployeeId() != null ? employeeDto.getEmployeeId().toString() : "";

        boolean isValid = ValidationUtils.validateInput(combEmployee, "empty");

        if (isValid) {
            // Save the project to the database using the ProjectModel class.
            try {
                boolean isSave = employeeModel.addTeamToEmployee(Integer.parseInt(employeeId), teamDto.getTeamId());
                if (isSave){
                    teamController.initialize(null, null);
                    AlertUtils.showSuccess("Success", "Team added successfully.");
                    DialogUtil.close();
                    DialogUtil.close();
                    new Thread(() -> {
                        boolean isSuccess = EmailUtil.sendEmail(employeeDto.getEmail(), "Team Added", "Your "+teamDto.getName()+" has been added to the project.");
                        Platform.runLater(() -> {
                            if (isSuccess) {
                                NotificationUtils.showSuccess("Email Sent", "Notification email sent successfully.");
                            } else {
                                NotificationUtils.showError("Email Failed", "Failed to send notification email.");
                            }
                        });
                    }).start();
                } else {
                    AlertUtils.showError("Error", "Failed to add team to employee.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                AlertUtils.showError("Error", "Failed to add team to employee : "+e.getMessage());
            }
        }
    }
}
