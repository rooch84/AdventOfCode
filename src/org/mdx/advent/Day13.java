package org.mdx.advent;

import java.util.HashMap;
import java.util.Map;

/**
 * --- Day 13: A Maze of Twisty Little Cubicles ---
 * 
 * You arrive at the first floor of this new building to discover a much less
 * welcoming environment than the shiny atrium of the last one. Instead, you are
 * in a maze of twisty little cubicles, all alike.
 * 
 * Every location in this area is addressed by a pair of non-negative integers
 * (x,y). Each such coordinate is either a wall or an open space. You can't move
 * diagonally. The cube maze starts at 0,0 and seems to extend infinitely toward
 * positive x and y; negative values are invalid, as they represent a location
 * outside the building. You are in a small waiting area at 1,1.
 * 
 * While it seems chaotic, a nearby morale-boosting poster explains, the layout
 * is actually quite logical. You can determine whether a given x,y coordinate
 * will be a wall or an open space using a simple system:
 * 
 * Find x*x + 3*x + 2*x*y + y + y*y. Add the office designer's favorite number
 * (your puzzle input). Find the binary representation of that sum; count the
 * number of bits that are 1. If the number of bits that are 1 is even, it's an
 * open space. If the number of bits that are 1 is odd, it's a wall. For
 * example, if the office designer's favorite number were 10, drawing walls as #
 * and open spaces as ., the corner of the building containing 0,0 would look
 * like this:
 * 
 * 0123456789 0 .#.####.## 1 ..#..#...# 2 #....##... 3 ###.#.###. 4 .##..#..#. 5
 * ..##....#. 6 #...##.### Now, suppose you wanted to reach 7,4. The shortest
 * route you could take is marked as O:
 * 
 * 0123456789 0 .#.####.## 1 .O#..#...# 2 #OOO.##... 3 ###O#.###. 4 .##OO#OO#. 5
 * ..##OOO.#. 6 #...##.### Thus, reaching 7,4 would take a minimum of 11 steps
 * (starting from your current location, 1,1).
 * 
 * What is the fewest number of steps required for you to reach 31,39?
 * 
 * --- Part Two ---
 * 
 * How many locations (distinct x,y coordinates, including your starting
 * location) can you reach in at most 50 steps?
 * 
 * @author Chris Rooney
 *
 */
public class Day13 {

	public static void main(String[] args) {

		if (args.length != 5) {
			System.out.println(
					"Incorrect number of arguments. Correct usage is <favourite number> <endX> <endY> <grid width> <max distance>");
		}

		int input = Integer.parseInt(args[0]);
		int endX = Integer.parseInt(args[1]);
		int endY = Integer.parseInt(args[2]);
		int w = Integer.parseInt(args[3]);
		int maxDist = Integer.parseInt(args[4]);

		Map<String, Cell> map = new HashMap<String, Cell>();

		for (int x = 0; x < w; ++x) {
			for (int y = 0; y < w; ++y) {
				int val = x * x + 3 * x + 2 * x * y + y + y * y;
				val += input;
				int count = 0;
				for (int i = 0; i < Integer.BYTES * 8; ++i) {
					if ((val >> i & 1) == 1) {
						count++;
					}
				}
				Character o = '.';
				if (count % 2 == 1) {
					o = '#';
				}
				map.put(x + "-" + y, new Cell(o));
			}
		}

		int sX = 1;
		int sY = 1;
		map.get(sX + "-" + sY).d = 0;
		traverse(1, sX, sY, w, map);

		boolean hasParent = true;
		String next = endX + "-" + endY;
		int count = 0;

		while (hasParent) {
			map.get(next).r = true;
			if (map.get(next).p != "") {
				count++;
				next = map.get(next).p;
			} else {
				hasParent = false;
			}
		}

		System.out.println("Shortest route to (" + endX + "," + endY + ") is as follows:");
		for (int y = 0; y < w; ++y) {
			for (int x = 0; x < w; ++x) {
				if (map.get(x + "-" + y).r) {
					System.out.print("0");
				} else {
					System.out.print(map.get(x + "-" + y).c + "");
				}
			}
			System.out.println("");
		}

		count = 0;
		for (int y = 0; y < w; ++y) {
			for (int x = 0; x < w; ++x) {
				if (map.get(x + "-" + y).d > -1 && map.get(x + "-" + y).d <= maxDist) {
					count++;
				}
			}
		}

		System.out.println("The route to (" + endX + "," + endY + ") is " + map.get(endX + "-" + endY).d + " steps");
		System.out.println(count + " locations can be reached in under " + maxDist + " steps");
	}

	public static void traverse(int d, int sX, int sY, int w, Map<String, Cell> map) {
		for (int x = -1; x <= 1; x++) {
			for (int y = -1; y <= 1; y++) {
				if (sX + x >= 0 && sY + y >= 0 && sX + x < w && sY + y < w && !(x == y) && Math.abs(x - y) < 2) {
					String pos = (sX + x) + "-" + (sY + y);
					if (map.get(pos).c == '.') {
						if (map.get(pos).d == -1) {
							map.get(pos).d = d;
							map.get(pos).p = sX + "-" + sY;
							traverse(d + 1, sX + x, sY + y, w, map);
						} else if (map.get(pos).d > d) {
							map.get(pos).d = d;
							map.get(pos).p = sX + "-" + sY;
							traverse(d + 1, sX + x, sY + y, w, map);
						}
					}
				}
			}
		}
	}

	private static class Cell {
		char c;
		int d;
		String p = "";
		boolean r = false;

		public Cell(char c) {
			this.c = c;
			d = -1;
		}
	}
}
