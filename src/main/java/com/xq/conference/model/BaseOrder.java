package com.xq.conference.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单
 * @ClassName BaseOrder
 * @Author yangweihang
 * @Date 2018年9月5日 上午11:24:55
 */
public class BaseOrder implements Serializable{
	private Integer oid;//订单id
	private String id;//产生订单的用户id --------------------------888
	private String name;//产生订单的用户名 --------------------------888
	private String tel;//产生订单的用户电话 --------------------------888
	private Date otime;//产生订单的时间
	private Integer otype;//订单类型(1.不重复预订  2.重复预订)
	private Integer opassstate;//审核部门是否通过(1.未通过  2.通过3.待审批  默认为3) ---------------888
	private Integer otakeorder;//服务状态(1.未接单 2.服务中3.服务完成  默认为1 )
	private Integer orderstate;//订单状态(0.以取消 1.未取消  2.已完成   数据库默认未取消为1)
	public Integer getOid() {
		return oid;
	}
	public void setOid(Integer oid) {
		this.oid = oid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public Date getOtime() {
		return otime;
	}
	public void setOtime(Date otime) {
		this.otime = otime;
	}
	public Integer getOtype() {
		return otype;
	}
	public void setOtype(Integer otype) {
		this.otype = otype;
	}
	public Integer getOpassstate() {
		return opassstate;
	}
	public void setOpassstate(Integer opassstate) {
		this.opassstate = opassstate;
	}
	public Integer getOtakeorder() {
		return otakeorder;
	}
	public void setOtakeorder(Integer otakeorder) {
		this.otakeorder = otakeorder;
	}
	public Integer getOrderstate() {
		return orderstate;
	}
	public void setOrderstate(Integer orderstate) {
		this.orderstate = orderstate;
	}
	public BaseOrder(Integer oid, String id, String name, String tel, Date otime, Integer otype, Integer opassstate,
			Integer otakeorder, Integer orderstate) {
		super();
		this.oid = oid;
		this.id = id;
		this.name = name;
		this.tel = tel;
		this.otime = otime;
		this.otype = otype;
		this.opassstate = opassstate;
		this.otakeorder = otakeorder;
		this.orderstate = orderstate;
	}
	public BaseOrder() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "BaseOrder [oid=" + oid + ", id=" + id + ", name=" + name + ", tel=" + tel + ", otime=" + otime
				+ ", otype=" + otype + ", opassstate=" + opassstate + ", otakeorder=" + otakeorder + ", orderstate="
				+ orderstate + "]";
	}
}
