package lk.ijse.project_management_tool.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {
    private Long projectId;
    private String name;
    private String description;
    private String startDate;
    private String endDate;
    private String duration;
    private String status;
    private Long teamId;

    public ProjectDto(String name, String description, String startDate, String endDate, String duration, int teamId) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = duration;
        this.teamId = (long) teamId;
    }
}