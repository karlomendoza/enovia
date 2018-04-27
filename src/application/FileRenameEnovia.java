package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * One timer for renaming enovia files It reads from an txt with the format OldFileName newFileName
 * 
 * And from a folder where all the files are, it crawls into each folder from that folder and finds where they are. output is from console, in the
 * format of, new file name \t filePath
 * 
 * @author Karlo Mendoza
 *
 */
public class FileRenameEnovia {

	public static File initialFolder = new File("C:\\Users\\Karlo Mendoza\\Desktop\\enovia_script\\files in here");
	public static File fileWithNames = new File("C:\\\\Users\\\\Karlo Mendoza\\\\Desktop\\\\enovia_script\\\\new 2.txt");

	public static Map<String, String> oldNameAndPath = new HashMap<String, String>();

	public static void main(String... strings) throws IOException {

		// read all structure and files from given folder
		listFilesForFolder(initialFolder);
		// end of read

		String previousDocumentName = "";
		try (BufferedReader br = new BufferedReader(new FileReader(fileWithNames))) {
			String line;
			while ((line = br.readLine()) != null) {

				String[] split = line.split(" ");

				String oldFileName = split[0];
				String newFileName = "";
				for (int i = 1; i < split.length; i++) {
					newFileName += " " + split[i];
				}

				if (previousDocumentName.equals(oldFileName)) {
					System.out.println(newFileName.trim() + "\t" + oldNameAndPath.get(oldFileName).trim());
					continue;
				} else {

					previousDocumentName = oldFileName;

					String oldFilePath = oldNameAndPath.get(oldFileName);
					if (oldFilePath == null) {
						System.out.println("Error file does not exists" + "\t" + oldFileName.trim());
					} else {
						System.out.println(newFileName.trim() + "\t" + oldNameAndPath.get(oldFileName).trim());
						Files.move(Paths.get(oldFilePath + "\\" + oldFileName), Paths.get(oldFilePath + "\\" + newFileName));
					}

				}

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void listFilesForFolder(final File folder) throws IOException {
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry);
			} else {
				oldNameAndPath.put(fileEntry.getName(), fileEntry.getParent());
				// System.out.println(fileEntry.getPath().substring((int) initialFolder.getPath().length(), (int) fileEntry.getPath().length()));
			}
		}
	}

}
