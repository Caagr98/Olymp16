package qualification.online;
import java.io.*;
import java.util.*;

public class firewall {
	private static class Rule {
		private String action;
		private Cond[] conds;
		private boolean log;
		
		public Rule(String action, Cond[] conds) {
			this.action = action;
			this.conds = conds;
			log = action.equals("log");
		}
		
		@Override public String toString() {
			return action + Arrays.toString(conds);
		}
	}
	
	private static class Cond {
		private int type;
		private int val;
		
		public Cond(String type, String val, int count) {
			if(type.equals("ip")) {
				this.type = 0;
				this.val = IP.parse(val);
			}
			if(type.equals("port")) {
				this.type = 1;
				this.val = Integer.parseInt(val);
			}
			if(type.equals("limit")) {
				this.type = 2;
				this.val = Integer.parseInt(val);
			}
		}
		
		@Override public String toString() {
			return type + "=" + val;
		}
	}
	
	private static class IP { //I could use InetAddress, but that's overkill
		private int ip;
		private int port;
		
		public IP(String ip, String port) {
			this.ip = parse(ip);
			this.port = Integer.parseInt(port);
		}
		
		public static int parse(String val) {
			if(!ipToId.containsKey(val)) ipToId.put(val, ipToId.size());
			return ipToId.get(val);
		}
		
		@Override public String toString() {
			return ip + ":" + port;
		}
	}
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		/*/
		run(new String[] {"accept ip=127.0.0.1", "drop port=22", "accept limit=5", "accept port=80", "accept port=10 port=11", "drop"}, new String[] { //
		                "127.0.0.1:80", //accept
		                "127.0.0.1:22", //accept
		                "192.168.0.1:80", //accept
		                "192.168.0.1:11", //drop
		                "192.168.0.1:12", //drop
		                "192.168.0.1:13", //drop
		                "192.168.0.1:14", //accept
		                "192.168.0.1:15", //accept
		                "192.168.0.1:16", //accept
		                "192.168.0.1:22", //drop
		                "192.168.0.1:80", //accept
		                "154.135.0.5:22", //drop
		                "154.135.0.5:80", //accept
		                "127.0.0.1:8080", //accept
		                "215.215.5.8:5919", //drop
		                "215.215.5.9:5919" //drop
		});
		/**/
		/*/
		run(new String[] {"accept ip=192.168.0.1", "log port=22", "accept", "drop"}, new String[] { //
		                "127.0.0.1:80",      //accept 1
		                                "127.0.0.1:22",      //log 2 accept 2
		                                "192.168.0.1:80",    //accept 3
		                                "192.168.0.1:11",    //accept 4
		                                "192.168.0.1:12",    //accept 5
		                                "192.168.0.1:13",    //accept 6
		                                "192.168.0.1:14",    //accept 7
		                                "192.168.0.1:15",    //accept 8
		                                "192.168.0.1:16",    //accept 9
		                                "192.168.0.1:22",    //accept 10
		                                "192.168.0.1:80",    //accept 11
		                                "154.135.0.5:22",    //log 12 accept 12
		                                "154.135.0.5:80",    //accept 13
		                                "127.0.0.1:8080",     //accept 14
		                                "215.215.5.8:5919",   //accept 15
		                                "215.215.5.9:5919"    //accept 16
		                });
		/**/
		/*/
		        run(new String[] {"log limit=1", "log limit=2", "log limit=3", "drop limit=1", "accept"}, new String[] { //
		                        "127.0.0.1:80",     //log drop
		                                        "127.0.0.1:22",     //log log drop
		                                        "192.168.0.1:80",   //log drop
		                                        "192.168.0.1:11",   //log log drop
		                                        "192.168.0.1:12",   //log log log drop
		                                        "192.168.0.1:13",   //log log log drop
		                                        "192.168.0.1:14",   //log log log drop
		                                        "192.168.0.1:15",   //log log log drop
		                                        "192.168.0.1:16",   //log log log drop
		                                        "192.168.0.1:22",   //log log log drop
		                                        "192.168.0.1:80",   //log log log drop
		                                        "154.135.0.5:22",   //log drop
		                                        "154.135.0.5:80",   //log log drop
		                                        "127.0.0.1:8080",   //log log log drop
		                                        "215.215.5.8:5919", //log drop
		                                        "215.215.5.9:5919"  //log drop
		                        });
		
		/**/
		/*/
		String[] rules = new String[100];
		String[] ips = new String[1000];
		for(int i = 0; i < rules.length; i++) {
		        StringBuilder sb = new StringBuilder();
		        sb.append("log");
		        for(int j = 0; j < 1000; j++)
		                sb.append(" ip=0.0.0.0");
		        rules[i] = sb.toString();
		}
		for(int i = 0; i < ips.length; i++)
		        ips[i] = "0.0.0.0:0";
		run(rules, ips);
		/**/
		/**/
		BufferedReader rdr = new BufferedReader(new InputStreamReader(System.in));
		String[] rules = new String[Integer.parseInt(rdr.readLine())];
		for(int i = 0; i < rules.length; i++)
			rules[i] = rdr.readLine();
		String[] ips = new String[Integer.parseInt(rdr.readLine())];
		for(int i = 0; i < ips.length; i++)
			ips[i] = rdr.readLine();
		rdr.close();
		run(rules, ips);
		/**/
	}
	
	static Map<String, Integer> ipToId = new HashMap();
	
	private static void run(String[] rulesS, String[] ipsS) {
//              long l = System.nanoTime();
		IP[] ips = new IP[ipsS.length];
		Rule[] rules = new Rule[rulesS.length];
		List<Cond> limits = new ArrayList();
		parse(rulesS, ipsS, ips, rules, limits);
//              System.err.println(System.nanoTime() - l);
//              l = System.nanoTime();
		
		//This is currently calculating 10000*100*dunno=1M*dunno
		int[] limit = new int[ipToId.size()];
		for(int i = 0; i < ips.length; i++) {
			IP ip = ips[i];
			limit[ip.ip]++;
			if(i >= 1000) limit[ips[i - 1000].ip]--;
			r: for(Rule r:rules) {
				for(Cond c:r.conds)
					if(c.type == 0) {
						if(ip.ip != c.val) continue r;
					} else if(c.type == 1) {
						if(ip.port != c.val) continue r;
					} else if(c.type == 2) if(limit[ip.ip] < c.val) continue r;
				System.out.println(r.action + " " + (i + 1));
				if(!r.log) break r;
			}
		}
//              System.err.println(System.nanoTime() - l);
	}
	
	private static void parse(String[] rulesS, String[] ipsS, IP[] ips, Rule[] rules, List<Cond> limits) {
		for(int i = 0; i < ipsS.length; i++) {
			String[] parts = ipsS[i].split(":", 2);
			ips[i] = new IP(parts[0], parts[1]);
		}
		for(int i = 0; i < rulesS.length; i++) {
			String[] split = rulesS[i].split(" ");
			Cond[] conds = new Cond[split.length - 1];
			for(int j = 0; j < conds.length; j++) {
				String[] parts = split[j + 1].split("=", 2);
				conds[j] = new Cond(parts[0], parts[1], ipToId.size());
				if(conds[j].type == 2) limits.add(conds[j]);
			}
			rules[i] = new Rule(split[0], conds);
		}
	}
}
