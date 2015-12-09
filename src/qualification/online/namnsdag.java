package qualification.online;
import java.util.Scanner;

public class namnsdag {
	
	public static void main(String[] args) {
//		run("anna", "peter", "petra", "anja", "markus", "anna"); //3 -- anja
//		run("jan", "petra", "bengt", "jan"); //3 -- jan
//		run("anya", "ana", "hanya", "enya", "anya"); //3 -- enya
//
		Scanner sc = new Scanner(System.in);
		String current = sc.next();
		String[] other = new String[sc.nextInt()];
		for(int i = 0; i < other.length; i++)
			other[i] = sc.next();
		sc.close();
		run(current, other);
	}
	
	private static void run(String current, String... other) {
		for(int i = 0; i < other.length; i++) {
			String s = other[i];
			if(s.length() != current.length()) continue;
			int diffs = 0;
			for(int j = 0; j < s.length(); j++)
				if(s.charAt(j) != current.charAt(j)) diffs++;
			if(diffs <= 1) {
				System.out.println(i + 1);
				return;
			}
		}
		throw new IllegalStateException();
	}
}
