package com.xq.conference.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xq.conference.dao.IServiceOrderDao;
import com.xq.conference.dao.IServicepeoDao;
import com.xq.conference.model.ServiceOrder;
import com.xq.conference.model.Servicepeo;
import com.xq.conference.service.IServicepeoService;
import com.xq.conference.util.InterfaceCallAndReturn;
import com.xq.conference.util.UrlUtil;

@Service("isps")
public class ServicepeoService implements IServicepeoService{
	
	@Autowired
	private IServicepeoDao ispdao;
	
	@Autowired
	private IServiceOrderDao isodao;
	
	@Autowired 
	private HttpSession session;

	@Override
	public List selectList() {
		// TODO Auto-generated method stub
		List list = ispdao.selectList();
		return list;
	}

	@Override
	public int insertServiceOrder(String[] id, Integer ocid) {
		id = new String[]{"1asdhgfkjasdgfpp","2aaskljdgflajsgdf"};
		
		ServiceOrder so = isodao.selectByOcid(ocid);
		if (so != null) {
			return -1;//返回-1说明该订单已经指派过服务人员了
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < id.length; i++) {
			
			Servicepeo ser = ispdao.selectSeridExistByMid(id[i]);
			if (ser == null) {
				ispdao.insertOne(id[i]);
				//查询刚才录得那条的主键是多少
				int lasetSerid = ispdao.selectLastSerid();
				sb.append(lasetSerid + ",");
			}else{
				sb.append(ser.getSerid() + ",");
			}
		}
		String sbString = sb.toString();
		int state = isodao.insertServiceOrder(sbString,ocid);
		return state;
	}

	@Override
	public List selectServicepeoByOcid(Integer ocid) {
		// TODO Auto-generated method stub
		JSONObject attribute = (JSONObject) session.getAttribute("user");
		JSONArray jsonarray = attribute.getJSONArray("userOffice");
		JSONObject object = (JSONObject) jsonarray.get(0);
		String hcode = (String) object.get("hospitalCode");
		String unitCode = (String) object.get("unitCode");
		String SeridArry = isodao.selectServicepeoByOcid(ocid);
		if (SeridArry == null || SeridArry.length() == 0) {
			return null;
		}
		String[] split = SeridArry.split(",");
		List list = new ArrayList();
		for (int i = 0; i < split.length; i++) {
			String string = split[i];
			String selectIdBySerid = ispdao.selectIdBySerid(new Integer(string));
			list.add(selectIdBySerid);
		}
		System.out.println(list);
		Map map = new HashMap();
		map.put("hcode", hcode);

		map.put("unitCode", unitCode);
		list.add(map);
		return list;
	}

	@Override
	public JSONObject selectServicepeoByOther() throws Exception {
		
		JSONObject attribute = (JSONObject) session.getAttribute("user");
		JSONArray jsonarray = attribute.getJSONArray("userOffice");
		JSONObject object = (JSONObject) jsonarray.get(0);
		String hcode = (String) object.get("hospitalCode");
		String unitCode = (String) object.get("unitCode");
		

		StringBuffer sb = new StringBuffer(UrlUtil.URLIHBS);
		sb.append("outsourcedController/getStaffListByOfficeOrTeam");

		StringBuffer param = new StringBuffer("?");
		String url = sb.toString();
		param.append("unitCode=");
		param.append(unitCode);
		param.append("&hospitalCode=");
		param.append(hcode);
		param.append("&type=");
		param.append("2");
		JSONObject returnJson = (JSONObject) InterfaceCallAndReturn.returnObj(url,param,"data");
		return returnJson;
	}
}
