package ggc;

/**
 * Interface Comparator
 *
 * @param <Batch>
 */
public interface Comparator<Batch> {
  int compare(Batch _this, Batch other);
}
