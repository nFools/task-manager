package org.nfools.taskmanager.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.nfools.commons.entity.Response;
import org.nfools.taskmanager.entity.data.transfer.TaskDTO;
import org.nfools.taskmanager.entity.view.TaskVO;
import org.nfools.taskmanager.map.TaskMap;
import org.nfools.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author xuancao
 */
@RequestMapping("${task-manager.api-prefix}")
@RestController
@Slf4j
public class ApiController {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private TaskMap taskMapImpl;

    @Resource
    private TaskService taskService;

    @Value("${task-manager.data-path}")
    private String dataPathString;

    @Value("${task-manager.timeout}")
    private Long timeout;

    @Value("${task-manager.third-party.view-url}")
    private String viewUrl;

    @PostMapping("/create-task")
    public Response createTask(@RequestParam(value = "files", required = false) MultipartFile[] multipartFiles, @RequestParam("parameterString") String parameterString) throws Exception {
        TaskDTO taskDTO = new ObjectMapper().readValue(parameterString, new TypeReference<TaskDTO>() {});
        log.info("task [{}]", taskDTO);
        taskDTO.setProgress(-1);
        taskDTO.setPath("");
        taskService.addTask(taskDTO);
        Path timePath = Paths.get(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)), String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1), String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)));
        String parent = Paths.get(dataPathString, timePath.toString(), String.valueOf(taskDTO.getId())).toString();
        new File(parent).mkdirs();
        if (null == multipartFiles || 0 == multipartFiles.length) {
            log.warn("[{}] no files", taskDTO.getId());
            return new Response(true);
        }
        for (MultipartFile multipartFile : multipartFiles) {
            String fileName = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(File.separator) + 1);
            File destination = Paths.get(parent, fileName).toFile();
            multipartFile.transferTo(destination);
            log.info("[{}] save {}", taskDTO.getId(), multipartFile.getOriginalFilename());
        }
        stringRedisTemplate.opsForValue().set(String.valueOf(taskDTO.getId()), String.valueOf(0), timeout, TimeUnit.MINUTES);
        taskDTO.setPath(parent);
        taskService.startTask(taskDTO);
        taskService.updateViewUrl(taskDTO);
        return new Response(true);
    }

    @DeleteMapping("/delete-task/{taskId}")
    public Response deleteTask(@PathVariable("taskId") Long taskId) {
        taskService.deleteTask(taskId);
        Response response = new Response(true);
        return response;
    }

    @RequestMapping("/refresh")
    public Response<List<TaskVO>> refresh() throws Exception {
        List<TaskDTO> taskList = taskService.findAll();
        List<TaskVO> taskVOList = taskMapImpl.toVOs(taskList);
        Response<List<TaskVO>> response = new Response<>(true);
        response.setData(taskVOList);
        return response;
    }

    @PostMapping("/analyse")
    public Response<Map<String, Object>> analyse(@RequestBody TaskDTO taskDTO) throws Exception {
        log.info("[{}] analyse [{}]",taskDTO.getId(), taskDTO);
        new Thread(() -> {
            Random random = new Random();
            for (int progress = 1; progress <= 100; progress++) {
                taskDTO.setProgress(progress);
                try {
                    update(taskDTO);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                try {
                    Thread.sleep(random.nextInt(600) * 1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
        Map<String, Object> map = new HashMap<>(1);
        map.put("viewUrl", viewUrl + "/" + taskDTO.getId());
        return new Response<>(true, map);
    }

    @PutMapping("/update")
    public Response update(@RequestBody TaskDTO taskDTO) throws Exception {

        stringRedisTemplate.opsForValue().setIfPresent(String.valueOf(taskDTO.getId()), String.valueOf(taskDTO.getProgress()), timeout, TimeUnit.MINUTES);
        if (Objects.equals(100, taskDTO.getProgress())) {
            taskService.updateProgress(taskDTO);
        }
        if (taskDTO.getProgress() < -1 || taskDTO.getProgress() > 100) {
            throw new RuntimeException("Illegal progress " + taskDTO.getProgress());
        }
        return new Response(true);
    }


}
