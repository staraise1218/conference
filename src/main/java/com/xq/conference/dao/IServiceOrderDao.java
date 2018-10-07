package com.xq.conference.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.xq.conference.model.ServiceOrder;


/**
 * @author mxc
 * 服务订单dao
 * 2018年9月24日11:17:54
 * version 1.0
 */

@Mapper
public interface IServiceOrderDao {

	@Select("select seridArry from serviceorder where soid = #{soid}")
	String selectSeridArry(@Param("soid")Integer soid);

	@Insert("insert into serviceorder (seridArry,ocid) values (#{sbString},#{ocid})")
	int insertServiceOrder(@Param("sbString")String sbString, @Param("ocid")Integer ocid);
	
	@Select("select seridArry from serviceorder where ocid = #{ocid}")
	String selectServicepeoByOcid(@Param("ocid")Integer ocid);

	@Select("select * from serviceorder where ocid = #{ocid}")
	ServiceOrder selectByOcid(@Param("ocid")Integer ocid);
	
}
