package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * One timer for renaming enovia files It reads from an txt with the format OldFileName newFileName
 * 
 * And from a folder where all the files are, it crawls into each folder from that folder and finds where they are. output is from console, in the
 * format of, new file name \t filePath
 * 
 * @author Karlo Mendoza
 *
 */
public class RandomStuff {

	public static File fileWithNames = new File("C:\\Users\\Karlo Mendoza\\Downloads\\validateEnovia.txt");

	public static void main(String... strings) throws IOException {

		try (BufferedReader br = new BufferedReader(new FileReader(fileWithNames))) {
			String line;
			while ((line = br.readLine()) != null) {

				File f = new File(line);
				if (!f.exists()) {
					System.out.println("ERROR: File: " + line);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
