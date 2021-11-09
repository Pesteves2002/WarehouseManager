package ggc;

import java.io.Serializable;
import java.util.List;

public abstract class Delivery implements Serializable {
  /**
   * Serial number for serialization.
   */
  private static final long serialVersionUID = 202109192006L;

  public abstract String showAndClearNotifications(List<Notification> notificationList);

}