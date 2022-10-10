package com.hpe.ctrm.service;

import java.util.Map;

/**
 * 流程业务service 的接口 必须实现接口 实现其方法
 */
public interface IActFlowService {
    /**
     * 设置流程变量
     * @param id
     * @return
     */
    Map<String, Object> setvariables(Integer id);
    /**
     * 整个流程开始时需要执行的任务
     * @param id
     */
    void startRunTask(Integer id);
    /**
     * 整个流程结束需要执行的任务
     * @param id
     */
    void endRunTask(Integer id);
}
