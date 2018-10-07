package com.xq.conference.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * 操作设置
 * @ClassName IOperationsetDao
 * @Author yangweihang
 * @Date 2018年9月6日 上午10:41:56
 */
@Mapper
public interface IOperationsetDao {
	
	/**
	 * 修改预定编辑间隔时间
	 * yangweihang
	 * @Date 2018年9月6日 上午10:47:15
	 * @param bookeditor
	 * @return
	 */
	@Update("update operationset set bookeditor = #{bookeditor}")
	int updatebookeditor(@Param("bookeditor")int bookeditor);
	
	/**
	 * 修改默认评论好评时间
	 * yangweihang
	 * @Date 2018年9月6日 上午11:05:25
	 * @param commentdefault
	 * @return
	 */
	@Update("update operationset set commentdefault = #{commentdefault}")
	int updatecommentdefault(@Param("commentdefault")int commentdefault);
	
	/**
	 * 修改会议室开始前的N小时不能进行对接确认操作
	 * yangweihang
	 * @Date 2018年9月6日 上午11:06:53
	 * @param jointdefault
	 * @return
	 */
	@Update("update operationset set jointdefault = #{jointdefault}")
	int updatejointdefault(@Param("jointdefault")int jointdefault);
}
