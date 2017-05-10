package johnnyscrape;

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by johnminchuk on 10/7/16.
 */
@Service
public class GetWebsiteC {

    @Autowired
    LinkRepository linkRepository;

    public void gatherLinks() throws IOException {

        List<String> asdf = new ArrayList<>();

        asdf.add("https://example.com/somemp4part");
        asdf.add("https://example.com/somemp4part");
        asdf.add("https://example.com/somemp4part");
        asdf.add("https://example.com/somemp4part");
        asdf.add("https://example.com/somemp4part");
        asdf.add("https://example.com/somemp4part");
        asdf.add("https://example.com/somemp4part");
        asdf.add("https://example.com/somemp4part");
        asdf.add("https://example.com/somemp4part");
        asdf.add("https://example.com/somemp4part");
        asdf.add("https://example.com/somemp4part");
        asdf.add("https://example.com/somemp4part");
        asdf.add("https://example.com/somemp4part");
        asdf.add("https://example.com/somemp4part");
        asdf.add("https://example.com/somemp4part");
        asdf.add("https://example.com/somemp4part");
        asdf.add("https://example.com/somemp4part");
        asdf.add("https://example.com/somemp4part");
        asdf.add("https://example.com/somemp4part");
        asdf.add("https://example.com/somemp4part");
        asdf.add("https://example.com/somemp4part");
        asdf.add("https://example.com/somemp4part");
        asdf.add("https://example.com/somemp4part");
        asdf.add("https://example.com/somemp4part");
        asdf.add("https://example.com/somemp4part");
        asdf.add("https://example.com/somemp4part");
        asdf.add("https://example.com/somemp4part");
        asdf.add("https://example.com/somemp4part");
        asdf.add("https://example.com/somemp4part");
        asdf.add("https://example.com/somemp4part");
        asdf.add("https://example.com/somemp4part");
        asdf.add("https://example.com/somemp4part");
        asdf.add("https://example.com/somemp4part");
        asdf.add("https://example.com/somemp4part");
        asdf.add("https://example.com/somemp4part");
        asdf.add("https://example.com/somemp4part");
        asdf.add("https://example.com/somemp4part");
        asdf.add("https://example.com/somemp4part");
        asdf.add("https://example.com/somemp4part");
        asdf.add("https://example.com/somemp4part");
        asdf.add("https://example.com/somemp4part");
        asdf.add("https://example.com/somemp4part");
        asdf.add("https://example.com/somemp4part");
        asdf.add("https://example.com/somemp4part");
        asdf.add("https://example.com/somemp4part");

        int count = 1;
        for (String s : asdf) {
            Link qwer = new Link();
            qwer.setUrl(s);
            qwer.setDownloaded(false);
            qwer.setNamespace("pm_02_synth");
            qwer.setDestination("/Volumes/PE_MAC_DISK/asdf/asdf" + String.format("%04d", count) + ".ts");
            count++;

            linkRepository.save(qwer);
        }

    }

    private void printAllPagesToDownloadFrom(List<HtmlAnchor> epLinks) {
        for (HtmlAnchor epLink : epLinks) {
            System.out.println(epLink.getHrefAttribute());
        }
    }

    public void downloadLinks() throws Exception {
        List<Link> pbtv = linkRepository.findByNamespaceAndDownloaded("pm_02_synth", false);

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
