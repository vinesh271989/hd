package stepdefinitions;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import junit.framework.Assert;

public class CartSolution {

	private WebDriver driver;
	private WebDriverWait wait;

	@FindBy(how = How.XPATH, using = "//*[@title='Close dialog']//acl-icon")
	private List<WebElement> modal;

	@FindBy(how = How.XPATH, using = "//*[text()='Shop by Department']//ancestor::a//acl-icon")
	private WebElement shopbydepartment;

	@FindBy(how = How.LINK_TEXT, using = "Bath")
	private WebElement bath;

	@FindBy(how = How.LINK_TEXT, using = "Vanity Tops")
	private WebElement vanityTops;

	@FindBy(how = How.XPATH, using = "//span[text()='Add to Cart']")
	private WebElement addToCartButton;
	
	@FindBy(how = How.ID, using = "topOfPanelRef")
	private WebElement addToCartPanel;
	
	@FindBy(how = How.XPATH, using = "//*[@id='topOfPanelRef']//*[@symbol='check']//following-sibling::span/span")
	private WebElement message;
	
	@FindBy(how = How.XPATH, using = "//*[@class='acl-container']//span[contains(text(),'$') and contains(@class,'large')]")
	private WebElement itemPrice;
	
	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Subtotal')]//following-sibling::div[contains(text(),'$')]")
	private WebElement subTotal;
	
	@FindBy(how = How.XPATH, using = "//span[text()='View Cart']")
	private WebElement viewCartButton;
	
	@FindBy(how = How.XPATH, using = "//*[@class='hdca-cart__product-pricing-qty']//input[@class='hdca-input']")
	private WebElement cartQuanity;
	
	@FindBy(how = How.XPATH, using = "//*[contains(@class,'product-pricing-total')]")
	private WebElement price;
	
	@FindBy(how = How.ID, using = "btnCheckoutNowOrderSummary")
	private WebElement checkoutNow;
	
		
	public CartSolution() {
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\drivers\\chromedriver.exe");
		this.driver = new ChromeDriver();
		PageFactory.initElements(driver, this);
		this.wait = new WebDriverWait(this.driver, 30);
	}

	public void WaitForElementToBeClickable(WebElement element) throws Exception {
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public void WaitForElementToBeVisible(WebElement element) throws Exception {
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public void mouseHover(WebElement element) throws Exception {
		Actions actions = new Actions(driver);
		actions.moveToElement(element).perform();
	}

	public void waitForPageToLoad() throws Exception {
		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};
		wait.until(pageLoadCondition);
	}

	public void closeModal(List<WebElement> element) throws Exception {
		if (element.size() > 0) {
			WaitForElementToBeClickable(element.get(0));
			element.get(0).click();
		}
	}

	public void verifyCart(String url, String quantity, String cartMessage) {

		try {
			// Maximize browser
			driver.manage().window().maximize();

			// Navigate to home page
			driver.get(url);

			// Close the store dialog box if it appears on launching the home page
			waitForPageToLoad();
			closeModal(modal);

			// Mouse hover on shop by department
			WaitForElementToBeVisible(shopbydepartment);
			mouseHover(shopbydepartment);

			// Mouse hover on bath
			WaitForElementToBeVisible(bath);
			mouseHover(bath);

			// Click on vanity tops
			WaitForElementToBeClickable(vanityTops);
			vanityTops.click();

			// Click on add to cart button
			WaitForElementToBeClickable(addToCartButton);
			addToCartButton.click();
			
			// Verify Add to cart panel is opened to the right
	    	WaitForElementToBeVisible(addToCartPanel);
	    	Assert.assertTrue(addToCartPanel.isDisplayed());
	    	
	    	// Item added to the cart validation
	    	Assert.assertTrue(message.getText().equals(cartMessage));
	    	
	    	// Verify the order sub total price matches the price of the product
			String itemPriceText  = itemPrice.getText().trim();
			String subTotalText = subTotal.getText().trim();	
			Assert.assertEquals(itemPriceText, subTotalText);
			
			// Click on view cart button
			WaitForElementToBeClickable(viewCartButton);
			viewCartButton.click();  
			
			// Verify the user is on the cart page
			waitForPageToLoad();
	    	Assert.assertTrue(driver.getTitle().contains("Cart"));
	    	
	    	// Store the item price to compare later after adding multiple products
	    	String totalPrice = price.getText();
	    	totalPrice = totalPrice.replaceAll("[$,]", "");
	    	BigDecimal finalPrice = new BigDecimal(totalPrice);
	    	
	    	// Update the cart quantity
	    	cartQuanity.click();
	    	cartQuanity.clear();
	    	cartQuanity.sendKeys(quantity);
	    	
	    	//Move the focus out of the quantity box
	    	new Actions(driver).moveToElement(price).perform();
	    	
	    	// Calculate the expected final amount
	    	MathContext m = new MathContext(quantity);  
	    	BigDecimal expectedAmount = finalPrice.multiply(finalPrice, m);
	    	
	    	// Retrieve the actual total price from cart
	    	WaitForElementToBeVisible(checkoutNow);
	    	WaitForElementToBeClickable(checkoutNow);
	    	totalPrice = price.getText();
	    	totalPrice = totalPrice.replaceAll("[$,]", "");
	    	BigDecimal actualAmount = new BigDecimal(totalPrice);
	    	
	    	// Verify the expected and actual total prices after the cart is updated
	    	Assert.assertTrue(actualAmount.equals(expectedAmount));

			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) throws Exception {
		String url = "https://www.homedepot.ca";
		String cartMessage = "1 item has been added to your cart";
		String quantity = "4";
		
		CartSolution cs = new CartSolution();
		cs.verifyCart(url, quantity, cartMessage);
		

	}
}
