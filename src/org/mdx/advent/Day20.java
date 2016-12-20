package org.mdx.advent;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * --- Day 20: Firewall Rules ---
 * 
 * You'd like to set up a small hidden computer here so you can use it to get
 * back into the network later. However, the corporate firewall only allows
 * communication with certain external IP addresses.
 * 
 * You've retrieved the list of blocked IPs from the firewall, but the list
 * seems to be messy and poorly maintained, and it's not clear which IPs are
 * allowed. Also, rather than being written in dot-decimal notation, they are
 * written as plain 32-bit integers, which can have any value from 0 through
 * 4294967295, inclusive.
 * 
 * For example, suppose only the values 0 through 9 were valid, and that you
 * retrieved the following blacklist:
 * 
 * 5-8 0-2 4-7 The blacklist specifies ranges of IPs (inclusive of both the
 * start and end value) that are not allowed. Then, the only IPs that this
 * firewall allows are 3 and 9, since those are the only numbers not in any
 * range.
 * 
 * Given the list of blocked IPs you retrieved from the firewall (your puzzle
 * input), what is the lowest-valued IP that is not blocked?
 * 
 * --- Part Two ---
 * 
 * How many IPs are allowed by the blacklist?
 * 
 * @author Chris Rooney
 *
 */
public class Day20 {

	public static void main(String[] args) {

		if (args.length != 2) {
			System.out.println("Error. Expect usage is <filename> <max range>");
			System.exit(0);
		}
		
		Scanner scn = null;
		try {
			scn = new Scanner(new File(args[0]));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		long max = Long.parseLong(args[1]);
		List<Range> r = new ArrayList<Range>();

		while (scn.hasNextLine()) {
			String line = scn.nextLine();
			r.add(new Range(Long.parseLong(line.split("-")[0]), Long.parseLong(line.split("-")[1])));
		}
		Collections.sort(r);

		long a = 0;
		long s = 0;
		boolean f = true;
		for (Range rng : r) {
			if ((a >= rng.s && a <= rng.e)) {
				a = rng.e + 1;
			} else if (a < rng.s) {
				if (f) {
					f = !f;
					System.out.println(a);
				}
				s += ((rng.s) - a);
				a = rng.e + 1;
			}
		}
		s += max - (a - 1);

		System.out.println(s);
	}

	private static class Range implements Comparable<Range> {
		long s;
		long e;

		public Range(long s, long e) {
			this.s = s;
			this.e = e;
		}

		@Override
		public int compareTo(Range o) {
			if (s - o.s < 0) {
				return -1;
			} else if (s - o.s > 0) {
				return 1;
			}

			return 0;
		}

	}
}
