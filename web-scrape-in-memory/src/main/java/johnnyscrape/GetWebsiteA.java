package johnnyscrape;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.xml.XmlPage;

/**
 * Created by johnminchuk on 3/11/17.
 */
public class GetWebsiteA {

    public static void main(String args[]) throws Exception {

        final WebClient webClient = new WebClient();
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setTimeout(60000);
        webClient.getOptions().setRedirectEnabled(true);
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setCssEnabled(false);




        final XmlPage page = webClient.getPage("http://feeds.feedburner.com/websitea");

        System.out.println("stop");


    }
}
