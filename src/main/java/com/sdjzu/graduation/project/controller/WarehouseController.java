package com.sdjzu.graduation.project.controller;

import com.github.pagehelper.PageInfo;
import com.sdjzu.graduation.project.common.utils.R;
import com.sdjzu.graduation.project.domain.Warehouse;
import com.sdjzu.graduation.project.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
/**
 * 有关仓库操作的Controller
 */
@RestController()
@RequestMapping(value = "warehouse")
public class WarehouseController extends AbstractController {
    /**
     * 自动注入仓库服务
     */
    @Autowired
    private WarehouseService warehouseService;

    /**
     *
     * @return
     */
    @GetMapping("/get/all")
    public R getAll(){

        return R.ok().put("data",warehouseService.queryAllByUserId(getUserId()));

    }

    /**
     * 分页查询用户仓库
     * @param request
     * @return
     */
    @GetMapping("/fetch/list")
    public R getAllPage(HttpServletRequest request) {

        /* 从请求获取页数 */
        Integer pageNum = Integer.parseInt(request.getParameter("page"));

        /* 从请求中获取数量 */
        Integer pageSize = Integer.parseInt(request.getParameter("limit"));

        /*从请求获取查询类别*/
        String searchValue = request.getParameter("searchValue");

        /*从请求获取查询内容*/
        String serchContext = "%"+request.getParameter("serchContext")+"%";

        /*分页信息*/
        PageInfo pageInfo = null;

        /*分页数据与分页查询数据*/
        if(searchValue==null){

            /*正常获取分页数据*/
            pageInfo = warehouseService.pageQueryAllByUserId(pageNum,pageSize,getUserId());

        }else {

            /*获取关键字查询后的分页数据*/
            pageInfo = warehouseService.search(pageNum,pageSize,searchValue,serchContext,getUserId());
        }

        /*如果分页数据不为空正常返回前台数据*/
        return (pageInfo==null)? R.error(): R.ok().put("data",pageInfo);
    }

    /**
     * 更新/添加仓库
     * @param
     * @return
     */
    @PostMapping("/update")
    public R update(@RequestBody Warehouse warehouse, HttpServletRequest request){

        /*如果id为空，增加一个仓库，否则更新仓库*/
        if(warehouse.getId()!=null){

            /*更新仓库*/
            if(warehouseService.update(warehouse)){

                return R.ok();

            }else {

                return R.error(500,"更新失败");
            }

        }else{

            /*设置仓库所属的用户id*/
            warehouse.setUserId(getUserId());

            /*设置仓库剩余容量*/
            warehouse.setLeftCapacity(warehouse.getTotalCapacity());

            if(warehouseService.add(warehouse)){

                return R.ok();

            }else {

                return R.error(500,"添加失败");

            }
        }
    }

    /**
     * 删除仓库
     * @param warehouse
     * @return
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Warehouse warehouse){

        try {

            if (warehouseService.delete(warehouse)){

                return R.ok();

            }else {

                return R.error(500, "删除失败");
            }

        }catch (Exception e){

            return R.error().put("msg","此仓库正在被使用无法删除");
        }
    }
}
