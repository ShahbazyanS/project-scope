package am.itspace.projectscope.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectDto {

    private String name;
    private Date deadline;
    private String members;
    private double hours;
}
