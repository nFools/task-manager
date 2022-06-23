package org.nfools.taskmanager.service.impl;

import org.nfools.taskmanager.entity.data.Task;
import org.nfools.taskmanager.entity.data.transfer.TaskDTO;
import org.nfools.taskmanager.mapper.TaskMapper;
import org.nfools.taskmanager.service.AbstractService;
import org.nfools.taskmanager.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.nfools.commons.entity.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TaskServiceImpl extends AbstractService implements TaskService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private TaskMapper taskMapper;

    @Value("${task-manager.third-party.analysis-url}")
    private String analysisUrl;

    @Override
    public void addTask(TaskDTO taskDTO) {
        Task task = taskMapImpl.toDO(taskDTO);
        taskMapper.insert(task);
        taskDTO.setId(task.getId());
    }

    @Override
    public TaskDTO findByTaskId(Long taskId) {
        return taskMapImpl.toDTO(taskMapper.selectById(taskId));
    }

    @Override
    public List<TaskDTO> findAll() {
        List<TaskDTO> taskDTOList = taskMapImpl.toDTOs(taskMapper.selectAll());
        return taskDTOList.stream().peek(taskDTO -> {
            try {
                if (null == taskDTO.getProgress()) {
                    int progress = Integer.parseInt(stringRedisTemplate.opsForValue().get(String.valueOf(taskDTO.getId())));
                    taskDTO.setProgress(progress);
                }
            } catch (Exception e) {
                taskDTO.setProgress(-1);
                log.warn(e.getMessage());
            }
        }).collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> findIncompletion() {
        List<TaskDTO> taskDTOList = taskMapImpl.toDTOs(taskMapper.selectAll());
        return taskDTOList.stream().filter(taskDTO -> null == taskDTO.getProgress() || (taskDTO.getProgress() > -1 && taskDTO.getProgress() < 100)).collect(Collectors.toList());
    }

    @Override
    public Integer updateViewUrl(TaskDTO taskDTO) {
        return taskMapper.updateViewUrl(taskMapImpl.toDO(taskDTO));
    }

    @Override
    public void deleteTask(Long taskId) {
        taskMapper.delete(taskId);
    }

    @Override
    public void startTask(TaskDTO taskDTO) {
        RestTemplate restTemplate = new RestTemplate();
        log.info("[{}] post [{}]",taskDTO.getId(), taskDTO);
        ResponseEntity<Response<Map<String, Object>>> responseEntity = restTemplate.exchange(analysisUrl, HttpMethod.POST, new HttpEntity<>(taskDTO), new ParameterizedTypeReference<Response<Map<String, Object>>>() {});
        log.info("[{}] return [{}]",taskDTO.getId(), responseEntity);
        Map<String, Object> map = responseEntity.getBody().getData();
        taskDTO.setProgress(0);
        taskDTO.setViewUrl(String.valueOf(map.get("viewUrl")));
    }

    @Override
    public Integer updateProgress(TaskDTO taskDTO) {
        return taskMapper.updateProgress(taskMapImpl.toDO(taskDTO));
    }

}
