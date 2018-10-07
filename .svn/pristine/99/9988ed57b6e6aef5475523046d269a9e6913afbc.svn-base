package com.xq.conference.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.xq.conference.model.User;

/**
 * 用户
 * @ClassName IUserDao
 * @Author yangweihang
 * @Date 2018年9月5日 下午4:11:58
 */
@Mapper
public interface IUserDao {

    /**
     * 按用户名查询用户
     * yangweihang
     * @Date 2018年9月5日 下午4:10:40
     * @param userName
     * @return
     */
    @Select("select * from user where userName = #{userName}")
    User selectByuserName(@Param("userName") String userName);
    
}

