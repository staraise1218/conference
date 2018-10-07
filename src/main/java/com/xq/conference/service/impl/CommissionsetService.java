package com.xq.conference.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xq.conference.dao.ICommissionsetDao;
import com.xq.conference.service.ICommissionsetService;

/**
 * 待办提醒设置
 * @ClassName CommissionsetService
 * @Author yangweihang
 * @Date 2018年9月6日 下午2:14:37
 */
@Service("ics")
public class CommissionsetService implements ICommissionsetService {
	
	@Autowired
	private ICommissionsetDao icdao;
	
	/**
	 * 设置待参会提醒
	 * yangweihang
	 * @Date 2018年9月6日 下午2:07:23
	 * @param cameetings 提醒间隔时间
	 * @param cameetingstimes  提醒次数
	 * @return
	 */
	public int updatecameetings(int cameetings,int cameetingstimes) {
		return icdao.updatecameetings(cameetings, cameetingstimes);
	}
	
	/**
	 * 设置待办事项提醒
	 * yangweihang
	 * @Date 2018年9月6日 下午2:11:41
	 * @param cbacklog	提醒间隔时间
	 * @param cbacklogtimes	提醒次数
	 * @return
	 */
	public int updatecbacklog(int cbacklog,int cbacklogtimes) {
		return icdao.updatecbacklog(cbacklog, cbacklogtimes);
	}
}
