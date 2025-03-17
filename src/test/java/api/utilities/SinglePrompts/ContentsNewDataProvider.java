package api.utilities.SinglePrompts;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.*;
import org.testng.annotations.DataProvider;

public class ContentsNewDataProvider {

	@DataProvider(name = "contentsDataFromExcel")
	public static Iterator<Object[]> readExcelData() {
		List<Object[]> testData = new ArrayList<>();
		String filePath = System.getProperty("user.dir") + "/testData/ContentsTestData.xlsx"; // Excel file path

		try (FileInputStream fis = new FileInputStream(new File(filePath));
				Workbook workbook = WorkbookFactory.create(fis)) {

			Sheet sheet = workbook.getSheetAt(0); // First sheet

			for (int i = 1; i <= sheet.getLastRowNum(); i++) { // Skip header row
				Row row = sheet.getRow(i);
				if (row == null)
					continue;

				// Read all necessary columns from the Excel file
				String testCaseName = getCellValue(row, 0);
				String promptId = getCellValue(row, 1);
				String toneId = getCellValue(row, 2);
				String question70 = getCellValue(row, 3);
				String question243 = getCellValue(row, 4);
				String languageId = getCellValue(row, 5);
				String isChat = getCellValue(row, 6);
				String expectedStatus = getCellValue(row, 7); // New column for expected status
				String enabled = getCellValue(row, 8).trim(); // 'Enabled' column

				// Only add test case if 'Enabled' is YES
				if (enabled.equalsIgnoreCase("YES")) {
					testData.add(new Object[] { testCaseName, promptId, toneId, question70, question243, languageId,
							isChat, expectedStatus, enabled });
				}
			}
		} catch (Exception e) {
			System.err.println("Error reading Excel file: " + e.getMessage());
			e.printStackTrace();
		}
		return testData.iterator();
	}

	// Helper method for retrieving cell values (handles both numeric and string
	// values properly)
	private static String getCellValue(Row row, int cellIndex) {
		if (row == null || row.getCell(cellIndex) == null)
			return "";
		Cell cell = row.getCell(cellIndex);
		switch (cell.getCellType()) {
		case STRING:
			return cell.getStringCellValue().trim();
		case NUMERIC:
			return String.valueOf((int) cell.getNumericCellValue()); // Convert numeric to integer format
		case BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());
		case FORMULA:
			return cell.getCellFormula();
		default:
			return "";
		}
	}
}
