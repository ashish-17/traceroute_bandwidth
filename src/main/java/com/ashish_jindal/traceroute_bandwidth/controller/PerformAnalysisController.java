/**
 * 
 */
package com.ashish_jindal.traceroute_bandwidth.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ashish_jindal.traceroute_bandwidth.dao.PingDataDao;
import com.ashish_jindal.traceroute_bandwidth.dao.TracerouteDao;
import com.ashish_jindal.traceroute_bandwidth.model.PingData;
import com.ashish_jindal.traceroute_bandwidth.model.TraceRoute;
import com.ashish_jindal.traceroute_bandwidth.service.PingWrapper;
import com.ashish_jindal.traceroute_bandwidth.service.TraceRouteWrapper;
import com.ashish_jindal.traceroute_bandwidth.service.TraceRouteWrapper.TraceData;

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

	@Autowired
	@Qualifier("tracerouteDao")
	private TracerouteDao tracerouteDao;

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
		
		if (echoRequests > 5000 || packetSize > 1000)
			return new ArrayList<PingData>();
		
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

	@RequestMapping(value={"tr/{tracert_id}/{address}/{echoRequests}/{packetSize}/{interval}"}, method = RequestMethod.GET)
	public @ResponseBody Map<String, ArrayList<PingData>> performTracert(
			@PathVariable String tracert_id, 
			@PathVariable String address, 
			@PathVariable int echoRequests,
			@PathVariable int packetSize,
			@PathVariable int interval) {
		
		if (echoRequests > 5000 || packetSize > 1000)
			return null;
		
		TraceRoute tr = new TraceRoute(tracert_id, address);
		
		TraceRouteWrapper trw = new TraceRouteWrapper(address);
		trw.execute();
		if (trw.getOutput().size() > 0) {
			tracerouteDao.addData(tr);
		}
		
		Map<String, ArrayList<PingData>> map = new HashMap<>();
		for (TraceData trd : trw.getOutput()) {
			PingWrapper pw = new PingWrapper(trd.getIp(), echoRequests, packetSize, (double)interval / 1000000);
			pw.execute();
			String ping_id = "tr_" + tracert_id + "_hop_" + trd.getHopNumber(); 
			if (pingDataDao.findDataById(ping_id).size() == 0) {
				List<PingWrapper.PingData> pingOutput = pw.getOutput();
				for (PingWrapper.PingData data : pingOutput) {
					pingDataDao.addData(new PingData(ping_id,trd.getIp(),packetSize,interval, data.getIcmpSeq(), data.getRtt(), tr));
				}
			}
			
			map.put(ping_id, new ArrayList<PingData>(pingDataDao.findDataById(ping_id)));
		}
		
		return map;
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

	@RequestMapping(value={"deletePing/{ping_id}"}, method = RequestMethod.GET)
	public @ResponseBody String deletePingData(@PathVariable String ping_id) {
		List<PingData> pingData = pingDataDao.findDataById(ping_id);
		for (PingData p : pingData) {
			pingDataDao.removePingData(p.getId());
		}
		
		return "success";
	}

	@RequestMapping(value={"deleteTracert/{tracert_id}"}, method = RequestMethod.GET)
	public @ResponseBody String deleteTracertData(@PathVariable String tracert_id) {
		tracerouteDao.removeData(tracerouteDao.findById(tracert_id).getId());
		
		return "success";
	}

	@RequestMapping(value={"getTracerts"}, method = RequestMethod.GET)
	public @ResponseBody List<String> getTracerts () {
		List<String> tracerts = tracerouteDao.getAllTraceroutes();

		return tracerts;
	}

	@RequestMapping(value={"getTracertData/{tracert_id}"}, method = RequestMethod.GET)
	public @ResponseBody Map<String, ArrayList<PingData>> getTracertData(@PathVariable String tracert_id) {
		TraceRoute tr = tracerouteDao.findById(tracert_id);
		Map<String, ArrayList<PingData>> map = new HashMap<>();
		List<String> pings = pingDataDao.fingPingsForTracert(tr.getId());
		for (String ping_id : pings) {
			map.put(ping_id, new ArrayList<PingData>(pingDataDao.findDataById(ping_id)));
		}
		return map;
	}
}
