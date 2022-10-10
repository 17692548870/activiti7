package com.hpe.ctrm.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 资源流
 */
@Entity
@Data
@Table(name = "tb_flow")
public class Flow implements Serializable {
    private static final long serialVersionUID = 127705857815200377L;
    @Id
    @GeneratedValue
    private Integer id;
    /**
     * 部署名
     */
    @Column(name = "flow_name")
    private String flowName;
    /**
     * 部署key
     */
    @Column(name = "flow_key")
    private String flowKey;
    /**
     * 文件地址
     */
    @Column(name = "file_path")
    private String filePath;
    /**
     * 状态
     */
    @Column
    private Integer state;
    /**
     * 部署时间
     */
    @Column(name = "create_time")
    private Date createTime;
}
