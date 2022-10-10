package com.hpe.ctrm.controller;

import com.hpe.ctrm.entity.Evection;
import com.hpe.ctrm.service.EvectionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RequestMapping("/evection")
@RestController
public class EvectionController {
    @Resource
    EvectionService evectionService;
    /**
     * 创建出差申请
     * @param request
     * @param evection
     */
    @PostMapping("/add")
    public void addEvection(HttpServletRequest request, @RequestBody Evection evection){
        evectionService.save(request, evection);
    }
}
