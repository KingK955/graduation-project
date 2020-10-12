package com.sdjzu.graduation.project.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sdjzu.graduation.project.domain.Components;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.sdjzu.graduation.project.mapper.ComponentsMapper;
import com.sdjzu.graduation.project.service.ComponentsService;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import java.util.List;

@Service
public class ComponentsServiceImpl implements ComponentsService{

    @Resource
    private ComponentsMapper componentsMapper;

    /**
     *
     * @param id
     * @return
     */
    @Override
    public Components queryOneById(Long id) {

        Example example = new Example(Components.class);

        example.createCriteria().andEqualTo("id", id);

        return componentsMapper.selectOneByExample(example);
    }

    @Override
    public List<Components> queryAllByUserId(Long userId) {

        Example example = new Example(Components.class);

        example.createCriteria().andEqualTo("userId", userId);

        return componentsMapper.selectByExample(example);

    }

    @Override
    public PageInfo<Components> pageQueryAllByUserId(int pageNum, int pageSize, Long userId) {

        Example example = new Example(Components.class);

        example.createCriteria().andEqualTo("userId", userId);

        PageHelper.startPage(pageNum,pageSize);

        PageInfo<Components> pageInfo = new PageInfo<>(componentsMapper.selectByExample(example));

        return pageInfo;

    }

    @Override
    public boolean delete(Components component) {

        int delete = componentsMapper.delete(component);

        if (delete>0){
            return true;
        }else {
            return false;
        }

    }

    @Override
    public boolean update(Components component) {

        int update = componentsMapper.updateByPrimaryKey(component);

        if(update>0){
            return true;
        }else {
            return false;
        }

    }

    @Override
    public boolean add(Components component) {

        int add = componentsMapper.insert(component);

        if(add>0){
            return true;
        }else {
            return false;
        }

    }

    @Override
    public PageInfo<Components> search(int pageNum, int pageSize, String searchValue, String searchContext, Long userId) {

        PageHelper.startPage(pageNum,pageSize);

        Example example = Example.builder(Components.class)
                .select()
                .where(Sqls.custom().andLike(searchValue,searchContext))
                .andWhere(Sqls.custom().andEqualTo("userId", userId))
                .build();

        PageInfo<Components> pageInfo = new PageInfo<>(componentsMapper.selectByExample(example));

        return pageInfo;
    }

}
