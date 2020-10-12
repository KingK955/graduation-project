package com.sdjzu.graduation.project.controller;

import com.sdjzu.graduation.project.common.utils.MapperUtils;
import com.sdjzu.graduation.project.common.utils.OkHttpClientUtil;
import com.sdjzu.graduation.project.common.utils.R;
import com.sdjzu.graduation.project.domain.PlatToken;
import com.sdjzu.graduation.project.service.PlatTokenService;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

@RestController()
@RequestMapping(value = "request")
public class RequestController extends AbstractController {

    @Autowired
    private PlatTokenService platTokenService;

    private static String common = "http://localhost:8082/out/factory";

    @GetMapping("/get/mine")
    public R getMine(HttpServletRequest request) throws Exception {
        /* 获取请求页数 */
        Integer pageNum = Integer.parseInt(request.getParameter("page").trim());
        //获取请求数量
        Integer pageSize = Integer.parseInt(request.getParameter("limit").trim());

        PlatToken platToken = platTokenService.queryByUserId(getUserId());

        Response response = OkHttpClientUtil
                .getInstance()
                .getData(common+"/get/my/request?page="+pageNum+"&limit="+pageSize,platToken.getToken());

        String jsonString = Objects.requireNonNull(response.body()).string();

        Map<String, Object> jsonMap = MapperUtils.json2map(jsonString);

        return R.ok().put("data",jsonMap.get("data"));

    }

    @GetMapping("/get/for/me")
    public R getForMe(HttpServletRequest request) throws Exception {
        /* 获取请求页数 */
        Integer pageNum = Integer.parseInt(request.getParameter("page").trim());
        //获取请求数量
        Integer pageSize = Integer.parseInt(request.getParameter("limit").trim());

        PlatToken platToken = platTokenService.queryByUserId(getUserId());

        Response response = OkHttpClientUtil
                .getInstance()
                .getData(common+"/get/request/for/me?page="+pageNum+"&limit="+pageSize,platToken.getToken());

        String jsonString = Objects.requireNonNull(response.body()).string();

        Map<String, Object> jsonMap = MapperUtils.json2map(jsonString);

        return R.ok().put("data",jsonMap.get("data"));

    }

     @GetMapping("/get/one/request/for/me")
    public R getOneRequestForMe(HttpServletRequest request) throws Exception {
        //request.getParameter("servicesId").trim()
        PlatToken platToken = platTokenService.queryByUserId(getUserId());

        String url = common+"/get/one/request/for/me?servicesId="+request.getParameter("servicesId").trim();
        Response response = OkHttpClientUtil
                .getInstance()
                .getData(url,platToken.getToken());

        String jsonString = Objects.requireNonNull(response.body()).string();

        Map<String, Object> jsonMap = MapperUtils.json2map(jsonString);

        return R.ok().put("data",jsonMap.get("data"));
    }

    @GetMapping("/get/one/my/request")
    public R getOneMyRequest(HttpServletRequest request) throws Exception {
        //request.getParameter("servicesId").trim()
        PlatToken platToken = platTokenService.queryByUserId(getUserId());

        String url = common+"/get/one/my/request?servicesId="+request.getParameter("servicesId").trim();

        Response response = OkHttpClientUtil
                .getInstance()
                .getData(url,platToken.getToken());

        String jsonString = Objects.requireNonNull(response.body()).string();

        Map<String, Object> jsonMap = MapperUtils.json2map(jsonString);

        return R.ok().put("data",jsonMap.get("data"));
    }

    @GetMapping("/delete/my/request")
    public R deleteMyRequest(HttpServletRequest request) throws Exception {

        PlatToken platToken = platTokenService.queryByUserId(getUserId());

        String url = common+"/delete/my/request?servicesId="+request.getParameter("servicesId").trim();

        Response response = OkHttpClientUtil
                .getInstance()
                .getData(url,platToken.getToken());

        String jsonString = Objects.requireNonNull(response.body()).string();

        Map<String, Object> jsonMap = MapperUtils.json2map(jsonString);

        return R.ok().put("data",jsonMap.get("data"));
    }

    @GetMapping("/agree")
    public R requestAgree(HttpServletRequest request) throws Exception {

        PlatToken platToken = platTokenService.queryByUserId(getUserId());

        String url = common+"/request/agree?servicesId="+request.getParameter("servicesId").trim();

        Response response = OkHttpClientUtil
                .getInstance()
                .getData(url,platToken.getToken());

        String jsonString = Objects.requireNonNull(response.body()).string();

        Map<String, Object> jsonMap = MapperUtils.json2map(jsonString);

        return R.ok().put("data",jsonMap.get("data"));
    }

    @GetMapping("/deny")
    public R requestDeny(HttpServletRequest request) throws Exception {

        PlatToken platToken = platTokenService.queryByUserId(getUserId());

        String url =common+ "/request/deny?servicesId="+request.getParameter("servicesId").trim();

        Response response = OkHttpClientUtil
                .getInstance()
                .getData(url,platToken.getToken());

        String jsonString = Objects.requireNonNull(response.body()).string();

        Map<String, Object> jsonMap = MapperUtils.json2map(jsonString);

        return R.ok().put("data",jsonMap.get("data"));
    }

}
