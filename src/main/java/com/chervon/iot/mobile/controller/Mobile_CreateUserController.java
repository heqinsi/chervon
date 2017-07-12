package com.chervon.iot.mobile.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Boy on 2017/6/24.
 */
@RestController
@RequestMapping("/api/v1")
public class Mobile_CreateUserController {
    private static final ObjectMapper mapper = new ObjectMapper();

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<?> getUser(@RequestBody String jsonData)throws Exception{
        JsonNode jsonNode = mapper.readTree(jsonData);
        String password_confirmation=jsonNode.get("password_confirmation").asText();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("Authorization","Bearer BBJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9");
        return  null;
    }
    @RequestMapping(value = "users/{user_id}" ,method= RequestMethod.PATCH)
    public ResponseEntity<?> updateUser(@RequestBody String jsonData, @PathVariable String user_id)throws Exception {
            return null;
    }

    @RequestMapping(value = "/users/{user_id}", method= RequestMethod.GET )
    public ResponseEntity<?> currentUser(@PathVariable String user_id)throws Exception{
        return null;
    }
}