package com.sdjzu.graduation.project.service;

import com.github.pagehelper.PageInfo;
import com.sdjzu.graduation.project.domain.WarehouseDetail;

import java.util.List;

public interface WarehouseDetailService {


    PageInfo<WarehouseDetail> pageQueryAll(int pageNum, int pageSize, Long projectId);

    List<WarehouseDetail> search(int pageNum, int pageSize, Long projectId, String searchValue);

    boolean insert(WarehouseDetail warehouseDetail);
}


