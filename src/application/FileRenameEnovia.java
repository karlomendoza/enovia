package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

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

	/** need to change this two with correct paths **/
	public static String path_to_rename_file = "C:\\Users\\Karlo Mendoza\\Desktop\\enoviaRename\\test\\";
	public static String path_to_files = "C:\\Users\\Karlo Mendoza\\Downloads\\";

	public static File fileWithNames = new File(path_to_rename_file + "renameFile1.txt");
	public static File log = new File(path_to_rename_file + "logRenameFile1.txt");

	public static List<String> paths = Arrays.asList(path_to_files + "Chennai");

	private static final String BREAK_LINE = "\n";

	public static void main(String... strings) throws IOException {

		long i = 0;
		try (BufferedWriter write = new BufferedWriter(new FileWriter(log))) {

			try (BufferedReader br = new BufferedReader(new FileReader(fileWithNames))) {
				String line;
				while ((line = br.readLine()) != null) {
					i++;
					try {

						line = line.replace("\"", "");
						String[] split = line.split("_AA_AA_AA_");

						String id = split[0];
						String oldFileName = split[1];
						String newFileName = split[2];
						String pathToCreate = split[3];

						oldFileName = oldFileName.trim();
						newFileName = newFileName.trim();
						pathToCreate = pathToCreate.trim();

						if (oldFileName == null || oldFileName.equals("") || oldFileName.equals("#NA")) {
							System.out.println("Error old file name is bad: " + newFileName + " old file: " + oldFileName);
							write.write(id + " " + newFileName + " " + oldFileName + " " + "\\" + newFileName + BREAK_LINE);
							continue;
						}

						String onlyOldFileName = oldFileName.substring(6);
						Boolean exists = false;
						try {
							for (String folder : paths) {
								File f = new File(folder + "\\" + onlyOldFileName);
								if (f.exists()) {
									String newFolderPath = folder + "\\" + pathToCreate;
									File newFolder = new File(newFolderPath);
									if (!newFolder.exists()) {
										newFolder.mkdirs();
									}

									Files.move(Paths.get(folder + "\\" + onlyOldFileName), Paths.get(newFolderPath + "\\" + newFileName));

									exists = true;
									System.out.println(id + "	" + newFileName + "	" + oldFileName + "	" + folder + "\\" + oldFileName + "	" + newFolderPath
											+ "\\" + newFileName);
									write.write(id + "	" + newFileName + "	" + oldFileName + "	" + folder + "\\" + oldFileName + "	" + newFolderPath + "\\"
											+ newFileName + BREAK_LINE);
									if (i % 100 == 0)
										write.flush();
									break;
								}
							}
							if (!exists) {
								write.write("Error file does not exists anywhere: " + id + "	" + newFileName + "	" + oldFileName + BREAK_LINE);
								System.out.println("Error file does not exists anywhere: " + newFileName + "	" + oldFileName);
							}
						} catch (Exception ex) {
							write.write("Error on file: " + newFileName.trim() + " id: " + id + ex.getMessage() + BREAK_LINE);
							System.out.println("Error on file: " + newFileName.trim() + " id: " + id + ex.getMessage());
						}
					} catch (Exception e) {
						write.write("Error on row # i: " + i + e.getMessage() + BREAK_LINE);
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
	}
}
