package com.sdjzu.graduation.project.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * login user information
 */
@Data
public class LoginInfoDTO implements Serializable {
    private String name;
    private String avatar;
    private String nickName;
}