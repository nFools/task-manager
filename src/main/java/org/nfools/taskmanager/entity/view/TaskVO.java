package org.nfools.taskmanager.entity.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xuancao
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaskVO {

    private String id;
    private String taskName;
    private String createTime;
    private String updatedTime;
    private Integer progress;
    private String remarks;
    private String viewUrl;

}
