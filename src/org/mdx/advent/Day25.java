package org.mdx.advent;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * --- Day 23: Safe Cracking ---
 * 
 * This is one of the top floors of the nicest tower in EBHQ. The Easter Bunny's
 * private office is here, complete with a safe hidden behind a painting, and
 * who wouldn't hide a star in a safe behind a painting?
 * 
 * The safe has a digital screen and keypad for code entry. A sticky note
 * attached to the safe has a password hint on it: "eggs". The painting is of a
 * large rabbit coloring some eggs. You see 7.
 * 
 * When you go to type the code, though, nothing appears on the display;
 * instead, the keypad comes apart in your hands, apparently having been
 * smashed. Behind it is some kind of socket - one that matches a connector in
 * your prototype computer! You pull apart the smashed keypad and extract the
 * logic circuit, plug it into your computer, and plug your computer into the
 * safe.
 * 
 * Now, you just need to figure out what output the keypad would have sent to
 * the safe. You extract the assembunny code from the logic chip (your puzzle
 * input). The code looks like it uses almost the same architecture and
 * instruction set that the monorail computer used! You should be able to use
 * the same assembunny interpreter for this as you did there, but with one new
 * instruction:
 * 
 * tgl x toggles the instruction x away (pointing at instructions like jnz does:
 * positive means forward; negative means backward):
 * 
 * For one-argument instructions, inc becomes dec, and all other one-argument
 * instructions become inc. For two-argument instructions, jnz becomes cpy, and
 * all other two-instructions become jnz. The arguments of a toggled instruction
 * are not affected. If an attempt is made to toggle an instruction outside the
 * program, nothing happens. If toggling produces an invalid instruction (like
 * cpy 1 2) and an attempt is later made to execute that instruction, skip it
 * instead. If tgl toggles itself (for example, if a is 0, tgl a would target
 * itself and become inc a), the resulting instruction is not executed until the
 * next time it is reached. For example, given this program:
 * 
 * cpy 2 a tgl a tgl a tgl a cpy 1 a dec a dec a cpy 2 a initializes register a
 * to 2. The first tgl a toggles an instruction a (2) away from it, which
 * changes the third tgl a into inc a. The second tgl a also modifies an
 * instruction 2 away from it, which changes the cpy 1 a into jnz 1 a. The
 * fourth line, which is now inc a, increments a to 3. Finally, the fifth line,
 * which is now jnz 1 a, jumps a (3) instructions ahead, skipping the dec a
 * instructions. In this example, the final value in register a is 3.
 * 
 * The rest of the electronics seem to place the keypad entry (the number of
 * eggs, 7) in register a, run the code, and then send the value left in
 * register a to the safe.
 * 
 * What value should be sent to the safe?
 * 
 * --- Part Two ---
 * 
 * The safe doesn't open, but it does make several angry noises to express its
 * frustration.
 * 
 * You're quite sure your logic is working correctly, so the only other thing
 * is... you check the painting again. As it turns out, colored eggs are still
 * eggs. Now you count 12.
 * 
 * As you run the program with this new input, the prototype computer begins to
 * overheat. You wonder what's taking so long, and whether the lack of any
 * instruction more powerful than "add one" has anything to do with it. Don't
 * bunnies usually multiply?
 * 
 * Anyway, what value should actually be sent to the safe?
 * 
 * @author Chris Rooney
 *
 */
public class Day25 {

	public static void main(String[] args) {

		if (args.length != 2) {
			System.err.println("Expected input is the file name.");
			System.exit(0);
		}

		Scanner scn = null;

		System.out.println(Integer.MAX_VALUE - 1852);

		int[] r = new int[4];
		List<Instruction> inst = new ArrayList<Instruction>();

		for (int i = 0; i < r.length; ++i) {
			r[i] = 0;
		}

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
					ins.b = Integer.parseInt(j.split(" ")[2]);
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
			} else if (j.startsWith("out")) {
				ins.m = 5;
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
		scn.close();

		for (int m = 0; m < 2000; ++m) {
			r[0] = m;
			r[1] = 0;
			r[2] = 0;
			r[3] = 0;
			int last = 1;
			System.out.println("Trying " + m);
			for (int i = 0; i < inst.size(); ++i) {
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
						i += (w - 1);
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
						if ((j.aIsNum && j.a == 0) || (!j.aIsNum && r[j.a] == 0)) {
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
				} else if (j.m == 5) {
					int b = j.a;
					if (!j.aIsNum) {
						b = r[j.a];
					}
					if (b == last) {
						break;
					}
					last = b;
				}
			}
		}
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
