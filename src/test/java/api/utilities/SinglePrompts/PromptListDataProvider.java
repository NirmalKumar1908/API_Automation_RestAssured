package api.utilities.SinglePrompts;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.*;
import org.testng.annotations.DataProvider;

public class PromptListDataProvider {

	/**
	 * DataProvider method to read test data from an Excel file. Only includes rows
	 * where the "Enabled" column is marked as "YES".
	 */
	@DataProvider(name = "errorCases")
	public static Object[][] readExcelData() {
		Object[][] data = null;
		try {
			// Define the path to the Excel file
			String filePath = System.getProperty("user.dir") + "/testData/PromptListTestData.xlsx";
			FileInputStream fis = new FileInputStream(new File(filePath));
			Workbook workbook = WorkbookFactory.create(fis);
			Sheet sheet = workbook.getSheetAt(0); // Reading the first sheet

			int rowCount = sheet.getPhysicalNumberOfRows(); // Total number of rows
			List<Object[]> filteredData = new ArrayList<>();

			// Loop through rows, skipping the header row (index 0)
			for (int i = 1; i < rowCount; i++) {
				Row row = sheet.getRow(i);
				if (row == null)
					continue;

				// Read the "Enabled" column (assumed to be the 5th column, index 4)
				String enabled = getCellValue(row, 4);

				// Only include rows where "Enabled" is "YES"
				if ("YES".equalsIgnoreCase(enabled)) {
					Object[] rowData = { getCellValue(row, 0), // Test Case Name (String)
							getNumericCellValue(row, 1), // Paginate (int)
							getNumericCellValue(row, 2), // Page (int)
							getNumericCellValue(row, 3) // Expected Status Code (int)
					};
					filteredData.add(rowData);
				}
			}

			// Convert list to Object[][] array for TestNG
			data = filteredData.toArray(new Object[0][]);

			// Close workbook and input stream
			workbook.close();
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
			return new Object[0][0]; // Return empty array if an error occurs
		}
		return data;
	}

	/**
	 * Helper method to retrieve a cell value as a String. Handles blank cells and
	 * null values gracefully.
	 */
	private static String getCellValue(Row row, int cellIndex) {
		if (row == null)
			return "";
		Cell cell = row.getCell(cellIndex);
		return (cell != null && cell.getCellType() != CellType.BLANK) ? cell.toString().trim() : "";
	}

	/**
	 * Helper method to retrieve a numeric cell value as an integer. If the cell is
	 * empty or not numeric, returns 0.
	 */
	private static int getNumericCellValue(Row row, int cellIndex) {
		if (row == null)
			return 0;
		Cell cell = row.getCell(cellIndex);
		return (cell != null && cell.getCellType() == CellType.NUMERIC) ? (int) cell.getNumericCellValue() : 0;
	}
}
