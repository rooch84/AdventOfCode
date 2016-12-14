package org.mdx.advent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

/**
 * --- Day 11: Radioisotope Thermoelectric Generators ---
 * 
 * You come upon a column of four floors that have been entirely sealed off from
 * the rest of the building except for a small dedicated lobby. There are some
 * radiation warnings and a big sign which reads "Radioisotope Testing Facility"
 * .
 * 
 * According to the project status board, this facility is currently being used
 * to experiment with Radioisotope Thermoelectric Generators (RTGs, or simply
 * "generators") that are designed to be paired with specially-constructed
 * microchips. Basically, an RTG is a highly radioactive rock that generates
 * electricity through heat.
 * 
 * The experimental RTGs have poor radiation containment, so they're dangerously
 * radioactive. The chips are prototypes and don't have normal radiation
 * shielding, but they do have the ability to generate an electromagnetic
 * radiation shield when powered. Unfortunately, they can only be powered by
 * their corresponding RTG. An RTG powering a microchip is still dangerous to
 * other microchips.
 * 
 * In other words, if a chip is ever left in the same area as another RTG, and
 * it's not connected to its own RTG, the chip will be fried. Therefore, it is
 * assumed that you will follow procedure and keep chips connected to their
 * corresponding RTG when they're in the same room, and away from other RTGs
 * otherwise.
 * 
 * These microchips sound very interesting and useful to your current
 * activities, and you'd like to try to retrieve them. The fourth floor of the
 * facility has an assembling machine which can make a self-contained, shielded
 * computer for you to take with you - that is, if you can bring it all of the
 * RTGs and microchips.
 * 
 * Within the radiation-shielded part of the facility (in which it's safe to
 * have these pre-assembly RTGs), there is an elevator that can move between the
 * four floors. Its capacity rating means it can carry at most yourself and two
 * RTGs or microchips in any combination. (They're rigged to some heavy
 * diagnostic equipment - the assembling machine will detach it for you.) As a
 * security measure, the elevator will only function if it contains at least one
 * RTG or microchip. The elevator always stops on each floor to recharge, and
 * this takes long enough that the items within it and the items on that floor
 * can irradiate each other. (You can prevent this if a Microchip and its
 * Generator end up on the same floor in this way, as they can be connected
 * while the elevator is recharging.)
 * 
 * You make some notes of the locations of each component of interest (your
 * puzzle input). Before you don a hazmat suit and start moving things around,
 * you'd like to have an idea of what you need to do.
 * 
 * When you enter the containment area, you and the elevator will start on the
 * first floor.
 * 
 * For example, suppose the isolated area has the following arrangement:
 * 
 * The first floor contains a hydrogen-compatible microchip and a
 * lithium-compatible microchip. The second floor contains a hydrogen generator.
 * The third floor contains a lithium generator. The fourth floor contains
 * nothing relevant. As a diagram (F# for a Floor number, E for Elevator, H for
 * Hydrogen, L for Lithium, M for Microchip, and G for Generator), the initial
 * state looks like this:
 * 
 * F4 . . . . . F3 . . . LG . F2 . HG . . . F1 E . HM . LM Then, to get
 * everything up to the assembling machine on the fourth floor, the following
 * steps could be taken:
 * 
 * Bring the Hydrogen-compatible Microchip to the second floor, which is safe
 * because it can get power from the Hydrogen Generator:
 * 
 * F4 . . . . . F3 . . . LG . F2 E HG HM . . F1 . . . . LM Bring both
 * Hydrogen-related items to the third floor, which is safe because the
 * Hydrogen-compatible microchip is getting power from its generator:
 * 
 * F4 . . . . . F3 E HG HM LG . F2 . . . . . F1 . . . . LM Leave the Hydrogen
 * Generator on floor three, but bring the Hydrogen-compatible Microchip back
 * down with you so you can still use the elevator:
 * 
 * F4 . . . . . F3 . HG . LG . F2 E . HM . . F1 . . . . LM At the first floor,
 * grab the Lithium-compatible Microchip, which is safe because Microchips don't
 * affect each other:
 * 
 * F4 . . . . . F3 . HG . LG . F2 . . . . . F1 E . HM . LM Bring both Microchips
 * up one floor, where there is nothing to fry them:
 * 
 * F4 . . . . . F3 . HG . LG . F2 E . HM . LM F1 . . . . . Bring both Microchips
 * up again to floor three, where they can be temporarily connected to their
 * corresponding generators while the elevator recharges, preventing either of
 * them from being fried:
 * 
 * F4 . . . . . F3 E HG HM LG LM F2 . . . . . F1 . . . . . Bring both Microchips
 * to the fourth floor:
 * 
 * F4 E . HM . LM F3 . HG . LG . F2 . . . . . F1 . . . . . Leave the
 * Lithium-compatible microchip on the fourth floor, but bring the
 * Hydrogen-compatible one so you can still use the elevator; this is safe
 * because although the Lithium Generator is on the destination floor, you can
 * connect Hydrogen-compatible microchip to the Hydrogen Generator there:
 * 
 * F4 . . . . LM F3 E HG HM LG . F2 . . . . . F1 . . . . . Bring both Generators
 * up to the fourth floor, which is safe because you can connect the
 * Lithium-compatible Microchip to the Lithium Generator upon arrival:
 * 
 * F4 E HG . LG LM F3 . . HM . . F2 . . . . . F1 . . . . . Bring the Lithium
 * Microchip with you to the third floor so you can use the elevator:
 * 
 * F4 . HG . LG . F3 E . HM . LM F2 . . . . . F1 . . . . . Bring both Microchips
 * to the fourth floor:
 * 
 * F4 E HG HM LG LM F3 . . . . . F2 . . . . . F1 . . . . . In this arrangement,
 * it takes 11 steps to collect all of the objects at the fourth floor for
 * assembly. (Each elevator stop counts as one step, even if nothing is added to
 * or removed from it.)
 * 
 * In your situation, what is the minimum number of steps required to bring all
 * of the objects to the fourth floor?
 * 
 * --- Part Two ---
 * 
 * You step into the cleanroom separating the lobby from the isolated area and
 * put on the hazmat suit.
 * 
 * Upon entering the isolated containment area, however, you notice some extra
 * parts on the first floor that weren't listed on the record outside:
 * 
 * An elerium generator. An elerium-compatible microchip. A dilithium generator.
 * A dilithium-compatible microchip. These work just like the other generators
 * and microchips. You'll have to get them up to assembly as well.
 * 
 * What is the minimum number of steps required to bring all of the objects,
 * including these four new ones, to the fourth floor?
 * 
 * @author Chris Rooney
 *
 */
public class Day11 {

	public static void main(String[] args) {

		Scanner scn = null;
		try {
			scn = new Scanner(new File(args[0]));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String start = scn.nextLine();

		if (args.length == 2 && args[1].equals("play")) {
			playGame(start);
		} else {
			calculateSolution(start);
		}
	}

	private static void calculateSolution(String start) {

		Map<String, Child> tree = new HashMap<String, Child>();
		State startState = new State(start);
		tree.put(startState.toString(), new Child(0));
		System.out.println(startState.printFloorPlan());

		Queue<String> stateQ = new LinkedList<String>();

		stateQ.add(start);

		boolean isEndState = false;
		while (!stateQ.isEmpty() && !isEndState) {

			String cS = stateQ.poll();
			State s = new State(cS);
			List<Item> it = s.getItemsOnFloor();
			for (int i = 0; i < it.size(); ++i) {
				if (s.e < 4) {
					s.e++;
					it.get(i).floor++;
					isEndState = check(cS, s, tree, stateQ);

					for (int j = i + 1; j < it.size(); ++j) {
						it.get(j).floor++;
						isEndState = isEndState ? isEndState : check(cS, s, tree, stateQ);
						it.get(j).floor--;
					}
					it.get(i).floor--;
					s.e--;
				}
				if (s.e > 1) {
					s.e--;
					it.get(i).floor--;
					isEndState = isEndState ? isEndState : check(cS, s, tree, stateQ);
					for (int j = i + 1; j < it.size(); ++j) {
						it.get(j).floor--;
						isEndState = isEndState ? isEndState : check(cS, s, tree, stateQ);
						it.get(j).floor++;
					}
					it.get(i).floor++;
					s.e++;
				}

			}
		}

		System.out.println(
				"Finished traversing, there are " + tree.size() + " possible states. Now looking for end state.");

		String endState = "";
		for (Map.Entry<String, Child> s : tree.entrySet()) {
			if (new State(s.getKey()).allOnFouthFloor()) {
				endState = s.getKey();
				System.out.println("Shortest path is " + s.getValue().l + " moves.");
			}
		}

		System.out.println("Press 'p' to play the solution.");
		try {
			if ((char) System.in.read() == 'p') {
				playSolution(endState, tree);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void playSolution(String endState, Map<String, Child> tree) {
		Stack<String> stack = new Stack<String>();
		stack.push(endState);
		String p = tree.get(endState).p;
		while (!p.equals("")) {
			stack.push(p);
			p = tree.get(p).p;
		}

		while (!stack.isEmpty()) {
			System.out.print("\033[H\033[2J");
			System.out.flush();
			System.out.println(new State(stack.pop()).printFloorPlan());

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private static boolean check(String prevS, State s, Map<String, Child> tree, Queue<String> q) {
		if (s.isValid()) {
			State newS = new State(s.toString());
			if (!tree.containsKey(newS.toString())) {
				tree.put(newS.toString(), new Child(tree.get(prevS.toString()).l + 1, prevS));

				q.add(newS.toString());
				if (newS.allOnFouthFloor()) {
					return true;
				}
			}
		}
		return false;
	}

	private static void playGame(String start) {
		int n = 0;
		State s = new State(start);
		Scanner scn = new Scanner(System.in);
		String error = "";
		String end = String.valueOf(s.items.size() - 1);
		if (s.items.size() > 10) {
			end = "" + (char) (s.items.size() + 86);
		}
		while (!s.allOnFouthFloor()) {
			System.out.print("\033[H\033[2J");
			System.out.flush();
			System.out.println(error);
			System.out.println("Number of moves is " + n);
			System.out.println(s.printFloorPlan());

			System.out.print("Next move <u,d><0-" + end + "><0-" + end + ">: ");
			String nextMove = scn.nextLine();
			boolean isValid = true;
			if (nextMove.length() < 2) {
				isValid = false;
				error = "Invalid input";
			}
			int prevE = s.e;
			char d = nextMove.charAt(0);
			if (isValid && d == 'u' && s.e < 4) {
				s.e++;
			} else if (isValid && d == 'd' && s.e > 1) {
				s.e--;
			} else if (isValid) {
				isValid = false;
				error = "Not a valid floor";
			}
			int i = -1;
			if (isValid) {
				if (nextMove.charAt(1) >= 97) {

					i = nextMove.charAt(1) - 87;
				} else {
					i = Integer.parseInt(nextMove.substring(1, 2));
				}
				if (s.items.get(i).floor != prevE) {
					isValid = false;
					error = "First item is not on this floor";
				}
				s.items.get(i).changeFloor(s.e);
			}
			int j = -1;
			if (isValid && nextMove.length() == 3) {
				if (nextMove.charAt(2) >= 97) {
					j = nextMove.charAt(2) - 87;
				} else {
					j = Integer.parseInt(nextMove.substring(2, 3));
				}

				if (i == j) {
					isValid = false;
					error = "Items are the same";
				} else {
					if (s.items.get(j).floor != prevE) {
						isValid = false;
						error = "Second item is not on this floor";
					}
					s.items.get(j).changeFloor(s.e);
				}
			}

			if (isValid && !s.isValid()) {
				isValid = false;
				error = "Conflict on next state";
			}

			if (!isValid) {
				if (i != -1) {
					s.items.get(i).resetFloor();
				}
				if (j != -1) {
					s.items.get(j).resetFloor();
				}
				s.e = prevE;
			} else {
				error = "";
				n++;
			}

		}
		scn.close();
	}

	private static class Item {
		int floor;
		int pFloor;
		String label;
		char type;
		char mat;

		public Item(int f, String t) {
			floor = f;
			type = t.charAt(1);
			mat = t.charAt(0);
			label = t;
		}

		public void resetFloor() {
			floor = pFloor;
		}

		public void changeFloor(int f) {
			pFloor = floor;
			floor = f;
		}

	}

	private static class Child {
		String p = "";
		int l;

		public Child(int l) {
			this.l = l;
		}

		public Child(int l, String p) {
			this(l);
			this.p = p;
		}
	}

	private static class State {
		List<Item> items = new ArrayList<Item>();
		int e;

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
