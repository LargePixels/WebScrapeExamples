package com.largepixels.webscrapeexamples.credential_stuffing;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.util.List;

/**
 * Created by johnminchuk on 5/2/17.
 *
 * Note: This one is still a work in progress, need to submit and check the response
 */
public class SimulateCredentialAttackHTMLUnit {

    //prd
    String[] usernames = {"user1","user2","user3","user4","user5","user6","user7","user8","user9","user10"};
    String[] passwords = {"password", "password", "nogood", "password", "password", "nogood", "password", "password", "password", "password"};
    String endpointPrefix = "www";

    //stg
    //String[] usernames = {"EveNoSub55","EveNoSub56","EveNoSub57","EveNoSub58","EveNoSub59","EveNoSub60","EveNoSub61","EveNoSub62","EveNoSub63","EveNoSub64"};
    //String[] passwords = {"password", "password", "nogood", "password", "password", "nogood", "password", "password", "password", "password"};
    //String endpointPrefix = "www";

    final WebClient webClient = new WebClient();

    public SimulateCredentialAttackHTMLUnit() {
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        //webClient.getOptions().setTimeout(60000);
        webClient.getOptions().setRedirectEnabled(true);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setCssEnabled(false);
    }

    private void runMe() throws Exception {

        for (int i = 0; i < usernames.length; i++) {
            System.out.println(usernames[i] + " : " + passwords[i]);

            final HtmlPage page = webClient.getPage("https://" + endpointPrefix + ".example.com/login");

            System.out.println(page.getTitleText());

            List<HtmlAnchor> epLinks = (List<HtmlAnchor>) page.getByXPath("//div[contains(@class, 'epi_tile')]/a");

            HtmlForm loginForm = (HtmlForm) page.getByXPath("//form[contains(@class, 'form-signin')]").get(0);

            loginForm.getInputByName("j_username").setValueAttribute("user1");
            loginForm.getInputByName("j_password").setValueAttribute("password");

            HtmlButton submitButton = (HtmlButton) page.getByXPath("//form[contains(@class, 'form-signin')]//button").get(0);

            submitButton.click();


            System.out.println("stop");

            Thread.sleep(10000);    //ten seconds
        }

    }


    public static void main(String args[]) throws Exception {
        SimulateCredentialAttackHTMLUnit simulateCredentialAttackHTMLUnit = new SimulateCredentialAttackHTMLUnit();
        simulateCredentialAttackHTMLUnit.runMe();
    }


}
