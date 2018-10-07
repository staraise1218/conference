package com.xq.conference.service;

import java.util.List;
import java.util.Map;

import com.xq.conference.model.Comment;


public interface ICommentService {
	/**
	 * 根据该会议室的名称查询该会议室的评论详情
	 * @param rname 会议室名称
	 * @return 评论集合
	 */
	List selectByComment(String rname);


	/**
	 * 查看服务统计
	 * @param rid 会议室id
	 * @param endTime 页面筛选的结束时间
	 * @param beginTime 页面筛选的开始时间
	 * @return 服务统计
	 */
	Map selectByLocal(String beginTime,String endTime,Integer rid);


	/**
	 * 插入一条评论内容
	 * @param co 评论对象
	 * @return 是否成功
	 */
	int insertOneComment(Comment co);


	/**
	 * 插入一个默认好评
	 * @return
	 */
	int insertDefaultConmment();
}
