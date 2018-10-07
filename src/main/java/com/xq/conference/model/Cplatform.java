package com.xq.conference.model;

import java.io.Serializable;

/**
 * 台型
 * @ClassName Cplatform
 * @Author yangweihang
 * @Date 2018年9月5日 上午11:59:11
 */
public class Cplatform implements Serializable{
	private Integer cid;//台型id
	private String cname;//台行名称
	private String cpic;//台型图片
	public Cplatform() {
	}
	public Cplatform(Integer cid, String cname, String cpic) {
		this.cid = cid;
		this.cname = cname;
		this.cpic = cpic;
	}
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getCpic() {
		return cpic;
	}
	public void setCpic(String cpic) {
		this.cpic = cpic;
	}
	@Override
	public String toString() {
		return "Cplatform [cid=" + cid + ", cname=" + cname + ", cpic=" + cpic + "]";
	}
}
