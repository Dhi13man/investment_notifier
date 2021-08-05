package com.dhi13man.investment_notifier;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Uses Core Java HttpsURLConnection to make network requests.
 */
public class NetworkRequestInterface {
    // User agent required for making requests.
    private static final String USER_AGENT = "Mozilla/5.0";

    /**
     * Gets the response of the connection after it has been made
     * @param connection [HttpsURLConnection]: The connection object with which the request is made.
     * @return response [StringBuffer]: The response received by the connection.
     **/
    private static StringBuffer decipherResponse(HttpsURLConnection connection) throws IOException {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream())
        );
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response;
    }

    /**
     * Make a HTTPS GET request.
     * @param endpointURL URL of the Endpoint where the GET request is to be made.
     * @throws IOException as network request could have error.
     * @return the Response body (or code if failed) as String.
     **/
    public String requestGET(String endpointURL) throws IOException {
        URL obj = new URL(endpointURL);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("accept", "application/json");
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        StringBuffer response;
        if (responseCode == HttpsURLConnection.HTTP_OK) { // success
            response = decipherResponse(con);
            System.out.println(response.toString());
        } else {
            response = new StringBuffer().append(responseCode);
            System.out.println("GET request failed");
        }
        return response.toString();
    }

    /**
     * Make a HTTPS POST request.
     * @param endpointURL URL of the Endpoint where the POST request is to be made.
     * @param postParameters the data to be sent as the POST request body
     * @throws IOException as network request could have error.
     * @return the Response Code of the POST request.
     **/
    public int requestPOST(String endpointURL, String postParameters) throws IOException {
        URL obj = new URL(endpointURL);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Content-Type", "application/json; utf-8");

        // For POST only - START
        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        os.write(postParameters.getBytes(StandardCharsets.UTF_8));
        os.flush();
        os.close();
        // For POST only - END

        int responseCode = con.getResponseCode();
        System.out.println("POST Response Code :: " + responseCode);

        if (responseCode == HttpsURLConnection.HTTP_OK) { //success
            StringBuffer response = decipherResponse(con);
            System.out.println(response.toString());
        } else {
            System.out.println("POST request not worked");
        }
        return responseCode;
    }
}
