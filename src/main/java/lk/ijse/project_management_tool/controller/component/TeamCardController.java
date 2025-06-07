package lk.ijse.project_management_tool.controller.component;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import lk.ijse.project_management_tool.controller.TeamController;
import lk.ijse.project_management_tool.controller.TeamViewCardController;
import lk.ijse.project_management_tool.dto.EmployeeDto;
import lk.ijse.project_management_tool.dto.TeamDto;
import lk.ijse.project_management_tool.model.EmployeeModel;
import lk.ijse.project_management_tool.utils.DialogUtil;
import lk.ijse.project_management_tool.utils.NotificationUtils;

import java.util.ArrayList;

public class TeamCardController {

    public Text txtMainTitle;
    public Label txtLable;
    public Label txtDescription;
    public HBox imgLoader;
    public Label txtRightButtob;
    public Label txtStatus;
    public Circle circleStatus;

    // refference
    TeamController teamController;
    TeamDto team;
    EmployeeModel employeeModel = new EmployeeModel();

    public void init(TeamDto dto, TeamController teamController) {
        this.team = dto;
        this.teamController = teamController;
        txtMainTitle.setText(dto.getName());
        txtDescription.setText(dto.getDescription());
        txtStatus.setText(dto.getStatus());
        txtRightButtob.setText("");
        txtLable.setText("Employee : " + dto.getTeamMemberCount());

        txtStatus.setTextFill(dto.getStatus().equals("ACTIVE") ? javafx.scene.paint.Color.GREEN : javafx.scene.paint.Color.RED);
        txtStatus.setTextFill(dto.getStatus().equals("ACTIVE") ? javafx.scene.paint.Color.GREEN : javafx.scene.paint.Color.RED);
        circleStatus.setFill(dto.getStatus().equals("ACTIVE") ? javafx.scene.paint.Color.GREEN : javafx.scene.paint.Color.RED);

        loadEmployee();
    }

    public void loadEmployee(){
        try{
            ArrayList<EmployeeDto> employees = employeeModel.getEmployeesByTeamId(team.getTeamId());
            imgLoader.getChildren().clear();
            for (EmployeeDto employee : employees) {
                ImageView avatar = new ImageView();
                avatar.setFitHeight(30);
                avatar.setFitWidth(30);
                avatar.setClip(new Circle(15, 15, 15));

                try {
                    String imagePath = employee.getProfile() == null ?
                            getClass().getResource("/images/user/user.png").toExternalForm() :
                            getClass().getResource("/images/user/" + employee.getProfile()).toExternalForm();
                    avatar.setImage(new Image(imagePath));
                } catch (NullPointerException e) {
                    NotificationUtils.showError("Error", e.getMessage());
                }

                imgLoader.getChildren().add(avatar);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClickTeamOnAction(MouseEvent mouseEvent) {
        btnAddViewOnAction(null);
    }

    public void btnChangeStatusOnAction(ActionEvent actionEvent) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Component/Forms/ChangeTeamStatus.fxml"));
            Parent customContent = loader.load();
            loader.<ChangeTeamStatusFormController>getController().init(teamController,team);
            DialogUtil.showCustom(null, null, customContent,
                    "Save", "Cancel",
                    () -> loader.<ChangeTeamStatusFormController>getController().save(),
                    null);
        } catch (Exception e) {
            e.printStackTrace();
            NotificationUtils.showError("Error Load Dialog", e.getMessage());
        }
    }

    public void btnAddViewOnAction(ActionEvent actionEvent) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Component/ViewTeamCard.fxml"));
            Parent customContent = loader.load();
            loader.<TeamViewCardController>getController().init(team);
            DialogUtil.showCustom(null, null, customContent,
                    null, "Cancel",
                    null,
                    null);
        } catch (Exception e) {
            e.printStackTrace();
            NotificationUtils.showError("Error Load Dialog", e.getMessage());
        }
    }

    public void btnAddEmployeeOnAction(ActionEvent actionEvent) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Component/Forms/AddTeamToEmployee.fxml"));
            Parent customContent = loader.load();
            loader.<AddTeamToEmployee>getController().init(teamController,team);
            DialogUtil.showCustom(null, null, customContent,
                    null, "Cancel",
                    ()->loader.<AddTeamToEmployee>getController().save(),
                    null);
        } catch (Exception e) {
            e.printStackTrace();
            NotificationUtils.showError("Error Load Dialog", e.getMessage());
        }
    }
}
