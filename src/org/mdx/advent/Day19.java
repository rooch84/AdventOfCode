package org.mdx.advent;

public class Day19 {

	public static void main(String[] args) {
		int s = 3017957;
		int[] e = new int[s];
		for (int i = 0; i < s; ++i) {
			e[i] = 1;
		}
		boolean oneE = false;
		
		int e1 = 0;
		int e2 = 0;
		while (!oneE) {
			
			
			e2 = (e1 + 1) % s;
			while (e[e2] == 0 && e2 != e1){
				e2 = (e2 + 1) % s;
				//System.out.println("e2: " + e2 + ", e1: " + e1);
			}
		//	System.out.println("End of first loop");
			
			if (e2 == e1) {
				oneE = true;
				break;
			}
			
			e[e1] += e[e2];
			e[e2] = 0;
			
			
			int eTmp = e1;
			e1 = (e1 + 1) % s;
			while (e[e1] == 0 && e1 != eTmp) {
				e1 = (e1 + 1) % s;
			//	System.out.println("Stuck in loop 2");
			}
			//System.out.println("Tmp is " + eTmp);
				
//			for (int i = 0; i < s; ++i) {
//				System.out.println(i + " " + e[i]);
//			}
		}
		
		for (int i = 0; i < s; ++i) {
			if (e[i] != 0) {
				System.out.println(i + 1);				
			}
		}
	}

}
