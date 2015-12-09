package qualification.school;
import java.util.Scanner;

public class uppg2 {
	public static void main(String[] args) {
//		run("8"); //17
//		run("92"); //119
//		run("200"); //1001
//		run("9550"); //9604
//		run("99999"); //189999, I think?
		Scanner sc = new Scanner(System.in);
		System.out.print("N ? ");
		run(sc.next());
		sc.close();
	}
	
	private static void run(String in) { //Oh my god this code is ugly
		int len = in.length();
		int[] n = new int[len + 1];
		for(int i = 0; i < len; i++)
			n[len - 1 - i] = in.charAt(i) - '0'; //Upward loops are easier than downward, so I'll flip it backwards
		
		int leadingZeroes;
		for(leadingZeroes = 0; leadingZeroes < len && n[leadingZeroes] == 0; leadingZeroes++) {}
		int numToInc;
		for(numToInc = leadingZeroes + 1; numToInc < len && n[numToInc] == 9; numToInc++) {}
		n[leadingZeroes]--;
		n[numToInc]++;
		int sum = 0;
		for(int i = 0; i < numToInc; i++) {
			sum += n[i];
			n[i] = 0;
		}
		for(int i = 0; i < numToInc && sum != 0; i++) {
			int put = Math.min(sum, 9);
			sum -= put;
			n[i] = put;
		}
		StringBuilder sb = new StringBuilder(n.length);
		for(int i = 0; i < n.length; i++)
			sb.append(n[i]);
		sb.reverse();
		if(sb.charAt(0) == '0') sb.deleteCharAt(0);
		System.out.println("Svar: " + sb);
	}
}
