package com.example.zxtkxt.controller;



import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.zxtkxt.core.api.ApiError;
import com.example.zxtkxt.core.api.ApiRest;
import com.example.zxtkxt.core.api.controller.BaseController;
import com.example.zxtkxt.mapper.UserMapper;
import com.example.zxtkxt.pojo.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;


@Api(tags = {"管理用户"})
@Controller
@RequestMapping("/user")
@ResponseBody
@CrossOrigin
public class UserController extends BaseController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private HttpServletRequest request;
    /**
     * 获取用户名
     * @return
     */
    @ApiOperation(value = "获取用户名")
    @GetMapping("/getName")
    public ApiRest<String> getName(){
        request.getSession().setAttribute("userName","name");
        String userName =(String) request.getSession().getAttribute("userName");
        System.out.println(userName);
        if (userName==null){
            return failure("请先登录");
        }
        return success("获取成功",userName);
    }

    /**
     * 登录验证
     * @return
     */
    @ApiOperation(value = "用户登录")
    @RequestMapping("/loginCheck")
    public ApiRest<String> loginCheck(@RequestBody User user, HttpServletRequest request){
        User user1 = userMapper.findByUser(user);
        if (user1==null){
            return failure(ApiError.ERROR_90010002.msg);
        }
        request.getSession().setAttribute("userName",user.getUserName());
        user1.setLatestLoginTime(new Date());
        userMapper.updateById(user1);
        if ("管理员".equals(user.getUserRole())){
            return success("登录成功","admin");
        }else {
            return success("登录成功","teacher");
        }
    }

    /**
     * 注册
     * @return
     */
    @ApiOperation(value = "用户注册")
    @RequestMapping("/registerCheck")
    public ApiRest<String> register( @RequestBody User user){
        user.setCreateTime(new Date());
        try {
          userMapper.insert(user);
        }catch (Exception e){
            return failure("注册失败");
        }
        return success("注册成功，请登录","login");

    }

    /**
     * 用户退出
     * @return
     */
    @ApiOperation(value = "用户退出")
    @RequestMapping(value = "/logout", method = {RequestMethod.POST})
    public ApiRest<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("userName");
        return success("index");
    }

    /**
     * 新增用户
     * @return
     */
    @ApiOperation(value = "新增用户")
    @RequestMapping("/save")
    public ApiRest<String> save(@RequestBody User user){
        user.setCreateTime(new Date());
     if (user.getPassword()==null){
         user.setPassword("123456");
     }
        try {
         userMapper.insert(user);
        }catch (Exception e){
            return failure("新增失败");
        }
        return success("新增成功");

    }

    /**
     * 编辑用户
     * @return
     */
    @ApiOperation(value = "编辑用户")
    @PutMapping()
    public ApiRest<String> update(@RequestBody User user){

        System.out.println(user);
        try {
           userMapper.updateById(user);
        }catch (Exception e){
            return failure("编辑失败");
        }
        return success("编辑成功");

    }


    /**
     * 删除用户
     * @return
     */
    @ApiOperation(value = "删除用户")
    @DeleteMapping("/{id}")
    public ApiRest<String> delete(@PathVariable Long id) {
        try {
            userMapper.deleteById(id);
        }catch (Exception e){
            return failure("删除失败");
        }
        return success("删除成功");
    }


    /**
     * 用户分页列表查询，包含用户表的一对多查询
     *
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @GetMapping("/queryUser")
    public ApiRest<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "" ) String search) {
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery().orderByAsc(User::getUserId);
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(User::getUserName, search);
        }

        Page<User> userPage = userMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);

        return success(userPage);
    }


}
