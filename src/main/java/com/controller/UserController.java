package com.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 *用户接口
* @ClassName: UserController
* @Description: TODO(这里用一句话描述这个类的作用)
* @author Mr.Dong
* @date 2017年7月10日 上午11:42:02
 */
@RestController
@RequestMapping("api/user")
@Api(value = "用户接口",description = "用户接口")
public class UserController {

    @ApiOperation(value = "查询用户",notes = "查询用户)")
    @RequestMapping(value = "getUsers",method = RequestMethod.GET)
    public Object getUsers(HttpServletRequest request){
        return null;
    }
}
