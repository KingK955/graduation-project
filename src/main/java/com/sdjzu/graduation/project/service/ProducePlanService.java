package com.sdjzu.graduation.project.service;

import com.github.pagehelper.PageInfo;
import com.sdjzu.graduation.project.domain.ProducePlan;

public interface ProducePlanService {


    ProducePlan queryOneById(Long id);

    PageInfo<ProducePlan> pageQueryAll(int pageNum, int pageSize, Long userId);

    boolean update(ProducePlan producePlan);

    boolean add(ProducePlan producePlan);

    boolean delete(ProducePlan producePlan);

}



