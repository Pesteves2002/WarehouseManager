package ggc;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;
import java.io.Serializable;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Wrapper to the class Comparator
 */
public class CollatorWrapper implements Comparator<String>, Serializable {
  /** Serial number for serialization. */
  private static final long serialVersionUID = 202110251850L;
  /** Collator that sorts by alphabetical order */
  private transient Collator _collator = Collator.getInstance(Locale.getDefault());

  /**
   * @param ois
   * @throws IOException
   * @throws ClassNotFoundException
   */
  private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
    ois.defaultReadObject();
    _collator = Collator.getInstance(Locale.getDefault());
  }

  /**
   * @param s1
   * @param s2
   * @return int
   */
  @Override
  public int compare(String s1, String s2) {
    return _collator.compare(s1, s2);
  }
}
