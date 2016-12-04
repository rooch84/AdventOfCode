package org.mdx.advent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Day4PartB {

	public static void main(String[] args) {

		Scanner scn = null;

		try {
			scn = new Scanner(new File(args[0]));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		int total = 0;

		while (scn.hasNextLine()) {
			String line = scn.nextLine().trim();

			String hash = line.substring(0, line.length() - 7);
			String check = line.substring(line.length() - 7, line.length()).replace("[", "").replace("]", "");
			String[] hashSplit = hash.split("-");
			String strId = hashSplit[hashSplit.length - 1];

			String hashClean = "";
			for (int i = 0; i < hashSplit.length - 1; ++i) {
				hashClean += hashSplit[i];
			}
			char[] chars = new char[hashClean.length()];
			hashClean.getChars(0,hashClean.length() , chars,0);
			Arrays.sort(chars);
			Map<Integer, List<Character>> byCount = new HashMap<Integer, List<Character>>();
			char current = '$';
			int count = 0;
			int max = -1;
			for (int i = 0; i < chars.length; ++i) {
				if (chars[i] != current || i == chars.length -1 ) {
					if (current != '$') {
						if (!byCount.containsKey(count)) {
							byCount.put(count, new ArrayList<Character>());
						}
						byCount.get(count).add(current);
						if (max < count) {
							max = count;
						}
					}
					count = 1;
					current = chars[i];
				} else {
					count++;
				}
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
				
				String nameOnly = "";
				for (int i = 0; i < hashSplit.length - 1; ++i) {
					nameOnly += hashSplit[i] + "-";
				}
				
				String dec = decryptRoom(nameOnly, Integer.parseInt(strId));
				if (dec.contains("north")) {
					System.out.println(dec);
					System.out.println(strId);
				}
			}

		}
		System.out.println(total);
	}
	
	private static String decryptRoom(String enc, int parseInt) {
		String name = "";
		for (int i = 0; i < enc.length(); ++i) {
			if (enc.charAt(i) == '-') {
				name += "-";
			} else {
				char foo = (char) (enc.charAt(i) + (parseInt % 26));
				if (enc.charAt(i) + (parseInt % 26) >= 123) {
					foo = (char) (enc.charAt(i) + (parseInt % 26) - 26);
				}

				name += foo;
			}
		}
		return name;

	}

}



