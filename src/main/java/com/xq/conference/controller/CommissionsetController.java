package com.xq.conference.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xq.conference.service.ICommissionsetService;

/**
 * 待办提醒设置
 * @ClassName CommissionsetController
 * @Author yangweihang
 * @Date 2018年9月6日 下午2:16:45
 */
@RestController
@RequestMapping("/commissionset")
public class CommissionsetController {
	
	@Autowired
	private ICommissionsetService ics;
	
	/**
	 * 设置待参会提醒
	 * yangweihang
	 * @Date 2018年9月6日 下午2:07:23
	 * @param cameetings 提醒间隔时间
	 * @param cameetingstimes  提醒次数
	 * @return
	 */
	@RequestMapping("/updatecameetings")
	public String updatecameetings(int cameetings,int cameetingstimes) {
		String str = "";
		int result = ics.updatecameetings(cameetings, cameetingstimes);
		if(result > 0) {
			str = "修改成功！";
		}else {
			str = "修改失败！";
		}
		return str;
	}
	
	/**
	 * 设置待办事项提醒
	 * yangweihang
	 * @Date 2018年9月6日 下午2:11:41
	 * @param cbacklog	提醒间隔时间
	 * @param cbacklogtimes	提醒次数
	 * @return
	 */
	@RequestMapping("/updatecbacklog")
	public String updatecbacklog(int cbacklog,int cbacklogtimes) {
		String str = "";
		int result = ics.updatecbacklog(cbacklog, cbacklogtimes);
		if(result > 0) {
			str = "修改成功！";
		}else {
			str = "修改失败！";
		}
		return str;
	}
}
