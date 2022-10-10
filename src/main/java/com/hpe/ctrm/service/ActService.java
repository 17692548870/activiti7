package com.hpe.ctrm.service;

import com.hpe.ctrm.entity.Flow;

import java.util.List;
import java.util.Map;

/**
 * 对activiti表的操作
 */
public interface ActService {
//    部署流程
    void saveDeploy(Flow flow);
    /**
     * 查看个人任务列表
     */
     List<Map<String, Object>> myTaskList(String userId);
    /**
     * 完成提交任务
     */
    void completeProcess(String remark, String taskId, String userId);
}
