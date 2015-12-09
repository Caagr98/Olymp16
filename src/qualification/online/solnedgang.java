package qualification.online;
import java.io.*;
import java.util.*;

public class solnedgang {
	public static class Cell implements Comparable<Cell> {
		long x, y;
		Collection<Cell> neighbors = new HashSet();
		long minTime = Long.MAX_VALUE;
		
		public Cell(long x, long y) {
			this.x = x;
			this.y = y;
		}
		
		@Override public int compareTo(Cell o) {
			if(x == o.x) return Long.compare(y, o.y);
			return Long.compare(x, o.x);
		}
		
		@Override public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(x + "," + y);
			sb.append("[");
			for(Cell c:neighbors)
				sb.append(c.x + "," + c.y + "; ");
			sb.append("]");
//			sb.append(minTime);
			return sb.toString();
		}
	}
	
	public static void main(String[] args) throws IOException {
//		run(10, 0, 1, 1, 2, 2, 0, 3, 2); //2
//		run(10, 1, 0, 2, 1, 3, 1, 4, 1, 2, 4, 3, 4, 1, 5, 0, 4); //3
//		run(100, 1, 0, 3, 0); //NATT
//		run(1, 0, 0, 1, 1); //:NATT
//		run(100, 0, 0); //:0
//		run(100, 0, 0, 1, 3, 0, 4); //:NATT
//		run(1, 0, 0, 1, 0); //:0
//		run(10, 1, 0, 0, 1, 2, 2, 1, 4); //:2
//		run(100, 0, 0, 1, 1, 2, 2, 2, 0);
//		Cell[] n = new Cell[10000000];
//		for(int i = 0; i < n.length; i++)
//			n[i] = new Cell((long)Math.log(i), (long)Math.sqrt(i));
//		run(1000, n);
		
//		Scanner sc = new Scanner(System.in);
//		Cell[] coords = new Cell[sc.nextInt()];
//		long night = sc.nextLong();
//		for(int i = 0; i < coords.length; i++)
//			coords[i] = new Cell(sc.nextLong(), sc.nextLong());
//		sc.close();
//		run(night, coords);
		
		BufferedReader rdr = new BufferedReader(new InputStreamReader(System.in));
		String[] header = rdr.readLine().split(" ");
		Cell[] coords = new Cell[Integer.parseInt(header[0])];
		long night = Long.parseLong(header[1]);
		for(int i = 0; i < coords.length; i++) {
			String[] line = rdr.readLine().split(" ");
			coords[i] = new Cell(Long.parseLong(line[0]), Long.parseLong(line[1]));
		}
		rdr.close();
		run(night, coords);
	}
	
	private static void run(long night, Cell... coords) {
		long l = System.nanoTime();
		System.err.println(0);
		Cell start = coords[0];
		Cell end = coords[coords.length - 1];
		Arrays.sort(coords);
		
		System.err.println(System.nanoTime() - l);
		l = System.nanoTime();
		
		long lastX = -1;
		TreeMap<Long, Cell> lastMap = null;
		Map<Long, TreeMap<Long, Cell>> map = new TreeMap();
		for(Cell c:coords) {
			if(c.x != lastX) {
				lastX = c.x;
				lastMap = new TreeMap();
				map.put(c.x, lastMap);
			}
			lastMap.put(c.y, c);
		}
		
		System.err.println(System.nanoTime() - l);
		l = System.nanoTime();
		
		long x;
		for(x = start.x; map.containsKey(x); x--);
		for(x++; map.containsKey(x + 1); x++) {
			TreeMap<Long, Cell> col = map.get(x);
			TreeMap<Long, Cell> next = map.get(x + 1);
			for(Long y:col.keySet()) {
				Long y2 = col.higherKey(y);
				Long ynext = next.floorKey(y + 1);
				if(ynext == null) ynext = next.higherKey(y + 1);
				while(ynext != null && (y2 == null || ynext < y2 - 1)) {
					Cell c1 = col.get(y);
					Cell c2 = next.get(ynext);
					c1.neighbors.add(c2);
					c2.neighbors.add(c1);
					ynext = next.higherKey(ynext);
				}
			}
		}
		
		System.err.println(System.nanoTime() - l);
		l = System.nanoTime();
		
		Set<Cell> toVisit_ = new HashSet(); //Don't want any duplicates in toVisit; this solves that
		LinkedList<Cell> toVisit = new LinkedList(); //I want it sorted, though
		toVisit.add(start);
		start.minTime = 0;
		while(!toVisit.isEmpty()) {
			Cell c = toVisit.removeFirst();
			toVisit_.remove(c);
//			System.out.println(c.minTime + " " + toVisit.size());
			if(c.minTime >= night) continue;
			for(Cell c2:c.neighbors) {
				long diff = Math.max(c.minTime, Math.abs(c.y - c2.y));
				if(diff < c2.minTime) {
					c2.minTime = diff;
					if(toVisit_.add(c2)) toVisit.addLast(c2);
				}
			}
		}
		System.out.println(end.minTime >= night ? "NATT" : end.minTime);
//		System.err.println(map);
//		System.err.println(start);
		
		System.err.println(System.nanoTime() - l);
	}
}
