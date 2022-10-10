package com.hpe.ctrm;

import com.hpe.ctrm.entity.Evection;
import com.hpe.ctrm.entity.User;
import com.hpe.ctrm.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {
    @Resource
    RepositoryService repositoryService;
    @Resource
    UserService userService;
    Evection evection = new Evection();
    @Test
    public void addEvection(){
        Optional<User> userById = userService.findUserById(1);
        System.out.println(userById.get().getUsername());
    }
    @Test
    public void deleteDepoly(){
        repositoryService.deleteDeployment("e8dd4a0f-9ccc-11eb-87c8-8612b37223e3",true);
    }


    @Test
    public void queryProcessInstance() {
// 流程定义key
        String processDefinitionKey = "myevection";
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
// 获取RunTimeService
        RuntimeService runtimeService = processEngine.getRuntimeService();
        List<ProcessInstance> list = runtimeService
                .createProcessInstanceQuery()
                .processDefinitionKey(processDefinitionKey)//
                .list();
        for (ProcessInstance processInstance : list) {
            System.out.println("----------------------------");
            System.out.println("流程实例id："
                    + processInstance.getProcessInstanceId());
            System.out.println("所属流程定义id："
                    + processInstance.getProcessDefinitionId());
            System.out.println("是否执行完成：" + processInstance.isEnded());
            System.out.println("是否暂停：" + processInstance.isSuspended());
            System.out.println("当前活动标识：" + processInstance.getActivityId());
        }
    }

    /**
     * 全部流程实例挂起与激活
     */
    @Test
    public void SuspendAllProcessInstance(){
// 获取processEngine
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
// 获取repositoryService
        RepositoryService repositoryService = processEngine.getRepositoryService();
// 查询流程定义的对象
        ProcessDefinition processDefinition =
                repositoryService.createProcessDefinitionQuery().
                        processDefinitionKey("evection").
                        singleResult();
// 得到当前流程定义的实例是否都为暂停状态
        boolean suspended = processDefinition.isSuspended();
// 流程定义id
        String processDefinitionId = processDefinition.getId();
// 判断是否为暂停
        if(suspended){
// 如果是暂停，可以执行激活操作 ,参数1 ：流程定义id ，参数2：是否激活，参数3：激活时间
            repositoryService.activateProcessDefinitionById(processDefinitionId,
                    true,
                    null
            );
            System.out.println("流程定义："+processDefinitionId+",已激活");
        }else {
// 如果是激活状态，可以暂停，参数1 ：流程定义id ，参数2：是否暂停，参数3：暂停时间
            repositoryService.suspendProcessDefinitionById(processDefinitionId,
                    true,
                    null);
            System.out.println("流程定义：" + processDefinitionId + ",已挂起");
        }
    }

    /**
     * 单个流程实例挂起与激活
     */
    @Test
    public void SuspendSingleProcessInstance(){
// 获取processEngine
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
// RuntimeService
        RuntimeService runtimeService = processEngine.getRuntimeService();
// 查询流程定义的对象
        ProcessInstance processInstance = runtimeService.
                createProcessInstanceQuery().
                processInstanceId("b65a82de-3be4-11ec-8fb3-005056c00001").
                singleResult();
// 得到当前流程定义的实例是否都为暂停状态
        boolean suspended = processInstance.isSuspended();
// 流程定义id
        String processDefinitionId = processInstance.getId();
// 判断是否为暂停
        if(suspended){
// 如果是暂停，可以执行激活操作 ,参数：流程定义id
            runtimeService.activateProcessInstanceById(processDefinitionId);
            System.out.println("流程定义："+processDefinitionId+",已激活");
        }else{
// 如果是激活状态，可以暂停，参数：流程定义id
            runtimeService.suspendProcessInstanceById( processDefinitionId);
            System.out.println("流程定义："+processDefinitionId+",已挂起");
        }
    }

    @Test
    public void completTask(){
// 获取引擎
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
// 获取操作任务的服务 TaskService
        TaskService taskService = processEngine.getTaskService();
// 完成任务,参数：流程实例id,完成zhangsan的任务
        Task task = taskService.createTaskQuery().processInstanceId("15001")
                .taskAssignee("rose")
                .singleResult();
        System.out.println("流程实例id="+task.getProcessInstanceId());
        System.out.println("任务Id="+task.getId());
        System.out.println("任务负责人="+task.getAssignee());
        System.out.println("任务名称="+task.getName());
        taskService.complete(task.getId());
    }


    /**
     * 设置流程负责人
     */
    @Test
    public void assigneeUEL(){
// 获取流程引擎
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
// 获取 RuntimeService
        RuntimeService runtimeService = processEngine.getRuntimeService();
// 设置assignee的取值，用户可以在界面上设置流程的执行
        Map<String,Object> assigneeMap = new HashMap<>();
        assigneeMap.put("assignee0","张三");
        assigneeMap.put("assignee1","李经理");
        assigneeMap.put("assignee2","王总经理");
        assigneeMap.put("assignee3","赵财务");
// 启动流程实例，同时还要设置流程定义的assignee的值
        runtimeService.startProcessInstanceByKey("evection",assigneeMap);
// 输出
        System.out.println(processEngine.getName());
    }


    @Test
    public void findProcessInstance(){
// 获取processEngine
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
// 获取TaskService
        TaskService taskService = processEngine.getTaskService();
// 获取RuntimeService
        RuntimeService runtimeService = processEngine.getRuntimeService();
// 查询流程定义的对象
        Task task = taskService.createTaskQuery()
                .processDefinitionKey("evection")
                .taskAssignee("张三")
                .singleResult();
// 使用task对象获取实例id
        String processInstanceId = task.getProcessInstanceId();
// 使用实例id，获取流程实例对象
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();
// 使用processInstance，得到 businessKey
        String businessKey = processInstance.getBusinessKey();
        System.out.println("businessKey=="+businessKey);
    }

    @Test
    public void CreateTable(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("processes/evection.bpmn")
                .addClasspathResource("processes/evection.png")
                .name("请假申请流程")
                .deploy();

        System.out.println("流程部署id-->"+deployment.getId());
        System.out.println("流程部署名字-->"+deployment.getName());
    }

    @Test
    public void testStartProcess(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        // 设置 assignee 的取值，
        Evection evection = new Evection();
        evection.setNum(9);
        Map<String,Object> map = new HashMap<>();
        map.put("assignee0","张三");
        map.put("assignee1","李四");
        map.put("assignee2","王五");
        map.put("assignee3","赵财务");
        map.put("evection",evection);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("evection",map);
        System.out.println("流程定义id："+processInstance.getProcessDefinitionId());
        System.out.println("流程实例id："+processInstance.getId());
        System.out.println("当前活动id："+processInstance.getActivityId());

    }

    @Test
    public void findTask(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        List<Task> list = taskService.createTaskQuery().processDefinitionKey("evection").taskAssignee("李四").list();

        list.stream().forEach(task->{
            String processDefinitionId = task.getProcessDefinitionId();
            System.out.println("流程实例id-->"+task.getProcessInstanceId());
            System.out.println("任务id-->"+task.getId());
            System.out.println("任务负责人-->"+task.getAssignee());
            System.out.println("任务名称-->"+task.getName());
            taskService.complete(task.getId());
        });

    }


}
