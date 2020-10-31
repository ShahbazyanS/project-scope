package am.itspace.projectscope.dto;

import am.itspace.projectscope.model.Projects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogDto {

    private Projects projects;
    private Double hours;
}
