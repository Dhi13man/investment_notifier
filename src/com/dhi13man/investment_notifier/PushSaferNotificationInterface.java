package com.dhi13man.investment_notifier;

import java.io.IOException;
import java.util.Base64;

/**
 * Interface Class that handles Notification sending.
 * Uses Core Java HttpsURLConnection to make network requests.
 */
public class PushSaferNotificationInterface {
    private final String apiKey;

    private final String notificationPostEndpoint;

    private final NetworkRequestInterface requestInterface;

    /**
     * Constructor to initialize the Notification Interface with only the API Key
     * Uses a NetworkRequestInterface object to send notifications.
     * @param pushSaferAPIKey The Private API Key to use PushSafer Service
     */
    public PushSaferNotificationInterface(String pushSaferAPIKey) {
        notificationPostEndpoint = "https://www.pushsafer.com/api";
        apiKey = pushSaferAPIKey;
        requestInterface = new NetworkRequestInterface();
    }

    /**
     * Constructor to initialize the Notification Interface with API Key and API Endpoint URL.
     * Uses a NetworkRequestInterface object to send notifications.
     * @param pushSaferPOSTEndpointURL the endpoint to send Notifications to.
     * @param pushSaferAPIKey The Private API Key to use PushSafer Service
     */
    public PushSaferNotificationInterface(String pushSaferPOSTEndpointURL, String pushSaferAPIKey) {
        notificationPostEndpoint =
                pushSaferPOSTEndpointURL == null ? "https://www.pushsafer.com/api": pushSaferPOSTEndpointURL;
        apiKey = pushSaferAPIKey;
        requestInterface = new NetworkRequestInterface();
    }

    /**
     * Send a notification to the notification POST_URL endpoint which this class was initialized with.
     * The backend then sends the notification where required.
     * @param notificationTitle The title of the Notification
     * @param notificationBody The main message body of the Notification
     * @return Integer representing Status Code of whether the Notification sending request was successful
     * @throws IOException as the function makes Network Requests, which are Error Prone
     */
    public int sendNotification(String notificationTitle, String notificationBody, String iconURL) throws IOException {
        String pictureURLBase64 = iconURL == null ? null : Base64.getEncoder().encodeToString(iconURL.getBytes());
        String postData = String.format(
                "{\"t\": \"%s\", \"m\": \"%s\", \"p\": \"%s\", \"k\": \"%s\"}",
                notificationTitle,
                notificationBody,
                pictureURLBase64,
                apiKey
        );
        System.out.println("Sending: " + postData);
        return requestInterface.requestPOST(notificationPostEndpoint, postData);
    }
}
