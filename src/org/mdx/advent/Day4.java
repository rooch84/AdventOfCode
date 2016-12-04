package org.mdx.advent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Day4 {

	public static void main(String[] args) {

		Scanner scn = null;

		try {
			scn = new Scanner(new File(args[0]));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		int total = 0;

		while (scn.hasNextLine()) {
			Map<Character, CharFreq> uc = new HashMap<Character, CharFreq>();
			String line = scn.nextLine().trim();

			String hash = line.substring(0, line.length() - 7);
			String check = line.substring(line.length() - 7, line.length()).replace("[", "").replace("]", "");
			String[] hashSplit = hash.split("-");
			String strId = hashSplit[hashSplit.length - 1];

			String hashClean = "";
			for (int i = 0; i < hashSplit.length - 1; ++i) {
				hashClean += hashSplit[i];
			}

			for (int i = 0; i < hashClean.length(); ++i) {
				char c = hashClean.charAt(i);
				if (uc.containsKey(c)) {
					uc.get(c).count += 1;
				} else {
					uc.put(c, new CharFreq(c));
				}
			}

			List<CharFreq> list = new ArrayList<CharFreq>(uc.values());
			Collections.sort(list);

			Map<Integer, List<Character>> byCount = new HashMap<Integer, List<Character>>();

			int max = list.get(0).count;
			for (CharFreq cf : list) {
				if (!byCount.containsKey(cf.count)) {
					byCount.put(cf.count, new ArrayList<Character>());
				}
				byCount.get(cf.count).add(cf.c);
			}

			boolean missing = false;
			boolean cannotGoLower = false;
			boolean error = false;
			for (int i = max; i > 0; --i) {
				if (byCount.containsKey(i)) {
					for (Character c : byCount.get(i)) {
						if (check.contains("" + c)) {
							if (cannotGoLower) {
								error = true;
							}
							check = check.replaceAll("" + c, "");
						} else {
							missing = true;
						}
					}
				}
				if (missing) {
					cannotGoLower = true;
				}
			}

			if (!error && check.equals("")) {
				total += Integer.parseInt(strId);
			}

		}
		System.out.println(total);
	}

	static class CharFreq implements Comparable<CharFreq> {

		public char c;
		public int count;

		public CharFreq(char c) {
			this.c = c;
			count = 1;
		}

		@Override
		public int compareTo(CharFreq o) {
			return o.count - count;
		}

	}

}