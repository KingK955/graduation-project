package com.sdjzu.graduation.project.service;

import com.github.pagehelper.PageInfo;
import com.sdjzu.graduation.project.domain.Warehouse;

import java.util.List;

public interface WarehouseService{

    Warehouse queryOneById(Long id);

    List<Warehouse> queryAllByUserId(Long userId);

    PageInfo<Warehouse> pageQueryAllByUserId(int pageNum, int pageSize, Long userId);

    boolean update(Warehouse warehouse);

    boolean add(Warehouse warehouse);

    boolean delete(Warehouse warehouse);

    PageInfo<Warehouse> search(int pageNum, int pageSize,String searchValue,String searchContext,Long userId);

}
