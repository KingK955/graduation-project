package com.sdjzu.graduation.project.mapper;

import com.sdjzu.graduation.project.domain.WarehouseDetail;
import org.apache.ibatis.annotations.Param;import tk.mybatis.mapper.MyMapper;import java.util.List;

public interface WarehouseDetailMapper extends MyMapper<WarehouseDetail> {
    List<WarehouseDetail> search(@Param("warehouseId") Long warehouseId, @Param("searchValue") String searchValue);
}