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
public class PingWrapper {
	private static final String EXECUTABLE = "ping";
	
	private String address;
	private int echoRequests = 10;
	private int packetSize = 32;
	private int interval = 1;

	private List<PingData> output;
	
	public PingWrapper(String address) {
		super();
		this.address = address;
		this.output = new ArrayList<>();
	}

	public PingWrapper(String address, int echoRequests, int packetSize, int interval) {
		super();
		this.address = address;
		this.echoRequests = echoRequests;
		this.interval = interval;
		this.output = new ArrayList<>();
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
	 * @return the echoRequests
	 */
	public int getEchoRequests() {
		return echoRequests;
	}

	/**
	 * @param echoRequests the echoRequests to set
	 */
	public void setEchoRequests(int echoRequests) {
		this.echoRequests = echoRequests;
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
	 * @return the interval
	 */
	public int getInterval() {
		return interval;
	}

	/**
	 * @param interval the interval to set
	 */
	public void setInterval(int interval) {
		this.interval = interval;
	}

	/**
	 * @return the output
	 */
	public List<PingData> getOutput() {
		return output;
	}

	/**
	 * @param output the output to set
	 */
	public void setOutput(List<PingData> output) {
		this.output = output;
	}

	/**
	 * Test driver for this class.
	 */
	public static void main(String args[]) {
		try {
			PingWrapper pw = new PingWrapper("google.com");

			pw.execute();

		} catch (Exception e) {
			System.out.println(e);
			System.exit(1);
		}
	}

	/**
	 * Sets the command line appropriate for the OS then executes the ping
	 * program. Afterward it calls the appropriate method to parse the output.
	 */
	public void execute() {
		try {
			String cmd = EXECUTABLE + " " + address + " -c " + echoRequests + " -s " + packetSize + " -i " + interval;
			
			Process p = Runtime.getRuntime().exec(cmd);

			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			parsePingOutput(in);
		} catch (Exception e) {
			System.out.println("Error running ping");
			System.out.println(e);
		}
	}
	
	public void parsePingOutput(BufferedReader in) throws IOException {
		String ping = null;
		while ((ping = in.readLine()) != null) {
			PingData pingData = parsePing(ping);
			if (pingData != null) {
				System.out.println("## - " + pingData);
				output.add(pingData);
			}
		}
	}
	
	public PingData parsePing(String ping) {
		try {
			int icmp_seq_start = ping.indexOf("icmp_seq");
			int ttl_start = ping.indexOf("ttl");
			
			int icmp_seq = Integer.parseInt(ping.substring(icmp_seq_start + 9, ttl_start).trim());
			
			int time_start = ping.indexOf("time=");
			int ms_start = ping.indexOf(" ms");
			
			Double rtt = Double.parseDouble(ping.substring(time_start+5, ms_start));
			
			return new PingData(icmp_seq, rtt);
		} catch (Exception e) {
			System.out.println("Invalid ping result");
			return null;
		}
	}
	
	public static class PingData {
		private int icmpSeq;
		private Double rtt;
		
		public PingData(int icmpSeq) {
			super();
			this.icmpSeq = icmpSeq;
			this.rtt = 0.0;
		}
		
		public PingData(int icmpSeq, Double rtt) {
			super();
			this.icmpSeq = icmpSeq;
			this.rtt = rtt;
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
			return "PingData [icmpSeq=" + icmpSeq + ", rtt=" + rtt + "]";
		}
	}
}