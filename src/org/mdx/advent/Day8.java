package org.mdx.advent;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * --- Day 8: Two-Factor Authentication ---
 * 
 * You come across a door implementing what you can only assume is an
 * implementation of two-factor authentication after a long game of requirements
 * telephone.
 * 
 * To get past the door, you first swipe a keycard (no problem; there was one on
 * a nearby desk). Then, it displays a code on a little screen, and you type
 * that code on a keypad. Then, presumably, the door unlocks.
 * 
 * Unfortunately, the screen has been smashed. After a few minutes, you've taken
 * everything apart and figured out how it works. Now you just have to work out
 * what the screen would have displayed.
 * 
 * The magnetic strip on the card you swiped encodes a series of instructions
 * for the screen; these instructions are your puzzle input. The screen is 50
 * pixels wide and 6 pixels tall, all of which start off, and is capable of
 * three somewhat peculiar operations:
 * 
 * rect AxB turns on all of the pixels in a rectangle at the top-left of the
 * screen which is A wide and B tall. rotate row y=A by B shifts all of the
 * pixels in row A (0 is the top row) right by B pixels. Pixels that would fall
 * off the right end appear at the left end of the row. rotate column x=A by B
 * shifts all of the pixels in column A (0 is the left column) down by B pixels.
 * Pixels that would fall off the bottom appear at the top of the column. For
 * example, here is a simple sequence on a smaller screen:
 * 
 * rect 3x2 creates a small rectangle in the top-left corner:
 * 
 * ###.... ###.... ....... rotate column x=1 by 1 rotates the second column down
 * by one pixel:
 * 
 * #.#.... ###.... .#..... rotate row y=0 by 4 rotates the top row right by four
 * pixels:
 * 
 * ....#.# ###.... .#..... rotate column x=1 by 1 again rotates the second
 * column down by one pixel, causing the bottom pixel to wrap back to the top:
 * 
 * .#..#.# #.#.... .#..... As you can see, this display technology is extremely
 * powerful, and will soon dominate the tiny-code-displaying-screen market.
 * That's what the advertisement on the back of the display tries to convince
 * you, anyway.
 * 
 * There seems to be an intermediate check of the voltage used by the display:
 * after you swipe your card, if the screen did work, how many pixels should be
 * lit?
 * 
 * --- Part Two ---
 * 
 * You notice that the screen is only capable of displaying capital letters; in
 * the font it uses, each letter is 5 pixels wide and 6 tall.
 * 
 * After you swipe your card, what code is the screen trying to display?
 * 
 * @author Chris Rooney
 *
 */
public class Day8 {

	public static void main(String[] args) {

		int w = 50;
		int h = 6;
		char[][] d = new char[w][h];

		Scanner scn = null;

		try {
			scn = new Scanner(new File(args[0]));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		updateDisplay(d, w, h, '.');

		while (scn.hasNextLine()) {
			String line = scn.nextLine();

			if (line.startsWith("rect")) {
				int rW = Integer.parseInt(line.split(" ")[1].split("x")[0]);
				int rH = Integer.parseInt(line.split(" ")[1].split("x")[1]);
				updateDisplay(d, rW, rH, '#');
			} else if (line.startsWith("rotate column")) {

				int i = Integer.parseInt(line.split(" ")[2].split("=")[1]);
				int a = Integer.parseInt(line.split(" ")[4]);

				for (int x = 0; x < a; ++x) {
					char tmp = d[i][h - 1];
					for (int j = h - 1; j >= 0; --j) {
						if (j > 0) {
							d[i][j] = d[i][j - 1];
						} else {
							d[i][j] = tmp;
						}
					}
				}
			} else if (line.startsWith("rotate row")) {
				int i = Integer.parseInt(line.split(" ")[2].split("=")[1]);
				int a = Integer.parseInt(line.split(" ")[4]);

				for (int x = 0; x < a; ++x) {
					char tmp = d[w - 1][i];
					for (int j = w - 1; j >= 0; --j) {
						if (j > 0) {
							d[j][i] = d[j - 1][i];
						} else {
							d[j][i] = tmp;
						}
					}

				}
			}
		}

		System.out.println(counter(d, w, h));
		printDisplay(d, w, h);
	}

	private static void updateDisplay(char[][] d, int w, int h, char c) {
		for (int j = 0; j < h; ++j) {
			for (int i = 0; i < w; ++i) {
				d[i][j] = c;
			}
		}
	}

	public static void printDisplay(char[][] d, int w, int h) {
		for (int j = 0; j < h; ++j) {
			for (int i = 0; i < w; ++i) {
				System.out.print(d[i][j]);
			}
			System.out.println("");
		}
	}

	public static int counter(char[][] d, int w, int h) {
		int total = 0;
		for (int j = 0; j < h; ++j) {
			for (int i = 0; i < w; ++i) {
				if (d[i][j] == '#') {
					total++;
				}
			}
		}
		return total;
	}
}
