package org.mdx.advent;

import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

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