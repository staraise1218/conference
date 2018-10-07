package com.xq.conference.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xq.conference.dao.ICplatformDao;
import com.xq.conference.service.ICplatformService;

@Service("icps")
public class CplatformService implements ICplatformService{
	
	@Autowired
	private ICplatformDao icpdao;

	public List selectList() {
		List list = icpdao.selectList();
		return list;
	}

	public String selectOnePic(int cid) {
		String picUrl = icpdao.selectOnePic(cid);
		return picUrl;
	}
}
