package com.sdjzu.graduation.project.controller;

import com.github.pagehelper.PageInfo;
import com.sdjzu.graduation.project.common.utils.R;
import com.sdjzu.graduation.project.domain.ProducePlan;
import com.sdjzu.graduation.project.domain.Warehouse;
import com.sdjzu.graduation.project.domain.WarehouseDetail;
import com.sdjzu.graduation.project.dto.FinishDTO;
import com.sdjzu.graduation.project.service.ProducePlanService;
import com.sdjzu.graduation.project.service.WarehouseDetailService;
import com.sdjzu.graduation.project.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 *  有关生产计划的Controller
 */
@RestController()
@RequestMapping(value = "plan")
public class ProducePlanController extends AbstractController {
    /**
     * 自动注入生产计划服务
     */
    @Autowired
    private ProducePlanService producePlanService;
    /**
     * 自动注入仓库服务
     */
    @Autowired
    private WarehouseService warehouseService;
    /**
     * 自动注入仓库细节服务
     */
    @Autowired
    private WarehouseDetailService warehouseDetailService;

    /**
     * 分页查询用户构件
     * @param request
     * @return
     */
    @GetMapping("/fetch/list")
    public R fetchList(HttpServletRequest request){

        /* 从请求获取页数 */
        Integer pageNum = Integer.parseInt(request.getParameter("page"));

        /* 从请求中获取数量 */
        Integer pageSize = Integer.parseInt(request.getParameter("limit"));

        /*从请求获取查询类别*/
        String searchValue = request.getParameter("searchValue");

        /*从请求获取查询内容*/
        String searchContext = "%"+request.getParameter("serchContext")+"%";

        /*分页信息*/
        PageInfo pageInfo = null;

        /*分页数据与分页查询数据*/
        if(searchValue==null){

            /*正常获取分页数据*/
            pageInfo = producePlanService.pageQueryAll(pageNum,pageSize,getUserId());

        }else{

        }
        /*如果分页数据不为空正常返回前台数据*/
        return (pageInfo==null)?R.error(): R.ok().put("data",pageInfo);

    }

    /**
     * 删除生产计划
     * @param producePlan
     * @return
     */
    @PostMapping("/delete")
    public R delete(@RequestBody ProducePlan producePlan){
        try{
            if (producePlanService.delete(producePlan)){

                return R.ok();

            }else {

                return R.error(500,"删除失败");

            }

        }catch (Exception e){

            return R.error().put("msg","此生产计划存在于仓库明细中无法删除");

        }
    }

    /**
     * 更新/添加生产计划
     * @param producePlan
     * @return
     */
    @PostMapping("/update")
    public R update(@RequestBody ProducePlan producePlan){

        /*如果id为空，增加一个生产计划，否则更新生产计划*/
        if(producePlan.getId()!=null){

            /*更新生产计划*/
            if(producePlanService.update(producePlan)){

                return R.ok();

            }else {

                return R.error(500,"更新失败");

            }

        }else{

            SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");

            String str=sdf.format(new Date());

            /*设置生产计划编号*/
            producePlan.setProduceNo("PN"+str);

            /*设置生产计划所属的用户id*/
            producePlan.setUserId(getUserId());

            /*添加生产计划*/
            if(producePlanService.add(producePlan)){

                return R.ok();

            }else {

                return R.error(500,"添加失败");

            }

        }
    }

    /**
     *
     * @param finishDTO
     * @return
     */
    @PostMapping("/finish")
    public R finish(@RequestBody FinishDTO finishDTO){

        ProducePlan producePlan = producePlanService.queryOneById(finishDTO.getProduceId());

        Warehouse warehouse = warehouseService.queryOneById(finishDTO.getWarehouseId());

        WarehouseDetail warehouseDetail = new WarehouseDetail();

        Double totalVolume = (producePlan.getComponentNumber()*producePlan.getComponent().getVolume()*1000)/1000;

        Double leftCapacity  =warehouse.getLeftCapacity()-totalVolume;
        //更新仓库信息

        warehouse.setLeftCapacity(leftCapacity);
        //更新仓库明细

        warehouseDetail.setDate(new Date());

        warehouseDetail.setNo(producePlan.getProduceNo());

        warehouseDetail.setAction(0);

        warehouseDetail.setVolume(totalVolume);

        warehouseDetail.setLeftCapacity(leftCapacity);

        warehouseDetail.setWarehouseId(warehouse.getId());
        //更新生产计划

        producePlan.setStatus(1L);

        producePlan.setWarehouseId(warehouse.getId());

        boolean p = producePlanService.update(producePlan);

        boolean w = warehouseService.update(warehouse);

        boolean wd =  warehouseDetailService.insert(warehouseDetail);

        if(p&&w&&wd){

            return R.ok().put("data","操作成功");

        }else {

            return R.error().put("data","操作失败");
        }
    }

}
