package com.largepixels.webscrapeexamples.scrape_to_mysql;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.util.List;

/**
 * This program was used to connect to a website, browse through a javascript enabled menu and read items
 */

public class Scraper {

    WebClient webClient = null;
    HtmlPage page = null;
	String url = "http://www.example.com/1";
	DbDao dbDao = new DbDao();
	
	public static void main(String args[]) {
		Scraper scraper = new Scraper();
		scraper.startLoop();
	}
	
	public void startLoop() {
		while (true) {
			runMe();

			try {
				Thread.sleep(60 * 60 * 1000);				//60 sec * 60 min, conver to milliseconds, so wait one hour between visits
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}
	}
	
	public void runMe() {

		try {

		    webClient = new WebClient(BrowserVersion.FIREFOX_38);
		    //webClient.getOptions().setThrowExceptionOnScriptError(false);
		    page = webClient.getPage(url);
		    
		    //scrollToBottomOfPage();
		    
		    final List<?> sectionPlayers = page.getByXPath("(//div[@class='section-asdf'])");									//get all section players on page
		    
		   for (int i = 0; i < sectionPlayers.size(); i++) {																	//loop through them
			   System.out.println("NAWICE... Lets get the asdf links!");
			   HtmlDivision htmlDivision = (HtmlDivision) sectionPlayers.get(i);
			   if( htmlDivision.getElementsByAttribute("a", "class", "toggle-asdf").size() == 1) {							//more than one blog posted
				   
				   List<HtmlElement> repostLink = htmlDivision.getElementsByAttribute("a", "class", "toggle-asdf");				//get the link to show posts
				   repostLink.get(0).click();																						
				   webClient.waitForBackgroundJavaScript(5000);																		//wait for the ajax page to reload
				   printLinksInSectionPlayer(htmlDivision);																			//print the new visible links																	
				   
				   List<HtmlElement> showMoreLink = htmlDivision.getElementsByAttribute("a", "class", "fav-asdf");				//get the show more link
				   if ( showMoreLink.size() == 1) {																					//if we have a show more link...
					   showMoreLink.get(0).click();																					//click it
					   webClient.waitForBackgroundJavaScript(5000);																	//wait for the ajax
					   printLinksInSectionPlayer(htmlDivision);																		//print the new visible links
					   
					   while (htmlDivision.getElementsByAttribute("a", "class", "fav-asdf").size() == 2) {						//if there are two links we have a next and prev button
						   showMoreLink = htmlDivision.getElementsByAttribute("a", "class", "fav-paging");							//get the next and prev buttons
						   showMoreLink.get(1).click();																				//click the next button
						   webClient.waitForBackgroundJavaScript(5000);
						   printLinksInSectionPlayer(htmlDivision);																	//print the links
					   }
				   }
			   }
			   else {
				   printLinksInSectionPlayer(htmlDivision);																		//only one blog post this print it
			   }
		   }
		    
			webClient.closeAllWindows();
		} catch (Exception e) {
			System.out.println("Welp, something went wrong...");
			e.printStackTrace();
		}
		
		System.out.println("DING! Toast is done!");
	}

	//this doesn't work...
	private void scrollToBottomOfPage() {
		webClient.waitForBackgroundJavaScript(5000);
		
		System.out.println("page size: " + page.asXml().length());
		
		String s = "window.scrollTo(0,1000000);";
        ScriptResult sr = page.executeJavaScript(s);

		System.out.println("page size: " + page.asXml().length());
        System.out.println("holler");
        //System.out.println("Result= " + sr.getJavaScriptResult() + "\n Page2\n");
        //HtmlPage page2 = (HtmlPage) sr.getNewPage();		
	}

	private void printLinksInSectionPlayer(HtmlDivision htmlDivision) {
		   List<HtmlElement> blogLinks = htmlDivision.getElementsByAttribute("a", "class", "readpost");
		   printBlogLinks(blogLinks);
		   System.out.println("dude");
	}

	private void printBlogLinks(List<HtmlElement> blogLinks) {
		for (HtmlElement element : blogLinks) {
			System.out.println(element.getAttribute("href"));
			dbDao.addDomain(element.getAttribute("href"));			//add it to the db
		}
	}
	
}
