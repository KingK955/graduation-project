package com.sdjzu.graduation.project.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "user_token")
public class UserToken implements Serializable {
    /**
     * 所属用户id
     */
    @Id
    @Column(name = "user_id")
    private Long userId;

    /**
     * token
     */
    @Column(name = "token")
    private String token;

    /**
     * 过期时间
     */
    @Column(name = "expire_time")
    private Date expireTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}