package lk.ijse.project_management_tool.controller.component.employee;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lk.ijse.project_management_tool.controller.StaffController;
import lk.ijse.project_management_tool.controller.component.project.ChangeStaffStatusController;
import lk.ijse.project_management_tool.controller.component.team.ChangeTeamStatusFormController;
import lk.ijse.project_management_tool.dto.EmployeeDto;
import lk.ijse.project_management_tool.model.EmployeeModel;
import lk.ijse.project_management_tool.utils.AlertUtils;
import lk.ijse.project_management_tool.utils.DialogUtil;
import lk.ijse.project_management_tool.utils.NotificationUtils;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class StaffCardController implements Initializable {
    public ImageView imgUserPic;
    public Label txtName;
    public Label txtTeam;
    public Label txtStatus;
    public Label txtPosition;
    public Label lblProgressLable;
    public ProgressBar progressBar;

    private EmployeeDto employeeDto;

    // reference
    private StaffController staffController;
    private EmployeeModel employeeModel  = new EmployeeModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblProgressLable.setText(progressBar.getProgress() * 100 + "%");
    }

    public void init(EmployeeDto employee, StaffController staffController){
        this.staffController = staffController;
        this.employeeDto = employee;

        txtName.setText(employee.getName());
        txtTeam.setText("Team Alfa");
        txtStatus.setText(employee.getStatus());
        txtPosition.setText(employee.getRole());

        String imageFileName = employee.getProfile();

        if (imageFileName != null && !imageFileName.isBlank()) {
            System.out.println("Profile image found: " + imageFileName);

            File externalImageFile = new File("src/main/resources/images/user/" + imageFileName); // or your saved folder

            if (externalImageFile.exists()) {
                System.out.println("Loading image from file system...");
                imgUserPic.setImage(new Image(externalImageFile.toURI().toString()));
            } else {
                System.out.println("Image not found in file system. Load default image...");
                imgUserPic.setImage(new Image(getClass().getResource("/images/user/user.png").toExternalForm()));
            }

        } else {
            System.out.println("Profile image not assigned. Load default image...");
            imgUserPic.setImage(new Image(getClass().getResource("/images/user/user.png").toExternalForm()));
        }
    }


    public void btnChangeStatusOnAction(ActionEvent actionEvent) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Component/Forms/ChangeStaffStatus.fxml"));
            Parent customContent = loader.load();
            loader.<ChangeStaffStatusController>getController().init(staffController,employeeDto);
            DialogUtil.showCustom(null, null, customContent,
                    "Save", "Cancel",
                    () -> loader.<ChangeStaffStatusController>getController().save(),
                    null);
        } catch (Exception e) {
            e.printStackTrace();
            NotificationUtils.showError("Error Load Dialog", e.getMessage());
        }
    }

    public void removeUserOnAction(ActionEvent actionEvent) {
        try{
            boolean isConformed = AlertUtils.showConform("Confirmation", "Are you sure you want to remove " + employeeDto.getName() + "?");
            if (isConformed){
                boolean isDeleted =employeeModel.deleteEmployee(employeeDto.getEmployeeId());
                if (isDeleted){
                    staffController.initialize(null,null);
                    AlertUtils.showSuccess("Success","Employee removed successfully");
                } else {
                    AlertUtils.showError("Error","Failed to remove employee");
                }
            }
        } catch (Exception e) {
            NotificationUtils.showError("Error","Error removing employee :"+e.getMessage());
        }
    }
}