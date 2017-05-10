package johnnyscrape;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * Created by johnminchuk on 2/20/17.
 */
@SpringBootApplication
public class JohnnyScrape {

    public static void main(String[] args) {
        SpringApplication.run(JohnnyScrape.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) throws Exception {

        //GetWebsiteB getWebsiteB = ctx.getBean(GetWebsiteB.class);
        //getWebsiteB.gatherLinks();
        //getWebsiteB.downloadLinks();

        GetWebsiteA getWebsiteA = ctx.getBean(GetWebsiteA.class);
        //getWebsiteA.gatherLinks();
        //getWebsiteA.downloadLinks();

        return null;

    }


}
