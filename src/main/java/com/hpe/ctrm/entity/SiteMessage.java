package com.hpe.ctrm.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 站内消息（用于通知）
 */
@Entity
@Data
@Table(name = "tb_sitemessage")
public class SiteMessage implements Serializable {
    private static final long serialVersionUID = 6764880352135002917L;
    @Id
    @GeneratedValue
    private Integer id;
    /**
     * 任务id
     */
    @Column(name = "task_id")
    private String taskId;
    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Integer userId;
    /**
     * 类型
     */
    @Column
    private Integer type;
    /**
     *
     */
    @Column
    private String content;
    /**
     * 是否读取
     */
    @Column(name = "is_read")
    private Integer isRead;
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private Date updateTime;

}
