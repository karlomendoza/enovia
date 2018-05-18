package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
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
public class EnoviaVerifyFileRename {

	public static File fileWithNames = new File("C:\\Users\\Karlo Mendoza\\Desktop\\masterControl\\checkMasterFile1.txt");
	public static File log = new File("C:\\Users\\Karlo Mendoza\\Desktop\\masterControl\\masterLogRenameFile1.txt");

	private static final String BREAK_LINE = "\n";

	public static void main(String... strings) throws IOException {

		try (BufferedWriter write = new BufferedWriter(new FileWriter(log))) {

			try (BufferedReader br = new BufferedReader(new FileReader(fileWithNames))) {
				String line;
				while ((line = br.readLine()) != null) {

					String file_name = line.trim();

					if (file_name == null || file_name.equals("") || file_name.equals("#NA")) {
						System.out.println("Error old file name is bad: " + file_name);
					}
					try {
						File f = null;
						f = new File(file_name);

						if (f.exists()) {
							write.write(file_name + BREAK_LINE);
							System.out.println(file_name);
						} else {
							write.write("Error file does not exists :	" + file_name + BREAK_LINE);
							System.out.println("Error file does not exists :	" + file_name);
						}

					} catch (Exception ex) {
						write.write("Error on file:	" + file_name + BREAK_LINE);
						System.out.println("Error on file:	" + file_name + ex.getMessage());
					}
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
