package johnnyscrape;

import org.hsqldb.util.DatabaseManagerSwing;

/**
 * Created by johnminchuk on 2/20/17.
 */
public class JohnnyScrapeBrowser {

    public static void main(String args []) {

        DatabaseManagerSwing databaseManagerSwing = new DatabaseManagerSwing();

        //this doesn't seem to work an i'm giving up.  need to address in the future.  copy this into the connection parameters
        //memory type connection, with this url..
        System.setProperty("jdbcUrl", "jdbc:hsqldb:file:johnny-scrape-db/db/data;shutdown=true;hsqldb.write_delay=false;");

        databaseManagerSwing.main();
    }
}
