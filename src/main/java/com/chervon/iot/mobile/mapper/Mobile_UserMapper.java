package com.chervon.iot.mobile.mapper;

import com.chervon.iot.mobile.model.Mobile_User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public interface Mobile_UserMapper {
      
    int deleteByPrimaryKey(Long id);

    void insert(Mobile_User record);

    Mobile_User getUserByEmail(String email);

    void  updateModifyTime(Mobile_User mobile_user);

    Mobile_User getUserSfid(String sfdc_id);

    int insertSelective(Mobile_User record);

    Mobile_User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Mobile_User record);

    void updateByPrimaryKey(Mobile_User record);
}