package lk.ijse.project_management_tool.controller.component;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import lk.ijse.project_management_tool.dto.TaskDto;
import java.sql.Date;
import lk.ijse.project_management_tool.model.ProjectModel;
import lk.ijse.project_management_tool.dto.ProjectDto;
import java.util.List;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import java.util.HashMap;
import lk.ijse.project_management_tool.model.TaskModel;
import java.time.LocalDate;
import java.time.ZoneId;

public class TaskFormController {
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

    private HashMap<String, Long> projectMap = new HashMap<>();

    public TaskDto getTaskDto() {
        String selected = cmbProjectId.getValue();
        Long projectId = projectMap.get(selected);
        return new TaskDto(
                null, // id
                txtTitle.getText(), // title
                txtDescription.getText(), // description
                cmbStatus.getValue(), // status
                projectId, // projectId
                selected != null ? selected.split(" - ", 2)[1] : null, // projectName
                spnProgress.getValue(), // progress
                txtTag.getText(), // tag
                null, // createdAt
                null // updatedAt
        );
    }

    @FXML
    public void initialize() {
        cmbStatus.getItems().addAll("TODO", "IN_WORK", "IN_PROGRESS", "DONE");
        spnProgress.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        try {
            List<ProjectDto> projects = new ProjectModel().getAllProjects();
            for (ProjectDto project : projects) {
                String display = project.getProjectId() + " - " + project.getName();
                cmbProjectId.getItems().add(display);
                projectMap.put(display, project.getProjectId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setFormData(Long taskId) {
        try {
            TaskDto dto = new TaskModel().getTaskById(taskId);
            if (dto == null)
                return;
            for (String key : projectMap.keySet()) {
                if (projectMap.get(key).equals(dto.getProjectId())) {
                    cmbProjectId.setValue(key);
                    break;
                }
            }
            txtTitle.setText(dto.getTitle());
            txtDescription.setText(dto.getDescription());
            cmbStatus.setValue(dto.getStatus());
            spnProgress.getValueFactory().setValue(dto.getProgress());
            txtTag.setText(dto.getTag());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}