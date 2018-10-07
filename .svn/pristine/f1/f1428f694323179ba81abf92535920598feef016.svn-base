package com.xq.conference.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xq.conference.dao.IBaseOrderDao;
import com.xq.conference.dao.IRoomDao;
import com.xq.conference.model.OrderContentAndRoom;
import com.xq.conference.model.Room;
import com.xq.conference.service.IRoomService;
import com.xq.conference.util.DateTools;
import com.xq.conference.util.DateUtil;
import com.xq.conference.util.InterfaceCallAndReturn;
import com.xq.conference.util.UrlUtil;

@Service("irs")
public class RoomService implements IRoomService{
	
	@Autowired
	private IRoomDao irdao;
	@Autowired
	private HttpSession session;
	@Autowired
	private IBaseOrderDao ibodao;//base 订单dao

	public int insertOneRoomToOther(String unitCode, String hospitalCode, String id, String spaceCode) throws Exception {
		int state;
		StringBuffer sb = new StringBuffer("?");
		sb.append("unitCode=" + unitCode);
		sb.append("&hospitalCode=" + hospitalCode);
		sb.append("&id=" + id);
		sb.append("&spaceCode=" + spaceCode);
		JSONObject returnJson = InterfaceCallAndReturn.returnJson("http://218.247.12.42:8196/hospitalBaseData/hospitalController/updateHospitalGridSpace", sb, "code");
		if (returnJson.toString().equals("200")) {
			System.out.println(returnJson);
			state = 1;
		}else {
			state = 0;
		}
		return state;
	}

	public int insertOneRoomMy(Room room) {
		//获取该医院的
		int state = irdao.insertOneRoomMy(room);
		return state;
	}
	
	/**
	 * 获取医院的部门信息
	 * @Date 2018年9月7日 上午9:31:34
	 * @return
	 */
	public List getDepartmentListByOther() {
		List<JSONObject> list = new ArrayList<JSONObject>();
		try {
			JSONObject parseObject = null;
			StringBuffer sb = new StringBuffer(UrlUtil.URLIHBS);
			sb.append("hospitalController/getHospitalOfficeInfoAndGridPhone");
			StringBuffer param = new StringBuffer("?");
			String url = sb.toString();
			//通过他提供的sso认证信息来获取他的医院编码 和 主管单位编码(用户登录之后将cookie存的对象取出来)
			JSONObject attribute = (JSONObject) session.getAttribute("user");
			JSONArray jsonarray = attribute.getJSONArray("userOffice");
			JSONObject object = (JSONObject) jsonarray.get(0);
			String unitCode = (String) object.get("unitCode");
			String hospitalCode = (String) object.get("hospitalCode");
			param.append("unitCode=");
			param.append(unitCode);
			param.append("&hospitalCode=");
			param.append(hospitalCode);
			JSONArray returnJson = (JSONArray) InterfaceCallAndReturn.returnObj(url,param,"data");
			String officeId = "";
			String officeName = "";
			System.out.println(returnJson.size());
			for (int i = 0; i < returnJson.size(); i++) {
				JSONObject getjson = (JSONObject) returnJson.get(i);
				officeId = getjson.getString("officeId");
				officeName = getjson.getString("officeName");
				list.add(getjson);
			}
			 
		} catch (Exception e) { 
		}
		return list;
	}
	
	/**
	 * 新增一个会议室
	 * yangweihang
	 * @Date 2018年9月7日 上午9:00:41
	 * @param r
	 * @return
	 */
	public int insertRoom(Room r) {
		//临时的注释
		JSONObject attribute = (JSONObject) session.getAttribute("user");
		JSONArray jsonarray = attribute.getJSONArray("userOffice");
		JSONObject object = (JSONObject) jsonarray.get(0);
		String hcode = object.getString("hospitalCode");
		String rlocation = object.getString("hospitalName");
		r.setRlocation(rlocation);
		r.setHcode(hcode);
		return irdao.insertRoom(r);
	}
	
	/**
	 * 获取医院的地理信息
	 * @Date 2018年9月7日 上午9:31:34
	 * @return
	 */
	public List getBigLocalListByOther(String rdepartment,int rbudingnubmer,int rfloor,int rhousenumber) {
		JSONObject getjson = null;
		String rlocation = null;
		List list = new ArrayList();
		try {
			StringBuffer sb = new StringBuffer(UrlUtil.URLIHBS);
			sb.append("hospitalController/getHospitalGridInfo");
			String url = sb.toString();
			StringBuffer param = new StringBuffer("?");
			JSONObject attribute = (JSONObject) session.getAttribute("user");
			JSONArray jsonarray = attribute.getJSONArray("userOffice");
			JSONObject object = (JSONObject) jsonarray.get(0);
			String unitCode = (String) object.get("unitCode");
			String hospitalCode = (String) object.get("hospitalCode");
			param.append("unitCode=");
			param.append(unitCode);
			param.append("&hospitalCode=");
			param.append(hospitalCode);
			JSONArray returnJson = (JSONArray) InterfaceCallAndReturn.returnObj(url, param, "data");
			for (int i = 0; i < returnJson.size(); i++) {
				JSONObject object2 = (JSONObject) returnJson.get(i);
				if(object2.get("gridName").equals(rdepartment)) {
					String string = object2.getString("gridName");	
				}
				list.add(object2);
				System.out.println("object2"+object2);
			} 
		} catch (Exception e) {
		}
		return list;
	}

	public String getBigLocalListByOther() {
		
		JSONObject attribute = (JSONObject) session.getAttribute("user");
		JSONArray jsonarray = attribute.getJSONArray("userOffice");
		JSONObject object = (JSONObject) jsonarray.get(0);
		String hospitalName = object.getString("hospitalName");
		
		return hospitalName;
	}
	
	/**
	 * 修改锁定未锁定
	 * yangweihang
	 * @Date 2018年9月7日 下午4:29:12
	 * @param rstate
	 * @return
	 */
	public int updaterstate(int rstate,String rname) {
		return irdao.updaterstate(rstate, rname);
	}
	
	/**
	 * 编辑会议室
	 * yangweihang
	 * @Date 2018年9月7日 下午4:48:22
	 * @param r
	 * @return
	 */
	public int updateroom(Room r) {
		return irdao.updateroom(r);
	}
	
	/**
	 * 按会议室id查询
	 * yangweihang
	 * @Date 2018年9月7日 下午4:58:21
	 * @param rid
	 * @return
	 */
	public Room selectroom(int rid) {
		Room room = irdao.selectroom(rid); 
		JSONObject attribute = (JSONObject) session.getAttribute("user");
		JSONArray jsonarray = attribute.getJSONArray("userOffice");
		JSONObject object = (JSONObject) jsonarray.get(0);
		String hospitalCode = (String) object.get("hospitalCode");//医院编码
		String unitCode = (String) object.get("unitCode");//主管部门编码
		String deptCode = object.getString("deptCode");//部门id
		//发送三方请求
		String urlreq = UrlUtil.URLIHBS + "hospitalController/getHospitalOfficeInfoById";
		StringBuffer sb = new StringBuffer("?");
		sb.append("unitCode=" + unitCode);
		sb.append("&hospitalCode=" + hospitalCode);
		sb.append("&id=" + deptCode);
		String returnJson = "";
		try {
			returnJson = InterfaceCallAndReturn.dataWant(urlreq, sb, "officeName");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		room.setRdepartment(returnJson);
		return room;
	}
	
	/**
	 * 虚拟删除会议室
	 * yangweihang
	 * @Date 2018年9月7日 下午5:17:30
	 * @param rid
	 * @return
	 */
	public int deleteroom(int rid) {
		return irdao.deleteroom(rid);
	}
	
	/**
	 * 时间间隔设置
	 * yangweihang
	 * @Date 2018年9月7日 下午9:04:05
	 * @param rinterval
	 * @return
	 */
	public int updaterinterval(int rinterval,int rid) {
		return irdao.updaterinterval(rinterval,rid);
	}
	
	/**
	 * 查询会议室
	 * yangweihang
	 * @Date 2018年9月7日 下午9:12:04
	 * @return
	 */
	public List<Room> selectByRoom() {
		return irdao.selectByRoom();
	}

	@Override
	public List selectOneRoomByLocal(int rbudingnubmer, int rfloor, String rname, int weekNum,String dateAssign) {
		
		List<OrderContentAndRoom> list = new ArrayList<OrderContentAndRoom>();
		try {
			JSONObject attribute = (JSONObject) session.getAttribute("user");
			JSONArray jsonarray = attribute.getJSONArray("userOffice");
			System.out.println(attribute);
			String id = (String) attribute.get("id");
			JSONObject object = (JSONObject) jsonarray.get(0);
			String hospitalCode = (String) object.get("hospitalCode");
			//1.查询当前日期所在的周是几号到几号
			String time = "";
			if (dateAssign == null) {
			    time = DateUtil.getTime("yyyy-MM-dd");//基准
			}else {
				time = dateAssign;
			}
			Date date = DateUtil.getDate(time, "yyyy-MM-dd");
			DateTools dt = DateTools.getFactory();
			Date addDay = dt.addDay(date, weekNum * 7);
			Map<String, String> weekBeginAndEnd = DateUtil.getWeekBeginAndEnd(DateUtil.toString(addDay));
			String weekBegin = weekBeginAndEnd.get("weekBegin");
			String weekEnd = weekBeginAndEnd.get("weekEnd");
			Date dateWeekBegin = DateUtil.getDate(weekBegin, "yyyy-MM-dd");
			Date dateWeekEnd = DateUtil.getDate(weekEnd, "yyyy-MM-dd");
			list = irdao.selectOneRoomByLocalAndhcode(rbudingnubmer, rfloor, rname, hospitalCode, dateWeekBegin,
					dateWeekEnd);
			for (int i = 0; i < list.size(); i++) {
				OrderContentAndRoom orderContentAndRoom = list.get(i);
				Integer oid = orderContentAndRoom.getOid();
				String mid = ibodao.selectMidByOid(oid);
				if (id.equals(mid)) {
					orderContentAndRoom.setIsMe(1);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}

	@Override
	public List<OrderContentAndRoom> selectOneRoomAndOrderByRname(String rname, int weekNum, String dateAssign) {
		List<OrderContentAndRoom> list = new ArrayList<OrderContentAndRoom>();
		try {
			JSONObject attribute = (JSONObject) session.getAttribute("user");
			JSONArray jsonarray = attribute.getJSONArray("userOffice");
			String id = (String) attribute.get("id");
			JSONObject object = (JSONObject) jsonarray.get(0);
			String hospitalCode = (String) object.get("hospitalCode");
			//1.查询当前日期所在的周是几号到几号
			String time = "";
			if (dateAssign == null) {
			    time = DateUtil.getTime("yyyy-MM-dd");//基准
			}else {
				time = dateAssign;
			}
			Date date = DateUtil.getDate(time, "yyyy-MM-dd");
			DateTools dt = DateTools.getFactory();
			Date addDay = dt.addDay(date, weekNum * 7);
			Map<String, String> weekBeginAndEnd = DateUtil.getWeekBeginAndEnd(DateUtil.toString(addDay));
			String weekBegin = weekBeginAndEnd.get("weekBegin");
			String weekEnd = weekBeginAndEnd.get("weekEnd");
			Date dateWeekBegin = DateUtil.getDate(weekBegin, "yyyy-MM-dd");
			Date dateWeekEnd = DateUtil.getDate(weekEnd, "yyyy-MM-dd");
			list = irdao.selectOneRoomAndOrderByRname(rname, hospitalCode, dateWeekBegin,
					dateWeekEnd);
			for (int i = 0; i < list.size(); i++) {
				OrderContentAndRoom orderContentAndRoom = list.get(i);
				Integer oid = orderContentAndRoom.getOid();
				String mid = ibodao.selectMidByOid(oid);
				if (id.equals(mid)) {
					orderContentAndRoom.setIsMe(1); 
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}

	@Override
	public List selectListByOften() {
		// TODO Auto-generated method stub
		JSONObject attribute = (JSONObject) session.getAttribute("user");
		JSONArray jsonarray = attribute.getJSONArray("userOffice");
		JSONObject object = (JSONObject) jsonarray.get(0);
		String hospitalCode = (String) object.get("hospitalCode");
		//System.out.println("医院编码："+hospitalCode);
		String deptCode = object.getString("deptCode");
		List list = new ArrayList();
		try {
			list = irdao.selectListByOften(hospitalCode, deptCode);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}

	@Override
	public List selectRoomByHcodeAndDeptCode() {
		// TODO Auto-generated method stub
		JSONObject attribute = (JSONObject) session.getAttribute("user");
		JSONArray jsonarray = attribute.getJSONArray("userOffice");
		JSONObject object = (JSONObject) jsonarray.get(0);
		String hospitalCode = (String) object.get("hospitalCode");
		String deptCode = object.getString("deptCode");
		List list = irdao.selectRoomByHcodeAndDeptCode(hospitalCode,deptCode);
		return list;
	}

	@Override
	public Map selectRoomByClientInsert(String rname, Integer rbudingnubmer, Integer rfloor, Integer page, Integer pageSize) {//标记
		Map map = null;
		try {
			// TODO Auto-generated method stub
			JSONObject attribute = (JSONObject) session.getAttribute("user");
			JSONArray jsonarray = attribute.getJSONArray("userOffice");
			JSONObject object = (JSONObject) jsonarray.get(0);
			String hospitalCode = (String) object.get("hospitalCode");//医院编码
			String unitCode = (String) object.get("unitCode");//主管部门编码
			String deptCode = object.getString("deptCode");//部门id
			Integer firstList = pageSize * (page - 1);
			Integer lastList = pageSize;
			List<Room> list = new ArrayList();
			try {
				list = irdao.selectRoomByClientInsert(rname, rbudingnubmer, rfloor, hospitalCode, firstList,
						lastList);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String urlreq = UrlUtil.URLIHBS + "hospitalController/getHospitalOfficeInfoById";
			for (int i = 0; i < list.size(); i++) {
				String rdepartmentcode = list.get(i).getRdepartment();
				//发送三方请求
				StringBuffer sb = new StringBuffer("?");
				sb.append("unitCode=" + unitCode);
				sb.append("&hospitalCode=" + hospitalCode);
				sb.append("&id=" + list.get(i).getRdepartment());
				String returnJson = InterfaceCallAndReturn.dataWant(urlreq, sb, "officeName");
				list.get(i).setRdepartment(returnJson);
			}
			Integer count = selectRoomByClientInsertcount(rname, rbudingnubmer, rfloor, hospitalCode);
			map = new HashMap();
			map.put("list", list);
			map.put("count", count);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return map;
	}

	@Override
	public List selectDeptNameLitsByHcode() {
		List list = new ArrayList();
		try {
			// TODO Auto-generated method stub
			JSONObject attribute = (JSONObject) session.getAttribute("user");
			JSONArray jsonarray = attribute.getJSONArray("userOffice");
			JSONObject object = (JSONObject) jsonarray.get(0);
			String hospitalCode = (String) object.get("hospitalCode");//医院编码
			String unitCode = (String) object.get("unitCode");//主管部门编码
			String urlreq = UrlUtil.URLIHBS + "hospitalController/getHospitalOfficeInfoAndGridPhone";
			StringBuffer sb = new StringBuffer("?");
			sb.append("unitCode=" + unitCode);
			sb.append("&hospitalCode=" + hospitalCode);
		    list = (List) InterfaceCallAndReturn.returnObj(urlreq, sb, "data");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	
	@Override
	public List selectRoomByHcode() {

		JSONObject attribute = (JSONObject) session.getAttribute("user");
		JSONArray jsonarray = attribute.getJSONArray("userOffice");
		JSONObject object = (JSONObject) jsonarray.get(0);
		String hospitalCode = (String) object.get("hospitalCode");
		List list = irdao.selectRoomByHcode(hospitalCode);
		return list;
	}
	
	public Integer selectRoomByClientInsertcount(String rname, Integer rbudingnubmer, Integer rfloor, String hospitalCode) {
		List list = irdao.selectRoomByClientInsertcount(rname, rbudingnubmer, rfloor, hospitalCode);
		return list.size();
	}

}
