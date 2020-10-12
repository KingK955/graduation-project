package com.sdjzu.graduation.project.service;

import com.github.pagehelper.PageInfo;
import com.sdjzu.graduation.project.domain.Components;

import java.util.List;

public interface ComponentsService{

    Components queryOneById(Long id);

    List<Components> queryAllByUserId(Long userId);

    PageInfo<Components> pageQueryAllByUserId(int pageNum, int pageSize, Long userId);

    boolean delete(Components component);

    boolean update(Components component);

    boolean add(Components component);

    PageInfo<Components> search(int pageNum, int pageSize,String searchValue,String searchContext,Long userId);
}
