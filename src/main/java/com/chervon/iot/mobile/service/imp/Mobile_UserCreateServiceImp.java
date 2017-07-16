package com.chervon.iot.mobile.service.imp;

import com.chervon.iot.common.exception.ResultMsg;
import com.chervon.iot.common.exception.ResultStatusCode;
import com.chervon.iot.mobile.mapper.Mobile_UserMapper;
import com.chervon.iot.mobile.model.Mobile_User;
import com.chervon.iot.mobile.model.entity.Included;
import com.chervon.iot.mobile.model.entity.ResponseBody;
import com.chervon.iot.mobile.model.entity.ResponseData;
import com.chervon.iot.mobile.sercuity.JwtTokenUtil;
import com.chervon.iot.mobile.service.Mobile_UserCreateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Boy on 2017/6/24.
 */
@Service
public class Mobile_UserCreateServiceImp implements Mobile_UserCreateService {
  //  public Map<String,String> userMap =new HashMap<String,String>();
    public Object[] included=new Object[0];

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private Mobile_UserMapper mobile_userMapper;
    @Autowired
    private ResponseBody responseBody;
    @Autowired
    private ResponseData responseData;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Override
    @Transactional
    public ResponseEntity<?> createUser(Device device,String type, Mobile_User user)throws SQLException, Exception{
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type","application/vnd.api+json");
        user.setSfdcId("11111");
        mobile_userMapper.insert(user);
        responseData.setType(type);
        responseData.setId(user.getSfdcId());
        Map<String,String> attribute = new HashMap();
        attribute.put("name",user.getName());
        attribute.put("username",user.getUsername());
        attribute.put("email",user.getEmail());
        attribute.put("status","unverified");
        responseData.setAttributes(attribute);
        Map<String,String> link = new HashMap<>();
        link.put("self","https://private-b1af72-egoapi.apiary-mock.com/api/v1/users/"+user.getSfdcId());
        responseData.setLinks(link);
        List<Included> includedList= new ArrayList();
        Map<String,String> meta = new HashMap();
        responseBody.setData(responseData);
        responseBody.setIncluded(includedList);
        responseBody.setMeta(meta);
        final String token = jwtTokenUtil.generateToken(user, device);
        headers.add("Authorization","Bearer "+token);
        return new ResponseEntity<Object>(responseBody,headers, HttpStatus.OK);
    }

    public ResponseEntity<?> getCurrentUser(String token ,String user_id)throws SQLException, Exception{
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type","application/vnd.api+json");
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        Mobile_User user =mobile_userMapper.getUserSfid(user_id);
        if(user!=null){
            responseData.setType("users");
            responseData.setId(user.getSfdcId());
            Map<String,String> attribute = new HashMap();
            attribute.put("name",user.getName());
            attribute.put("username",user.getUsername());
            attribute.put("email",user.getEmail());
            attribute.put("status","verified");
            responseData.setAttributes(attribute);
            Map<String,String> link = new HashMap<>();
            link.put("self","https://private-b1af72-egoapi.apiary-mock.com/api/v1/users/"+user.getSfdcId());
            responseData.setLinks(link);
            List<Included> includedList= new ArrayList();
            Map<String,String> meta = new HashMap();
            responseBody.setData(responseData);
            responseBody.setIncluded(includedList);
            responseBody.setMeta(meta);
            headers.add("Authorization",token);
            return new ResponseEntity<Object>(responseBody,headers, HttpStatus.OK);
        }
        List<ResultMsg.Error> errors = new ArrayList<ResultMsg.Error>();
        ResultMsg.Error error = new ResultMsg.Error(ResultStatusCode.SC_BAD_REQUEST.getErrcode(),ResultStatusCode.SC_BAD_REQUEST.getTitle(),null);
        errors.add(error);
        ResultMsg resultMsg = new ResultMsg(errors);
        return new ResponseEntity(resultMsg,headers, HttpStatus.valueOf(ResultStatusCode.SC_BAD_REQUEST.getErrcode()));

    }

    @Override
    @Transactional
    public ResponseEntity<?> updateUser(String Authorization ,String user_id, Mobile_User user) throws SQLException,Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type","application/vnd.api+json");
       String email= jwtTokenUtil.getEmailFromToken(Authorization);
       Mobile_User mobile_user = mobile_userMapper.getUserByEmail(email);
       if(mobile_user.getSfdcId()!=user_id){
           List<ResultMsg.Error> errors = new ArrayList<ResultMsg.Error>();
           ResultMsg.Error error = new ResultMsg.Error(ResultStatusCode.SC_FORBIDDEN.getErrcode(),ResultStatusCode.SC_FORBIDDEN.getTitle(),null);
           errors.add(error);
           ResultMsg resultMsg = new ResultMsg(errors);
           headers.add("Authorization",Authorization);
           return new ResponseEntity(resultMsg,headers, HttpStatus.valueOf(ResultStatusCode.SC_FORBIDDEN.getErrcode()));
       }
       else if(mobile_user.getEmail()==email){
           user.setSfdcId(user_id);
           mobile_userMapper.updateByPrimaryKey(user);
           responseData.setType("users");
           responseData.setId(user.getSfdcId());
           Map<String,String> attribute = new HashMap();
           attribute.put("name",user.getName());
           attribute.put("username",user.getUsername());
           attribute.put("email",user.getEmail());
           attribute.put("status","verified");
           responseData.setAttributes(attribute);
           Map<String,String> link = new HashMap<>();
           link.put("self","https://private-b1af72-egoapi.apiary-mock.com/api/v1/users/"+user.getSfdcId());
           responseData.setLinks(link);
           List<Included> includedList= new ArrayList();
           Map<String,String> meta = new HashMap();
           responseBody.setData(responseData);
           responseBody.setIncluded(includedList);
           responseBody.setMeta(meta);
           headers.add("Authorization",Authorization);
           return new ResponseEntity<Object>(responseBody,headers, HttpStatus.OK);
       }
        System.out.println("1");
        return null;
    }
}
