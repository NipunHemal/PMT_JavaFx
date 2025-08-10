package lk.ijse.project_management_tool.controller.component.project;

import javafx.scene.control.ComboBox;
import lk.ijse.project_management_tool.controller.StaffController;
import lk.ijse.project_management_tool.dto.EmployeeDto;
import lk.ijse.project_management_tool.model.EmployeeModel;
import lk.ijse.project_management_tool.utils.AlertUtils;
import lk.ijse.project_management_tool.utils.DialogUtil;
import lk.ijse.project_management_tool.utils.ValidationUtils;

public class ChangeStaffStatusController {
    public ComboBox status;
    EmployeeDto employeeDto;
    StaffController staffController;

    // reference
    EmployeeModel employeeModel = new EmployeeModel();

    public void save(){
        String save = status.getValue() != null ? status.getValue().toString() : "";

        boolean isStatus = ValidationUtils.validateInput(status, "empty");

        if (!isStatus) {
            AlertUtils.showError("Error", "Please select a status.");
            return;
        }

        if (employeeDto == null) {
            AlertUtils.showError("Error", "employee is not initialized.");
            return;
        }

        try{
            boolean isUpdate = employeeModel.changeEmployeeStatus(Integer.parseInt(employeeDto.getEmployeeId().toString()), save);
            if (isUpdate) {
                staffController.initialize(null, null);
                DialogUtil.close();
                AlertUtils.showSuccess("Success", "Employee status updated successfully.");
            } else {
                AlertUtils.showError("Error", "Failed to update employee status.");
            }
        } catch (Exception e) {
            AlertUtils.showError("Error", "Failed to update employee status : "+e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void init(StaffController staffController, EmployeeDto employeeDto) {
        this.staffController = staffController;
        this.employeeDto = employeeDto;
    }
}
