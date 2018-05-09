package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
public class FileRenameEnovia {

	public static File initialFolder = new File("Y:\\RDM 0123 Enovia - Batch 2");
	public static File fileWithNames = new File("Y:\\RDM 0123 Enovia - Batch 2\\new 2.txt");

	public static List<String> a = Arrays.asList("Y:\\RDM 0123 Enovia - Batch 2\\Chennai", "Y:\\RDM 0123 Enovia - Batch 2\\CostaRica",
			"Y:\\RDM 0123 Enovia - Batch 2\\LakeForest\\Folder1", "Y:\\RDM 0123 Enovia - Batch 2\\LakeForest\\Folder2",
			"Y:\\RDM 0123 Enovia - Batch 2\\LakeForest\\Folder3", "Y:\\RDM 0123 Enovia - Batch 2\\LakeForest\\Folder4",
			"Y:\\RDM 0123 Enovia - Batch 2\\LakeForest\\Folder5", "Y:\\RDM 0123 Enovia - Batch 2\\LakeForest\\Folder6",
			"Y:\\RDM 0123 Enovia - Batch 2\\MFCS-Hospira", "Y:\\RDM 0123 Enovia - Batch 2\\MFCS-Hospira1", "Y:\\RDM 0123 Enovia - Batch 2\\PRD_METADATA",
			"Y:\\RDM 0123 Enovia - Batch 2\\San Diego", "Y:\\RDM 0123 Enovia - Batch 2\\San Jose");

	public static Set<String> filesRepeated = new HashSet<>();

	public static void main(String... strings) throws IOException {

		try (BufferedReader br = new BufferedReader(new FileReader(fileWithNames))) {
			String line;
			while ((line = br.readLine()) != null) {

				String[] split = line.split("	");

				String fullOldFileName = split[0];

				String oldFileName = fullOldFileName.split("/")[2];

				String newFileName = "";
				for (int i = 1; i < split.length; i++) {
					newFileName += " " + split[i];
				}

				oldFileName = oldFileName.trim();
				newFileName = newFileName.trim();

				Boolean exists = false;
				try {
					for (String folder : a) {
						File f = new File(folder + "\\" + oldFileName);
						if (f.exists()) {
							System.out.println(folder + "	" + newFileName + "	" + fullOldFileName);

							// if the same physical file name is in another folder, we can't tell which one is the correct file
							if (exists) {
								System.out.println("MEGA ERROR this file is in multiple folders: " + folder + "	" + newFileName + "	" + oldFileName);
							}

							if (!filesRepeated.add(folder + "\\" + newFileName)) {
								System.out.println("This file would be duplicated in here: " + folder + "\\" + newFileName);
							}
							exists = true;
						}
					}
					if (!exists) {
						System.out.println("Error file does not exists anywhere: " + newFileName + "	" + oldFileName);
					}
				} catch (Exception ex) {
					System.out.println("Error on file: " + newFileName.trim() + " " + ex.getMessage());
				}

				// Files.move(Paths.get(oldFilePath + "\\" + oldFileName), Paths.get(oldFilePath + "\\" + newFileName));

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
