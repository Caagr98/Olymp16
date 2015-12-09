package qualification.school;
import java.util.Scanner;

public class uppg1 {
	public static void main(String[] args) {
//		run(1, 3, 2, 4); //1 4
//		run(4, 1, 5, 3, 10); //5 11
//		run(10, 10);//0, 0
		Scanner sc = new Scanner(System.in);
		System.out.print("Antal veckor ? ");
		int[] n = new int[sc.nextInt()];
		for(int i = 0; i < n.length; i++) {
			System.out.print("Vecka " + (i + 1) + " ? ");
			n[i] = sc.nextInt();
		}
		System.out.println();
		sc.close();
		run(n);
	}
	
	private static void run(int... n) {
		int empty = 0, missed = 0;
		for(int i = 1; i < n.length; i++) {
			int error = n[i] - n[i - 1];
			if(error > 0) missed += error;
			else empty -= error;
		}
		System.out.println("Tomma: " + empty);
		System.out.println("Missade: " + missed);
	}
}
