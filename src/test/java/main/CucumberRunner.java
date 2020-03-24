package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.google.common.io.Files;

import cucumber.api.DataTable;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import helpers.ReportHelper;

@CucumberOptions(strict = true, monochrome = true, features = "src/test/resources/features", glue = "stepdefinition", format = {"pretty","json:target/cucumber.json"}, tags = { "@Regression" })

public class CucumberRunner extends AbstractTestNGCucumberTests {

    public static Properties config = null;
    public static WebDriver driver = null;

    public void LoadConfigProperty() throws IOException {
        config = new Properties();
        FileInputStream ip = new FileInputStream(
                System.getProperty("user.dir") + "/src/test/resources/config/config.properties");
        config.load(ip);
    }

    public void configureDriverPath() throws IOException {
        if(System.getProperty("os.name").startsWith("Linux")) {
            String firefoxDriverPath = System.getProperty("user.dir") + "/src/test/resources/drivers/linux/geckodriver";
            System.setProperty("webdriver.gecko.driver", firefoxDriverPath);
            String chromeDriverPath = System.getProperty("user.dir") + "/src/test/resources/drivers/linux/chromedriver";
            System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        }
        if(System.getProperty("os.name").startsWith("Mac")) {
            String firefoxDriverPath = System.getProperty("user.dir") + "/src/test/resources/drivers/mac/geckodriver";
            System.setProperty("webdriver.gecko.driver", firefoxDriverPath);
            String chromeDriverPath = System.getProperty("user.dir") + "/src/test/resources/drivers/mac/chromedriver";
            System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        }
        if(System.getProperty("os.name").startsWith("Windows")) {
            String firefoxDriverPath = System.getProperty("user.dir") + "//src//test//resources//drivers//windows//geckodriver.exe";
            System.setProperty("webdriver.gecko.driver", firefoxDriverPath);
            String chromeDriverPath = System.getProperty("user.dir") + "//src//test//resources//drivers//windows//chromedriver.exe";
            System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        }
    }

    public void openBrowser() throws Exception {
        // loads the config options
        LoadConfigProperty();
        // configures the driver path
        configureDriverPath();
        if (config.getProperty("browserType").equals("firefox")) {
            driver = new FirefoxDriver();
        } else if (config.getProperty("browserType").equals("chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-gpu");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.setExperimentalOption("useAutomationExtension", false);
            driver = new ChromeDriver(options);
        }
    }

    public void maximizeWindow() {
        driver.manage().window().maximize();
    }

    public void implicitWait(int time) {
        driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
    }

    public void explicitWait(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, 3000);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void explicitWaitForTitle(String text) {
        WebDriverWait wait = new WebDriverWait(driver, 3000);
        wait.until(ExpectedConditions.titleIs(text));
    }

    public void loadCarousel() throws InterruptedException {
        Thread.sleep(2000);
    }

    public ArrayList getColoumFromDataTable(DataTable table, int coloum){
        List<List<String>> rows = table.asLists(String.class);
        ArrayList<String> expectedList = new ArrayList<String>();
        for (List<String> columns : rows) {
            System.out.println(columns.get(coloum)+"$$$$$$");
            expectedList.add(columns.get(coloum).replaceAll(" ",""));

        }
        //expectedList.removeIf(item -> item == "null" || "".equals(item));
        ArrayList<String>  arrlist2 = new ArrayList<String>();
        arrlist2.add("NIL");
        expectedList.removeAll(arrlist2);
        System.out.println(expectedList +"  :size of the expected &&&");
        return expectedList;
    }
    public static String safeChar(String input)
    {
    char[] allowed = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_".toCharArray();
    char[] charArray = input.toString().toCharArray();
    StringBuilder result = new StringBuilder();
    for (char c : charArray)
    {
        for (char a : allowed)
        {
            if(c==a) result.append(a);
        }
    }
    return result.toString();
    }

    public void pageLoad(int time) {
        driver.manage().timeouts().pageLoadTimeout(time, TimeUnit.SECONDS);
    }

    public void deleteAllCookies() {
        driver.manage().deleteAllCookies();
    }

    public void setEnv() throws Exception {
        LoadConfigProperty();
        String baseUrl = config.getProperty("siteUrl");
        driver.get(baseUrl);
    }

    public static String currentDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String cal1 = dateFormat.format(cal.getTime());
        return cal1;
    }

    @BeforeSuite(alwaysRun = true)
    public void setUp() throws Exception {
        openBrowser();
        //maximizeWindow();
        implicitWait(30);
        deleteAllCookies();
        setEnv();
    }

    @AfterClass(alwaysRun = true)
    public void takeScreenshot() throws IOException {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File trgtFile = new File(System.getProperty("user.dir") + "//screenshots/screenshot.png");
        trgtFile.getParentFile().mkdir();
        trgtFile.createNewFile();
        Files.copy(scrFile, trgtFile);

    }

    @AfterMethod(alwaysRun = true)
    public void tearDownr(ITestResult result) throws IOException {
        if (result.isSuccess()) {
            File imageFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String failureImageFileName = result.getMethod().getMethodName()
                    + new SimpleDateFormat("MM-dd-yyyy_HH-ss").format(new GregorianCalendar().getTime()) + ".png";
            File failureImageFile = new File(System.getProperty("user.dir") + "//screenshots//" + failureImageFileName);
            failureImageFile.getParentFile().mkdir();
            failureImageFile.createNewFile();
            Files.copy(imageFile, failureImageFile);
        }

    }
    @AfterSuite(alwaysRun=true)
    public void generateHTMLReports() {
        ReportHelper.generateCucumberReport();
    }

    @AfterSuite(alwaysRun = true)
    public void quit() throws IOException, InterruptedException {
        driver.quit();
    }

}
