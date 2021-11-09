package ggc;

import java.util.List;

public class DefaultNotification extends Delivery{

  /**
   * Serial number for serialization.
   */
  private static final long serialVersionUID = 202109192006L;

  public String showAndClearNotifications(List<Notification> notificationList) {
    String s = "";
    for (Notification notification: notificationList)
    {
      s += "\n" + notification.toString() ;
    }
    return s;
  }
}
