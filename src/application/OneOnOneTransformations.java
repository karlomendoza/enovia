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
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import entities.FormData;
import utils.Utils;

public class OneOnOneTransformations {

	public static Map<String, Map<String, String>> loadListData(File transformationFile, String sheetName)
			throws IOException, InvalidFormatException {
		Map<String, Map<String, String>> transformationData = new HashMap<>();
		try (Workbook listDataWorkbook = Utils.getWorkBook(transformationFile)) {
			Sheet dataListSheet = listDataWorkbook.getSheet(sheetName);
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
					for (int c = 0; c < dataListCols / 2; c += 2) {
						Cell cell = dataListRow.getCell((int) c);
						Cell cell2 = dataListRow.getCell((int) c + 1);
						if (cell != null && cell2 != null) {
							String valueString = Utils.returnCellValueAsString(cell);
							String valueString2 = Utils.returnCellValueAsString(cell2);
							if (r > 0) {
								if (!valueString.equals("")) {
									Map<String, String> map = transformationData.get(header.get(c));
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

	public static void processData(FormData formData) throws InvalidFormatException, IOException {

		Map<String, Map<String, String>> listData = loadListData(formData.getMetaDataFile(), formData.getWhatever());
		Map<Integer, String> columnsToCheck = new HashMap<>();

		try (Workbook wb = Utils.getWorkBook(formData.getMetaDataFile())) {
			Sheet sheet = wb.getSheetAt(0);
			Row row;
			Cell cell;

			int rows; // No of rows
			rows = sheet.getPhysicalNumberOfRows();

			int cols = 0; // No of columns
			int tmp = 0;

			// This trick ensures that we get the data properly even if it doesn't start
			// from first few rows
			for (int i = 0; i < 10 || i < rows; i++) {
				row = sheet.getRow(i);
				if (row != null) {
					tmp = sheet.getRow(i).getPhysicalNumberOfCells();
					if (tmp > cols)
						cols = tmp;
				}
			}

			Sheet writeSheet = wb.createSheet("Java Tranformation");

			for (int r = 0; r < rows; r++) {
				row = sheet.getRow(r);
				if (row != null) {
					// if it's not the header
					if (r > 0) {
						for (int c = 0; c < cols; c++) {
							if (columnsToCheck.containsKey(c)) {
								cell = row.getCell((int) c);
								if (cell != null) {
									String valueString = Utils.returnCellValueAsString(cell);

									// TODO obtener spliter de parte del usuario
									String[] split = valueString.split(formData.getSplitter());
									Row writeRow = writeSheet.createRow(r);

									for (int i = 0; i < split.length; i++) {
										Cell writeCell = writeRow.createCell(i);
										writeCell.setCellValue(listData.get(columnsToCheck.get(c)).get(split[i]));
									}

									// StringJoiner sj = new StringJoiner(formData.getSplitter().replace("\\", ""));
									//
									// for (String value : split) {
									// sj.add(listData.get(columnsToCheck.get(c)).get(value));
									// }
									// writeCell.setCellValue(sj.toString());
								}
							}
						}
					} else if (r == 0) {
						// get the column number of the fileName and extension
						for (int c = 0; c < cols; c++) {
							cell = row.getCell((int) c);
							if (cell != null) {
								String valueString = Utils.returnCellValueAsString(cell);
								if (listData.containsKey(valueString)) {
									columnsToCheck.put(c, valueString);
								}
							}

						}
					}
				}
			}

			try (FileOutputStream outputStream = new FileOutputStream(formData.getMetaDataFile())) {
				wb.write(outputStream);
			}
		} catch (Exception ioe) {
			ioe.printStackTrace();
		}
	}
}
