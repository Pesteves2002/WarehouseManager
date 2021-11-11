package ggc.notifications;

import java.util.List;

// example of another method of delivery
public class Mail extends Delivery{
  /** Serial number for serialization. */
  private static final long serialVersionUID = 202110262229L;

  public String showAndClearNotifications(List<Notification> notificationList) {
    String s = "";
    for (Notification notification: notificationList)
    {
      s += "\nEMAIL |" + notification.toString()  ;
    }
    return s;
  }

}
