package org.mdx.advent;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * --- Day 2: Bathroom Security ---
 * 
 * You arrive at Easter Bunny Headquarters under cover of darkness. However, you
 * left in such a rush that you forgot to use the bathroom! Fancy office
 * buildings like this one usually have keypad locks on their bathrooms, so you
 * search the front desk for the code.
 * 
 * "In order to improve security," the document you find says, "bathroom codes
 * will no longer be written down. Instead, please memorize and follow the
 * procedure below to access the bathrooms."
 * 
 * The document goes on to explain that each button to be pressed can be found
 * by starting on the previous button and moving to adjacent buttons on the
 * keypad: U moves up, D moves down, L moves left, and R moves right. Each line
 * of instructions corresponds to one button, starting at the previous button
 * (or, for the first line, the "5" button); press whatever button you're on at
 * the end of each line. If a move doesn't lead to a button, ignore it.
 * 
 * You can't hold it much longer, so you decide to figure out the code as you
 * walk to the bathroom. You picture a keypad like this:
 * 
 * 1 2 3 4 5 6 7 8 9 Suppose your instructions are:
 * 
 * ULL RRDDD LURDL UUUUD You start at "5" and move up (to "2"), left (to "1"),
 * and left (you can't, and stay on "1"), so the first button is 1. Starting
 * from the previous button ("1"), you move right twice (to "3") and then down
 * three times (stopping at "9" after two moves and ignoring the third), ending
 * up with 9. Continuing from "9", you move left, up, right, down, and left,
 * ending with 8. Finally, you move up four times (stopping at "2"), then down
 * once, ending with 5. So, in this example, the bathroom code is 1985.
 * 
 * Your puzzle input is the instructions from the document you found at the
 * front desk. What is the bathroom code?
 * 
 * @author Chris Rooney
 *
 */
public class Day2 {

	public static void main(String[] args) {

		int[][] keypad = { { 1, 4, 7 }, { 2, 5, 8 }, { 3, 6, 9 } };

		int w = 3;
		int h = 3;

		Scanner scn = null;

		try {
			scn = new Scanner(new File(args[0]));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		int x = 1;
		int y = 1;

		while (scn.hasNextLine()) {

			String nextMove = scn.nextLine();

			for (int i = 0; i < nextMove.length(); ++i) {
				char dir = nextMove.charAt(i);

				switch (dir) {
				case 'U':
					y = y == 0 ? y : y - 1;
					break;
				case 'R':
					x = x == w - 1 ? x : x + 1;
					break;
				case 'L':
					x = x == 0 ? x : x - 1;
					break;
				case 'D':
					y = y == h - 1 ? y : y + 1;
					break;
				}
			}
			System.out.print(keypad[x][y]);
		}
		System.out.println("");

	}

}