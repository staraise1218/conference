package com.xq.conference.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.xq.conference.model.Cplatform;

/**
 * 台型dao
 * @author 川
 * 2018年9月7日13:28:59
 */
@Mapper
public interface ICplatformDao {

	/**
	 * 查询表中所有数据
	 * @return
	 */
	@Select("select * from cplatform")
	List<Cplatform> selectList();

	@Select("select cpic from cplatform where cid = #{cid}")
	String selectOnePic(@Param("cid")int cid);
	
}
