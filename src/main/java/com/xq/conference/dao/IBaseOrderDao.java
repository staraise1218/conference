package com.xq.conference.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.xq.conference.model.BaseOrder;
import com.xq.conference.model.OrderSimpleInfo;

/**
 * base订单dao
 * @author 川
 * 2018年9月7日10:13:40
 */
@Mapper
public interface IBaseOrderDao {

	
	/**
	 * 录入一个对象
	 * @param bo baseorder对象
	 * @return 是否成功
	 */
	@Insert("insert into baseorder (id,otime,otype,name,tel)values (#{bo.id},#{bo.otime},#{bo.otype},#{bo.name},#{bo.tel})")
	int insertOne(@Param("bo")BaseOrder bo);

	/**
	 * 查询该用户插入的最后一条id
	 * @return
	 */
	@Select("SELECT LAST_INSERT_ID()")
	int selectLastOid();

	/**
	 * 根据主订单id查询该用户的id
	 * @param oid 主订单id
	 * @return 该用户的Mid
	 */
	@Select("select id from baseorder where oid = #{oid}")
	String selectMidByOid(@Param("oid")Integer oid);

	@Select("select b.otakeorder as otakeorder,b.orderstate as orderstate,b.id as id,oc.ocid as ocid,oc.ocusename as uname ,r.rname as rname,oc.ocbegintime as ocbegintime,oc.ocendtime as ocendtime,"
			+ "b.opassstate as opassstate,(select round(sum(aonum*aoprice)) from appreciationgoodsorder as a "
			+ "group by a.ocid having oc.ocid = a.ocid) as price,oc.ocdockingtime as ocdockingtime "
			+ "from room as r left join ordercontent as oc on r.rid = oc.rid left join baseorder as b on oc.oid = b.oid where b.id = #{id} order by b.otime desc limit #{firstList},#{lastList}")
	List<OrderSimpleInfo> selectSimpleList(@Param("id")String id, @Param("firstList")Integer firstList, @Param("lastList")Integer lastList);

	@Update("update baseorder set opassstate = #{pass} where oid = #{oid}")
	int update(@Param("oid")int oid, @Param("pass")int pass);

	@Select("select * from baseorder where opassstate = #{orderstate} and oid in (select oid from ordercontent where rid in (select rid from room where rdepartment = #{deptCode} and hcode = #{hcode}))")
	List<BaseOrder> selectBackLog(@Param("orderstate")int orderstate, @Param("deptCode")String deptCode, @Param("hcode")String hcode);

	@Update("update baseorder set otakeorder = #{serverState} where oid = #{oid}")
	int updateServerState(@Param("oid")int oid, @Param("serverState")int serverState);

	@Update("update baseorder set orderstate = #{orderstate} where oid = (select oid from ordercontent where ocid = #{ocid})")
	int UpdateOrderState(@Param("ocid")int ocid, @Param("orderstate")int orderstate);

	@Select("select count(*) from ordercontent where oid in (select oid from baseorder where opassstate = #{i}) and rid in (select rid from room where hcode = #{hcode} and rdepartment = #{deptCode}) order by ocbegintime desc")
	Integer selectStateList(@Param("deptCode")String deptCode,@Param("hcode")String hcode, @Param("i")int i);

	/**
	 * 主管部门筛选信息查询列表
	 * @param page 当前页码
	 * @param pageSize 页码大小
	 * @param rid 会议室名称
	 * @param otime 订单时间
 	 * @param sid 服务人员名称
	 * @param opassstate 订单状态
	 * @param hcode 
	 * @param deptCode 
	 * @return 筛选过后的列表
	 */
	@Select("<script>"
			+ "select r.rname as rname, oc.ocid as ocid,b.oid as oid,oc.ocdockingtime as ocdockingtime,b.name as username,b.otime as otime,b.opassstate as opassstate,b.tel,so.seridArry as seridArry,m.cmgrade as cmgrade "
			+ "from ordercontent as oc left join room as r on r.rid = oc.rid "
			+ "left join baseorder as b on oc.oid = b.oid "
			+ "left join serviceorder as so on so.ocid = oc.ocid "
			+ "left join comment as m on oc.ocid = m.ocid "
			+ "where rdepartment =  #{deptCode} and "
			+ "<if test=\"rid != null and rid != ''\">"
			+ "r.rid = #{rid} and"
			+ "</if>"
			+ "<if test=\"datebegin != null\">"
			+ "b.otime between #{datebegin} and #{dateend} and"
			+ "</if>"
			+ "<if test=\"sid != null\">"
			+ "s.serid = #{sid} and"
			+ "</if>"
			+ "<if test=\"opassstate != null\">"
			+ "b.opassstate = #{opassstate} and "
			+ "</if>"
			+ " hcode = #{hcode} "
			+ " order by b.otime desc"
			+ " limit #{firstList} ,#{lastList}"
			+ "</script>")
	List<Map> selectApprovalScreen(@Param("firstList")Integer firstList,@Param("lastList")Integer lastList,@Param("rid")Integer rid,@Param("datebegin")Date datebegin,@Param("dateend")Date dateend,@Param("sid")Integer sid,@Param("opassstate")Integer opassstate, @Param("deptCode")String deptCode, @Param("hcode")String hcode);

	@Select("select count(*) from baseorder where id = #{id}")
	int selectCountAll(@Param("id")String id);

	
	@Select("select count(*) from ordercontent where oid in (select oid from baseorder where otakeorder = #{i}) and rid in (select rid from room where hcode = #{hcode} and rdepartment = #{deptCode})  order by ocbegintime desc")
	Integer selectStateListByService(@Param("deptCode")String deptCode, @Param("hcode")String hcode, @Param("i")int i);

	@Update("update baseorder set orderstate = 2 where oid = #{oid}")
	int updateMemberComplete(@Param("oid")Integer oid);

	@Update("update baseorder set otakeorder = #{otakeorder} where oid = #{oid}")
	int updateServiceComplete(@Param("oid")Integer oid, @Param("otakeorder")Integer otakeorder);
	@Select("<script>"
			+ "select r.rname as rname, b.oid as oid,oc.ocid as ocid,oc.ocdockingtime as ocdockingtime,b.name as username,b.otime as otime,b.opassstate as opassstate,b.tel,so.soid as soid,m.cmgrade as cmgrade "
			+ "from ordercontent as oc left join room as r on r.rid = oc.rid "
			+ "left join baseorder as b on oc.oid = b.oid "
			+ "left join serviceorder as so on so.soid = oc.serid "
			+ "left join comment as m on oc.ocid = m.ocid "
			+ "where rdepartment =  #{deptCode} and  "
			+ "<if test=\"rid != null and rid != ''\">"
			+ "r.rid = #{rid} and"
			+ "</if>"
			+ "<if test=\"datebegin != null\">"
			+ "b.otime between #{datebegin} and #{dateend} and"
			+ "</if>"
			+ "<if test=\"sid != null\">"
			+ "s.serid = #{sid} and"
			+ "</if>"
			+ "<if test=\"opassstate != null\">"
			+ "b.opassstate = #{opassstate} and"
			+ "</if>"
			+ " hcode = #{hcode} "
			+ "</script>")
	List<Map> selectApprovalScreenCount(@Param("rid")Integer rid,@Param("datebegin")Date datebegin,@Param("dateend")Date dateend,@Param("sid")Integer sid,@Param("opassstate")Integer opassstate, @Param("deptCode")String deptCode, @Param("hcode")String hcode);

	@Select("select name from baseorder where oid in (select oid from ordercontent where rid in (select rid from room where hcode = #{hcode} )) GROUP BY name")
	List<String> selectMemberNmeByHcode(@Param("hcode")String hcode);
	
}
