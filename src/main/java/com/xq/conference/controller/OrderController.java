package com.xq.conference.controller;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xq.conference.model.Ordercontent;
import com.xq.conference.service.IOrderService;

@RestController
@RequestMapping("/order")
public class OrderController {
	
	/**
	 * 订单服务
	 */
	@Autowired
	private IOrderService iorders;
	
	/**
	 * 查询该用户的所有基础订单 mid在session里取
	 * @return 基础订单列表 
	 */
	@RequestMapping(value="/selectListByMid",produces="application/json;charset=UTF-8")
	public List selectListByMid() {
		List list = iorders.selectListByMid();
		return list;
	}
	
	
	/**
	 * 根据用户id查询列表------用户界面的列表 用户订单基本信息列表
	 * @param pageSize每一页显示多少行
	 * @param pagenum第多少页
	 * @return list 普通用户订单列表（基础信息）
	 */
	@RequestMapping(value="/selectSimpleListByMember",produces="application/json;charset=UTF-8")
	public Map selectSimpleListByMember(Integer pageSize,Integer pageNum) {
		Map map = iorders.selectSimpleList(pageSize,pageNum);
		//System.out.println("map" + map);
		return map;
	}
	
	/**
	 * 根据医院编码和部门编码查寻列表 --------审批部门界面的列表
	 * @return list 审批部门的订单列表
	 */
	/*@RequestMapping("/selectSimpleListByShenPi")
	public List selectSimpleListByShenPi(Integer page,Integer pageSize) {
		List list = iorders.selectSimpleListByShenPi(page,pageSize);
		return list;
	}*/
	
	/**
	 * @return list 服务部门的订单列表
	 */
	@RequestMapping(value = "/selectSimpleListByFuWu", produces="application/json;charset=UTF-8")
	public Map selectSimpleListByFuWu(Integer page,Integer pageSize,Integer rid,String beginOtime,String endOtime,Integer otakeorder,String name) {
		
		Map map = iorders.selectSimpleListByFuWu(page,pageSize,rid,beginOtime,endOtime,otakeorder,name);
		
		return map;
	}
	/**
	 * 审批入口
	 * 审批一个订单是否通过 （1.不通过 2.通过  默认未审批3）
	 * @param oid 要修改的订单编号
	 * @param pass 是否通过
	 * @return 审批是否完成 (0.未修改完成 1.修改完成)
	 */
	@RequestMapping(value="/approveOrder",produces="application/json;charset=UTF-8")
	public int approveOrder(Integer oid,Integer pass,String message) {
		//修改一个订单的申请状态
		int state = iorders.update(oid,pass,message);
		return state;
	}
	
	/**
	 * 服务入口
	 * 服务部门接单(1.未接单 2.服务中3.服务完成)
	 * @param oid 要修改的订单编号
	 * @param 单子状态
	 * @return 是否完成 (0.未修改完成 1.修改完成)
	 */
	@RequestMapping(value="/receivingOrder",produces="application/json;charset=UTF-8")
	public int receivingOrder(Integer oid,Integer state) {
		//修改一个服务的状态
		int ServerState = iorders.updateServerState(oid,state);
		return ServerState;
	}
	
	/**
	 * 用户取消订单,取消订单之后自动释放该时间段（取消操作才是真正的释放时间段）
	 * @param ocid 订单主题内容id
	 * @return 修改是否成功
	 */
	@RequestMapping(value="/memberUpdateOrder",produces="application/json;charset=UTF-8")
	public int memberUpdateOrder(int ocid,String message) {
		int orderstate = 0;//这个状态为已取消0
		int state = iorders.UpdateOrderState(ocid,orderstate,message);
		return state;
	}
	
	/**
	 * 订单详情列表（通用的 三个角色统一的）
	 * @param ocid 订单主体内容id
	 * @return 一个对象集合 里面有订单主体对象 + 基础会议物品 + 增值会议物品 + 我的评价（如果评价了显示 不评价不显示）
	 */
	@RequestMapping(value="/selectOneInfo",produces="application/json;charset=UTF-8")
	public List selectOneInfo(int ocid) {
		List list = iorders.selectOneInfo(ocid);
		return list;
	}

	/**
	 * 查询该部门的前六条的预订信息（目前是六条）-----用于主管部门
	 * @param num 几条
	 * @return 预订信息列表
	 */
	@RequestMapping(value="/selectListByCheckAndDeft",produces="application/json;charset=UTF-8")
	public Map selectListByCheckAndDeft() {
		Map map = iorders.selectListByCheckAndDeft();
		return map;
	}

	/**
	 * 查询该医院的前六条的预订信息（目前是六条）-----用于服务部门
	 * @return 预订信息列表
	 */
	@RequestMapping(value="/selectSixListForService",produces="application/json;charset=UTF-8")
	public Map selectSixListForService() {
		Map map = iorders.selectSixListForService();
		return map;
	}
	/**
	 * @param orderstate 订单的状态 (1.未通过  2.通过3.待审批)
	 * 根据状态查询订单列表（部门参数在session里 不用传参）
	 */
	@RequestMapping(value="/selectBackLog",produces="application/json;charset=UTF-8")
	public List selectBackLog(int orderstate) {
		List list = iorders.selectBackLog(orderstate);
		return list;
	}
	
	/**
	 * @param orderstate 订单的状态 (1.未通过  2.通过3.待审批)
	 * 根据状态查询订单列表有多少条（参数在session里 不用传参）
	 */
	@RequestMapping(value="/selectBackLogSum",produces="application/json;charset=UTF-8")
	public int selectBackLogSum(int orderstate) {
		int sum = iorders.selectBackLogSum(orderstate);
		return sum;
	}
	

	
	/**
	 * 查询该部门 该医院的所有预定时间
	 * @return 预定时间列表
	 */
	@RequestMapping(value="/selectTime",produces="application/json;charset=UTF-8")
	public List selectTime() {
		List list = iorders.selectTime();
		return list;
	}
	
	/**
	 * 查询该部门所有订单状态的总数 该部门编码在session中取 -------用于主管部门主页
	 * @return 该部门各个订单状态总数 one:未通过 two：通过 three：未审批
	 */
	@RequestMapping(value="/selectStateList",produces="application/json;charset=UTF-8")
	public Map selectStateList() {
		Map map = iorders.selectStateList();
		return map;
	}
	
	/**
	 * 查询服务部门的各个订单服务状态的条数
	 * @return one.未接单 two.服务中three.服务完成  默认为1 
	 */
	@RequestMapping(value="/selectStateListByService",produces="application/json;charset=UTF-8")
	public Map selectStateListByService() {
		Map map = iorders.selectStateListByService();
		return map;
	}
	
	/**
	 * 主管部门筛选信息查询列表
	 * @param page 当前页码
	 * @param pageSize 页码大小
	 * @param rname 会议室名称
	 * @param beginOtime 限制订单开始时间
	 * @param endOtime 限制订单结束时间
 	 * @param sername 服务人员名称
	 * @param opassstate 订单状态
	 * @return 筛选过后的列表
	 */
	@RequestMapping(value="/selectApprovalScreen",produces="application/json;charset=UTF-8")
	public Map selectApprovalScreen(Integer page,Integer pageSize,Integer rid,String beginOtime,String endOtime,Integer sid,Integer opassstate)throws Exception  {
		Map map = iorders.selectApprovalScreen(page,pageSize,rid,beginOtime,endOtime,sid,opassstate);
		return map;
	}
	
	/**
	 * 提供任何属性 来修改订单
	 * @param oc 包含有修改字段的oc对象
	 * @return 修改是否成功 -1该时间段冲突（不可修改） 0.修改失败 1.修改成功
	 */
	@RequestMapping(value="/updateOrder",produces="application/json;charset=UTF-8")
	public int updateOrder(Ordercontent oc) {
		System.out.println("duixiang1:" + oc);
		
		int state = iorders.updateOrder(oc);
		return state;
	}
	/**
	 * 普通用户查询消息代办 --------用户普通用户预订页面
	 * @return 返回代办消息列表
	 */
	@RequestMapping(value="/selectMessageByMember",produces="application/json;charset=UTF-8")
	public List selectMessageByMember() {
		List list = iorders.selectMessageByMember();
		return list;
	}

	/**
	 * 查询会员铃铛消息 ------普通用户铃铛信息
	 * @return
	 */
	@RequestMapping(value="/selectInformMessageByMember",produces="application/json;charset=UTF-8")
	public List selectInformMessageByMember() {
		List list = iorders.selectInformMessageByMember();
		System.out.println(list);
		return list;
	}

	/**
	 * 主管部门查询消息代办 --------主管部门预订页面
	 * @return 返回代办消息列表
	 */
	@RequestMapping(value="/selectMessageByApproval",produces="application/json;charset=UTF-8")
	public List selectMessageByApproval() {
		List list = iorders.selectMessageByApproval();
		return list;
	}
	
	/**
	 * 查主管部门铃铛消息 ------主管部门铃铛信息
	 * @return list
	 */
	@RequestMapping(value="selectInformMessageByApproval",produces="application/json;charset=UTF-8")
	public List selectInformMessageByApproval() {
		List list = iorders.selectInformMessageByApproval();
		return list;
	}
	
	/**
	 * 服务部门查询消息代办 --------服务部门预订页面
	 * @return 返回会议准备事项消息列表
	 */
	@RequestMapping(value="/selectMessageByService",produces="application/json;charset=UTF-8")
	public List selectMessageByService() {
		List list = iorders.selectMessageByService();
		return list;
	}
	
	/**
	 * 查服务部门铃铛消息 ------服务部门铃铛信息
	 * @return list
	 */
	@RequestMapping(value="/selectInformMessageByService",produces="application/json;charset=UTF-8")
	public List selectInformMessageByService() {
		List list = iorders.selectInformMessageByService();
		return null;
	}
	/**
	 * 用户点击已完成按钮
	 * @param 订单id
	 * @return 是否成功
	 */
	@RequestMapping(value="/updateMemberComplete",produces="application/json;charset=UTF-8")
	public Integer updateMemberComplete(Integer oid) {
		
		Integer state = iorders.updateMemberComplete(oid);
		return state;
	}
	
	/**
	 * 服务部门点击已完成按钮（otakeorder状态为3）或者接单按钮（otakeorder状态为2） 数据库默认为1（未接单）
	 * @param 订单id
	 * @return 是否成功
	 */
	@RequestMapping(value="/updateServiceComplete",produces="application/json;charset=UTF-8")
	public Integer updateServiceComplete(Integer oid,Integer otakeorder) {
		Integer state = iorders.updateServiceComplete(oid,otakeorder);
		return state;
	}
	
	/**
	 * 查询该医院的所有预订人
	 * @return 该医院所有预订人列表
	 */
	@RequestMapping(value = "/selectMemberNmeByHcode",produces="application/json;charset=UTF-8")
	public List selectMemberNmeByHcode() {
		List list = iorders.selectMemberNmeByHcode();
		return list;
	}
	
	/**
	 * 页面点击修改已对接
	 * @return
	 */
	@RequestMapping("/updateButtState")
	public int updateButtState(Integer ocid) {
		
		int state = iorders.updateButtState(ocid);
		return state;
	}
	
	
	/**
	 * 根据具体日期 查询该日期有哪些会议室被预定该会议室有哪些订单
	 * @param date 哪一天
	 * @return list<Map>
	 */
	@RequestMapping("/selectRoomnameAndNum")
	public List<Map> selectRoomnameAndNum(String date) {
		
		List<Map> list = iorders.selectRoomnameAndNum(date);
		return list;
	}
}
