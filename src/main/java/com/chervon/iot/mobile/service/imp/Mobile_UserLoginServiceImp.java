package com.chervon.iot.mobile.service.imp;
import com.chervon.iot.mobile.mapper.Mobile_UserMapper;
import com.chervon.iot.mobile.model.Mobile_User;
import com.chervon.iot.mobile.model.entity.Included;
import com.chervon.iot.mobile.model.entity.Relationship;
import com.chervon.iot.mobile.model.entity.ResponseBody;
import com.chervon.iot.mobile.model.entity.ResponseData;
import com.chervon.iot.mobile.service.Mobile_UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.*;

/**
 * Created by 喷水君 on 2017/6/27.
 */
@Service
public class Mobile_UserLoginServiceImp implements Mobile_UserLoginService {
    public Map<String,String> userMap =new HashMap<String,String>();
    @Autowired
    private Mobile_User mobileUser;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private Mobile_UserMapper mobile_userMapper;
    @Autowired
    private Relationship relationship;
    @Autowired
    private ResponseData responseData;

    @Autowired
    private Included included;
    @Autowired
    private ResponseBody responseBody;


    @Override
    public Mobile_User getUserByEmail(String email) throws SQLException {

        mobileUser = mobile_userMapper.getUserByEmail(email);
        System.out.println(mobileUser + "mobile");
        return  mobileUser;
    }

    @Override
    public ResponseBody loginReturn(Mobile_User mobile_user) {
        responseData.setType("session");
        responseData.setId(mobile_user.getSfdcId());
        Map<String,String> attributeMap = new HashMap<String,String>();
        String createDate =String.valueOf(mobile_user.getCreatedate());
        attributeMap.put("created_at",createDate);
        responseData.setAttributes(attributeMap);
        Map<String,String> links = new HashMap<String, String>();
        Map<String,String> data = new HashMap<String, String>();
        links.put("self", "https://private-c0530-iyo.apiary-mock.com");
        links.put("related","https://private-c0530-iyo.apiary-mock.");
        data.put("type","users");
        data.put("id",mobile_user.getSfdcId());
        relationship.setLinks(links);
        relationship.setData(data);
        Map<String,Relationship> creator = new HashMap<String, Relationship>();
        creator.put("creator",relationship);
        responseData.setRelationships(creator);
        Map<String,String> linkMap = new HashMap<String,String>();
        linkMap.put("self","https://private-c0530-iyo.apiary-mock.com/api");
        responseData.setLinks(linkMap);
        included.setType("users");
        included.setId(mobile_user.getSfdcId());
        Map<String,String> includeAttribute = new HashMap<String,String>();
        includeAttribute.put("name", mobile_user.getName());
        includeAttribute.put("username", mobile_user.getUsername());
        includeAttribute.put("email", mobile_user.getEmail());
        includeAttribute.put("status", "verified");
        included.setAttributes(includeAttribute);
        Map<String,String> includeLink = new HashMap<String,String>();
        includeLink.put("self", "https://private-c0530-iyo.apiary-mock.com/a");
        included.setLinks(includeLink);
        List<Included> includedList = new ArrayList<Included>();
        includedList.add(included);
        responseBody.setData(responseData);
        responseBody.setIncluded(includedList);
        Map<String,String> metaMap = new HashMap<String,String>();
        responseBody.setMeta(metaMap);
        return responseBody;
    }

    @Override
    @Transactional
    public void modifyTime(Mobile_User mobile_user)throws SQLException {
      mobile_userMapper.updateModifyTime(mobile_user);
    }
}
