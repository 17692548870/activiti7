package com.hpe.ctrm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    /**
     * 状态
     */
    private Integer status;
    /**
     * 消息
     */
    private String message;
}
