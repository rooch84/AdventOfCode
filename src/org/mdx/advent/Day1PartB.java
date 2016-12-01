package org.mdx.advent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.io.File;
import java.io.FileNotFoundException;

public class Day1PartB {

	public static void main(String[] args) {
		int currentDir = 0;
		int[] distance = new int[2];
		distance[0] = 0;
		distance[1] = 0;

		Map<Integer, Set<Integer>> uniqueLocations = new HashMap<Integer, Set<Integer>>();

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

			boolean sameLocation = false;
			currentDir = newOrientation(currentDir, direction);
			for (int i = 1; i <= amount; ++i) {
				switch (currentDir) {
				case 0:

					distance[0] += 1;
					break;
				case 1:
					distance[1] += 1;
					break;
				case 2:
					distance[0] -= 1;
					break;
				case 3:
					distance[1] -= 1;
					break;
				}
				sameLocation = uniqueLocationTest(distance, uniqueLocations);
				if (sameLocation)
					break;
			}
			if (sameLocation) {
				System.out.println(
						"Same location was found " + (Math.abs(distance[0]) + Math.abs(distance[1]) + " blocks away"));
				break;
			}
		}
	}

	private static boolean uniqueLocationTest(int[] distance, Map<Integer, Set<Integer>> uniqueLocations) {
		if (uniqueLocations.containsKey(distance[0])) {
			if (uniqueLocations.get(distance[0]).contains(distance[1])) {
				return true;
			} else {
				uniqueLocations.get(distance[0]).add(distance[1]);
			}
		} else {
			uniqueLocations.put(distance[0], new HashSet<Integer>());
			uniqueLocations.get(distance[0]).add(distance[1]);

		}

		return false;
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