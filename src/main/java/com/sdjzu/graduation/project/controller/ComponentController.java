package com.sdjzu.graduation.project.controller;

import com.github.pagehelper.PageInfo;
import com.sdjzu.graduation.project.common.utils.R;
import com.sdjzu.graduation.project.domain.Components;
import com.sdjzu.graduation.project.service.ComponentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 有关构件操作的Controller
 */
@RestController()
@RequestMapping(value = "component")
public class ComponentController extends AbstractController {

    /**
     * 自动注入组件服务
     */
    @Autowired
    private ComponentsService componentsService;

    /**
     * 获取用的所有组件
     * @return R
     */
    @GetMapping("/get/all")
    public R getAllComponent(){

        /*通过id查询所有组件*/
        List<Components> components = componentsService.queryAllByUserId(getUserId());

        /*返回前台数据*/
        return R.ok().put("data",components);

    }

    /**
     * 分页查询用户构件
     * @param request
     * @return
     */
    @GetMapping("/fetch/list")
    public R getAllComponentPage(HttpServletRequest request){

        /* 从请求中获取页数 */
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
            pageInfo = componentsService.pageQueryAllByUserId(pageNum,pageSize,getUserId());

        }else{

            /*获取关键字查询后的分页数据*/
            pageInfo = componentsService.search(pageNum,pageSize,searchValue,searchContext,getUserId());

        }

        /*如果分页数据不为空正常返回前台数据*/
        return (pageInfo==null)? R.error(): R.ok().put("data",pageInfo);

    }

    /**
     * 删除构件
     * @param component
     * @return
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Components component){
        try{

            if (componentsService.delete(component)){

                return R.ok();

            }else {

                return R.error(500,"删除失败");

            }
        }catch (Exception e){

            return R.error().put("msg","此构件正在被使用无法删除");

        }
    }

    /**
     * 更新/添加构件
     * @param component
     * @return
     */
    @PostMapping("/update")
    public R update(@RequestBody Components component, HttpServletRequest request){

        /*如果id为空，增加一个构件，否则更新构件*/
        if(component.getId()!=null){
            /*更新构件*/
            if(componentsService.update(component)){

                return R.ok();

            }else {

                return R.error(500,"更新失败");

            }
        }else{

            /*设置默认的构件数量*/
            component.setNumber(20L);

            /*设置构件所属的用户id*/
            component.setUserId(getUserId());

            /*增加构件*/
            if(componentsService.add(component)){

                return R.ok();

            }else {

                return R.error(500,"添加失败");

            }

        }

    }
}
