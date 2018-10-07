package com.xq.conference.model;

/**
 * 管理员
 * @ClassName Administrator
 * @Author yangweihang
 * @Date 2018年9月5日 下午2:55:16
 */
public class Administrator {
	private Integer aid;//管理员id
	private String aname;//管理员名
	private String apwd;//管理员密码
	private String aphone;//管理员电话
	public Administrator() {
	}
	public Administrator(Integer aid, String aname, String apwd, String aphone) {
		this.aid = aid;
		this.aname = aname;
		this.apwd = apwd;
		this.aphone = aphone;
	}
	public Integer getAid() {
		return aid;
	}
	public void setAid(Integer aid) {
		this.aid = aid;
	}
	public String getAname() {
		return aname;
	}
	public void setAname(String aname) {
		this.aname = aname;
	}
	public String getApwd() {
		return apwd;
	}
	public void setApwd(String apwd) {
		this.apwd = apwd;
	}
	public String getAphone() {
		return aphone;
	}
	public void setAphone(String aphone) {
		this.aphone = aphone;
	}
	@Override
	public String toString() {
		return "Administrator [aid=" + aid + ", aname=" + aname + ", apwd=" + apwd + ", aphone=" + aphone + "]";
	}
}
