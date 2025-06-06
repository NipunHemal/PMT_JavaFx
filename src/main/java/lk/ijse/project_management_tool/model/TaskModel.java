package lk.ijse.project_management_tool.model;

import lk.ijse.project_management_tool.dto.TaskDto;
import lk.ijse.project_management_tool.utils.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TaskModel {
    public boolean saveTask(TaskDto task) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO tasks (project_id, note, start_date, deadline, status) VALUES (?,?,?,?,?)";
        return CrudUtil.execute(sql,
                task.getProjectId(),
                task.getNote(),
                task.getStartDate(),
                task.getDeadline(),
                task.getStatus()
        );
    }

    public boolean updateTask(TaskDto task) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE tasks SET project_id=?, note=?, start_date=?, deadline=?, status=? WHERE task_id=?";
        return CrudUtil.execute(sql,
                task.getProjectId(),
                task.getNote(),
                task.getStartDate(),
                task.getDeadline(),
                task.getStatus(),
                task.getTaskId()
        );
    }

    public boolean deleteTask(Long taskId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM tasks WHERE task_id=?";
        return CrudUtil.execute(sql, taskId);
    }

    public ArrayList<TaskDto> getAllTasks() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM tasks";
        ResultSet resultSet = CrudUtil.execute(sql);
        ArrayList<TaskDto> tasks = new ArrayList<>();

        while (resultSet.next()) {
            tasks.add(new TaskDto(
                    resultSet.getLong("task_id"),
                    resultSet.getLong("project_id"),
                    resultSet.getString("note"),
                    resultSet.getString("start_date"),
                    resultSet.getDate("deadline"),
                    resultSet.getString("status")
            ));
        }
        return tasks;
    }

    public TaskDto getTaskById(Long taskId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM tasks WHERE task_id=?";
        ResultSet resultSet = CrudUtil.execute(sql, taskId);

        if (resultSet.next()) {
            return new TaskDto(
                    resultSet.getLong("task_id"),
                    resultSet.getLong("project_id"),
                    resultSet.getString("note"),
                    resultSet.getString("start_date"),
                    resultSet.getDate("deadline"),
                    resultSet.getString("status")
            );
        }
        return null;
    }

    public ArrayList<TaskDto> getTasksByProjectId(Long projectId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM tasks WHERE project_id=?";
        ResultSet resultSet = CrudUtil.execute(sql, projectId);
        ArrayList<TaskDto> tasks = new ArrayList<>();

        while (resultSet.next()) {
            tasks.add(new TaskDto(
                    resultSet.getLong("task_id"),
                    resultSet.getLong("project_id"),
                    resultSet.getString("note"),
                    resultSet.getString("start_date"),
                    resultSet.getDate("deadline"),
                    resultSet.getString("status")
            ));
        }
        return tasks;
    }
} 