package com.xq.conference.model;

import java.io.Serializable;
import java.util.Date;

import com.xq.conference.util.DateUtil;

/**
 * 评论
 * @ClassName Comment
 * @Author yangweihang
 * @Date 2018年9月5日 上午11:47:25
 */
public class Comment implements Serializable{
	private Integer cmid;//评论id
	private String cmcontent;//评论内容
	private Integer cmgrade;//分数
	private Date cmtime;//时间
	private Integer ocid;//订单内容id
	private Integer rid;//房间id
	private String uname;//用户名
	//自定义构造 用于默认好评
	public Comment(Integer cmgrade,Integer ocid, Integer rid, String uname) {
		super();
		this.cmgrade = 5;
		this.cmtime = DateUtil.getDate(DateUtil.getTime(), "yyyy-MM-dd HH:mm:ss");
		this.ocid = ocid;
		this.rid = rid;
		this.uname = uname;
	}
	
	
	public Integer getCmid() {
		return cmid;
	}
	public void setCmid(Integer cmid) {
		this.cmid = cmid;
	}
	public String getCmcontent() {
		return cmcontent;
	}
	public void setCmcontent(String cmcontent) {
		this.cmcontent = cmcontent;
	}
	public Integer getCmgrade() {
		return cmgrade;
	}
	public void setCmgrade(Integer cmgrade) {
		this.cmgrade = cmgrade;
	}
	public Date getCmtime() {
		return cmtime;
	}
	public void setCmtime(Date cmtime) {
		this.cmtime = cmtime;
	}
	public Integer getOcid() {
		return ocid;
	}
	public void setOcid(Integer ocid) {
		this.ocid = ocid;
	}
	public Integer getRid() {
		return rid;
	}
	public void setRid(Integer rid) {
		this.rid = rid;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public Comment(Integer cmid, String cmcontent, Integer cmgrade,
			Date cmtime, Integer ocid, Integer rid, String uname) {
		super();
		this.cmid = cmid;
		this.cmcontent = cmcontent;
		this.cmgrade = cmgrade;
		this.cmtime = cmtime;
		this.ocid = ocid;
		this.rid = rid;
		this.uname = uname;
	}
	public Comment() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Comment [cmid=" + cmid + ", cmcontent=" + cmcontent
				+ ", cmgrade=" + cmgrade + ", cmtime=" + cmtime + ", ocid="
				+ ocid + ", rid=" + rid + ", uname=" + uname + "]";
	}
}
