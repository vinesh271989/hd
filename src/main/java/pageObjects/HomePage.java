package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
	WebDriver driver;

	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	 @FindBy(how = How.ID, using = "email") 
	 private WebElement email;
	 
	
	public void navigateTo_HomePage() {
		driver.navigate().to("https://www.homedepot.ca/en/");
	}
	
	public void clickOnAMenuItemOnHeader(String menuItem)
	{
		System.out.print("click menu item");
		JavascriptExecutor js = (JavascriptExecutor)driver;
		WebElement button = driver.findElement(By.xpath("//global-header-categories//span[text()='" + menuItem + "']"));
		js.executeScript("arguments[0].click();", button);
	}
	
	public void clickOnAServiceOption(String option) {
		driver.findElement(By.xpath("//servicemaccordion-component//*[text()='" + option + "']//following-sibling::a")).click();
	}
	
	public void clickOnAServiceType(String serviceType) {
		driver.findElement(By.xpath("//*[@class='hdca-home-services__services-list-item-link' and text()='" +  serviceType + "']")).click();
	}
	
	public void clickOnButton(String text) {
		driver.findElement(By.xpath("//*[normalize-space(text())='" + text + "']")).click();
	}
	
	public void verifyConfirmLocationPopUp(String headerText) {
		driver.findElement(By.xpath("//*[@class='hdca-modal__header-text' and text()='" + headerText + "']")).isDisplayed();
	}
	
	public void enterEmailAdrress(String emailAddress) {
		this.email.sendKeys(emailAddress);
	}
	
	public void validateEmailAdrressMessage(String message) {
		driver.findElement(By.xpath("//*text())='" + message + "']")).isDisplayed();
	}
	

}








