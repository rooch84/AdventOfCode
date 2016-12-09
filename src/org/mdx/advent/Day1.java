package org.mdx.advent;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * --- Day 1: No Time for a Taxicab ---
 * 
 * Santa's sleigh uses a very high-precision clock to guide its movements, and
 * the clock's oscillator is regulated by stars. Unfortunately, the stars have
 * been stolen... by the Easter Bunny. To save Christmas, Santa needs you to
 * retrieve all fifty stars by December 25th.
 * 
 * Collect stars by solving puzzles. Two puzzles will be made available on each
 * day in the advent calendar; the second puzzle is unlocked when you complete
 * the first. Each puzzle grants one star. Good luck!
 * 
 * You're airdropped near Easter Bunny Headquarters in a city somewhere. "Near",
 * unfortunately, is as close as you can get - the instructions on the Easter
 * Bunny Recruiting Document the Elves intercepted start here, and nobody had
 * time to work them out further.
 * 
 * The Document indicates that you should start at the given coordinates (where
 * you just landed) and face North. Then, follow the provided sequence: either
 * turn left (L) or right (R) 90 degrees, then walk forward the given number of
 * blocks, ending at a new intersection.
 * 
 * There's no time to follow such ridiculous instructions on foot, though, so
 * you take a moment and work out the destination. Given that you can only walk
 * on the street grid of the city, how far is the shortest path to the
 * destination?
 * 
 * For example:
 * 
 * Following R2, L3 leaves you 2 blocks East and 3 blocks North, or 5 blocks
 * away. R2, R2, R2 leaves you 2 blocks due South of your starting position,
 * which is 2 blocks away. R5, L5, R5, R3 leaves you 12 blocks away. How many
 * blocks away is Easter Bunny HQ?
 * 
 * @author Chris Rooney
 *
 */
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
		System.out.println("Total distance is " + (Math.abs(distance[0]) + Math.abs(distance[1]) + " blocks away"));

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