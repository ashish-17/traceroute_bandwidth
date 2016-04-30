/**
 * 
 */
package com.ashish_jindal.traceroute_bandwidth.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ashish_jindal.traceroute_bandwidth.dao.PingDataDao;
import com.ashish_jindal.traceroute_bandwidth.model.PingData;
import com.ashish_jindal.traceroute_bandwidth.service.PingWrapper;

/**
 * @author ashish
 *
 */
@Controller
@RequestMapping("/analysis")
public class PerformAnalysisController {

	@Autowired
	@Qualifier("pingDataDao")
	private PingDataDao pingDataDao;

	@RequestMapping(method = RequestMethod.GET)
	public String showPage() {
		return "analysis";
	}
	
	@RequestMapping(value={"{ping_id}/{address}/{echoRequests}/{packetSize}/{interval}"}, method = RequestMethod.GET)
	public @ResponseBody List<PingData> performPing(
			@PathVariable String ping_id, 
			@PathVariable String address, 
			@PathVariable int echoRequests,
			@PathVariable int packetSize,
			@PathVariable int interval) {
		
		PingWrapper pw = new PingWrapper(address, echoRequests, packetSize, (double)interval / 1000000);
		pw.execute();
		
		List<PingData> pingData = new ArrayList<PingData>();
		if (pingDataDao.findDataById(ping_id).size() == 0) {
			List<PingWrapper.PingData> pingOutput = pw.getOutput();
			for (PingWrapper.PingData data : pingOutput) {
				pingDataDao.addData(new PingData(ping_id,address,packetSize,interval, data.getIcmpSeq(), data.getRtt(), null));
			}
			
			pingData = pingDataDao.findDataById(ping_id);
		}
		
		return pingData;
	}

	@RequestMapping(value={"getPings"}, method = RequestMethod.GET)
	public @ResponseBody List<String> getPings () {
		List<String> pings = pingDataDao.getAllPings();

		return pings;
	}

	@RequestMapping(value={"getPingData/{ping_id}"}, method = RequestMethod.GET)
	public @ResponseBody List<PingData> getPingData(@PathVariable String ping_id) {
		List<PingData> pingData = pingDataDao.findDataById(ping_id);
		return pingData;
	}
}
