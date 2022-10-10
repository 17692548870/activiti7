package com.hpe.ctrm.controller;

import com.hpe.ctrm.entity.Flow;
import com.hpe.ctrm.service.ActService;
import com.hpe.ctrm.service.FlowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/flow")
@Slf4j
public class FlowController {
  @Resource
    FlowService flowService;
  @Resource
    ActService actService;
    /**
     * 查询所有流程（已部署或未部署的）
     */
    @GetMapping("/findAll")
    public List<Flow> findAllFlow(){
      log.info("所有流程:{}", flowService.findAllFlow());
        return flowService.findAllFlow();
    }

  /**
   * 部署流程
   * @param request id：flow资源表id
   * @return 0-部署失败  1- 部署成功  2- 已经部署过
   */
  @PutMapping("/deployment/{id}")
  public Integer deployment(HttpServletRequest request,
                            @PathVariable(name = "id")Integer id){
//    当前流程资源
      Flow oneFlow = flowService.findOneById(id);
//      如果当前状态为0：已部署
    if(oneFlow.getState() == 0){
//      返回状态值2：部署失败
      return 2;
   }
     //进行部署：部署当前流程
   actService.saveDeploy(oneFlow);
    //返回部署成功返回Flow的状态值
    return flowService.updateFlowState(id);
  }



}
