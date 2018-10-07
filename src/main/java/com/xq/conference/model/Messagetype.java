package com.xq.conference.model;

/**
 * 消息
 * @ClassName Messagetype
 * @Author yangweihang
 * @Date 2018年9月5日 下午12:00:38
 */
public class Messagetype {
	private Integer mtid;//消息类型id
	private String mtname;//消息类型名称
	private Integer mtintervaltime;//消息提醒间隔时间
	public Integer getMtid() {
		return mtid;
	}
	public void setMtid(Integer mtid) {
		this.mtid = mtid;
	}
	public String getMtname() {
		return mtname;
	}
	public void setMtname(String mtname) {
		this.mtname = mtname;
	}
	public Integer getMtintervaltime() {
		return mtintervaltime;
	}
	public void setMtintervaltime(Integer mtintervaltime) {
		this.mtintervaltime = mtintervaltime;
	}
	public Messagetype() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Messagetype(Integer mtid, String mtname, Integer mtintervaltime) {
		super();
		this.mtid = mtid;
		this.mtname = mtname;
		this.mtintervaltime = mtintervaltime;
	}
	@Override
	public String toString() {
		return "Messagetype [mtid=" + mtid + ", mtname=" + mtname + ", mtintervaltime=" + mtintervaltime + "]";
	}
}
