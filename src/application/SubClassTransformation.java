package application;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import utils.Utils;

public class SubClassTransformation {

	public static void main(String... strings) throws InvalidFormatException, IOException {
		File metaDataFiles = new File("C:\\Users\\Karlo Mendoza\\Excel Work\\ICU MEDICAL\\Master Control\\T1\\Tests\\");
		String infoCardTypeColumn = "Infocard Type";
		String infoCardSubTypeColumn = "Infocard SubType";
		String documentNumberColumn = "Document #";

		File transformationFile = null;

		processData(metaDataFiles, transformationFile, infoCardTypeColumn, infoCardSubTypeColumn, documentNumberColumn);
	}

	public static CellStyle cellStyle;
	public static List<Integer> dates = new ArrayList<>();

	static {
		dates.add(11);
		dates.add(12);
		dates.add(13);
	}

	public static Map<String, Map<String, String>> loadListData(File transformationFile) throws IOException, InvalidFormatException {
		Map<String, Map<String, String>> transformationData = new HashMap<>();
		try (Workbook listDataWorkbook = Utils.getWorkBook(transformationFile)) {
			Sheet dataListSheet = listDataWorkbook.getSheetAt(0);
			Row dataListRow;
			int dataListCols = 0;
			int dataListTmp = 0;
			int numberOfRows = dataListSheet.getPhysicalNumberOfRows();

			for (int i = 0; i < 10 || i < numberOfRows; i++) {
				dataListRow = dataListSheet.getRow(i);
				if (dataListRow != null) {
					dataListTmp = dataListSheet.getRow(i).getPhysicalNumberOfCells();
					if (dataListTmp > dataListCols)
						dataListCols = dataListTmp;
				}
			}

			List<String> header = new ArrayList<>();

			for (int r = 0; r < numberOfRows; r++) {
				dataListRow = dataListSheet.getRow(r);
				if (dataListRow != null) {
					for (int c = 0; c < dataListCols; c += 2) {
						Cell cell = dataListRow.getCell((int) c);
						Cell cell2 = dataListRow.getCell((int) c + 1);
						if (cell != null && cell2 != null) {
							String valueString = Utils.returnCellValueAsString(cell);
							String valueString2 = Utils.returnCellValueAsString(cell2);
							if (r > 0) {
								if (!valueString.equals("")) {
									Map<String, String> map = transformationData.get(header.get(c / 2));
									map.put(valueString, valueString2);
								}
							} else {
								header.add(valueString);
								transformationData.put(valueString, new HashMap<String, String>());
							}
						}
					}
				}
			}
		}
		// return null;
		return transformationData;
	}

	public static void processData(File metaDataFiles, File transformationFile, String infoCardTypeColumn, String infoCardSubTypeColumn,
			String documentNumberColumn) throws InvalidFormatException, IOException {

		Map<String, Map<String, String>> listData = null;
		Map<Integer, String> columnsToCheck = null;
		if (transformationFile != null && transformationFile.exists()) {
			listData = loadListData(transformationFile);
			columnsToCheck = new HashMap<>();
		}

		File[] listOfFiles = metaDataFiles.listFiles();

		try (SXSSFWorkbook writeIntoBook = new SXSSFWorkbook(100);) {
			Sheet writeSheet = writeIntoBook.createSheet("data");

			cellStyle = writeIntoBook.createCellStyle();
			cellStyle.setDataFormat((short) 14);

			for (File file : listOfFiles) {
				if (file.getName().contains("results") || file.isDirectory() || file.getName().endsWith("txt")) {
					continue;
				}

				try (Workbook wb = Utils.getWorkBook(file)) {
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

					int documentNumberColumnNumber = -1;
					int infoCardTypeColumnNumber = -1;
					int infoCardSubTypeColumnNumber = -1;

					if (writeSheet.getPhysicalNumberOfRows() == 0) {
						Row createRow = writeSheet.createRow(0);
						Cell subClassHeader = createRow.createCell(0);
						subClassHeader.setCellValue("SubClass");
						setCellsValuesToRow(createRow, headerRow, cols);
					}

					for (int r = 0; r < rows; r++) {
						row = readSheet.getRow(r);
						if (row != null) {
							// if it's not the header
							if (r > 0) {

								String transformTo = "";
								// String transformTo = MasterControlSubclassTransformationRules.subClassTransformation(
								// Utils.returnCellValueAsString(row.getCell((int) infoCardTypeColumnNumber)),
								// Utils.returnCellValueAsString(row.getCell((int) infoCardSubTypeColumnNumber)),
								// Utils.returnCellValueAsString(row.getCell((int) documentNumberColumnNumber)));

								Row writeToRow = writeSheet.createRow(writeSheet.getPhysicalNumberOfRows());

								Cell createCell = writeToRow.createCell(0);
								createCell.setCellValue(transformTo);
								setCellsValuesToRow(writeToRow, row, cols);

								if (transformationFile != null && transformationFile.exists()) {
									// one on one transformations
									for (int c = 0; c < cols; c++) {
										if (columnsToCheck.containsKey(c)) {
											createCell = writeToRow.getCell((int) c);
											if (createCell != null) {
												String valueString = Utils.returnCellValueAsString(createCell);
												if (listData.get(columnsToCheck.get(c)).containsKey(valueString)) {
													createCell.setCellValue(listData.get(columnsToCheck.get(c)).get(valueString));
												}

											}
										}
									}
								}

							} else if (r == 0) {
								// get the column number of the subClass
								for (int c = 0; c < cols; c++) {
									cell = row.getCell((int) c);
									if (cell != null) {
										String valueString = Utils.returnCellValueAsString(cell);
										// Set the number of the column
										if (valueString.equals(documentNumberColumn)) {
											documentNumberColumnNumber = c;
										}
										if (valueString.equals(infoCardTypeColumn)) {
											infoCardTypeColumnNumber = c;
										}
										if (valueString.equals(infoCardSubTypeColumn)) {
											infoCardSubTypeColumnNumber = c;
										}

										// one on one transformation stuff
										if (transformationFile != null && transformationFile.exists()) {
											if (listData.containsKey(valueString)) {
												columnsToCheck.put(c, valueString);
											}
										}
									}
								}
							}
						}
					}

				}
			}
			File f = new File(metaDataFiles.getParentFile() + "\\MetaData SubClass Transformed.xlsx");
			try (FileOutputStream outputStream = new FileOutputStream(f)) {
				writeIntoBook.write(outputStream);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * Gets all the cells from dataRow and copys them in writeToRow, basically it copies the whole row, but skips the first one to allow to put the
	 * subClass
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
				Cell createCell = writeToRow.createCell(c + 1);

				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_NUMERIC:
					createCell.setCellValue(cell.getNumericCellValue());
					if (dates.contains(c)) {
						createCell.setCellStyle(cellStyle);
					}
					break;
				case Cell.CELL_TYPE_STRING:
					createCell.setCellValue(cell.getStringCellValue());
				}
			}
		}
	}
}
