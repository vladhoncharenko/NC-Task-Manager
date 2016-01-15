package app.model;

import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Array List Implementation
 *
 * @author Vlad Honcharenko
 * @version 1.0
 */

public class ArrayTaskList extends TaskList {
    final static Logger logger = Logger.getLogger(ArrayTaskList.class);
    private static final long serialVersionUID = 1L;

    /**
     * ArrayTaskList constructor, that creates basic array with capacity 10
     * elements
     */
    public ArrayTaskList() {
        basicArray = new Task[10];
    }

    private Task[] basicArray;

    /**
     * Adds tasks to task list
     *
     * @param task
     * @throws NullTaskException
     */
    @Override
    public void add(Task task) throws NullTaskException {
        if (task == null)
            throw new NullTaskException("Task can not be null");

        if (index == basicArray.length) {
            increaseArray();
        }
        basicArray[index] = task;
        size++;
        index++;
    }

    /**
     * Removes task from task list
     *
     * @param task
     * @return false if not success
     * @throws NullTaskException
     */
    @Override
    public boolean remove(Task task) throws NullTaskException {
        if (task == null)
            throw new NullTaskException("Task can not be null");
        for (int i = 0; i < index; i++) {
            if (basicArray[i].equals(task)) {
                System.arraycopy(basicArray, i + 1, basicArray, i, this.index - i);
                size--;
                this.index--;
                return true;
            }
        }
        return false;
    }

    /**
     * Gets task by index
     *
     * @param index
     * @return basicArray[index] one task from array
     */
    @Override
    public Task getTask(int index) {
        if (index <= -1 || index > this.index)
            throw new ArrayIndexOutOfBoundsException(
                    "Enter a valid task index, that exist's in array, in method getTask ");

        return basicArray[index];
    }

    /**
     * Increases basic array on 10 elements
     */
    public void increaseArray() {

        int newSize = basicArray.length + 10;
        basicArray = Arrays.copyOf(basicArray, newSize);

    }

    /**
     * Sets new value of task
     *
     * @param index
     * @param task
     * @throws NullTaskException
     */
    public void setNewValue(int index, Task task) throws NullTaskException {

        if (index <= -1 || index > this.index)
            throw new ArrayIndexOutOfBoundsException(
                    "Enter a valid task index, that exist's in array, in method setNewValue");
        if (task == null)
            throw new NullTaskException("Task can not be null");

        basicArray[index] = task;
    }

    /**
     * Outputs Array Task List
     *
     * @return null
     */
    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < index; i++) {
            result += this.getTask(i).getTitle() + "\n";
        }
        return result;
    }

    /**
     * Creates new Array List Iterator
     *
     * @return new ArrayListIterator()
     */

    public Iterator<Task> iterator() {

        return new ArrayListIterator();
    }

    /**
     * Own Iterator class for ArrayListIterator
     */
    private class ArrayListIterator implements Iterator<Task> {

        int cursor;

        /**
         * Checks, is task list has next element
         *
         * @return true or false
         */
        public boolean hasNext() {
            return cursor != size;
        }

        /**
         * Returns next task in task list
         *
         * @return elementData[i]
         */
        public Task next() {

            int i = cursor;
            if (i >= size)
                throw new NoSuchElementException("This element is not exist");
            Task[] elementData = basicArray;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i + 1;
            return elementData[i];
        }

    }

    /**
     * Returns unique code for object
     *
     * @return result
     **/
    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(basicArray);
        result = prime * result + index;
        result = prime * result + size;
        return result;

    }

    /**
     * Clones Array Task List
     *
     * @return clonedArray
     * @throws CloneNotSupportedException
     */
    public Object clone() throws CloneNotSupportedException {

        ArrayTaskList clonedArray = new ArrayTaskList();
        for (Task l : this) {
            try {
                clonedArray.add(l);
            } catch (NullTaskException e) {
                logger.error("In clone()-", e);
            }
        }
        return clonedArray;

    }

}