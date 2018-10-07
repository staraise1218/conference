package com.xq.conference.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xq.conference.dao.IUserDao;
import com.xq.conference.model.User;
import com.xq.conference.service.IUserService;

/**
 * 用户
 * @ClassName UserService
 * @Author yangweihang
 * @Date 2018年9月5日 下午4:13:55
 */
@Service("ius")
public class UserService implements IUserService {

    @Autowired
    private IUserDao iudao;

	
	/**
	 * 按用户名查询用户
	 * yangweihang
	 * @Date 2018年9月5日 下午4:14:22
	 * @param userName
	 * @return
	 */
	public User selectByuserName(String userName) {
		return iudao.selectByuserName(userName);
	}
	
}
