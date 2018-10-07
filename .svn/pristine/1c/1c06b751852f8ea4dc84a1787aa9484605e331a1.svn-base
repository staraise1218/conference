package com.xq.conference.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.xq.conference.model.Power;

/**
 * 权限
 * @ClassName IUserPowerDao
 * @Author yangweihang
 * @Date 2018年9月5日 下午7:24:34
 */
@Mapper
public interface IPowerDao {
	
	/**
	 * 按用户名查询用户的权限
	 * yangweihang
	 * @Date 2018年9月6日 上午8:46:21
	 * @param username
	 * @return
	 */
	@Select("select p.* from userpower as up left join power as p on up.pid = p.pid where up.username = #{username}")
	Power selectByUsername(@Param("username")String username);
	
	/**
	 * 新增权限名
	 * yangweihang
	 * @Date 2018年9月6日 下午3:53:34
	 * @param pname
	 * @return
	 */
	@Insert("insert into power(pname) values(#{pname})")
	int insertpower(@Param("pname")String pname);
}
