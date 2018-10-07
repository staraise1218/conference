package com.xq.conference.service;

import com.xq.conference.model.Power;

/**
 * 权限
 * @ClassName IUserPowerService
 * @Author yangweihang
 * @Date 2018年9月6日 上午8:51:16
 */
public interface IPowerService {
	
	/**
	 * 按用户名查找用户的权限
	 * yangweihang
	 * @Date 2018年9月6日 上午9:11:21
	 * @param username
	 * @return
	 */
	Power selectByUsername(String username);
	
	/**
	 * 新增权限名
	 * yangweihang
	 * @Date 2018年9月6日 下午3:53:34
	 * @param pname
	 * @return
	 */
	int insertpower(String pname);
}
