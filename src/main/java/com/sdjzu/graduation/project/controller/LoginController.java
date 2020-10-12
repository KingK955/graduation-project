package com.sdjzu.graduation.project.controller;

import com.sdjzu.graduation.project.common.utils.R;
import com.sdjzu.graduation.project.domain.User;
import com.sdjzu.graduation.project.dto.LoginDTO;
import com.sdjzu.graduation.project.dto.LoginInfoDTO;
import com.sdjzu.graduation.project.service.UserService;
import com.sdjzu.graduation.project.service.UserTokenService;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;
/**
 *  有关登录的控制器
 */
@RestController
public class LoginController extends AbstractController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserTokenService tokenService;

    /**
     * 登录
     */
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody LoginDTO dto)throws IOException {
        /*获取用户信息*/
        User user = userService.queryByUserName(dto.getUsername());
        System.out.println(user);
        /*如果账号不存在返回账号或密码不存在*/
        if(user == null || !user.getPassword().equals(new Sha256Hash(dto.getPassword(), user.getSalt()).toHex())) {
            return R.error("账号或密码不正确");
        }

        /*账号锁定*/
        if(user.getStatus() == 0){
            return R.error("账号已被锁定,请联系管理员");
        }

        //生成token，并保存到数据库
        R r = tokenService.createToken(user.getId());
        return r;
    }

    @GetMapping(value = "/info")
    public R info() {
        User user = userService.queryUser(getUserId());
        // 封装并返回结果.
        LoginInfoDTO loginInfoDTO = new LoginInfoDTO();
        loginInfoDTO.setName(user.getUsername());
        loginInfoDTO.setAvatar(user.getAvater());
        loginInfoDTO.setNickName(user.getNickname());
        return R.ok().put("data", loginInfoDTO);
    }

    /**
     * 退出
     */
    @PostMapping("/logout")
    public R logout() {
        tokenService.logout(getUserId());
        return R.ok();
    }

}
