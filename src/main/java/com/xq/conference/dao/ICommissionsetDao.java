package com.xq.conference.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * 待办提醒设置
 * @ClassName ICommissionsetDao
 * @Author yangweihang
 * @Date 2018年9月6日 下午2:04:32
 */
@Mapper
public interface ICommissionsetDao {
	
	/**
	 * 设置待参会提醒
	 * yangweihang
	 * @Date 2018年9月6日 下午2:07:23
	 * @param cameetings 提醒间隔时间
	 * @param cameetingstimes  提醒次数
	 * @return
	 */
	@Update("update commissionset set cameetings=#{cameetings},cameetingstimes=#{cameetingstimes}")
	int updatecameetings(@Param("cameetings")int cameetings,@Param("cameetingstimes")int cameetingstimes);
	
	/**
	 * 设置待办事项提醒
	 * yangweihang
	 * @Date 2018年9月6日 下午2:11:41
	 * @param cbacklog	提醒间隔时间
	 * @param cbacklogtimes	提醒次数
	 * @return
	 */
	@Update("update commissionset set cbacklog=#{cbacklog},cbacklogtimes=#{cbacklogtimes}")
	int updatecbacklog(@Param("cbacklog")int cbacklog,@Param("cbacklogtimes")int cbacklogtimes);
}
