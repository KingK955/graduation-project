package com.sdjzu.graduation.project.controller;

import com.sdjzu.graduation.project.domain.User;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  抽象控制器，其他Controller继承该类直接能够获取登陆的用户对象
 */
public class AbstractController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 获取用户对象
     * @return User（用户）
     */
    protected User getUser() {
        return (User) SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * 获取用户id
     * @return 用户id
     */
    protected Long getUserId() {
        return getUser().getId();
    }
}
