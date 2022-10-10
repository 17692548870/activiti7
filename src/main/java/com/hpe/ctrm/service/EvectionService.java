package com.hpe.ctrm.service;


import com.hpe.ctrm.entity.Evection;

import javax.servlet.http.HttpServletRequest;

/**
 * 出差单信息表
 */
public interface EvectionService {
    /**
     * 新建出差申请
     */
   Evection save(HttpServletRequest request, Evection evection);
}
