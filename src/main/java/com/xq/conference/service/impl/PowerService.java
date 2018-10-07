package com.xq.conference.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xq.conference.dao.IPowerDao;
import com.xq.conference.model.Power;
import com.xq.conference.service.IPowerService;

/**
 * 权限
 * @ClassName IPowerService
 * @Author yangweihang
 * @Date 2018年9月6日 上午9:12:14
 */
@Service("ips")
public class PowerService implements IPowerService {
	
	@Autowired
	private IPowerDao ipdao;
	
	/**
	 * 按用户名查找用户的权限
	 * yangweihang
	 * @Date 2018年9月6日 上午9:13:26
	 * @param username
	 * @return
	 */
	public Power selectByUsername(String username) {
		return ipdao.selectByUsername(username);
	}
	
	/**
	 * 新增权限名
	 * yangweihang
	 * @Date 2018年9月6日 下午3:53:34
	 * @param pname
	 * @return
	 */
	public int insertpower(String pname) {
		return ipdao.insertpower(pname);
	}
}
