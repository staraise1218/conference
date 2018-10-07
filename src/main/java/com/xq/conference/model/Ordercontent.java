package com.xq.conference.model;

import java.io.Serializable;
import java.util.Date;

import com.xq.conference.util.DateUtil;

/**
 * 订单内容
 * @ClassName Ordercontent
 * @Author yangweihang
 * @Date 2018年9月5日 上午11:31:13
 */


public class Ordercontent implements Serializable{
	private Integer ocid;//订单内容id
	private Integer oid;//订单id
	private Integer rid;//会议室id
	private Integer ocnum;//参会人数
	private Date ocbegintime;//开始时间 --------------------------------------888
	private Date ocendtime;//结束时间
	private Integer cid;//台型id###########***##########
	private String ocagenda;//议程
	private Integer ocusenum;//会议室用人数
	private String ocarrangement;//会议布置情况
	private String ocsourcename;//会议主题
	private String ocsourcephone;//对接状态
	private Integer ocdockingstate;//是否与服务人员对接（1.是 2.否）
	private Date ocdockingtime;//对接时间 ------------------------------------888
	private String ocmaterialsname;//会议物资联系人
	private String ocmaterialsphone;//会议物资联系人电话
	private String ocusename;//会议使用联系人
	private String ocusephone;//会议使用联系人电话
	private String ocreservename;//会议预定联系人
	private String ocreservephone;//会议预定联系人电话
	private Integer ocbigcarnum;//大车位数目
	private Integer ocsmallnum;//小车位数目
	private Integer ocprintnum;//会议议程打印份数
	private Integer ocplatform;//主席台是否摆放（1.是 2.否）
	private Integer ocshowplatform;//展台个数
	private Integer ocshowshelf;//展架个数
	private String ocpower;//功率
	private String ocpeoname;//参会人名
	private String ocaccessory;//附件
	private String ocremark;//备注
	private Integer serid;//服务人员id
	
	private Room r;
	
	
	
	public Room getR() {
		return r;
	}
	public void setR(Room r) {
		this.r = r;
	}
	public Integer getOcid() {
		return ocid;
	}
	public void setOcid(Integer ocid) {
		this.ocid = ocid;
	}
	public Integer getOid() {
		return oid;
	}
	public void setOid(Integer oid) {
		this.oid = oid;
	}
	public Integer getRid() {
		return rid;
	}
	public void setRid(Integer rid) {
		this.rid = rid;
	}
	public Integer getOcnum() {
		return ocnum;
	}
	public void setOcnum(Integer ocnum) {
		this.ocnum = ocnum;
	}
	public Date getOcbegintime() {
		return ocbegintime;
	}
	public void setOcbegintime(Date ocbegintime) {
		this.ocbegintime = ocbegintime;
	}
	public Date getOcendtime() {
		return ocendtime;
	}
	public void setOcendtime(Date ocendtime) {
		this.ocendtime = ocendtime;
	}
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public String getOcagenda() {
		return ocagenda;
	}
	public void setOcagenda(String ocagenda) {
		this.ocagenda = ocagenda;
	}
	public Integer getOcusenum() {
		return ocusenum;
	}
	public void setOcusenum(Integer ocusenum) {
		this.ocusenum = ocusenum;
	}
	public String getOcarrangement() {
		return ocarrangement;
	}
	public void setOcarrangement(String ocarrangement) {
		this.ocarrangement = ocarrangement;
	}
	public String getOcsourcename() {
		return ocsourcename;
	}
	public void setOcsourcename(String ocsourcename) {
		this.ocsourcename = ocsourcename;
	}
	public String getOcsourcephone() {
		return ocsourcephone;
	}
	public void setOcsourcephone(String ocsourcephone) {
		this.ocsourcephone = ocsourcephone;
	}
	public Integer getOcdockingstate() {
		return ocdockingstate;
	}
	public void setOcdockingstate(Integer ocdockingstate) {
		this.ocdockingstate = ocdockingstate;
	}
	public Date getOcdockingtime() {
		return ocdockingtime;
	}
	public void setOcdockingtime(Date ocdockingtime) {
		this.ocdockingtime = ocdockingtime;
	}
	public String getOcmaterialsname() {
		return ocmaterialsname;
	}
	public void setOcmaterialsname(String ocmaterialsname) {
		this.ocmaterialsname = ocmaterialsname;
	}
	public String getOcmaterialsphone() {
		return ocmaterialsphone;
	}
	public void setOcmaterialsphone(String ocmaterialsphone) {
		this.ocmaterialsphone = ocmaterialsphone;
	}
	public String getOcusename() {
		return ocusename;
	}
	public void setOcusename(String ocusename) {
		this.ocusename = ocusename;
	}
	public String getOcusephone() {
		return ocusephone;
	}
	public void setOcusephone(String ocusephone) {
		this.ocusephone = ocusephone;
	}
	public String getOcreservename() {
		return ocreservename;
	}
	public void setOcreservename(String ocreservename) {
		this.ocreservename = ocreservename;
	}
	public String getOcreservephone() {
		return ocreservephone;
	}
	public void setOcreservephone(String ocreservephone) {
		this.ocreservephone = ocreservephone;
	}
	public Integer getOcbigcarnum() {
		return ocbigcarnum;
	}
	public void setOcbigcarnum(Integer ocbigcarnum) {
		this.ocbigcarnum = ocbigcarnum;
	}
	public Integer getOcsmallnum() {
		return ocsmallnum;
	}
	public void setOcsmallnum(Integer ocsmallnum) {
		this.ocsmallnum = ocsmallnum;
	}
	public Integer getOcprintnum() {
		return ocprintnum;
	}
	public void setOcprintnum(Integer ocprintnum) {
		this.ocprintnum = ocprintnum;
	}
	public Integer getOcplatform() {
		return ocplatform;
	}
	public void setOcplatform(Integer ocplatform) {
		this.ocplatform = ocplatform;
	}
	public Integer getOcshowplatform() {
		return ocshowplatform;
	}
	public void setOcshowplatform(Integer ocshowplatform) {
		this.ocshowplatform = ocshowplatform;
	}
	public Integer getOcshowshelf() {
		return ocshowshelf;
	}
	public void setOcshowshelf(Integer ocshowshelf) {
		this.ocshowshelf = ocshowshelf;
	}
	public String getOcpower() {
		return ocpower;
	}
	public void setOcpower(String ocpower) {
		this.ocpower = ocpower;
	}
	public String getOcpeoname() {
		return ocpeoname;
	}
	public void setOcpeoname(String ocpeoname) {
		this.ocpeoname = ocpeoname;
	}
	public String getOcaccessory() {
		return ocaccessory;
	}
	public void setOcaccessory(String ocaccessory) {
		this.ocaccessory = ocaccessory;
	}
	public String getOcremark() {
		return ocremark;
	}
	public void setOcremark(String ocremark) {
		this.ocremark = ocremark;
	}
	public Integer getSerid() {
		return serid;
	}
	public void setSerid(Integer serid) {
		this.serid = serid;
	}

	@Override
	public String toString() {
		return "Ordercontent [ocid=" + ocid + ", oid=" + oid + ", rid=" + rid + ", ocnum=" + ocnum + ", ocbegintime="
				+ ocbegintime + ", ocendtime=" + ocendtime + ", cid=" + cid + ", ocagenda=" + ocagenda + ", ocusenum="
				+ ocusenum + ", ocarrangement=" + ocarrangement + ", ocsourcename=" + ocsourcename + ", ocsourcephone="
				+ ocsourcephone + ", ocdockingstate=" + ocdockingstate + ", ocdockingtime=" + ocdockingtime
				+ ", ocmaterialsname=" + ocmaterialsname + ", ocmaterialsphone=" + ocmaterialsphone + ", ocusename="
				+ ocusename + ", ocusephone=" + ocusephone + ", ocreservename=" + ocreservename + ", ocreservephone="
				+ ocreservephone + ", ocbigcarnum=" + ocbigcarnum + ", ocsmallnum=" + ocsmallnum + ", ocprintnum="
				+ ocprintnum + ", ocplatform=" + ocplatform + ", ocshowplatform=" + ocshowplatform + ", ocshowshelf="
				+ ocshowshelf + ", ocpower=" + ocpower + ", ocpeoname=" + ocpeoname + ", ocaccessory=" + ocaccessory
				+ ", ocremark=" + ocremark + ", serid=" + serid + ", r=" + r
				+ "]";
	}
	public Ordercontent(Integer ocid, Integer oid, Integer rid, Integer ocnum, Date ocbegintime, Date ocendtime,
			Integer cid, String ocagenda, Integer ocusenum, String ocarrangement, String ocsourcename,
			String ocsourcephone, Integer ocdockingstate, Date ocdockingtime, String ocmaterialsname,
			String ocmaterialsphone, String ocusename, String ocusephone, String ocreservename, String ocreservephone,
			Integer ocbigcarnum, Integer ocsmallnum, Integer ocprintnum, Integer ocplatform, Integer ocshowplatform,
			Integer ocshowshelf, String ocpower, String ocpeoname, String ocaccessory, String ocremark, Integer serid,
			Room r,String stringocbegintime,String stringocendtime,String stringocdockingtime) {
		super();
		this.ocid = ocid;
		this.oid = oid;
		this.rid = rid;
		this.ocnum = ocnum;
		this.ocbegintime = ocbegintime;
		this.ocendtime = ocendtime;
		this.cid = cid;
		this.ocagenda = ocagenda;
		this.ocusenum = ocusenum;
		this.ocarrangement = ocarrangement;
		this.ocsourcename = ocsourcename;
		this.ocsourcephone = ocsourcephone;
		this.ocdockingstate = ocdockingstate;
		this.ocdockingtime = ocdockingtime;
		this.ocmaterialsname = ocmaterialsname;
		this.ocmaterialsphone = ocmaterialsphone;
		this.ocusename = ocusename;
		this.ocusephone = ocusephone;
		this.ocreservename = ocreservename;
		this.ocreservephone = ocreservephone;
		this.ocbigcarnum = ocbigcarnum;
		this.ocsmallnum = ocsmallnum;
		this.ocprintnum = ocprintnum;
		this.ocplatform = ocplatform;
		this.ocshowplatform = ocshowplatform;
		this.ocshowshelf = ocshowshelf;
		this.ocpower = ocpower;
		this.ocpeoname = ocpeoname;
		this.ocaccessory = ocaccessory;
		this.ocremark = ocremark;
		this.serid = serid;
		this.r = r;
	}
	public Ordercontent() {
		super();
		// TODO Auto-generated constructor stub
	}
}
