package org.mdx.advent;

import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

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