package pt.fccn.mobile.arquivo.tests.urlsearch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Locale;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pt.fccn.arquivo.selenium.Retry;
import pt.fccn.arquivo.selenium.WebDriverTestBaseParalell;
import pt.fccn.mobile.arquivo.utils.BundleMessage;
import pt.fccn.mobile.arquivo.utils.DateUtils;
import pt.fccn.mobile.arquivo.utils.LocaleUtils;
import pt.fccn.mobile.arquivo.utils.LocalizedString;

/**
 *
 * @author Ivo Branco <ivo.branco@fccn.pt>
 *
 */
public class URLSearchListTest extends WebDriverTestBaseParalell {

	public URLSearchListTest(String os, String version, String browser, String deviceName, String deviceOrientation) {
		super(os, version, browser, deviceName, deviceOrientation);
	}

	@Test
	@Retry
	public void urlSearchListTestPT() {
		urlSearchListTest("fccn.pt", LocaleUtils.PORTUGUESE);
	}

	@Test
	@Retry
	public void urlSearchListTestEN() {
		urlSearchListTest("fccn.pt", LocaleUtils.ENGLISH);
	}

	private void urlSearchListTest(String url, Locale locale) {
		LocaleUtils.changeLanguageTo(this, locale);

		run("Search fccn.pt url", () -> {
			driver.findElement(By.id("txtSearch")).clear();
			driver.findElement(By.id("txtSearch")).sendKeys(url);
			driver.findElement(By.xpath("//*[@id=\"buttonSearch\"]/button")).click();
		});

		run("Change to list mode if not in it", () -> {
			WebElement resultsGridCurrentType = driver.findElementByXPath("//*[@id=\"layoutTV\"]/h4");

			LocalizedString listText = new LocalizedString().pt("Lista").en("List");

			if (!resultsGridCurrentType.getText().contains(listText.apply(locale))) {
				driver.findElementByXPath("//*[@id=\"layoutTV\"]/button").click();
			}
		});

		String versionLabel = new LocalizedString().pt("versão").en("version").apply(locale);

		run("Verify year", () -> {
			WebElement yearTableHeader = driver.findElementById("th_1996");
			assertNotNull("Verify if year table header exist", yearTableHeader);

			appendError("Year 1995 shouldn't be visible", () -> new WebDriverWait(driver, 20)
					.until(ExpectedConditions.invisibilityOfElementLocated(By.id("th_1995"))));

			WebElement yearWebElement = yearTableHeader.findElement(By.xpath(".//div[1]/h4"));
			assertNotNull("Year web element not found", yearWebElement);
			assertEquals("Verify year text is correct", "1996", yearWebElement.getText().trim());

			yearTableHeader.findElement(By.xpath(".//div[2]/h4"));

			WebElement numberOfVersionsWE = yearTableHeader.findElement(By.xpath(".//div[2]/h4"));
			assertNotNull("Number of versions", numberOfVersionsWE);
			assertEquals("Verify year number of versions", "1 " + versionLabel, numberOfVersionsWE.getText().trim());

			numberOfVersionsWE.click();
		});

		run("Verify month", () -> {
			WebElement numberOfMonthVersionsWE = waitUntilElementIsVisibleAndGet(By.id("month_1996_10"));
			assertNotNull("Number of month versions should be not null", numberOfMonthVersionsWE);
			assertEquals("Verify month number of versions", "1 " + versionLabel,
					numberOfMonthVersionsWE.getText().trim());
		});

		appendError("September shouldn't be visible", () -> new WebDriverWait(driver, 20)
				.until(ExpectedConditions.invisibilityOfElementLocated(By.id("month_1996_09"))));
		appendError("November shouldn't be visible", () -> new WebDriverWait(driver, 20)
				.until(ExpectedConditions.invisibilityOfElementLocated(By.id("month_1996_11"))));

		run("Open october", () -> {
			WebElement monthHeaderWE = driver.findElement(By.id("1996_10"));
			assertNotNull("Month should be not null", monthHeaderWE);
			monthHeaderWE.click();
		});

		appendError("Verify specific timetamp", () -> {
			String timestamp = "19961013145650";
			WebElement dayWE = waitUntilElementIsVisibleAndGet(By.id(timestamp));

			String expected = new BundleMessage().pt("{0,date,d} {0,date,MMMM} às {0,time,HH}:{0,time,mm}")
					.en("{0,date,d} {0,date,MMMM} at {0,time,HH}:{0,time,mm}")
					.format(locale, DateUtils.asDateFromTimestamp(timestamp));

			assertEquals(expected, dayWE.getText());
		});

	}

	public static void main(String[] args) {
		System.out.println(DateUtils.asDateFromTimestamp("19961013145650"));
	}
}
