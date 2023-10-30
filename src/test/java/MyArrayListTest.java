import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Comparator;

public class MyArrayListTest {
    private record TestClass(int value) implements Comparable<TestClass> {
        @Override
            public int compareTo(TestClass obj) {
                return value - obj.value;
            }
        }
    private record WrongTestClass(int value) {}
    private MyArrayList<Integer> list;
    private Integer[] values;

    @Before
    public void init() {
        list = new MyArrayList<>();
        values = new Integer[] { 2, 6, 3, 7, 9, 11, 45, 87, 9, 4, 1};

        for (Integer value : values) {
            list.add(value);
        }
    }

    @After
    public void teardown() {
        list.clear();
    }

    @Test
    public void addAndSizeTest() {
        final int size = list.size();

        for (Integer value : values) {
            list.add(value);
            list.add(0, value);
        }

        assertEquals(size + values.length * 2L, list.size());
    }

    @Test
    public void addWithIndexToEmptyListTest() {
        MyArrayList<Integer> emptyList = new MyArrayList<>();

        for (int i = 0; i < values.length; i++) {
            emptyList.add(i, values[i]);
        }

        assertEquals(values.length, emptyList.size());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void addNegativeIndexTest() {
        list.add(-1, values[0]);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void addIndexOutOfBoundsTest() {
        list.add(12, values[0]);
    }

    @Test
    public void getTest() {
        for (Integer value : values) {
            list.add(0, value);

            assertEquals(value, list.get(0));
        }
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getNegativeIndexTest() {
        list.get(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getIndexOutOfBoundsTest() {
        list.get(11);
    }

    @Test
    public void removeTest() {
        final int size = list.size();

        for (int i = 0; i < values.length; i++) {
            list.remove(0);
        }

        assertEquals(size - values.length, list.size());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void removeNegativeIndexTest() {
        list.remove(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void removeIndexOutOfBoundsTest() {
        list.remove(11);
    }

    @Test
    public void setTest() {
        final int size = list.size();

        for (Integer value : values) {
            list.set(0, value);

            assertEquals(value, list.get(0));
        }

        assertEquals(size, list.size());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void setNegativeIndexTest() {
        list.set(-1, values[0]);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void setIndexOutOfBoundsTest() {
        list.set(11, values[0]);
    }

    @Test
    public void clearTest() {
        list.clear();

        assertEquals(0, list.size());
    }

    @Test
    public void sortComparableTest() {
        TestClass[] comparableClasses = new TestClass[] {
                new TestClass(12),
                new TestClass(7),
                new TestClass(3)
        };

        MyArrayList<TestClass> comparable = new MyArrayList<>(2);

        for (TestClass c : comparableClasses) {
            comparable.add(c);
        }

        comparable.sort();

        for (int i = 0; i < comparable.size(); i++) {
            assertEquals(comparableClasses[comparableClasses.length - 1 - i], comparable.get(i));
        }
    }

    @Test(expected = ClassCastException.class)
    public void sortComparableExceptionTest() {
        WrongTestClass notComparableClass = new WrongTestClass(7);
        MyArrayList<WrongTestClass> notComparable = new MyArrayList<>();

        notComparable.add(notComparableClass);
        notComparable.add(notComparableClass);

        notComparable.sort();
    }

    @Test
    public void sortComparatorTest() {
        WrongTestClass[] notComparableClasses = new WrongTestClass[] {
                new WrongTestClass(12),
                new WrongTestClass(7),
                new WrongTestClass(3)
        };

        MyArrayList<WrongTestClass> notComparable = new MyArrayList<>(2);

        for (WrongTestClass c : notComparableClasses) {
            notComparable.add(c);
        }

        Comparator<WrongTestClass> comparator = Comparator.comparingInt(o -> o.value);
        notComparable.sort(comparator);

        for (int i = 0; i < notComparable.size(); i++) {
            assertEquals(notComparableClasses[notComparableClasses.length - 1 - i], notComparable.get(i));
        }
    }

    @Test
    public void sortEmptyListTest() {
        MyArrayList<TestClass> comparable = new MyArrayList<>();
        MyArrayList<WrongTestClass> notComparable = new MyArrayList<>();

        Comparator<WrongTestClass> comparator = Comparator.comparingInt(o -> o.value);

        comparable.sort();
        notComparable.sort(comparator);

        assertEquals(0, comparable.size());
        assertEquals(0, notComparable.size());
    }

    @Test
    public void toStringTest() {
        assertEquals("[ 2 6 3 7 9 11 45 87 9 4 1 ]", list.toString());
    }
}
