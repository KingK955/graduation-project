package com.sdjzu.graduation.project.controller;

import com.sdjzu.graduation.project.common.utils.R;
import com.sdjzu.graduation.project.domain.PlatToken;
import com.sdjzu.graduation.project.domain.User;
import com.sdjzu.graduation.project.dto.PasswordDTO;
import com.sdjzu.graduation.project.dto.PlatTokenDTO;
import com.sdjzu.graduation.project.dto.ProfileDTO;
import com.sdjzu.graduation.project.service.PlatTokenService;
import com.sdjzu.graduation.project.service.UserService;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController()
@RequestMapping(value = "profile")
public class ProfileController extends AbstractController {

    @Autowired
    private UserService userService;

    @Autowired
    private PlatTokenService platTokenService;

    @GetMapping("/info/{username}")

    public R info(@PathVariable String username){

        User user = userService.queryByUserName(username);

        ProfileDTO profileDTO = new ProfileDTO();

        BeanUtils.copyProperties(user,profileDTO);

        return R.ok().put("data",profileDTO);
    }

    @PostMapping("/update")
    public R update(@RequestBody ProfileDTO profileDTO){

        User user = new User();

        //spring 工具类，帮助对象转化
        BeanUtils.copyProperties(profileDTO,user);

        user.setPassword(getUser().getPassword());

        user.setSalt(getUser().getSalt());

        int result = userService.update(user);

        if(result >0){

            return R.ok().put("data","更新个人信息成功");

        }else{

            return R.error().put("data","更新个人信息失败");

        }
    }

    @PostMapping("/modify/password")
    public R password(@RequestBody PasswordDTO form){

        //sha256加密
        String oldPassword = new Sha256Hash(form.getOldPassword(), getUser().getSalt()).toHex();
        //sha256加密
        String newPassword = new Sha256Hash(form.getNewPassword(), getUser().getSalt()).toHex();

        User user = getUser();

        if(user.getPassword().equals(oldPassword)){

            user.setPassword(newPassword);

            if(userService.update(user)<0){

                return R.error("修改密码失败");

            }
        }else {

            return R.error("原密码不正确");

        }
        //更新密码
        return R.ok().put("data","更新密码成功");
    }

    @GetMapping("/is/token")
    public R isToken(){

        Map<String,Object> map = new HashMap<>();

        map.put("isToken",platTokenService.isToken(getUserId()));

        PlatToken platToken = platTokenService.queryByUserId(getUserId());

        if (platToken!=null){

            map.put("token",platToken.getToken());

        }

        return R.ok().put("data",map);
    }

    @PostMapping("/update/token")
    public R isToken(@RequestBody PlatTokenDTO platTokenDTO){

        System.out.println(platTokenDTO);

        PlatToken platToken = platTokenService.queryByUserId(getUserId());

        if(platToken==null) {

            platToken = new PlatToken();

            platToken.setUserId(getUserId());

            platToken.setToken(platTokenDTO.getToken());

            return platTokenService.add(platToken)? R.ok().put("data","更新token成功"): R.error().put("data","更新token失败");

        }else{

            platToken.setToken(platTokenDTO.getToken());

            return platTokenService.update(platToken)? R.ok().put("data","更新token成功"): R.error().put("data","更新token失败");

        }
    }
}
