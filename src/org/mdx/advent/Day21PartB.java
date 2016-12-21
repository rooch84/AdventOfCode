package org.mdx.advent;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * --- Part Two ---
 * 
 * You scrambled the password correctly, but you discover that you can't
 * actually modify the password file on the system. You'll need to un-scramble
 * one of the existing passwords by reversing the scrambling process.
 * 
 * What is the un-scrambled version of the scrambled password fbgdceah?
 * 
 * @author Chris Rooney
 *
 */
public class Day21PartB {

	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Error. Expect usage is <filename> <input>");
			System.exit(0);
		}

		String input = args[1];
		char[] o = input.toCharArray();

		Scanner scn = null;
		try {
			scn = new Scanner(new File(args[0]));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		List<String> inst = new ArrayList<String>();

		while (scn.hasNextLine()) {

			inst.add(0, scn.nextLine());
		}

		for (String s : inst) {

			String[] w = s.split(" ");

			if (s.startsWith("swap position")) {
				char tmp = o[Integer.parseInt(w[2])];
				o[Integer.parseInt(w[2])] = o[Integer.parseInt(w[5])];
				o[Integer.parseInt(w[5])] = tmp;
			} else if (s.startsWith("swap letter")) {
				for (int i = 0; i < o.length; ++i) {
					if (o[i] == w[2].charAt(0)) {
						o[i] = w[5].charAt(0);
					} else if (o[i] == w[5].charAt(0)) {
						o[i] = w[2].charAt(0);
					}
				}
			} else if (s.startsWith("rotate left")) {
				rotate(o, 1, Integer.parseInt(w[2]));
			} else if (s.startsWith("rotate right")) {
				rotate(o, -1, Integer.parseInt(w[2]));
			} else if (s.startsWith("rotate based")) {
				int j = 0;
				for (int i = 0; i < o.length; ++i) {
					if (o[i] == w[6].charAt(0)) {
						j = i;
						break;
					}
				}

				j = lookup(j);

				if (j >= 4) {
					j++;
				}
				rotate(o, -1, 1 + j);
			} else if (s.startsWith("reverse positions")) {
				int l = Integer.parseInt(w[2]);
				int r = Integer.parseInt(w[4]);
				for (int i = 0; i <= (r - l) / 2; ++i) {
					char tmp = o[l + i];
					o[l + i] = o[r - i];
					o[r - i] = tmp;
				}
			} else if (s.startsWith("move position")) {
				int s1 = Integer.parseInt(w[5]);
				int e = Integer.parseInt(w[2]);
				char tmp = o[s1];
				if (e < s1) {
					shiftUp(o, e, s1 - e);
					o[e] = tmp;
				} else {
					shiftDown(o, s1, e - s1);
					o[e] = tmp;
				}
			}
		}
		for (int i = 0; i < o.length; ++i) {
			System.out.print(o[i]);
		}
		System.out.println("");

	}

	private static int lookup(int j) {

		switch (j) {
		case 0:
			return 7;
		case 1:
			return 0;
		case 2:
			return 4;
		case 3:
			return 1;
		case 4:
			return 5;
		case 5:
			return 2;
		case 6:
			return 6;
		case 7:
			return 3;
		}
		return -1;
	}

	public static void rotate(char[] o, int d, int s) {
		if (d == -1 && o.length > 1) {
			for (int i = 0; i < s; ++i) {
				char tmp = o[0];
				for (int j = 0; j < o.length - 1; ++j) {
					o[j] = o[j + 1];
				}
				o[o.length - 1] = tmp;
			}
		} else if (d == 1 && o.length > 1) {
			for (int i = 0; i < s; ++i) {
				char tmp = o[o.length - 1];
				for (int j = o.length - 1; j > 0; --j) {
					o[j] = o[j - 1];
				}
				o[0] = tmp;
			}
		}
	}

	public static void shiftUp(char[] o, int s, int l) {
		for (int i = s + l; i >= s + 1; --i) {
			o[i] = o[i - 1];
		}
	}

	public static void shiftDown(char[] o, int s, int l) {
		for (int i = s; i < s + l; ++i) {
			o[i] = o[i + 1];
		}
	}
}
