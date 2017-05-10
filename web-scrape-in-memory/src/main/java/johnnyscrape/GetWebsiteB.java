package johnnyscrape;

import com.gargoylesoftware.htmlunit.JavaScriptPage;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlScript;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by johnminchuk on 10/7/16.
 */
@Service
public class GetWebsiteB {

    @Autowired
    LinkRepository linkRepository;

    public void gatherLinks() throws IOException {

        final WebClient webClient = new WebClient();
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setTimeout(60000);
        webClient.getOptions().setRedirectEnabled(true);
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setCssEnabled(false);

        final HtmlPage page = webClient.getPage("https://www.websiteb.com/episode/asdf/asdf");

        System.out.println(page.getTitleText());

        List<HtmlAnchor> epLinks = (List<HtmlAnchor>) page.getByXPath("//div[contains(@class, 'epi_tile')]/a");

        printAllPagesToDownloadFrom(epLinks);

        for (HtmlAnchor epLink : epLinks) {

            HtmlPage epPage = webClient.getPage(epLink.getHrefAttribute());

            HtmlScript epJs =  (HtmlScript) epPage.getByXPath("//script[contains(@src, 'jwplatform.com')]").get(0);

            JavaScriptPage jsPage = webClient.getPage("http:" + epJs.getSrcAttribute());

            //System.out.println(jsPage.getContent());

            String[] lines = jsPage.getContent().split(System.getProperty("line.separator"));

            int stopper = 0;
            for (int i = 0; i < lines.length; i++) {
                if (lines[i].contains("\"height\": 720,")) {
                    stopper = i;
                    break;
                }
            }

            String urlLine = lines[stopper-1];                 //the line above this one is what we need

            Pattern p = Pattern.compile("\"([^\"]*)\"");        //anything between quotes
            Matcher m = p.matcher(urlLine);
            m.find();                                           //iterate to first
            m.find();                                           //second match has what we want

            String downloadUrl = "http:" + m.group(1);

            String[] valuesInQuotes = StringUtils.substringsBetween(page.getUrl().toString(), "/", "/");

            String[] split = epPage.getUrl().toString().split("/");
            StringBuilder destFileName = new StringBuilder();
            destFileName.append("pbtv/");
            destFileName.append(split[split.length-2]);
            destFileName.append("---");
            destFileName.append(split[split.length-1]);
            destFileName.append(".mp4");

            Link newLink = new Link();
            newLink.setUrl(downloadUrl);
            newLink.setDestination(destFileName.toString());
            newLink.setDownloaded(false);
            newLink.setNamespace("pbtv");

            linkRepository.save(newLink);
            System.out.println("Just saved this to local db: " + newLink.toString());

            //System.out.println("downloading: " + downloadUrl);
            //System.out.println("to location: " + destFileName.toString());
            //FileUtils.copyURLToFile(new URL(downloadUrl), new File(destFileName.toString()));
            //System.out.println("down downloading");

        }

    }

    private void printAllPagesToDownloadFrom(List<HtmlAnchor> epLinks) {
        for (HtmlAnchor epLink : epLinks) {
            System.out.println(epLink.getHrefAttribute());
        }
    }

    public void downloadLinks() throws Exception {
        List<Link> pbtv = linkRepository.findByNamespaceAndDownloaded("pbtv", false);

        for (Link link : pbtv) {

            System.out.println("lets try to download this one: " + link.toString());

            if ( !link.getDownloaded() ) {

                File f = new File(link.getDestination());
                if(!f.exists() && !f.isDirectory()) {
                    System.out.println("This file is marked as not downloaded and does not exist on the filesystem");

                    FileUtils.copyURLToFile(new URL(link.getUrl()), new File(link.getDestination()));

                }

                link.setDownloaded(true);
                linkRepository.save(link);
            }
        }
    }
}
