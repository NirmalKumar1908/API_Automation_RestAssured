package api.utilities.SinglePrompts;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.*;
import org.testng.annotations.DataProvider;

public class CategoryListDataProvider {

	@DataProvider(name = "categoryErrorCases")
	public static Iterator<Object[]> readExcelData() {
		List<Object[]> testData = new ArrayList<>();
		String filePath = System.getProperty("user.dir") + "/testData/CategoryTestData.xlsx"; // Dynamic file path

		try (FileInputStream fis = new FileInputStream(new File(filePath));
				Workbook workbook = WorkbookFactory.create(fis)) {

			Sheet sheet = workbook.getSheetAt(0); // Assuming the data is in the first sheet

			for (int i = 1; i <= sheet.getLastRowNum(); i++) { // Skipping header row
				Row row = sheet.getRow(i);
				if (row == null)
					continue;

				int paginate = (int) getNumericCellValue(row, 0);
				int page = (int) getNumericCellValue(row, 1);
				int expectedStatusCode = (int) getNumericCellValue(row, 2);
				String enabled = getCellValue(row, 3).trim(); // "Enabled" column

				// Only include test cases where "Enabled" is "YES"
				if (enabled.equalsIgnoreCase("YES")) {
					testData.add(new Object[] { paginate, page, expectedStatusCode });
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return testData.iterator();
	}

	// Helper method to handle string cell values
	private static String getCellValue(Row row, int cellIndex) {
		if (row == null)
			return "";
		Cell cell = row.getCell(cellIndex);
		return (cell != null && cell.getCellType() != CellType.BLANK) ? cell.toString().trim() : "";
	}

	// Helper method to safely get numeric cell values
	private static double getNumericCellValue(Row row, int cellIndex) {
		if (row == null)
			return 0;
		Cell cell = row.getCell(cellIndex);
		return (cell != null && cell.getCellType() == CellType.NUMERIC) ? cell.getNumericCellValue() : 0;
	}
}
