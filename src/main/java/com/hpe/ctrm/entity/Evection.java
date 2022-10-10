package com.hpe.ctrm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 请假单实体
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_evection")
public class Evection implements Serializable {
    private static final long serialVersionUID = -6456704930049302117L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Integer userId;
    /**
     * 请假名称
     */
    @Column(name = "evection_name")
    private String evectionName;
    /**
     * 请假天数
     */
    @Column
    private Integer num;
    /**
     * 开始时间
     */
    @Column(name = "begin_date")
    private Date beginDate;
    /**
     * 结束时间
     */
    @Column(name = "end_date")
    private Date endDate;
    /**
     * 请假目的地
     */
    @Column
    private String destination;
    /**
     * 请假事由
     */
    @Column
    private String reson;
    /**
     * 状态
     */
    @Column
    private Integer state;
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
