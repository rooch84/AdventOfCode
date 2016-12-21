package org.mdx.advent;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * --- Day 21: Scrambled Letters and Hash ---
 * 
 * The computer system you're breaking into uses a weird scrambling function to
 * store its passwords. It shouldn't be much trouble to create your own
 * scrambled password so you can add it to the system; you just have to
 * implement the scrambler.
 * 
 * The scrambling function is a series of operations (the exact list is provided
 * in your puzzle input). Starting with the password to be scrambled, apply each
 * operation in succession to the string. The individual operations behave as
 * follows:
 * 
 * swap position X with position Y means that the letters at indexes X and Y
 * (counting from 0) should be swapped. swap letter X with letter Y means that
 * the letters X and Y should be swapped (regardless of where they appear in the
 * string). rotate left/right X steps means that the whole string should be
 * rotated; for example, one right rotation would turn abcd into dabc. rotate
 * based on position of letter X means that the whole string should be rotated
 * to the right based on the index of letter X (counting from 0) as determined
 * before this instruction does any rotations. Once the index is determined,
 * rotate the string to the right one time, plus a number of times equal to that
 * index, plus one additional time if the index was at least 4. reverse
 * positions X through Y means that the span of letters at indexes X through Y
 * (including the letters at X and Y) should be reversed in order. move position
 * X to position Y means that the letter which is at index X should be removed
 * from the string, then inserted such that it ends up at index Y. For example,
 * suppose you start with abcde and perform the following operations:
 * 
 * swap position 4 with position 0 swaps the first and last letters, producing
 * the input for the next step, ebcda. swap letter d with letter b swaps the
 * positions of d and b: edcba. reverse positions 0 through 4 causes the entire
 * string to be reversed, producing abcde. rotate left 1 step shifts all letters
 * left one position, causing the first letter to wrap to the end of the string:
 * bcdea. move position 1 to position 4 removes the letter at position 1 (c),
 * then inserts it at position 4 (the end of the string): bdeac. move position 3
 * to position 0 removes the letter at position 3 (a), then inserts it at
 * position 0 (the front of the string): abdec. rotate based on position of
 * letter b finds the index of letter b (1), then rotates the string right once
 * plus a number of times equal to that index (2): ecabd. rotate based on
 * position of letter d finds the index of letter d (4), then rotates the string
 * right once, plus a number of times equal to that index, plus an additional
 * time because the index was at least 4, for a total of 6 right rotations:
 * decab. After these steps, the resulting scrambled password is decab.
 * 
 * Now, you just need to generate a new scrambled password and you can access
 * the system. Given the list of scrambling operations in your puzzle input,
 * what is the result of scrambling abcdefgh?
 * 
 * @author Chris Rooney
 *
 */
public class Day21 {

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

		while (scn.hasNextLine()) {

			String line = scn.nextLine();
			String[] w = line.split(" ");

			if (line.startsWith("swap position")) {
				char tmp = o[Integer.parseInt(w[2])];
				o[Integer.parseInt(w[2])] = o[Integer.parseInt(w[5])];
				o[Integer.parseInt(w[5])] = tmp;
			} else if (line.startsWith("swap letter")) {
				for (int i = 0; i < o.length; ++i) {
					if (o[i] == w[2].charAt(0)) {
						o[i] = w[5].charAt(0);
					} else if (o[i] == w[5].charAt(0)) {
						o[i] = w[2].charAt(0);
					}
				}
			} else if (line.startsWith("rotate left")) {
				rotate(o, -1, Integer.parseInt(w[2]));
			} else if (line.startsWith("rotate right")) {
				rotate(o, 1, Integer.parseInt(w[2]));
			} else if (line.startsWith("rotate based")) {
				int j = 0;
				for (int i = 0; i < o.length; ++i) {
					if (o[i] == w[6].charAt(0)) {
						j = i;
						break;
					}
				}
				if (j >= 4) {
					j++;
				}
				rotate(o, 1, 1 + j);
			} else if (line.startsWith("reverse positions")) {
				int l = Integer.parseInt(w[2]);
				int r = Integer.parseInt(w[4]);
				for (int i = 0; i <= (r - l) / 2; ++i) {
					char tmp = o[l + i];
					o[l + i] = o[r - i];
					o[r - i] = tmp;
				}
			} else if (line.startsWith("move position")) {
				int s = Integer.parseInt(w[2]);
				int e = Integer.parseInt(w[5]);
				char tmp = o[s];
				if (e < s) {
					shiftUp(o, e, s - e);
					o[e] = tmp;
				} else {
					shiftDown(o, s, e - s);
					o[e] = tmp;
				}
			}
		}
		for (int i = 0; i < o.length; ++i) {
			System.out.print(o[i]);
		}
		System.out.println("");

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
