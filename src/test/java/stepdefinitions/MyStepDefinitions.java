package stepdefinitions;

import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.Given;
import cucumber.api.junit.Cucumber;
import junit.framework.Assert;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@RunWith(Cucumber.class)
public class MyStepDefinitions {

	WebDriver driver;

	@Given("^As a user, I am on Homedepot.ca home page$")
	public void as_a_user_i_am_on_homedepotca_home_page() throws Exception {
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.navigate().to("https://www.homedepot.ca/en/");
	}

	@When("^I hover on \"([^\"]*)\" And then hover on \"([^\"]*)\"$")
	public void i_hover_on_something_and_then_hover_on_something(String strArg1, String strArg2) throws Exception {
		List<WebElement> modal = driver.findElements(By.xpath("//*[@title='Close dialog']//acl-icon"));
		closeModal(modal);
		
		WebElement shopbydepartment = driver.findElement(By.xpath("//*[text()='Shop by Department']//ancestor::a//acl-icon"));
		WebElement bath = driver.findElement(By.linkText("Bath"));
		
		WaitForElementToBeVisible(shopbydepartment);
		mouseHover(shopbydepartment);
		
		WaitForElementToBeVisible(bath);
		mouseHover(bath);
	}
	
	@When("^I hover on \"([^\"]*)\" And \"([^\"]*)\"$")
    public void i_hover_on_something_and_something(String strArg1, String strArg2) throws Exception {
		i_hover_on_something_and_then_hover_on_something(strArg1, strArg2);
    }
	

	@When("^I click on \"([^\"]*)\"$")
	public void i_click_on_something(String strArg1) throws Exception {
		WebElement vanityTops = driver.findElement(By.linkText("Vanity Tops"));
		WaitForElementToBeClickable(vanityTops);
		vanityTops.click();
	}

	@When("^I click on \"([^\"]*)\" dropdown$")
	public void i_click_on_something_dropdown(String strArg1) throws Exception {
		waitForPageToLoad();
		WebElement sortDropdown = driver.findElement(By.xpath("//*[normalize-space(text())='Sort by']//following-sibling::div//span"));
		WaitForElementToBeClickable(sortDropdown);
		sortDropdown.click();
	}

	@When("^Select \"([^\"]*)\" option$")
	public void select_something_option(String strArg1) throws Exception {
		WebElement topSeller = driver.findElement(By.xpath("//a[text()='" + strArg1 + "']"));
		WaitForElementToBeClickable(topSeller);
		topSeller.click();
	}
	
    @When("^I click on \"([^\"]*)\" for any product displayed$")
    public void i_click_on_something_for_any_product_displayed(String strArg1) throws Exception {
		WebElement addToCartButton = driver.findElement(By.xpath("//span[text()='" + strArg1 + "']"));
		WaitForElementToBeClickable(addToCartButton);
		addToCartButton.click();
    }
    
	@Then("^I verify \"([^\"]*)\" is displayed in place of \"([^\"]*)\"$")
	public void i_verify_something_is_displayed_in_place_of_something(String strArg1, String strArg2) throws Exception {
		WebElement dropdownOption = driver.findElement(By.xpath("//*[normalize-space(text())='Sort by']//following-sibling::div//span"));
		waitForPageToLoad();
		WaitForElementToBeVisible(dropdownOption);
		String actualValue = dropdownOption.getText();
		Assert.assertEquals("Top Sellers", actualValue);
	}

	@Then("^I verify the verbiage \"([^\"]*)\" and Selected Store Name is displayed$")
	public void i_verify_the_verbiage_something_and_selected_store_name_is_displayed(String strArg1) throws Exception {
		WebElement verbiage = driver.findElement(By.xpath("//*[@id='stock']//ancestor::label//span[@class='hdca-form-field__label-text']"));
		String actualText = verbiage.getText();
		WebElement storeName = driver.findElement(By.xpath("//*[contains(@class,'store-info-name')]"));
		String storeText = storeName.getText();	
		String expectedText = "In Stock Today at " + storeText;
		Assert.assertEquals(expectedText, actualText);
	}
	
    @Then("^I verify the Add to cart Panel slides from right$")
    public void i_verify_the_add_to_cart_panel_slides_from_right() throws Exception {
    	WebElement addToCartPanel = driver.findElement(By.id("topOfPanelRef"));
    	WaitForElementToBeVisible(addToCartPanel);
    	Assert.assertTrue(addToCartPanel.isDisplayed());
    }

    @Then("^I verify the price is multiplied accordingly$")
    public void i_verify_the_price_is_multiplied_accordingly() throws Exception {
        
    }

    @Then("^I verify the message \"([^\"]*)\" displayed on the Panel$")
    public void i_verify_the_message_something_displayed_on_the_panel(String strArg1) throws Exception {
    	WebElement message = driver.findElement(By.xpath("//*[@id='topOfPanelRef']//span[text()='" + strArg1 + "']"));
    	Assert.assertTrue(message.isDisplayed());
    }

    @Then("^I verify the Order Subtotal Price matches the Price of the product$")
    public void i_verify_the_order_subtotal_price_matches_the_price_of_the_product() throws Exception {
    	WebElement itemPrice = driver.findElement(By.xpath("//*[@class='acl-container']//span[contains(text(),'$') and contains(@class,'large')]"));
		String itemPriceText  = itemPrice.getText().trim();
		WebElement subTotal = driver.findElement(By.xpath("//*[contains(text(),'Subtotal')]//following-sibling::div[contains(text(),'$')]"));
		String subTotalText = subTotal.getText().trim();	
		Assert.assertEquals(itemPriceText, subTotalText);
    }

    @Then("^I click on \"([^\"]*)\" button on the panel$")
    public void i_click_on_something_button_on_the_panel(String strArg1) throws Exception {
		WebElement viewCartButton = driver.findElement(By.xpath("//span[text()='" + strArg1 + "']"));
		WaitForElementToBeClickable(viewCartButton);
		viewCartButton.click();    	
    }

    @Then("^I verify I am on the Cart Page and Change the Quantity of the product to (\\d+)$")
    public void i_verify_i_am_on_the_cart_page_and_change_the_quantity_of_the_product_to_4(String quantity) throws Exception {
    	waitForPageToLoad();
    	Assert.assertTrue(driver.getTitle().contains("Cart"));
    	
    	WebElement price = driver.findElement(By.xpath("//*[contains(@class,'product-pricing-total')]"));
    	String totalPrice = price.getText();
    	totalPrice = totalPrice.replaceAll("[$,]", "");
    	BigDecimal finalPrice = new BigDecimal(totalPrice);
    	
    	WebElement element = driver.findElement(By.xpath("//*[@class='hdca-cart__product-pricing-qty']//input[@class='hdca-input']"));
    	element.click();
    	element.clear();
    	element.sendKeys(quantity);
    	new Actions(driver).moveToElement(price).perform();
    	
    	MathContext m = new MathContext(quantity);  
    	BigDecimal expectedAmount = finalPrice.multiply(finalPrice, m);
    	totalPrice = price.getText();
    	totalPrice = totalPrice.replaceAll("[$,]", "");
    	BigDecimal actualAmount = new BigDecimal(totalPrice);
    	Assert.assertTrue(actualAmount.equals(expectedAmount));
    }

	@Then("^I verify the lowest level in the breadcrumb matches the title on the current page as \"([^\"]*)\"$")
	public void i_verify_the_lowest_level_in_the_breadcrumb_matches_the_title_on_the_current_page_as_something(String expectedText) throws Exception {
		List<WebElement> breadCrumbs = driver.findElements(By.xpath("//*[@id='breadcrumbs']//li"));
		int size = breadCrumbs.size();
		String lowestBreadCrumbText = breadCrumbs.get(size-1).getText();
		Assert.assertEquals(expectedText, lowestBreadCrumbText);
		
		String title = driver.getTitle();
		boolean titleTextValue = title.contains(expectedText);
		Assert.assertTrue(titleTextValue);
	}

	public void WaitForElementToBeClickable(WebElement element) throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public void WaitForElementToBeVisible(WebElement element) throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public void mouseHover(WebElement element) throws Exception {
		Actions actions = new Actions(driver);
		actions.moveToElement(element).perform();
	}

	public void closeModal(List<WebElement> element) throws Exception {
		if (element.size() > 0) {
			WaitForElementToBeClickable(element.get(0));
			element.get(0).click();
		}
	}

	public void waitForPageToLoad() {
		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(pageLoadCondition);
	}

}
