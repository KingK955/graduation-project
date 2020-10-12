package com.sdjzu.graduation.project.service;

import com.sdjzu.graduation.project.domain.User;

public interface UserService{

    User queryUser(Long id);

    User queryByUserName(String username);

    int update(User user);

}
