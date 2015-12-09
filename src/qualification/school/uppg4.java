package qualification.school;
import java.util.Scanner;

public class uppg4 {
	public static void main(String[] args) {
//		run(18, 0, 2); //155
//		run(3, 0, 1, 2, 0, 0, 3, 6); //144
//		run(30, 30); //220
//
//		run(2, 6); //76
//		run(6, 2); //76
		
		Scanner sc = new Scanner(System.in);
		System.out.print("Antal dagar ? ");
		int[] n = new int[sc.nextInt()];
		for(int i = 0; i < n.length; i++) {
			System.out.print("Mognar dag " + (i + 1) + " ? ");
			n[i] = sc.nextInt();
		}
		sc.close();
		run(n);
	}
	
	private static void run(int... n) {
		int days = n.length + 2;
		int[] eaten = new int[days];
		
		while(true) {
			int max = 0;
			int maxn = 0;
			for(int i = 0; i < n.length; i++)
				if(n[i] > max) {
					max = n[i];
					maxn = i;
				}
			if(max == 0) break;
			int min = 100;
			int minDay = -1;
			for(int k = 0; k < 3; k++)
				if(eaten[maxn + k] < min) {
					min = eaten[maxn + k];
					minDay = maxn + k;
				}
			eaten[minDay]++;
			n[maxn]--;
		}
		
		int score = 0;
		for(int i = 0; i < days; i++)
			score += score(eaten[i]);
		System.out.println("Svar: " + score);
	}
	
	private static int score(int i) {
		int score = 0;
		for(int j = 0; j < i && j < 10; j++)
			score += 10 - j;
		return score;
	}
}
