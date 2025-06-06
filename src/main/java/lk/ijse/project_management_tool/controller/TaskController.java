package lk.ijse.project_management_tool.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import lk.ijse.project_management_tool.controller.component.TaskCardController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import lk.ijse.project_management_tool.utils.DialogUtil;
import lk.ijse.project_management_tool.model.TaskModel;
import lk.ijse.project_management_tool.dto.TaskDto;
import java.util.List;
import lk.ijse.project_management_tool.controller.component.TaskFormController;

import java.util.Arrays;

public class TaskController {
    @FXML
    private VBox todoContainer;
    @FXML
    private VBox inWorkContainer;
    @FXML
    private VBox inProgressContainer;
    @FXML
    private VBox doneContainer;

    @FXML
    public void initialize() {
        loadTasks();
    }

    private void loadTasks() {
        try {
            todoContainer.getChildren().clear();
            inWorkContainer.getChildren().clear();
            inProgressContainer.getChildren().clear();
            doneContainer.getChildren().clear();
            List<TaskDto> tasks = new TaskModel().getAllTasks();
            boolean todoEmpty = true, inWorkEmpty = true, inProgressEmpty = true, doneEmpty = true;
            for (TaskDto dto : tasks) {
                TaskCardController card = TaskCardController.create();
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
            DialogUtil.showCustom(
                    "Add Task",
                    "",
                    form,
                    "Save",
                    "Cancel",
                    () -> {
                        try {
                            TaskFormController controller = loader.getController();
                            TaskDto dto = controller.getTaskDto();
                            new TaskModel().saveTask(dto);
                            loadTasks();
                            DialogUtil.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    },
                    null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}