package org.nfools.taskmanager.mapper;

import org.nfools.taskmanager.entity.data.Task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xuancao
 */
@Mapper
public interface TaskMapper {

    List<Task> selectAll();
    Task selectById(@Param("id") Long id);
    void insert(@Param("task")Task task);

    Integer updateViewUrl(@Param("task")Task task);

    Integer delete(Long taskId);

    Integer updateProgress(@Param("task")Task task);
}
