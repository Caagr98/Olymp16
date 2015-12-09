package qualification.online;
import java.util.Scanner;

public class fargrobot {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		String s = sc.next();
		sc.close();
		run(n, s);
		
	}
	
	private static void run(int n, String s) {
		String cmds = "RGB";
		int[] c = new int[s.length()];
		for(int i = 0; i < s.length(); i++)
			c[i] = cmds.indexOf(s.charAt(i));
		StringBuilder sb = new StringBuilder();
		int steps = 0;
		boolean[] used = new boolean[cmds.length()];
		int nused = 0;
		for(int i = 0; i < c.length; i++)
			if(!used[c[i]]) {
				used[c[i]] = true;
				nused++;
				if(nused == used.length) {
					steps++;
					java.util.Arrays.fill(used, false);
					nused = 0;
					sb.append(cmds.charAt(c[i]));
					if(steps == n) break;
				}
			}
		System.out.println(sb);
	}
}
