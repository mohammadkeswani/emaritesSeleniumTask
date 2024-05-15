import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class emaritesseleniumTask extends Parametars {
	// before Test navigate to emirates website and maximize the screen 
		@BeforeTest
		public void BeforeTest(){

			

			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3000));
			driver.get("https://www.emirates.com/jo/english/"); // navigate to Url
			driver.manage().window().maximize();
			// accept alear message 
			driver.findElement(By.id("onetrust-accept-btn-handler")).click();
		}

		// book a ticket from Riyadh to Amman added 2 adult and 1 child on business Class and search 
		@Test(priority = 1)
		public void book_ticket() {
			WebDriverWait wait = new WebDriverWait(driver , Duration.ofSeconds(5));

			driver.findElement(By.xpath("(//button[@name='clear Departure airport'])[1]")).click();
			// select from Riyadh

			driver.findElement(By.cssSelector(".js-field-input.field__input.js-dropdown-open.field__input--active"))
					.sendKeys(fromCun);  

			driver.findElement(By.xpath(
					"//div[@aria-label='Riyadh, Saudi Arabia King Khalid International Airport RUH Operated by Emirates']"))
					.click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
					"(//div[@aria-label='Amman, Jordan Queen Alia International Airport AMM Operated by Emirates'])[3]")));
			driver.findElement(By.xpath(
					"(//div[@aria-label='Amman, Jordan Queen Alia International Airport AMM Operated by Emirates'])[3]")) // select to Amman
					.click(); 
			
			for (int i = 0; i < 2; i++) { // click next 2 times on calendar to find "august"
				driver.findElement(By.xpath(
						"(//button[@class='ek-datepicker__button ek-datepicker__button--next icon-arrow-right'][normalize-space()='Next Month'])[1]"))
						.click();
			}
			driver.findElement(By.cssSelector("td[data-string$='1872024']")).click(); // choose arrival
			driver.findElement(By.cssSelector("td[data-string$='3172024']")).click(); // choose Returning date
			// add 2 adults
			driver.findElement(By.cssSelector("div[class='increment-field js-increment-field increment-field--subtract-disabled'] button:nth-child(1)")).click(); 
			// select 1 child
			driver.findElement(By.xpath("(//button[contains(@type,'button')])[11]")).click(); 

			// choose Class
			driver.findElement(By.cssSelector("#field-search-flight-class")).click();
			driver.findElement(By.cssSelector("a[data-dropdown-display$='Business Class']")).click(); // select business Class
			driver.findElement(By.xpath("//form[contains(@method,'post')]//button[contains(@type,'submit')]")).click(); // Search
			
		}
		//validate site message and lowest price 
		@Test(priority = 2)
		public void assertSite_lowestPrice() {
			WebDriverWait wait = new WebDriverWait(driver , Duration.ofSeconds(5));
			//wait the element 5 second to appear before exception
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ts-rmsg__hdrcnt")));  
			// get text and split it to get the actual message
			String acutalSite =  driver.findElement(By.cssSelector(".ts-rmsg__hdrcnt")).getText().split("our")[1].trim();
			// validate if the actual site == expected site
			softassert.assertEquals(acutalSite, expectedSite , "site has been return passed");  
		
			List<WebElement> priceElements = driver.findElements(By.className("ts-fbr-option__price"));

	        // Extract prices as doubles
	        List<Double> prices = new ArrayList<>();
	        for (WebElement element : priceElements) {
	            String priceText = element.getText().replaceAll(",", ""); // Remove non-numeric characters
	            try {
	                double price = Double.parseDouble(priceText);
	                prices.add(price);
	            } catch (NumberFormatException e) {
	                // Handle the case where the price cannot be parsed as a double
	                System.out.println("Skipping invalid price: " + priceText);
	            }
	        }

	        // Find the lowest price
	        double lowestPrice = Collections.min(prices);

			
			softassert.assertAll();
			
		}
		@Test(priority = 3)
		// Select a flight and select  flexibility
		public void selectFlight() {
			driver.findElement(By.id("option-0-1-0")).click();
			driver.findElement(By.cssSelector("a[id$='ctl00_c_FlightResultOutBound_rptBoundResult_ctl00_ctl05_ctl66_ancSelect']")).click();
			driver.findElement(By.id("option-1-1-0")).click();
			driver.findElement(By.cssSelector("a[id$='ctl00_c_FlightResultInBound_rptBoundResult_ctl00_ctl05_ctl66_ancSelect']")).click();
			// click on Continue to Passengers
			driver.findElement(By.xpath("//a[.='Continue to Passengers']")).click();	
			
		}
			// check if passenger tab opened and Enter valid information for passengers
			@Test(priority = 4)
			public void passengersTab() {
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
				//check if the login button displayed
				boolean actualMoveToPassengerTab = driver.findElement(By.id("btnLoginpassanger")).isDisplayed(); 
				softassert.assertEquals(actualMoveToPassengerTab , expectedMovedToPassenger);
				//fill data for first passenger
				driver.findElement(By.xpath("//input[@id='ctl00_c_ctPax1_txtPS_TITLED']")).sendKeys(title);
				driver.findElement(By.name("ctl00$c$ctPax1$txtFirstName")).sendKeys(firstPassengerFN);
				driver.findElement(By.name("ctl00$c$ctPax1$txtLastName")).sendKeys(firstPassengerLN);
				driver.findElement(By.name("ctl00$c$ctPax1$txtDay")).sendKeys(firstPassengerDay + Keys.TAB);
				driver.findElement(By.xpath("//input[@id='ctl00_c_ctPax1_txtMonth']")).sendKeys(firstPassengerMon + Keys.TAB);
				driver.findElement(By.name("ctl00$c$ctPax1$txtYear")).sendKeys(firstPassengerYear + Keys.TAB);
				driver.findElement(By.cssSelector("#ctl00_c_ctPax1_aPassVerfCollapse")).click();
				//fill data for second passenger
				driver.findElement(By.xpath("//input[@id='ctl00_c_ctPax2_txtPS_TITLED']")).sendKeys(title);
				driver.findElement(By.name("ctl00$c$ctPax2$txtFirstName")).sendKeys(secPassengerFN);
				driver.findElement(By.name("ctl00$c$ctPax2$txtLastName")).sendKeys(secPassengerLN);
				driver.findElement(By.name("ctl00$c$ctPax2$txtDay")).sendKeys(secPassengerDay + Keys.TAB);
				driver.findElement(By.xpath("//input[@id='ctl00_c_ctPax2_txtMonth']")).sendKeys(secPassengerMon  + Keys.TAB);
				driver.findElement(By.name("ctl00$c$ctPax2$txtYear")).sendKeys(secPassengerYear +  Keys.TAB);
				driver.findElement(By.cssSelector("#ctl00_c_ctPax2_aPassVerfCollapse")).click();
				//fill data for second passenger
				driver.findElement(By.xpath("//input[@id='ctl00_c_ctPax3_txtPS_TITLED']")).sendKeys(title);
				driver.findElement(By.name("ctl00$c$ctPax3$txtFirstName")).sendKeys(thirdPassengerFN);
				driver.findElement(By.name("ctl00$c$ctPax3$txtLastName")).sendKeys(thirdPassengerLN);
				driver.findElement(By.name("ctl00$c$ctPax3$txtDay")).sendKeys(thirdPassengerDay + Keys.TAB);
				driver.findElement(By.xpath("//input[@id='ctl00_c_ctPax3_txtMonth']")).sendKeys(thirdPassengerMon + Keys.TAB);
				driver.findElement(By.name("ctl00$c$ctPax3$txtYear")).sendKeys(thirdPassengerYear + Keys.TAB);
				driver.findElement(By.cssSelector("#ctl00_c_ctPax3_aPassVerfCollapse")).click();
				//contact information 
				driver.findElement(By.xpath("//input[@aria-describedby='ek-error-txtCountryCode1 ek-tooltip-desc-txtCountryCode1']")).sendKeys(countryCode);
				driver.findElement(By.xpath("//input[@aria-describedby='ek-error-txtContactNum1 ek-tooltip-desc-txtContactNum1']")).sendKeys(mobileNumber + Keys.TAB);
				driver.findElement(By.xpath("//input[@id='ctl00_c_Cont_txtEmail']")).sendKeys(email);
				
				softassert.assertAll();
		}
			// Press on the “View Summary” button, assert the price from the popup, and print it to the console.
		@Test(priority = 5)	
		public void  checkPrice() {
			//Press on the “View Summary” button
			driver.findElement(By.xpath("//a[@data-id='popup_cta']")).click();
			//get cost
			String cost = driver.findElement(By.cssSelector("strong[class$='sbox-totalfare']")).getText();
			//get total price
			String totalPrice = driver.findElement(By.cssSelector("span[class$='ts-sc__grnd-amount--text']")).getText();
			softassert.assertEquals(cost, totalPrice);
			System.out.println(totalPrice);
		}
		

}
