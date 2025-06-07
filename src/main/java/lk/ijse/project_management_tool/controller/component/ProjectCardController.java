package lk.ijse.project_management_tool.controller.component;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import lk.ijse.project_management_tool.controller.ProjectController;
import lk.ijse.project_management_tool.dto.ProjectDto;
import lk.ijse.project_management_tool.utils.ColourMap;

import java.net.URL;
import java.util.ResourceBundle;

public class ProjectCardController implements Initializable {
    public VBox vbxProjectCard;
    public ProgressBar progressBar;
    public Label txtHeading;
    public Label txtSubHeading;
    public Label txt2;
    public Label progressLable;
    public Label txt3;
    public Label txtStartDate;
    public HBox imgLoader;
    public Label tctEndDate;
    public Circle circle;

    // reappearances
    ProjectController projectController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        String [] Colours = ColourMap.getRandomColour();
        String newStyle = "-fx-background-color: " + Colours[0] + ";";
        String newStyleLighten = "-fx-accent: " + Colours[1] + ";";

        vbxProjectCard.setStyle(vbxProjectCard.getStyle() + newStyle);
        progressBar.setStyle(progressBar.getStyle() + newStyleLighten);
    }

    public void init(ProjectDto dto, ProjectController projectController) {
        this.projectController = projectController;
        txtHeading.setText(dto.getName());
        txtSubHeading.setText(dto.getDescription());
        txtStartDate.setText(dto.getStartDate());
        tctEndDate.setText(dto.getEndDate());
        txt3.setText(dto.getStatus());

        double randomDouble = Math.random();
        progressBar.setProgress(randomDouble);
        progressLable.setText(String.format("%.2f%%", randomDouble * 100));}

    public void onClickCardOnAction(MouseEvent mouseEvent) {
    }

    public void btnMoreOnAction(ActionEvent actionEvent) {
    }

    public void btnChangeStatusOnAction(ActionEvent actionEvent) {
    }

    public void btnAddViewOnAction(ActionEvent actionEvent) {
    }

    public void btnAddEmployeeOnAction(ActionEvent actionEvent) {
    }
}
