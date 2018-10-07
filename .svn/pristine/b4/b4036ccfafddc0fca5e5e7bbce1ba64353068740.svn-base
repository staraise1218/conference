package com.xq.conference.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xq.conference.dao.IUserPowerDao;
import com.xq.conference.model.UserPower;
import com.xq.conference.service.IUserPowerService;

/**
 * 用户权限
 * @ClassName UserPowerService
 * @Author yangweihang
 * @Date 2018年9月6日 下午5:57:28
 */
@Service("iups")
public class UserPowerService implements IUserPowerService {

	@Autowired
	private IUserPowerDao iupdao;
	
	/**
	 * 新增用户权限
	 * yangweihang
	 * @Date 2018年9月6日 下午5:55:51
	 * @param username
	 * @return
	 */
	public int insertuserpower(String username) {
		return iupdao.insertuserpower(username);
	}
	
	/**
	 * 按用户名查询权限
	 * yangweihang
	 * @Date 2018年9月6日 下午6:04:05
	 * @param username
	 * @return
	 */
	public UserPower selectByUsername(String username) {
		return iupdao.selectByUsername(username);
	}
	
	/**
	 * 修改用户权限
	 * yangweihang
	 * @Date 2018年9月6日 下午6:40:57
	 * @param pid
	 * @return
	 */
	public int updatepid(int pid,String username) {
		return iupdao.updatepid(pid,username);
	}
	
	/**
	 * 用户停用启用
	 * yangweihang
	 * @Date 2018年9月6日 下午7:14:36
	 * @param ustatus
	 * @return
	 */
	public int updateustatus(int ustatus,String username) {
		return iupdao.updateustatus(ustatus, username);
	}
	
}
