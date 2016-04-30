/**
 * 
 */
package com.ashish_jindal.traceroute_bandwidth.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author ashish
 *
 */
@Entity
@Table(name="TRACEROUTE")
public class TraceRoute {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private String tracert_id;
	private String address;

	public TraceRoute() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TraceRoute(String tracert_id, String address) {
		super();
		this.tracert_id = tracert_id;
		this.address = address;
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
	 * @return the tracert_id
	 */
	public String getTracert_id() {
		return tracert_id;
	}

	/**
	 * @param tracert_id the tracert_id to set
	 */
	public void setTracert_id(String tracert_id) {
		this.tracert_id = tracert_id;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TraceRoute [id=" + id + ", tracert_id=" + tracert_id + ", address=" + address + "]";
	}
}
