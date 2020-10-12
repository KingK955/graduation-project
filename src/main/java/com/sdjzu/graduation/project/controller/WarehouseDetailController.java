package com.sdjzu.graduation.project.controller;


import com.github.pagehelper.PageInfo;
import com.sdjzu.graduation.project.common.utils.R;
import com.sdjzu.graduation.project.domain.WarehouseDetail;
import com.sdjzu.graduation.project.service.WarehouseDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 有关仓库细节操作的Controller
 */
@RestController()
@RequestMapping(value = "warehouse/detail")
public class WarehouseDetailController {
    /**
     * 自动注入仓库细节服务
     */
    @Autowired
    private WarehouseDetailService warehouseDetailService;

    /**
     * 分页查询所有仓库细节
     * @param request
     * @return
     */
    @GetMapping("/fetch/list/")
    public R getAllComponentPage(HttpServletRequest request) {

        /* 从请求获取页数 */
        Integer pageNum = Integer.parseInt(request.getParameter("page"));

        /* 从请求中获取数量 */
        Integer pageSize = Integer.parseInt(request.getParameter("limit"));

        /* 从请求获取仓库Id*/
        Long warehouseId = Long.parseLong(request.getParameter("warehouseId"));

        /* 从请求获取查询内容 */
        String searchContext = request.getParameter("serchContext");

        /*分页信息*/
        PageInfo pageInfo = null;

        /*分页数据与分页查询数据*/
        if (searchContext == null) {

            /*正常获取分页数据*/
            pageInfo = warehouseDetailService.pageQueryAll(pageNum, pageSize, warehouseId);

        } else {

            List<WarehouseDetail> warehouseDetails = warehouseDetailService.search(pageNum,pageSize,warehouseId,"%"+searchContext+"%");

            Map<String,Object> map = new HashMap<>();

            map.put("total",warehouseDetails.size());

            map.put("list",warehouseDetails);

            return R.ok().put("data",map);
        }

        /*如果分页数据不为空正常返回前台数据*/
        return  (pageInfo == null) ? R.error() : R.ok().put("data", pageInfo);
    }
}
