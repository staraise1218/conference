package com.xq.conference.service;

/**
 * 操作设置
 * @ClassName IOperationsetService
 * @Author yangweihang
 * @Date 2018年9月6日 上午11:26:38
 */
public interface IOperationsetService {
	/**
	 * 修改预定编辑间隔时间
	 * yangweihang
	 * @Date 2018年9月6日 上午10:47:15
	 * @param bookeditor
	 * @return
	 */
	int updatebookeditor(int bookeditor);
	
	/**
	 * 修改默认评论好评时间
	 * yangweihang
	 * @Date 2018年9月6日 上午11:05:25
	 * @param commentdefault
	 * @return
	 */
	int updatecommentdefault(int commentdefault);
	
	/**
	 * 修改会议室开始前的N小时不能进行对接确认操作
	 * yangweihang
	 * @Date 2018年9月6日 上午11:06:53
	 * @param jointdefault
	 * @return
	 */
	int updatejointdefault(int jointdefault);
}
