package com.hpe.ctrm.entity;

import lombok.Data;

import javax.persistence.*;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 用户表实体类
 */
@Entity
@Data
@Table(name = "tb_user")
public class User implements Serializable {
    private static final long serialVersionUID = 2573602669556746881L;
    /**
     * 用户id
     */
    @Id
    @GeneratedValue
    private Integer id;
    /**
     * 用户名
     */
    @Column
    private String username;
    /**
     * 密码
     */
    @Column
    private String password;
    /**
     * email电子邮件
     */
    @Column
    private String email;
    /**
     * 性别 : 1男，2女
     */
    @Column
    private Integer gender;
    /**
     * role角色
     */
    @Column
    private String role;
    /**
     * 角色组
     */
    @Column(name = "role_group")
    private String roleGroup;
}
