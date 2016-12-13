package org.mdx.advent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day11 {

	// static String[] layout = { "2HG", "1HM", "3LG", "1LM" };
	// static String[] layout = { "1SG", "1SM", "1PG", "1PM", "2TG", "2RG",
	// "2RM", "2CG", "2CM", "3TM", "1EG", "1EM",
	// "1DG", "1DM" };
	// static String start = "1E2HG1HM3LG1LM";
	// static String start = "1E1SG1SM1PG1PM2TG2RG2RM2CG2CM3TM";
	 static String start = "1E1SG1SM1PG1PM2TG2RG2RM2CG2CM3TM1EG1EM1DG1DM";

	public static void main(String[] args) {
		Map<String, State> tree = new HashMap<String, State>();
		State startState = new State(start);
		tree.put(startState.toString(), startState);
		System.out.println(startState.printFloorPlan());
		traverse(startState, tree);
		System.out.println("Finished traversing, there are " + tree.size() + " possible states. Now finding shortest path");
		Set<String> q = new HashSet<String>(tree.keySet());
		List<String> dist = new ArrayList<String>();
		
		for (State s : tree.values()) {
			s.l = Integer.MAX_VALUE;
			s.p = "";
		}

		tree.get(start).l = 0;
		dist.add(start);

		while (q.size() > 0) {
			String n = dist.remove(0);
			q.remove(n);
			State state = tree.get(n);

			int a = state.l + 1;
			for (String s : state.children) {
				if (a < tree.get(s).l) {
					tree.get(s).l = a;
					tree.get(s).p = n;
					int i = 0;
					while (i < dist.size() && tree.get(dist.get(i)).l < a) {
						i++;
					}
					dist.add(i, s);
				}
			}
		}

		for (State s : tree.values()) {
			if (s.allOnFouthFloor()) {
				System.out.println("Completed in " + s.l + " moves");
			}
		}

	}

	public static void traverse(State s, Map<String, State> tree) {
		List<Item> it = s.getItemsOnFloor();
		for (int i = 0; i < it.size(); ++i) {
			if (s.e < 4) {
				s.e++;
				it.get(i).floor++;
				check(s, tree);
				for (int j = i + 1; j < it.size(); ++j) {
					it.get(j).floor++;
					check(s, tree);
					it.get(j).floor--;
				}
				it.get(i).floor--;
				s.e--;
			}
			if (s.e > 1) {
				s.e--;
				it.get(i).floor--;
				check(s, tree);
				for (int j = i + 1; j < it.size(); ++j) {
					it.get(j).floor--;
					check(s, tree);
					it.get(j).floor++;
				}
				it.get(i).floor++;
				s.e++;
			}

		}
	}

	private static void check(State s, Map<String, State> tree) {
		if (s.isValid()) {
			State newS = new State(s.toString());
			if (!tree.containsKey(newS.toString())) {
				tree.put(newS.toString(), newS);
				if (!newS.allOnFouthFloor()) {
					traverse(newS, tree);
				}
			}
			s.children.add(s.toString());
		}
	}

	// Scanner s = new Scanner(System.in);
	// while (!allOnFouthFloor(items)) {
	// System.out.println(printFloorPlan(items, e));
	// String nextMove = s.nextLine();
	// char d = nextMove.charAt(0);
	// int newE = 0;
	// boolean isValid = true;
	// if (d == 'u' && e < 4) {
	// newE = e + 1;
	// } else if (d == 'd' && e > 1) {
	// newE = e - 1;
	// } else {
	// isValid = false;
	// System.out.println("Not a valid floor");
	// }
	// int i = -1;
	// if (nextMove.charAt(1) >= 97) {
	// i = nextMove.charAt(1) - 87;
	// } else {
	// i = Integer.parseInt(nextMove.substring(1, 2));
	// }
	// int j = -1;
	// if (items[i].floor != e) {
	// isValid = false;
	// System.out.println("First item is not on this floor");
	// }
	// items[i].changeFloor(newE);
	//
	// if (nextMove.length() == 3) {
	// if (nextMove.charAt(2) >= 97) {
	// j = nextMove.charAt(2) - 87;
	// } else {
	// j = Integer.parseInt(nextMove.substring(2, 3));
	// }
	// if (items[j].floor != e) {
	// isValid = false;
	// System.out.println("Second item is not on this floor");
	// }
	// items[j].changeFloor(newE);
	// }
	//
	// if (i == j) {
	// isValid = false;
	// System.out.println("Items are the same");
	// }
	//
	// if (!validMove(items)) {
	// isValid = false;
	// System.out.println("Conflict on next level");
	// }
	//
	// if (!isValid) {
	// items[i].resetFloor();
	// if (j != -1) {
	// items[j].resetFloor();
	// }
	//
	// } else {
	// n++;
	// e = newE;
	// moves.add(nextMove);
	// }
	//
	// System.out.println("Number of moves is " + n);
	// }
	// s.close();
	// }

	private static class Item {
		int floor;
		String label;
		char type;
		char mat;

		public Item(int f, String t) {
			floor = f;
			type = t.charAt(1);
			mat = t.charAt(0);
			label = t;
		}
	}

	private static class State {
		List<Item> items = new ArrayList<Item>();
		List<String> children = new ArrayList<String>();
		int e;
		int l;
		String p;

		public String toString() {
			String output = e + "E";
			for (Item i : items) {
				output += i.floor + ("" + i.mat) + ("" + i.type);
			}
			return output;
		}

		public List<Item> getItemsOnFloor() {
			List<Item> i = new ArrayList<Item>();
			for (Item it : items) {
				if (it.floor == e) {
					i.add(it);
				}
			}
			return i;
		}

		public void fromString(String s) {
			items.clear();
			e = Integer.parseInt(s.substring(0, 1));
			for (int i = 2; i <= s.length() - 2; i += 3) {
				items.add(new Item(Integer.parseInt(s.substring(i, i + 1)), s.substring(i + 1, i + 3)));
			}
		}

		public State(String s) {
			fromString(s);
		}

		public String printFloorPlan() {
			String output = "     ";
			for (int j = 0; j < items.size(); ++j) {
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

		boolean isValid() {

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

		public boolean allOnFouthFloor() {
			for (Item i : items) {
				if (i.floor != 4) {
					return false;
				}
			}
			return true;
		}

	}
}
