package application;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import utils.Utils;

public class NumberTransformation {

	public static void main(String... strings) throws InvalidFormatException, IOException {
		File metaDataFiles = new File("C:\\Users\\Karlo Mendoza\\Excel Work\\ICU MEDICAL\\Enovia\\data\\");

		processData(metaDataFiles);
	}

	public static CellStyle cellStyle;
	public static List<Integer> dates = new ArrayList<>();

	static {
	}

	public static void processData(File metaDataFiles) throws InvalidFormatException, IOException {

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

					if (writeSheet.getPhysicalNumberOfRows() == 0) {
						Row createRow = writeSheet.createRow(0);
						Cell subClassHeader = createRow.createCell(0);
						subClassHeader.setCellValue("Number");
						createRow.createCell(1).setCellValue("Description");
						setCellsValuesToRow(createRow, headerRow, cols);
					}

					for (int r = 0; r < rows; r++) {
						row = readSheet.getRow(r);
						if (row != null) {
							// if it's not the header
							if (r > 0) {
								Row writeToRow = writeSheet.createRow(writeSheet.getPhysicalNumberOfRows());
								String name = Utils.returnCellValueAsString(row.getCell((int) 2));
								String[] split = name.split(" ");
								String numberValue = split[0];
								Cell createCell = writeToRow.createCell(2);
								createCell.setCellValue(name);
								Cell create2Cell = writeToRow.createCell(3);

								if (name.contains("Change Control") || name.startsWith("CR") || name.startsWith("NC") || name.contains("Audit")
										|| name.startsWith("TQA") || name.contains("Redline")) {

									createCell.setCellValue("");
									create2Cell.setCellValue(name);
									setCellsValuesToRow(writeToRow, row, cols);
									continue;
								}

								try {
									Integer valueOf = Integer.parseInt(String.valueOf(numberValue.charAt(0)));
									createCell.setCellValue(numberValue);
									create2Cell.setCellValue(name.replace(numberValue, ""));
								} catch (Exception ex) {
									if (numberValue.startsWith("SD") || numberValue.startsWith("TM") || numberValue.startsWith("DE")
											|| numberValue.startsWith("DU") || numberValue.startsWith("CN") || numberValue.startsWith("K")
											|| numberValue.startsWith("NPBP")) {
										createCell.setCellValue(numberValue);
										create2Cell.setCellValue(name.replace(numberValue, ""));
									} else if (name.startsWith("CS0x")) {
										createCell.setCellValue(name.substring(0, name.length() - 12));
										create2Cell.setCellValue(name);
									} else if (name.startsWith("IEC")) {
										int indexOf = name.indexOf("Closing");

										createCell.setCellValue(name.substring(0, indexOf + 7));
										create2Cell.setCellValue(name);

									} else if (name.startsWith("PPD-SD")) {
										createCell.setCellValue(name.substring(0, 13));
										create2Cell.setCellValue(name);
									} else if (name.startsWith("VCR ")) {
										createCell.setCellValue(name.substring(0, name.indexOf("-")));
										create2Cell.setCellValue(name);
									} else if (name.startsWith("IS RC")) {
										int indexOf = name.indexOf("Implementation");

										createCell.setCellValue(name.substring(0, indexOf + 14));
										create2Cell.setCellValue(name.substring(indexOf + 14));

									} else if (numberValue.startsWith("DHF")) {
										try {
											Integer valueOf = Integer.parseInt(String.valueOf(numberValue.charAt(4)));
											createCell.setCellValue(numberValue);
											create2Cell.setCellValue(name.replace(numberValue, ""));
											valueOf++;
											setCellsValuesToRow(writeToRow, row, cols);
											continue;
										} catch (Exception exp) {
											//
										}

										String chart4 = String.valueOf(name.charAt(4));
										String chart5 = String.valueOf(name.charAt(5));
										if (chart4.matches("[a-zA-Z]") || chart5.matches("[a-zA-Z]")) {
											int i = 4;
											while (true) {
												if (String.valueOf(name.charAt(i)).equals(" ") || String.valueOf(name.charAt(i)).equals("-")) {

													i++;
													while (true) {
														try {
															if (String.valueOf(name.charAt(i)).equals(" ") || String.valueOf(name.charAt(i)).equals("_")) {

																createCell.setCellValue(name.substring(0, i));
																create2Cell.setCellValue(name.substring(i + 1));
																break;

															}
														} catch (Exception ex3) {
															createCell.setCellValue(name.substring(0, i));
															create2Cell.setCellValue(name.substring(0, i));
															break;
														}
														i++;
													}
													break;
												}
												i++;
											}
										}

									} else {
										try {

											try {
												if (Integer.parseInt(String.valueOf(name.charAt(name.length() - 12))) >= 0) {

												}
											} catch (Exception exp4) {
												Long valueOf = Long.parseLong(name.substring(name.length() - 11));
												createCell.setCellValue(valueOf);
												create2Cell.setCellValue(name.replace(name.substring(name.length() - 12), ""));
												setCellsValuesToRow(writeToRow, row, cols);
												continue;
											}

											try {
												if (Integer.parseInt(String.valueOf(name.charAt(name.length() - 13))) >= 0) {
													Long valueOf = Long.parseLong(name.substring(name.length() - 13));
													createCell.setCellValue(valueOf);
													create2Cell.setCellValue(name.replace(name.substring(name.length() - 14), ""));
													setCellsValuesToRow(writeToRow, row, cols);
													continue;
												}
											} catch (Exception exp4) {

											}

											Long valueOf = Long.parseLong(name.substring(name.length() - 12));
											createCell.setCellValue(valueOf);
											create2Cell.setCellValue(name.replace(name.substring(name.length() - 13), ""));

										} catch (Exception ex2) {
											createCell.setCellValue("manual transform");
											create2Cell.setCellValue("manual transform");
										}
									}
								}
								setCellsValuesToRow(writeToRow, row, cols);
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
		int i = -1;
		for (int c = 0; c <= colsNumber; c++) {
			i++;
			Cell cell = dataRow.getCell((int) i);
			if (cell != null) {
				if (c == 2) {
					c++;
					c++;
				}
				Cell createCell = writeToRow.createCell(c);

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
