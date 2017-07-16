package com.chervon.iot.mobile.service.imp;

import com.chervon.iot.common.exception.ResultMsg;
import com.chervon.iot.common.exception.ResultStatusCode;
import com.chervon.iot.mobile.mapper.Mobile_UserMapper;
import com.chervon.iot.mobile.model.Mobile_User;
import javax.mail.MessagingException;

import com.chervon.iot.mobile.model.entity.Included;
import com.chervon.iot.mobile.model.entity.ResponseBody;
import com.chervon.iot.mobile.model.entity.ResponseData;
import com.chervon.iot.mobile.sercuity.JwtTokenUtil;
import com.chervon.iot.mobile.service.Mobile_UserForgetPasswordService;
import com.chervon.iot.mobile.util.SendEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Service;

import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static sun.audio.AudioDevice.device;
/**
 * Created by 喷水君 on 2017/6/27.
 */
@Service
public class Mobile_UserForgetPasswordServiceImp implements Mobile_UserForgetPasswordService {
    @Autowired
    private Mobile_UserMapper mobile_userMapper;
    @Autowired
    private Mobile_User mobile_user;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${email.url}")
    private  String emailUrl;
    @Autowired
    private ResponseData responData;
    @Autowired
    private Included included;
    @Autowired
    private ResponseBody responseBody;
    @Override
    public ResponseEntity<?> forgetPassword(String type, String email, Device device)throws SQLException,MessagingException,GeneralSecurityException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type","application/vnd.api+json");
       mobile_user= mobile_userMapper.getUserByEmail(email);
       if(mobile_user!=null){
           final String token = jwtTokenUtil.generateToken(mobile_user, device);
           String  url = emailUrl+mobile_user.getSfdcId()+token;
           SendEmail  sendEmail = new SendEmail();
           sendEmail.sendAttachmentsMail(email,url);
            responData.setType(type);
            responData.setId(mobile_user.getSfdcId());
            Map<String,String>  attribute = new HashMap<>();
            attribute.put("email",email);
            responData.setAttributes(attribute);
            Map<String,String>  link = new HashMap<>();
            link.put("self","//private-b1af72-egoapi.apiary-mock.com/api/v1/resets/"+mobile_user.getSfdcId());
            responData.setLinks(link);
            List<Included> includedList = new ArrayList<>();
            responseBody.setIncluded(includedList);
            Map<String,String>  meta = new HashMap<>();
            meta.put("message","An email has been sent with your password recovery instructions!");
            responseBody.setData(responData);
            responseBody.setMeta(meta);
            return new ResponseEntity<Object>(responseBody,headers,HttpStatus.OK);
         }
        List<ResultMsg.Error> errors = new ArrayList<ResultMsg.Error>();
        ResultMsg.Error error = new ResultMsg.Error(ResultStatusCode.SC_BAD_REQUEST.getErrcode(),ResultStatusCode.SC_BAD_REQUEST.getTitle(),null);
        errors.add(error);
        ResultMsg resultMsg = new ResultMsg(errors);
        return new ResponseEntity(resultMsg,headers, HttpStatus.valueOf(ResultStatusCode.SC_BAD_REQUEST.getErrcode()));

    }
}
