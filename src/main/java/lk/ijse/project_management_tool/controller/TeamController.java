package lk.ijse.project_management_tool.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import lk.ijse.project_management_tool.controller.component.team.AddTeamFormController;
import lk.ijse.project_management_tool.controller.component.team.TeamCardController;
import lk.ijse.project_management_tool.dto.TeamDto;
import lk.ijse.project_management_tool.model.TeamModel;
import lk.ijse.project_management_tool.utils.DialogUtil;
import lk.ijse.project_management_tool.utils.NotificationUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TeamController implements Initializable {
    public FlowPane fpnCardLoaderContainer;
    public Label lblAllTems;
    public Label lblActiveTeams;
    public Label lblInactiveTeams;
    public Label lblAchievedTeams;

    // refference
    TeamModel teamModel = new TeamModel();

    public void btnCreateTeamOnAction(ActionEvent actionEvent) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Component/Forms/AddTeam.fxml"));
            Parent customContent = loader.load();
            loader.<AddTeamFormController>getController().init(this);

            DialogUtil.showCustom(null, null, customContent,
                    "Save", "Cancel",
                    () -> loader.<AddTeamFormController>getController().save(),
                    null);
        } catch (Exception e) {
            e.printStackTrace();
            NotificationUtils.showError("Error Load Dialog", e.getMessage());
        }
    }

    private void loadProjects() {
        try{
            fpnCardLoaderContainer.getChildren().clear();
            ArrayList<TeamDto> teams = teamModel.getAllTeams();
            for (TeamDto team : teams) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Component/TeamCard.fxml"));
                Parent teamCard  = loader.load();
                loader.<TeamCardController>getController().init(team,this);
                fpnCardLoaderContainer.getChildren().add(teamCard);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadProjects();
        loadHeader();
    }

    public void loadHeader(){

        try{
            ArrayList<TeamDto> teams = teamModel.getAllTeamsWithSuspend();

            lblAllTems.setText(String.valueOf(teams.size()));
            lblActiveTeams.setText(String.valueOf(teams.stream().filter(team -> team.getStatus().equals("ACTIVE")).count()));
            lblInactiveTeams.setText(String.valueOf(teams.stream().filter(team -> team.getStatus().equals("INACTIVE")).count()));
            lblAchievedTeams.setText(String.valueOf(teams.stream().filter(team -> team.getStatus().equals("SUSPEND")).count()));
        } catch (Exception e) {
            NotificationUtils.showError("Error", "Failed to load Team headers : "+e.getMessage());
            e.printStackTrace();
        }
    }
}
