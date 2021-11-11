package ggc.products;

/**
 * Interface Comparator
 *
 * @param <Batch>
 */
public interface Comparator<Batch> {
  int compare(Batch batch1, Batch batch2);
}
