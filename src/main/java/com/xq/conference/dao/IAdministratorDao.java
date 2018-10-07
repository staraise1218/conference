package com.xq.conference.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.xq.conference.model.Administrator;

/**
 * 管理员
 * @ClassName IAdministratorDao
 * @Author yangweihang
 * @Date 2018年9月5日 下午6:23:15
 */
@Mapper
public interface IAdministratorDao {
	
	/**
	 * 按用户名和密码查询管理员
	 * yangweihang
	 * @Date 2018年9月5日 下午6:32:19
	 * @param aname
	 * @param apwd
	 * @return
	 */
	@Select("select * from administrator where aname=#{aname} and apwd=#{apwd}")
	Administrator selectByLogin(@Param("aname")String aname,@Param("apwd")String apwd);
}
