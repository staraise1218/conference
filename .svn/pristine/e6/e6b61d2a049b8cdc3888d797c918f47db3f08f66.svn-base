package com.xq.conference.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 消息主体内容
 * @author 川
 * 2018年9月12日13:08:08
 */
public class MessageContent implements Serializable {
	private Integer mcid;//消息id
	private Integer mtid;//关联消息类型id
	private Integer ocid;//关联订单主体id
	private String mtcontent;//消息内容
	private Date mcdate;//消息生成日期
	private Integer readState;//消息已读未读 1.未读 2.已读 默认为1
	public Integer getMcid() {
		return mcid;
	}
	public void setMcid(Integer mcid) {
		this.mcid = mcid;
	}
	public Integer getMtid() {
		return mtid;
	}
	public void setMtid(Integer mtid) {
		this.mtid = mtid;
	}
	public Integer getOcid() {
		return ocid;
	}
	public void setOcid(Integer ocid) {
		this.ocid = ocid;
	}
	public String getMtcontent() {
		return mtcontent;
	}
	public void setMtcontent(String mtcontent) {
		this.mtcontent = mtcontent;
	}
	public Date getMcdate() {
		return mcdate;
	}
	public void setMcdate(Date mcdate) {
		this.mcdate = mcdate;
	}
	public Integer getReadState() {
		return readState;
	}
	public void setReadState(Integer readState) {
		this.readState = readState;
	}
	public MessageContent(Integer mcid, Integer mtid, Integer ocid, String mtcontent, Date mcdate, Integer readState) {
		super();
		this.mcid = mcid;
		this.mtid = mtid;
		this.ocid = ocid;
		this.mtcontent = mtcontent;
		this.mcdate = new Date();
		this.readState = readState;
	}
	public MessageContent() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "MessageContent [mcid=" + mcid + ", mtid=" + mtid + ", ocid=" + ocid + ", mtcontent=" + mtcontent
				+ ", mcdate=" + mcdate + ", readState=" + readState + "]";
	}
}
