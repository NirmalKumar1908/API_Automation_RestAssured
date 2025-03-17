package api.utilities.AuthUtilities;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.*;
import org.testng.annotations.DataProvider;

public class LoginDataProvider {

	@DataProvider(name = "loginDataFromExcel")
	public static Iterator<Object[]> readExcelData() {
		List<Object[]> testData = new ArrayList<>();
		String filePath = System.getProperty("user.dir") + "/testData/LoginTestData.xlsx"; // Dynamic file path

		try (FileInputStream fis = new FileInputStream(new File(filePath));
				Workbook workbook = WorkbookFactory.create(fis)) {

			Sheet sheet = workbook.getSheetAt(0); // First sheet

			for (int i = 1; i <= sheet.getLastRowNum(); i++) { // Skip header row
				Row row = sheet.getRow(i);
				if (row == null)
					continue;

				String testCaseName = getCellValue(row, 0);
				String email = getCellValue(row, 1);
				String password = getCellValue(row, 2);
				String expectedStatusCode = getNumericCellValue(row, 3);
				String enabled = getCellValue(row, 4).trim(); // "Enabled" column

				// Only include test cases where "Enabled" is "YES"
				if (enabled.equalsIgnoreCase("YES")) {
					testData.add(new Object[] { testCaseName, email, password, expectedStatusCode });
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

	// Helper method to handle numeric cell values safely
	private static String getNumericCellValue(Row row, int cellIndex) {
		if (row == null)
			return "0";
		Cell cell = row.getCell(cellIndex);
		return (cell != null && cell.getCellType() == CellType.NUMERIC)
				? String.valueOf((int) cell.getNumericCellValue())
				: "0";
	}
}
