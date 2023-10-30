import java.util.Comparator;

/**
 * This class is my version of {@link java.util.ArrayList} which contains all listed methods from the task
 * file to fill, view, change and sort (using Comparator or class which implement Comparable) generic array.
 * These all methods which have param index throws {@link IndexOutOfBoundsException}.
 * @param <E> type of elements at array.
 */
public class MyArrayList<E> {
    private Object[] data;
    private int capacity;
    private int size;
    private final static int INITIAL_CAPACITY = 10;
    private final static double CAPACITY_SCALE = 1.5;

    /**
     * Constructs empty list with default initial capacity.
     */
    public MyArrayList() {
        this.capacity = INITIAL_CAPACITY;
        this.size = 0;
        this.data = new Object[capacity];
    }

    /**
     * Constructs empty list with specified initial capacity.
     * @param capacity initial capacity of list.
     */
    public MyArrayList(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.data = new Object[capacity];
    }

    /**
     * Adds element to the end of the list.
     * If list is full, it first changes capacity of array by capacity scale.
     * @param element element to add to list.
     */
    public void add(E element) {
        if (isFull()) {
            changeCapacity();
        }

        data[size++] = element;
    }

    /**
     * Adds element to specified index of the list.
     * If index is busy, it moves elements to the right and insert element to specified index.
     * Also change capacity of list if it's full.
     * @param index index to insert.
     * @param element element to add to list.
     * @throws IndexOutOfBoundsException if index is out of range ({@code index < 0 || index >= size}).
     */
    public void add(int index, E element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of range");
        }

        if (isFull()) {
            changeCapacity();
        }

        System.arraycopy(data, index, data, index + 1, size - index);

        data[index] = element;
        size++;
    }

    /**
     * Returns element from list with specified index.
     * @param index index of element to return.
     * @return element at specified index.
     * @throws IndexOutOfBoundsException if index is out of range ({@code index < 0 || index >= size}).
     */
    public E get(int index) {
        checkIndex(index);

        @SuppressWarnings("unchecked")
        final E element = (E) data[index];

        return element;
    }

    /**
     * Remove element from list with specified index.
     * @param index index of element to remove.
     * @throws IndexOutOfBoundsException if index is out of range ({@code index < 0 || index >= size}).
     */
    public void remove(int index) {
        checkIndex(index);

        System.arraycopy(data, index + 1, data, index, --size - index);
        data[size] = null;
    }

    /**
     * Remove all elements from list.
     * Change capacity to default initial capacity.
     */
    public void clear() {
        capacity = INITIAL_CAPACITY;
        data = new Object[capacity];
        size = 0;
    }

    /**
     * Sort all elements at list from least to most.
     * Use <a href="https://ru.wikipedia.org/wiki/%D0%91%D1%8B%D1%81%D1%82%D1%80%D0%B0%D1%8F_
     * %D1%81%D0%BE%D1%80%D1%82%D0%B8%D1%80%D0%BE%D0%B2%D0%BA%D0%B0#%D0%A1%D1%85%D0%B5%D0%BC%D0%B0_
     * %D0%A5%D0%BE%D0%B0%D1%80%D0%B0">quick sort</a> algorithm. <p>
     *
     * Use it for elements of class which implements {@link Comparable}.
     * @throws ClassCastException if can not cast element class to {@link Comparable}.
     */
    public void sort() {
        if (!isEmpty()) {
            quickSort(data, 0, size - 1, null);
        }
    }

    /**
     * Sort all elements at list from least to most.
     * Use <a href="https://ru.wikipedia.org/wiki/%D0%91%D1%8B%D1%81%D1%82%D1%80%D0%B0%D1%8F_
     * %D1%81%D0%BE%D1%80%D1%82%D0%B8%D1%80%D0%BE%D0%B2%D0%BA%D0%B0#%D0%A1%D1%85%D0%B5%D0%BC%D0%B0_
     * %D0%A5%D0%BE%D0%B0%D1%80%D0%B0">quick sort</a> algorithm. <p>
     *
     * Use it for elements which can be sorted by {@link Comparator}.
     * @param comparator comparator of element's class.
     */
    public void sort(Comparator<E> comparator) {
        if (!isEmpty()) {
            quickSort(data, 0, size - 1, comparator);
        }
    }

    /**
     * Change element at list with specified index.
     * @param index index of element to change.
     * @param element element to replace.
     * @throws IndexOutOfBoundsException if index is out of range ({@code index < 0 || index >= size}).
     */
    public void set(int index, E element) {
        checkIndex(index);

        data[index] = element;
    }

    /**
     * Returns actual size of list.
     * @return count of elements at list.
     */
    public int size() {
        return size;
    }

    private boolean isFull() {
        return size == capacity;
    }

    private boolean isEmpty() {
        return size == 0;
    }

    private void changeCapacity() {
        int oldCapacity = capacity;

        capacity *= CAPACITY_SCALE;

        Object[] tmp = data.clone();
        data = new Object[capacity];

        System.arraycopy(tmp, 0, data, 0, oldCapacity);
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of range");
        }
    }

    private void quickSort(Object[] data, int low, int high, Comparator<E> comparator) {
        if (low < high) {
            int p = partition(data, low, high, comparator);

            quickSort(data, low, p, comparator);
            quickSort(data, p + 1, high, comparator);
        }
    }

    private int partition(Object[] data, int low, int high, Comparator<E> comparator) {
        @SuppressWarnings("unchecked")
        final E pivot = (E) data[(low + high) / 2];

        int i = low;
        int j = high;

        while (true) {
            if (comparator == null) {
                while (((Comparable) data[i]).compareTo(pivot) < 0) {
                    i++;
                }

                while (((Comparable) data[j]).compareTo(pivot) > 0) {
                    j--;
                }
            } else {
                while (comparator.compare((E) data[i], pivot) < 0) {
                    i++;
                }

                while (comparator.compare((E) data[j], pivot) > 0) {
                    j--;
                }
            }

            if (i >= j) {
                return j;
            }

            Object tmp = data[i];
            data[i++] = data[j];
            data[j--] = tmp;
        }
    }

    /**
     * Constructs list with elements as {@link String} object.
     * Using element method {@code toString()}.
     * @return line with elements of list. Example: {@code "[ 1 2 3 4 5 ]"}.
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[ ");
        for (int i = 0; i < size; i++) {
            builder.append(data[i].toString()).append(' ');
        }
        builder.append(']');

        return builder.toString();
    }
}
