package pt.fccn.mobile.arquivo.tests.urlsearch;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import pt.fccn.arquivo.selenium.Retry;
import pt.fccn.arquivo.selenium.WebDriverTestBaseParalell;
import pt.fccn.mobile.arquivo.utils.LocaleUtils;

/**
 *
 * @author Ivo Branco <ivo.branco@fccn.pt>
 *
 */
public class URLSearchTableTest extends WebDriverTestBaseParalell {

	public URLSearchTableTest(String os, String version, String browser, String deviceName, String deviceOrientation) {
		super(os, version, browser, deviceName, deviceOrientation);
	}

	@Test
	@Retry
	public void urlSearchTableTestPT() {
		LocaleUtils.changeLanguageToPT(this);
		urlSearchTableTest("fccn.pt", "Tabela", "13 Out");
	}

	@Test
	@Retry
	public void urlSearchTableTestEN() {
		LocaleUtils.changeLanguageToEN(this);
		urlSearchTableTest("fccn.pt", "Tabela", "13 Oct");
	}

	private void urlSearchTableTest(String url, String tableText, String firstResultText) {

		run("Search fccn.pt url", () -> {
			driver.findElement(By.id("txtSearch")).clear();
			driver.findElement(By.id("txtSearch")).sendKeys(url);
			driver.findElement(By.xpath("//*[@id=\"buttonSearch\"]/button")).click();
		});

		run("Change to Table mode if not in it", () -> {
			WebElement resultsGridCurrentType = driver.findElementByXPath("//*[@id=\"layoutTV\"]/h4");
			if (!resultsGridCurrentType.getText().contains(tableText)) {
				driver.findElementByXPath("//*[@id=\"layoutTV\"]/button").click();
			}
		});

		run("Check if first version match", () -> {
			waitUntilElementIsVisibleAndGet(By.id("conteudo-versoes"));
			List<WebElement> firstRow = driver.findElementsByXPath("//*[@id=\"1\" and @class=\"trTV\"]/td");
			WebElement firstCell = firstRow.get(0);
			WebElement anchor = firstCell.findElement(By.xpath(".//a"));
			String visibleDate = anchor.getText();
			assertEquals("Check if first version match", firstResultText, visibleDate);
		});
	}

}