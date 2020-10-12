package com.sdjzu.graduation.project.service;

import com.sdjzu.graduation.project.common.utils.R;
import com.sdjzu.graduation.project.domain.UserToken;

public interface UserTokenService{

    UserToken queryByToken(String token);

    UserToken queryByUserId(Long userId);

    /**
     * 生成token
     *
     * @param userId 用户ID
     */
    R createToken(Long userId);

    void logout(long userId);
}
