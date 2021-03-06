package pt.fccn.mobile.arquivo.tests.pagesearch;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pt.fccn.arquivo.selenium.Retry;
import pt.fccn.arquivo.selenium.WebDriverTestBaseParalell;

/**
 * 
 * @author pedro.gomes.fccn@gmail.com
 *
 */

public class PageAdvancedSearchWithPhraseOptionTest extends WebDriverTestBaseParalell {

	public PageAdvancedSearchWithPhraseOptionTest(String os, String version, String browser, String deviceName,
			String deviceOrientation) {
		super(os, version, browser, deviceName, deviceOrientation);
	}
	@Test
	@Retry
	public void testPageAdvancedSearchWithPhraseOption() throws Exception {
		run("Search FCCN term", () -> {
			driver.findElement(By.id("txtSearch")).clear();
			driver.findElement(By.id("txtSearch")).sendKeys("fccn");
			driver.findElement(By.xpath("//*[@id=\"buttonSearch\"]/button")).click();
		});
		
		run("Click on advanced search link to navigate to advanced search page",
				() -> waitUntilElementIsVisibleAndGet(By.id("advancedSearchButton")).click());
		
		appendError(() -> {
			assertEquals("Check if search words maintain fccn term", "fccn",
					driver.findElement(By.id("adv_and")).getAttribute("value"));
		});
		
		appendError("Insert the negation option on form field", () -> driver.findElement(By.id("adv_phr")).sendKeys("speedmeter"));
		
		appendError("Click on search on arquivo.pt button", () -> driver.findElement(By.id("btnSubmitBottom")).click());
		 
		appendError(() -> assertEquals("Verify if the - operator is on text box",
				"fccn \"speedmeter\"",
				driver.findElement(By.id("txtSearch")).getAttribute("value").trim()));

		assertThat("Verify if the term fccn is displayed on any search result",
				driver.findElement(By.xpath("//*[@id=\"resultados-lista\"]/ul/li[1]/div[1]/a/h2")).getText(), containsString("FCCN"));
		
		assertThat("Verify if the term fccn is displayed on any search result",
				driver.findElement(By.xpath("//*[@id=\"resultados-lista\"]/ul/li[1]/div[1]/a/h2")).getText(), containsString("Speedmeter FCCN"));
	}
}
