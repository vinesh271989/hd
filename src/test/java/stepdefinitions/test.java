package stepdefinitions;

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

public class test {

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

	@FindBy(how = How.XPATH, using = "//*[normalize-space(text())='Sort by']//following-sibling::div//span")
	private WebElement sortDropdown;

	@FindBy(how = How.XPATH, using = "//a[text()='Top Sellers']")
	private WebElement topSeller;

	@FindBy(how = How.XPATH, using = "//*[@id='stock']//ancestor::label//span[@class='hdca-form-field__label-text']")
	private WebElement verbiage;

	@FindBy(how = How.XPATH, using = "//*[contains(@class,'store-info-name')]")
	private WebElement storeName;

	@FindBy(how = How.XPATH, using = "//*[@id='breadcrumbs']//li")
	private List<WebElement> breadCrumbs;

	public test() {
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

	public void verifyBreadCrumb(String url, String expectedDropdownValue, String expectedBreadcrumbText) {

		try {
			// Maximize browser
			driver.manage().deleteAllCookies();
			driver.manage().window().maximize();

			// Navigate to home page
			driver.get(url);

			// Click on sort by drop-down
			waitForPageToLoad();
			WaitForElementToBeClickable(sortDropdown);
			sortDropdown.click();

			// Select Top Sellers from the drop down
			WaitForElementToBeClickable(topSeller);
			topSeller.click();

			// Verify the drop down value
			waitForPageToLoad();
			WaitForElementToBeVisible(sortDropdown);
			String actualDropdownValue = sortDropdown.getText();
			Assert.assertEquals(expectedDropdownValue, actualDropdownValue);

			// Verify the verbiage
			String actualVerbiageText = verbiage.getText();
			String storeText = storeName.getText();
			String expectedVerbiageText = "In Stock Today at " + storeText;
			Assert.assertEquals(expectedVerbiageText, actualVerbiageText);

			// Verify bread crumb text and title
			int size = breadCrumbs.size();
			String lowestBreadCrumbText = breadCrumbs.get(size - 1).getText();
			Assert.assertEquals(expectedBreadcrumbText, lowestBreadCrumbText);
			boolean titleTextValue = driver.getTitle().contains(expectedBreadcrumbText);
			Assert.assertTrue(titleTextValue);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) throws Exception {
		String url = "https://www.homedepot.ca/en/home/categories/bath/bathroom-vanities/vanity-tops.html";
		String expectedDropdownValue = "Top Sellers";
		String expectedBreadcrumbText = "Vanity Tops";
		
		test bcs = new test();
		bcs.verifyBreadCrumb(url,expectedDropdownValue,expectedBreadcrumbText);
	}
}
