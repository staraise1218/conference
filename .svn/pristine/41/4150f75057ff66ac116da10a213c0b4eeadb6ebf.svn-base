package com.xq.conference.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xq.conference.dao.IAdministratorDao;
import com.xq.conference.model.Administrator;
import com.xq.conference.service.IAdministratorService;

/**
 * 管理员
 * IAdministratorService
 * @ClassName AdministratorService
 * @Author yangweihang
 * @Date 2018年9月5日 下午6:59:49
 */
@Service("ias")
public class AdministratorService implements IAdministratorService {
	
	@Autowired
	private IAdministratorDao iadao;

	/**
	 * 
	 * yangweihang
	 * @Date 2018年9月5日 下午7:00:22
	 * @param aname
	 * @param apwd
	 * @return
	 */
	public Administrator selectByLogin(String aname, String apwd) {
		return iadao.selectByLogin(aname, apwd);
	}

}
