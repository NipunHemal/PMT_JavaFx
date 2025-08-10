package lk.ijse.project_management_tool.model;

import lk.ijse.project_management_tool.db.DbConnector;
import lk.ijse.project_management_tool.dto.EmployeeDto;
import lk.ijse.project_management_tool.utils.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeModel {
    public boolean saveEmployee(EmployeeDto employee) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO employee (name, email, contact, address, role, password,profile ) VALUES (?,?,?,?,?,?,?)";
        return CrudUtil.execute(sql,
                employee.getName(),
                employee.getEmail(),
                employee.getContact(),
                employee.getAddress(),
                employee.getRole(),
                employee.getPassword(),
                employee.getProfile()
        );
    }

    public boolean updateEmployee(EmployeeDto employee) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE employee SET name=?, email=?, contact=?, address=?, role=?, password=?, status=?, team_id=? WHERE employee_id=?";
        return CrudUtil.execute(sql,
                employee.getName(),
                employee.getEmail(),
                employee.getContact(),
                employee.getAddress(),
                employee.getRole(),
                employee.getPassword(),
                employee.getStatus(),
                employee.getTeamId(),
                employee.getEmployeeId()
        );
    }

    public boolean deleteEmployee(Long employeeId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM employee WHERE employee_id=?";
        return CrudUtil.execute(sql, employeeId);
    }

    public ArrayList<EmployeeDto> getAllEmployees() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM employee";
        ResultSet resultSet = CrudUtil.execute(sql);
        ArrayList<EmployeeDto> employees = new ArrayList<>();

        while (resultSet.next()) {
            employees.add(new EmployeeDto(
                    resultSet.getLong("employee_id"),
                    resultSet.getString("name"),
                    resultSet.getString("email"),
                    resultSet.getString("contact"),
                    resultSet.getString("address"),
                    resultSet.getString("role"),
                    resultSet.getString("password"),
                    resultSet.getString("status"),
                    resultSet.getLong("team_id"),
                    resultSet.getString("profile")
            ));
        }
        return employees;
    }

    public EmployeeDto getEmployeeById(Long employeeId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM employee WHERE employee_id=?";
        ResultSet resultSet = CrudUtil.execute(sql, employeeId);

        if (resultSet.next()) {
            return new EmployeeDto(
                    resultSet.getLong("employee_id"),
                    resultSet.getString("name"),
                    resultSet.getString("email"),
                    resultSet.getString("contact"),
                    resultSet.getString("address"),
                    resultSet.getString("role"),
                    resultSet.getString("password"),
                    resultSet.getString("status"),
                    resultSet.getLong("team_id"),
                    null
            );
        }
        return null;
    }

    public ArrayList<EmployeeDto> getEmployeesByTeamId(int teamId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM employee WHERE team_id=?";
        ResultSet resultSet = CrudUtil.execute(sql, teamId);
        ArrayList<EmployeeDto> employees = new ArrayList<>();

        while (resultSet.next()) {
            employees.add(new EmployeeDto(
                    resultSet.getLong("employee_id"),
                    resultSet.getString("name"),
                    resultSet.getString("email"),
                    resultSet.getString("contact"),
                    resultSet.getString("address"),
                    resultSet.getString("role"),
                    resultSet.getString("password"),
                    resultSet.getString("status"),
                    resultSet.getLong("team_id"),
                    resultSet.getString("profile")
            ));
        }
        return employees;
    }

    public EmployeeDto getEmployeeByEmail(String email) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM employee WHERE email=?";
        ResultSet resultSet = CrudUtil.execute(sql, email);

        if (resultSet.next()) {
            return new EmployeeDto(
                    resultSet.getLong("employee_id"),
                    resultSet.getString("name"),
                    resultSet.getString("email"),
                    resultSet.getString("contact"),
                    resultSet.getString("address"),
                    resultSet.getString("role"),
                    null,
                    resultSet.getString("status"),
                    resultSet.getLong("team_id"),
                    resultSet.getString("profile")
            );
        }
        return null;
    }

    public void getEmployeeByTeamId(Long teamId) throws SQLException, ClassNotFoundException {
        try{
            String sql = "SELECT * FROM employee WHERE team_id=?";
            ResultSet resultSet = CrudUtil.execute(sql, teamId);
            ArrayList<EmployeeDto> employees = new ArrayList<>();

            while (resultSet.next()) {
                employees.add(new EmployeeDto(
                        resultSet.getLong("employee_id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("contact"),
                        resultSet.getString("address"),
                        resultSet.getString("role"),
                        null,
                        resultSet.getString("status"),
                        resultSet.getLong("team_id"),
                        resultSet.getString("profile")
                ));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean addTeamToEmployee(int employeeId, int teamId) throws SQLException, ClassNotFoundException {
        Connection conn = DbConnector.getInstance().getConnection();
        conn.setAutoCommit(false);
        try{

            ResultSet result = CrudUtil.execute("SELECT * FROM employee WHERE employee_id=? AND team_id=?", employeeId,teamId);
            if (result.next()) {
                conn.rollback();
                throw new RuntimeException("Team already assigned to this employee.");
            }

            String sql = "UPDATE employee SET team_id=? WHERE employee_id=?";
            boolean isUpdated = CrudUtil.execute(sql, teamId,employeeId);

            if (isUpdated) {
                conn.commit();
                return true;
            } else {
                conn.rollback();
                return false;
            }
        } catch (Exception e) {
            conn.rollback();
            throw new RuntimeException(e);
        } finally {
            conn.setAutoCommit(true);
        }
    }

    public boolean changeEmployeeStatus(int employeeId, String status) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE employee SET status=? WHERE employee_id=?";
        return CrudUtil.execute(sql, status, employeeId);
    }

    public ArrayList<Integer> summaryOfEmployeeCount() throws SQLException, ClassNotFoundException {
        String sql = "SELECT (SELECT COUNT(employee_id) from employee) as allEmployee, (SELECT COUNT(employee_id) from employee where status = 'ACTIVE') as pendingEmployee, (SELECT COUNT(employee_id) from employee where status = 'PENDING') as activeEmployee, (SELECT COUNT(employee_id) from employee where status = 'INACTIVE') as inactiveEmployee";
        ResultSet resultSet = CrudUtil.execute(sql);
        ArrayList<Integer> employeeCount = new ArrayList<>();
        while (resultSet.next()) {
            employeeCount.add(resultSet.getInt(1));
            employeeCount.add(resultSet.getInt(2));
            employeeCount.add(resultSet.getInt(3));
            employeeCount.add(resultSet.getInt(4));
        }
        return employeeCount;
    }

}