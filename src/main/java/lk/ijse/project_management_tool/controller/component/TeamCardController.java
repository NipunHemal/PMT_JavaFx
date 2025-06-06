package lk.ijse.project_management_tool.controller.component;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import lk.ijse.project_management_tool.controller.TeamController;
import lk.ijse.project_management_tool.dto.TeamDto;
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
    }

    public void onClickTeamOnAction(MouseEvent mouseEvent) {
        System.out.println("clicked");
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
    }

    public void btnAddEmployeeOnAction(ActionEvent actionEvent) {
    }
}
