package com.xq.conference.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xq.conference.service.ICplatformService;

/**
 * 台型控制器
 * @author 川
 * 2018年9月7日13:19:02
 */
@RestController
@RequestMapping("/cplatform")
public class CplatformController {
	
	@Autowired
	private ICplatformService icps;//台型服务
	
	/**
	 * 查询所有信息
	 * @return
	 */
	@RequestMapping(value="/selectList",produces="application/json;charset=UTF-8")
	public List selectList() {
		List list = icps.selectList();
		return list;
	}
	
	/**
	 * 根据台型id查询该台型照片
	 * @param cid 台型id
	 * @return 照片路径
	 */
	@RequestMapping(value="selectOnePic",produces="application/json;charset=UTF-8")
	public String selectOnePic(int cid) {
		String picUrl = icps.selectOnePic(cid);
		return picUrl;
	}
}
