package com.hpe.ctrm.service.Impl;

import com.hpe.ctrm.entity.Evection;
import com.hpe.ctrm.repository.EvectionRepository;
import com.hpe.ctrm.service.ActService;
import com.hpe.ctrm.service.EvectionService;
import com.hpe.ctrm.service.FlowService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Slf4j
@Service("evectionService")
public class EvectionServiceImpl implements EvectionService {
    @Resource
    EvectionRepository evectionRepository;
    @Resource
    FlowService flowService;
    @Resource
    ActService actService;

    /**
     * 创建出差申请:创建人应该为第一负责人，创建好出差单及等于（启动流程,完成个人任务）
     *
     * @param request
     * @param evection
     * @return
     */
    @Override
    public Evection save(HttpServletRequest request, Evection evection) {
      //从attribute里面获取userId
        Integer userId = (Integer) request.getSession().getAttribute("userId");
//       设置创建此任务单的userId
        evection.setUserId(userId);
//        设置任务单态
        evection.setState(0);
//      保存请假单到数据库
        Evection save = evectionRepository.save(evection);
        //得到请假单id
        Integer id = evection.getId();
        //设置流程key
        String formKey = "evection";
        //设置对象名
        String beanName = "iActFlowService";
        //使用流程变量设置字符串（格式 ： Evection:Id 的形式）
        //使用正在执行对象表中的一个字段BUSINESS_KEY(Activiti提供的一个字段)，让启动的流程（流程实例）关联业务
        String bussinessKey = formKey + ":" + id;
//        启动流程
        ProcessInstance processInstance = flowService.startProcess(formKey, beanName, bussinessKey, id);
        //		流程实例ID
        String processDefinitionId = processInstance.getProcessDefinitionId();
        log.info("processDefinitionId is {}", processDefinitionId);
//       个人任务列表
        List<Map<String, Object>> taskList = actService.myTaskList(userId.toString());
        if (!CollectionUtils.isEmpty(taskList)) {
            for (Map<String, Object> map : taskList) {
                if (map.get("assignee").toString().equals(userId.toString()) &&
                        map.get("processDefinitionId").toString().equals(processDefinitionId)) {
                    log.info("processDefinitionId is {}", map.get("processDefinitionId").toString());
                    log.info("taskid is {}", map.get("taskid").toString());
                     actService.completeProcess("同意", map.get("taskid").toString(), userId.toString());
                }

            }

        }
        return save;
    }
}
