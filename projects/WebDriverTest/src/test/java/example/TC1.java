package example;		

import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;		
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;		
import org.testng.annotations.BeforeTest;	
import org.testng.annotations.AfterTest;	

public class TC1 {	

	    
	    private WebDriver driver;	
	    
	//Bardzo fajnie ze wydzieliles tworzenie polaczenia do Firefox do BeforeTest    
	@BeforeTest
		public void TestSetup() {	

			//Natomiast niestety ten test nie zadziala, u tych osob ktore nie maja sterownik w sciezce "/usr/local/bin/geckodriver". 
			//Lepszym tutaj rozwiazaniem byloby wrzucenie do Repo pliku z sterownikim i podpiecie sie do tego folderu
			// String path = System.getProperty("user.dir") + Paths.get("/src/resources/geckodriver");
			// System.setProperty("webdriver.gecko.driver", path);
			System.setProperty("webdriver.gecko.driver","/usr/local/bin/geckodriver");
			driver = new FirefoxDriver();     
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.get("https://myWebApplication");

		}	
	    
		@Test(description="Verify if user with vlaid login and password is able to login")			
		public void  SuccesfullLogin() {	
			     
	        LoginToWebApplication();
	        VerifyPageContent();
		}
			
	//Dobrze ze nie zapomniales zamknac polaczenie do Firefox. Rownie dobrze ze jest to zrobione w @AfterTest	
	@AfterTest
		public void TestTeardown() {
			driver.quit();			
		}		
		
		public void LoginToWebApplication() {
			
			String usernameInput = "username";
			String passwordInput = "password";

			//Przegladnij sobie prosze wzorzec Page Object Pattern/ Page Object Model. Wazna zasada jest nie uzywanie "driver-a" w pliku gdzie wykonujemy test
			WebElement loginLocator = driver.findElement(By.name(usernameInput)); 
			loginLocator.sendKeys("user_mail");
			WebElement passwordLocator = driver.findElement(By.name(passwordInput));
			passwordLocator.sendKeys("user_password");
			WebElement loginButton = driver.findElement(By.xpath("//input[@type='submit']"));
			loginButton.click();
		}
		
		public void VerifyPageContent() {
			 
			//Uzywanie xPath nie jest najbardziej optymalnym rozwiązaniem. Przegladnij sobie prosze https://saucelabs.com/resources/articles/selenium-tips-css-selectors 
			String xpathToMainNaviItem = "//a[@ui-sref='app.main']";
			 WebElement naviItem = driver.findElement(By.xpath(xpathToMainNaviItem));
			 Assert.assertTrue(naviItem.isDisplayed());
			 Assert.assertTrue(naviItem.isEnabled());
		}
		
		

		
}
