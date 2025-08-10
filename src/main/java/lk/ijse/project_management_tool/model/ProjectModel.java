package lk.ijse.project_management_tool.model;

import lk.ijse.project_management_tool.dto.ProjectDto;
import lk.ijse.project_management_tool.utils.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProjectModel {
    public boolean saveProject(ProjectDto project) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO projects (name, description, start_date, end_date, duration, status, team_id) VALUES (?,?,?,?,?,?,?)";
        return CrudUtil.execute(sql,
                project.getName(),
                project.getDescription(),
                project.getStartDate(),
                project.getEndDate(),
                project.getDuration(),
                project.getStatus(),
                project.getTeamId()
        );
    }

    public boolean updateProject(ProjectDto project) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE projects SET name=?, description=?, start_date=?, end_date=?, duration=?, status=?, team_id=? WHERE project_id=?";
        return CrudUtil.execute(sql,
                project.getName(),
                project.getDescription(),
                project.getStartDate(),
                project.getEndDate(),
                project.getDuration(),
                project.getStatus(),
                project.getTeamId(),
                project.getProjectId()
        );
    }

    public boolean deleteProject(int projectId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM projects WHERE project_id=?";
        return CrudUtil.execute(sql, projectId);
    }

    public ArrayList<ProjectDto> getAllProjects() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM projects";
        ResultSet resultSet = CrudUtil.execute(sql);
        ArrayList<ProjectDto> projects = new ArrayList<>();

        while (resultSet.next()) {
            projects.add(new ProjectDto(
                    resultSet.getLong("project_id"),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getString("start_date"),
                    resultSet.getString("end_date"),
                    resultSet.getString("duration"),
                    resultSet.getString("status"),
                    resultSet.getLong("team_id")
            ));
        }
        return projects;
    }

    public ProjectDto getProjectById(Long projectId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM projects WHERE project_id=?";
        ResultSet resultSet = CrudUtil.execute(sql, projectId);

        if (resultSet.next()) {
            return new ProjectDto(
                    resultSet.getLong("project_id"),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getString("start_date"),
                    resultSet.getString("end_date"),
                    resultSet.getString("duration"),
                    resultSet.getString("status"),
                    resultSet.getLong("team_id")
            );
        }
        return null;
    }

    public ArrayList<ProjectDto> getProjectsByTeamId(Long teamId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM projects WHERE team_id=?";
        ResultSet resultSet = CrudUtil.execute(sql, teamId);
        ArrayList<ProjectDto> projects = new ArrayList<>();

        while (resultSet.next()) {
            projects.add(new ProjectDto(
                    resultSet.getLong("project_id"),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getString("start_date"),
                    resultSet.getString("end_date"),
                    resultSet.getString("duration"),
                    resultSet.getString("status"),
                    resultSet.getLong("team_id")
            ));
        }
        return projects;
    }

    public boolean updateProjectStatus(int projectId, String status) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE projects SET status=? WHERE project_id=?";
        return CrudUtil.execute(sql, status, projectId);
    }

    public boolean updateProjectTeam(int projectId, int teamId) throws SQLException, ClassNotFoundException {


        String sql = "UPDATE projects SET team_id=? WHERE project_id=?";
        return CrudUtil.execute(sql, teamId, projectId);
    }

    public ArrayList<Integer> summaryOfProjectCount() throws SQLException, ClassNotFoundException {
        String sql = "SELECT (SELECT COUNT(project_id) from projects) as AllProject, (SELECT COUNT(project_id) from projects where status = 'In Progress') as InProgressProject, (SELECT COUNT(project_id) from projects where status = 'Completed') as CompletedProject, (SELECT COUNT(project_id) from projects where status = 'On Hold') as OnHoldProject, (SELECT COUNT(project_id) from projects where status = 'Cancelled') as CancelledProject;";
        ResultSet resultSet = CrudUtil.execute(sql);
        ArrayList<Integer> projectCount = new ArrayList<>();
        while (resultSet.next()) {
            projectCount.add(resultSet.getInt(1));
            projectCount.add(resultSet.getInt(2));
            projectCount.add(resultSet.getInt(3));
            projectCount.add(resultSet.getInt(4));
            projectCount.add(resultSet.getInt(5));
        }
        return projectCount;
    }
}