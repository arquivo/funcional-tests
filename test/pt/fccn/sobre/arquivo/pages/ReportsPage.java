package pt.fccn.sobre.arquivo.pages;

import java.io.FileNotFoundException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pt.fccn.arquivo.tests.util.AnalyzeURLs;

public class ReportsPage {

	WebDriver driver;
	private final int timeout = 50;
	
	public ReportsPage( WebDriver driver ) throws FileNotFoundException{
		this.driver = driver;
		
	}
	
	public boolean checkReportsLinks( String language ) {
		System.out.println( "[checkReportsLinks]" );
		String xpatha = "//*[@id=\"post-2977\"]/div/div/ul/li/a"; //get footer links
    	
		try{
    		List< WebElement > results = ( new WebDriverWait( driver, timeout ) )
	                .until( ExpectedConditions
	                        .visibilityOfAllElementsLocatedBy(
	                        		      By.xpath( xpatha )
	                        )
	        );
    		
    		System.out.println( "[Reports] results size = " + results.size( ) );
    		for( WebElement elem : results ) {
    			String url = elem.getAttribute( "href" );
    			int statusCode = AnalyzeURLs.linkExists( url );
    			if( !AnalyzeURLs.checkOk( statusCode ) ) {
    				System.out.println( "Failed: Url[" + url + "] status-code[" + statusCode + "]" );
    				return false;
    			}
    		}
    		
	    	return true;
    	} catch( NoSuchElementException e ){
            System.out.println( "Error in checkOPSite" );
            e.printStackTrace( );
            return false;
    	}
		
	}
	
}