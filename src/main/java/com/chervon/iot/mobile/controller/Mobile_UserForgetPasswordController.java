package com.chervon.iot.mobile.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Boy on 2017/6/26.
 */
@RestController
@RequestMapping("/api/v1")
public class Mobile_UserForgetPasswordController {
    private static final ObjectMapper mapper = new ObjectMapper();
    @RequestMapping(value = "/resets", method= RequestMethod.POST)
    public ResponseEntity<?> createReset(@RequestBody String email)throws Exception{
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("Authorization","Bearer BBJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9");
        return null;
    }
}
