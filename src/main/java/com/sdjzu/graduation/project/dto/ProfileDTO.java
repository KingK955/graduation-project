package com.sdjzu.graduation.project.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ProfileDTO implements Serializable {

    private Long id;
    /**
     * 用户名
     */
    private String username;

    /**
     * 头像
     */
    private String avater;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 昵称
     */
    private String nickname;



    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date creatTime;

    private static final long serialVersionUID = -7424707846497864323L;
}
