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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * One timer for renaming enovia files It reads from an txt with the format OldFileName newFileName
 * 
 * And from a folder where all the files are, it crawls into each folder from that folder and finds where they are. output is from console, in the
 * format of, new file name \t filePath
 * 
 * @author Karlo Mendoza
 *
 */
public class FileRenameEnoviaV2 {

	public static File fileWithNames = new File("C:\\Users\\Karlo Mendoza\\Downloads\\new 2.txt");

	public static List<String> a = Arrays.asList("C:\\Users\\Karlo Mendoza\\Downloads\\Chennai");

	// public static List<String> a = Arrays.asList("Y:\\RDM 0123 Enovia - Batch 2\\Chennai", "Y:\\RDM 0123 Enovia - Batch 2\\CostaRica",
	// "Y:\\RDM 0123 Enovia - Batch 2\\LakeForest\\Folder1", "Y:\\RDM 0123 Enovia - Batch 2\\LakeForest\\Folder2",
	// "Y:\\RDM 0123 Enovia - Batch 2\\LakeForest\\Folder3", "Y:\\RDM 0123 Enovia - Batch 2\\LakeForest\\Folder4",
	// "Y:\\RDM 0123 Enovia - Batch 2\\LakeForest\\Folder5", "Y:\\RDM 0123 Enovia - Batch 2\\LakeForest\\Folder6",
	// "Y:\\RDM 0123 Enovia - Batch 2\\MFCS-Hospira", "Y:\\RDM 0123 Enovia - Batch 2\\MFCS-Hospira1", "Y:\\RDM 0123 Enovia - Batch 2\\PRD_METADATA",
	// "Y:\\RDM 0123 Enovia - Batch 2\\San Diego", "Y:\\RDM 0123 Enovia - Batch 2\\San Jose");

	public static Set<String> filesRepeated = new HashSet<>();
	private static final String BREAK_LINE = "\n";

	public static void main(String... strings) throws IOException {

		long i = 0;
		try (BufferedWriter indexFile = new BufferedWriter(new FileWriter("C:\\Users\\Karlo Mendoza\\Downloads\\enoviaRenameOutput.txt"))) {

			try (BufferedReader br = new BufferedReader(new FileReader(fileWithNames))) {
				String line;
				while ((line = br.readLine()) != null) {
					i++;

					line = line.replace("\"", "");
					String[] split = line.split("\\|\\|\\|");

					String fullOldFileName = split[4];

					String oldFileName = fullOldFileName.split("/")[2];

					String newFileName = split[3];

					String id = split[0];

					oldFileName = oldFileName.trim();
					newFileName = newFileName.trim();

					if (oldFileName == null || oldFileName.equals("") || oldFileName.equals("#NA")) {
						System.out.println("Error old file name is bad: " + newFileName + " old file: " + oldFileName);
					}

					Boolean exists = false;
					try {
						for (String folder : a) {
							File f = new File(folder + "\\" + oldFileName);
							if (f.exists()) {
								String newFolderPath = folder + "\\" + oldFileName.substring(0, oldFileName.length() - 4);
								File newFolder = new File(newFolderPath);
								if (!newFolder.exists()) {
									newFolder.mkdirs();
								}

								Files.move(Paths.get(folder + "\\" + oldFileName), Paths.get(newFolderPath + "\\" + newFileName));

								exists = true;
								System.out.println(id + "	" + newFileName + "	" + fullOldFileName + "	" + folder + "\\" + oldFileName + "	" + newFolderPath
										+ "\\" + newFileName);

								indexFile.write(id + "	" + newFileName + "	" + fullOldFileName + "	" + folder + "\\" + oldFileName + "	" + newFolderPath + "\\"
										+ newFileName + BREAK_LINE);

								if (i % 100 == 0)
									indexFile.flush();
								break;
							}
						}
						if (!exists) {
							System.out.println("Error file does not exists anywhere: " + newFileName + "	" + oldFileName);
						}
					} catch (Exception ex) {
						System.out.println("Error on file: " + newFileName.trim() + " id: " + id + ex.getMessage());
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
