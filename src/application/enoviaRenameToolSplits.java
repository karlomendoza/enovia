package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import utils.Utils;

public class enoviaRenameToolSplits {

	private static final String RENAME_FILE_NAME = "renameFile";
	private static final String BREAK_LINE = "\n";

	private static final File file = new File("C:\\Users\\Karlo Mendoza\\Downloads\\__Kalypso_A_EER_AttributesAndAttachedFiles.xlsx");

	private static Set<String> filesToCheck = new HashSet<String>();

	public static void main(String... strings) throws InvalidFormatException, IOException {

		try (Workbook wb = Utils.getWorkBook(file)) {
			Sheet readSheet = wb.getSheetAt(0);
			Row row;
			int rows = readSheet.getPhysicalNumberOfRows(); // No of rows
			int cols = 0; // No of columns
			int tmp = 0;

			// This trick ensures that we get the data properly even if it doesn't start
			// from first few rows
			for (int i = 0; i < 10 || i < rows; i++) {
				row = readSheet.getRow(i);
				if (row != null) {
					tmp = readSheet.getRow(i).getPhysicalNumberOfCells();
					if (tmp > cols)
						cols = tmp;
				}
			}

			int i = 1;
			long j = 0;
			BufferedWriter indexFile = new BufferedWriter(new FileWriter(file.getParentFile().getPath() + "\\" + RENAME_FILE_NAME + i + ".txt"));

			for (int r = 0; r < rows; r++) {
				row = readSheet.getRow(r);
				if (row != null) {
					// if it's not the header
					if (r > 0) {

						String id = Utils.returnCellValueAsString(row.getCell((int) 0));
						String actualFileName = Utils.returnCellValueAsString(row.getCell((int) 6));
						String physicalFileName = Utils.returnCellValueAsString(row.getCell((int) 8));

						if (physicalFileName == null || physicalFileName.isEmpty() || physicalFileName.equals(""))
							continue;

						if (!filesToCheck.add(physicalFileName)) {
							System.out.println("duplicated files: " + physicalFileName);
							continue;
						}

						String physicalFileNameWithOutExtension = physicalFileName.substring(6, physicalFileName.length() - 4);

						j++;
						indexFile.write(id + "_AA_AA_AA_" + physicalFileName + "_AA_AA_AA_" + actualFileName + "_AA_AA_AA_" + physicalFileNameWithOutExtension
								+ BREAK_LINE);
						if (j % 1330000 == 0) {
							indexFile.flush();
							indexFile.close();
							i++;
							indexFile = new BufferedWriter(new FileWriter(file.getParentFile().getPath() + "\\" + RENAME_FILE_NAME + i + ".txt"));
						}

					}
				}
			}

			indexFile.flush();
			indexFile.close();
		}
	}
}
