package com.xq.conference.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xq.conference.dao.IOperationsetDao;
import com.xq.conference.service.IOperationsetService;

/**
 * 操作设置
 * @ClassName OperationsetService
 * @Author yangweihang
 * @Date 2018年9月6日 上午11:27:39
 */
@Service("ios")
public class OperationsetService implements IOperationsetService {
	
	@Autowired
	private IOperationsetDao iodao;
	
	/**
	 * 修改预定编辑间隔时间
	 * yangweihang
	 * @Date 2018年9月6日 上午10:47:15
	 * @param bookeditor
	 * @return
	 */
	public int updatebookeditor(int bookeditor) {
		return iodao.updatebookeditor(bookeditor);
	}
	
	/**
	 * 修改默认评论好评时间
	 * yangweihang
	 * @Date 2018年9月6日 上午11:05:25
	 * @param commentdefault
	 * @return
	 */
	public int updatecommentdefault(int commentdefault) {
		return iodao.updatecommentdefault(commentdefault);
	}
	
	/**
	 * 修改会议室开始前的N小时不能进行对接确认操作
	 * yangweihang
	 * @Date 2018年9月6日 上午11:06:53
	 * @param jointdefault
	 * @return
	 */
	public int updatejointdefault(int jointdefault) {
		return iodao.updatejointdefault(jointdefault);
	}
}
