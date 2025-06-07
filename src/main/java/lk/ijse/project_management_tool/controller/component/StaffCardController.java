package lk.ijse.project_management_tool.controller.component;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lk.ijse.project_management_tool.dto.EmployeeDto;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblProgressLable.setText(progressBar.getProgress() * 100 + "%");
    }

    public void init(EmployeeDto employee){
        txtName.setText(employee.getName());
        txtTeam.setText("Team Alfa");
        txtStatus.setText(employee.getStatus());
        txtPosition.setText(employee.getRole());

        if ( employee.getProfile() != null ) {
            System.out.println("sssss");
            try{
                System.out.println("vvvvvvvvvvv");
                String imagePath = getClass().getResource("/images/user/" + employee.getProfile()).toExternalForm();
                imgUserPic.setImage(new Image(imagePath));
            } catch (Exception e) {
                e.printStackTrace();
                String imagePath = getClass().getResource("/images/user/user.png").toExternalForm();
                imgUserPic.setImage(new Image(imagePath));
            }
        } else {
            System.out.println("aaaaa");
            String imagePath = getClass().getResource("/images/user/user.png").toExternalForm();
            imgUserPic.setImage(new Image(imagePath));
        }
    }
}