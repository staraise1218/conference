package com.xq.conference.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

/**
 * 服务人员服务接口
 * @author 川
 * 2018年9月11日15:51:17
 */
public interface IServicepeoService {

	List selectList();

	int insertServiceOrder(String[] id, Integer ocid);

	List selectServicepeoByOcid(Integer ocid);

	/**
	 * 获取外部院外职工信息
	 * @return
	 * @throws Exception 
	 */
	JSONObject selectServicepeoByOther() throws Exception;
	
}
