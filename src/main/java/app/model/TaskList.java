package app.model;

import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.Iterator;

/**
 * Abstract Class for Task List
 *
 * @author Vlad Honcharenko
 * @version 1.0
 */

abstract public class TaskList implements Iterable<Task>, Cloneable, DateFormat, Serializable {

    private static final long serialVersionUID = 1L;
    final static Logger logger = Logger.getLogger(ArrayTaskList.class);
    public int size = 0;
    public int index = 0;

    /**
     * Adds tasks to task list
     *
     * @param task
     * @throws NullTaskException
     */
    public void add(Task task) throws NullTaskException {
    }

    /**
     * Returns size of list
     *
     * @return size
     */
    public int size() {
        return this.size;

    }

    /**
     * Returns index of list
     *
     * @return index
     */
    public int index() {
        return this.index;
    }

    /**
     * Removes task from task list
     *
     * @param task
     * @return true
     * @throws NullTaskException
     */
    public boolean remove(Task task) throws NullTaskException {
        return true;
    }

    /**
     * Gets task by index
     *
     * @param index
     * @return null
     */
    public Task getTask(int index) {
        return null;
    }

    /**
     * Returns unique code for object
     *
     * @return 0
     **/
    public int hashCode() {

        return 0;

    }

    /**
     * Returns true if both objects are equals
     *
     * @param obj
     * @return isEquals
     */
    public boolean equals(Object obj) {

        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        TaskList other = (TaskList) obj;
        if (index != other.index)
            return false;
        if (size != other.size)
            return false;

        boolean isEquals = true;
        Iterator<Task> firstObjIterator = this.iterator();
        Iterator<Task> secondObjIterator = other.iterator();

        while (firstObjIterator.hasNext() && secondObjIterator.hasNext()) {
            if (!firstObjIterator.next().equals(secondObjIterator.next())) {
                isEquals = false;
                break;
            }

        }

        return isEquals;

    }

    /**
     * Clones Task List
     *
     * @return null
     */
    public TaskList clone() {
        TaskList clone = null;
        try {
            clone = (TaskList) super.clone();
        } catch (CloneNotSupportedException e) {
            logger.error("Error while cloning TaskList", e);
        }
        return clone;
    }
}