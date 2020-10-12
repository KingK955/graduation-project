package com.sdjzu.graduation.project.service;

import com.sdjzu.graduation.project.domain.PlatToken;

public interface PlatTokenService{

    PlatToken queryByUserId(Long id);

    boolean isToken(Long userId);

    boolean add(PlatToken platToken);

    boolean update(PlatToken platToken);

}
