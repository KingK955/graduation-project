package com.sdjzu.graduation.project.service.impl;

import com.sdjzu.graduation.project.domain.User;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.sdjzu.graduation.project.mapper.UserMapper;
import com.sdjzu.graduation.project.service.UserService;
import tk.mybatis.mapper.entity.Example;

@Service
public class UserServiceImpl implements UserService{

    @Resource
    private UserMapper userMapper;

    @Override
    public User queryUser(Long id) {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("id", id);
        return userMapper.selectOneByExample(example);
    }

    @Override
    public User queryByUserName(String username) {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("username", username);
        return userMapper.selectOneByExample(example);
    }

    @Override
    public int update(User user) {
        return userMapper.updateByPrimaryKey(user);
    }

}
