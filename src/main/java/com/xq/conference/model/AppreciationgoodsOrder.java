package com.xq.conference.model;

/**
 * 增值物品订单
 * @ClassName Appreciationgoodsorder
 * @Author yangweihang
 * @Date 2018年9月5日 上午11:54:00
 */
public class AppreciationgoodsOrder {
	private Integer aoid;//增值物品订单id
	private Integer ocid;//订单内容id
	private String aoname;//物品名称
	private Integer aonum;//物品数量
	private Double aoprice;//物品单价---------------------888
	public AppreciationgoodsOrder() {
	}
	public AppreciationgoodsOrder(Integer aoid, Integer ocid, String aoname, Integer aonum, Double aoprice) {
		this.aoid = aoid;
		this.ocid = ocid;
		this.aoname = aoname;
		this.aonum = aonum;
		this.aoprice = aoprice;
	}
	public Integer getAoid() {
		return aoid;
	}
	public void setAoid(Integer aoid) {
		this.aoid = aoid;
	}
	public Integer getOcid() {
		return ocid;
	}
	public void setOcid(Integer ocid) {
		this.ocid = ocid;
	}
	public String getAoname() {
		return aoname;
	}
	public void setAoname(String aoname) {
		this.aoname = aoname;
	}
	public Integer getAonum() {
		return aonum;
	}
	public void setAonum(Integer aonum) {
		this.aonum = aonum;
	}
	public Double getAoprice() {
		return aoprice;
	}
	public void setAoprice(Double aoprice) {
		this.aoprice = aoprice;
	}
	@Override
	public String toString() {
		return "AppreciationgoodsOrder [aoid=" + aoid + ", ocid=" + ocid + ", aoname=" + aoname + ", aonum=" + aonum
				+ ", aoprice=" + aoprice + "]";
	}
}
