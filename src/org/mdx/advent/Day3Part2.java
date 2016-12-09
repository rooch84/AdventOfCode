package org.mdx.advent;

import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * --- Part Two ---
 * 
 * Now that you've helpfully marked up their design documents, it occurs to you
 * that triangles are specified in groups of three vertically. Each set of three
 * numbers in a column specifies a triangle. Rows are unrelated.
 * 
 * For example, given the following specification, numbers with the same
 * hundreds digit would be part of the same triangle:
 * 
 * 101 301 501 102 302 502 103 303 503 201 401 601 202 402 602 203 403 603 In
 * your puzzle input, and instead reading by columns, how many of the listed
 * triangles are possible?
 * 
 * 
 * @author Chris Rooney
 *
 */
public class Day3Part2 {

	public static void main(String[] args) {

		Scanner scn = null;

		try {
			scn = new Scanner(new File(args[0]));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		int numSides = 3;
		int numCols = 3;
		int total = 0;

		int[][] sides = new int[numCols][numSides];
		int count = 0;
		while (scn.hasNextLine()) {

			String line = scn.nextLine().trim();

			int i = 0;
			for (String s : line.split("\\s+")) {
				sides[i++][count] = Integer.valueOf(s);
			}

			count++;

			if (count > 0 && count % numCols == 0) {

				for (int j = 0; j < numCols; ++j) {
					Arrays.sort(sides[j]);
					if (sides[j][0] + sides[j][1] > sides[j][2]) {
						total++;
					}
				}
				sides = new int[numCols][numSides];
				count = 0;
			}
		}
		System.out.println(total);
	}
}