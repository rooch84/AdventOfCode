package org.mdx.advent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Of course, that would be the message - if you hadn't agreed to use a modified
 * repetition code instead.
 * 
 * In this modified code, the sender instead transmits what looks like random
 * data, but for each character, the character they actually want to send is
 * slightly less likely than the others. Even after signal-jamming noise, you
 * can look at the letter distributions in each column and choose the least
 * common letter to reconstruct the original message.
 * 
 * In the above example, the least common character in the first column is a; in
 * the second, d, and so on. Repeating this process for the remaining characters
 * produces the original message, advent.
 * 
 * Given the recording in your puzzle input and this new decoding methodology,
 * what is the original message that Santa is trying to send?
 * 
 * @author Chris Rooney
 *
 */
public class Day6PartB {

	public static void main(String[] args) {

		Scanner scn = null;

		try {
			scn = new Scanner(new File(args[0]));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		List<Map<Character, Integer>> charCount = new ArrayList<Map<Character, Integer>>();

		boolean first = true;
		while (scn.hasNextLine()) {
			String line = scn.nextLine().trim();

			for (int i = 0; i < line.length(); ++i) {
				if (first) {
					charCount.add(new HashMap<Character, Integer>());
				}

				if (charCount.get(i).containsKey(line.charAt(i))) {
					charCount.get(i).put(line.charAt(i), charCount.get(i).get(line.charAt(i)) + 1);
				} else {
					charCount.get(i).put(line.charAt(i), 1);
				}

			}

			first = false;
		}

		int[] minVal = new int[charCount.size()];
		;
		char[] minChar = new char[charCount.size()];

		for (int i = 0; i < charCount.size(); ++i) {
			minVal[i] = Integer.MAX_VALUE;
			for (Map.Entry<Character, Integer> entry : charCount.get(i).entrySet()) {
				if (minVal[i] > entry.getValue()) {
					minVal[i] = entry.getValue();
					minChar[i] = entry.getKey();
				}
			}
		}

		for (char c : minChar) {
			System.out.print(c);
		}
		System.out.println("");
	}
}