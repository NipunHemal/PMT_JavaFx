package lk.ijse.project_management_tool.controller.component;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lk.ijse.project_management_tool.dto.EmployeeDto;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblProgressLable.setText(progressBar.getProgress() * 100 + "%");
    }

    public void init(EmployeeDto employee){
        txtName.setText(employee.getName());
        txtTeam.setText("Team Alfa");
        txtStatus.setText(employee.getStatus());
        txtPosition.setText(employee.getRole());

        if (imgUserPic != null && employee.getProfile() != null && !employee.getProfile().isEmpty()) {
            String imagePath = getClass().getResource("/images/user/" + employee.getProfile()).toExternalForm();
            imgUserPic.setImage(new Image(imagePath));
        } else {
            String imagePath = getClass().getResource("/images/user/user.png").toExternalForm();
            imgUserPic.setImage(new Image(imagePath));
        }
    }
}