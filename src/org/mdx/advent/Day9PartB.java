package org.mdx.advent;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * --- Part Two ---
 * 
 * Apparently, the file actually uses version two of the format.
 * 
 * In version two, the only difference is that markers within decompressed data
 * are decompressed. This, the documentation explains, provides much more
 * substantial compression capabilities, allowing many-gigabyte files to be
 * stored in only a few kilobytes.
 * 
 * For example:
 * 
 * (3x3)XYZ still becomes XYZXYZXYZ, as the decompressed section contains no
 * markers. X(8x2)(3x3)ABCY becomes XABCABCABCABCABCABCY, because the
 * decompressed data from the (8x2) marker is then further decompressed, thus
 * triggering the (3x3) marker twice for a total of six ABC sequences.
 * (27x12)(20x12)(13x14)(7x10)(1x12)A decompresses into a string of A repeated
 * 241920 times. (25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN
 * becomes 445 characters long. Unfortunately, the computer you brought probably
 * doesn't have enough memory to actually decompress the file; you'll have to
 * come up with another way to get its decompressed length.
 * 
 * What is the decompressed length of the file using this improved format?
 * 
 * @author Chris Rooney
 *
 */
public class Day9PartB {

	public static void main(String[] args) {

		Scanner scn = null;

		try {
			scn = new Scanner(new File(args[0]));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		while (scn.hasNextLine()) {
			System.out.println(decompress(scn.nextLine()));
		}

	}

	public static long decompress(String input) {
		long o = 0;
		for (int i = 0; i < input.length(); ++i) {
			char c = input.charAt(i);
			if (c == '(') {
				int e = input.indexOf(')', i);
				int length = Integer.parseInt(input.substring(i + 1, e).split("x")[0]);
				int mul = Integer.parseInt(input.substring(i + 1, e).split("x")[1]);
				long decomp = decompress(input.substring(e + 1, e + 1 + length));
				o += decomp * mul;
				i = e + length;
			} else {
				o += 1;
			}
		}
		return o;
	}

}
