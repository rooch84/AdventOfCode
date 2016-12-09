package org.mdx.advent;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * --- Part Two ---
 * 
 * You finally arrive at the bathroom (it's a several minute walk from the lobby
 * so visitors can behold the many fancy conference rooms and water coolers on
 * this floor) and go to punch in the code. Much to your bladder's dismay, the
 * keypad is not at all like you imagined it. Instead, you are confronted with
 * the result of hundreds of man-hours of bathroom-keypad-design meetings:
 * 
 * 1 2 3 4 5 6 7 8 9 A B C D You still start at "5" and stop when you're at an
 * edge, but given the same instructions as above, the outcome is very
 * different:
 * 
 * You start at "5" and don't move at all (up and left are both edges), ending
 * at 5. Continuing from "5", you move right twice and down three times (through
 * "6", "7", "B", "D", "D"), ending at D. Then, from "D", you move five more
 * times (through "D", "B", "C", "C", "B"), ending at B. Finally, after five
 * more moves, you end at 3. So, given the actual keypad layout, the code would
 * be 5DB3.
 * 
 * Using the same instructions in your puzzle input, what is the correct
 * bathroom code?
 * 
 * 
 * @author Chris Rooney
 *
 */
public class Day2PartB {

	public static void main(String[] args) {

		char[][] keypad = { { '$', '$', '5', '$', '$' }, { '$', '2', '6', 'A', '$' }, { '1', '3', '7', 'B', 'D' },
				{ '$', '4', '8', 'C', '$' }, { '$', '$', '9', '$', '$' } };

		int width = 5;
		int height = 5;
		Scanner scn = null;

		try {
			scn = new Scanner(new File(args[0]));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		int x = 0;
		int y = 2;

		while (scn.hasNextLine()) {

			String nextMove = scn.nextLine();

			for (int i = 0; i < nextMove.length(); ++i) {
				char dir = nextMove.charAt(i);

				switch (dir) {
				case 'U':
					y = y == 0 || keypad[x][y - 1] == '$' ? y : y - 1;
					break;
				case 'R':
					x = x == width - 1 || keypad[x + 1][y] == '$' ? x : x + 1;
					break;
				case 'L':
					x = x == 0 || keypad[x - 1][y] == '$' ? x : x - 1;
					break;
				case 'D':
					y = y == height - 1 || keypad[x][y + 1] == '$' ? y : y + 1;
					break;
				}

			}

			System.out.print(keypad[x][y]);
		}
		System.out.println("");

	}

}