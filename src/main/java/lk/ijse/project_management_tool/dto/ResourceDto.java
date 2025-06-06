package lk.ijse.project_management_tool.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResourceDto {
    private Long resId;
    private Long projectId;
    private String type;
    private String path;
    private String note;
} 