/**
 * 
 */
package com.ashish_jindal.traceroute_bandwidth.dao;

import java.util.List;

import com.ashish_jindal.traceroute_bandwidth.model.PingData;

/**
 * @author ashish
 *
 */
public interface PingDataDao {

	public void addData(PingData p);
	public List<String> getAllPings();
	public List<PingData> findDataById(String ping_id);
	public void removePingData(int id);
}
