package org.nfools.taskmanager.service;

import org.nfools.taskmanager.entity.data.transfer.TaskDTO;

import java.util.List;

public interface TaskService {
    void addTask(TaskDTO taskDTO);

    TaskDTO findByTaskId(Long taskId);


    List<TaskDTO> findAll();
    List<TaskDTO> findIncompletion();

    Integer updateViewUrl(TaskDTO taskDTO);

    void deleteTask(Long taskId);

    void startTask(TaskDTO taskDTO);

    Integer updateProgress(TaskDTO taskDTO);
}
