package qualification.school;
import java.util.Scanner;

public class uppg5 {
	public static void main(String[] args) {
//		run("A..", "...", "..B");
//		run("A#...", ".....", "...#B");
//		run("A..#...", ".B.1...", "..c#...");
		Scanner sc = new Scanner(System.in);
		int r = sc.nextInt();
		sc.nextInt(); //Ignore column count, it's not needed in Java
		String[] level = new String[r];
		for(int i = 0; i < r; i++)
			level[i] = sc.next();
		sc.close();
		System.out.println();
		run(level);
	}
	
	static boolean[][] solid;
	static boolean[][] btn;
	static boolean[][] toggle;
	static int gx, gy;
	static int w, h;
	static int[][][][] min;
	static int minM;
	
	private static void run(String... level) {
		h = level.length;
		w = level[0].length();
		int x = 0, y = 0;
		solid = new boolean[w][h];
		btn = new boolean[w][h];
		toggle = new boolean[w][h];
		min = new int[7][7][3][2];
		minM = 999999;
		for(int i = 0; i < h; i++)
			for(int j = 0; j < w; j++) {
				char ch = level[i].charAt(j);
				if(ch == 'A') {
					x = j;
					y = i;
				}
				if(ch == 'B') {
					gx = j;
					gy = i;
				}
				if(ch == '#' || ch == '1') solid[j][i] = true;
				if(ch == 'c') btn[j][i] = true;
				if(ch == '1' || ch == '0') toggle[j][i] = true;
			}
		dfs(x, y, 0, false, 0);
		System.out.println("Svar: " + minM);
	}
	
	private static void dfs(int x, int y, int i, boolean b, int j) {
		if(solid(x, y, b)) return;
		if(i == 1 && solid(x + 1, y, b)) return;
		if(i == 2 && solid(x, y + 1, b)) return;
		j -= 999999;
		if(min[x][y][i][b ? 1 : 0] <= j) return;
		if(minM <= j) return;
		min[x][y][i][b ? 1 : 0] = j;
		j += 999999;
		
//		System.out.println(x + " " + y + " " + i);
		if(i == 0) {
			if(x == gx && y == gy) {
				minM = j;
				return;
			}
			if(btn[x][y]) b = !b;
			dfs(x - 2, y, 1, b, j + 1);
			dfs(x + 1, y, 1, b, j + 1);
			dfs(x, y - 2, 2, b, j + 1);
			dfs(x, y + 1, 2, b, j + 1);
		}
		if(i == 1) {
			dfs(x - 1, y, 0, b, j + 1);
			dfs(x + 2, y, 0, b, j + 1);
			dfs(x, y - 1, 1, b, j + 1);
			dfs(x, y + 1, 1, b, j + 1);
		}
		if(i == 2) {
			dfs(x, y - 1, 0, b, j + 1);
			dfs(x, y + 2, 0, b, j + 1);
			dfs(x - 1, y, 2, b, j + 1);
			dfs(x + 1, y, 2, b, j + 1);
		}
	}
	
	private static boolean solid(int i, int j, boolean b) {
		return i < 0 || i >= w || j < 0 || j >= h || solid[i][j] != (toggle[i][j] && b);
	}
}
