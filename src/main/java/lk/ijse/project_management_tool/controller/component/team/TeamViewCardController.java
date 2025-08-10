package lk.ijse.project_management_tool.controller.component.team;

import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import lk.ijse.project_management_tool.controller.TeamController;
import lk.ijse.project_management_tool.dto.EmployeeDto;
import lk.ijse.project_management_tool.dto.TeamDto;
import lk.ijse.project_management_tool.model.EmployeeModel;
import lk.ijse.project_management_tool.model.TeamModel;
import lk.ijse.project_management_tool.utils.AlertUtils;
import lk.ijse.project_management_tool.utils.DialogUtil;
import lk.ijse.project_management_tool.utils.NotificationUtils;

import java.util.ArrayList;

public class TeamViewCardController {
    public Text tstMainTitle;
    public Label lblStatus;
    public Circle statusCircle;
    public Label lblSub;
    public Label lblMain;
    public Label txtDescription;
    public VBox hbxTeamMemberLoader;

    // reference
    EmployeeModel employeeModel = new EmployeeModel();
    TeamModel teamModel = new TeamModel();
    TeamDto team;

    public void init(TeamDto team) {
        this.team = team;
        tstMainTitle.setText(team.getName());
        lblStatus.setTextFill(team.getStatus().equals("ACTIVE") ? javafx.scene.paint.Color.GREEN : javafx.scene.paint.Color.RED);
        statusCircle.setFill(team.getStatus().equals("ACTIVE") ? javafx.scene.paint.Color.GREEN : javafx.scene.paint.Color.RED);
        txtDescription.setText(team.getDescription());
        lblMain.setText("\uD83D\uDC6A " + team.getTeamMemberCount());
        loadEmployees();
    }

    private void loadEmployees() {
        hbxTeamMemberLoader.getChildren().clear();
        try{
            ArrayList<EmployeeDto> employeeDtos = employeeModel.getEmployeesByTeamId(team.getTeamId());
            for (EmployeeDto employeeDto : employeeDtos) {
                hbxTeamMemberLoader.getChildren().add(createMemberCard(employeeDto.getEmployeeId(),employeeDto.getName(), employeeDto.getRole(), employeeDto.getStatus(),employeeDto.getProfile()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            NotificationUtils.showError("Error","Something went wrong : "+e.getMessage());
        }
    }

    private void removeEmployee(int employeeId,int teamId) {
        try {
            boolean isRemove = teamModel.removeTeamToEmployee(employeeId,teamId);
            if (isRemove) {
                AlertUtils.showSuccess("Remove Employee","Employee removed successfully");
                loadEmployees();
            } else {
                AlertUtils.showSuccess("Remove Employee","Employee removed successfully");
            }
        } catch (Exception e) {
            e.printStackTrace();
            NotificationUtils.showError("Error","Something went wrong : "+e.getMessage());
        }
    }


    private HBox createMemberCard(Long employeeId,String memberName, String roleText, String status, String profile) {
        HBox hBox = new HBox(12);
        hBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        hBox.setPrefHeight(54);
        hBox.setPrefWidth(388);
        hBox.setStyle("-fx-background-color: #F5F6FA; -fx-background-radius: 10;");
        hBox.paddingProperty().setValue(new Insets(5));


        StackPane avatarContainer = new StackPane();
        avatarContainer.setMaxHeight(36);
        avatarContainer.setMaxWidth(36);
        avatarContainer.setMinHeight(36);
        avatarContainer.setMinWidth(36);
        avatarContainer.setStyle("-fx-background-color: #DEE8F5; -fx-background-radius: 18;");

        ImageView avatar = new ImageView();
        avatar.setFitHeight(30);
        avatar.setFitWidth(30);
        avatar.setClip(new Circle(15, 15, 15));

        try {
            String imagePath = (profile == null) ?
                getClass().getResource("/images/user/user.png").toExternalForm() :
                getClass().getResource("/images/user/" + profile).toExternalForm();
            avatar.setImage(new Image(imagePath));
        } catch (NullPointerException e) {
            NotificationUtils.showError("Error", e.getMessage());
        }

        avatarContainer.getChildren().add(avatar);

        VBox memberInfo = new VBox(1);
        memberInfo.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        Text name = new Text(memberName);
        name.setStyle("-fx-font-size: 14.5px; -fx-font-weight: bold; -fx-fill: #232949;");

        Text role = new Text("Role: " + roleText);
        role.setStyle("-fx-font-size: 12.5px; -fx-fill: #676D7B;");

        memberInfo.getChildren().addAll(name, role);

        Pane spacer = new Pane();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

        JFXButton removeButton = new JFXButton("\uD83D\uDDD1");
        removeButton.setStyle("-fx-background-color: #FADBD8; -fx-background-radius: 6; -fx-padding: 2 10 2 10; -fx-text-fill: #C0392B; -fx-font-size: 12px;");
        removeButton.setOnAction(event -> removeEmployee(Integer.parseInt(employeeId.toString()),team.getTeamId()));


        Label onlineStatus = new Label(status);
        onlineStatus.setStyle(status.equals("ACTIVE") ? "-fx-background-color: #D1FADF; -fx-background-radius: 6; -fx-padding: 2 10 2 10; -fx-text-fill: #138A50; -fx-font-size: 12px;"
                                        : "-fx-background-color: #FADBD8; -fx-background-radius: 6; -fx-padding: 2 10 2 10; -fx-text-fill: #C0392B; -fx-font-size: 12px;");

        hBox.getChildren().addAll(avatarContainer, memberInfo, spacer, onlineStatus,removeButton);

        return hBox;
    }
}
