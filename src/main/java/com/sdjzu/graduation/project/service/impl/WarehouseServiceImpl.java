package com.sdjzu.graduation.project.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sdjzu.graduation.project.domain.Warehouse;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.sdjzu.graduation.project.mapper.WarehouseMapper;
import com.sdjzu.graduation.project.service.WarehouseService;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import java.util.List;

@Service
public class WarehouseServiceImpl implements WarehouseService{

    @Resource
    private WarehouseMapper warehouseMapper;

    @Override
    public Warehouse queryOneById(Long id) {
        Example example = new Example(Warehouse.class);

        example.createCriteria().andEqualTo("id", id);

        return warehouseMapper.selectOneByExample(example);
    }

    @Override
    public List<Warehouse> queryAllByUserId(Long userId) {
        Example example = new Example(Warehouse.class);

        example.createCriteria().andEqualTo("userId", userId);

        return warehouseMapper.selectByExample(example);
    }

    @Override
    public PageInfo<Warehouse> pageQueryAllByUserId(int pageNum, int pageSize, Long userId) {

        Example example = new Example(Warehouse.class);

        example.createCriteria().andEqualTo("userId", userId);

        PageHelper.startPage(pageNum,pageSize);

        PageInfo<Warehouse> pageInfo = new PageInfo<>(warehouseMapper.selectByExample(example));

        return pageInfo;
    }

    @Override
    public boolean update(Warehouse warehouse) {

        int update = warehouseMapper.updateByPrimaryKey(warehouse);

        if(update>0){
            return true;
        }else {
            return false;
        }

    }

    @Override
    public boolean add(Warehouse warehouse) {

        int add = warehouseMapper.insert(warehouse);

        if(add>0){
            return true;
        }else {
            return false;
        }

    }

    @Override
    public boolean delete(Warehouse warehouse) {

        int delete = warehouseMapper.delete(warehouse);

        if (delete>0){
            return true;
        }else {
            return false;
        }

    }

    @Override
    public PageInfo<Warehouse> search(int pageNum, int pageSize, String searchValue, String searchContext, Long userId) {

        PageHelper.startPage(pageNum,pageSize);

        Example example = Example.builder(Warehouse.class)
                .select()
                .where(Sqls.custom().andLike(searchValue,searchContext))
                .andWhere(Sqls.custom().andEqualTo("userId", userId))
                .build();

        PageInfo<Warehouse> pageInfo = new PageInfo<>(warehouseMapper.selectByExample(example));

        return pageInfo;
    }

}
