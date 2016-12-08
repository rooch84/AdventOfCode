package org.mdx.advent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * --- Part Two ---
 * 
 * You would also like to know which IPs support SSL (super-secret listening).
 * 
 * An IP supports SSL if it has an Area-Broadcast Accessor, or ABA, anywhere in
 * the supernet sequences (outside any square bracketed sections), and a
 * corresponding Byte Allocation Block, or BAB, anywhere in the hypernet
 * sequences. An ABA is any three-character sequence which consists of the same
 * character twice with a different character between them, such as xyx or aba.
 * A corresponding BAB is the same characters but in reversed positions: yxy and
 * bab, respectively.
 * 
 * For example:
 * 
 * aba[bab]xyz supports SSL (aba outside square brackets with corresponding bab
 * within square brackets). xyx[xyx]xyx does not support SSL (xyx, but no
 * corresponding yxy). aaa[kek]eke supports SSL (eke in supernet with
 * corresponding kek in hypernet; the aaa sequence is not related, because the
 * interior character must be different). zazbz[bzb]cdb supports SSL (zaz has no
 * corresponding aza, but zbz has a corresponding bzb, even though zaz and zbz
 * overlap). 
 * 
 * How many IPs in your puzzle input support SSL?
 * 
 * @author Chris Rooney
 *
 */
public class Day7PartB {

	public static void main(String[] args) {

		Scanner scn = null;
		int total = 0;

		try {
			scn = new Scanner(new File(args[0]));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		while (scn.hasNextLine()) {
			char[] c = scn.nextLine().trim().toCharArray();
			boolean inHypernet = false;
			List<char[]> abaList = new ArrayList<char[]>();
			for (int i = 0; i < c.length; ++i) { // Outside hypernet scan
				if (c[i] == '[') {
					inHypernet = true;
				} else if (c[i] == ']') {
					inHypernet = false;
				}
				if (!inHypernet) {
					if (i <= c.length - 3) {
						if (isABA(Arrays.copyOfRange(c, i, i + 3))) {
							abaList.add(Arrays.copyOfRange(c, i, i + 3));
						}
					}
				}
			}

			for (int i = 0; i < c.length; ++i) { // Inside hypernet scan
				if (c[i] == '[') {
					inHypernet = true;
				} else if (c[i] == ']') {
					inHypernet = false;
				}
				if (inHypernet) {
					if (i <= c.length - 3) {
						if (abaCheck(abaList, Arrays.copyOfRange(c, i, i + 3))) {
							total++;
							break;
						}
					}
				}
			}

		}

		System.out.println(total);

	}

	private static boolean abaCheck(List<char[]> abaList, char[] i) {
		for (char[] aba : abaList) {
			if (i[0] == aba[1] && i[1] == aba[0] && i[2] == aba[1]) {
				return true;
			}
		}
		return false;
	}

	public static boolean isABA(char[] i) {
		if (i[0] == i[2] && i[0] != i[1] && bracketCheck(i)) {
			return true;
		}

		return false;
	}

	private static boolean bracketCheck(char[] c) {
		for (int i = 0; i < c.length; ++i) {
			if (c[i] == '[' || c[i] == ']') {
				return false;
			}
		}
		return true;
	}
}