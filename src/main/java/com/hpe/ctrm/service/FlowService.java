package com.hpe.ctrm.service;

import com.hpe.ctrm.entity.Flow;
import org.activiti.engine.runtime.ProcessInstance;

import java.util.List;


public interface FlowService {
    /**
     * 查询所有流程资源
     * @return
     */
    List<Flow> findAllFlow();
    /**
     * 根据id查询当前对应的流程资源
     */
    Flow findOneById(Integer id);
    /**
     * 根据id修改部署状态：改为已部署
     */
     int updateFlowState(Integer id);
    /**
     * 启动流程
     * @param formKey : evection
     * @param beanName :evectionService
     * @param bussinessKey :  evection:id
     * @param id :
     * @return
     */
    ProcessInstance startProcess(String formKey, String beanName, String bussinessKey, Integer id);
}
