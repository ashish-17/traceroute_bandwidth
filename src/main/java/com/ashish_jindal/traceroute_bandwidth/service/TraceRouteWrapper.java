/**
 * 
 */
package com.ashish_jindal.traceroute_bandwidth.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ashish
 *
 */
public class TraceRouteWrapper {

	private static final String EXECUTABLE = "traceroute";
	public static final int MAX_HOPS = 30;
	public static final int DEFAULT_PACKET_SIZE = 60;

	private String address;
	private int packetSize;
	List<TraceData> output;

	/**
	 * Test driver for this class.
	 */
	public static void main(String args[]) {
		try {
			TraceRouteWrapper tw = new TraceRouteWrapper(args[0]);
			tw.execute();
		} catch (Exception e) {
			System.out.println(e);
			System.exit(1);
		}
	}
	
	public TraceRouteWrapper(String address) {
		super();
		this.address = address;
		this.packetSize = DEFAULT_PACKET_SIZE;
		this.output = new ArrayList<TraceRouteWrapper.TraceData>();
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
	 * @return the output
	 */
	public List<TraceData> getOutput() {
		return output;
	}


	public void execute() {
		BufferedReader in = null;
		try {
			String cmd = EXECUTABLE + " " + address;

			Process p = Runtime.getRuntime().exec(cmd);
			
			in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			parseTracerouteOutput(in);
			System.out.println("*****End8*****");

		} catch (Exception e) {
			System.out.println("Error running traceroute");
			System.out.println(e);
		}
	}
	
	public void parseTracerouteOutput(BufferedReader in) throws IOException {
		String trace = null;
		while ((trace = in.readLine()) != null) {
			TraceData traceData = parseTrace(trace);
			if (traceData != null) {
				output.add(traceData);
			}
		}
	}
	
	public TraceData parseTrace(String trace) {
		TraceData traceData = new TraceData();
		//System.out.println(trace);
		String[] data = trace.split("\\s+");
		try {
			if (data[0].length() > 0 && output.size() == 0) {
				Integer.parseInt(data[0]);
			}
			
			for (int dataIndex = 0; dataIndex < data.length; ++dataIndex) {
				if (data[dataIndex].length() == 0 || data[dataIndex].equals("*") || data[dataIndex].equals("ms")) {
					continue;
				}else if (dataIndex == 3){
					traceData.setNextValue(data[dataIndex].substring(data[dataIndex].indexOf("(")+1, data[dataIndex].indexOf(")")));
				} else {
					traceData.setNextValue(data[dataIndex]);
				}
			}
			
			System.out.println(traceData);
			return traceData;
		} catch (Exception e) {
			// Skip the initial input which start with some non integer values.
			return null;
		}
	}
	
	public static class TraceData {
		private int hopNumber;
		private String ip;
		private String reverseDNS;
		private List<Double> rtt;
		private int indexOfNextValue;
		
		public TraceData() {
			super();
			
			hopNumber = -1;
			ip = "";
			reverseDNS = "";
			rtt = new ArrayList<Double>();
			indexOfNextValue = 0;
		}

		public void setNextValue(String value) {
			if (indexOfNextValue == 0) {
				hopNumber = Integer.parseInt(value);
			} else if (indexOfNextValue == 1) {
				ip = value;
			} else if (indexOfNextValue == 2) {
				reverseDNS = value;
			} else {
				try {
					rtt.add(Double.parseDouble(value));
				} catch (Exception e) {
					// Eat away this. Don't process it
					return;
				}
			}
			
			indexOfNextValue++;
		}
		
		/**
		 * @return the hopNumber
		 */
		public int getHopNumber() {
			return hopNumber;
		}

		/**
		 * @param hopNumber the hopNumber to set
		 */
		public void setHopNumber(int hopNumber) {
			this.hopNumber = hopNumber;
		}

		/**
		 * @return the ip
		 */
		public String getIp() {
			return ip;
		}

		/**
		 * @param ip the ip to set
		 */
		public void setIp(String ip) {
			this.ip = ip;
		}

		/**
		 * @return the reverseDNS
		 */
		public String getReverseDNS() {
			return reverseDNS;
		}

		/**
		 * @param reverseDNS the reverseDNS to set
		 */
		public void setReverseDNS(String reverseDNS) {
			this.reverseDNS = reverseDNS;
		}

		/**
		 * @return the rtt
		 */
		public List<Double> getRtt() {
			return rtt;
		}

		/**
		 * @param rtt the rtt to set
		 */
		public void setRtt(List<Double> rtt) {
			this.rtt = rtt;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "TraceData [hopNumber=" + hopNumber + ", ip=" + ip + ", reverseDNS=" + reverseDNS + ", rtt=" + rtt
					+ "]";
		}
	}
}
