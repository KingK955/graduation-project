package com.sdjzu.graduation.project.service.impl;

import com.sdjzu.graduation.project.common.utils.R;
import com.sdjzu.graduation.project.domain.UserToken;
import com.sdjzu.graduation.project.shiro.TokenGenerator;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.sdjzu.graduation.project.mapper.UserTokenMapper;
import com.sdjzu.graduation.project.service.UserTokenService;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

@Service
public class UserTokenServiceImpl implements UserTokenService{

    //12小时后过期
    private final static int EXPIRE = 3600 * 12;


    @Resource
    private UserTokenMapper userTokenMapper;

    @Override
    public UserToken queryByToken(String token) {
        Example example = new Example(UserToken.class);

        example.createCriteria().andEqualTo("token", token);

        return userTokenMapper.selectOneByExample(example);
    }

    @Override
    public UserToken queryByUserId(Long userId) {
        Example example = new Example(UserToken.class);
        example.createCriteria().andEqualTo("userId", userId);
        return userTokenMapper.selectOneByExample(example);
    }

    @Override
    public R createToken(Long userId) {
        //生成一个token
        String token = TokenGenerator.generateValue();

        //当前时间
        Date now = new Date();
        //过期时间
        Date expireTime = new Date(now.getTime() + EXPIRE * 1000);

        //判断是否生成过token
        UserToken tokenEntity = this.queryByUserId(userId);
        if (tokenEntity == null) {
            tokenEntity = new UserToken();
            tokenEntity.setUserId(userId);
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);

            //保存token
            userTokenMapper.insert(tokenEntity);
        } else {
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);

            //更新token
            userTokenMapper.updateByPrimaryKey(tokenEntity);
        }

        R r = R.ok().put("token", token).put("expire", EXPIRE);

        return r;
    }

    @Override
    public void logout(long userId) {
        //生成一个token
        String token = TokenGenerator.generateValue();

        //修改token
        UserToken userToken = new UserToken();
        userToken.setUserId(userId);
        userToken.setToken(token);
        userTokenMapper.updateByPrimaryKey(userToken);
    }

}
