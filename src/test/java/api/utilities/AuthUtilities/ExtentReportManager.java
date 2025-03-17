package api.utilities.AuthUtilities;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager implements ITestListener {

    private ExtentSparkReporter sparkReporter;
    private ExtentReports extent;
    private ExtentTest test;
    private static String moduleName = "";
    private static boolean isReportCreated = false;

    @Override
    public void onStart(ITestContext testContext) {
        moduleName = testContext.getName();

        if (!isReportCreated) {
            String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
            String reportFileName = "Test-Report-" + timeStamp + ".html";

            sparkReporter = new ExtentSparkReporter(".\\reports\\" + reportFileName);
            sparkReporter.config().setDocumentTitle("GW_RestAssuredAutomationProject");
            sparkReporter.config().setReportName("Gravity Write API Test");
            sparkReporter.config().setTheme(Theme.DARK);

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            extent.setSystemInfo("Application", "Gravity Write API");
            extent.setSystemInfo("Operating System", System.getProperty("os.name"));
            extent.setSystemInfo("User Name", System.getProperty("user.name"));
            extent.setSystemInfo("Environment", "LIVE ENV");
            extent.setSystemInfo("User", "Nirmal Kumar A");

            isReportCreated = true;
        }
    }

    private String getTestName(ITestResult result) {
        Object[] parameters = result.getParameters();
        return result.getName() + Arrays.toString(parameters);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        long duration = result.getEndMillis() - result.getStartMillis();
        test = extent.createTest(getTestName(result));
        test.assignCategory(moduleName);
        test.log(Status.PASS, "Test Passed in " + duration + "ms");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        long duration = result.getEndMillis() - result.getStartMillis();
        test = extent.createTest(getTestName(result));
        test.assignCategory(moduleName);
        test.log(Status.FAIL, "Test Failed in " + duration + "ms");
        test.log(Status.FAIL, result.getThrowable().getMessage());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test = extent.createTest(getTestName(result));
        test.assignCategory(moduleName);
        test.log(Status.SKIP, "Test Skipped");
        test.log(Status.SKIP, result.getThrowable().getMessage());
    }

    @Override
    public void onFinish(ITestContext testContext) {
        String moduleSummary = "Module: " + moduleName + " | " +
                               "Passed: " + testContext.getPassedTests().size() + " | " +
                               "Failed: " + testContext.getFailedTests().size() + " | " +
                               "Skipped: " + testContext.getSkippedTests().size() + " | " +
                               "Execution Time: " + (testContext.getEndDate().getTime() - testContext.getStartDate().getTime()) + "ms";

        System.out.println(moduleSummary);
        extent.flush();
    }
}
