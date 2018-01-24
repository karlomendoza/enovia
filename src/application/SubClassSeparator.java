package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.agile.api.APIException;

public class SubClassSeparator {

	public static void processData(FormData formData) throws InvalidFormatException, IOException, APIException {

		try (Workbook wb = getWorkBook(formData.getMetaDataFile())) {
			Sheet readSheet = wb.getSheetAt(0);
			Row row;
			Row headerRow;
			Cell cell;

			// Load HeaderRow
			headerRow = readSheet.getRow(0);

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

			// Workbook writeBook = getWorkBook();

			int subClassColumnNumber = -1;
			String lastSubClassprocessed = "";
			Workbook writeBook = null;
			Sheet writeSheet = null;
			File f = null;

			for (int r = 0; r < rows; r++) {
				row = readSheet.getRow(r);
				if (row != null) {
					// if it's not the header
					if (r > 0) {
						String subClass = returnCellValueAsString(row.getCell((int) subClassColumnNumber));
						if (subClass.equals(""))
							subClass = "NoSubClass";

						subClass = cleanInput(subClass);

						if (!lastSubClassprocessed.equals(subClass)) {
							if (!lastSubClassprocessed.equalsIgnoreCase(""))
								saveExcels(writeBook, f);

							lastSubClassprocessed = subClass;
							f = new File(formData.getMetaDataFile().getParentFile() + "\\" + subClass + ".xls");

							if (f.exists()) {
								writeBook = getWorkBook(f);
								writeBook.getSheet("data");
							} else {
								writeBook = getWorkBook(null);
								writeSheet = writeBook.createSheet("data");
							}
							Row createRow = writeSheet.createRow((int) 0);
							setCellsValuesToRow(createRow, headerRow, cols);
						}

						Row createRow2 = writeSheet.createRow((int) writeSheet.getPhysicalNumberOfRows());

						setCellsValuesToRow(createRow2, row, cols);

					} else if (r == 0) {
						// get the column number of the subClass
						for (int c = 0; c < cols; c++) {
							cell = row.getCell((int) c);
							if (cell != null) {
								String valueString = returnCellValueAsString(cell);
								// Set the number of the column
								if (valueString.equals(formData.getSubClassColumn())) {
									subClassColumnNumber = c;
									break;
								}
							}
						}
					}
				}
			}
			saveExcels(writeBook, f);
		}

	}

	private static void saveExcels(Workbook writeBook, File f) throws FileNotFoundException, IOException {

		try (FileOutputStream outputStream = new FileOutputStream(f.getAbsolutePath())) {
			writeBook.write(outputStream);
			writeBook.close();
		}
	}

	/**
	 * Removes invalid characters from string, since we want to use that as a name
	 * for files in windows
	 * 
	 * @param input
	 * @return
	 */
	private static String cleanInput(String input) {
		input = input.replace("/", " ");
		input = input.replace("\\", " ");
		input = input.replace(":", " ");
		input = input.replace("*", " ");
		input = input.replace("?", " ");
		input = input.replace("\"", " ");
		input = input.replace("<", " ");
		input = input.replace(">", " ");
		input = input.replace("|", " ");
		return input;
	}

	/**
	 * Gets all the cells from dataRow and copys them in writeToRow, basically it
	 * copies the whole row
	 * 
	 * @param writeToRow
	 * @param dataRow
	 * @param colsNumber
	 *            number of columns to copy
	 */
	private static void setCellsValuesToRow(Row writeToRow, Row dataRow, int colsNumber) {
		for (int c = 0; c < colsNumber; c++) {
			Cell cell = dataRow.getCell((int) c);
			if (cell != null) {
				Cell createCell = writeToRow.createCell(c);
				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_NUMERIC:
					createCell.setCellValue(cell.getNumericCellValue());
					break;
				case Cell.CELL_TYPE_STRING:
					createCell.setCellValue(cell.getStringCellValue());
				}
			}
		}
	}

	/**
	 * Returns a workbook of the same type as the file that gets passed as a
	 * parameter, works for both xlsx and xls file types
	 * 
	 * @param workBookName
	 * @return
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	private static Workbook getWorkBook(File workBookName) throws IOException, InvalidFormatException {
		if (workBookName == null)
			return new HSSFWorkbook();

		String extension = FilenameUtils.getExtension(workBookName.getName());

		if (extension.equalsIgnoreCase("xlsx")) {
			return new XSSFWorkbook(workBookName);
		} else if (extension.equalsIgnoreCase("xls")) {
			InputStream fileToRead = new FileInputStream(workBookName.getAbsolutePath());
			return new HSSFWorkbook(fileToRead);
		}
		return null;
	}

	/**
	 * reads the value from a cell and returns it's value as a String
	 * 
	 * @param cell
	 * @return
	 */
	private static String returnCellValueAsString(Cell cell) {
		if (cell != null) {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC:
				return String.valueOf(cell.getNumericCellValue());
			case Cell.CELL_TYPE_STRING:
				return cell.getStringCellValue();
			}
		}
		return "";
	}
}
