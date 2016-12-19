package org.mdx.advent;

/**
 * Test input is 5
 * Actual input is 3017957
 * 
 * --- Day 19: An Elephant Named Joseph ---
 * 
 * The Elves contact you over a highly secure emergency channel. Back at the
 * North Pole, the Elves are busy misunderstanding White Elephant parties.
 * 
 * Each Elf brings a present. They all sit in a circle, numbered starting with
 * position 1. Then, starting with the first Elf, they take turns stealing all
 * the presents from the Elf to their left. An Elf with no presents is removed
 * from the circle and does not take turns.
 * 
 * For example, with five Elves (numbered 1 to 5):
 * 
 * 1 5 2 4 3 Elf 1 takes Elf 2's present. Elf 2 has no presents and is skipped.
 * Elf 3 takes Elf 4's present. Elf 4 has no presents and is also skipped. Elf 5
 * takes Elf 1's two presents. Neither Elf 1 nor Elf 2 have any presents, so
 * both are skipped. Elf 3 takes Elf 5's three presents. So, with five Elves,
 * the Elf that sits starting in position 3 gets all the presents.
 * 
 * With the number of Elves given in your puzzle input, which Elf gets all the
 * presents?
 * 
 * @author Chris Rooney
 *
 */
public class Day19 {

	public static void main(String[] args) {
		int s = Integer.parseInt(args[0]);
		int[] e = new int[s];
		for (int i = 0; i < s; ++i) {
			e[i] = 1;
		}
		boolean oneE = false;

		int e1 = 0;
		int e2 = 0;
		while (!oneE) {

			e2 = (e1 + 1) % s;
			while (e[e2] == 0 && e2 != e1) {
				e2 = (e2 + 1) % s;
			}

			if (e2 == e1) {
				oneE = true;
				break;
			}

			e[e1] += e[e2];
			e[e2] = 0;

			int eTmp = e1;
			e1 = (e1 + 1) % s;
			while (e[e1] == 0 && e1 != eTmp) {
				e1 = (e1 + 1) % s;
			}
		}

		for (int i = 0; i < s; ++i) {
			if (e[i] != 0) {
				System.out.println(i + 1);
			}
		}
	}

}
