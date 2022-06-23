package org.nfools.taskmanager.map;

import org.nfools.taskmanager.entity.data.Task;
import org.nfools.taskmanager.entity.data.transfer.TaskDTO;
import org.nfools.taskmanager.entity.view.TaskVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;

/**
 * @author Xuan Cao
 * @date 2022/6/13
 */
@Mapper(componentModel = "spring")
public interface TaskMap {


    @Mapping(source = "createTime", target = "createTime", qualifiedByName = "localDateTime2Long")
    @Mapping(source = "updatedTime", target = "updatedTime", qualifiedByName = "localDateTime2Long")
    @Mapping(source = "ok", target = "progress", qualifiedByName = "ok2Progress")
    TaskDTO toDTO(Task task);

    List<TaskDTO> toDTOs(List<Task> taskList);

    @Mapping(source = "progress", target = "ok", qualifiedByName = "parseProgress")
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updatedTime", ignore = true)
    Task toDO(TaskDTO taskDTO);

    List<Task> toDOs(List<TaskDTO> taskDTOList);

    @Mapping(source = "createTime", target = "createTime", qualifiedByName = "timeAsLong2String")
    @Mapping(source = "updatedTime", target = "updatedTime", qualifiedByName = "timeAsLong2String")
    TaskVO toVO(TaskDTO taskDTO);

    List<TaskVO> toVOs(List<TaskDTO> taskDTOList);

    @Named("timeAsLong2String")
    default String timeAsLong2String(Long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault()).toString();
    }

    @Named("parseProgress")
    default Integer parseProgress(Integer progress) {
        if (Objects.equals(100, progress)) {
            return 1;
        }
        if (Objects.equals(-1, progress)) {
            return -1;
        }
        return 0;
    }

    @Named("ok2Progress")
    default Integer ok2Progress(Integer ok) {
        if (ok > 0) {
            return 100;
        } else if (Objects.equals(0, ok)) {
            return null;
        } else {
            return -1;
        }
    }

    @Named("localDateTime2Long")
    default Long localDateTime2Long(LocalDateTime localDateTime) {
        return Timestamp.valueOf(localDateTime).getTime();
    }


}
