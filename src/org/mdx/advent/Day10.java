package org.mdx.advent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * --- Day 10: Balance Bots ---
 * 
 * You come upon a factory in which many robots are zooming around handing small
 * microchips to each other.
 * 
 * Upon closer examination, you notice that each bot only proceeds when it has
 * two microchips, and once it does, it gives each one to a different bot or
 * puts it in a marked "output" bin. Sometimes, bots take microchips from
 * "input" bins, too.
 * 
 * Inspecting one of the microchips, it seems like they each contain a single
 * number; the bots must use some logic to decide what to do with each chip. You
 * access the local control computer and download the bots' instructions (your
 * puzzle input).
 * 
 * Some of the instructions specify that a specific-valued microchip should be
 * given to a specific bot; the rest of the instructions indicate what a given
 * bot should do with its lower-value or higher-value chip.
 * 
 * For example, consider the following instructions:
 * 
 * value 5 goes to bot 2 bot 2 gives low to bot 1 and high to bot 0 value 3 goes
 * to bot 1 bot 1 gives low to output 1 and high to bot 0 bot 0 gives low to
 * output 2 and high to output 0 value 2 goes to bot 2 Initially, bot 1 starts
 * with a value-3 chip, and bot 2 starts with a value-2 chip and a value-5 chip.
 * Because bot 2 has two microchips, it gives its lower one (2) to bot 1 and its
 * higher one (5) to bot 0. Then, bot 1 has two microchips; it puts the value-2
 * chip in output 1 and gives the value-3 chip to bot 0. Finally, bot 0 has two
 * microchips; it puts the 3 in output 2 and the 5 in output 0. In the end,
 * output bin 0 contains a value-5 microchip, output bin 1 contains a value-2
 * microchip, and output bin 2 contains a value-3 microchip. In this
 * configuration, bot number 2 is responsible for comparing value-5 microchips
 * with value-2 microchips.
 * 
 * Based on your instructions, what is the number of the bot that is responsible
 * for comparing value-61 microchips with value-17 microchips?
 *  
 * --- Part Two ---
 * 
 * What do you get if you multiply together the values of one chip in each of
 * outputs 0, 1, and 2?
 * 
 * 
 * @author Chris Rooney
 *
 */
public class Day10 {

	static int[] chipSearch = { 17, 61 };

	public static void main(String[] args) {
		Scanner scn = null;

		Map<Integer, Integer> chips = new HashMap<Integer, Integer>();
		Map<Integer, Bot> bots = new HashMap<Integer, Bot>();
		Map<Integer, Integer> outputs = new HashMap<Integer, Integer>();

		try {
			scn = new Scanner(new File(args[0]));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		while (scn.hasNextLine()) {
			String line = scn.nextLine();
			if (line.startsWith("value")) {
				chips.put(Integer.valueOf(line.split(" ")[1]), Integer.valueOf(line.split(" ")[5]));
			} else {
				Bot bot = new Bot();
				bot.id = Integer.valueOf(line.split(" ")[1]);
				bot.low = Integer.valueOf(line.split(" ")[6]);
				bot.high = Integer.valueOf(line.split(" ")[11]);
				if (line.split(" ")[5].equals("output")) {
					bot.lowBot = false;
				}
				if (line.split(" ")[10].equals("output")) {
					bot.highBot = false;
				}
				bots.put(Integer.valueOf(line.split(" ")[1]), bot);
			}
		}

		for (Map.Entry<Integer, Integer> entry : chips.entrySet()) {
			bots.get(entry.getValue()).addChip(entry.getKey());
		}

		int i = -1;
		do {
			i = botHasTwoChips(bots);
			if (i != -1) {
				int c1 = bots.get(i).c.remove(0);
				int c2 = bots.get(i).c.remove(0);
				if (bots.get(i).lowBot) {
					bots.get(bots.get(i).low).addChip(c1);
				} else {
					outputs.put(bots.get(i).low, c1);
				}
				if (bots.get(i).highBot) {
					bots.get(bots.get(i).high).addChip(c2);
				} else {
					outputs.put(bots.get(i).high, c2);
				}
			}
		} while (i != -1);
		System.out.println("Product of o0, o1 and o2 is " + outputs.get(0) * outputs.get(1) * outputs.get(2));
	}

	private static int botHasTwoChips(Map<Integer, Bot> bots) {

		for (Map.Entry<Integer, Bot> b : bots.entrySet()) {
			if (b.getValue().c.size() == 2) {
				return b.getKey();
			}
		}
		return -1;
	}

	private static class Bot {
		List<Integer> c = new ArrayList<Integer>();
		int id = -1;
		int low = -1;
		int high = -1;
		boolean lowBot = true;
		boolean highBot = true;

		void addChip(int i) {
			if (c.size() == 0) {
				c.add(i);
			} else if (c.size() == 1) {
				if (i < c.get(0)) {
					c.add(0, i);
				} else {
					c.add(i);
				}

				if (c.get(0) == chipSearch[0] && c.get(1) == chipSearch[1]) {
					System.out.println("I'm bot " + id + " and I've just made that check");
				}
			} else {
				System.out.println("Error, bot already has two chips");
			}
		}
	}
}
