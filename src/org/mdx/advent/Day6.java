package org.mdx.advent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * --- Day 6: Signals and Noise ---
 * 
 * Something is jamming your communications with Santa. Fortunately, your signal
 * is only partially jammed, and protocol in situations like this is to switch
 * to a simple repetition code to get the message through.
 * 
 * In this model, the same message is sent repeatedly. You've recorded the
 * repeating message signal (your puzzle input), but the data seems quite
 * corrupted - almost too badly to recover. Almost.
 * 
 * All you need to do is figure out which character is most frequent for each
 * position. For example, suppose you had recorded the following messages:
 * 
 * eedadn
 * drvtee
 * eandsr
 * raavrd
 * atevrs
 * tsrnev
 * sdttsa
 * rasrtv
 * nssdts
 * ntnada
 * svetve
 * tesnvt
 * vntsnd
 * vrdear
 * dvrsen
 * enarar 
 * 
 * The most common character in the first
 * column is e; in the second, a; in the third, s, and so on. Combining these
 * characters returns the error-corrected message, easter.
 * 
 * Given the recording in your puzzle input, what is the error-corrected version
 * of the message being sent?
 *  
 * @author Chris Rooney
 *
 */
public class Day6 {

	public static void main(String[] args) {

		Scanner scn = null;

		try {
			scn = new Scanner(new File(args[0]));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		List<Map<Character, Integer>> charCount = new ArrayList<Map<Character, Integer>>();
		int[] maxVal = null;
		char[] maxChar = null;
		boolean first = true;
		while (scn.hasNextLine()) {
			String line = scn.nextLine().trim();

			if (first) {
				maxVal = new int[line.length()];
				maxChar = new char[line.length()];
			}
			for (int i = 0; i < line.length(); ++i) {
				if (first) {
					charCount.add(new HashMap<Character, Integer>());
				}

				if (charCount.get(i).containsKey(line.charAt(i))) {
					charCount.get(i).put(line.charAt(i), charCount.get(i).get(line.charAt(i)) + 1);
				} else {
					charCount.get(i).put(line.charAt(i), 1);
				}
				if (maxVal[i] < charCount.get(i).get(line.charAt(i))) {
					maxVal[i] = charCount.get(i).get(line.charAt(i));
					maxChar[i] = line.charAt(i);
				}
			}

			first = false;
		}

		for (char c : maxChar) {
			System.out.print(c);
		}
		System.out.println("");
	}
}