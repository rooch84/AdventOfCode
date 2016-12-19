package org.mdx.advent;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Test input is 5
 * Actual input is 3017957
 * 
 * --- Part Two ---
 * 
 * Realizing the folly of their present-exchange rules, the Elves agree to
 * instead steal presents from the Elf directly across the circle. If two Elves
 * are across the circle, the one on the left (from the perspective of the
 * stealer) is stolen from. The other rules remain unchanged: Elves with no
 * presents are removed from the circle entirely, and the other elves move in
 * slightly to keep the circle evenly spaced.
 * 
 * For example, with five Elves (again numbered 1 to 5):
 * 
 * The Elves sit in a circle; Elf 1 goes first: 1 5 2 4 3 Elves 3 and 4 are
 * across the circle; Elf 3's present is stolen, being the one to the left. Elf
 * 3 leaves the circle, and the rest of the Elves move in: 1 1 5 2 --> 5 2 4 - 4
 * Elf 2 steals from the Elf directly across the circle, Elf 5: 1 1 - 2 --> 2 4
 * 4 Next is Elf 4 who, choosing between Elves 1 and 2, steals from Elf 1: - 2 2
 * --> 4 4 Finally, Elf 2 steals from Elf 4: 2 --> 2 - So, with five Elves, the
 * Elf that sits starting in position 2 gets all the presents.
 * 
 * With the number of Elves given in your puzzle input, which Elf now gets all
 * the presents?
 * 
 * @author Chris Rooney
 *
 */
public class Day19PartB {

	public static void main(String[] args) {
		int s = Integer.parseInt(args[0]);
		List<Integer> e = new ArrayList<Integer>();

		for (int i = 0; i < s; ++i) {
			e.add(i + 1);
		}

		int e1 = 0;
		while (e.size() > 1) {
			int e2 = (e1 + e.size() / 2) % e.size();
			e.remove(e2);
			if (e2 > e1) {
				e1 = (e1 + 1) % e.size();
			} else {
				e1 = e1 % e.size();
			}
		}
		System.out.println(e.get(0));
	}

}
