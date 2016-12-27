package org.mdx.advent;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Day24 {

	public static void main(String[] args) {

		Scanner scn = null;
		try {
			scn = new Scanner(new File(args[0]));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		Map<String, Cell> cells = new HashMap<String, Cell>();

		int mX = -1;
		int mY = -1;
		int total = 0;
		int count = 0;
		int ssX = -1;
		int ssY = -1;
		while (scn.hasNextLine()) {
			mY++;
			String l = scn.nextLine();
			mX = -1;
			for (char c : l.toCharArray()) {
				mX++;
				Cell cell = new Cell();
				cell.x = mX;
				cell.y = mY;
				cell.id = mX + "-" + mY;

				if (c == '#') {
					cell.v = false;
				} else {
					cell.v = true;
				}
				if (c == 48) {
					ssX = mX;
					ssY = mY;
					cell.n = -1;
				} else if (c > 48 && c <= 57) {	
					cell.n = c - 48;
					total++;
				} else {
					cell.n = -1;
				}

				cells.put(cell.id, cell);
			}
		}

		int sX = ssX;
		int sY = ssY;

		int dist = 0;
		while(count < total) {
			List<Cell> ends = runDijkstra(sX,sY,mX,mY, cells);
			Collections.sort(ends);
			dist += ends.get(0).d;
			sX = ends.get(0).x;
			sY = ends.get(0).y;
			ends.get(0).i = true;
			count++;
		//for (Cell c : ends) {
		//	System.out.println(c.id + ", n: " + c.n + ", d: " + c.d);
		//}
		}

		
		System.out.println(dist);

	}
	
	private static List<Cell> runDijkstra(int sX, int sY, int mX, int mY, Map<String, Cell> cells) {
		Set<String> visited = new HashSet<String>();
		List<Cell> unvisited = new ArrayList<Cell>();
		for (Cell n : cells.values()) {
			if (n.v) {
				n.d = Integer.MAX_VALUE;
				unvisited.add(n);
			}
		}
		System.out.println(sX + "-" + sY);
		cells.get(sX + "-" + sY).d = 0;
		visited.add(sX + "-" + sY);
		List<Cell> ends = new ArrayList<Cell>();
		while (unvisited.size() > 0) {
			Collections.sort(unvisited);
			Cell n = unvisited.remove(0);
			//System.out.println(n.d);
			for (int x = -1; x <= 1; x++) {
				for (int y = -1; y <= 1; y++) {
					if (n.x + x >= 0 && n.y + y >= 0 && n.x + x <= mX && n.y + y <= mY && !(x == y)
							&& Math.abs(x - y) < 2) {
						String nbr = (n.x + x) + "-" + (n.y + y);
						if (cells.get(nbr).v) {
							if (visited.contains(nbr)) {
								if (cells.get(nbr).d > n.d + 1) {
									cells.get(nbr).d = n.d + 1;
								}
							} else {
								visited.add(nbr);
								cells.get(nbr).d = n.d + 1;
								if (cells.get(nbr).n > -1 && !cells.get(nbr).i) {
									ends.add(cells.get(nbr));
								}
							}
						}
					}
				}
			}
		}
		
		return ends;
	}
	
	private static class Cell implements Comparable<Cell> {
		String id;
		int x;
		int y;
		int d;
		boolean v;
		boolean i = false;
		int n;

		@Override
		public int compareTo(Cell o) {
			return d - o.d;
		}
	}
}
