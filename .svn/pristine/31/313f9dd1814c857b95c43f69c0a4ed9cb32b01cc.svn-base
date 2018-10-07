package com.xq.conference.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.xq.conference.model.OrderContentAndRoom;
import com.xq.conference.model.Ordercontent;

/**
 * 订单主体dao
 * @author 川
 * 2018年9月7日10:14:14
 */
@Mapper
public interface IOrderContentDao {

	@Insert("insert into ordercontent(oid,rid,ocnum,ocbegintime,ocendtime,"
			+ "cid,ocagenda,ocusenum,ocarrangement,ocsourcename,ocsourcephone,"
			+ "ocdockingstate,ocdockingtime,ocmaterialsname,ocmaterialsphone,ocusename,"
			+ "ocusephone,ocreservename,ocreservephone,ocbigcarnum,ocsmallnum,ocprintnum,"
			+ "ocplatform,ocshowplatform,ocshowshelf,ocpower,ocpeoname,ocaccessory,ocremark) "
			+ "values (#{oc.oid},#{oc.rid},#{oc.ocnum},#{oc.ocbegintime},#{oc.ocendtime},"
			+ "#{oc.cid},#{oc.ocagenda},#{oc.ocusenum},#{oc.ocarrangement},#{oc.ocsourcename},"
			+ "#{oc.ocsourcephone},#{oc.ocdockingstate},#{oc.ocdockingtime},#{oc.ocmaterialsname},"
			+ "#{oc.ocmaterialsphone},#{oc.ocusename},#{oc.ocusephone},#{oc.ocreservename},"
			+ "#{oc.ocreservephone},#{oc.ocbigcarnum},#{oc.ocsmallnum},#{oc.ocprintnum},"
			+ "#{oc.ocplatform},#{oc.ocshowplatform},#{oc.ocshowshelf},#{oc.ocpower},#{oc.ocpeoname},"
			+ "#{oc.ocaccessory},#{oc.ocremark})")
	int insetOne(@Param("oc")Ordercontent oc);

	/**
	 * 查询该用户插入的最后一条id
	 * @return
	 */
	@Select("SELECT LAST_INSERT_ID()")
	int selectLastOcid();

	/**
	 * 根据医院编码和日期和不是以取消订单做限制条件查询订单详细内容
	 * @param hcode 医院编码
	 * @param dateWeekBegin 时间开始
	 * @param dateWeekEnd  时间结束
	 * @param rid 房间id
	 * @return 新的OrderContentAndRoom模型
	 */
	@Select("select * \r\n" + 
			"from ordercontent as oc \r\n" + 
			"left join room as r on oc.rid = r.rid \r\n" + 
			"left join baseorder as b on oc.oid = b.oid \r\n" + 
			"where oc.ocbegintime BETWEEN #{dateWeekBegin} AND #{dateWeekEnd}  AND oc.rid = r.rid AND hcode = #{hcode} and r.rid = #{rid} and b.orderstate <> 0")
	List<OrderContentAndRoom> selectListByDateAndHcode(@Param("hcode")String hcode, @Param("dateWeekBegin")Date dateWeekBegin,
			@Param("dateWeekEnd")Date dateWeekEnd, @Param("rid")int rid);

	@Select("select * from ordercontent where ocid = #{rid}")
	Ordercontent selectOneByRid(@Param("rid")int rid);

	@Select("select ocaccessory from ordercontent where ocid = #{ocid}")
	String selectOcaccessoryPath(@Param("ocid")int ocid);

	@Select("select * from baseorder where id = #{id}")
	List selectListByMid(@Param("id")String id);

	/**
	 * 查询开始时间是否冲突
	 * @param ocbegintime
	 * @param rid
	 * @return
	 */
	@Select("select round((UNIX_TIMESTAMP(#{ocbegintime})-UNIX_TIMESTAMP(ocendtime))/60) from ordercontent where ocendtime<=#{ocbegintime} and rid = #{rid} order by ocendtime DESC limit 1")
	Integer selectChongtuBegin(@Param("ocbegintime")Date ocbegintime, @Param("rid")int rid);

	/**
	 * 查看结束时间是否冲突
	 * @param ocendtime
	 * @param rid
	 * @return
	 */
	@Select("select round((UNIX_TIMESTAMP(ocbegintime)-UNIX_TIMESTAMP(#{ocendtime}))/60) from ordercontent where ocbegintime>=#{ocendtime} and rid = #{rid} order by ocbegintime limit 1")
	Integer selectChongtuEnd(@Param("ocendtime")Date ocendtime, @Param("rid")int rid);

	
	/**
	 * 根据订单主键查询订单主体内容
	 * @param ocid
	 * @return
	 */
	@Select("select * from ordercontent where ocid = #{ocid}")
	Ordercontent selectOneByOcid(@Param("ocid")int ocid);

	@Select("select * from ordercontent as oc left join room as r on r.rid = oc.rid left join baseorder as b on b.oid = oc.oid where r.rdepartment = #{deptCode} and b.opassstate = 1 and r.hcode = #{hcode} order by b.otime desc limit 2")
	List<Map> selectListByCheckAndDeftOne(@Param("deptCode")String deptCode, @Param("hcode")String hcode);
	
	@Select("select * from ordercontent where rid in (select rid from room where rdepartment = #{deptCode} and hcode = #{hcode})")
	List<Ordercontent> selectTime(@Param("deptCode")String deptCode, @Param("hcode")String hcode);

	/**
	 * 审批部门审批列表
	 * @param deptCode 部门编码
	 * @param hcode 医院编码
	 * @param pageSize 一页多少行
	 * @param page 当前页码
	 * @return 返回列表基本信息
	 */
	@Select("select r.rname as rname, oc.ocid as ocid,oc.ocdockingtime as ocdockingtime,b.name as username,b.otime as otime,b.opassstate as opassstate,b.tel,s.sername as sername,m.cmgrade as cmgrade\r\n" + 
			"from ordercontent as oc left join room as r on r.rid = oc.rid \r\n" + 
			"left join baseorder as b on oc.oid = b.oid\r\n" + 
			"left join servicepeo as s on s.serid = oc.serid\r\n" + 
			"left join comment as m on oc.ocid = m.ocid\r\n" + 
			"where r.rdepartment = #{deptCode} and r.hcode = #{hcode} limit #{firstList},#{lastList}")
	List<Map> selectSimpleListByShenPi(@Param("deptCode")String deptCode, @Param("hcode")String hcode, @Param("firstList")Integer firstList,@Param("lastList")Integer lastList);

	
	@Select("<script>"+
			"select b.oid as oid ,b.id as id,oc.ocid as ocid,r.rname as rname,b.otime as otime,b.tel,m.cmgrade,b.name as name,b.otakeorder,oc.ocdockingtime " + 
			"from room as r left join ordercontent as oc on r.rid = oc.rid " + 
			"left join baseorder as b on oc.oid = b.oid " + 
			"left join comment as m on oc.ocid = m.ocid " + 
			"where rdepartment =  #{deptCode} and  "
			+ "<if test=\"rid != null\">"
			+ "r.rid = #{rid} and "
			+ "</if>"
			+ "<if test=\"datebegin != null\">"
			+ "b.otime between #{datebegin} and #{dateend} and "
			+ "</if>"
			+ "<if test=\"otakeorder != null\">"
			+ "b.otakeorder = #{otakeorder} and "
			+ "</if>"
			+ "<if test=\"name != null and name != ''\">"
			+ "b.name = #{name} and "
			+ "</if>"
			+ "b.opassstate = 2 and "
			+ "b.orderstate != 0 and "
			+ " hcode = #{hcode} "
			+ " order by b.otime desc"
			+ " limit #{firstList},#{lastList}"
			+ "</script>"
			)
	List<Map> selectSimpleListByFuWu(@Param("firstList")int firstList, @Param("lastList")int lastList,@Param("rid")Integer rid, @Param("datebegin")Date datebegin, @Param("dateend")Date dateend, @Param("otakeorder")Integer otakeorder, @Param("name")String name, @Param("deptCode")String deptCode, @Param("hcode")String hcode);
	
	@Update("<script>"
			+"update ordercontent "
			+ "<trim prefix=\"set\" suffixOverrides=\",\">  "
			+"<if test=\"oc.ocsourcename != null and oc.ocsourcename != ''\">"
			+ "ocsourcename = #{oc.ocsourcename},"
			+ "</if>"
			+"<if test=\"oc.ocreservename != null and oc.ocreservename != ''\">"
			+ "ocreservename = #{oc.ocreservename},"
			+ "</if>"
			+"<if test=\"oc.ocreservephone != null and oc.ocreservephone != ''\">"
			+ "ocreservephone = #{oc.ocreservephone},"
			+ "</if>"
			+"<if test=\"oc.ocusename != null and oc.ocusename != ''\">"
			+ "ocusename = #{oc.ocusename},"
			+ "</if>"
			+"<if test=\"oc.ocusephone != null and oc.ocusephone != ''\">"
			+ "ocusephone = #{oc.ocusephone},"
			+ "</if>"
			+"<if test=\"oc.cid != null\">"
			+ "cid = #{oc.cid},"
			+ "</if>"
			+"<if test=\"oc.ocdockingstate != null\">"
			+ "ocdockingstate = #{oc.ocdockingstate},"
			+ "</if>"
			+"<if test=\"oc.ocdockingtime != null\">"
			+ "ocdockingtime = #{oc.ocdockingtime},"
			+ "</if>"
			+"<if test=\"oc.ocmaterialsname != null and oc.ocmaterialsname != ''\">"
			+ "ocmaterialsname = #{oc.ocmaterialsname},"
			+ "</if>"
			+"<if test=\"oc.ocmaterialsphone != null and oc.ocmaterialsphone != ''\">"
			+ "ocmaterialsphone = #{oc.ocmaterialsphone},"
			+ "</if>"
			+"<if test=\"oc.ocbigcarnum != null\">"
			+ "ocbigcarnum = #{oc.ocbigcarnum},"
			+ "</if>"
			+"<if test=\"oc.ocsmallnum != null\">"
			+ "ocsmallnum = #{oc.ocsmallnum},"
			+ "</if>"
			+"<if test=\"oc.ocshowplatform != null and oc.ocshowplatform != ''\">"
			+ "ocshowplatform = #{oc.ocshowplatform},"
			+ "</if>"
			+"<if test=\"oc.ocshowshelf != null and oc.ocshowshelf != ''\">"
			+ "ocshowshelf = #{oc.ocshowshelf},"
			+ "</if>"
			+"<if test=\"oc.ocpower != null and oc.ocpower != ''\">"
			+ "ocpower = #{oc.ocpower},"
			+ "</if>"
			+"<if test=\"oc.ocaccessory != null and oc.ocaccessory != ''\">"
			+ "ocaccessory = #{oc.ocaccessory},"
			+ "</if>"
			+"<if test=\"oc.ocremark != null and oc.ocremark != ''\">"
			+ "ocremark = #{oc.ocremark},"
			+ "</if>"
			+"<if test=\"oc.serid != null\">"
			+ "serid = #{oc.serid},"
			+ "</if>"
			+ "</trim>"
			+ "where ocid = #{oc.ocid}"
			+"</script>"
			)
	int updateOrder(@Param("oc")Ordercontent oc);
	@Select("<script>"+
			"select b.id as id,oc.ocid as ocid,r.rname as rname,b.otime as otime,b.tel,m.cmgrade,b.name as name,b.otakeorder,oc.ocdockingtime " + 
			"from room as r left join ordercontent as oc on r.rid = oc.rid " + 
			"left join baseorder as b on oc.oid = b.oid " + 
			"left join comment as m on oc.ocid = m.ocid " + 
			"where rdepartment =  #{deptCode} and   "
			+ "<if test=\"rid != null\">"
			+ "r.rid = #{rid} and "
			+ "</if>"
			+ "<if test=\"datebegin != null\">"
			+ "b.otime between #{datebegin} and #{dateend} and "
			+ "</if>"
			+ "<if test=\"otakeorder != null\">"
			+ "b.otakeorder = #{otakeorder} and "
			+ "</if>"
			+ "<if test=\"name != null and name != ''\">"
			+ " b.name = #{name} and "
			+ "</if>"
			+ " b.opassstate = 2 and "
			+ " b.orderstate != 0 and "
			+ " hcode = #{hcode} "
			+ "</script>"
			)
	List<Map> selectSimpleListByFuWucount(@Param("rid")Integer rid, @Param("datebegin")Date datebegin, @Param("dateend")Date dateend, @Param("otakeorder")Integer otakeorder, @Param("name")String name, @Param("deptCode")String deptCode, @Param("hcode")String hcode);

	@Select("select oc.ocid as ocid,b.otakeorder as otakeorder ,oc.ocbegintime as ocbegintime,oc.ocendtime as ocendtime,r.rname as rname,b.name as name,b.tel as tel from ordercontent  as oc left join room as r on r.rid = oc.rid left join baseorder as b on b.oid = oc.oid where r.hcode = #{hcode} and b.otakeorder = 1 order by b.otime desc limit 2")
	List<Map> selectSixListForServiceOne(@Param("hcode")String hcode);

	@Select("select rname from room where rid = (select rid from ordercontent where ocid = #{ocid})")
	String selectRnameByOcid(@Param("ocid")int ocid);

	@Update("update ordercontent set ocsourcephone = '2' where ocid = #{ocid}")
	int updateButtState(@Param("ocid")Integer ocid);

	@Select("select * from ordercontent as oc left join room as r on r.rid = oc.rid left join baseorder as b on b.oid = oc.oid where r.rdepartment = #{deptCode} and b.opassstate = 2 and r.hcode = #{hcode} order by b.otime desc limit 2")
	List<Map> selectListByCheckAndDeftTwo(@Param("deptCode")String deptCode, @Param("hcode")String hcode);
	
	@Select("select * from ordercontent as oc left join room as r on r.rid = oc.rid left join baseorder as b on b.oid = oc.oid where r.rdepartment = #{deptCode} and b.opassstate = 3 and r.hcode = #{hcode} order by b.otime desc limit 2")
	List<Map> selectListByCheckAndDeftThree(@Param("deptCode")String deptCode, @Param("hcode")String hcode);

	@Select("select oc.ocid as ocid,b.otakeorder as otakeorder ,oc.ocbegintime as ocbegintime,oc.ocendtime as ocendtime,r.rname as rname,b.name as name,b.tel as tel from ordercontent  as oc left join room as r on r.rid = oc.rid left join baseorder as b on b.oid = oc.oid where r.hcode = #{hcode} and b.otakeorder = 2 order by b.otime desc limit 2")
	List<Map> selectSixListForServiceTwo(@Param("hcode")String hcode);

	@Select("select oc.ocid as ocid,b.otakeorder as otakeorder ,oc.ocbegintime as ocbegintime,oc.ocendtime as ocendtime,r.rname as rname,b.name as name,b.tel as tel from ordercontent  as oc left join room as r on r.rid = oc.rid left join baseorder as b on b.oid = oc.oid where r.hcode = #{hcode} and b.otakeorder = 3 order by b.otime desc limit 2")
	List<Map> selectSixListForServiceThree(@Param("hcode")String hcode);

	@Select("select rid from ordercontent where ocbegintime > #{clientDateBegin} and ocendtime < #{clientDateEnd} and rid in (select rid from room where hcode = #{hcode}) GROUP BY rid")
	List<Integer> selectRidByDateAndHcode(@Param("clientDateBegin")Date clientDateBegin, @Param("clientDateEnd")Date clientDateEnd, @Param("hcode")String hcode);

	@Select("select count(*) from ordercontent where ocbegintime > #{clientDateBegin} and ocendtime < #{clientDateEnd} and rid = #{rid}")
	int selectSumOrderByRid(@Param("rid")int rid, @Param("clientDateBegin")Date clientDateBegin, @Param("clientDateEnd")Date clientDateEnd);
	
}
