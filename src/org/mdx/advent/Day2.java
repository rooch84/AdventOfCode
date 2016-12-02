package org.mdx.advent;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Day2 {

	public static void main(String[] args) {
		
		int[][] keypad = { {1,4,7}, {2,5,8}, {3,6,9}};
		
		int w = 3;
		int h = 3;
		
		Scanner scn = null;

		try {
			scn = new Scanner(new File(args[0]));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		int x = 1;
		int y = 1;
		
		while (scn.hasNextLine()) {

			String nextMove = scn.nextLine();
			
			for (int i = 0; i < nextMove.length(); ++i) {
				char dir = nextMove.charAt(i);
			
				switch (dir) {
				case 'U':
					y  = y == 0 ? y : y - 1;
					break;
				case 'R':
					x = x == w-1 ? x : x + 1;
					break;
				case 'L':
					x = x == 0 ? x : x - 1;
					break;
				case 'D':
					y = y == h-1 ? y : y + 1;
					break;
				}
			}
			System.out.print(keypad[x][y]);
		}
		System.out.println("");

	}

}