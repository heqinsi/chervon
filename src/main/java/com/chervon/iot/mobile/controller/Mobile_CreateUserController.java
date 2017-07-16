package com.chervon.iot.mobile.controller;

import com.chervon.iot.common.exception.ResultMsg;
import com.chervon.iot.common.exception.ResultStatusCode;
import com.chervon.iot.mobile.model.Mobile_User;
import com.chervon.iot.mobile.service.Mobile_UserCreateService;
import com.chervon.iot.mobile.service.Mobile_UserLoginService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 创建用户，查看用户，更新用户
 * Created by Boy on 2017/6/24.
 */
@RestController
@RequestMapping("/api/v1")
public class Mobile_CreateUserController {

    @Autowired
    private Mobile_UserLoginService mobile_userLoginService;

    @Autowired
    private Mobile_UserCreateService mobile_userCreateService;
    @Autowired
    private  Mobile_User mobileUser;
    //json解析对象
    private static final ObjectMapper mapper = new ObjectMapper();
    //创建用户
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<?> ceateUser(@RequestBody String jsonData, Device device)throws SQLException,Exception{
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type","application/vnd.api+json");
        //json解析
        JsonNode jsonNode = mapper.readTree(jsonData);
        String password_confirmation=jsonNode.get("data").get("attributes").get("password_confirmation").asText();
        String password=jsonNode.get("data").get("attributes").get("password").asText();
       //输入的两次密码不匹配
        if(!password.equals(password_confirmation)){
            List<ResultMsg.Error> errors = new ArrayList<ResultMsg.Error>();
            ResultMsg.Error error = new ResultMsg.Error(ResultStatusCode.SC_BAD_REQUEST.getErrcode(),ResultStatusCode.SC_BAD_REQUEST.getTitle(),null);
            errors.add(error);
            ResultMsg resultMsg = new ResultMsg(errors);
            return new ResponseEntity(resultMsg,headers, HttpStatus.valueOf(ResultStatusCode.SC_BAD_REQUEST.getErrcode()));
        }
        String type=jsonNode.get("data").get("type").asText();
        String email=jsonNode.get("data").get("attributes").get("email").asText();
        String name=jsonNode.get("data").get("attributes").get("name").asText();
        //通过email查询是否该email已经被注册
        Mobile_User mobile_user=mobile_userLoginService.getUserByEmail(email);
        //已注册
        if (mobile_user!=null){
            List<ResultMsg.Error> errors = new ArrayList<ResultMsg.Error>();
            ResultMsg.Error error = new ResultMsg.Error(ResultStatusCode.SC_CONFLICT.getErrcode(),ResultStatusCode.SC_CONFLICT.getTitle(),null);
            errors.add(error);
            ResultMsg resultMsg = new ResultMsg(errors);
            return new ResponseEntity(resultMsg,headers, HttpStatus.valueOf(ResultStatusCode.SC_CONFLICT.getErrcode()));
        }
        mobileUser.setCreatedate(new Date());
        mobileUser.setPassword(password);
        mobileUser.setEmail(email);
        mobileUser.setEnabled(true);
        mobileUser.setName(name);
        //mobileUser
        ResponseEntity responseEntity = mobile_userCreateService.createUser(device,type,mobileUser);
        return  responseEntity;
    }
    //查询指定用户的的信息
    @RequestMapping(value = "/users/{user_id}", method= RequestMethod.GET )
    public ResponseEntity<?> currentUser(@RequestHeader String Authorization,@PathVariable String user_id)throws SQLException,Exception{
        return   mobile_userCreateService.getCurrentUser(Authorization,user_id);

    }
    //更新信息
    @RequestMapping(value = "users/{user_id}" ,method= RequestMethod.PATCH)
    public ResponseEntity<?> updateUser(@RequestHeader String Authorization,@RequestBody String jsonData,@PathVariable String user_id)throws SQLException,Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type","application/vnd.api+json");
        JsonNode jsonNode = mapper.readTree(jsonData);
        String password_confirmation=jsonNode.get("data").get("attributes").get("password_confirmation").asText();
        String password=jsonNode.get("data").get("attributes").get("password").asText();
        //输入的两次密码不匹配
        if(!password.equals(password_confirmation)){
            List<ResultMsg.Error> errors = new ArrayList<ResultMsg.Error>();
            ResultMsg.Error error = new ResultMsg.Error(ResultStatusCode.SC_BAD_REQUEST.getErrcode(),ResultStatusCode.SC_BAD_REQUEST.getTitle(),null);
            errors.add(error);
            System.out.println(false);
            ResultMsg resultMsg = new ResultMsg(errors);
            return new ResponseEntity(resultMsg,headers, HttpStatus.valueOf(ResultStatusCode.SC_BAD_REQUEST.getErrcode()));
        }
        String type=jsonNode.get("data").get("type").asText();
        String email=jsonNode.get("data").get("attributes").get("email").asText();
        String name=jsonNode.get("data").get("attributes").get("name").asText();
        mobileUser.setCreatedate(new Date());
        mobileUser.setPassword(password);
        mobileUser.setEmail(email);
        mobileUser.setEnabled(true);
        mobileUser.setName(name);
        return mobile_userCreateService.updateUser(Authorization,user_id,mobileUser);
    }


}