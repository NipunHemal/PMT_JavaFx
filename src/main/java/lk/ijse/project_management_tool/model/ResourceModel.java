package lk.ijse.project_management_tool.model;

import lk.ijse.project_management_tool.dto.ResourceDto;
import lk.ijse.project_management_tool.utils.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ResourceModel {
    public boolean saveResource(ResourceDto resource) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO resources (project_id, type, path, note) VALUES (?,?,?,?)";
        return CrudUtil.execute(sql,
                resource.getProjectId(),
                resource.getType(),
                resource.getPath(),
                resource.getNote()
        );
    }

    public boolean updateResource(ResourceDto resource) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE resources SET project_id=?, type=?, path=?, note=? WHERE res_id=?";
        return CrudUtil.execute(sql,
                resource.getProjectId(),
                resource.getType(),
                resource.getPath(),
                resource.getNote(),
                resource.getResId()
        );
    }

    public boolean deleteResource(Long resId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM resources WHERE res_id=?";
        return CrudUtil.execute(sql, resId);
    }

    public ArrayList<ResourceDto> getAllResources() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM resources";
        ResultSet resultSet = CrudUtil.execute(sql);
        ArrayList<ResourceDto> resources = new ArrayList<>();

        while (resultSet.next()) {
            resources.add(new ResourceDto(
                    resultSet.getLong("res_id"),
                    resultSet.getLong("project_id"),
                    resultSet.getString("type"),
                    resultSet.getString("path"),
                    resultSet.getString("note")
            ));
        }
        return resources;
    }

    public ResourceDto getResourceById(Long resId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM resources WHERE res_id=?";
        ResultSet resultSet = CrudUtil.execute(sql, resId);

        if (resultSet.next()) {
            return new ResourceDto(
                    resultSet.getLong("res_id"),
                    resultSet.getLong("project_id"),
                    resultSet.getString("type"),
                    resultSet.getString("path"),
                    resultSet.getString("note")
            );
        }
        return null;
    }

    public ArrayList<ResourceDto> getResourcesByProjectId(Long projectId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM resources WHERE project_id=?";
        ResultSet resultSet = CrudUtil.execute(sql, projectId);
        ArrayList<ResourceDto> resources = new ArrayList<>();

        while (resultSet.next()) {
            resources.add(new ResourceDto(
                    resultSet.getLong("res_id"),
                    resultSet.getLong("project_id"),
                    resultSet.getString("type"),
                    resultSet.getString("path"),
                    resultSet.getString("note")
            ));
        }
        return resources;
    }
} 