package com.xq.conference.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.xq.conference.model.UserPower;

/**
 * 用户权限
 * @ClassName IUserPowerDao
 * @Author yangweihang
 * @Date 2018年9月6日 下午5:51:18
 */
@Mapper
public interface IUserPowerDao {
	
	/**
	 * 新增用户权限
	 * yangweihang
	 * @Date 2018年9月6日 下午5:55:51
	 * @param username
	 * @return
	 */
	@Insert("insert into userpower(username) values(#{username})")
	int insertuserpower(@Param("username")String username);
	
	/**
	 * 按用户名查询权限
	 * yangweihang
	 * @Date 2018年9月6日 下午6:04:05
	 * @param username
	 * @return
	 */
	@Select("select * from userpower where username = #{username}")
	UserPower selectByUsername(@Param("username")String username);
	
	/**
	 * 修改用户权限
	 * yangweihang
	 * @Date 2018年9月6日 下午6:40:57
	 * @param pid
	 * @return
	 */
	@Update("update userpower set pid=#{pid} where username = #{username}")
	int updatepid(@Param("pid")int pid,@Param("username")String username);
	
	/**
	 * 用户停用启用
	 * yangweihang
	 * @Date 2018年9月6日 下午7:14:36
	 * @param ustatus
	 * @return
	 */
	@Update("update userpower set ustatus=#{ustatus} where username=#{username}")
	int updateustatus(@Param("ustatus")int ustatus,@Param("username")String username);
}
