package example;		
import org.testng.annotations.Test;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;		
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;		
import org.testng.annotations.BeforeTest;	
import org.testng.annotations.AfterTest;	

public class TC2 {	

	    
	    private WebDriver driver;	
	    
	    @BeforeTest
		public void TestSetup() {	

			System.setProperty("webdriver.gecko.driver","/usr/local/bin/geckodriver");
			driver = new FirefoxDriver();     
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.get("http://store.demoqa.com");
		}	
	    
		@Test(description="Verify if user sees all required product categories")			
		public void  UserSeesListOfProductCategories() throws InterruptedException {	
			
			    VerifyIfPageIsOpen();
			    VerfiyProductCategories();
		}
			
		@AfterTest
		public void TestTeardown() {
			driver.quit();			
		}		
		
		public void VerifyIfPageIsOpen() {
	        System.out.println(driver.getTitle());
	        Assert.assertEquals("ONLINE STORE | Toolsqa Dummy Test site", driver.getTitle());
	        Assert.assertTrue(driver.findElement(By.id("main-nav")).isDisplayed());
		}
		
		public void VerfiyProductCategories() throws InterruptedException {
			
			WebElement productCategoryMenu = driver.findElement(By.linkText("Product Category"));
			Assert.assertTrue(productCategoryMenu.isDisplayed());
			FocusMouseOnWebElement(productCategoryMenu);
			List <WebElement> displayedCategories = driver.findElements(By.xpath("//li[@id='menu-item-33']/ul/li/a"));
			HashSet<String> expectedCategories = getExpectedCategories();
			for (WebElement category : displayedCategories) {
				Assert.assertTrue(category.isEnabled());	
				Assert.assertTrue(category.isDisplayed());
				Assert.assertTrue(expectedCategories.contains(category.getAttribute("innerText")));	
			}
		}

		public void FocusMouseOnWebElement(WebElement webElement) {
			Actions action = new Actions(driver);
			action.moveToElement(webElement).perform();
		}

		public HashSet<String> getExpectedCategories() {
			HashSet<String> hset = new HashSet<String>();
			hset.add("Accessories");
			hset.add("iMacs");
			hset.add("iPads");
			hset.add("iPhones");
			hset.add("iPods");
			hset.add("MacBooks");			
			return hset;
		}

}