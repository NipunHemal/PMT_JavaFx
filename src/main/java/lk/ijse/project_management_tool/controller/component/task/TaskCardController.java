package lk.ijse.project_management_tool.controller.component.task;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.input.MouseEvent;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.List;

import lk.ijse.project_management_tool.controller.TaskController;
import lk.ijse.project_management_tool.model.TaskModel;
import lk.ijse.project_management_tool.utils.AlertUtils;
import lk.ijse.project_management_tool.utils.DialogUtil;
import lk.ijse.project_management_tool.dto.TaskDto;

public class TaskCardController {
    @FXML
    private VBox cardContainer;

    @FXML
    private Label categoryLabel;

    @FXML
    private Label titleLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label progressLabel;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private HBox dateIndicators;

    @FXML
    private Label dateLabel;

    @FXML
    private HBox assigneeContainer;

    @FXML
    private Label priorityLabel;

    @FXML
    private Label projectNameLabel;

    @FXML
    private Label tagLabel;

    private Long taskId;
    private Runnable onDelete;

    TaskController taskController;

    public void init(TaskController taskController) {
        this.taskController = taskController;
    }

    public static TaskCardController create() throws IOException {
        FXMLLoader loader = new FXMLLoader(TaskCardController.class.getResource("/view/components/TaskCard.fxml"));
        loader.load();
        return loader.getController();
    }

    public void setCategory(String category, String color) {
        categoryLabel.setText(category);
        categoryLabel.setStyle("-fx-background-color: " + color
                + "; -fx-text-fill: white; -fx-background-radius: 6; -fx-padding: 6 12; -fx-font-size: 12px; -fx-font-weight: bold;");
    }

    public void setTitle(String title) {
        titleLabel.setText(title);
    }

    public void setDescription(String description) {
        descriptionLabel.setText(description);
    }

    public void setProgress(int current, int total) {
        progressLabel.setText(current + "%");
        progressBar.setProgress((double) current / total);
    }

    public void setDate(String date) {
        dateLabel.setText(date);
    }

    public void setPriority(String priority, String color) {
        priorityLabel.setText(priority);
        priorityLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: " + color + "; -fx-font-weight: bold;");
    }

    public void setAssignees(List<String> colors) {
        assigneeContainer.getChildren().clear();
        for (String color : colors) {
            Circle circle = new Circle(14);
            circle.setStyle("-fx-fill: " + color + "; -fx-stroke: white; -fx-stroke-width: 2;");
            assigneeContainer.getChildren().add(circle);
        }
    }

    public void setDateIndicators(List<String> colors) {
        dateIndicators.getChildren().clear();
        for (String color : colors) {
            Circle circle = new Circle(4);
            circle.setStyle("-fx-fill: " + color + ";");
            dateIndicators.getChildren().add(circle);
        }
    }

    public VBox getCardContainer() {
        return cardContainer;
    }

    public void setTask(TaskDto dto, Runnable onDelete) {
        this.taskId = dto.getId();
        this.onDelete = onDelete;
        projectNameLabel.setText(dto.getProjectName());
        tagLabel.setText(dto.getTag());
        setCategory(dto.getTag(), "#3B82F6"); // Using tag as category
        setTitle(dto.getTitle());
        setDescription(dto.getDescription());
        setProgress(dto.getProgress(), 100);
        setDate(dto.getCreatedAt() != null ? dto.getCreatedAt().toString() : "");
        setPriority(dto.getStatus(), getStatusColor(dto.getStatus()));
    }

    private String getStatusColor(String status) {
        switch (status) {
            case "TODO":
                return "#EF4444";
            case "IN_WORK":
                return "#F59E0B";
            case "IN_PROGRESS":
                return "#3B82F6";
            case "DONE":
                return "#10B981";
            default:
                return "#6B7280";
        }
    }

    @FXML
    private void onDeleteTaskClicked() {
        boolean isConfirmed = AlertUtils.showConform("Confirmation", "Are you sure you want to delete this task?");
        if (!isConfirmed) return;

        try {
            boolean isDeleted = new TaskModel().deleteTask(taskId);
            if (isDeleted) {
                taskController.initialize(null, null);
                AlertUtils.showSuccess("Success", "Task deleted successfully");
            } else {
                AlertUtils.showError("Error", "Failed to delete task");
            }
        } catch (Exception e) {
            AlertUtils.showError("Error", "Failed to delete task");
            e.printStackTrace();
        }
    }

    @FXML
    private void cardClicked(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/components/TaskForm.fxml"));
            Parent form = loader.load();
            loader.<TaskFormController>getController().init(taskController,null);
            loader.<TaskFormController>getController().setFormData(taskId);
            DialogUtil.showCustom(
                    "Update Task",
                    "",
                    form,
                    "Update",
                    "Cancel",
                    () -> loader.<TaskFormController>getController().update(taskId),
                    null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}