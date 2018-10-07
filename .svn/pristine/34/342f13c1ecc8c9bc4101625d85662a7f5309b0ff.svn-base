package com.xq.conference.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.xq.conference.model.AppreciationgoodsOrder;
import com.xq.conference.model.Basegoodsorder;
import com.xq.conference.model.Ordercontent;

/**
 * 订单服务接口
 * @author 川
 * 2018年9月7日10:07:52
 */
public interface IOrderService {

	/**
	 * 页面传来 日期、会议室id、上传附件（采用时间MD5+文件名）---预订会议室
	 * @param oc OrderContent对象
	 * @param otype 是否重复预定(1.不重复 2.重复)
	 * @param unit 重复的单位（1.天 2.周 3.月）
	 * @param num 数量
	 * @return -1:该会议室正在被编辑 1：预定成功 0:预定冲突
	 */
	int insertorder(Ordercontent oc, int otype, int unit, int num, List<Basegoodsorder> blist, List<AppreciationgoodsOrder> alist);


	/**
	 * 根据id查询该会议室用用订单（通用方法）
	 * 分周查询 以当前时间所在周为基准、前台判断是不是自己用isMe字段（1.是自己）
	 * @param weekNum 下、上几周
	 * @param type 0：以当前时间为基准 1：以用户自定义时间为基准
	 * @param dateAssign 用户自定义时间点 如果不是用户自定时间就是空字符串
	 * @return 临时模型对象
	 * @throws Exception
	 */
	List selectListByNowWeek(int rid,String dateAssign) throws Exception;


	/**
	 * 根据订单主键查询定单详情
	 * @param rid 订单id
	 * @return 一个订单主体内容对象
	 */
	Ordercontent selectOneByRid(int rid);


	/**
	 * 根据订单详细表的id下载该上传的附件
	 * @param ocid 详细表的id
	 * return 0.没有附件 1.下载成功
	 */
	String selectOcaccessoryPath(int ocid);


	/**
	 * 根据mid查询该用户所有的订单
	 * @return 订单列表
	 */
	List selectListByMid();


	/**
	 * 判断用户的日期是否冲突（0：代表预订当前时间之前的的时间段（无意义）1.有冲突 2.可以预定）
	 * @param begintime 用户输入的开始时间
	 * @param endtime 用户输入的结束时间
	 * @param rid 房间id
	 * @return 是否可以预定
	 */
	int selectChongtu(Date ocbegintime, Date ocendtime, int rid);

	/**
	 * 根据用户id查询列表------用户界面的列表
	 * @param pagenum 第几页
	 * @param pageSize 一页多少条
	 * @return list 普通用户订单列表（基础信息）
	 */
	Map selectSimpleList(Integer pageSize, Integer pagenum);


	/**
	 * 修改订单状态
	 * @param oid 主订单id
	 * @param pass 是否通过
	 * @param message 
	 * @return 审批是否完成
	 */
	int update(int oid, int pass, String message);


	/**
	 * 订单详情列表
	 * @param ocid 订单主题内容id
	 * @return 一个对象集合 里面有订单主体对象 + 基础会议物品 + 增值会议物品 + 我的评价（如果评价了显示 不评价不显示）
	 */
	List selectOneInfo(int ocid);


	/**
	 * 查询该部门下的前六条（目前是）预订信息
	 * @return 预订信息集合
	 */
	Map selectListByCheckAndDeft();


	/**
	 * @param orderstate 订单的状态 (1.未通过  2.通过3.待审批)
	 * 根据状态查询订单列表（参数在session里 不用传参）
	 */
	List selectBackLog(int orderstate);

	/**
	 * @param orderstate 订单的状态 (1.未通过  2.通过3.待审批)
	 * 根据状态查询订单列表shuliang1（参数在session里 不用传参）
	 */
	int selectBackLogSum(int orderstate);


	/**
	 * 查询该部门 该医院的所有预定时间
	 * @return 预定时间列表
	 */
	List selectTime();

	/**
	 * 根据医院编码和部门编码查寻列表 --------审批部门界面的列表
	 * @param pageSize 一页多少行
	 * @param page 当前页码
	 * @return list 返回列表
	 */
	List selectSimpleListByShenPi(Integer page, Integer pageSize);


	/**
	 * 根据医院编码和审批通过状态查寻列表 --------服务部门界面的列表
	 * @param pageSize 
	 * @param page 
	 * @param name 
	 * @param otakeorder 
	 * @param endOtime 
	 * @param beginOtime 
	 * @param rid 
	 * @return list 服务部门的订单列表
	 */
	Map selectSimpleListByFuWu(Integer page, Integer pageSize, Integer rid, String beginOtime, String endOtime, Integer otakeorder, String name);

	/**
	 * 服务入口
	 * 服务部门接单(1.未接单 2.服务中3.服务完成)
	 * @param oid 要修改的订单编号
	 * @param 单子状态
	 * @return 审批是否完成 (0.未修改完成 1.修改完成)
	 */
	int updateServerState(int oid, int serverState);


	/**
	 * 用户取消一个订单 将state修改成0
	 * @param ocid 订单主体内容id
	 * @param orderstate =取消订单状态值为0
	 * @param message 取消原因
	 * @return 修改是否成功
	 */
	int UpdateOrderState(int ocid, int orderstate, String message);


	/**
	 * 查询该部门所有订单状态的总数 该部门编码在session中取
	 * @return 该部门各个订单状态总数
	 */
	Map selectStateList();

	/**
	 * 主管部门筛选信息查询列表
	 * @param page 当前页码
	 * @param pageSize 页码大小
	 * @param rname 会议室名称
	 * @param beginOtime 限制订单开始时间
	 * @param endOtime 限制订单结束时间
 	 * @param sid 服务人员名称
	 * @param opassstate 订单状态
	 * @param type 
	 * @return 筛选过后的列表
	 */
	Map selectApprovalScreen(Integer page,Integer pageSize,Integer rid,String beginOtime,String endOtime,Integer sid,Integer opassstate)throws Exception ;


	/**
	 * 审批部门修改订单
	 * @param oc
	 * @return
	 */
	int updateOrder(Ordercontent oc);


	/**
	 * 查询表服务部门各个订单状态的数目
	 * @return
	 */
	Map selectStateListByService();

	/**
	 * 普通用户查询消息代办 --------用户普通用户预订页面
	 * @return 返回代办消息列表
	 */
	List selectMessageByMember();

	/**
	 * 主管部门查询消息代办 --------主管部门预订页面
	 * @return 返回代办消息列表
	 */
	List selectMessageByApproval();

	/**
	 * 服务部门查询消息代办 --------服务部门预订页面
	 * @return 返回会议准备事项消息列表
	 */
	List selectMessageByService();

	/**
	 * 用户点击已完成按钮
	 * @param 订单id
	 * @return 是否成功
	 */
	Integer updateMemberComplete(Integer oid);

	/**
	 * 服务部门点击已完成按钮（otakeorder状态为3）或者接单按钮（otakeorder状态为2） 数据库默认为1（未接单）
	 * @param 订单id
	 * @return 是否成功
	 */
	Integer updateServiceComplete(Integer oid, Integer otakeorder);

	/**
	 * 查询该医院的所有预订人
	 * @return 该医院所有预订人列表
	 */
	List selectMemberNmeByHcode();

	/**
	 * 查询该医院的前六条的预订信息（目前是六条）-----用于服务部门
	 * @param num 几条
	 * @return 预订信息列表
	 */
	Map selectSixListForService();


	/**
	 * 查询会员的铃铛信息
	 * @return
	 */
	List selectInformMessageByMember();

	/**
	 * 查询主管部门的铃铛信息
	 * @return
	 */
	List selectInformMessageByApproval();


	/**
	 * 查询服务部门的铃铛信息
	 * @return
	 */
	List selectInformMessageByService();


	/**
	 * 订单完成按钮
	 * @param ocid 
	 * @return
	 */
	int updateButtState(Integer ocid);

	/**
	 * 根据具体日期 查询该日期有哪些会议室被预定该会议室有哪些订单
	 * @param date 哪一天
	 * @return list<Map>
	 */
	List<Map> selectRoomnameAndNum(String date);


	List selectListByNowday(int rid, String dateAssign);

}
