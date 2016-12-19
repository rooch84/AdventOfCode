package org.mdx.advent;

import java.util.ArrayList;
import java.util.List;

public class Day19PartB {

	public static void main(String[] args) {
		int s = 3017957;
		List<Integer> e = new ArrayList<Integer>();
		
		for (int i = 0; i < s; ++i) {
			e.add(i + 1);
		}
		
		int e1 = 0;
		while (e.size() > 1) {
			int e2 = (e1 + e.size() / 2) % e.size();
			e.remove(e2);
			if (e2 > e1) {
				e1 = (e1 + 1) % e.size();
			} else {
				e1 = e1 % e.size();
			}
		}
		System.out.println(e.get(0));
	}

}
