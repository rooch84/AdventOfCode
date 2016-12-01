package org.mdx.advent;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Day1 {

	public static void main(String[] args) {
		int currentDir = 0;
		int[] distance = new int[2];
		distance[0] = 0;
		distance[1] = 0;

		Scanner scn = null;

		try {
			scn = new Scanner(new File(args[0]));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		while (scn.hasNext()) {

			String nextMove = scn.next().replace(",", "");
			char direction = nextMove.charAt(0);
			int amount = Integer.parseInt(nextMove.substring(1));

			currentDir = newOrientation(currentDir, direction);
			switch (currentDir) {
			case 0:

				distance[0] += amount;
				break;
			case 1:
				distance[1] += amount;
				break;
			case 2:
				distance[0] -= amount;
				break;
			case 3:
				distance[1] -= amount;
				break;
			}
		}
		System.out
				.println("Total distance is " + (Math.abs(distance[0]) + Math.abs(distance[1]) + " blocks away"));

	}

	static int newOrientation(int currentDir, char dir) {
		if (dir == 'L') {
			currentDir = (currentDir == 0) ? 3 : currentDir - 1;
		} else {
			currentDir = (currentDir == 3) ? 0 : currentDir + 1;
		}

		return currentDir;
	}

}