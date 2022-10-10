package com.hpe.ctrm.listener;

import com.hpe.ctrm.service.FlowService;
import com.hpe.ctrm.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MyTaskListener implements TaskListener {
    private static final long serialVersionUID = -2900631168476197899L;

    @Override
    public void notify(DelegateTask delegateTask) {
        log.info("eventName=={}",delegateTask.getEventName());
        if(delegateTask.getEventName().equals("assignment")){
            FlowService flowService = (FlowService) SpringContextUtil.getBean("flowService");
            log.info("flowService={}",flowService);
           // flowService.createTaskEvent(delegateTask);
        }
    }
}
