package com.xq.conference.model;

/**
 * 代办提醒设置
 * @ClassName Commissionset
 * @Author yangweihang
 * @Date 2018年9月5日 下午3:04:52
 */
public class Commissionset {
	private Integer cid;//代办提醒设置id
	private Integer cameetings;//待参会提醒间隔时间
	private Integer cameetingstimes;//待参会提醒次数
	private Integer cbacklog;//代办事项提醒间隔时间
	private Integer cbacklogtimes;//代办事项提醒次数
	public Commissionset() {
	}
	public Commissionset(Integer cid, Integer cameetings, Integer cameetingstimes, Integer cbacklog,
			Integer cbacklogtimes) {
		this.cid = cid;
		this.cameetings = cameetings;
		this.cameetingstimes = cameetingstimes;
		this.cbacklog = cbacklog;
		this.cbacklogtimes = cbacklogtimes;
	}
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public Integer getCameetings() {
		return cameetings;
	}
	public void setCameetings(Integer cameetings) {
		this.cameetings = cameetings;
	}
	public Integer getCameetingstimes() {
		return cameetingstimes;
	}
	public void setCameetingstimes(Integer cameetingstimes) {
		this.cameetingstimes = cameetingstimes;
	}
	public Integer getCbacklog() {
		return cbacklog;
	}
	public void setCbacklog(Integer cbacklog) {
		this.cbacklog = cbacklog;
	}
	public Integer getCbacklogtimes() {
		return cbacklogtimes;
	}
	public void setCbacklogtimes(Integer cbacklogtimes) {
		this.cbacklogtimes = cbacklogtimes;
	}
	@Override
	public String toString() {
		return "Commissionset [cid=" + cid + ", cameetings=" + cameetings + ", cameetingstimes=" + cameetingstimes
				+ ", cbacklog=" + cbacklog + ", cbacklogtimes=" + cbacklogtimes + "]";
	}
}
