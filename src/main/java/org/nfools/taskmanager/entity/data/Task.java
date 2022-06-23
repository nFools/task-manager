package org.nfools.taskmanager.entity.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task{

    private Long id;
    private LocalDateTime createTime;
    private LocalDateTime updatedTime;
    private String taskName;
    private Integer ok;
    private String remarks;
    private String path;
    private String viewUrl;

}
