package com.xq.conference.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.xq.conference.model.OrderContentAndRoom;
import com.xq.conference.model.Room;

/**
 * 会议室dao
 * @author 川
 * 2018年9月6日09:49:53
 */
@Mapper
public interface IRoomDao {

	/**
	 * 在我放数据库插入一条会议室数据（数据录到我放数据库）
	 * @param room 会议室对象
	 * @return 录入是否成功（0.不成功 1.成功）
	 */
	@Insert("insert into room() valuse () ")
	int insertOneRoomMy(Room room);
	
	/**
	 * 新增一个会议室
	 * yangweihang
	 * @Date 2018年9月7日 上午9:00:41
	 * @param r
	 * @return
	 */
	@Insert("insert into room(rname,rdepartment,rbudingnubmer,rfloor,rhousenumber,rinterval,rlocation,rintroduce,rpic,hcode) values(#{r.rname},#{r.rdepartment},#{r.rbudingnubmer},#{r.rfloor},#{r.rhousenumber},#{r.rinterval},#{r.rlocation},#{r.rintroduce},#{r.rpic},#{r.hcode})")
	int insertRoom(@Param("r")Room r);
	
	/**
	 * 修改锁定未锁定
	 * yangweihang
	 * @Date 2018年9月7日 下午4:29:12
	 * @param rstate
	 * @return
	 */
	@Update("update room set rstate=#{rstate} where rname=#{rname}")
	int updaterstate(@Param("rstate")int rstate,@Param("rname")String rname);
	
	/**
	 * 编辑会议室
	 * yangweihang
	 * @Date 2018年9月7日 下午4:48:22
	 * @param r
	 * @return
	 */
	@Update("update room set rname=#{r.rname},rdepartment=#{r.rdepartment},rbudingnubmer=#{r.rbudingnubmer},rfloor=#{r.rfloor},rhousenumber=#{r.rhousenumber},rinterval=#{r.rinterval},rlocation=#{r.rlocation},rintroduce=#{r.rintroduce},rpic=#{r.rpic} where rid=#{r.rid}")
	int updateroom(@Param("r")Room r);
	
	/**
	 * 按会议室id查询
	 * yangweihang
	 * @Date 2018年9月7日 下午4:58:21
	 * @param rid
	 * @return
	 */
	@Select("select * from room where rid=#{rid}")
	Room selectroom(@Param("rid")int rid);
	
	/**
	 * 虚拟删除会议室
	 * yangweihang
	 * @Date 2018年9月7日 下午5:17:30
	 * @param rid
	 * @return
	 */
	@Update("update room set rstate=3 where rid=#{rid}")
	int deleteroom(@Param("rid")int rid);
	
	/**
	 * 时间间隔设置
	 * yangweihang
	 * @Date 2018年9月7日 下午9:04:05
	 * @param rinterval
	 * @return
	 */
	@Update("update room set rinterval=#{rinterval} where rid=#{rid}")
	int updaterinterval(@Param("rinterval")int rinterval,@Param("rid")int rid);
	
	/**
	 * 查询会议室
	 * yangweihang
	 * @Date 2018年9月7日 下午9:12:04
	 * @return
	 */
	@Select("select * from room")
	List<Room> selectByRoom();


	@Select("SELECT oc.*,r.* \r\n" + 
			"FROM ordercontent oc\r\n" + 
			"left join room r on oc.rid = r.rid\r\n" + 
			"WHERE oc.ocbegintime BETWEEN #{dateWeekBegin} AND #{dateWeekEnd} AND oc.rid = r.rid AND hcode = #{hospitalCode} AND r.rname = #{rname} AND r.rstate = 1 AND r.rbudingnubmer = #{rbudingnubmer} and r.rfloor = #{rfloor}")
	List<OrderContentAndRoom> selectOneRoomByLocalAndhcode(@Param("rbudingnubmer")int rbudingnubmer, @Param("rfloor")int rfloor, @Param("rname")String rname,
			@Param("hospitalCode")String hospitalCode, @Param("dateWeekBegin")Date dateWeekBegin, @Param("dateWeekEnd")Date dateWeekEnd);

	@Select("SELECT oc.*,r.* \r\n" + 
			"FROM ordercontent oc\r\n" + 
			"left join room r on oc.rid = r.rid\r\n" + 
			"WHERE oc.ocbegintime BETWEEN #{dateWeekBegin} AND #{dateWeekEnd} AND oc.rid = r.rid AND hcode = #{rname} AND r.rname = #{hospitalCode} AND r.rstate = 1") 
	List<OrderContentAndRoom> selectOneRoomAndOrderByRname(@Param("rname")String rname, @Param("hospitalCode")String hospitalCode, @Param("dateWeekBegin")Date dateWeekBegin,
			@Param("dateWeekEnd")Date dateWeekEnd);

	@Select("select count(*) ,r.rname as rname ,r.rid as rid from room as r left join ordercontent as oc on r.rid = oc.rid where r.hcode = #{hospitalCode} and rstate = 1 and r.rdepartment = #{deptCode} GROUP BY r.rid order by count(*) desc limit 5")
	List<Room> selectListByOften(@Param("hospitalCode")String hospitalCode, @Param("deptCode")String deptCode);

	@Select("select * from room where rdepartment = #{deptCode} and hcode = #{hospitalCode}")
	List<Room> selectRoomByHcodeAndDeptCode(@Param("hospitalCode")String hospitalCode, @Param("deptCode")String deptCode);

	/**
	 * 查询该间隔时间
	 * @param rid
	 * @return 分钟
	 */
	@Select("select rinterval from room where rid = #{rid}")
	Integer selectRintervalByRid(@Param("rid")int rid);
	
	@Select("select rstate from room where rid = #{rid}")
	int selectDelete(@Param("rid")int rid);

	
	@Select("<script>"
			+ "select * from room where "
			+ "hcode = #{hospitalCode} and"
			+ "<if test=\"rname != null and rname != ''\">"
			+ "rname = #{rname} and"
			+ "</if>"
			+ "<if test=\"rbudingnubmer != null\">"
			+ "rbudingnubmer = #{rbudingnubmer} and"
			+ "</if>"
			+ "<if test=\"rfloor != null\">" 
			+ "rfloor = #{rfloor} and "
			+ "</if>"
			+ " rstate != 3 "
			+ " order by rid desc "
			+ " limit #{firstList},#{lastList}"
			+ "</script>")
	List<Room> selectRoomByClientInsert(@Param("rname")String rname, @Param("rbudingnubmer")Integer rbudingnubmer, @Param("rfloor")Integer rfloor, @Param("hospitalCode")String hospitalCode, @Param("firstList")Integer firstList, @Param("lastList")Integer lastList);
	
	@Select("<script>"
			+ "select * from room where "
			+ "hcode = #{hospitalCode} and"
			+ "<if test=\"rname != null and rname != ''\">"
			+ "rname = #{rname} and"
			+ "</if>"
			+ "<if test=\"rbudingnubmer != null\">"
			+ "rbudingnubmer = #{rbudingnubmer} and"
			+ "</if>"
			+ "<if test=\"rfloor != null\">" 
			+ "rfloor = #{rfloor} and"
			+ "</if>"
			+ "1 = 1 "
			+ "</script>")
	List<Room> selectRoomByClientInsertcount(@Param("rname")String rname, @Param("rbudingnubmer")Integer rbudingnubmer, @Param("rfloor")Integer rfloor, @Param("hospitalCode")String hospitalCode);

	@Select("select * from room where hcode = #{hospitalCode}")
	List<Room> selectRoomByHcode(@Param("hospitalCode")String hospitalCode);

	@Select("select rname from room where rid = #{rid}")
	String selectRnameByRid(@Param("rid")int rid);

	@Select("select rid from room")
	List<Integer> selectListRid();
}
