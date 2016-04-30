/**
 * 
 */
package com.ashish_jindal.traceroute_bandwidth.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
	
	private String ping_id;
	private String address;
	private int packetSize;
	private long inteval; // in microseconds
	private int icmpSeq;
	private Double rtt;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tracert_id", nullable = true)
	private TraceRoute tracert;
	

	/**
	 * @return the tracert
	 */
	public TraceRoute getTracert() {
		return tracert;
	}

	/**
	 * @param tracert the tracert to set
	 */
	public void setTracert(TraceRoute tracert) {
		this.tracert = tracert;
	}

	public PingData() {}
	
	public PingData(String ping_id, String address, int packetSize, long inteval, int icmpSeq, Double rtt,
			TraceRoute tracert) {
		super();
		this.ping_id = ping_id;
		this.address = address;
		this.packetSize = packetSize;
		this.inteval = inteval;
		this.icmpSeq = icmpSeq;
		this.rtt = rtt;
		this.tracert = tracert;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the packetSize
	 */
	public int getPacketSize() {
		return packetSize;
	}

	/**
	 * @param packetSize the packetSize to set
	 */
	public void setPacketSize(int packetSize) {
		this.packetSize = packetSize;
	}

	/**
	 * @return the inteval
	 */
	public long getInteval() {
		return inteval;
	}

	/**
	 * @param inteval the inteval to set
	 */
	public void setInteval(long inteval) {
		this.inteval = inteval;
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
	public String getPing_id() {
		return ping_id;
	}
	/**
	 * @param ping_id the ping_id to set
	 */
	public void setPing_id(String ping_id) {
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
		return "PingData [id=" + id + ", ping_id=" + ping_id + ", address=" + address + ", packetSize=" + packetSize
				+ ", inteval=" + inteval + ", icmpSeq=" + icmpSeq + ", rtt=" + rtt + ", tracert=" + tracert + "]";
	}
}
