package com.sdjzu.graduation.project.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "`user`")
public class User implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 用户名
     */
    @Column(name = "username")
    private String username;

    /**
     * 密码
     */
    @Column(name = "`password`")
    private String password;

    /**
     * 头像
     */
    @Column(name = "avater")
    private String avater;

    /**
     * 邮箱
     */
    @Column(name = "email")
    private String email;

    /**
     * 电话
     */
    @Column(name = "mobile")
    private String mobile;

    /**
     * 账号状态
     */
    @Column(name = "`status`")
    private Integer status;

    /**
     * 昵称
     */
    @Column(name = "nickname")
    private String nickname;

    /**
     * 创建时间
     */
    @Column(name = "creat_time")
    private Date creatTime;

    /**
     * 盐
     */
    @Column(name = "salt")
    private String salt;

    private static final long serialVersionUID = 1L;
}