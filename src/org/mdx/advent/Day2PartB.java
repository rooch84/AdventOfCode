package org.mdx.advent;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Day2PartB {

	public static void main(String[] args) {
		
		char[][] keypad = { {'$','$','5','$','$'}, {'$','2','6','A','$'}, 
				{'1','3','7','B','D'}, {'$','4','8','C', '$'}, {'$','$','9','$','$'}};
		
		int width = 5;
		int height = 5;
		Scanner scn = null;

		try {
			scn = new Scanner(new File(args[0]));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		int x = 0;
		int y = 2;
		
		while (scn.hasNextLine()) {

			String nextMove = scn.nextLine();
			
			for (int i = 0; i < nextMove.length(); ++i) {
				char dir = nextMove.charAt(i);
			
				switch (dir) {
				case 'U':
					y  = y == 0 || keypad[x][y-1] == '$' ? y : y - 1;
					break;
				case 'R':
					x = x == width - 1 || keypad[x+1][y] == '$' ? x : x + 1;
					break;
				case 'L':
					x = x == 0 || keypad[x-1][y] == '$' ? x : x - 1;
					break;
				case 'D':
					y = y == height - 1 || keypad[x][y+1] == '$' ? y : y + 1;
					break;
				}
		
			
			}

			System.out.print(keypad[x][y]);
		}
		System.out.println("");

	}

}