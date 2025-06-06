package lk.ijse.project_management_tool.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamDto {
    private int teamId;
    private String name;
    private String description;
    private String status;
    private int teamMemberCount;


    public TeamDto(int teamId, String name, String description, String status) {
        this.teamId = teamId;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public TeamDto(String name, String description) {
        this.name = name;
        this.description = description;
    }
}