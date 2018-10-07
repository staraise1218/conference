package com.xq.conference.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xq.conference.dao.IAppreciationgoodsOrderDao;
import com.xq.conference.dao.IBaseOrderDao;
import com.xq.conference.dao.IBasegoodDao;
import com.xq.conference.dao.ICommentDao;
import com.xq.conference.dao.IMessageContentDao;
import com.xq.conference.dao.IOrderContentDao;
import com.xq.conference.dao.IRoomDao;
import com.xq.conference.dao.IServiceOrderDao;
import com.xq.conference.dao.IServicepeoDao;
import com.xq.conference.model.AppreciationgoodsOrder;
import com.xq.conference.model.BaseOrder;
import com.xq.conference.model.Basegoodsorder;
import com.xq.conference.model.Comment;
import com.xq.conference.model.MessageContent;
import com.xq.conference.model.OrderContentAndRoom;
import com.xq.conference.model.OrderSimpleInfo;
import com.xq.conference.model.Ordercontent;
import com.xq.conference.service.IOrderService;
import com.xq.conference.util.DateTools;
import com.xq.conference.util.DateUtil;
import com.xq.conference.util.InterfaceCallAndReturn;
import com.xq.conference.util.UrlUtil;

@Service("iorders")
public class OrderService implements IOrderService{

	@Autowired
	private IBaseOrderDao ibodao;//base 订单dao
	
	@Autowired
	private IRoomDao irdao;
	
	@Autowired
	private IOrderContentDao iocdao;//订单详情dao
	
	@Autowired
	private IBasegoodDao ibgdao;//基础物品dao
	
	@Autowired
	private IAppreciationgoodsOrderDao iagdao;//增值物品dao
	
	@Autowired
	private ICommentDao ictdao;
	
	@Autowired
	private IMessageContentDao imcdao;//消息主体内容dao
	
	@Autowired
	private IServiceOrderDao isordao;
	
	@Autowired
	private IServicepeoDao isodao;
	
	@Autowired
	private IServicepeoDao isdao;
	
	@Autowired 
	private HttpSession session;
	
	public int insertorder(Ordercontent oc,int otype,int unit,int num, List<Basegoodsorder> blist, List<AppreciationgoodsOrder> alist) {
		
		int orderContentstate = 0;
		int orderState = 0;
		int basegoosstate = 0;
		int appgoosstate = 0;
		
		//取得session里的值
		JSONObject attribute = (JSONObject) session.getAttribute("user");
		//得到该用户id
		String mid = attribute.getString("id");
		//得到该用户名
		String name = attribute.getString("name");
		//获取用户电话
		String phone = attribute.getString("phone");
		//获取当前时间
		String timeString = DateUtil.getTime();
		//将时间转换成Date类型
		Date otime = DateUtil.getDate(timeString, "yyyy-MM-dd HH:mm:ss");
		//将BaseOrder构造
		BaseOrder bo = new BaseOrder();
		bo.setOtime(otime);
		bo.setId(mid);
		bo.setOtype(otype);
		bo.setName(name);
		bo.setTel(phone);
		//时间处理工具
		DateTools dt = DateTools.getFactory();
		//提出他这次预定的开始时间
		Date ocbegintime = oc.getOcbegintime();
		//Date ocbegintime = DateUtil.getDate("2018-9-7");
		//提出他这次预定的结束时间
		Date ocendtime = oc.getOcendtime();
		//Date ocendtime = DateUtil.getDate("2018-9-13");
		//如果该订单是重复的 那就循环写订单
		if (otype == 2) {
			if (unit == 1) {//重复类型为天
				//循环录num天
				for (int i = 0; i < num; i++) {
					if (i > 0) {
						ocbegintime = dt.addDay(ocbegintime, 1);
						ocendtime = dt.addDay(ocendtime, 1);
					}
					//将订单基本信息录入到BaseOrder表中
					orderState = ibodao.insertOne(bo);
					//查询他刚才插入动作生成的oid
					int lastOid = ibodao.selectLastOid();
					//将oc对象的oid放对象里
					oc.setOcbegintime(ocbegintime);
					oc.setOcendtime(ocendtime);
					oc.setOid(lastOid);
					//插入订单内容表一条数据
					orderContentstate = iocdao.insetOne(oc); 
					//录入基础物品信息
					//查询最后一条ordercontent表id
					int lastOcid = iocdao.selectLastOcid();
					System.out.println("lastOcid:"+lastOcid);
					for (int j = 0; j < blist.size(); j++) {
						blist.get(j).setOcid(lastOcid);
					}
					for (int k = 0; k < alist.size(); k++) {

						alist.get(k).setOcid(lastOcid);
					}
					basegoosstate = ibgdao.insert(blist);
					appgoosstate = iagdao.insert(alist);
					//录入 主管部门的 待办消息（待审核）
					imcdao.insertByAll(lastOcid,4);
				}
			}else if(unit == 2) {
				//循环录num周
				for (int i = 0; i < num; i++) {
					if (i > 0) {
						ocbegintime = dt.addDay(ocbegintime, 7);
						ocendtime = dt.addDay(ocendtime, 7);
					}
					//将订单基本信息录入到BaseOrder表中
					orderState = ibodao.insertOne(bo);
					//查询他刚才插入动作生成的oid
					int lastOid = ibodao.selectLastOid();
					//将oc对象的oid放对象里
					oc.setOcbegintime(ocbegintime);
					oc.setOcendtime(ocendtime);
					oc.setOid(lastOid);
					//插入订单内容表一条数据
					orderContentstate = iocdao.insetOne(oc); 
					//录入基础物品信息
					//查询最后一条ordercontent表id
					int lastOcid = iocdao.selectLastOcid();
					System.out.println("lastOcid:"+lastOcid);
					for (int j = 0; j < blist.size(); j++) {
						blist.get(j).setOcid(lastOcid);
					}
					for (int k = 0; k < alist.size(); k++) {

						alist.get(k).setOcid(lastOcid);
					}
					basegoosstate = ibgdao.insert(blist);
					appgoosstate = iagdao.insert(alist);
					//录入 主管部门的 待办消息（待审核）
					imcdao.insertByAll(lastOcid,4);
				}
			}else {
				//循环录num月
				for (int i = 0; i < num; i++) {
					if (i > 0) {
						ocbegintime = dt.addMonth(ocbegintime, 1);
						ocendtime = dt.addMonth(ocendtime, 1);
					}
					//将订单基本信息录入到BaseOrder表中
					orderState = ibodao.insertOne(bo);
					//查询他刚才插入动作生成的oid
					int lastOid = ibodao.selectLastOid();
					//将oc对象的oid放对象里
					oc.setOcbegintime(ocbegintime);
					oc.setOcendtime(ocendtime);
					oc.setOid(lastOid);
					//插入订单内容表一条数据
					orderContentstate = iocdao.insetOne(oc); 
					//录入基础物品信息
					//查询最后一条ordercontent表id
					int lastOcid = iocdao.selectLastOcid();
					System.out.println("lastOcid:"+lastOcid);
					for (int j = 0; j < blist.size(); j++) {
						blist.get(j).setOcid(lastOcid);
					}
					for (int k = 0; k < alist.size(); k++) {

						alist.get(k).setOcid(lastOcid);
					}
					basegoosstate = ibgdao.insert(blist);
					appgoosstate = iagdao.insert(alist);
					//录入 主管部门的 待办消息（待审核）
					imcdao.insertByAll(lastOcid,4);
			}
		}
		
		
		}else {
			//将订单基本信息录入到BaseOrder表中
			orderState = ibodao.insertOne(bo);
			//查询他刚才插入动作生成的oid
			int lastOid = ibodao.selectLastOid();
			//将oc对象的oid放对象里
			oc.setOcbegintime(ocbegintime);
			oc.setOcendtime(ocendtime);
			oc.setOid(lastOid);
			//插入订单内容表一条数据
			orderContentstate = iocdao.insetOne(oc); 
			//录入基础物品信息
			//查询最后一条ordercontent表id
			int lastOcid = iocdao.selectLastOcid();
			System.out.println("lastOcid:"+lastOcid);
			for (int j = 0; j < blist.size(); j++) {
				blist.get(j).setOcid(lastOcid);
			}
			for (int k = 0; k < alist.size(); k++) {

				alist.get(k).setOcid(lastOcid);
			}
			basegoosstate = ibgdao.insert(blist);
			appgoosstate = iagdao.insert(alist);
			//录入 主管部门的 待办消息（待审核）
			imcdao.insertByAll(lastOcid,4);
		}	
		if (orderContentstate == 1 && orderState == 1) {
			return 1;
		}
			return 0;
	}

	public List selectListByNowWeek(int rid,String dateAssign) throws ParseException {
		// TODO Auto-generated method stub
		//提取该用户的医院编码
		JSONObject attribute = (JSONObject) session.getAttribute("user");
		JSONArray jsonarray = attribute.getJSONArray("userOffice");
		JSONObject object = (JSONObject) jsonarray.get(0);
		String hcode = (String) object.get("hospitalCode");
		String id = (String) attribute.get("id");
		//过滤掉 医院、日期 
		//1.查询日期所在的周是几号到几号
		String time = dateAssign;
		Date date = DateUtil.getDate(time, "yyyy-MM-dd");//起始时间
		DateTools dt = DateTools.getFactory();
		Date addDay = dt.addDay(date, 6);//结束日期
		/*Map<String,String>  weekBeginAndEnd = DateUtil.getWeekBeginAndEnd(DateUtil.toString(addDay));
		String weekBegin = weekBeginAndEnd.get("weekBegin");
		String weekEnd = weekBeginAndEnd.get("weekEnd");
		Date dateWeekBegin = DateUtil.getDate(weekBegin, "yyyy-MM-dd");
		Date dateWeekEnd = DateUtil.getDate(weekEnd, "yyyy-MM-dd");
		System.out.println("开始：" +dateWeekBegin);
		System.out.println("结束：" +dateWeekEnd);*/
		//2.在两个时间之间的预订情况,并且预定状态是未取消的
		List list = iocdao.selectListByDateAndHcode(hcode,date,addDay,rid);
		for (int i = 0; i < list.size(); i++) {
			OrderContentAndRoom orderContentAndRoom = (OrderContentAndRoom) list.get(i);
			Integer oid = orderContentAndRoom.getOid();
			String mid = ibodao.selectMidByOid(oid);
			if (id.equals(mid)) {
				orderContentAndRoom.setIsMe(1);
			}
		}
		//list.add(DateUtil.toString(dateWeekBegin));//放进当前周一日期
		return list;
	}

	@Override
	public Ordercontent selectOneByRid(int rid) {
		// TODO Auto-generated method stub
		Ordercontent oc = iocdao.selectOneByRid(rid);
		return oc;
	}

	@Override
	public String selectOcaccessoryPath(int ocid) {
		// TODO Auto-generated method stub
		String filePath = iocdao.selectOcaccessoryPath(ocid);
		return filePath;
	}

	@Override
	public List selectListByMid() {
		// TODO Auto-generated method stub
		//qusession
		JSONObject attribute = (JSONObject) session.getAttribute("user");
		JSONArray jsonarray = attribute.getJSONArray("userOffice");
		JSONObject object = (JSONObject) jsonarray.get(0);
		String id = (String) attribute.get("id");
		List list = iocdao.selectListByMid(id);
		return list;
	}

	@Override
	public int selectChongtu(Date ocbegintime, Date ocendtime, int rid) {
		
		int YN = 0;
		//先查询该会议室是否被删除
		int del = irdao.selectDelete(rid);
		System.out.println(rid + "rid" + del);
		if (del == 3) {//已被删除
			System.out.println("en?");
			return -1;
		}
		//查出距离结束时间最近的开始时间
		Integer YNBegin = iocdao.selectChongtuBegin(ocbegintime,rid);

		Integer YNEnd = iocdao.selectChongtuEnd(ocendtime,rid);

		//查询该会议室的相隔时间
		Integer time = irdao.selectRintervalByRid(rid);
		System.out.println("间隔时间："  +time);
		System.out.println("YNBegin：" + YNBegin);
		System.out.println("YNEnd：" + YNEnd);
		//System.out.println(YNBegin + "ddd" + YNEnd + "555" + time);
		//可以预定
		if ((YNBegin == null && YNEnd == null) || (YNBegin > time && YNEnd == null) || (YNBegin > time && YNEnd > time)) {
			YN = 2;
		}else {//有冲突 不可以预定 
			YN = 1;
		}
		return YN;
	}

	@Override
	public Map selectSimpleList(Integer pageSize, Integer pagenum) {
		// TODO Auto-generated method stub		
		JSONObject attribute = (JSONObject) session.getAttribute("user");
		JSONArray jsonarray = attribute.getJSONArray("userOffice");
		JSONObject object = (JSONObject) jsonarray.get(0);
		String id = (String) attribute.get("id");
		int firstList = pageSize * (pagenum - 1);
		int lastList = pageSize;
		List<OrderSimpleInfo> list = ibodao.selectSimpleList(id,firstList,lastList);
		//查询所有数据
		int count = ibodao.selectCountAll(id);
		Map map = new HashMap();
		map.put("list", list);
		map.put("count", count);
		return map;
	}

	@Override
	public int update(int oid, int pass,String message) {
		int state = 0;
		//审批不通过
		if (pass == 1) {
		    state = ibodao.update(oid,pass);
		    //审核不通过，录入订单被退回
		    imcdao.insertByAllOid(oid, 8);//铃铛
		}else {//审核通过
			int state1 = ibodao.update(oid,pass);
		    //录入待参会参会代办
			int stateinsertcanhui = imcdao.insertByAllOid(oid,3);
			//录入待对接
			int stateinsertduijei = imcdao.insertByAllOid(oid,2);
			//录入服务部门的会议准备
			int stateinsertzhunbei = imcdao.insertByAllOid(oid,5);
			//录入审核被通过
			int stateinsertshenhe = imcdao.insertByAllOid(oid,7);//铃铛
			if (state1 == 1 && stateinsertcanhui == 1 && stateinsertduijei == 1 && stateinsertzhunbei == 1 && stateinsertshenhe == 1) {
				state = 1;
			}
		}
		return state;
	}

	@Override
	public List selectOneInfo(int ocid) {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		//1.查询订单主体信息
		Ordercontent oc = iocdao.selectOneByOcid(ocid);
		//2.查询基础物品订单
		List<Basegoodsorder> listbasegoosdorder = ibgdao.selectListByOcid(ocid);
		//3.查询增值物品订单
		List<AppreciationgoodsOrder> listappreciationgoodsorder = iagdao.selectListByOcid(ocid);
		//4.查询该订单的评论内容
		Comment co = ictdao.selectOneCommentByOcid(ocid);
		//查询会议室名称（根据ocid）
		String rname = iocdao.selectRnameByOcid(ocid);
		list.add(oc);
		list.add(listbasegoosdorder);
		list.add(listappreciationgoodsorder);
		list.add(co);
		list.add(rname);
		JSONObject attribute = (JSONObject) session.getAttribute("user");
		String name = attribute.getString("name");
		String phone = attribute.getString("phone");
		return list;
	}

	@Override
	public Map selectListByCheckAndDeft() {
		// TODO Auto-generated method stub
		//1.获取部门id
		JSONObject attribute = (JSONObject) session.getAttribute("user");
		JSONArray jsonarray = attribute.getJSONArray("userOffice");
		JSONObject object = (JSONObject) jsonarray.get(0);
		String deptCode = object.getString("deptCode");
		String hcode = object.getString("hospitalCode");
		List<Map> list1 = iocdao.selectListByCheckAndDeftOne(deptCode,hcode);
		List<Map> list2 = iocdao.selectListByCheckAndDeftTwo(deptCode,hcode);
		List<Map> list3 = iocdao.selectListByCheckAndDeftThree(deptCode,hcode);
		System.out.println("1:" + list1.size() + "2：" + list2.size() + "3:" + list3.size());
		Map map = new HashMap();
		map.put("list1", list1);
		map.put("list2", list2);
		map.put("list3", list3);
		return map;
	}


	@Override
	public Map selectSixListForService() {
		// TODO Auto-generated method stub
		JSONObject attribute = (JSONObject) session.getAttribute("user");
		JSONArray jsonarray = attribute.getJSONArray("userOffice");
		JSONObject object = (JSONObject) jsonarray.get(0);
		String hcode = object.getString("hospitalCode");
		List list1 = iocdao.selectSixListForServiceOne(hcode);
		List list2 = iocdao.selectSixListForServiceTwo(hcode);
		List list3 = iocdao.selectSixListForServiceThree(hcode);
		Map map = new HashMap();
		map.put("list1", list1);
		map.put("list2", list2);
		map.put("list3", list3);
		return map;
	}

	@Override
	public List selectBackLog(int orderstate) {
		// TODO Auto-generated method stub
		JSONObject attribute = (JSONObject) session.getAttribute("user");
		JSONArray jsonarray = attribute.getJSONArray("userOffice");
		JSONObject object = (JSONObject) jsonarray.get(0);
		String deptCode = object.getString("deptCode");//部门id
		String hcode = object.getString("hospitalCode");
		List list = ibodao.selectBackLog(orderstate,deptCode,hcode);
		return list;
	}

	@Override
	public int selectBackLogSum(int orderstate) {
		// TODO Auto-generated method stub
		List list = selectBackLog(orderstate);
		return list.size();
	}

	@Override
	public List selectTime() {
		
		JSONObject attribute = (JSONObject) session.getAttribute("user");
		JSONArray jsonarray = attribute.getJSONArray("userOffice");
		JSONObject object = (JSONObject) jsonarray.get(0);
		String deptCode = object.getString("deptCode");//部门id
		String hcode = object.getString("hospitalCode");//医院编码
		List list = iocdao.selectTime(deptCode,hcode);
		
		return list;
	}

	@Override
	public List selectSimpleListByShenPi(Integer page, Integer pageSize) {
		// TODO Auto-generated method stub
		JSONObject attribute = (JSONObject) session.getAttribute("user");
		JSONArray jsonarray = attribute.getJSONArray("userOffice");
		JSONObject object = (JSONObject) jsonarray.get(0);
		String deptCode = object.getString("deptCode");//部门id
		String hcode = object.getString("hospitalCode");//医院编码
		int firstList = pageSize * (page - 1);
		int lastList = pageSize;
		List<Map> list = iocdao.selectSimpleListByShenPi(deptCode,hcode,firstList,lastList);
		return list;
	}

	@Override
	public Map selectSimpleListByFuWu(Integer page, Integer pageSize, Integer rid, String beginOtime, String endOtime, Integer otakeorder, String name) {
		// TODO Auto-generated method stub
		JSONObject attribute = (JSONObject) session.getAttribute("user");
		JSONArray jsonarray = attribute.getJSONArray("userOffice");
		JSONObject object = (JSONObject) jsonarray.get(0);
		String deptCode = object.getString("deptCode");
		String hcode = object.getString("hospitalCode");
		int firstList = pageSize * (page - 1);
		int lastList = pageSize;
		Date datebegin = null;
		Date dateend = null;
		if (beginOtime != null && beginOtime != "" && endOtime != null && endOtime != "") {
			 datebegin = DateUtil.getDate(beginOtime,"yyyy-MM-dd");
			 dateend = DateUtil.getDate(endOtime,"yyyy-MM-dd");
		}
		System.out.print(name + hcode + deptCode);
		List list = iocdao.selectSimpleListByFuWu(firstList,lastList,rid,datebegin,dateend,otakeorder,name,deptCode,hcode);
		List listcount = iocdao.selectSimpleListByFuWucount(rid,datebegin,dateend,otakeorder,name,deptCode,hcode);
		Integer count = listcount.size();
		Map map = new HashMap();
		map.put("list", list);
		map.put("count", count);
		return map;
	}

	@Override
	public int updateServerState(int oid, int serverState) {
		// TODO Auto-generated method stub
		int state = ibodao.updateServerState(oid,serverState);
		//如果该订单已完成，那么插入定时任务队列
		if (serverState == 3) {
			//我需要将默认好评任务加入到任务队列中
			
		}
		return state;
	}

	@Override
	public int UpdateOrderState(int ocid, int orderstate, String message) {
		// TODO Auto-generated method stub
		int s = 0;
		//修改订单状态
		int state = ibodao.UpdateOrderState(ocid,orderstate);
		//插入取消原因 mtid = 1
		int statequxiao = imcdao.insert(message,ocid);
		if (state == 1 && statequxiao == 1) {
			s = 1;
		}else {
			s = 0;
		}
		return s;
	}

	@Override
	public Map selectStateList() {
		Map map = new HashMap();
		JSONObject attribute = (JSONObject) session.getAttribute("user");
		JSONArray jsonarray = attribute.getJSONArray("userOffice");
		JSONObject object = (JSONObject) jsonarray.get(0);
		String deptCode = object.getString("deptCode");
		String hcode = object.getString("hospitalCode");
		Integer one = ibodao.selectStateList(deptCode,hcode,1);
		Integer two = ibodao.selectStateList(deptCode,hcode,2);
		Integer three = ibodao.selectStateList(deptCode,hcode,3);
		map.put("one", one);
		map.put("two", two);
		map.put("three", three);
		return map;
	}

	@Override
	public Map selectApprovalScreen(Integer page,Integer pageSize,Integer rid,String beginOtime,String endOtime,Integer sid,Integer opassstate) throws Exception {
		JSONObject attribute = (JSONObject) session.getAttribute("user");
		JSONArray jsonarray = attribute.getJSONArray("userOffice");
		JSONObject object = (JSONObject) jsonarray.get(0);
		String deptCode = object.getString("deptCode");
		String hcode = object.getString("hospitalCode");
		String unitCode = (String) object.get("unitCode");
		int firstList = pageSize * (page - 1);
		int lastList = pageSize;
		Date datebegin = null;
		Date dateend = null;
		if (beginOtime != null && beginOtime != "" && endOtime != null && endOtime != "") {
			 datebegin = DateUtil.getDate(beginOtime,"yyyy-MM-dd");
			 dateend = DateUtil.getDate(endOtime,"yyyy-MM-dd");
		}
		List<Map> list = ibodao.selectApprovalScreen(firstList,lastList,rid,datebegin,dateend,sid,opassstate,deptCode,hcode);
		
		System.out.println("list" + list);
		/*将list中的服务人员名称set进去*/
		for (int i = 0; i < list.size(); i++) {
			String seridArry = (String) list.get(i).get("seridArry");
			//System.out.println("hahaha:" + seridArry);
			String id = "";
			StringBuffer sernameList = new StringBuffer();
			if (seridArry != null) {
				String[] split = seridArry.split(",");
				for (int j = 0; j < split.length; j++) {
					String string = split[j];//得到我方人员id
					//System.out.println(string);
					//获取他方人员id
					id = isodao.selectIdBySerid(new Integer(string));
					//System.out.println(id);
					StringBuffer sb = new StringBuffer(UrlUtil.URLIHBS);
					sb.append("outsourcedController/getStaffInfoByType");
					String url = sb.toString();
					StringBuffer param = new StringBuffer("?");
					param.append("unitCode=");
					param.append(unitCode);
					param.append("&hospitalCode=");
					param.append(hcode);
					param.append("&type=");
					param.append(2);
					//调用他方接口
					param.append("&staffId=");
					param.append(id);
					sernameList.append(InterfaceCallAndReturn.dataWant(url, param, "name")).append("  ");
					/*String returnJson = InterfaceCallAndReturn.dataWant(url, param, "name");
					System.out.println(returnJson);*/
				} 
			}
			list.get(i).put("sernameList", sernameList);
		}
		List countlist = ibodao.selectApprovalScreenCount(rid,datebegin,dateend,sid,opassstate,deptCode,hcode);
		Integer coune = countlist.size();
		Map map = new HashMap();
		map.put("list", list);
		map.put("coune", coune);
		return map;
	}

	@Override
	public int updateOrder(Ordercontent oc) {
		// TODO Auto-generated method stub
		//1.有冲突 2.没冲突
		int selectChongtu = selectChongtu(oc.getOcbegintime(), oc.getOcendtime(), oc.getRid());
		//该订单可以被修改
		int state = 0;
		if (selectChongtu == 2) {
		    state = iocdao.updateOrder(oc);
			//修改之后啊 给用户提一下醒
			imcdao.insertByAll(oc.getOcid(), 6);
		}else {
			state = -1;
		}
		return state;
	}

	@Override
	public Map selectStateListByService() {
		Map map = new HashMap();
		JSONObject attribute = (JSONObject) session.getAttribute("user");
		JSONArray jsonarray = attribute.getJSONArray("userOffice");
		JSONObject object = (JSONObject) jsonarray.get(0);
		String deptCode = object.getString("deptCode");
		String hcode = object.getString("hospitalCode");
		Integer one = ibodao.selectStateListByService(deptCode,hcode,1);
		Integer two = ibodao.selectStateListByService(deptCode,hcode,2);
		Integer three = ibodao.selectStateListByService(deptCode,hcode,3);
		map.put("one", one);
		map.put("two", two);
		map.put("three", three);
		return map;
	}

	@Override
	public List selectMessageByMember() {

		//取得session里的值
		JSONObject attribute = (JSONObject) session.getAttribute("user");
		//得到该用户id
		String mid = attribute.getString("id");
		List list = imcdao.selectMessageByMember(mid);
		return list;
	}

	@Override
	public List selectMessageByApproval() {
		// TODO Auto-generated method stub
		JSONObject attribute = (JSONObject) session.getAttribute("user");
		JSONArray jsonarray = attribute.getJSONArray("userOffice");
		JSONObject object = (JSONObject) jsonarray.get(0);
		String deptCode = object.getString("deptCode");
		String hcode = object.getString("hospitalCode");
		List list = imcdao.selectMessageByApproval(deptCode,hcode);
		return list;
	}

	@Override
	public List selectMessageByService() {
		JSONObject attribute = (JSONObject) session.getAttribute("user");
		JSONArray jsonarray = attribute.getJSONArray("userOffice");
		JSONObject object = (JSONObject) jsonarray.get(0);
		String hcode = object.getString("hospitalCode");
		List list = imcdao.selectMessageByService(hcode);
		return list;
	}

	@Override
	public Integer updateMemberComplete(Integer oid) {
		
		int state = ibodao.updateMemberComplete(oid);
		return state;
	}

	@Override
	public Integer updateServiceComplete(Integer oid, Integer otakeorder) {

		int state = 0;
		try {
			System.out.println("oid:" + oid + "otakeorder:" + otakeorder);
			state = ibodao.updateServiceComplete(oid, otakeorder); 
		} catch (Exception e) {
			// TODO: handle exception
		}
		return state;
	}

	@Override
	public List selectMemberNmeByHcode() {
		JSONObject attribute = (JSONObject) session.getAttribute("user");
		JSONArray jsonarray = attribute.getJSONArray("userOffice");
		JSONObject object = (JSONObject) jsonarray.get(0);
		String hcode = object.getString("hospitalCode");
		List list = ibodao.selectMemberNmeByHcode(hcode);
		return list;
	}

	@Override
	public List selectInformMessageByMember() {
		//取得session里的值
		JSONObject attribute = (JSONObject) session.getAttribute("user");
		//得到该用户id
		String mid = attribute.getString("id");
		List<MessageContent> list = imcdao.selectInformMessageByMember(mid);
		for (int i = 0; i < list.size(); i++) {
			MessageContent messageContent = list.get(i);
			Integer mcid = messageContent.getMcid();
			imcdao.updateReadState(mcid);//修改所返回的为已读
		}
		return list;
	}

	@Override
	public List selectInformMessageByApproval() {
		//取得session里的值
		JSONObject attribute = (JSONObject) session.getAttribute("user");
		//得到该用户id
		String mid = attribute.getString("id");
		List<MessageContent> list = imcdao.selectInformMessageByApproval(mid);
		for (int i = 0; i < list.size(); i++) {
			MessageContent messageContent = list.get(i);
			Integer mcid = messageContent.getMcid();
			imcdao.updateReadState(mcid);//修改所返回的为已读
		}
		return list;
	}

	@Override
	public List selectInformMessageByService() {
		//取得session里的值
		JSONObject attribute = (JSONObject) session.getAttribute("user");
		//得到该用户id
		String mid = attribute.getString("id");
		List<MessageContent> list = imcdao.selectInformMessageByService(mid);
		for (int i = 0; i < list.size(); i++) {
			MessageContent messageContent = list.get(i);
			Integer mcid = messageContent.getMcid();
			imcdao.updateReadState(mcid);//修改所返回的为已读
		}
		return list;
	}

	@Override
	public int updateButtState(Integer ocid) {
		int state = iocdao.updateButtState(ocid);
		return state;
	}

	@Override
	public List<Map> selectRoomnameAndNum(String date) {
		
		List listReal = new ArrayList();
		//获取医院编码
		JSONObject attribute = (JSONObject) session.getAttribute("user");
		JSONArray jsonarray = attribute.getJSONArray("userOffice");
		JSONObject object = (JSONObject) jsonarray.get(0);
		String hcode = object.getString("hospitalCode");
		//将date转换成时间类型
		Date clientDateBegin = DateUtil.getDate(date);
		DateTools factory = DateTools.getFactory();
		Date clientDateEnd = factory.addDay(DateUtil.getDate(date), 1);
		//日期、医院编码获取rid
		List list = iocdao.selectRidByDateAndHcode(clientDateBegin,clientDateEnd,hcode);
		//System.out.println("aaa" + list);
		List<Integer> ridList = irdao.selectListRid();
		//System.out.println("bbb" + ridList);
		//List noridList = new ArrayList();
		for (int i = 0; i < ridList.size(); i++) {
			for (int j = 0; j < list.size(); j++) {
				if (list.get(j) == ridList.get(i)) {
					ridList.remove(list.get(j));
				}
			}
		}
		System.out.println("xi9xix" + ridList);
		/*if (list.size() == 0) {
			return null;
		}*/
		for (int i = 0; i < list.size(); i++) {
			Map map = new HashMap();
			int rid = (int) list.get(i);
			//根据rid查询rname
			String rname = irdao.selectRnameByRid(rid); 
			int sum = iocdao.selectSumOrderByRid(rid,clientDateBegin,clientDateEnd);

			map.put("rname", rname);
			map.put("sum", sum);
			map.put("rid", rid);
			listReal.add(map) ;
		}
		for (int i = 0; i < ridList.size(); i++) {
			Map map = new HashMap();
			int rid = (int) ridList.get(i);
			//根据rid查询rname
			String rname = irdao.selectRnameByRid(rid); 

			map.put("rname", rname);
			map.put("sum", 0);
			map.put("rid", rid);
			//System.out.println("jajajajaja:");
			listReal.add(map) ;
		}
		//System.out.println(listReal);
		return listReal;
	}

	@Override
	public List selectListByNowday(int rid, String dateAssign) {
		// TODO Auto-generated method stub
				//提取该用户的医院编码
				JSONObject attribute = (JSONObject) session.getAttribute("user");
				JSONArray jsonarray = attribute.getJSONArray("userOffice");
				JSONObject object = (JSONObject) jsonarray.get(0);
				String hcode = (String) object.get("hospitalCode");
				String id = (String) attribute.get("id");
				//过滤掉 医院、日期 
				//1.查询日期所在的周是几号到几号
				String time = dateAssign;
				Date date = DateUtil.getDate(time, "yyyy-MM-dd");//起始时间
				DateTools dt = DateTools.getFactory();
				Date addDay = dt.addDay(date, 1);//结束日期
				/*Map<String,String>  weekBeginAndEnd = DateUtil.getWeekBeginAndEnd(DateUtil.toString(addDay));
				String weekBegin = weekBeginAndEnd.get("weekBegin");
				String weekEnd = weekBeginAndEnd.get("weekEnd");
				Date dateWeekBegin = DateUtil.getDate(weekBegin, "yyyy-MM-dd");
				Date dateWeekEnd = DateUtil.getDate(weekEnd, "yyyy-MM-dd");
				System.out.println("开始：" +dateWeekBegin);
				System.out.println("结束：" +dateWeekEnd);*/
				//2.在两个时间之间的预订情况,并且预定状态是未取消的
				List list = iocdao.selectListByDateAndHcode(hcode,date,addDay,rid);
				for (int i = 0; i < list.size(); i++) {
					OrderContentAndRoom orderContentAndRoom = (OrderContentAndRoom) list.get(i);
					Integer oid = orderContentAndRoom.getOid();
					String mid = ibodao.selectMidByOid(oid);
					if (id.equals(mid)) {
						orderContentAndRoom.setIsMe(1);
					}
				}
				//list.add(DateUtil.toString(dateWeekBegin));//放进当前周一日期
				return list;
	}
}
