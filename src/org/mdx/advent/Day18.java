package org.mdx.advent;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * --- Day 18: Like a Rogue ---
 * 
 * As you enter this room, you hear a loud click! Some of the tiles in the floor
 * here seem to be pressure plates for traps, and the trap you just triggered
 * has run out of... whatever it tried to do to you. You doubt you'll be so
 * lucky next time.
 * 
 * Upon closer examination, the traps and safe tiles in this room seem to follow
 * a pattern. The tiles are arranged into rows that are all the same width; you
 * take note of the safe tiles (.) and traps (^) in the first row (your puzzle
 * input).
 * 
 * The type of tile (trapped or safe) in each row is based on the types of the
 * tiles in the same position, and to either side of that position, in the
 * previous row. (If either side is off either end of the row, it counts as
 * "safe" because there isn't a trap embedded in the wall.)
 * 
 * For example, suppose you know the first row (with tiles marked by letters)
 * and want to determine the next row (with tiles marked by numbers):
 * 
 * ABCDE 12345 The type of tile 2 is based on the types of tiles A, B, and C;
 * the type of tile 5 is based on tiles D, E, and an imaginary "safe" tile.
 * Let's call these three tiles from the previous row the left, center, and
 * right tiles, respectively. Then, a new tile is a trap only in one of the
 * following situations:
 * 
 * Its left and center tiles are traps, but its right tile is not. Its center
 * and right tiles are traps, but its left tile is not. Only its left tile is a
 * trap. Only its right tile is a trap. In any other situation, the new tile is
 * safe.
 * 
 * Then, starting with the row ..^^., you can determine the next row by applying
 * those rules to each new tile:
 * 
 * The leftmost character on the next row considers the left (nonexistent, so we
 * assume "safe"), center (the first ., which means "safe"), and right (the
 * second ., also "safe") tiles on the previous row. Because all of the trap
 * rules require a trap in at least one of the previous three tiles, the first
 * tile on this new row is also safe, .. The second character on the next row
 * considers its left (.), center (.), and right (^) tiles from the previous
 * row. This matches the fourth rule: only the right tile is a trap. Therefore,
 * the next tile in this new row is a trap, ^. The third character considers
 * .^^, which matches the second trap rule: its center and right tiles are
 * traps, but its left tile is not. Therefore, this tile is also a trap, ^. The
 * last two characters in this new row match the first and third rules,
 * respectively, and so they are both also traps, ^. After these steps, we now
 * know the next row of tiles in the room: .^^^^. Then, we continue on to the
 * next row, using the same rules, and get ^^..^. After determining two new
 * rows, our map looks like this:
 * 
 * ..^^. .^^^^ ^^..^ Here's a larger example with ten tiles per row and ten
 * rows:
 * 
 * .^^.^.^^^^ ^^^...^..^ ^.^^.^.^^. ..^^...^^^ .^^^^.^^.^ ^^..^.^^.. ^^^^..^^^.
 * ^..^^^^.^^ .^^^..^.^^ ^^.^^^..^^ In ten rows, this larger example has 38 safe
 * tiles.
 * 
 * Starting with the map in your puzzle input, in a total of 40 rows (including
 * the starting row), how many safe tiles are there?
 * 
 * --- Part Two ---
 * 
 * How many safe tiles are there in a total of 400000 rows?
 * 
 * 
 * @author Chris Rooney
 *
 */
public class Day18 {

	public static void main(String[] args) {

		if (args.length != 2) {
			System.out.println("Error. Expect usage is <rows> <file>");
			System.exit(0);
		}
		
		String exp = "\\.\\.\\^|\\.\\^\\^|\\^\\.\\.|\\^\\^\\.";
		Pattern p = Pattern.compile(exp);
		Pattern p2 = Pattern.compile("\\.");
		Matcher m;

		Scanner scn = null;
		try {
			scn = new Scanner(new File(args[1]));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String line = "";
		while (scn.hasNextLine()) {
			line = scn.nextLine();
			break;
		}
		scn.close();
		int length = Integer.parseInt(args[0]);
		int c = 0;

		for (int i = 0; i < length; ++i) {
			String nextLine = "";
			for (int j = -1; j < line.length() - 1; ++j) {
				String sub = "";
				if (j < 0) {
					sub = "." + line.substring(0, 2);
				} else if (j > line.length() - 3) {
					sub = line.substring(j, j + 2) + ".";
				} else {
					sub = line.substring(j, j + 3);
				}
				m = p.matcher(sub);

				if (m.matches()) {
					nextLine += "^";
				} else {
					nextLine += ".";
				}
			}

			m = p2.matcher(line);

			while (m.find()) {
				c++;
			}
			line = nextLine;
		}

		System.out.println(c);
	}
}
