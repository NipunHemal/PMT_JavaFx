package lk.ijse.project_management_tool.controller.component.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import lk.ijse.project_management_tool.controller.DashboardController;
import lk.ijse.project_management_tool.controller.ProjectController;
import lk.ijse.project_management_tool.controller.TaskController;
import lk.ijse.project_management_tool.controller.component.team.ChangeTeamStatusFormController;
import lk.ijse.project_management_tool.controller.component.team.TeamViewCardController;
import lk.ijse.project_management_tool.dto.EmployeeDto;
import lk.ijse.project_management_tool.dto.ProjectDto;
import lk.ijse.project_management_tool.model.EmployeeModel;
import lk.ijse.project_management_tool.model.ProjectModel;
import lk.ijse.project_management_tool.utils.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
    ProjectDto projectDto;
    ProjectModel projectModel = new ProjectModel();
    EmployeeModel employeeModel = new EmployeeModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        String [] Colours = ColourMap.getRandomColour();
        String newStyle = "-fx-background-color: " + Colours[0] + ";";
        String newStyleLighten = "-fx-accent: " + Colours[1] + ";";

        vbxProjectCard.setStyle(vbxProjectCard.getStyle() + newStyle);
        progressBar.setStyle(progressBar.getStyle() + newStyleLighten);
    }

    public void init(ProjectDto dto, ProjectController projectController) {
        this.projectDto = dto;
        this.projectController = projectController;
        txtHeading.setText(dto.getName());
        txtSubHeading.setText(dto.getDescription());
        txtStartDate.setText(dto.getStartDate());
        tctEndDate.setText(dto.getEndDate());
        txt3.setText(dto.getStatus());

        double randomDouble = Math.random();
        progressBar.setProgress(randomDouble);
        progressLable.setText(String.format("%.2f%%", randomDouble * 100));
        loadEmployee();
    }

    public void loadEmployee(){
        try{

            if (projectDto.getTeamId() == null) {
                imgLoader.getChildren().clear();
                return;
            }

            ArrayList<EmployeeDto> employees = employeeModel.getEmployeesByTeamId(Integer.parseInt(projectDto.getTeamId().toString()));
            imgLoader.getChildren().clear();
            for (EmployeeDto employee : employees) {
                ImageView avatar = new ImageView();
                avatar.setFitHeight(30);
                avatar.setFitWidth(30);
                avatar.setClip(new Circle(15, 15, 15));

                try {
                    System.out.println("Profile image found: " + employee.getProfile());
                    String profile = employee.getProfile();

                    if (profile != null && !profile.isBlank()) {
                        File externalImageFile = new File("src/main/resources/images/user/" + profile); // or your external folder

                        if (externalImageFile.exists()) {
                            avatar.setImage(new Image(externalImageFile.toURI().toString()));
                        } else {
                            // Fallback to default image in resources
                            String defaultImage = getClass().getResource("/images/user/user.png").toExternalForm();
                            avatar.setImage(new Image(defaultImage));
                        }

                    } else {
                        // Profile is null or blank
                        String defaultImage = getClass().getResource("/images/user/user.png").toExternalForm();
                        avatar.setImage(new Image(defaultImage));
                    }

                } catch (Exception e) {
                    NotificationUtils.showError("Error", e.getMessage());
                }


                imgLoader.getChildren().add(avatar);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeProject(int projectId){
        try{
            boolean isConformed = AlertUtils.showConform("Confirmation","Are you sure you want to remove this project?");
            if (isConformed){
                boolean isDeleted = projectModel.deleteProject(projectId);
                if (isDeleted){
                    projectController.initialize(null,null);
                    AlertUtils.showSuccess("Success","Project removed successfully");
                } else {
                    AlertUtils.showError("Error","Failed to remove project");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void onClickCardOnAction(MouseEvent mouseEvent) {
        TaskController.setProject(projectDto);
        DashboardNavigator.navigate("Task");
    }

    public void btnChangeStatusOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Component/Forms/ChangeProjectStatus.fxml"));
        Parent customContent = loader.load();
        loader.<ChangeProjectStatusController>getController().init(projectController,projectDto);
        DialogUtil.showCustom(null, null, customContent,
                "Save", "Cancel",
                () -> loader.<ChangeProjectStatusController>getController().save(),
                null);
    }

    public void btnAddViewOnAction(ActionEvent actionEvent) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Component/ViewProjectCard.fxml"));
            Parent customContent = loader.load();
            loader.<ProjectViewCardController>getController().init(projectDto);
            DialogUtil.showCustom(null, null, customContent,
                    null, "Cancel",
                    null,
                    ()->DialogUtil.close());
        } catch (Exception e) {
            e.printStackTrace();
            NotificationUtils.showError("Error Load Dialog", e.getMessage());
        }
    }

    public void removeProjectOnAction(ActionEvent actionEvent) {
        removeProject(Integer.parseInt(projectDto.getProjectId().toString()));
    }
}
