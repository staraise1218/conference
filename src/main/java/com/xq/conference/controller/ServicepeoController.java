package com.xq.conference.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.xq.conference.service.IServicepeoService;

/**
 * 服务人员控制器
 * @author 川
 * 2018年9月11日15:49:37
 */
@RequestMapping("/servicepeo")
@RestController
public class ServicepeoController {
	
	@Autowired
	private IServicepeoService isps;
	
	/**
	 * 查询所有服务人员列表 用于下拉
	 * @return 全部的服务人员
	 */
	/*@RequestMapping("/selectList")
	public List selectList() {
		
		List list = isps.selectList();
		return list;
	}*/
	
	/**
	 * 添加服务人员
	 * @param seridArry 用户id数组 （他们接口的人员id，佟舟给我一个id数组）
	 * @param ocid 这个订单的主体id
	 * @return 0.添加失败 1.添加成功
	 */
	@RequestMapping("/insertServiceOrder")
	public int insertServiceOrder(String[] id,Integer ocid) {
		int state = isps.insertServiceOrder(id,ocid);
		return state;
	}
	
	@RequestMapping("/selectServicepeoByOcid")
	public List selectServicepeoByOcid(Integer ocid) {
		
		List list = isps.selectServicepeoByOcid(ocid);
		return list;
	}
	
	/**
	 * 通过他们的接口获取服务人员的列表
	 * @return 列表
	 * @throws Exception
	 */
	@RequestMapping(value="/selectServicepeoByOther",produces="application/json;charset=UTF-8")
	public JSONObject selectServicepeoByOther() throws Exception {
		
		JSONObject json = isps.selectServicepeoByOther();
		return json;
	}
}
