package ggc;

import java.util.List;

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
