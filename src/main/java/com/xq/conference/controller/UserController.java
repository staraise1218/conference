package com.xq.conference.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.xq.conference.model.Administrator;
import com.xq.conference.model.AppreciationgoodsOrder;
import com.xq.conference.model.Basegoodsorder;
import com.xq.conference.model.Ordercontent;
import com.xq.conference.model.Power;
import com.xq.conference.model.UserPower;
import com.xq.conference.service.IAdministratorService;
import com.xq.conference.service.IOrderService;
import com.xq.conference.service.IPowerService;
import com.xq.conference.service.IUserPowerService;
import com.xq.conference.util.DateUtil;
import com.xq.conference.util.InterfaceCallAndReturn;
import com.xq.conference.util.MD5;
import com.xq.conference.util.PowerUtil;
import com.xq.conference.util.UrlUtil;

/**
 * 用户
 * @ClassName UserController
 * @Author yangweihang
 * @Date 2018年9月5日 下午4:17:13
 */
@RestController
@RequestMapping("/user")
public class UserController {
	
	
	private static ConcurrentHashMap<String, Integer> stateOfRid = new ConcurrentHashMap<String, Integer>();//存放rid，加入同步锁
	
	@Autowired
	private IAdministratorService ias;
	
	@Autowired
	private IPowerService ips;
	
	@Autowired
	private IOrderService iorders;
	
	@Autowired
	private IUserPowerService iups;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private  ServletContext application;
	
	/**
	 * 用户登录
	 * yangweihang
	 * @Date 2018年9月5日 下午4:27:27
	 * @param userName 用户名称
	 * @param pwd 
	 * @return 
	 */
	@RequestMapping("/login")
	public Integer login(String interfaceNum,String userNames,String password) {
		Integer mv = 0;
		String url = UrlUtil.URL+"userController/userLoginIn";
		try {
			StringBuffer sb = new StringBuffer("?");
			sb.append("interfaceNum=" + interfaceNum);
			sb.append("&userNames=" + userNames);
			sb.append("&password=" + password);
			System.out.println(url + sb);
			JSONObject json = InterfaceCallAndReturn.returnJson(url, sb, "data");
			String username = (String)json.get("userName");
			String id = (String)json.get("id");
			//管理员登录
			Administrator a = ias.selectByLogin(userNames, password);
			//用户
			if(username != null) {
				//会话跟踪用户
				session.setAttribute("user", json);
				System.out.println("Session:" + session.getAttribute("user"));
				//按照权限分配到不同的页面
				Power p = ips.selectByUsername(username);
				if(p == null) {//普通用户
					mv = 1;
				}else if(p.getPid() == PowerUtil.TWO) {//主管部门
					mv = 2;
				
				}else if(p.getPid() == PowerUtil.THREE) {//服务部门
					mv = 3;
				//没有权限的用户或是普通用户都走普通用户的页面
				}else {
					mv = 4;
				}
				//按用户名查询权限
				UserPower u = iups.selectByUsername(username);
				if(u == null) {
					//增加用户权限
					iups.insertuserpower(username);
				}
			//管理员
			}else if(a != null) {
				//会话跟踪管理员名
				session.setAttribute("username", a.getAname());
				mv = 5;//管理员首页
			//没有找到用户
			}else {
				mv = 6;//用户不存在
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	/**
	 * 页面传来 日期、会议室id、上传附件（采用时间MD5+文件名）---预订会议室统一入口
	 * @param oc OrderContent对象
	 * @param otype 是否重复预定(1.不重复 2.重复)
	 * @param unit 重复的单位（1.天 2.周 3.月）
	 * @param num 数量
	 * @return -3：用户的预定时间在当前时间之前 -2：会议室被删除 -1:该会议室正在被编辑 0:预定冲突 1：预定成功 
	 */
	@RequestMapping("/reserve")
	public int reserve(Ordercontent oc,int otype,int unit,int num,String[] boname,Integer[] bonum,String[] aoname,Integer[] aonum,Double[] aoprice,MultipartFile ocaccessoryfile,String stringocbegintime,String stringocendtime,String stringocdockingtime) {
		
		//System.out.println("Map:" + stateOfRid);
		oc.setOcbegintime(DateUtil.getDate(stringocbegintime,"yyyy-MM-dd HH:mm:ss"));
		oc.setOcendtime(DateUtil.getDate(stringocendtime,"yyyy-MM-dd HH:mm:ss"));
		oc.setOcdockingtime(DateUtil.getDate(stringocdockingtime,"yyyy-MM-dd HH:mm:ss"));
		int passState = 0 ;
		//预定之前先检查此会议室是否被编辑
		/*if (stateOfRid.get(oc.getRid().toString()) != null) {
			return -1;//直接返回 表示该会议室正在被编辑
		}*/
		//如果没有被编辑 那么应该将此房间id添加到map中，如果房间被释放 那么应该被清除 有按钮
		//stateOfRid.put(oc.getRid().toString(), oc.getRid());
		//System.out.println(oc.gets);
		/*此部分主要审核是否生成订单部分*/
		int check = checkYN(oc.getOcbegintime() ,oc.getOcendtime() ,oc.getRid());
		if (check == 2) {
			System.out.println("此时间区间可以被预定!");
			boname = new String[] { "哈哈哈", "嘿嘿嘿", "呵呵呵" };
			bonum = new Integer[] { 1, 2, 3 };
			aoname = new String[] { "哎呀", "我去" };
			aonum = new Integer[] { 5, 6 };
			aoprice = new Double[] { 5.2, 6.1 };
			//录入一条订单信息
			List<Basegoodsorder> blist = new ArrayList<Basegoodsorder>();
			for (int i = 0; i < boname.length; i++) {
				Basegoodsorder b = new Basegoodsorder(null, null, boname[i], bonum[i]);
				blist.add(b);
			}
			List<AppreciationgoodsOrder> alist = new ArrayList<AppreciationgoodsOrder>();
			for (int i = 0; i < aoname.length; i++) {
				AppreciationgoodsOrder a = new AppreciationgoodsOrder(null, null, aoname[i], aonum[i], aoprice[i]);
				alist.add(a);
			}
			//获得项目路径
			String pUrl = application.getRealPath("/ocaccessory/");
			StringBuffer url = new StringBuffer(pUrl);
			BufferedOutputStream out = null;
			try {
				String name = ocaccessoryfile.getOriginalFilename();
				byte[] bytes = ocaccessoryfile.getBytes();
				url.append(MD5.GetMD5Code(DateUtil.getTime()));
				url.append(name);
				out = new BufferedOutputStream(new FileOutputStream(new File(url.toString())));
				out.write(bytes);
				System.out.println(MD5.GetMD5Code(DateUtil.getTime()));
				out.flush();
				out.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
			System.out.println(url.toString());
			oc.setOcaccessory(url.toString());
			int state = iorders.insertorder(oc, otype, unit, num, blist, alist);
			if (state == 1) {
				return 1;
			}
		}else if(check == 1) {
			passState = 0;//预订有冲突
		}else if (check == -1) {
			passState = -2;//会议室被删除
		}else if(check == 0) {
			passState = -3;//预订当前时间之前的的时间段
		}
		releaseRoom(oc.getRid());//订单完成释放此房间
		return passState;
	}
	
	/**
	 * 判断用户的日期是否冲突（ -1.该会议室被删除 0：代表预订当前时间之前的的时间段（无意义）1.有冲突 2.可以预定）
	 * @param begintime 用户输入的开始时间
	 * @param endtime 用户输入的结束时间
	 * @param rid 房间id
	 * @return 是否可以预定
	 */
	@RequestMapping("/checkYN")
	public int checkYN(Date begintime,Date endtime,Integer rid) {
		int YN = 0;
		//先判断是不是大于今天的日期么
		Date ocbegintime = begintime;//该用户定义的开始时间
		Date ocendtime = endtime;//该用户定义的结束时间
		Date today = new Date();
		if (ocbegintime.getTime() < today.getTime()) {
			YN =  0;//表示预定当前日期之前的了
		}
		//判断该日期跟别的预定情况有没有冲突
		YN = iorders.selectChongtu(ocbegintime,ocendtime,rid);
		return YN;
	}
	
	/**
	 * 用户释放此会议室
	 * @param rid
	 * @return 
	 */
	@RequestMapping("/releaseRoom")
	public void releaseRoom(Integer rid) {
		stateOfRid.remove(rid.toString());
	}
	
	/**
	 * 锁住该会议室
	 */
	public void lookThisRoom(Integer rid) {
		stateOfRid.put(rid.toString(), rid);
	}
	
	/**
	 * 查看此房间是否被锁住 如果没被锁住那么可以进入 并且锁上此会议室（返回0锁成功） 如果已经被锁上返回-1 该会议室不可被编辑
	 * @param rid
	 * @return
	 */
	@RequestMapping("/checkLockRoom")
	public int checkLookRoom(Integer rid) {
		System.out.println(stateOfRid);
		if (stateOfRid.get(rid.toString()) != null) {
			return -1;//直接返回 表示该会议室正在被编辑
		}else {
			lookThisRoom(rid);//如果没锁柱就锁上
			return 0;//锁成功返回0
		}
	}
	/**
	 * 根据订单详细表的id下载该上传的附件
	 * @param ocid 详细表的id
	 * return 0.没有附件 1.下载成功
	 */
	@RequestMapping("/downOcaccessory")
	public int downOcaccessory(int ocid,HttpServletResponse response) {
		String filePath = iorders.selectOcaccessoryPath(ocid);
		if (filePath != null) {
			File filetemp = new File(filePath);
			filetemp.getName();
			System.out.println(filePath);
			try {
				//设置字符集
		        response.setContentType("application/octet-stream; charset=utf-8");
		        response.setHeader("Content-Disposition", "attachment; filename="+new String(filetemp.getName().getBytes(),"ISO8859-1"));
				//获取输入流
				BufferedInputStream in = new BufferedInputStream(new FileInputStream(filePath));
				//获取输出流
				BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
				
				int r;
				while ((r = in.read()) != -1) {
					out.write(r);
				}
				out.flush();
				out.close();
				in.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return ocid;
	}

	/**
	 * 根据id查询该会议室在页面当前周的订单 用于页面按照上一周下一周按钮来查询（档用户进来首页默认是当前时间所在的周 0为当前日期所在周）
	 * 分周查询 以当前时间所在周为基准、前台判断是不是自己用isMe字段（1.是自己）
	 * @param weekNum 下、上几周
	 * @param rid 房间id
	 * @return 临时模型对象
	 * @throws Exception
	 */
	/*@RequestMapping(value="/showRoomByNowWeek",produces="application/json;charset=UTF-8")
	public List showRoomByNowWeek(int weekNum,int rid) throws Exception {
		int type = 0;//以当前时间为基准
		List list = iorders.selectListByNowWeek(weekNum,rid,type,"");
		System.out.println(list);
		return list;
	}*/
	
	
	/**
	 * 根据id查询该会议室在页面      用户设定日期的     当前周的订单 用于页面按照上一周下一周按钮来查询（档用户进来首页默认是当前时间所在的周 0为当前日期所在周）
	 * 分周查询 以当前时间所在周为基准、前台判断是不是自己用isMe字段（1.是自己）
	 * @param weekNum 下、上几周
	 * @param rid 房间id
	 * @param dateAssign 用户自定义的时间 格式为 yyyy-MM-dd
	 * @return 临时模型对象
	 * @throws Exception
	 */
	@RequestMapping(value="/showRoomByAssignWeek",produces="application/json;charset=UTF-8")
	public List showRoomByAssignWeek(int rid,String dateAssign) throws Exception  {
		List list = iorders.selectListByNowWeek(rid,dateAssign);
		return list;
	}
	
	@RequestMapping(value="/showRoomByAssignDay",produces="application/json;charset=UTF-8")
	public List showRoomByAssignDay(int rid,String dateAssign) throws Exception  {
		List list = iorders.selectListByNowday(rid,dateAssign);
		return list;
	}

	/**
	 * 退出
	 * yangweihang
	 * @Date 2018年9月6日 下午7:34:07
	 * @return
	 */
	@RequestMapping("/dropout")
	public String dropout() {
		JSONObject attribute = (JSONObject) session.getAttribute("user");
		String sessionId = attribute.getString("sessionId");
		//调用对方接口 传过去sessionid
		StringBuffer sb = new StringBuffer(UrlUtil.URL);
		sb.append("userController/userLogout");
		String url = sb.toString();
		StringBuffer param = new StringBuffer("?");

		param.append("sessionId=");
		param.append(sessionId);
		session.removeAttribute("user");
		return "退出";
	}
	
	
	/**
	 * 客户端获取session
	 * @return
	 */
	@RequestMapping("/outPutSession")
	public JSONObject outPutSession() {
		JSONObject attribute = (JSONObject) session.getAttribute("user");
		return attribute;
	}
}
