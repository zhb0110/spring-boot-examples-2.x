package com.example.springbootjwt.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.springbootjwt.config.JwtConfig;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
public class TokenController {

    @Resource
    private JwtConfig jwtConfig;

    /**
     * 用户登录接口
     *
     * @param userName
     * @param passWord
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestParam("userName") String userName,
                                        @RequestParam("passWord") String passWord) {
        JSONObject json = new JSONObject();

        /** 验证userName，passWord和数据库中是否一致，如不一致，直接return ResultTool.errer(); 【这里省略该步骤】*/

        // 这里模拟通过用户名和密码，从数据库查询userId
        // 这里把userId转为String类型，实际开发中如果subject需要存userId，则可以JwtConfig的createToken方法的参数设置为Long类型
        String userId = 5 + "";
        String token = jwtConfig.createToken(userId);
        if (!StringUtils.isEmpty(token)) {
            json.put("token", token);
        }
//        return ResultTool.success(json);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    /**
     * 需要 Token 验证的接口
     */
    @PostMapping("/info")
    public ResponseEntity<Object> info() {
//        return ResultTool.success("info");
        return new ResponseEntity<>("info", HttpStatus.OK);
    }

    /**
     * 根据请求头的token获取userId
     *
     * @param request
     * @return
     */
    @GetMapping("/getUserInfo")
    public ResponseEntity<Object> getUserInfo(HttpServletRequest request) {
        String usernameFromToken = jwtConfig.getUsernameFromToken(request.getHeader("token"));
//        return ResultTool.success(usernameFromToken);

        return new ResponseEntity<>(usernameFromToken, HttpStatus.OK);
    }

    /*
        为什么项目重启后，带着之前的token还可以访问到需要info等需要token验证的接口？
        答案：只要不过期，会一直存在，类似于redis
     */

}
