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

    public EmployeeDto(String name, String email, String contact, String address, String role, String password,String profile) {
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.address = address;
        this.role = role;
        this.password = password;
        this.profile = profile;
    }
}