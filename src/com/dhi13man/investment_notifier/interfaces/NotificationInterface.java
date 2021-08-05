package com.dhi13man.investment_notifier.interfaces;

import java.awt.*;
import java.awt.TrayIcon.MessageType;

/**
 * Interface Class that handles Notification sending.
 * Uses Core Java awt to show Desktop Notifications.
 */
public class NotificationInterface {
    /**
     * Send a notification to the notification POST_URL endpoint which this class was initialized with.
     * The backend then sends the notification where required.
     * @param notificationTitle The title of the Notification
     * @param notificationBody The main message body of the Notification
     * @param iconURL What should be the icon of the Notification.
     * @throws AWTException as the function makes use of java.awt.* to show notifications.
     */
    public static void sendNotification(
            String notificationTitle,
            String notificationBody,
            String iconURL
    ) throws AWTException {
        //Obtain only one instance of the SystemTray object
        SystemTray tray = SystemTray.getSystemTray();

        //If the icon is a file
        Image image = Toolkit.getDefaultToolkit().getImage(iconURL);

        TrayIcon trayIcon = new TrayIcon(image, "");
        //Let the system resize the image if needed
        trayIcon.setImageAutoSize(true);
        //Set tooltip text for the tray icon
        trayIcon.setToolTip(notificationTitle);
        tray.add(trayIcon);

        trayIcon.displayMessage(
                notificationTitle,
                notificationBody,
                (notificationTitle.contains("Fall") ? MessageType.ERROR : MessageType.WARNING)
        );
    }
}
