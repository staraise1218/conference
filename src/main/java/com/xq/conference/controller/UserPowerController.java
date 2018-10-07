package com.xq.conference.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xq.conference.service.IUserPowerService;

/**
 * 用户权限
 * @ClassName UserPowerController
 * @Author yangweihang
 * @Date 2018年9月6日 下午6:42:55
 */
@RestController
@RequestMapping("/userpower")
public class UserPowerController {
	
	@Autowired
	private IUserPowerService iups;
	
	@Autowired
	private HttpServletRequest request;
	
	/**
	 * 修改用户权限
	 * yangweihang
	 * @Date 2018年9月6日 下午6:40:57
	 * @param pid
	 * @return
	 */
	@RequestMapping("/updatepid")
	public String updatepid(int pid,String username) {
		String str = "";
		int result = iups.updatepid(pid,username);
		if(result > 0) {
			str = "修改成功！";
		}else {
			str = "修改失败！";
		}
		return str;
	}
	
	/**
	 * 用户停用启用
	 * yangweihang
	 * @Date 2018年9月6日 下午7:23:12
	 * @param ustatus
	 * @param username
	 * @return
	 */
	@RequestMapping("/updateustatus")
	public String updateustatus(int ustatus,String username) {
		String str = "";
		int result = iups.updateustatus(ustatus,username);
		if(result > 0) {
			if(ustatus == 1) {
				str = "用户停用成功！";
			}else if(ustatus == 2){
				str = "用户启用成功！";
			}
		}else {
			str = "修改失败！";
		}
		return str;
	}
}
