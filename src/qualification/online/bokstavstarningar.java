package qualification.online;
import java.io.*;
import java.util.Arrays;

public class bokstavstarningar {
	public static void main(String[] args) throws IOException {
//		System.out.println(run(new String[] {"TSU", "NKT", "KMO", "LJA"}, new String[] {"KATT", "NATT", "STOL", "MASK", "KOST"})); //3 (KATT, STOL, MASK)
//		int N = 4;
//		int M = 1000;
//		int K = 2;
//		while(true) {
//			long seed = System.nanoTime();
//			Random r = new Random(seed);
//			String[] dice = new String[N];
//			for(int i = 0; i < dice.length; i++) {
//				StringBuilder sb = new StringBuilder();
//				for(int j = 0; j < K; j++)
//					sb.append((char)('A' + r.nextInt(N)));
//				dice[i] = sb.toString();
//			}
//			String[] words = new String[M];
//			for(int i = 0; i < words.length; i++) {
//				StringBuilder sb = new StringBuilder();
//				for(int j = 0; j < dice.length; j++)
//					sb.append((char)('A' + r.nextInt(N)));
//				words[i] = sb.toString();
//			}
//			run(dice, words);
//		}
		
		BufferedReader rdr = new BufferedReader(new InputStreamReader(System.in));
		String[] s = rdr.readLine().split(" ");
		String[] dice = new String[Integer.parseInt(s[0])];
		String[] words = new String[Integer.parseInt(s[2])];
		for(int i = 0; i < dice.length; i++)
			dice[i] = rdr.readLine();
		for(int i = 0; i < words.length; i++)
			words[i] = rdr.readLine();
		rdr.close();
		System.out.println(run(dice, words));
	}
	
	private static int run(String[] dice, String[] words) {
		int n = 0;
		char[][] w = new char[words.length][];
		for(int i = 0; i < words.length; i++) {
			char[] ch = words[i].toCharArray();
			Arrays.sort(ch);
			w[i] = ch;
		}
		char[][] d = new char[dice.length][];
		for(int i = 0; i < dice.length; i++) {
			char[] ch = dice[i].toCharArray();
			Arrays.sort(ch);
			d[i] = ch;
		}
		for(char[] c:w)
			n += calc(0, d, c) ? 1 : 0;
		return n;
	}
	
	private static boolean calc(int depth, char[][] dice, char[] s) {
		int[] match = new int[s.length]; //Char N, bit M: whether dice M can be used for char N
		for(int i = 0; i < s.length; i++)
			for(int j = 0; j < dice.length; j++)
				if(Arrays.binarySearch(dice[j], s[i]) >= 0) match[i] |= 1 << j;
		
		int n = 0;
		for(int i:match)
			if(i == 0) return false;
			else n |= i;
		if(n != (1 << s.length) - 1) return false;
		mergeSortByBitCount(match);
		
		return permutate(match, 0, 0);
	}
	
	private static boolean permutate(int[] match, int pos, int used) { //
		System.out.println(Arrays.toString(match) + " " + pos + " " + used);
		if(pos == match.length) return true;
		for(int i = 0; i < match.length; i++)
			if((used & 1 << i) == 0 && (match[pos] & 1 << i) != 0 && permutate(match, pos + 1, used | 1 << i)) return true;
		return false;
	}
	
	static void mergeSortByBitCount(int[] A) {
		if(A.length > 1) {
			int q = A.length / 2;
			
			int[] leftArray = Arrays.copyOfRange(A, 0, q);
			int[] rightArray = Arrays.copyOfRange(A, q, A.length);
			
			mergeSortByBitCount(leftArray);
			mergeSortByBitCount(rightArray);
			
			merge(A, leftArray, rightArray);
		}
	}
	
	static void merge(int[] a, int[] l, int[] r) {
		int totElem = l.length + r.length;
		int i, li, ri;
		i = li = ri = 0;
		while(i < totElem)
			if(li < l.length && ri < r.length) {
				if(Integer.bitCount(l[li]) < Integer.bitCount(r[ri])) {
					a[i] = l[li];
					i++;
					li++;
				} else {
					a[i] = r[ri];
					i++;
					ri++;
				}
			} else {
				if(li >= l.length) while(ri < r.length) {
					a[i] = r[ri];
					i++;
					ri++;
				}
				if(ri >= r.length) while(li < l.length) {
					a[i] = l[li];
					li++;
					i++;
				}
			}
		
	}
}
