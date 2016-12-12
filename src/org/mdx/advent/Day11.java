package org.mdx.advent;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day11 {

	// static String[] layout = { "2HG", "1HM", "3LG", "1LM" };
	static String[] layout = { "1SG", "1SM", "1PG", "1PM", "2TG", "2RG", "2RM", "2CG", "2CM", "3TM", "1EG", "1EM",
			"1DG", "1DM" };

	public static void main(String[] args) {
		List<String> moves = new ArrayList<String>();

		Item[] items = new Item[layout.length];
		int n = 0;
		int e = 1;
		for (int i = 0; i < layout.length; i++) {
			items[i] = new Item(Integer.parseInt(layout[i].substring(0, 1)), layout[i].substring(1, 3));
		}

		Scanner s = new Scanner(System.in);
		while (!allOnFouthFloor(items)) {
			System.out.println(printFloorPlan(items, e));
			String nextMove = s.nextLine();
			char d = nextMove.charAt(0);
			int newE = 0;
			boolean isValid = true;
			if (d == 'u' && e < 4) {
				newE = e + 1;
			} else if (d == 'd' && e > 1) {
				newE = e - 1;
			} else {
				isValid = false;
				System.out.println("Not a valid floor");
			}
			int i = -1;
			if (nextMove.charAt(1) >= 97) {
				i = nextMove.charAt(1) - 87;
			} else {
				i = Integer.parseInt(nextMove.substring(1, 2));
			}
			int j = -1;
			if (items[i].floor != e) {
				isValid = false;
				System.out.println("First item is not on this floor");
			}
			items[i].changeFloor(newE);

			if (nextMove.length() == 3) {
				if (nextMove.charAt(2) >= 97) {
					j = nextMove.charAt(2) - 87;
				} else {
					j = Integer.parseInt(nextMove.substring(2, 3));
				}
				if (items[j].floor != e) {
					isValid = false;
					System.out.println("Second item is not on this floor");
				}
				items[j].changeFloor(newE);
			}

			if (i == j) {
				isValid = false;
				System.out.println("Items are the same");
			}

			if (!validMove(items)) {
				isValid = false;
				System.out.println("Conflict on next level");
			}

			if (!isValid) {
				items[i].resetFloor();
				if (j != -1) {
					items[j].resetFloor();
				}

			} else {
				n++;
				e = newE;
				moves.add(nextMove);
			}

			System.out.println("Number of moves is " + n);
		}
		s.close();
	}

	private static boolean validMove(Item[] items) {

		for (int j = 1; j <= 4; ++j) {
			boolean hasBoth = false;
			char first = 0;
			for (Item i : items) {
				if (i.floor == j) {
					if (first == 0) {
						first = i.type;
					} else if (i.type != first) {
						hasBoth = true;
					}
				}
			}

			if (hasBoth) {
				for (Item i : items) {
					boolean hasPartner = false;
					if (i.floor == j && i.type == 'M') {
						for (Item k : items) {
							if (k.floor == j && k.type == 'G' && i.mat == k.mat) {
								hasPartner = true;
								break;
							}
						}
						if (!hasPartner) {
							return false;
						}
					}
				}
			}
		}

		return true;

	}

	private static String printFloorPlan(Item[] items, int e) {
		String output = "     ";
		for (int j = 0; j < items.length; ++j) {
			if (j < 10) {
				output += j + "  ";
			} else {
				output += (char) (j + 87) + "  ";
			}
		}
		output += "\n";
		for (int j = 4; j >= 1; --j) {
			output += "F" + j + " ";
			if (e == j) {
				output += "E ";
			} else {
				output += ". ";
			}
			for (Item i : items) {
				if (i.floor == j) {
					output += i.label;
				} else {
					output += ". ";
				}
				output += " ";
			}
			output += "\n";
		}
		return output;
	}

	private static boolean allOnFouthFloor(Item[] items) {
		for (Item i : items) {
			if (i.floor != 4) {
				return false;
			}
		}
		return true;
	}

	private static class Item {
		int floor;
		String label;
		char type;
		char mat;
		int prevFloor;

		public Item(int f, String t) {
			floor = f;
			type = t.charAt(1);
			mat = t.charAt(0);
			label = t;
		}

		void changeFloor(int i) {
			prevFloor = floor;
			floor = i;
		}

		void resetFloor() {
			floor = prevFloor;
		}
	}
}
