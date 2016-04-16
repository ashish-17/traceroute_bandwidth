/**
 * 
 */
package com.ashish_jindal.traceroute_bandwidth.controller;

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
	
	private static int count = 0;
	
	@RequestMapping(value={"{address}/{echoRequests}/{packetSize}/{interval}"}, method = RequestMethod.GET)
	public @ResponseBody List<PingData> performPing(
			@PathVariable String address, 
			@PathVariable int echoRequests,
			@PathVariable int packetSize,
			@PathVariable int interval) {
		
		PingWrapper pw = new PingWrapper(address, echoRequests, packetSize, (double)interval / 1000);
		pw.execute();
		
		List<PingWrapper.PingData> pingOutput = pw.getOutput();
		for (PingWrapper.PingData data : pingOutput) {
			pingDataDao.addData(new PingData(count, data.getIcmpSeq(), data.getRtt()));
		}
		
		count++;
		List<PingData> pingData = pingDataDao.findDataById(count-1);
		return pingData;
	}

	@RequestMapping(value={"getPings"}, method = RequestMethod.GET)
	public @ResponseBody List<Integer> getPings () {
		List<Integer> pings = pingDataDao.getAllPings();

		return pings;
	}

	@RequestMapping(value={"getPingData/{ping_id}"}, method = RequestMethod.GET)
	public @ResponseBody List<PingData> getPingData(@PathVariable int ping_id) {
		List<PingData> pingData = pingDataDao.findDataById(ping_id);
		return pingData;
	}
}
