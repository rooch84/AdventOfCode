package org.mdx.advent;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

import processing.core.PApplet;
import processing.core.PFont;

/**
 * To compile from root: javac -cp ./lib/core.jar src/org/mdx/advent/Day5PartB.java -d .
 * To run from root: java -cp .:./lib/core.jar org/mdx/advent/Day5PartB <input>
 *  
 * 
 * --- Part Two ---
 * 
 * As the door slides open, you are presented with a second door that uses a
 * slightly more inspired security mechanism. Clearly unimpressed by the last
 * version (in what movie is the password decrypted in order?!), the Easter
 * Bunny engineers have worked out a better solution.
 * 
 * Instead of simply filling in the password from left to right, the hash now
 * also indicates the position within the password to fill. You still look for
 * hashes that begin with five zeroes; however, now, the sixth character
 * represents the position (0-7), and the seventh character is the character to
 * put in that position.
 * 
 * A hash result of 000001f means that f is the second character in the
 * password. Use only the first result for each position, and ignore invalid
 * positions.
 * 
 * For example, if the Door ID is abc:
 * 
 * The first interesting hash is from abc3231929, which produces 0000015...; so,
 * 5 goes in position 1: _5______. In the previous method, 5017308 produced an
 * interesting hash; however, it is ignored, because it specifies an invalid
 * position (8). The second interesting hash is at index 5357525, which produces
 * 000004e...; so, e goes in position 4: _5__e___. You almost choke on your
 * popcorn as the final character falls into place, producing the password
 * 05ace8e3.
 * 
 * Given the actual Door ID and this new method, what is the password? Be extra
 * proud of your solution if it uses a cinematic "decrypting" animation.
 * 
 * @author Chris Rooney
 *
 */
public class Day5PartB extends PApplet {
	int length = 8;
	int margin = 20;
	int width = 400;
	int height = 100;
	char[] password = new char[length];
	PFont f;
	String input;
	
	public static void main(String[] args) {
		String[] mainSketch = concat(new String[] { "org.mdx.advent.Day5PartB" }, args);
		PApplet.main(mainSketch);
	}

	public void settings() {
		size(width, height);
	}

	public void setup() {
		input = args[0];
		thread("run");
		f = createFont("CourierNew.ttf", 48);
		textFont(f);
		textAlign(LEFT, CENTER);
	}

	public void draw() {
		background(0);
		fill(0, 255, 0);
		for (int i = 0; i < password.length; ++i) {
			float x = margin + (width - 2 * margin) / length * i;
			float y = 0.5f * height;
			if (password[i] != 0) {
				text(password[i] > 64 && password[i] < 91 ? (char) (password[i] + 32) : password[i], x, y);
			} else {
				text((char) random(48, 122), x, y);
			}
		}
	}

	public void run() {
		String start = "00000";
		int charsFound = 0;
		int j = -1;

		while (charsFound < 8) {

			String hash = "";
			while (!hash.startsWith(start)) {
				j++;
				try {
					byte[] byteHash = MessageDigest.getInstance("MD5").digest((input + j).getBytes());
					hash = DatatypeConverter.printHexBinary(byteHash);

				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
			}

			int index = Character.getNumericValue(hash.charAt(5));
			if (hash.charAt(5) >= 48 && hash.charAt(5) <= 55 && password[index] == 0) {
				password[index] = hash.charAt(6);
				charsFound++;
			}
		}
	}

}
