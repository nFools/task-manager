package org.nfools.taskmanager.component;

import lombok.extern.slf4j.Slf4j;
import org.nfools.taskmanager.entity.data.transfer.TaskDTO;
import org.nfools.taskmanager.service.TaskService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
@EnableScheduling
@Slf4j
public class TaskMonitor {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private TaskService taskService;

    @Scheduled(fixedDelay = 60 * 1000)
    public void gc() {
        log.debug("gc");
        try {
            List<TaskDTO> incompletion = taskService.findIncompletion();
            incompletion.forEach(item -> {
                if (null == stringRedisTemplate.opsForValue().get(String.valueOf(item.getId()))) {
                    item.setProgress(-1);
                    stringRedisTemplate.delete(String.valueOf(item.getId()));
                    log.info("GC [{}]", item);
                    taskService.updateProgress(item);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
