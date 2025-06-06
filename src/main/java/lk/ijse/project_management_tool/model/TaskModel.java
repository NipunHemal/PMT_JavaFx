package lk.ijse.project_management_tool.model;

import lk.ijse.project_management_tool.dto.TaskDto;
import lk.ijse.project_management_tool.utils.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TaskModel {
    public boolean saveTask(TaskDto task) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO tasks (title, description, status, project_id, project_name, progress, tag) VALUES (?,?,?,?,?,?,?)";
        return CrudUtil.execute(sql,
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getProjectId(),
                task.getProjectName(),
                task.getProgress(),
                task.getTag());
    }

    public boolean updateTask(TaskDto task) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE tasks SET title=?, description=?, status=?, project_id=?, project_name=?, progress=?, tag=? WHERE id=?";
        return CrudUtil.execute(sql,
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getProjectId(),
                task.getProjectName(),
                task.getProgress(),
                task.getTag(),
                task.getId());
    }

    public boolean deleteTask(Long taskId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM tasks WHERE id=?";
        return CrudUtil.execute(sql, taskId);
    }

    public ArrayList<TaskDto> getAllTasks() throws SQLException, ClassNotFoundException {
        String sql = "SELECT t.*, p.name as project_name FROM tasks t LEFT JOIN projects p ON t.project_id = p.project_id";
        ResultSet resultSet = CrudUtil.execute(sql);
        ArrayList<TaskDto> tasks = new ArrayList<>();

        while (resultSet.next()) {
            tasks.add(new TaskDto(
                    resultSet.getLong("id"),
                    resultSet.getString("title"),
                    resultSet.getString("description"),
                    resultSet.getString("status"),
                    resultSet.getLong("project_id"),
                    resultSet.getString("project_name"),
                    resultSet.getInt("progress"),
                    resultSet.getString("tag"),
                    resultSet.getDate("created_at"),
                    resultSet.getDate("updated_at")));
        }
        return tasks;
    }

    public TaskDto getTaskById(Long taskId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT t.*, p.name as project_name FROM tasks t LEFT JOIN projects p ON t.project_id = p.project_id WHERE t.id=?";
        ResultSet resultSet = CrudUtil.execute(sql, taskId);

        if (resultSet.next()) {
            return new TaskDto(
                    resultSet.getLong("id"),
                    resultSet.getString("title"),
                    resultSet.getString("description"),
                    resultSet.getString("status"),
                    resultSet.getLong("project_id"),
                    resultSet.getString("project_name"),
                    resultSet.getInt("progress"),
                    resultSet.getString("tag"),
                    resultSet.getDate("created_at"),
                    resultSet.getDate("updated_at"));
        }
        return null;
    }

    public ArrayList<TaskDto> getTasksByProjectId(Long projectId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT t.*, p.name as project_name FROM tasks t LEFT JOIN projects p ON t.project_id = p.project_id WHERE t.project_id=?";
        ResultSet resultSet = CrudUtil.execute(sql, projectId);
        ArrayList<TaskDto> tasks = new ArrayList<>();

        while (resultSet.next()) {
            tasks.add(new TaskDto(
                    resultSet.getLong("id"),
                    resultSet.getString("title"),
                    resultSet.getString("description"),
                    resultSet.getString("status"),
                    resultSet.getLong("project_id"),
                    resultSet.getString("project_name"),
                    resultSet.getInt("progress"),
                    resultSet.getString("tag"),
                    resultSet.getDate("created_at"),
                    resultSet.getDate("updated_at")));
        }
        return tasks;
    }

    public boolean updateTaskStatus(Long taskId, String status) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE tasks SET status=? WHERE id=?";
        return CrudUtil.execute(sql, status, taskId);
    }
}