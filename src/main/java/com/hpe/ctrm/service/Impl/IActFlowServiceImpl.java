package com.hpe.ctrm.service.Impl;

import com.hpe.ctrm.entity.Evection;
import com.hpe.ctrm.repository.EvectionRepository;
import com.hpe.ctrm.service.IActFlowService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service("iActFlowService")
public class IActFlowServiceImpl implements IActFlowService {
    @Resource
    EvectionRepository evectionRepository;

    /**
     * 设置出差申请的 流程变量
     *
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> setvariables(Integer id) {
        Evection evection = evectionRepository.getOne(id);
        //设置流程变量
        Map<String, Object> variables = new HashMap<>();
        variables.put("assignee0", 1);
        variables.put("assignee1", 2);
        variables.put("assignee2", 3);
        variables.put("assignee3", 4);
        variables.put("evection", evection);
        return variables;
    }
    /**
     * 设置开始任务时的状态
     * @param id
     */
    @Override
    public void startRunTask(Integer id) {
        evectionRepository.startTask(id);
    }
    /**
     * 设置结束任务时的状态
     * @param id
     */
    @Override
    public void endRunTask(Integer id) {
        evectionRepository.endTask(id);
    }
}
