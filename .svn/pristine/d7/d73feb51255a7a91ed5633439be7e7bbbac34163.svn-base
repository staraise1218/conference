package com.xq.conference.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.xq.conference.model.OrderContentAndRoom;
import com.xq.conference.model.Ordercontent;
import com.xq.conference.model.Room;
import com.xq.conference.service.IOrderService;
import com.xq.conference.service.IRoomService;

/**
 * 会议室控制器
 * @author 川
 * 2018年9月6日09:37:14
 * version 1.0
 */
@RestController
@RequestMapping("/room")
public class RoomController {
	
	@Autowired
	private IRoomService irs;//会议室服务
	
	@Autowired
	private IOrderService iorders;//订单服务
	
	@Autowired
	private  ServletContext application;//App上下文
	
	@Autowired
	private  HttpServletRequest request;//App上下文
	
	/**
	 * 插入一条会议室信息
	 * @param room 会议室对象
	 * @return 是否插入成功 （0 没成功 1 成功）
	 */
	@RequestMapping("/insertOneRoom")
	public int insertOneRoom(Room room) {
		int state = irs.insertOneRoomMy(room);
		return state;
	}
	
	
	/**
	 * 调用三方接口 获取这个人的医院id
	 * @return 返回医院编码
	 *//*
	public String selectHcodeByMyself() {
		return null;
	}*/
	/**
	 * 通过他们的接口获取该医院的所有部门信息用于录入会议室的管理部门的选择
	 * @return 该医院的部门信息列表
	 */
	@RequestMapping("/getDepartmentListByOther")
	public List getDepartmentListByOther() {
		List list = irs.getDepartmentListByOther();
		return list;
	}
	@RequestMapping("/selectRoomUrl")
	public String selectRoomUrl() {
		return null;
	}
	
	/**
	 * 新增会议室并且上传多个图片
	 * yangweihang
	 * @Date 2018年9月7日 上午9:04:04
	 * @param r
	 * @return
	 */
	@RequestMapping("/insertroom")
	public String insertRoom(Room r,MultipartFile[] files) {
		
		System.out.println("文件数组：" + files);
		System.out.println("files"+files.length);
		String str = "";
		StringBuffer sb = new StringBuffer();
		//批量上传图片
		String uploadDir = application.getRealPath("/images");
		System.out.println("服务器路径：" + uploadDir);
		//String uploadDir = request.getSession().getServletContext().getRealPath("/img");
        MultipartFile file = null;
        BufferedOutputStream stream = null;
        if (files != null) {
			for (int i = 0; i < files.length; ++i) {
				file = files[i];
				if (!file.isEmpty()) {
					try {
						byte[] bytes = file.getBytes();
						stream = new BufferedOutputStream(
								new FileOutputStream(new File(uploadDir + file.getOriginalFilename())));
						stream.write(bytes);
						stream.flush();
						stream.close();
						sb.append(file.getOriginalFilename());
						sb.append(",");
					} catch (Exception e) {
						stream = null;
						System.out.println("--------------");
						return "You failed to upload " + i + " => " + e.getMessage();
					}
				} else {
					System.out.println("===============");
					return "You failed to upload " + i + " because the file was empty.";
				}
			} 
		}
        System.out.println("sb"+sb.toString());
		String rpic = sb.toString();
        r.setRpic(rpic);
        str = "upload successful";
		//新增会议室
		int result = irs.insertRoom(r);
		if(result > 0) {
			str = "新增成功！"; 
		}else {
			str = "新增失败！";
		}
		return str;
	}
	
	/**
	 * 虚拟删除会议室
	 * yangweihang
	 * @Date 2018年9月7日 下午5:17:30
	 * @param rid
	 * @return
	 */
	@RequestMapping("deleteroom")
	public String deleteroom(int rid) {
		String str = "";
		int result = irs.deleteroom(rid);
		if(result > 0) {
			str = "删除成功！";
		}else {
			str = "删除失败！";
		}
		return str;
	}
	
	
	/**
	 * 根据该医院的编码和主管单位编码来获取整个医院大区域名称
	 * 对方接口：hospitalController/getHospitalGridInfo
	 * @return 该区域info
	 */
	@RequestMapping("/getBigLocalListByOther")
	public String getBigLocalListByOther() {
		String hname = irs.getBigLocalListByOther();
		return hname;
	}
	
	/**
	 * 修改锁定未锁定
	 * yangweihang
	 * @Date 2018年9月7日 下午4:29:12
	 * @param rstate
	 * @return
	 */
	@RequestMapping("/updaterstate")
	public String updaterstate(int rstate,String rname) {
		String str = "";
		int result = irs.updaterstate(rstate, rname);
		if(result > 0) {
			if(rstate == 1) {
				str = "解锁成功！";
			}else {
				str = "锁定成功！";
			}
		}else {
			str = "修改失败";
		}
		return str;
	}
	
	/**
	 * 编辑会议室
	 * yangweihang
	 * @Date 2018年9月7日 下午4:48:22
	 * @param r
	 * @return
	 */
	@RequestMapping("/updateroom")
	public String updateroom(Room r,MultipartFile[] files) {
		String str = "";
		StringBuffer sb = new StringBuffer();
		//批量上传图片
		String uploadDir = application.getRealPath("/images/roomimg/");
        MultipartFile file = null;
        BufferedOutputStream stream = null;
        for (int i = 0; i < files.length; ++i) {
            file = files[i];
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    stream = new BufferedOutputStream(new FileOutputStream(new File(uploadDir + file.getOriginalFilename())));
                    stream.write(bytes);
                    stream.flush();
                    stream.close();
                    sb.append(uploadDir + file.getOriginalFilename());
                    sb.append(",");
                } catch (Exception e) {
                    stream = null;
                    return "You failed to upload " + i + " => "
                            + e.getMessage();
                }
            } else {
                return "You failed to upload " + i
                        + " because the file was empty.";
            }
        }
        String rpic = sb.toString();
        r.setRpic(rpic);
        str = "upload successful";
        //查询要修改的会议室
        Room sels = irs.selectroom(r.getRid());
		//修改会议室
        String rlocation = r.getRdepartment()+r.getRbudingnubmer()+"楼"+r.getRfloor()+"层"+r.getRhousenumber();
        r.setRlocation(rlocation);
		int result = irs.updateroom(r);
		if(result > 0) {
			str = "修改成功！";
		}else {
			str = "修改失败！";
		}
		return str;
	}
	
	/**
	 * 生成excle
	 * @param response 响应对象
	 * @param oid 订单id
	 */
	@RequestMapping("/getRoomDown")
	public void getRoomDown(HttpServletResponse response,int oid) {
		//创建一个只有String的list
		List<String> list = new ArrayList<String>();
		//这里查出 "会议室名称","预定时间","对接时间","服务状态","预定人","预定人电话","星级评价"这几个字段的值  然后装进list中
		try {
			//设置响应为下载
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-disposition", "attachment; filename="
					+ new String("教室信息.xlsx".getBytes("gb2312"), "iso8859-1"));
			//1.建立一个excle
			Workbook book = new HSSFWorkbook();
			//2.在这个excle建立一个分表
			Sheet sheet1 = (Sheet) book.createSheet();
			//设置第一行
			Row row = sheet1.createRow(0);
			//设置第二行
			Row row2 = sheet1.createRow(1);
			String[] roomInfo = new String[]{"会议室名称","预定时间","对接时间","服务状态","预定人","预定人电话","星级评价"};
			//设置第一行各个内容
			for (int i = 0; i < roomInfo.length; i++) {
				Cell cell = row.createCell(i);
				Cell cell2 = row2.createCell(i);
				cell.setCellValue(roomInfo[i]);
				cell2.setCellValue("ddd");//这个“ddd”是测试 只要把list中的每一个值放进去就行了
			}
			// 写出
			book.write(response.getOutputStream());
		} catch (Exception e) {
		}
	}
	
	/**
	 * 形成会议室的excle
	 * yangweihang
	 * @Date 2018年9月8日 上午10:58:03
	 * @return
	 */
	@RequestMapping("/getexcle")
	public String getexcle(HttpServletResponse response) {//设置响应为下载
		try {
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-disposition",
					"attachment; filename=" + new String("教室信息.xlsx".getBytes("gb2312"), "iso8859-1"));
			//第一步创建workbook 
			HSSFWorkbook wb = new HSSFWorkbook();
			//第二步创建sheet 
			HSSFSheet sheet = wb.createSheet("会议室");
			//第三步创建行row:添加表头0行 
			HSSFRow row = sheet.createRow(0);
			HSSFCellStyle style = wb.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//居中 
			//第四步创建单元格 
			HSSFCell cell = row.createCell(0);//第一个单元格 
			cell.setCellValue("序号");//设定值 
			cell.setCellStyle(style);//内容居中 
			cell = row.createCell(1);//第二个单元格   
			cell.setCellValue("会议室名称");
			cell.setCellStyle(style);
			cell = row.createCell(2);//第三个单元格  
			cell.setCellValue("所属部门");
			cell.setCellStyle(style);
			cell = row.createCell(3);//第四个单元格  
			cell.setCellValue("楼号");
			cell.setCellStyle(style);
			cell = row.createCell(4);//第五个单元格  
			cell.setCellValue("楼层");
			cell.setCellStyle(style);
			cell = row.createCell(5);//第六个单元格  
			cell.setCellValue("门牌号");
			cell.setCellStyle(style);
			cell = row.createCell(6);//第七个单元格  
			cell.setCellValue("间隔时间");
			cell.setCellStyle(style);
			cell = row.createCell(7);//第八个单元格  
			cell.setCellValue("位置");
			cell.setCellStyle(style);
			cell = row.createCell(8);//第九个单元格  
			cell.setCellValue("简介");
			cell.setCellStyle(style);
			//第五步插入数据 
			List<Room> list = irs.selectByRoom();
			for (int i = 0; i < list.size(); i++) {
				Room r = list.get(i);
				//创建行 
				row = sheet.createRow(i + 1);
				//创建单元格并且添加数据 
				row.createCell(0).setCellValue(i + 1);
				row.createCell(1).setCellValue(r.getRname());
				row.createCell(2).setCellValue(r.getRdepartment());
				row.createCell(3).setCellValue(r.getRbudingnubmer());
				row.createCell(4).setCellValue(r.getRfloor());
				row.createCell(5).setCellValue(r.getRhousenumber());
				row.createCell(6).setCellValue(r.getRinterval());
				row.createCell(7).setCellValue(r.getRlocation());
				row.createCell(8).setCellValue(r.getRintroduce());
			}
			//第六步将生成excel文件保存到指定路径下 
			wb.write(response.getOutputStream());
		} catch (Exception e) {
		}
		return "Excel文件生成成功...";
	}
	
	
	/**
	 * 单个上传图片
	 * yangweihang
	 * @Date 2018年9月7日 上午11:45:38
	 * @param uploadDir
	 * @param file
	 */
	public String executeupload(String uploadDir,MultipartFile file) {
		if (file.isEmpty()) {
            return "文件为空";
        }
        // 获取文件名
        String fileName = file.getOriginalFilename();
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        // 文件上传后的路径
        String filePath = uploadDir;
        File dest = new File(filePath + fileName);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            return "上传成功";
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上传失败";
	}
	
	/**
	 * 时间间隔设置
	 * yangweihang
	 * @Date 2018年9月7日 下午9:04:05
	 * @param rinterval
	 * @return
	 */
	@RequestMapping("/updaterinterval")
	public String updaterinterval(int rinterval,int rid) {
		String str = "";
		int result = irs.updaterinterval(rinterval,rid);
		if(result > 0) {
			str = "设置成功！";
		}else {
			str = "设置失败！";
		}
		return str;
	}

	/**
	 * 查询会议室
	 * yangweihang
	 * @Date 2018年9月7日 下午9:12:04
	 * @return
	 */
	@RequestMapping("/selectByRoom")
	public List<Room> selectByRoom(){
		return irs.selectByRoom();
	}
	
	/**
	 * 页面上 鼠标悬浮 查询该会议室的详情
	 * @param rid 会议室id
	 */
	@RequestMapping("/selectOneRoomByRid")
	public Room selectOneRoomByRid(int rid) {
		// TODO Auto-generated method stub
		Room selectroom = irs.selectroom(rid);
		return selectroom;
	}
	
	/*@RequestMapping("/outPutPicStream")
	public void outPutPicStream(int rid,HttpServletResponse response) {
		
		//根据rid查图片路径
		Room selectroom = irs.selectroom(rid);
		String rpic = selectroom.getRpic();
		String realPath = application.getRealPath("");
		File file = new File(realPath + rpic);
		response.setContentType("multipart/form-data");
		try {
			ServletOutputStream outputStream = response.getOutputStream();//输出流
			InputStream inputStream = new FileInputStream(file);
			int len = 0;
			byte[] buffer = new byte[1024 * 10];
			while ((len = inputStream.read(buffer)) != -1){
				outputStream.write(buffer,0,len);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	/**
	 * 根据一个订单内容id查询一个对象
	 * @param ocid 对单内容id
	 * @return 一个订单主体对象
	 */
	@RequestMapping("/selectOneRoomAndRid")
	public Ordercontent selectOneRoomOrderAndRid(int ocid) {
		//查询该订单详情
		Ordercontent oc = iorders.selectOneByRid(ocid);
		return oc;
	}
	
	/**
	 * 根据会议室的楼号、楼层、门牌号、医院编码确定一个会议室的订单列表
	 * @param rbudingnubmer 楼号
	 * @param rfloor 楼层
	 * @param rname 会议室名称
	 * @param weekNum 当前日期的第几周（用于页面上按周翻页）
	 * @return 一个房间指定周的订单列表
	 */
	@RequestMapping(value = "/selectOneRoomAndOrderByLocal" , produces="application/json;charset=UTF-8")
	public List selectOneRoomByLocal(int rbudingnubmer,int rfloor,String rname,int weekNum,String dateAssign) {
		
		List<OrderContentAndRoom> list = irs.selectOneRoomByLocal(rbudingnubmer,rfloor,rname,weekNum,dateAssign);
		return list;
	}
	
	/**
	 * 根据会议室的名医院编码确定一个会议室的订单列表
	 * @param rname 会议室名
	 * @param weekNum 当前日期的第几周
	 * @return 一个房间指定周的订单列表
	 */
	@RequestMapping( value= "/selectOneRoomAndOrderByRname" , produces="application/json;charset=UTF-8")
	public List selectOneRoomAndOrderByRname(String rname,int weekNum,String dateAssign) {
		List<OrderContentAndRoom> list = irs.selectOneRoomAndOrderByRname(rname,weekNum,dateAssign);
		return list;
	}
	
	/**
	 * 查询该医院所有会议室的常用会议室(前五条排行)
	 * @return 会议室列表
	 */
	@RequestMapping(value = "/selectOften",produces="application/json;charset=UTF-8")
	public List<Room> selectOften() {
		List list = irs.selectListByOften();
		return list;
	}
	
	/**
	 * 根据医院编码和部门编码查询会议室列表 用于下拉列表---------可以用于主管部门的筛选
	 * @return 会议室列表
	 */
	@RequestMapping("/selectRoomByHcodeAndDeptCode")
	public List selectRoomByHcodeAndDeptCode() {
		List list = irs.selectRoomByHcodeAndDeptCode();
		return list;
	}
	
	/**
	 * 根据医院编码查询会议室列表 用于下拉列表---------可以用于服务部门的筛选
	 * @return 会议室列表
	 */
	@RequestMapping("/selectRoomByHcode")
	public List selectRoomByHcode() {
		List list = irs.selectRoomByHcode();
		return list;
	}
	
	/**
	 * 根据用户的筛选来选出符合要求的会议室
	 * @return 符合要求会议室的列表
	 * @param rname 会议时间名称
	 * @param rbudingnubmer 楼号
	 * @param rfloor 楼层
	 */
	@RequestMapping("/selectRoomByClientInsert")
	public Map selectRoomByClientInsert(String rname,Integer rbudingnubmer,Integer rfloor,Integer page,Integer pageSize) {
		Map map = irs.selectRoomByClientInsert(rname,rbudingnubmer,rfloor,page,pageSize);
		return map;
	}

	/**
	 * 根据医院code来获取该医院的所有部门
	 * @return
	 */
	@RequestMapping("/selectDeptNameLitsByHcode")
	public List selectDeptNameLitsByHcode() {
		List list = irs.selectDeptNameLitsByHcode();
		return list;
	}
}
