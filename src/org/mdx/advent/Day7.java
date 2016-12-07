package org.mdx.advent;

import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * --- Day 7: Internet Protocol Version 7 ---
 * 
 * While snooping around the local network of EBHQ, you compile a list of IP
 * addresses (they're IPv7, of course; IPv6 is much too limited). You'd like to
 * figure out which IPs support TLS (transport-layer snooping).
 * 
 * An IP supports TLS if it has an Autonomous Bridge Bypass Annotation, or ABBA.
 * An ABBA is any four-character sequence which consists of a pair of two
 * different characters followed by the reverse of that pair, such as xyyx or
 * abba. However, the IP also must not have an ABBA within any hypernet
 * sequences, which are contained by square brackets.
 * 
 * For example:
 * 
 * abba[mnop]qrst supports TLS (abba outside square brackets). abcd[bddb]xyyx
 * does not support TLS (bddb is within square brackets, even though xyyx is
 * outside square brackets). aaaa[qwer]tyui does not support TLS (aaaa is
 * invalid; the interior characters must be different). ioxxoj[asdfgh]zxcvbn
 * supports TLS (oxxo is outside square brackets, even though it's within a
 * larger string). 
 * 
 * How many IPs in your puzzle input support TLS?
 * 
 * @author Chris Rooney
 *
 */
public class Day7 {

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
			boolean invalid = true;
			for (int i = 0; i < c.length; ++i) {
				if (c[i] == '[') {
					inHypernet = true;
				} else if (c[i] == ']') {
					inHypernet = false;
				}
				if (i <= c.length - 4) {
					boolean abba = isABBA(Arrays.copyOfRange(c, i, i + 4));
					if (abba && inHypernet) {
						invalid = true;
						break;
					} else if (abba) {
						invalid = false;
					}
				}
			}
			
			if (!invalid) {
				total++;
			}

		}

		System.out.println(total);

	}

	public static boolean isABBA(char[] i) {
		if (i[0] == i[3] && i[1] == i[2] && i[0] != i[1]) {
			return true;
		}

		return false;
	}
}

