package com.xq.conference.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xq.conference.model.Comment;
import com.xq.conference.service.ICommentService;

@RestController
@RequestMapping("/comment")
public class CommentController {
	
	@Autowired
	private ICommentService icts;
	
	/**
	 * 根据名字查询该会议室的评论
	 * @param rname 会议室的名字
	 * @return 评论列表
	 */
	@RequestMapping(value="/selectCommentByRname",produces="application/json;charset=UTF-8")
	public List selectByComment(String rname) {
		List list = icts.selectByComment(rname);
		return list;
	}
	
	
	/**
	 * 查看服务统计
	 * @param rid 会议室id
	 * @param endTime 页面筛选的结束时间
	 * @param beginTime 页面筛选的开始时间
	 * @return 服务统计
	 */
	@RequestMapping(value="selectServiceStatistic",produces="application/json;charset=UTF-8")
	public Map selectByLocal(String beginTime,String endTime,Integer rid) {
		System.out.println("嘻嘻嘻嘻嘻");
		Map map = icts.selectByLocal(beginTime,endTime,rid);
		return map;
	}
	
	
	
	/**
	 * 插入一个评论内容
	 * @param ocid
	 * @return 是否插入成功
	 */
	@RequestMapping("/insertOneComment")
	public int insertOneComment(Comment co) {
		int state = icts.insertOneComment(co);
		return state;
	}
}
