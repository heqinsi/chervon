package com.chervon.iot.mobile.mapper;

import com.chervon.iot.mobile.model.Mobile_UserActionLog;

public interface Mobile_UserActionLogMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mobile_useractionlog
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mobile_useractionlog
     *
     * @mbggenerated
     */
    int insert(Mobile_UserActionLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mobile_useractionlog
     *
     * @mbggenerated
     */
    int insertSelective(Mobile_UserActionLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mobile_useractionlog
     *
     * @mbggenerated
     */
    Mobile_UserActionLog selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mobile_useractionlog
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Mobile_UserActionLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mobile_useractionlog
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Mobile_UserActionLog record);
}