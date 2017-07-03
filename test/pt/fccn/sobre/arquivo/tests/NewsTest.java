package pt.fccn.sobre.arquivo.tests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Test;

import pt.fccn.saw.selenium.Retry;
import pt.fccn.saw.selenium.WebDriverTestBaseParalell;
import pt.fccn.sobre.arquivo.pages.IndexSobrePage;
import pt.fccn.sobre.arquivo.pages.NewsPage;
import pt.fccn.sobre.arquivo.pages.PublicationsPage;

public class NewsTest extends WebDriverTestBaseParalell {

	public NewsTest(String os, String version, String browser, String deviceName, String deviceOrientation) {
		super( os, version, browser, deviceName, deviceOrientation );
	}

	@Test
	@Retry
	public void newsTest( )  {
		System.out.print( "Running examples Test. \n");
		IndexSobrePage index = null;
		try{
			index = new IndexSobrePage( driver );
			NewsPage news = index.goToNewsPage( );
			System.out.println( "Going to the checkNewsLinks" );
		    
	        assertTrue("Failed The News Page Test in Portuguese", news.checkNewsLinks( "PT" ) );
	        
	        //TODO english version missing
	        
		} catch( IOException e ) {
			fail("IOException -> footerTest");
		}
		
    }
	
}
