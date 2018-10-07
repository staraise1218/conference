package com.xq.conference.service;


import com.xq.conference.model.UserPower;

/**
 * 用户权限
 * @ClassName IUserPowerService
 * @Author yangweihang
 * @Date 2018年9月6日 下午5:56:29
 */
public interface IUserPowerService {
	
	/**
	 * 新增用户权限
	 * yangweihang
	 * @Date 2018年9月6日 下午5:55:51
	 * @param username
	 * @return
	 */
	int insertuserpower(String username);
	
	/**
	 * 按用户名查询权限
	 * yangweihang
	 * @Date 2018年9月6日 下午6:04:05
	 * @param username
	 * @return
	 */
	UserPower selectByUsername(String username);
	
	/**
	 * 修改用户权限
	 * yangweihang
	 * @Date 2018年9月6日 下午6:40:57
	 * @param pid
	 * @return
	 */
	int updatepid(int pid,String username);
	
	/**
	 * 用户停用启用
	 * yangweihang
	 * @Date 2018年9月6日 下午7:14:36
	 * @param ustatus
	 * @return
	 */
	int updateustatus(int ustatus,String username);
}
