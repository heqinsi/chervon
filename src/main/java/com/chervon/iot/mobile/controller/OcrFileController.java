package com.chervon.iot.mobile.controller;


import com.chervon.iot.mobile.util.HttpClientUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 喷水君 on 2017/7/11.
 */
@RestController
@RequestMapping("api/v1")
public class OcrFileController {
    private static final ObjectMapper mapper = new ObjectMapper();
    @Value("${ocr.base.url}")
    private  String baseUrl;
    @RequestMapping(value="/ocr")
    @ResponseBody
    public ResponseEntity<?> ocrLoad(@RequestParam("file") MultipartFile files)throws IOException{
        System.out.println(files.getClass());
        System.out.println(files.getOriginalFilename());
        byte[] buffer = files.getBytes();
        File file = new File(files.getOriginalFilename());
        OutputStream output = new FileOutputStream(file);
        BufferedOutputStream bufferedOutput = new BufferedOutputStream(output);
        bufferedOutput.write(buffer);
        System.out.println(file);
        String string=  HttpClientUtil.doPostJson(baseUrl,file);
        System.out.println("doPost"+string);
        JsonNode jsonNode = mapper.readTree(string);
        String handle=jsonNode.get("handle").asText();
        String baseUrls =baseUrl+"/"+handle+"/sss12345";
        String ocrData = HttpClientUtil.doGet(baseUrls);
        System.out.println("ocrData+++++++++++++"+ocrData);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return new ResponseEntity<Object>(ocrData,headers, HttpStatus.MULTI_STATUS);
    }
}
