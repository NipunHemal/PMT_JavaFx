package lk.ijse.project_management_tool.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import lk.ijse.project_management_tool.controller.component.ProjectCardController;
import lk.ijse.project_management_tool.dto.ProjectDto;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TeamController implements Initializable {
    public FlowPane fpnCardLoaderContainer;

    public void btnCreateTeamOnAction(ActionEvent actionEvent) {

    }

    private void loadProjects() {
        try{
            fpnCardLoaderContainer.getChildren().clear();
//            ArrayList<ProjectDto> projects = projectModel.getAllProjects();
            for (int i = 0; i < 10; i++) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Component/TeamCard.fxml"));
                VBox teamCard  = loader.load();
//                loader.<ProjectCardController>getController().init(project,this);
                fpnCardLoaderContainer.getChildren().add(teamCard);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadProjects();
    }
}
