package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString


public class CompletedTask {

    private String taskName;
    private String taskAssignedDate;
    private String taskCompletedDate;

}
