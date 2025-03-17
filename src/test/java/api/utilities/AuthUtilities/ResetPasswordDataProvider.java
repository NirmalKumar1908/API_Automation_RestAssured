package api.utilities.AuthUtilities;

import org.testng.annotations.DataProvider;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.*;

public class ResetPasswordDataProvider {

	@DataProvider(name = "resetPasswordDataFromExcel")
	public static Iterator<Object[]> getResetPasswordData() {
		List<Object[]> testData = new ArrayList<>();
		String filePath = System.getProperty("user.dir") + "/testData/ResetPasswordTestData.xlsx"; // Dynamic file path

		try (FileInputStream fis = new FileInputStream(new File(filePath));
				Workbook workbook = WorkbookFactory.create(fis)) {

			Sheet sheet = workbook.getSheet("ResetPasswordData"); // Update with actual sheet name

			for (Row row : sheet) {
				if (row.getRowNum() == 0)
					continue; // Skip header row

				String testCaseName = row.getCell(0).getStringCellValue();
				String email = row.getCell(1).getStringCellValue();
				String newPassword = row.getCell(2).getStringCellValue();
				String confirmPassword = row.getCell(3).getStringCellValue();
				String expectedStatusCode = row.getCell(4).toString().split("\\.")[0]; // Convert to integer string
				String enabled = row.getCell(5).getStringCellValue().trim();

				if (enabled.equalsIgnoreCase("YES")) { // Only include enabled test cases
					testData.add(
							new Object[] { testCaseName, email, newPassword, confirmPassword, expectedStatusCode });
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return testData.iterator();
	}
}
