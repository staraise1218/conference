package com.xq.conference.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.xq.conference.model.AppreciationgoodsOrder;

/**
 * 增值物品dao
 * @author mxc
 * 2018年9月8日10:31:42
 *
 */
@Mapper
public interface IAppreciationgoodsOrderDao {

	@Insert( "<script>"
			+"INSERT INTO appreciationgoodsorder(ocid, aoname, aonum,aoprice) VALUES "
			+ "<foreach collection ='alist' item='item' separator =','>"
			+ "(#{item.ocid},#{item.aoname},#{item.aonum},#{item.aoprice})"
			+ "</foreach >"
			+"</script>")
	int insert(@Param(value="alist")List<AppreciationgoodsOrder> alist);

	@Select("select * from appreciationgoodsorder where ocid = #{ocid}")
	List<AppreciationgoodsOrder> selectListByOcid(@Param("ocid")int ocid);
	
}
