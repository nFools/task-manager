package org.nfools.taskmanager.controller;


import org.nfools.taskmanager.entity.data.transfer.TaskDTO;
import org.nfools.taskmanager.entity.view.TaskVO;
import org.nfools.taskmanager.map.TaskMap;
import org.nfools.taskmanager.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xuancao
 */
@Controller
@RequestMapping("${server.servlet.ui-path}")
public class UiController {

    @Resource
    private TaskMap taskMapImpl;

    @Resource
    private TaskService taskService;

    @RequestMapping("/")
    public String index(Model model) throws Exception {
        List<TaskVO> taskVOList = taskMapImpl.toVOs(taskService.findAll());
        model.addAttribute("taskVOList", taskVOList);
        return "index";
    }


    @GetMapping("/refresh")
    public String refresh(Model model) throws Exception {
        List<TaskDTO> taskList = taskService.findAll();
        List<TaskVO> taskVOList = taskMapImpl.toVOs(taskList);
        model.addAttribute("taskVOList", taskVOList);
        return "index::task-table";
    }

    @GetMapping("/detail/{id}")
    @ResponseBody
    public String detail(@PathVariable("id") Long id) {
        return id.toString();
    }

}
