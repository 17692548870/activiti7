package com.hpe.ctrm.service.Impl;

import com.hpe.ctrm.entity.Flow;
import com.hpe.ctrm.entity.User;
import com.hpe.ctrm.service.ActService;
import com.hpe.ctrm.service.EvectionService;
import com.hpe.ctrm.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

@Slf4j
@Service
public class ActServiceImpl implements ActService {
    //    流程资料服务
    @Resource
    RepositoryService repositoryService;
    //   流程运行时服务
    @Resource
    RuntimeService runtimeService;
    //    任务服务
    @Resource
    TaskService taskService;
    //    历史服务
    @Resource
    HistoryService historyService;
    @Resource
    private UserService userService;
    @Resource
    EvectionService evectionService;

    /**
     * 部署流程
     */
    @Override
    public void saveDeploy(Flow flow) {
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource(flow.getFilePath())//根据资源文件部署
                .name(flow.getFlowName())//部署名字
                .deploy();//进行部署
//输出部署的信息
        Map<String, Object> map = new HashMap<>();
        map.put("部署id:", deploy.getId());
        map.put("部署名称:", deploy.getName());
        map.put("部署时间:", deploy.getDeploymentTime());
        map.put("部署类别:", deploy.getCategory());
        map.put("部署key:", deploy.getKey());
        map.put("部署TenantId:", deploy.getTenantId());
        log.info("部署信息：{}", map);

    }

    /**
     * 查看个人任务列表
     */
    public List<Map<String, Object>> myTaskList(String userId) {

        /**
         * 根据负责人id(此id与act表关联)  查询任务
         */
        TaskQuery taskQuery = taskService.createTaskQuery().taskAssignee(userId);
        List<Task> list = taskQuery.orderByTaskCreateTime().desc().list();
        List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
        for (Task task : list) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("taskid", task.getId());
            map.put("taskname", task.getName());
            map.put("description", task.getDescription());
            map.put("priority", task.getPriority());
            map.put("owner", task.getOwner());
            map.put("assignee", task.getAssignee());
            map.put("delegationState", task.getDelegationState());
            map.put("processInstanceId", task.getProcessInstanceId());
            map.put("executionId", task.getExecutionId());
            map.put("processDefinitionId", task.getProcessDefinitionId());
            map.put("createTime", task.getCreateTime());
            map.put("taskDefinitionKey", task.getTaskDefinitionKey());
            map.put("dueDate", task.getDueDate());
            map.put("category", task.getCategory());
            map.put("parentTaskId", task.getParentTaskId());
            map.put("tenantId", task.getTenantId());

            Optional<User> userInfo = userService.findUserById(Integer.valueOf(task.getAssignee()));
            map.put("assigneeUser", userInfo.get().getUsername());
            listMap.add(map);
        }
        return listMap;
    }

    /**
     * 查看个人任务信息
     */
    public List<Map<String, Object>> myTaskInfoList(String userid) {

        /**
         * 根据负责人id  查询任务
         */
        TaskQuery taskQuery = taskService.createTaskQuery().taskAssignee(userid);

        List<Task> list = taskQuery.orderByTaskCreateTime().desc().list();

        List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
        for (Task task : list) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("taskid", task.getId());
            map.put("assignee", task.getAssignee());
            map.put("processInstanceId", task.getProcessInstanceId());
            map.put("executionId", task.getExecutionId());
            map.put("processDefinitionId", task.getProcessDefinitionId());
            map.put("createTime", task.getCreateTime());
            ProcessInstance processInstance = runtimeService
                    .createProcessInstanceQuery()
                    .processInstanceId(task.getProcessInstanceId())
                    .singleResult();
            if (processInstance != null) {
                String businessKey = processInstance.getBusinessKey();
                if (!StringUtils.isBlank(businessKey)) {
                    String type = businessKey.split(":")[0];
                    String id = businessKey.split(":")[1];
                    if (type.equals("evection")) {
                      /*  Evection evection = evectionService.(Long.valueOf(id));
                        User userInfo = userService.findOneUserById(evection.getUserid());
                        map.put("flowUserName", userInfo.getUsername());
                        map.put("flowType", "出差申请");
                        map.put("flowcontent", "出差" + evection.getNum() + "天");*/
                    }
                }
            }
            listmap.add(map);
        }

        return listmap;
    }


    /**
     * 完成提交任务
     */
    public void completeProcess(String remark, String taskId, String userId) {
        //任务Id 查询任务对象
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

        if (task == null) {
            log.error("completeProcess - task is null!!");
            return;
        }
        //任务对象  获取流程实例Id
        String processInstanceId = task.getProcessInstanceId();

        //设置审批人的userId
        Authentication.setAuthenticatedUserId(userId);

        //添加记录
        taskService.addComment(taskId, processInstanceId, remark);
        System.out.println("-----------完成任务操作 开始----------");
        System.out.println("任务Id=" + taskId);
        System.out.println("负责人id=" + userId);
        System.out.println("流程实例id=" + processInstanceId);
        //完成办理
        taskService.complete(taskId);
        System.out.println("当前负责人:"+userId+"-----完成任务了操作 结束----------");
    }

    /**
     * 查询历史记录
     * @param businessKey 业务id
     */
    public void searchHistory(String businessKey) {
        List<HistoricProcessInstance> list1 = historyService.createHistoricProcessInstanceQuery().processInstanceBusinessKey(businessKey).list();
        if (CollectionUtils.isEmpty(list1)) {
            return;
        }
        String processDefinitionId = list1.get(0).getProcessDefinitionId();
        // 历史相关Service
        List<HistoricActivityInstance> list = historyService
                .createHistoricActivityInstanceQuery()
                .processDefinitionId(processDefinitionId)
                .orderByHistoricActivityInstanceStartTime()
                .asc()
                .list();
        for (HistoricActivityInstance hiact : list) {
            if (StringUtils.isBlank(hiact.getAssignee())) {
                continue;
            }
            System.out.println("活动ID:" + hiact.getId());
            System.out.println("流程实例ID:" + hiact.getProcessInstanceId());
            Optional<User> user = userService.findUserById(Integer.valueOf(hiact.getAssignee()));
            System.out.println("办理人ID：" + hiact.getAssignee());
            System.out.println("办理人名字：" + user.get().getUsername());
            System.out.println("开始时间：" + hiact.getStartTime());
            System.out.println("结束时间：" + hiact.getEndTime());
            System.out.println("==================================================================");
        }
    }

}
