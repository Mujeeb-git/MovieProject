package MoviePage;


import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class MoviePage {

	WebDriver driver;
	JavascriptExecutor jse;



			@FindBy(linkText = "To Have and Have Not (1944)")
			public WebElement movielink1;
			
			@FindBy(xpath ="//*[@id='movieSearchList']/div/a[1]")
			public WebElement movielink2;
	
			
			public void MovieSearch(String value) {
				
				if (value.equalsIgnoreCase("To Have and Have Not (1944)")) {
					jse.executeScript("arguments[0].scrollIntoView(true);", movielink1);
					movielink1.click();
									} 
				else if (value.equalsIgnoreCase("Fallen Angel (1945)")) {
					jse.executeScript("arguments[0].scrollIntoView(true);", movielink2);
					movielink2.click();
					
				} 
			}
			

}
