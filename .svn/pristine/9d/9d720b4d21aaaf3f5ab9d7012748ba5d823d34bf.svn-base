package com.xq.conference.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.xq.conference.model.MessageContent;

/**
 * 
 * 消息主体内容dao
 * @author 川
 * 2018年9月12日13:52:20
 */ 
@Mapper
public interface IMessageContentDao {

	@Insert("insert into messagecontent (mtid,ocid,mtcontent) values (1,#{ocid},#{message})")
	int insert(@Param("message")String message, @Param("ocid")int ocid);

	@Insert("insert into messagecontent (mtid,ocid,mtcontent) values (1,(select ocid from ordercontent where oid = #{oid}),#{message})")
	int insertByOid(@Param("message")String message, @Param("oid")int oid);

	@Select("select * from messagecontent where ocid in (select ocid from ordercontent where oid in (select oid from baseorder where id = #{mid})) and (mtid = 3 or mtid = 2) order by mcid desc limit 5")
	List<MessageContent> selectMessageByMember(@Param("mid")String mid);

	@Select("select * from messagecontent where ocid in (select ocid from ordercontent where rid in (select rid from room where rdepartment = #{deptCode} and hcode = #{hcode}) and oid in (select oid from baseorder where opassstate = 3)) and mtid = 4 order by mcid desc limit 5")
	List<MessageContent> selectMessageByApproval(@Param("deptCode")String deptCode, @Param("hcode")String hcode);

	@Select("select * from messagecontent where ocid in (select ocid from ordercontent where rid in (select rid from room where hcode = #{hcode}) and oid in (select oid from baseorder where otakeorder = 1)) and mtid = 5 order by mcid  limit 5")
	List<MessageContent> selectMessageByService(@Param("hcode")String hcode);

	@Insert("insert into messagecontent (mtid,ocid,mtcontent) values (#{i},#{lastOcid},(select template from messagetype where mtid = #{i}))")
	int insertByAll(@Param("lastOcid")int lastOcid, @Param("i")int i);

	@Insert("insert into messagecontent (mtid,ocid,mtcontent) values (#{i},(select ocid from ordercontent where oid = #{oid}),(select template from messagetype where mtid = #{i}))")
	int insertByOidPlus(@Param("message")String message, @Param("oid")int oid, @Param("i")int i);

	@Insert("insert into messagecontent (mtid,ocid,mtcontent) values (#{i},(select ocid from ordercontent where oid = #{oid}),(select template from messagetype where mtid = #{i}))")
	int insertByAllOid(@Param("oid")int oid, @Param("i")int i);

	@Select("select * from messagecontent where readState = 1 and ocid in (select ocid from ordercontent where oid in (select oid from baseorder where id = #{mid})) and (mtid = 7 or mtid = 8) order by mcid desc limit 5")
	List<MessageContent> selectInformMessageByMember(@Param("mid")String mid);

	@Select("select * from messagecontent where readState = 1 and  ocid in (select ocid from ordercontent where oid in (select oid from baseorder where id = #{mid})) and (mtid = 4) order by mcid desc limit 5")
	List<MessageContent> selectInformMessageByApproval(@Param("mid")String mid);

	@Select("select * from messagecontent where readState = 1 and  ocid in (select ocid from ordercontent where oid in (select oid from baseorder where id = #{mid})) and (mtid = 5) order by mcid desc limit 5")
	List<MessageContent> selectInformMessageByService(@Param("mid")String mid);
	
	@Update("update messagecontent set readState = 2 where mcid = #{mcid}")
	void updateReadState(@Param("mcid")Integer mcid);
}
