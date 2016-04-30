/**
 * 
 */
package com.ashish_jindal.traceroute_bandwidth.dao;

import java.util.List;

import com.ashish_jindal.traceroute_bandwidth.model.TraceRoute;

/**
 * @author ashish
 *
 */
public interface TracerouteDao {

	public void addData(TraceRoute t);
	public List<String> getAllTraceroutes();
	public TraceRoute findById(String traceroute_id);
	public void removeData(int id);
}
