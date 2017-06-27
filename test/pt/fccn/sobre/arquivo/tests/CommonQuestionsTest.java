package pt.fccn.sobre.arquivo.tests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

import pt.fccn.arquivo.pages.AdvancedPage;
import pt.fccn.arquivo.pages.IndexPage;
import pt.fccn.arquivo.pages.ReplayPage;
import pt.fccn.saw.selenium.Retry;
import pt.fccn.saw.selenium.WebDriverTestBaseParalell;
import pt.fccn.sobre.arquivo.pages.CommonQuestionsPage;
import pt.fccn.sobre.arquivo.pages.IndexSobrePage;

public class CommonQuestionsTest extends WebDriverTestBaseParalell {

	public CommonQuestionsTest( String os, String version, String browser, String deviceName, String deviceOrientation ) {
		super( os, version, browser, deviceName, deviceOrientation );
	}
	
	boolean isPreProd = true;
	
	@Test
	@Retry
	public void commonQuestionsTest( )  {
		System.out.print( "Running Common Questions Test. \n");
		IndexSobrePage index = null;
		try{
			 index = new IndexSobrePage( driver );
		} catch( IOException e ) {
			fail("IOException -> IndexSobrePage");
		}
		
        System.out.println( "Going to the CommonQuestions" );
        try{
	        CommonQuestionsPage commonQuestions = index.goToCommonQuestionsPage( );
	        assertTrue("Failed The Common Question in Portuguese", commonQuestions.inspectQuestions( "PT" ) );
			//assertTrue("Failed The Common Question in English", commonQuestions.inspectQuestions( "EN" ) );
        } catch( FileNotFoundException e ) {
			fail("FileNotFoundException -> goToCommonQuestionsPage");
		}
    }

}