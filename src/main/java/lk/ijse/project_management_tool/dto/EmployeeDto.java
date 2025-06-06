package lk.ijse.project_management_tool.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    private Long employeeId;
    private String name;
    private String email;
    private String contact;
    private String address;
    private String role;
    private String password;
    private String status;
    private Long teamId;
    private String profile;
}