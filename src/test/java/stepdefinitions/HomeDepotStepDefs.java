package stepdefinitions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.java.en.*;

public class HomeDepotStepDefs {

	WebDriver driver;

	@When("^I click on \"([^\"]*)\" link on the header below the search bar$")
	public void i_click_on_something_link_on_the_header_below_the_search_bar(String menuItem) throws Exception {
		System.out.println("click menu item");
		WebElement ele = driver.findElement(By.xpath("//global-header-categories//span[text()='" + menuItem + "']"));
		WaitForElementToBeClickable(ele);
		ele.click();
	}

	@Then("^I verify the \"([^\"]*)\" Pop up displays the selected store location$")
	public void i_verify_the_something_pop_up_displays_the_selected_store_location(String headerText) throws Exception {
		WebElement ele = driver.findElement(By.xpath("//*[@class='hdca-modal__header-text' and text()='" + headerText + "']"));
		WaitForElementToBeVisible(ele);
		ele.isDisplayed();
	}

	@And("^I click on \"([^\"]*)\" services option$")
	public void i_click_on_something_services_option(String option) throws Exception {
		WebElement ele = driver.findElement(By.xpath("//servicemaccordion-component//*[text()='" + option + "']//following-sibling::a"));
		WaitForElementToBeClickable(ele);
		ele.click();
	}

	@And("^I click on \"([^\"]*)\" option from dropdown$")
	public void i_click_on_something_option_from_dropdown(String serviceType) throws Exception {
		WebElement ele = driver.findElement(By.xpath("//*[@class='hdca-home-services__services-list-item-link' and text()='" + serviceType + "']"));
		WaitForElementToBeClickable(ele);
		WaitForElementToBeClickable(ele);
	    ele.click();
	}

	@And("^I click on \"([^\"]*)\" button$")
	public void i_click_on_something_button(String text) throws Exception {
		WebElement ele = driver.findElement(By.xpath("//*[normalize-space(text())='" + text + "']"));
		WaitForElementToBeClickable(ele);
		ele.click();
	}

	@And("^I Fill invalid email address in email address field$")
	public void i_fill_invalid_email_address_in_email_address_field() throws Exception {
		WebElement ele = driver.findElement(By.id("email"));
		WaitForElementToBeVisible(ele);
		ele.sendKeys("abc");
	}

	@And("^I validate the error message displayed as \"([^\"]*)\"$")
	public void i_validate_the_error_message_displayed_as_something(String message) throws Exception {
		WebElement ele = driver.findElement(By.xpath("//*text())='" + message + "']"));
		WaitForElementToBeVisible(ele);
		ele.isDisplayed();
	}

	private void WaitForElementToBeClickable(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	private void WaitForElementToBeVisible(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

}
