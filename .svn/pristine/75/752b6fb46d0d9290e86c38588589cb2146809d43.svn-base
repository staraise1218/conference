package com.xq.conference.model;

/**
 * 会员
 * @ClassName User
 * @Author yangweihang
 * @Date 2018年9月5日 上午11:10:54
 */
public class User {
	private Integer id;//用户id
	private String name;//真实姓名
	private String pwd;//密码
	private String userName;//用户名
	private String phone;//手机号
	private String wechat;//微信号(ID)
	private String qqCode;//qq号(ID)
	private String email;//电子邮箱
	private String sessionId;//sessionId唯一标识，用于认证用户是否登录
	private String sourceType;//用户注册来源类别0.自主注册 1.审批注册    注释:微信调用返回1时，需走完善信息流程
	public User() {
	}
	public User(Integer id, String name, String pwd, String userName, String phone, String wechat, String qqCode, String email,
			String sessionId, String sourceType) {
		this.id = id;
		this.name = name;
		this.userName = userName;
		this.phone = phone;
		this.wechat = wechat;
		this.qqCode = qqCode;
		this.email = email;
		this.sessionId = sessionId;
		this.sourceType = sourceType;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getWechat() {
		return wechat;
	}
	public void setWechat(String wechat) {
		this.wechat = wechat;
	}
	public String getQqCode() {
		return qqCode;
	}
	public void setQqCode(String qqCode) {
		this.qqCode = qqCode;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", pwd=" + pwd + ", userName=" + userName + ", phone=" + phone
				+ ", wechat=" + wechat + ", qqCode=" + qqCode + ", email=" + email + ", sessionId=" + sessionId
				+ ", sourceType=" + sourceType + "]";
	}
}
