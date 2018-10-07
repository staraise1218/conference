package com.xq.conference.service;

import java.util.List;
import java.util.Map;

import com.xq.conference.model.OrderContentAndRoom;
import com.xq.conference.model.Room;

/**
 * 房间服务
 * @author 川
 * 2018年9月5日16:49:49
 */
public interface IRoomService {

	/**
	 * 向对方数据库插入一条会议室信息
	 * @param unitCode 主管单位编码
	 * @param hospitalCode 医院编码
	 * @param id 区域ID 
	 * @param spaceCode 空间编码
	 * @return 调用接口是否成功
	 * @throws Exception 
	 */
	int insertOneRoomToOther(String unitCode, String hospitalCode, String id, String spaceCode) throws Exception;

	/**
	 * 创建一个会议室（数据录到我放数据库）
	 * @param room 会议室对象
	 * @return 录入是否成功（0.不成功 1.成功）
	 */
	int insertOneRoomMy(Room room);

	/**
	 * 通过他们的接口获取该医院的所有部门信息
	 * @return 该医院的部门信息列表
	 */
	List getDepartmentListByOther();

	/**
	 * 根据该医院的编码和主管单位编码来获取整个医院大区域名称
	 * @return 该区域info
	 */
	String getBigLocalListByOther();
	
	/**
	 * 新增一个会议室
	 * yangweihang
	 * @Date 2018年9月7日 上午9:00:41
	 * @param r
	 * @return
	 */
	int insertRoom(Room r);
	
	/**
	 * 修改锁定未锁定
	 * yangweihang
	 * @Date 2018年9月7日 下午4:29:12
	 * @param rstate
	 * @return
	 */
	int updaterstate(int rstate,String rname);
	
	/**
	 * 编辑会议室
	 * yangweihang
	 * @Date 2018年9月7日 下午4:48:22
	 * @param r
	 * @return
	 */
	int updateroom(Room r);
	
	/**
	 * 按会议室id查询
	 * yangweihang
	 * @Date 2018年9月7日 下午4:58:21
	 * @param rid
	 * @return
	 */
	Room selectroom(int rid);
	
	/**
	 * 虚拟删除会议室
	 * yangweihang
	 * @Date 2018年9月7日 下午5:17:30
	 * @param rid
	 * @return
	 */
	int deleteroom(int rid);
	
	/**
	 * 时间间隔设置
	 * yangweihang
	 * @Date 2018年9月7日 下午9:04:05
	 * @param rinterval
	 * @return
	 */
	int updaterinterval(int rinterval,int rid);
	
	/**
	 * 查询会议室
	 * yangweihang
	 * @Date 2018年9月7日 下午9:12:04
	 * @return
	 */
	List<Room> selectByRoom();

	/**
	 * 根据会议室的楼号、楼层、门牌号、医院编码确定一个会议室
	 * @param rbudingnubmer 楼号
	 * @param rfloor 楼层
	 * @param rname 会议室名称
	 * @param weekNum 当前日期的第几周
	 * @param dateAssign 
	 * @return 一个房间
	 */
	List selectOneRoomByLocal(int rbudingnubmer, int rfloor, String rname, int weekNum, String dateAssign);

	/**
	 * 根据会议室的名医院编码确定一个会议室的订单列表
	 * @param rname 会议室名
	 * @param weekNum 当前日期的第几周
	 * @param dateAssign 
	 * @return 一个房间指定周的订单列表
	 */
	List<OrderContentAndRoom> selectOneRoomAndOrderByRname(String rname, int weekNum, String dateAssign);

	/**
	 * 查询该医院所有会议室的常用会议室
	 * @return 会议室列表
	 */
	List selectListByOften();

	/**
	 * 根据医院编码和部门编码查询会议室列表
	 * @return 会议室列表
	 */
	List selectRoomByHcodeAndDeptCode();

	/**
	 * 根据用户的筛选来选出符合要求的会议室
	 * @return 符合要求会议室的列表
	 * @param rname 会议时间名称
	 * @param rbudingnubmer 楼号
	 * @param rfloor 楼层
	 * @param pageSize 
	 * @param page 
	 */
	Map selectRoomByClientInsert(String rname, Integer rbudingnubmer, Integer rfloor, Integer page, Integer pageSize);

	List selectRoomByHcode();
	
	Integer selectRoomByClientInsertcount(String rname, Integer rbudingnubmer, Integer rfloor, String hospitalCode);

	List selectDeptNameLitsByHcode();
}
