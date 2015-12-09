package qualification.school;
import java.util.Scanner;

public class uppg3 {
	public static void main(String[] args) {
//		run(6, 0, 4, 0, 2, 1, 2, 0, 1);//04,11,03,01
//		run(85, 2, 2, 2, 0, 2, 1, 1, 0);//13,11,12,01
//		run(37, 1, 1, 0, 2, 2, 0, 1, 1);//20,11,20,20
//		run(9876543210L, 4, 5, 7, 2, 8, 0, 6, 3, 3, 5, 4, 4, 1, 8, 0, 9, 7, 1, 2, 6); //18,54,17,90,71,17,45,36,08,26
		Scanner sc = new Scanner(System.in);
		System.out.print("Antal rader ? ");
		int[] n = new int[sc.nextInt() * 2];
		for(int i = 0; i < n.length; i += 2) {
			System.out.print("Rad " + (i / 2 + 1) + ", vänster ? ");
			n[i] = sc.nextInt();
			System.out.print("Rad " + (i / 2 + 1) + ", höger   ? ");
			n[i + 1] = sc.nextInt();
		}
		System.out.print("Talet N ? ");
		int mov = sc.nextInt();
		System.out.println();
		sc.close();
		run(mov, n);
	}
	
	private static void run(long mov, int... n) {
		int N = n.length / 2;
		long[] tot = new long[N];
		long[] left = new long[N];
		for(int i = 0; i < N; i++) {
			int idx = N - i - 1;
			tot[idx] = n[2 * i] + n[2 * i + 1] + 1;
			left[idx] = n[2 * i];
		}
		
		long num = 0, mul = 1;
		for(int i = 0; i < N; i++) {
			num += left[i] * mul;
			mul *= tot[i];
		}
		num += mov;
		
		for(int i = 0; i < N; i++) {
			left[i] = num % tot[i];
			num /= tot[i];
		}
		for(int i = 0; i < N; i++) {
			int idx = N - i - 1;
			System.out.println(left[idx] + " " + (tot[idx] - left[idx] - 1));
		}
	}
}
