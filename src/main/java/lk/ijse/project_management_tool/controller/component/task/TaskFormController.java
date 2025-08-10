package lk.ijse.project_management_tool.controller.component.task;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import lk.ijse.project_management_tool.controller.ProjectController;
import lk.ijse.project_management_tool.controller.TaskController;
import lk.ijse.project_management_tool.dto.TaskDto;
import lk.ijse.project_management_tool.model.ProjectModel;
import lk.ijse.project_management_tool.dto.ProjectDto;

import java.net.URL;
import java.util.List;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;

import java.util.HashMap;
import java.util.ResourceBundle;

import lk.ijse.project_management_tool.model.TaskModel;
import lk.ijse.project_management_tool.utils.AlertUtils;
import lk.ijse.project_management_tool.utils.DialogUtil;
import lk.ijse.project_management_tool.utils.ValidationUtils;

public class TaskFormController implements Initializable {
    @FXML
    private ComboBox<String> cmbProjectId;
    @FXML
    private TextField txtTitle;
    @FXML
    private TextArea txtDescription;
    @FXML
    private ComboBox<String> cmbStatus;
    @FXML
    private Spinner<Integer> spnProgress;
    @FXML
    private TextField txtTag;
    private TaskController taskController;
    private ProjectDto projectDto;

    public TaskDto getTaskDto() {
        String selected = cmbProjectId.getValue();
        return new TaskDto(
                null, // id
                txtTitle.getText(), // title
                txtDescription.getText(), // description
                cmbStatus.getValue(), // status
                null, // projectId
                selected != null ? selected.split(" - ", 2)[1] : null, // projectName
                spnProgress.getValue(), // progress
                txtTag.getText(), // tag
                null, // createdAt
                null // updatedAt
        );
    }

    public void setFormData(Long taskId) {
        cmbStatus.setVisible(true);
        try {
            TaskDto dto = new TaskModel().getTaskById(taskId);
            if (dto == null)
                return;
            txtTitle.setText(dto.getTitle());
            txtDescription.setText(dto.getDescription());
            cmbStatus.setValue(dto.getStatus());
            txtTag.setText(dto.getTag());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Long taskId) {
        String title = txtTitle.getText();
        String description = txtDescription.getText();
        String status = cmbStatus.getValue();
        String tag = txtTag.getText();

        TaskDto taskDto = new TaskDto(taskId, title, description, status, null,null, 0, tag, null, null);

        int progress = taskDto.getProgress();
        if (status == "TODO") {
            progress = 0;
        } else if (status == "IN_PROGRESS") {
            progress = 25;
        }else if (status == "IN_WORK") {
            progress = 50;
        }else if (status == "DONE") {
            progress = 100;
        }

        taskDto.setProgress(progress);

        // validation
        boolean isTitle = ValidationUtils.validateInput(txtTitle, "empty");
        boolean isDescription = ValidationUtils.validateInput(txtDescription, "empty");
        boolean isTag = ValidationUtils.validateInput(txtTag, "empty");
        boolean isStatus = ValidationUtils.validateInput(cmbStatus, "empty");

        try {
            if (isTitle && isDescription && isTag && isStatus) {
                if (new TaskModel().updateTask(taskDto)) {
                    taskController.initialize(null, null);
                    AlertUtils.showSuccess("Success","Task update successfully");
                    DialogUtil.close();
                } else {
                    AlertUtils.showError("Error","Failed to update task");
                }
            } else {
                AlertUtils.showError("Error","Please fill all the fields");
            }
        } catch (Exception e) {
            AlertUtils.showError("Error","Failed to update task : "+e.getMessage());
            e.printStackTrace();
        }
    }


    public  void  save(){

        String title = txtTitle.getText();
        String description = txtDescription.getText();
        String tag = txtTag.getText();
        TaskDto taskDto = new TaskDto(null, title, description, null, projectDto.getProjectId(), projectDto.getName(), 0, tag, null, null);

        // validation
        boolean isTitle = ValidationUtils.validateInput(txtTitle, "empty");
        boolean isDescription = ValidationUtils.validateInput(txtDescription, "empty");
        boolean isTag = ValidationUtils.validateInput(txtTag, "empty");

        try {
            if (isTitle && isDescription && isTag) {
                if (new TaskModel().saveTask(taskDto)) {
                    taskController.initialize(null, null);
                    AlertUtils.showSuccess("Success","Task saved successfully");
                    DialogUtil.close();
                } else {
                    AlertUtils.showError("Error","Failed to save task");
                }
            } else {
                AlertUtils.showError("Error","Please fill all the fields");
            }
        } catch (Exception e) {
            AlertUtils.showError("Error","Failed to save task : "+e.getMessage());
            e.printStackTrace();
        }
    }

    public void init(TaskController taskController,ProjectDto projectDto) {
        this.taskController = taskController;
        this.projectDto = projectDto;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbStatus.getItems().addAll("TODO", "IN_WORK", "IN_PROGRESS", "DONE");
        cmbStatus.setVisible(false);
    }
}