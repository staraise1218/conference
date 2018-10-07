package com.xq.conference.model;

import java.util.Arrays;

/**
 * 服务人员订单列表
 * @author mxc
 * 2018年9月24日10:16:23
 * version 1.0
 */
public class ServiceOrder {
	/**
	 * 服务人员订单id
	 */
	private Integer soid;
	/**
	 * 关联的订单详情id 
	 */
	private Integer ocid;
	/**
	 * 服务人员id数组
	 */
	private Integer[] seridArry;
	
	public ServiceOrder(Integer soid, Integer ocid, Integer[] seridArry) {
		super();
		this.soid = soid;
		this.ocid = ocid;
		this.seridArry = seridArry;
	}

	public Integer getSoid() {
		return soid;
	}

	public void setSoid(Integer soid) {
		this.soid = soid;
	}

	public Integer getOcid() {
		return ocid;
	}

	public void setOcid(Integer ocid) {
		this.ocid = ocid;
	}

	public Integer[] getSeridArry() {
		return seridArry;
	}

	public void setSeridArry(Integer[] seridArry) {
		this.seridArry = seridArry;
	}

	public ServiceOrder() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "ServiceOrder [soid=" + soid + ", ocid=" + ocid + ", seridArry="
				+ Arrays.toString(seridArry) + "]";
	}
}
