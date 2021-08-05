package com.dhi13man.investment_notifier.interfaces;

import java.util.logging.Level;
import java.util.logging.Logger;
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
    /** User agent required for making requests. **/
    private static final String USER_AGENT = "Mozilla/5.0";

    /** Logger object to log the class' outputs. **/
    private static final Logger logger = Logger.getLogger(NetworkRequestInterface.class.getName());

    /**
     * Gets the response of the connection after it has been made
     * @param connection [HttpsURLConnection]: The connection object with which the request is made.
     * @return response [StringBuilder]: The response received by the connection.
     **/
    private static StringBuilder decipherResponse(HttpsURLConnection connection) throws IOException {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream())
        );
        String inputLine;
        StringBuilder response = new StringBuilder();

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
        logger.log(Level.FINE, "GET Response Code: {0}", responseCode);
        StringBuilder response;
        if (responseCode == HttpsURLConnection.HTTP_OK) { // success
            response = decipherResponse(con);
            logger.log(Level.INFO, "{0}", response);
        } else {
            response = new StringBuilder().append(responseCode);
            logger.log(Level.SEVERE, "GET request failed!");
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
        final OutputStream os = con.getOutputStream();
        os.write(postParameters.getBytes(StandardCharsets.UTF_8));
        os.flush();
        os.close();
        // For POST only - END

        int responseCode = con.getResponseCode();
        logger.log(Level.FINE, "POST Response Code: {0}", responseCode);

        if (responseCode == HttpsURLConnection.HTTP_OK) { //success
            StringBuilder response = decipherResponse(con);
            logger.log(Level.INFO, "{0}", response);
        } else {
            logger.log(Level.SEVERE, "POST request failed!");
        }
        return responseCode;
    }
}
