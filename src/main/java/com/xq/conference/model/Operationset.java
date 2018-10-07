package com.xq.conference.model;

/**
 * 操作设置
 * @ClassName Operationset
 * @Author yangweihang
 * @Date 2018年9月5日 下午3:03:01
 */
public class Operationset {
	private Integer osid;//操作设置id
	private Integer bookeditor;//预定编辑间隔时间
	private Integer commentdefault;//默认评论好评时间
	private Integer jointdefault;//会议室开始前的N小时不能进行对接确认操作
	public Operationset() {
	}
	public Operationset(Integer osid, Integer bookeditor, Integer commentdefault, Integer jointdefault) {
		this.osid = osid;
		this.bookeditor = bookeditor;
		this.commentdefault = commentdefault;
		this.jointdefault = jointdefault;
	}
	public Integer getOsid() {
		return osid;
	}
	public void setOsid(Integer osid) {
		this.osid = osid;
	}
	public Integer getBookeditor() {
		return bookeditor;
	}
	public void setBookeditor(Integer bookeditor) {
		this.bookeditor = bookeditor;
	}
	public Integer getCommentdefault() {
		return commentdefault;
	}
	public void setCommentdefault(Integer commentdefault) {
		this.commentdefault = commentdefault;
	}
	public Integer getJointdefault() {
		return jointdefault;
	}
	public void setJointdefault(Integer jointdefault) {
		this.jointdefault = jointdefault;
	}
	@Override
	public String toString() {
		return "Operationset [osid=" + osid + ", bookeditor=" + bookeditor + ", commentdefault=" + commentdefault
				+ ", jointdefault=" + jointdefault + "]";
	}
}
