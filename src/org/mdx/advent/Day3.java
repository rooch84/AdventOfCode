package org.mdx.advent;

import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * --- Day 3: Squares With Three Sides ---
 * 
 * Now that you can think clearly, you move deeper into the labyrinth of
 * hallways and office furniture that makes up this part of Easter Bunny HQ.
 * This must be a graphic design department; the walls are covered in
 * specifications for triangles.
 * 
 * Or are they?
 * 
 * The design document gives the side lengths of each triangle it describes,
 * but... 5 10 25? Some of these aren't triangles. You can't help but mark the
 * impossible ones.
 * 
 * In a valid triangle, the sum of any two sides must be larger than the
 * remaining side. For example, the "triangle" given above is impossible,
 * because 5 + 10 is not larger than 25.
 * 
 * In your puzzle input, how many of the listed triangles are possible?
 * 
 * @author Chris Rooney
 *
 */
public class Day3 {

	public static void main(String[] args) {

		Scanner scn = null;

		try {
			scn = new Scanner(new File(args[0]));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		int numSides = 3;
		int total = 0;

		while (scn.hasNextLine()) {
			String line = scn.nextLine().trim();
			int[] sides = new int[numSides];

			int i = 0;
			for (String s : line.split("\\s+")) {
				sides[i++] = Integer.valueOf(s);
			}
			Arrays.sort(sides);
			if (sides[0] + sides[1] > sides[2]) {
				total++;
			}

		}
		System.out.println(total);
	}
}