package org.mdx.advent;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * --- Day 12: Leonardo's Monorail ---
 * 
 * You finally reach the top floor of this building: a garden with a slanted
 * glass ceiling. Looks like there are no more stars to be had.
 * 
 * While sitting on a nearby bench amidst some tiger lilies, you manage to
 * decrypt some of the files you extracted from the servers downstairs.
 * 
 * According to these documents, Easter Bunny HQ isn't just this building - it's
 * a collection of buildings in the nearby area. They're all connected by a
 * local monorail, and there's another building not far from here!
 * Unfortunately, being night, the monorail is currently not operating.
 * 
 * You remotely connect to the monorail control systems and discover that the
 * boot sequence expects a password. The password-checking logic (your puzzle
 * input) is easy to extract, but the code it uses is strange: it's assembunny
 * code designed for the new computer you just assembled. You'll have to execute
 * the code and get the password.
 * 
 * The assembunny code you've extracted operates on four registers (a, b, c, and
 * d) that start at 0 and can hold any integer. However, it seems to make use of
 * only a few instructions:
 * 
 * cpy x y copies x (either an integer or the value of a register) into register
 * y. inc x increases the value of register x by one. dec x decreases the value
 * of register x by one. jnz x y jumps to an instruction y away (positive means
 * forward; negative means backward), but only if x is not zero. The jnz
 * instruction moves relative to itself: an offset of -1 would continue at the
 * previous instruction, while an offset of 2 would skip over the next
 * instruction.
 * 
 * For example:
 * 
 * cpy 41 a inc a inc a dec a jnz a 2 dec a The above code would set register a
 * to 41, increase its value by 2, decrease its value by 1, and then skip the
 * last dec a (because a is not zero, so the jnz a 2 skips it), leaving register
 * a at 42. When you move past the last instruction, the program halts.
 * 
 * After executing the assembunny code in your puzzle input, what value is left
 * in register a?
 * 
 * --- Part Two ---
 * 
 * As you head down the fire escape to the monorail, you notice it didn't start;
 * register c needs to be initialized to the position of the ignition key.
 * 
 * If you instead initialize register c to be 1, what value is now left in
 * register a?
 * 
 * 
 * @author Chris Rooney
 *
 */
public class Day23 {

	public static void main(String[] args) {

		if (args.length != 2) {
			System.err.println("Expected input is the file name and the initial value of reg a.");
			System.exit(0);
		}

		Scanner scn = null;

		int[] r = new int[4];
		List<Instruction> inst = new ArrayList<Instruction>();

		for (int i = 0; i < r.length; ++i) {
			r[i] = 0;
		}

		r[0] = Integer.parseInt(args[1]);

		try {
			scn = new Scanner(new File(args[0]));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		while (scn.hasNextLine()) {
			String j = scn.nextLine();

			Instruction ins = new Instruction();

			if (j.startsWith("jnz")) {
				ins.m = 0;

				char c1 = j.split(" ")[1].charAt(0);
				char c2 = j.split(" ")[2].charAt(0);

				if (c1 >= 97 && c1 <= 100) {
					ins.a = c1 - 97;
					ins.aIsNum = false;
				} else {
					ins.a = Integer.parseInt(j.split(" ")[1]);
					ins.aIsNum = true;
				}
				if (c2 >= 97 && c2 <= 100) {
					ins.b = c2 - 97;
					ins.bIsNum = false;
				} else {
					ins.b = Integer.parseInt(j.split(" ")[2]) - 1;
					ins.bIsNum = true;
				}
			} else if (j.startsWith("cpy")) {
				ins.m = 1;
				char c1 = j.split(" ")[1].charAt(0);
				char c2 = j.split(" ")[2].charAt(0);
				if (c1 >= 97 && c1 <= 100) {
					ins.a = c1 - 97;
					ins.aIsNum = false;
				} else {
					ins.a = Integer.parseInt(j.split(" ")[1]);
					ins.aIsNum = true;
				}
				ins.b = c2 - 97;
				ins.bIsNum = false;
			} else if (j.startsWith("inc")) {
				ins.m = 2;
				ins.a = j.split(" ")[1].charAt(0) - 97;
			} else if (j.startsWith("dec")) {
				ins.m = 3;
				ins.a = j.split(" ")[1].charAt(0) - 97;
			} else if (j.startsWith("tgl")) {
				ins.m = 4;
				char c1 = j.split(" ")[1].charAt(0);
				if (c1 >= 97 && c1 <= 100) {
					ins.a = c1 - 97;
					ins.aIsNum = false;
				} else {
					ins.a = Integer.parseInt(j.split(" ")[1]);
					ins.aIsNum = true;
				}
			}

			inst.add(ins);

		}

		for (int i = 0; i < inst.size(); ++i) {
			//System.out.println("i: " + i + " (" + r[0] + ", " + r[1] + ", " + r[2] + ", " + r[3] + ")");
			Instruction j = inst.get(i);
			if (j.m == 0) {
				int v = j.a;
				if (!j.aIsNum) {
					v = r[j.a];
				}
				int w = j.b;
				if (!j.bIsNum) {
					w = r[j.b];
				}
				if (v != 0) {
				//	System.out.println("skipping: " + w);
					i += w;
				}
			} else if (j.m == 1) {
				if (!j.bIsNum) {
					if (!j.aIsNum) {
						r[j.b] = r[j.a];
					} else {
						r[j.b] = j.a;
					}
				}
			} else if (j.m == 2) {
				r[j.a]++;
			} else if (j.m == 3) {
				r[j.a]--;
			} else if (j.m == 4) {
				if (j.toBeToggled) {
					j.m = 2;
				} else {
					int x = -1;
					if (j.aIsNum && i + j.a >= 0 && i + j.a < inst.size()) {
						x = i + j.a;
						
					} else if (!j.aIsNum && i + r[j.a] >= 0 && i + r[j.a] < inst.size()) {
						x = i + r[j.a];
					}
					//System.out.println("x: " + x);
					if ((j.aIsNum && j.a == 0) || (!j.aIsNum && r[j.a] == 0) ) {
						//System.out.println("I'm being called");
						j.toBeToggled = true;
					} else if (x > -1) {
						Instruction k = inst.get(x);
						if (k.m == 0) {
							k.m = 1;
						} else if (k.m == 1) {
							k.m = 0;
						} else if (k.m == 2) {
							k.m = 3;
						} else if (k.m == 3) {
							k.m = 2;
						} else if (k.m == 4) {
							k.m = 2;
						}
					}
				}
			}
		}
		System.out.println(r[0]);
	}

	private static class Instruction {
		int m;
		int a;
		int b;
		boolean aIsNum;
		boolean bIsNum;
		boolean toBeToggled = false;
	}
}
