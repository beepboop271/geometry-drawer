package shapes;

import java.util.Iterator;

public class ReadOnlyIterator<E> implements Iterator<E>, Iterable<E> {
  private final Iterator<E> iterator;

  public ReadOnlyIterator(Iterator<E> iterator) {
    this.iterator = iterator;
  }

  @Override
  public boolean hasNext() {
    return this.iterator.hasNext();
  }

  @Override
  public E next() {
    return this.iterator.next();
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException(
      "Cannot remove from a ReadOnlyIterator"
    );
  }

  @Override
  public ReadOnlyIterator<E> iterator() {
    return this;
  }
}
