package com.xq.conference.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xq.conference.service.IOperationsetService;

/**
 * 操作设置
 * @ClassName OperationsetController
 * @Author yangweihang
 * @Date 2018年9月6日 上午11:24:40
 */
@RestController
@RequestMapping("/operationset")
public class OperationsetController {
	
	@Autowired
	private IOperationsetService ios;
	
	/**
	 * 修改预定编辑间隔时间
	 * yangweihang
	 * @Date 2018年9月6日 上午10:47:15
	 * @param bookeditor
	 * @return
	 */
	@RequestMapping("/updatebookeditor")
	public String updatebookeditor(int bookeditor) {
		String str = "";
		int result = ios.updatebookeditor(bookeditor);
		if(result > 0) {
			str = "修改成功！";
		}else {
			str = "修改失败！";
		}
		return str;
	}
	
	/**
	 * 修改默认评论好评时间
	 * yangweihang
	 * @Date 2018年9月6日 上午11:05:25
	 * @param commentdefault
	 * @return
	 */
	@RequestMapping("/updatecommentdefault")
	public String updatecommentdefault(int commentdefault) {
		String str = "";
		int result = ios.updatecommentdefault(commentdefault);
		if(result > 0) {
			str = "修改成功！";
		}else {
			str = "修改失败！";
		}
		return str;
	}
	
	/**
	 * 修改会议室开始前的N小时不能进行对接确认操作
	 * yangweihang
	 * @Date 2018年9月6日 上午11:06:53
	 * @param jointdefault
	 * @return
	 */
	@RequestMapping("/updatejointdefault")
	public String updatejointdefault(int jointdefault) {
		String str = "";
		int result = ios.updatejointdefault(jointdefault);
		if(result > 0) {
			str = "修改成功！";
		}else {
			str = "修改失败！";
		}
		return str;
	}
}
