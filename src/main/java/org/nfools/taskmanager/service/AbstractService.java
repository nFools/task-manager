package org.nfools.taskmanager.service;

import org.nfools.taskmanager.map.TaskMap;

import javax.annotation.Resource;

/**
 * @author Xuan Cao
 * @date 2022/6/10
 */
public abstract class AbstractService {

    @Resource
    protected TaskMap taskMapImpl;

}
