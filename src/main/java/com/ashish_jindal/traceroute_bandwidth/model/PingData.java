/**
 * 
 */
package com.ashish_jindal.traceroute_bandwidth.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author ashish
 *
 */
@Entity
@Table(name="PING")
public class PingData {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private int ping_id;
	private int icmpSeq;
	private Double rtt;
	
	public PingData(int ping_id, int icmpSeq, Double rtt) {
		super();
		this.ping_id = ping_id;
		this.icmpSeq = icmpSeq;
		this.rtt = rtt;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the ping_id
	 */
	public int getPing_id() {
		return ping_id;
	}
	/**
	 * @param ping_id the ping_id to set
	 */
	public void setPing_id(int ping_id) {
		this.ping_id = ping_id;
	}
	/**
	 * @return the icmpSeq
	 */
	public int getIcmpSeq() {
		return icmpSeq;
	}
	/**
	 * @param icmpSeq the icmpSeq to set
	 */
	public void setIcmpSeq(int icmpSeq) {
		this.icmpSeq = icmpSeq;
	}
	/**
	 * @return the rtt
	 */
	public Double getRtt() {
		return rtt;
	}
	/**
	 * @param rtt the rtt to set
	 */
	public void setRtt(Double rtt) {
		this.rtt = rtt;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PingData [id=" + id + ", ping_id=" + ping_id + ", icmpSeq=" + icmpSeq + ", rtt=" + rtt + "]";
	}
	
	
}
