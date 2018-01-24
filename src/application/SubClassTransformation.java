package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.agile.api.APIException;

public class SubClassTransformation {

	private static Map<String, String> sapDMSTransformation = new HashMap<>();
	static {
		sapDMSTransformation.put("CR-LOCR", "Equipment, Facility, and Utility (EFU) - LO - Lock Out-Tag Out");
		sapDMSTransformation.put("CR-OPCR", "Equipment, Facility, and Utility (EFU) - OP - Operational Parameters");
		sapDMSTransformation.put("CR-OSCR", "Equipment, Facility, and Utility (EFU) - OS - Operational Specification");
		sapDMSTransformation.put("CR-UTCR", "Equipment, Facility, and Utility (EFU) - UT - Utilization Table");
		sapDMSTransformation.put("971-", "Non-Quality System Document");
		sapDMSTransformation.put("973-", "Non-Quality System Document");
		sapDMSTransformation.put("974-", "Non-Quality System Document");
		sapDMSTransformation.put("976-", "Non-Quality System Document");
		sapDMSTransformation.put("977-", "Non-Quality System Document");
		sapDMSTransformation.put("978-", "Non-Quality System Document");
		sapDMSTransformation.put("980-", "Non-Quality System Document");
		sapDMSTransformation.put("985-", "Non-Quality System Document");
		sapDMSTransformation.put("982-", "Non-Quality System Document");
		sapDMSTransformation.put("983-", "Non-Quality System Document");
		sapDMSTransformation.put("995-", "Non-Quality System Document");
		sapDMSTransformation.put("961-", "Non-Quality System Document");
		sapDMSTransformation.put("964-", "Non-Quality System Document");
		sapDMSTransformation.put("898-", "Non-Quality System Document");
		sapDMSTransformation.put("CR-ENV", "Non-Quality System Document");
		sapDMSTransformation.put("CR-HKG", "Non-Quality System Document");
		sapDMSTransformation.put("CR-INF", "Non-Quality System Document");
		sapDMSTransformation.put("CR-MAI", "Non-Quality System Document");
		sapDMSTransformation.put("CR-MAT", "Non-Quality System Document");
		sapDMSTransformation.put("CR-MIS", "Non-Quality System Document");
		sapDMSTransformation.put("CR-SAP", "Non-Quality System Document");
		sapDMSTransformation.put("CR-PRO", "Non-Quality System Document");
		sapDMSTransformation.put("CR-UNI", "Non-Quality System Document");

		sapDMSTransformation.put("502-", "Production Method - Manufacturing Work Instructions");
		sapDMSTransformation.put("505-", "Production Method - Manufacturing Work Instructions");
		sapDMSTransformation.put("509-", "Production Method - Manufacturing Work Instructions");
		sapDMSTransformation.put("513-", "Production Method - Manufacturing Work Instructions");
		sapDMSTransformation.put("514-", "Production Method - Manufacturing Work Instructions");
		sapDMSTransformation.put("517-", "Production Method - Manufacturing Work Instructions");
		sapDMSTransformation.put("518-", "Production Method - Manufacturing Work Instructions");
		sapDMSTransformation.put("519-", "Production Method - Manufacturing Work Instructions");
		sapDMSTransformation.put("520-", "Production Method - Manufacturing Work Instructions");
		sapDMSTransformation.put("522-", "Production Method - Manufacturing Work Instructions");
		sapDMSTransformation.put("559-", "Production Method - Manufacturing Work Instructions");
		sapDMSTransformation.put("560-", "Production Method - Manufacturing Work Instructions");
		sapDMSTransformation.put("597-", "Production Method - Manufacturing Work Instructions");
		sapDMSTransformation.put("599-", "Production Method - Manufacturing Work Instructions");
		sapDMSTransformation.put("850-", "Production Method - Manufacturing Work Instructions");
		sapDMSTransformation.put("266-", "Production Method - Manufacturing Work Instructions");
		sapDMSTransformation.put("CR-BOPCAS", "Production Method - Manufacturing Work Instructions");
		sapDMSTransformation.put("CR-BOPCPR", "Production Method - Manufacturing Work Instructions");
		sapDMSTransformation.put("CR-BOPINJ", "Production Method - Manufacturing Work Instructions");
		sapDMSTransformation.put("CR-BOPLIM", "Production Method - Manufacturing Work Instructions");
		sapDMSTransformation.put("CR-BOPMCH", "Production Method - Manufacturing Work Instructions");
		sapDMSTransformation.put("CR-BOPMQA", "Production Method - Manufacturing Work Instructions");
		sapDMSTransformation.put("CR-BOPPKG", "Production Method - Manufacturing Work Instructions");
		sapDMSTransformation.put("CR-BOPSLP", "Production Method - Manufacturing Work Instructions");
		sapDMSTransformation.put("CR-BOPSPC", "Production Method - Manufacturing Work Instructions");
		sapDMSTransformation.put("CR-BOPWHG", "Production Method - Manufacturing Work Instructions");
		sapDMSTransformation.put("CR-BOPWSH", "Production Method - Manufacturing Work Instructions");
		sapDMSTransformation.put("CR-BOPL", "Production Method - Manufacturing Work Instructions");
		sapDMSTransformation.put("CR-EXT", "Production Method - Manufacturing Work Instructions");

		sapDMSTransformation.put("92.D", "Production Method - Process Specification - Drug");
		sapDMSTransformation.put("92.R", "Production Method - Process Specification - Rubber");
		sapDMSTransformation.put("92.S", "Production Method - Process Specification - Sterilization");

		sapDMSTransformation.put("92.T", "Production Method - Process Specification - Technical");
		sapDMSTransformation.put("256-", "Production Method - Process Specification - Technical");
		sapDMSTransformation.put("249-", "Production Method - Process Specification - Technical");
		sapDMSTransformation.put("219-", "Production Method - Process Specification - Technical");
		sapDMSTransformation.put("561-", "Production Method - Process Specification - Technical");
		sapDMSTransformation.put("562-", "Production Method - Process Specification - Technical");
		sapDMSTransformation.put("563-", "Production Method - Process Specification - Technical");
		sapDMSTransformation.put("564-", "Production Method - Process Specification - Technical");
		sapDMSTransformation.put("566-", "Production Method - Process Specification - Technical");
		sapDMSTransformation.put("568-", "Production Method - Process Specification - Technical");
		sapDMSTransformation.put("569-", "Production Method - Process Specification - Technical");
		sapDMSTransformation.put("571-", "Production Method - Process Specification - Technical");
		sapDMSTransformation.put("576-", "Production Method - Process Specification - Technical");
		sapDMSTransformation.put("579-", "Production Method - Process Specification - Technical");
		sapDMSTransformation.put("580-", "Production Method - Process Specification - Technical");
		sapDMSTransformation.put("581-", "Production Method - Process Specification - Technical");
		sapDMSTransformation.put("583-", "Production Method - Process Specification - Technical");
		sapDMSTransformation.put("595-", "Production Method - Process Specification - Technical");
		sapDMSTransformation.put("596-", "Production Method - Process Specification - Technical");
		sapDMSTransformation.put("930-", "Production Method - Process Specification - Technical");
		sapDMSTransformation.put("932-", "Production Method - Process Specification - Technical");
		sapDMSTransformation.put("100-", "Production Method - Process Specification - Technical");
		sapDMSTransformation.put("101-", "Production Method - Process Specification - Technical");
		sapDMSTransformation.put("105-", "Production Method - Process Specification - Technical");

		sapDMSTransformation.put("IS.PP.507.202.LA", "Production Method - Production Line Setup Instructions");
		sapDMSTransformation.put("IS.PP.507.203.LA", "Production Method - Production Line Setup Instructions");
		sapDMSTransformation.put("IS.PP.506.201", "Production Method - Production Line Setup Instructions");
		sapDMSTransformation.put("964-97700-001-CR", "Production Method - Production Line Setup Instructions");

		sapDMSTransformation.put("Q", "Product Packaging, Labeling, and Manuals - Artwork");
		sapDMSTransformation.put("D", "Product Packaging, Labeling, and Manuals - Artwork");
		sapDMSTransformation.put("429-", "Product Packaging, Labeling, and Manuals - Artwork");
		sapDMSTransformation.put("430-", "Product Packaging, Labeling, and Manuals - Artwork");
		sapDMSTransformation.put("425-", "Product Packaging, Labeling, and Manuals - Artwork");
		sapDMSTransformation.put("735-", "Product Packaging, Labeling, and Manuals - Artwork");
		sapDMSTransformation.put("736-", "Product Packaging, Labeling, and Manuals - Artwork");

		sapDMSTransformation.put("441-", "Product Packaging, Labeling, and Manuals - Other");
		sapDMSTransformation.put("444-", "Product Packaging, Labeling, and Manuals - Other");
		sapDMSTransformation.put("445-", "Product Packaging, Labeling, and Manuals - Other");

		sapDMSTransformation.put("40.", "Product Packaging, Labeling, and Manuals - Printed Material Summary");
		sapDMSTransformation.put("50.", "Product Packaging, Labeling, and Manuals - Printed Material Summary");
		sapDMSTransformation.put("55.", "Product Packaging, Labeling, and Manuals - Printed Material Summary");

		sapDMSTransformation.put("10.70", "Product Packaging, Labeling, and Manuals - Specification");
		sapDMSTransformation.put("10.78", "Product Packaging, Labeling, and Manuals - Specification");
		sapDMSTransformation.put("13.70", "Product Packaging, Labeling, and Manuals - Specification");
		sapDMSTransformation.put("13.78", "Product Packaging, Labeling, and Manuals - Specification");
		sapDMSTransformation.put("20.", "Product Packaging, Labeling, and Manuals - Specification");
		sapDMSTransformation.put("70.", "Product Packaging, Labeling, and Manuals - Specification");
		sapDMSTransformation.put("92.Y", "Product Packaging, Labeling, and Manuals - Specification");
		sapDMSTransformation.put("92.Z", "Product Packaging, Labeling, and Manuals - Specification");
		sapDMSTransformation.put("845-", "Product Packaging, Labeling, and Manuals - Specification");

		sapDMSTransformation.put("T", "Product Packaging, Labeling, and Manuals - Template/Print on Demand");
		sapDMSTransformation.put("599-", "Product Packaging, Labeling, and Manuals - Template/Print on Demand");

		sapDMSTransformation.put("Z10", "Quality System Procedure");
		sapDMSTransformation.put("970-", "Quality System Procedure");
		sapDMSTransformation.put("972-", "Quality System Procedure");
		sapDMSTransformation.put("975-", "Quality System Procedure");
		sapDMSTransformation.put("978-", "Quality System Procedure");
		sapDMSTransformation.put("CR-DDC", "Quality System Procedure");
		sapDMSTransformation.put("CR-DOC", "Quality System Procedure");
		sapDMSTransformation.put("CR-IQA", "Quality System Procedure");
		sapDMSTransformation.put("CR-MET", "Quality System Procedure");
		sapDMSTransformation.put("CR-QAE", "Quality System Procedure");
		sapDMSTransformation.put("CR-TRG", "Quality System Procedure");

		sapDMSTransformation.put("51.", "Quality System Record - Other");

		sapDMSTransformation.put("15.", "Sampling Plan");

		sapDMSTransformation.put("950-", "Servicing");
		sapDMSTransformation.put("965-", "Servicing");
		sapDMSTransformation.put("966-", "Servicing");
		sapDMSTransformation.put("969-", "Servicing");
		sapDMSTransformation.put("982-", "Servicing");
		sapDMSTransformation.put("984-", "Servicing");
		sapDMSTransformation.put("987-", "Servicing");
		sapDMSTransformation.put("990-", "Servicing");
		sapDMSTransformation.put("845-", "Servicing");
		sapDMSTransformation.put("847-", "Servicing");

		sapDMSTransformation.put("846-", "Servicing - Product");
		sapDMSTransformation.put("894-", "Servicing - Product");
		sapDMSTransformation.put("895-", "Servicing - Product");
		sapDMSTransformation.put("896-", "Servicing - Product");

		sapDMSTransformation.put("11.", "Spec - API and Excipient");
		sapDMSTransformation.put("14.", "Spec - API and Excipient");

		sapDMSTransformation.put("32.", "Spec - Commodity and Process Summary");
		sapDMSTransformation.put("34.", "Spec - Commodity and Process Summary");
		sapDMSTransformation.put("35.", "Spec - Commodity and Process Summary");
		sapDMSTransformation.put("36.", "Spec - Commodity and Process Summary");
		sapDMSTransformation.put("CP.", "Spec - Commodity and Process Summary");

		// sapDMSTransformation.put("10.", "Spec - Commodity/Material");
		// sapDMSTransformation.put("6XX-", "Spec - Commodity/Material");
		// sapDMSTransformation.put("7XX-", "Spec - Commodity/Material");
		// sapDMSTransformation.put("280-", "Spec - Commodity/Material");

		sapDMSTransformation.put("980-", "Spec - Environmental Specification");
		sapDMSTransformation.put("904-", "Spec - Environmental Specification");
		sapDMSTransformation.put("910-", "Spec - Environmental Specification");

		sapDMSTransformation.put("45.", "Spec - Marketed Product Stability Protocol");

		// sapDMSTransformation.put("280-", "Spec - Other");

		sapDMSTransformation.put("95.", "Spec - Performance Specification");

		sapDMSTransformation.put("249-", "Spec - Printed Circuit Board - Assembly/Schematic");
		sapDMSTransformation.put("807-", "Spec - Printed Circuit Board - Assembly/Schematic");
		sapDMSTransformation.put("805-", "Spec - Printed Circuit Board - Assembly/Schematic");
		sapDMSTransformation.put("810-", "Spec - Printed Circuit Board - Assembly/Schematic");
		sapDMSTransformation.put("261-", "Spec - Printed Circuit Board - Assembly/Schematic");
		sapDMSTransformation.put("263-", "Spec - Printed Circuit Board - Assembly/Schematic");
		sapDMSTransformation.put("264-", "Spec - Printed Circuit Board - Assembly/Schematic");
		sapDMSTransformation.put("265-", "Spec - Printed Circuit Board - Assembly/Schematic");
		sapDMSTransformation.put("269-", "Spec - Printed Circuit Board - Assembly/Schematic");

		// sapDMSTransformation.put("80.", "Spec - Product Purchase Specification");
		// sapDMSTransformation.put("85.", "Spec - Product Purchase Specification");

		sapDMSTransformation.put("234-", "Spec - Software Specification");
		sapDMSTransformation.put("238-", "Spec - Software Specification");
		sapDMSTransformation.put("273-", "Spec - Software Specification");
		sapDMSTransformation.put("278-", "Spec - Software Specification");
		sapDMSTransformation.put("259-", "Spec - Software Specification");

		sapDMSTransformation.put("60.", "Spec - Sterile Solution");

		sapDMSTransformation.put("TDS.", "Spec - Technical Data Sheet");

		sapDMSTransformation.put("61.", "Spec - Test and Inspection Specification");
		sapDMSTransformation.put("62.", "Spec - Test and Inspection Specification");
		sapDMSTransformation.put("63.", "Spec - Test and Inspection Specification");
		sapDMSTransformation.put("64.", "Spec - Test and Inspection Specification");
		sapDMSTransformation.put("68.", "Spec - Test and Inspection Specification");
		sapDMSTransformation.put("69.", "Spec - Test and Inspection Specification");
		sapDMSTransformation.put("526-", "Spec - Test and Inspection Specification");
		sapDMSTransformation.put("541-", "Spec - Test and Inspection Specification");
		sapDMSTransformation.put("551-", "Spec - Test and Inspection Specification");
		sapDMSTransformation.put("588-", "Spec - Test and Inspection Specification");

		sapDMSTransformation.put("90.B", "Test Method - Biologic");
		sapDMSTransformation.put("94.B", "Test Method - Biologic");
		sapDMSTransformation.put("TM-BIO-HCR- XXXX", "Test Method - Biologic");

		sapDMSTransformation.put("90.C", "Test Method - Chemical");
		sapDMSTransformation.put("94.C", "Test Method - Chemical");
		sapDMSTransformation.put("TM-CHE-HCR- XXXX", "Test Method - Chemical");

		sapDMSTransformation.put("TM-DIM-HCR- XXXX", "Test Method - Functional");
		sapDMSTransformation.put("TM-VIS-HCR- XXXX", "Test Method - Functional");

		sapDMSTransformation.put("90.I", "Test Method - Identity");
		sapDMSTransformation.put("94.I", "Test Method - Identity");

		sapDMSTransformation.put("90.M", "Test Method - Microbiologic");
		sapDMSTransformation.put("94.M", "Test Method - Microbiologic");

		sapDMSTransformation.put("90.P", "Test Method - Physical");
		sapDMSTransformation.put("94.P", "Test Method - Physical");
		sapDMSTransformation.put("TM-PHY-HCR-XXXX", "Test Method - Physical");
	}

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

			int subClassColumnToTransfor = -1;
			int descriptionColumnNumber = -1;

			try (Workbook writeIntoBook = getWorkBook(formData.getResultsFile())) {
				Sheet writeSheet = writeIntoBook.getSheetAt(0);
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

							String description = returnCellValueAsString(row.getCell((int) descriptionColumnNumber));

							if (description == null || description.equals("")) {
								continue;
							}

							String subClassColumnValueToTransform = returnCellValueAsString(
									row.getCell((int) subClassColumnToTransfor));

							String transformTo = "";
							for (String key : sapDMSTransformation.keySet()) {
								if (subClassColumnValueToTransform.startsWith(key)) {
									transformTo = sapDMSTransformation.get(key);
									break;
								}
							}

							Row writeToRow = writeSheet.createRow(writeSheet.getPhysicalNumberOfRows());

							Cell createCell = writeToRow.createCell(0);
							createCell.setCellValue(transformTo);

							setCellsValuesToRow(writeToRow, row, cols);

						} else if (r == 0) {
							// get the column number of the subClass
							for (int c = 0; c < cols; c++) {
								cell = row.getCell((int) c);
								if (cell != null) {
									String valueString = returnCellValueAsString(cell);
									// Set the number of the column
									if (valueString.equals(formData.getSubClassColumn())) {
										subClassColumnToTransfor = c;
									}
									if (valueString.equals(formData.getDescriptionColumn())) {
										descriptionColumnNumber = c;
									}
								}
							}
						}
					}
				}

				try (FileOutputStream outputStream = new FileOutputStream(formData.getResultsFile())) {
					writeIntoBook.write(outputStream);
					writeIntoBook.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
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

	/**
	 * Gets all the cells from dataRow and copys them in writeToRow, basically it
	 * copies the whole row, but skips the first one to allow to put the subClass
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
					createCell.setCellType(Cell.CELL_TYPE_NUMERIC);
					createCell.setCellValue(cell.getNumericCellValue());
					break;
				case Cell.CELL_TYPE_STRING:
					createCell.setCellType(Cell.CELL_TYPE_STRING);
					createCell.setCellValue(cell.getStringCellValue());
				}
			}
		}
	}
}
