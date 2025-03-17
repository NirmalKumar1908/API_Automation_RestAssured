package api.utilities.SinglePrompts;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.*;
import org.testng.annotations.DataProvider;

public class StoreContentDataProvider {

	@DataProvider(name = "storeContentDataFromExcel")
	public static Iterator<Object[]> readExcelData() {
		List<Object[]> testData = new ArrayList<>();
		String filePath = System.getProperty("user.dir") + "/testData/StoreContentTestData.xlsx"; // Dynamic path

		try (FileInputStream fis = new FileInputStream(new File(filePath));
				Workbook workbook = WorkbookFactory.create(fis)) {

			Sheet sheet = workbook.getSheetAt(0); // Read first sheet

			for (int i = 1; i <= sheet.getLastRowNum(); i++) { // Skip header row
				Row row = sheet.getRow(i);
				if (row == null)
					continue;

				String testCaseName = getCellValue(row, 0);
				String promptId = getCellValue(row, 1);
				String content = getCellValue(row, 2);
				String toneId = getCellValue(row, 3);
				String question70 = getCellValue(row, 4);
				String question243 = getCellValue(row, 5);
				String languageId = getCellValue(row, 6);
				String chatAnswer = getCellValue(row, 7);
				String isExisting = getCellValue(row, 8);
				String expectedStatusCode = getNumericCellValue(row, 9);
				String enabled = getCellValue(row, 10).trim(); // "Enabled" column

				// Include test cases where "Enabled" is "YES" only
				if (enabled.equalsIgnoreCase("YES")) {
					testData.add(new Object[] { testCaseName, promptId, content, toneId, question70, question243,
							languageId, chatAnswer, isExisting, expectedStatusCode });
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
