import java.io.Serializable;

/**
 * Abstract Class for Task List
 * 
 * @author Vlad Honcharenko
 * @version 1.0
 */

abstract public class TaskList implements Iterable<Task>, Cloneable, DateFormat, Serializable {

	private static final long serialVersionUID = 1L;

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
	 * @return true
	 */
	public boolean equals(Object obj) {

		return true;
	}

	/**
	 * Clones Task List
	 * 
	 * @return null
	 * @throws CloneNotSupportedException
	 */
	public Object clone() throws CloneNotSupportedException {

		return null;
	}
}