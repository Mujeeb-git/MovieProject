package Movie_Test;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import javax.xml.crypto.Data;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import com.gargoylesoftware.htmlunit.javascript.host.dom.Text;

import MoviePage.MoviePage;

public class MovieTestCase {

	WebDriver driver;
	WebDriverWait wait;
	MoviePage objMoviePage;
	JavascriptExecutor jse;
	Data data;
	Text text;

	@Test
	public void MovieTest() throws Exception {
		File file = new File(System.getProperty("user.dir") + "/DataSource/Datafile.properties");
		FileInputStream fileInput = new FileInputStream(file);
		Properties prop = new Properties();
		prop.load(fileInput);
		
		// Set the path of the Chrome driver.
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "//Drivers//chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();

		// Enter URL.
		driver.get(prop.getProperty("URL"));

		// wait for the Movie names to be displayed for clicking
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='movieSearchList']/div/a[1]")));
		

		for (int i = 1; i <= 4; i++) {
			String strXpath = "//*[@id=\"movieSearchList\"]/div/a[text()='"+prop.getProperty("Movie"+i+"Name")+"']";
			driver.findElement(By.xpath(strXpath)).click();
			wait.until(
					ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='leftContent']/div/div[1]/div[1]/a[1]")));

			// verify Movie Title
			Assert.assertTrue(driver.findElement(By.xpath("//*[@class='db-movietitle']")).getText()
					.equalsIgnoreCase(prop.getProperty("Movie" + i + "Title")));
			System.out.println("Test Case "+i+": Movie Title - PASSED");

			// verify Movie Overview
			Assert.assertTrue(driver.findElement(By.xpath("//*[@class='bsynopsis']")).getText()
					.equalsIgnoreCase(prop.getProperty("Movie" + i + "Overview")));
			System.out.println("Test Case "+i+": Movie Overview - PASSED");

			// verify Movie 2 Actors
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
					driver.findElement(By.xpath("//*[@class='castandcrew-cont'][1]")));
			Thread.sleep(500);
			Assert.assertTrue(driver.findElement(By.xpath("//*[@class='castandcrew-cont'][1]"))
					.getText().contains(prop.getProperty("Movie" + i + "Actor1")));
			Assert.assertTrue(driver.findElement(By.xpath("//*[@class='castandcrew-cont'][1]"))
					.getText().contains(prop.getProperty("Movie" + i + "Actor2")));
			System.out.println("Test Case "+i+": Movie 2 Actors - PASSED");

			// verify Movie Release Date
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
					driver.findElement(By.xpath("//*[@id='additional']/table/tbody/tr[2]/td[2]")));
			Thread.sleep(500);
			Assert.assertTrue(driver.findElement(By.xpath("//*[@id='additional']/table/tbody/tr[2]/td[2]")).getText()
					.equalsIgnoreCase(prop.getProperty("Movie" + i + "ReleaseDate")));
			System.out.println("Test Case "+i+": Movie Release Date - PASSED");

			// again go back to the main page
			driver.get("http://www.tcm.com/tcmdb/");
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='movieSearchList']/div/a[1]")));
		}

	}

	@AfterTest
	public void tearDown() {
		// closing all the browser windows
		driver.quit();
	}

}