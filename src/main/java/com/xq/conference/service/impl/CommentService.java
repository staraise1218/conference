package com.xq.conference.service.impl;

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
import com.xq.conference.dao.ICommentDao;
import com.xq.conference.dao.IRoomDao;
import com.xq.conference.model.Comment;
import com.xq.conference.model.Room;
import com.xq.conference.service.ICommentService;
import com.xq.conference.util.DateUtil;

@Service("icts")
public class CommentService implements ICommentService {
	
	@Autowired
	private ICommentDao ictdao;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private IRoomDao irdao;

	@Override
	public List selectByComment(String rname) {
		JSONObject attribute = (JSONObject) session.getAttribute("user");
		String uname = attribute.getString("name");
		JSONArray jsonarray = attribute.getJSONArray("userOffice");
		JSONObject object = (JSONObject) jsonarray.get(0);
		String deptCode = (String) object.get("deptCode");
		String hcode = (String) object.get("hospitalCode");
		List<Comment> list = ictdao.selectByComment(rname,hcode,deptCode);
		int sumBuman = 0;
		int sumYiban = 0;
		int sumManyi = 0;
		for (int i = 0; i < list.size(); i++) {
			Integer cmgrade = list.get(i).getCmgrade();
			list.get(i).setUname(uname);
			if (cmgrade <= 1) {
				sumBuman++;
			}else if (cmgrade >=2 && cmgrade < 4) {
				sumYiban++;
			}else {
				sumManyi++;
			}
		}
		Map map = new HashMap();
		map.put("sumBuman", sumBuman);
		map.put("sumYiban", sumYiban);
		map.put("sumManyi", sumManyi);
		List listReal = new ArrayList();
		listReal.add(map);
		listReal.add(list);
		return listReal;
	}

	@Override
	public Map selectByLocal(String beginTime,String endTime,Integer rid) {
		JSONObject attribute = (JSONObject) session.getAttribute("user");
		String uname = attribute.getString("name");
		System.out.println(beginTime + "ddd" + endTime);
		Date dateBegin = null;
		Date dateEnd = null;
		if (beginTime != null  &&  endTime != null && beginTime != "" && endTime != "") {
			 dateBegin = DateUtil.getDate(beginTime);
			 dateEnd = DateUtil.getDate(endTime);
		}
		List<Comment> list = ictdao.selectByLocal(rid,dateBegin,dateEnd);
		int sumBuman = 0;
		int sumYiban = 0;
		int sumManyi = 0;
		int avg = 0;
		//查询会议室名称
		int sum = 0;
		for (int i = 0; i < list.size(); i++) {
			Integer cmgrade = list.get(i).getCmgrade();
			sum = cmgrade + sum;
			list.get(i).setUname(uname);
			if (cmgrade <= 1) {
				sumBuman++;
			}else if (cmgrade >=2 && cmgrade < 4) {
				sumYiban++;
			}else {
				sumManyi++;
			}
		}
		if (list.size() == 0) {
			avg = 0;
		}else {
			avg = (int)(sum/list.size());
		}
		Room selectroom = irdao.selectroom(rid);
		Map map = new HashMap();
		/*将评论列表存到Map中*/
		map.put("commentList", list);
		/*饼形图比例*/
		map.put("sumBuman", sumBuman);
		map.put("sumYiban", sumYiban);
		map.put("sumManyi", sumManyi);
		/*当值平均数*/
		map.put("avg", avg);
		map.put("rname", selectroom.getRname());
		System.out.println(map);
		return map;
	}

	@Override
	public int insertOneComment(Comment co) {
		// TODO Auto-generated method stub
		JSONObject attribute = (JSONObject) session.getAttribute("user");
		String uname = attribute.getString("name");
		co.setCmtime(new Date());
		co.setUname(uname);
		System.out.println(co);
		int state = ictdao.insertOneComment(co);
		return state;
	}

	@Override
	public int insertDefaultConmment() {
		Comment co = new Comment();
		co.setCmgrade(5);
		co.setCmtime(new Date());
		int state = ictdao.insertOneComment(co);
		//这里需要设置他的属性
		return state;
	}
}
