package utilityLibrary;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import org.apache.commons.io.FileUtils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class Helper {

	static XSSFWorkbook workbook;
	static XSSFSheet sheet;
	HSSFCell cell;

	// Retrieve current date in the yyyyMMddHHmmss format
	public static String currentDateStr() {
		// Date to String
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String dateStr = dateFormat.format(date);
		// Reporter.log("The current date is "+d,true);
		return dateStr;

	}

	
	// Retrieve current date in the yyyy_MM_dd_HH_mm_ss format
		public String dateString() {
			// Date to String
			String d;
			DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
			Date date = new Date();
			d = dateFormat.format(date);
			// Reporter.log("The current date is "+d,true);
			return d;

		}
		
	// Take Screenshot of the web page
	public static void takeScreenshot(WebDriver driver) throws Exception {
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(src,
				new File(System.getProperty("user.dir") + "\\Screenshots\\" + currentDateStr() + ".png"));
	}

	// write into excel in second column and in given row
	public void writeDataToExcel(int rowNum, String dataToWrite) throws IOException {
		String path = System.getProperty("user.dir") + "\\DataResource\\Data Sheet_Output.xlsx";
		// Reporter.log(System.getProperty("user.dir"),true);
		// Reporter.log(path,true);
		ExcelOperations.writeExcel(path, "Sheet1", rowNum, 2, dataToWrite);
	}

	// write into excel in second column and in given row
	public String readDataFromExcel(int rowNum) throws IOException {
		String path = System.getProperty("user.dir") + "\\DataResource\\Data Sheet.xlsx";
		return ExcelOperations.readExcel(path, "Sheet1", rowNum, 2);
	}

	// Open Web Application

	public static WebDriver openAUT(WebDriver driver) throws Exception {
		String browserName = getValue("browserName");
		String url = getValue("url");

		// launch browser and navigate to the url

		if (("chrome").equalsIgnoreCase(browserName)) {
			System.setProperty("webdriver.chrome.driver", "Drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		} else if (("ff").equalsIgnoreCase(browserName)) {
			System.setProperty("webdriver.gecko.driver", "Drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
		} else if (("ie").equalsIgnoreCase(browserName)) {
			System.setProperty("webdriver.ie.driver", "Drivers\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		}
		driver.manage().window().maximize();
		driver.get(url);
		Thread.sleep(10000);
		return driver;
	}

	public static String getValue(String name) throws Exception {
		// Use Properties file to store the credentials
		Properties prop = new Properties();
		File file = new File(System.getProperty("user.dir") + "//DataSource//Datafile.properties");
		FileInputStream fis = new FileInputStream(file);
		prop.load(fis);
		return prop.getProperty(name);
	}

	// Read data from excel for DFD, column 2
	public static String getDFDData(int rowNum, String SheetName) throws IOException {
		String path = System.getProperty("user.dir") + "\\DataSource\\Data Sheet.xlsx";
		return ExcelOperations.readExcel(path, SheetName, rowNum - 1, 1);
	}

	// Read data from excel for DFD, column 3
	public static String getDFDData2(int rowNum, String SheetName) throws IOException {
		String path = System.getProperty("user.dir") + "\\DataSource\\Data Sheet.xlsx";
		return ExcelOperations.readExcel(path, SheetName, rowNum - 1, 2);
	}

	// Read data from excel for DFD, column 2
	public static String getData(String SheetName, int rowNum, int colNum) throws IOException {
		String path = System.getProperty("user.dir") + "\\DataSource\\Data Sheet.xlsx";
		return ExcelOperations.readExcel(path, SheetName, rowNum - 1, colNum - 1);
	}

	// Read data from excel for Movie page
	public static String getMovieData(int rowNum, int colNum) throws IOException {
		return ExcelOperations.readExcel(System.getProperty("user.dir") + "//DataSource//Data Sheet.xlsx", "Movie",
				rowNum-1, colNum-1);
	}

	
	// get Last row for Movie page
	public static int getLastRowMovie() throws IOException {

		// Import excel sheet.
		File src = new File(System.getProperty("user.dir") + "//DataSource//Data Sheet.xlsx");

		// Load the file.
		FileInputStream finput = new FileInputStream(src);

		// Load he workbook.
		workbook = new XSSFWorkbook(finput);

		// Load the sheet in which data is stored.
		sheet = workbook.getSheet("Movie");

		return sheet.getLastRowNum();
	}

			
}
