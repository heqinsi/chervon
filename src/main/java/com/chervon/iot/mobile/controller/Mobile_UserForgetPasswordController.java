package com.chervon.iot.mobile.controller;

import com.chervon.iot.common.exception.ResultMsg;
import com.chervon.iot.common.exception.ResultStatusCode;
import com.chervon.iot.mobile.service.Mobile_UserForgetPasswordService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.chervon.iot.mobile.model.entity.ResponseBody;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Boy on 2017/6/26.
 */
@RestController
@RequestMapping("/api/v1")
public class Mobile_UserForgetPasswordController {

    private static final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private Mobile_UserForgetPasswordService mobile_userForgetPasswordService;
    @Autowired
    private  ResponseBody responseBody;

    @RequestMapping(value = "/resets", method= RequestMethod.POST)
    public ResponseEntity<?> createReset(@RequestBody String jsonData, Device device)throws SQLException,MessagingException,GeneralSecurityException,IOException{
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type","application/vnd.api+json");
        JsonNode jsonNode = mapper.readTree(jsonData);
        String type = jsonNode.get("data").get("type").asText();
        String  email =jsonNode.get("data").get("attributes").get("email").asText();
        System.out.println(email+"email"+type);
        ResponseEntity  responseEntity = mobile_userForgetPasswordService.forgetPassword(type, email,device);
       return  responseEntity;
    }
}
