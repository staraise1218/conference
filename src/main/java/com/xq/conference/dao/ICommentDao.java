package com.xq.conference.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.xq.conference.model.Comment;

@Mapper
public interface ICommentDao {
	
	
	@Select("select * from comment where rid = (select rid from room where hcode  = #{hcode} and rdepartment = #{deptCode} and rname = #{rname})")
	List<Comment> selectByComment(@Param("rname")String rname,@Param("hcode")String hcode, @Param("deptCode")String deptCode);

	@Select(  "<script>"
			+ " select * from comment where rid = (select rid from room where "
			+ "rid = #{rid} "
			+ "<if test =\"dateBegin != null\">"
			+ "and cmtime between #{dateBegin} and #{dateEnd})"
			+ "</if>"
			+ ")"
			+ "</script>"
			)
	List<Comment> selectByLocal(@Param("rid")Integer rid, @Param("dateBegin")Date dateBegin, @Param("dateEnd")Date dateEnd);

	/**
	 * 插入一个评论
	 * @param co 评论对象
	 * @return 是否完成
	 */
	@Insert("insert into comment (cmcontent,cmgrade,cmtime,ocid,rid,uname) values (#{co.cmcontent},#{co.cmgrade},#{co.cmtime},#{co.ocid},#{co.rid},#{co.uname})")
	int insertOneComment(@Param("co")Comment co);

	@Select("select *from comment where ocid = #{ocid}")
	Comment selectOneCommentByOcid(@Param("ocid")int ocid);
	
}
