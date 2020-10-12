package com.sdjzu.graduation.project.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sdjzu.graduation.project.domain.WarehouseDetail;
import com.sdjzu.graduation.project.mapper.ProducePlanMapper;
import com.sdjzu.graduation.project.service.ProducePlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.sdjzu.graduation.project.mapper.WarehouseDetailMapper;
import com.sdjzu.graduation.project.service.WarehouseDetailService;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class WarehouseDetailServiceImpl implements WarehouseDetailService {

    @Resource
    private WarehouseDetailMapper warehouseDetailMapper;

    @Resource
    private ProducePlanService producePlanService;


    @Override
    public PageInfo<WarehouseDetail> pageQueryAll(int pageNum, int pageSize, Long warehouseId) {

        Example example = new Example(WarehouseDetail.class);

        example.createCriteria().andEqualTo("warehouseId", warehouseId);

        PageHelper.startPage(pageNum, pageSize);

        PageInfo<WarehouseDetail> pageInfo = new PageInfo<>(warehouseDetailMapper.selectByExample(example));


        return pageInfo;
    }

    @Override
    public List<WarehouseDetail> search(int pageNum, int pageSize, Long projectId, String searchValue) {

        List<WarehouseDetail> warehouseDetails = warehouseDetailMapper.search(projectId, searchValue);


        return warehouseDetails;
    }

    @Override
    public boolean insert(WarehouseDetail warehouseDetail) {

        return warehouseDetailMapper.insert(warehouseDetail) > 0 ? true : false;

    }

}


