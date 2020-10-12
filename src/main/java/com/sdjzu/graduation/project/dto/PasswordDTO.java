package com.sdjzu.graduation.project.dto;

import lombok.Data;

/**
 * 密码表单
 *
 * @author Mark sunlightcs@gmail.com
 */
@Data
public class PasswordDTO {
    /**
     * 原密码
     */
    private String oldPassword;
    /**
     * 新密码
     */
    private String newPassword;

}