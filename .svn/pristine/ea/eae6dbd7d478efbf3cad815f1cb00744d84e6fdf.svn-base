package com.xq.conference.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.xq.conference.model.Basegoodsorder;

@Mapper
public interface IBasegoodDao {

	
	/**
	 * 批量录入
	 * @param blist
	 * @return
	 */
	@Insert( "<script>"
			+"INSERT INTO basegoodsorder(ocid, boname, bonum) VALUES "
			+ "<foreach collection ='blist' item='item' separator =','>"
			+ "(#{item.ocid},#{item.boname},#{item.bonum})"
			+ "</foreach >"
			+"</script>")
	
	int insert(@Param(value="blist")List<Basegoodsorder> blist);

	@Select("select * from basegoodsorder where ocid = #{ocid}")
	List<Basegoodsorder> selectListByOcid(@Param("ocid")int ocid);
	
}
