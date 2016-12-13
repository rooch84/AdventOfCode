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
		
		Map<String, Child> tree = new HashMap<String, Child>();
		State startState = new State(start);
		tree.put(startState.toString(), new Child(0));
		System.out.println(startState.printFloorPlan());
		
		Queue<String> stateQ = new LinkedList<String>();
		
		stateQ.add(start);
		
		boolean isEndState = false;
		while(!stateQ.isEmpty() && !isEndState) {
			
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
		
		System.out.println("Finished traversing, there are " + tree.size() + " possible states. Now looking for end state.");

		String endState = "";
		for (Map.Entry<String, Child> s : tree.entrySet()) {
			if (new State(s.getKey()).allOnFouthFloor()) {
				endState = s.getKey();
				System.out.println("Found in.  Shortest path is " + s.getValue().l + " moves.");
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
		while(!p.equals("")) {
			stack.push(p);
			p = tree.get(p).p;
		}
		
		while(!stack.isEmpty()) {
			
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
				tree.put(newS.toString(), new Child(tree.get(prevS.toString()).l+1, prevS));
				
				q.add(newS.toString());
				if (newS.allOnFouthFloor()) {
					return true; 
				}
			}
		}
		return false;
	}

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
