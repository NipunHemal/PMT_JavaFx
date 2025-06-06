package lk.ijse.project_management_tool.model;

import lk.ijse.project_management_tool.dto.AssignmentDto;
import lk.ijse.project_management_tool.utils.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AssignmentModel {
    public boolean saveAssignment(AssignmentDto assignment) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO assignments (employee_id, task_id, note, status) VALUES (?,?,?,?)";
        return CrudUtil.execute(sql,
                assignment.getEmployeeId(),
                assignment.getTaskId(),
                assignment.getNote(),
                assignment.getStatus()
        );
    }

    public boolean updateAssignment(AssignmentDto assignment) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE assignments SET note=?, status=? WHERE employee_id=? AND task_id=?";
        return CrudUtil.execute(sql,
                assignment.getNote(),
                assignment.getStatus(),
                assignment.getEmployeeId(),
                assignment.getTaskId()
        );
    }

    public boolean deleteAssignment(Long employeeId, Long taskId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM assignments WHERE employee_id=? AND task_id=?";
        return CrudUtil.execute(sql, employeeId, taskId);
    }

    public ArrayList<AssignmentDto> getAllAssignments() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM assignments";
        ResultSet resultSet = CrudUtil.execute(sql);
        ArrayList<AssignmentDto> assignments = new ArrayList<>();

        while (resultSet.next()) {
            assignments.add(new AssignmentDto(
                    resultSet.getLong("employee_id"),
                    resultSet.getLong("task_id"),
                    resultSet.getString("note"),
                    resultSet.getString("status")
            ));
        }
        return assignments;
    }

    public ArrayList<AssignmentDto> getAssignmentsByEmployeeId(Long employeeId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM assignments WHERE employee_id=?";
        ResultSet resultSet = CrudUtil.execute(sql, employeeId);
        ArrayList<AssignmentDto> assignments = new ArrayList<>();

        while (resultSet.next()) {
            assignments.add(new AssignmentDto(
                    resultSet.getLong("employee_id"),
                    resultSet.getLong("task_id"),
                    resultSet.getString("note"),
                    resultSet.getString("status")
            ));
        }
        return assignments;
    }

    public ArrayList<AssignmentDto> getAssignmentsByTaskId(Long taskId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM assignments WHERE task_id=?";
        ResultSet resultSet = CrudUtil.execute(sql, taskId);
        ArrayList<AssignmentDto> assignments = new ArrayList<>();

        while (resultSet.next()) {
            assignments.add(new AssignmentDto(
                    resultSet.getLong("employee_id"),
                    resultSet.getLong("task_id"),
                    resultSet.getString("note"),
                    resultSet.getString("status")
            ));
        }
        return assignments;
    }
} 