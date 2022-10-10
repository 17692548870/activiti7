package com.hpe.ctrm.listener;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MyExecutionListener implements ExecutionListener {

    private static final long serialVersionUID = -3687396029807598604L;

    @Override
    public void notify(DelegateExecution delegateExecution) {
        log.info("delegateExecution is {}",delegateExecution);
    }
}
