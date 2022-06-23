package org.nfools.taskmanager.entity.data.transfer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {

    private Long id;
    private Long createTime;
    private Long updatedTime;
    private String taskName;
    private Integer progress;
    private String remarks;
    private String path;
    private String viewUrl;

}
