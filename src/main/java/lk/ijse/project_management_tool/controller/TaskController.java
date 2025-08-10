package lk.ijse.project_management_tool.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import lk.ijse.project_management_tool.controller.component.task.TaskCardController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import lk.ijse.project_management_tool.dto.EmployeeDto;
import lk.ijse.project_management_tool.dto.ProjectDto;
import lk.ijse.project_management_tool.model.EmployeeModel;
import lk.ijse.project_management_tool.utils.AlertUtils;
import lk.ijse.project_management_tool.utils.DashboardNavigator;
import lk.ijse.project_management_tool.utils.DialogUtil;
import lk.ijse.project_management_tool.model.TaskModel;
import lk.ijse.project_management_tool.dto.TaskDto;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import lk.ijse.project_management_tool.controller.component.task.TaskFormController;
import lk.ijse.project_management_tool.utils.NotificationUtils;

public class TaskController implements Initializable {
    public Label lblTitle;
    public HBox imageLoader;
    public Button btnInvite;
    public Label lblSubTitle;
    @FXML
    private VBox todoContainer;
    @FXML
    private VBox inWorkContainer;
    @FXML
    private VBox inProgressContainer;
    @FXML
    private VBox doneContainer;

    private static ProjectDto projectDto;
    private EmployeeModel employeeModel = new EmployeeModel();

    public static void setProject(ProjectDto projectDto) {
        TaskController.projectDto = projectDto;
    }

    public static ProjectDto getProject() {
        return projectDto;
    }

    private void loadTasks() {
        try {
            todoContainer.getChildren().clear();
            inWorkContainer.getChildren().clear();
            inProgressContainer.getChildren().clear();
            doneContainer.getChildren().clear();
            List<TaskDto> tasks = new TaskModel().getAllTasksForProject(projectDto.getProjectId());
            boolean todoEmpty = true, inWorkEmpty = true, inProgressEmpty = true, doneEmpty = true;
            for (TaskDto dto : tasks) {
                TaskCardController card = TaskCardController.create();
                card.init(this);
                card.setTask(dto, () -> {
                    loadTasks();
                });
                switch (dto.getStatus()) {
                    case "TODO":
                        todoContainer.getChildren().add(card.getCardContainer());
                        todoEmpty = false;
                        break;
                    case "IN_WORK":
                        inWorkContainer.getChildren().add(card.getCardContainer());
                        inWorkEmpty = false;
                        break;
                    case "IN_PROGRESS":
                        inProgressContainer.getChildren().add(card.getCardContainer());
                        inProgressEmpty = false;
                        break;
                    case "DONE":
                        doneContainer.getChildren().add(card.getCardContainer());
                        doneEmpty = false;
                        break;
                }
            }
            if (todoEmpty)
                todoContainer.getChildren().add(new javafx.scene.control.Label("No tasks"));
            if (inWorkEmpty)
                inWorkContainer.getChildren().add(new javafx.scene.control.Label("No tasks"));
            if (inProgressEmpty)
                inProgressContainer.getChildren().add(new javafx.scene.control.Label("No tasks"));
            if (doneEmpty)
                doneContainer.getChildren().add(new javafx.scene.control.Label("No tasks"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onAddTaskClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/components/TaskForm.fxml"));
            Parent form = loader.load();
            loader.<TaskFormController>getController().init(this, projectDto);
            DialogUtil.showCustom(
                    "Add Task",
                    "",
                    form,
                    "Save",
                    "Cancel",
                    () -> loader.<TaskFormController>getController().save(),
                    null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (projectDto != null) {
            lblTitle.setText(projectDto.getName());
            lblSubTitle.setText(projectDto.getDescription());
            loadTasks();
            loadEmployee();
        } else {
            AlertUtils.showError("No project selected", "Please select a project.");
            throw  new RuntimeException("No project selected");
        }
    }

    public void loadEmployee(){
        try{

            if (projectDto.getTeamId() == null) {
                imageLoader.getChildren().clear();
                return;
            }

            ArrayList<EmployeeDto> employees = employeeModel.getEmployeesByTeamId(Integer.parseInt(projectDto.getTeamId().toString()));
            imageLoader.getChildren().clear();
            int i = 1;
            for (EmployeeDto employee : employees) {
                if (i++ > 3) continue;
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


                imageLoader.getChildren().add(avatar);
            }
            if (employees.size() > 3) {
                imageLoader.getChildren().addLast(new Label("+" + (employees.size() - 3)));
            }
            if (employees.size() == 0) {
                imageLoader.getChildren().add(new Label("No employees"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}