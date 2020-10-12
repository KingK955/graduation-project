package com.sdjzu.graduation.project.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sdjzu.graduation.project.domain.ProducePlan;
import com.sdjzu.graduation.project.service.ComponentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.sdjzu.graduation.project.mapper.ProducePlanMapper;
import com.sdjzu.graduation.project.service.ProducePlanService;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class ProducePlanServiceImpl implements ProducePlanService {

    @Resource
    private ProducePlanMapper producePlanMapper;

    @Autowired
    private ComponentsService componentsService;

    @Override
    public ProducePlan queryOneById(Long id) {

        Example example = new Example(ProducePlan.class);

        example.createCriteria().andEqualTo("id", id);

        ProducePlan producePlan = producePlanMapper.selectOneByExample(example);

        producePlan.setComponent(componentsService.queryOneById(producePlan.getComponentId()));

        return producePlan;
    }

    @Override
    public PageInfo<ProducePlan> pageQueryAll(int pageNum, int pageSize, Long userId) {

        Example example = new Example(ProducePlan.class);

        example.createCriteria().andEqualTo("userId", userId);

        PageHelper.startPage(pageNum, pageSize);

        PageInfo<ProducePlan> pageInfo = new PageInfo<>(producePlanMapper.selectByExample(example));

        pageInfo.getList().forEach(producePlan -> {

            producePlan.setComponent(componentsService.queryOneById(producePlan.getComponentId()));

        });

        return pageInfo;
    }

    @Override
    public boolean update(ProducePlan producePlan) {

        if (producePlanMapper.updateByPrimaryKey(producePlan) > 0) {

            return true;

        } else {

            return false;

        }
    }

    @Override
    public boolean add(ProducePlan producePlan) {

        if (producePlanMapper.insert(producePlan) > 0) {

            return true;

        } else {

            return false;

        }
    }

    @Override
    public boolean delete(ProducePlan producePlan) {

        if (producePlanMapper.delete(producePlan) > 0) {

            return true;

        } else {

            return false;

        }
    }
}



