package com.hpe.ctrm.service.Impl;

import com.hpe.ctrm.entity.Flow;
import com.hpe.ctrm.repository.FlowRepository;
import com.hpe.ctrm.service.FlowService;
import com.hpe.ctrm.service.IActFlowService;
import com.hpe.ctrm.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Slf4j
@Service("flowService")
public class FlowServiceImpl implements FlowService {
    @Resource
    FlowRepository flowRepository;
    @Resource
    RuntimeService runtimeService;

    /**
     * @return 所有流程资源
     */
    @Override
    public List<Flow> findAllFlow() {
    return flowRepository.findAllByCreateTime();
    }

    /**
     *  据id查询当前对应的流程资源
     * @param id Flow:id
     * @return Flow
     */
    @Override
    public Flow findOneById(Integer id) {
        return flowRepository.findOneById(id);
    }

    /**
     * 根据id修改部署状态：改为已部署
     * @param id 资源id
     * @return 资源状态
     */
    @Override
    public int updateFlowState(Integer id) {
        return flowRepository.updateFlowStateById(id);
    }
    /**
     * 启动流程实例
     */
    @Override
    public ProcessInstance startProcess(String formKey, String beanName, String bussinessKey, Integer id) {
        IActFlowService customService = (IActFlowService) SpringContextUtil.getBean(beanName);
//		修改业务的状态
        customService.startRunTask(id);
        //设置流程变量
        Map<String, Object> variables = customService.setvariables(id);
        variables.put("bussinessKey", bussinessKey);
//		启动流程
        log.info("【启动流程】，formKey ：{},bussinessKey:{}", formKey, bussinessKey);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(formKey, bussinessKey, variables);
//		流程实例ID
        String processDefinitionId = processInstance.getProcessDefinitionId();
        log.info("【启动流程】- 成功，processDefinitionId：{}", processDefinitionId);
        return processInstance;
    }


}
