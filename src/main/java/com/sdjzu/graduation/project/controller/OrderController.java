package com.sdjzu.graduation.project.controller;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sdjzu.graduation.project.common.utils.MapperUtils;
import com.sdjzu.graduation.project.common.utils.OkHttpClientUtil;
import com.sdjzu.graduation.project.common.utils.R;
import com.sdjzu.graduation.project.domain.PlatToken;
import com.sdjzu.graduation.project.domain.Warehouse;
import com.sdjzu.graduation.project.domain.WarehouseDetail;
import com.sdjzu.graduation.project.dto.OrderDTO;
import com.sdjzu.graduation.project.dto.ServicesDTO;
import com.sdjzu.graduation.project.service.PlatTokenService;
import com.sdjzu.graduation.project.service.WarehouseDetailService;
import com.sdjzu.graduation.project.service.WarehouseService;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

@RestController()
@RequestMapping(value = "order")
public class OrderController extends AbstractController {


    @Autowired
    private PlatTokenService platTokenService;

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private WarehouseDetailService warehouseDetailService;

    @GetMapping("/fetch/list")
    public R fetchList(HttpServletRequest request) throws Exception {

        /* 获取请求页数 */
        Integer pageNum = Integer.parseInt(request.getParameter("page").trim());
        /* 获取请求数量 */
        Integer pageSize = Integer.parseInt(request.getParameter("limit").trim());

        String searchValue = request.getParameter("searchValue");

        String searchContext = request.getParameter("searchContext");

        PlatToken platToken = platTokenService.queryByUserId(getUserId());
        Response response;

        if(searchValue==null){
            response = OkHttpClientUtil
                    .getInstance()
                    .getData("http://localhost:8082/out/factory/fetch/list?page="+pageNum+"&limit="+pageSize,platToken.getToken());
        }else {
            response = OkHttpClientUtil
                    .getInstance()
                    .getData("http://localhost:8082/out/factory/fetch/list?page="+pageNum+"&limit="+pageSize+"&searchValue="+searchValue+"&searchContext="+searchContext,platToken.getToken());
        }

        String jsonString = Objects.requireNonNull(response.body()).string();

        Map<String, Object> jsonMap = MapperUtils.json2map(jsonString);

        return R.ok().put("data",jsonMap.get("data"));
    }

    @GetMapping("/get/all/building")
    public R getAllFactory() throws Exception {
        PlatToken platToken = platTokenService.queryByUserId(getUserId());
        Response response = OkHttpClientUtil
                .getInstance()
                .getData("http://localhost:8082/out/factory/all/building",platToken.getToken());

        String jsonString = Objects.requireNonNull(response.body()).string();

        Map<String, Object> jsonMap = MapperUtils.json2map(jsonString);

        return R.ok().put("data",jsonMap.get("data"));
    }

    @GetMapping("/get/by/order/no")
    public R getByOrderNo (HttpServletRequest request) throws Exception {

        PlatToken platToken = platTokenService.queryByUserId(getUserId());

        String orderNo = request.getParameter("orderNo");

        Response response = OkHttpClientUtil
                .getInstance()
                .getData("http://localhost:8082/out/factory/get/by/order/no?orderNo="+orderNo,platToken.getToken());

        String jsonString = Objects.requireNonNull(response.body()).string();

        //System.out.println(jsonString);

        Map<String, Object> jsonMap = MapperUtils.json2map(jsonString);

        return R.ok().put("data",jsonMap.get("data"));
    }

    @GetMapping("/deliver/goods")
    public R takeGoods(HttpServletRequest request) throws IOException {

        PlatToken platToken = platTokenService.queryByUserId(getUserId());
        Long warehouseId = Long.parseLong(request.getParameter("warehouseId"));
        String orderNo = request.getParameter("orderNo");
        Response response = OkHttpClientUtil
                .getInstance()
                .getData("http://localhost:8082/out/building/get/by/order/no?orderNo="+orderNo,platToken.getToken());

        JSONObject jsonObject = JSONObject.parseObject(Objects.requireNonNull(response.body()).string());

        OrderDTO orderDTO = JSON.parseObject(jsonObject.getString("data"),OrderDTO.class);

        Warehouse warehouse = warehouseService.queryOneById(warehouseId);

        WarehouseDetail warehouseDetail = new WarehouseDetail();

        Double totalVolume = (orderDTO.getComponentNumber()*orderDTO.getComponentVolume()*1000)/1000;

        Double leftCapacity  =warehouse.getLeftCapacity()+totalVolume;
        //更新仓库信息
        warehouse.setLeftCapacity(leftCapacity);
        //更新仓库明细
        warehouseDetail.setDate(new Date());
        warehouseDetail.setNo(orderDTO.getOrderNo());
        warehouseDetail.setAction(1);
        warehouseDetail.setLeftCapacity(leftCapacity);
        warehouseDetail.setVolume(totalVolume);
        warehouseDetail.setWarehouseId(warehouse.getId());

        Response res = OkHttpClientUtil
                .getInstance()
                .getData("http://localhost:8082/out/factory/deliver/goods?orderNo="+orderNo,platToken.getToken());

        boolean w = warehouseService.update(warehouse);
        boolean wd =  warehouseDetailService.insert(warehouseDetail);
        System.out.println(orderNo);
        if(w&&wd){
            return R.ok().put("data","操作成功");
        }else {
            return R.error().put("data","操作失败");
        }
    }

    @PostMapping("/delay/application")
    public R delayApplication(@RequestBody ServicesDTO servicesDTO) throws Exception {

        PlatToken platToken = platTokenService.queryByUserId(getUserId());

        String json = JSON.toJSONString(servicesDTO);

        String response = OkHttpClientUtil
                .getInstance()
                .postJson("http://localhost:8082/out/factory/application",platToken.getToken(),json);

        Map<String, Object> jsonMap = MapperUtils.json2map(response);

        return R.ok().put("data",jsonMap.get("data"));
    }
}
