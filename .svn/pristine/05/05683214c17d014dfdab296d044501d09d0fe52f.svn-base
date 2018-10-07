package com.xq.conference.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.xq.conference.model.Servicepeo;

/**
 * 服务人员接口
 * @author 川
 * 2018年9月11日15:52:55
 */
@Mapper
public interface IServicepeoDao {

	@Select("select * from servicepeo")
	List<Servicepeo> selectList();

	@Select("select sername from servicepeo where serid = #{integer}")
	String selectNmaeBySid(@Param("integer")Integer integer);

	@Select("select * from servicepeo where serid = #{integer}")
	Servicepeo selectone(@Param("integer")Integer integer);

	@Select("select * from servicepeo where id = #{string}")
	Servicepeo selectSeridExistByMid(@Param("string")String string);

	@Select("insert into servicepeo (id) values (#{string})")	
	void insertOne(@Param("string")String string);

	@Select("SELECT LAST_INSERT_ID()")
	int selectLastSerid();

	@Select("select id from servicepeo where serid = #{integer}")
	String selectIdBySerid(@Param("integer")Integer integer);
	
}
