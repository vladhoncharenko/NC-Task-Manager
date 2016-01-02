package app.model;
import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Linked List Implementation
 * 
 * @author Vlad Honcharenko
 * @version 1.0
 */

public class LinkedTaskList extends TaskList {

	private static final long serialVersionUID = 1L;

	public LinkedTaskList() {

	}

	/**
	 * Class that allows to keep task,next and previous link in one object
	 */
	public class Node implements Serializable {

		private static final long serialVersionUID = 1L;
		public Task task;
		public Node next;
		public Node previous;

		/**
		 * Node Class Constructor
		 * 
		 * @param previousConstructor
		 * @param elementConstructor
		 * @param nextConstructor
		 */
		public Node(Node previousConstructor, Task elementConstructor, Node nextConstructor) {
			task = elementConstructor;
			next = nextConstructor;
			previous = previousConstructor;
		}

		/**
		 * Returns task from object of this class
		 * 
		 * @return task
		 */
		public Task getData() {
			return task;
		}

		/**
		 * Returns next object in list
		 * 
		 * @return next
		 */
		public Node getNext() {
			return next;
		}

		/**
		 * Returns previous object in list
		 * 
		 * @return next
		 */
		public Node getPrevious() {
			return previous;
		}

		/**
		 * Shows title of current task
		 */
		public void displayLink() {
			System.out.print(task.getTitle() + "\n");

		}

	}

	public Node first;
	public Node last;

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

		size++;
		index++;
		final Node lastNode = last;
		final Node newNode = new Node(lastNode, task, null);
		last = newNode;
		if (lastNode == null)
			first = newNode;
		else
			lastNode.next = newNode;

	}

	/**
	 * Removes task from task list
	 * 
	 * @param task
	 * @return true if success
	 * @return false if not success
	 */
	@Override
	public boolean remove(Task task) {
		if (task == null) {
			for (Node x = first; x != null; x = x.next) {
				if (x.task == null) {
					unlink(x);
					index--;
					return true;
				}
			}
		} else {
			for (Node x = first; x != null; x = x.next) {
				if (task.equals(x.task)) {
					unlink(x);
					index--;
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Gets task by index
	 * 
	 * @param index
	 * @return x.task
	 */
	@Override
	public Task getTask(int index) {
		if (index < 0 || index > size)
			throw new IndexOutOfBoundsException("Enter a valid task index, that exist's in this list");
		if (index < (size >> 1)) {
			Node x = first;
			for (int i = 0; i < index; i++)
				x = x.next;
			return x.task;
		} else {
			Node x = last;
			for (int i = size - 1; i > index; i--)
				x = x.previous;
			return x.task;
		}
	}

	/**
	 * Deletes object links
	 * 
	 * @param x
	 * @return element
	 */
	public Task unlink(Node x) {

		final Task element = x.task;
		final Node next = x.next;
		final Node previous = x.previous;

		if (previous == null) {
			first = next;
		} else {
			previous.next = next;
			x.previous = null;
		}

		if (next == null) {
			last = previous;
		} else {
			next.previous = previous;
			x.next = null;
		}

		x.task = null;
		size--;
		return element;
	}

	/**
	 * Returns object of Node class
	 * 
	 * @param index
	 * @return x
	 */
	public Node getNode(int index) {
		if (index < 0 || index > size)
			throw new IndexOutOfBoundsException("Enter a valid task index, that exist's in this list");
		if (index < (size >> 1)) {
			Node x = first;
			for (int i = 0; i < index; i++)
				x = x.next;
			return x;
		} else {
			Node x = last;
			for (int i = size - 1; i > index; i--)
				x = x.previous;
			return x;
		}
	}

	/**
	 * Outputs Linked Task List
	 * 
	 * @return null
	 */
	@Override
	public String toString() {
		Node current = first;
		while (current != null) {
			current.displayLink();
			current = current.next;

		}
		return null;
	}

	/**
	 * Creates new Linked List Iterator
	 * 
	 * @return new LinkedListIterator()
	 */

	public Iterator<Task> iterator() {

		return new LinkedListIterator();
	}

	/**
	 * Own Iterator class for LinkedListIterator
	 */
	private class LinkedListIterator implements Iterator<Task> {

		Node firstNodeTask = first;

		/**
		 * Checks, is task list has next element
		 * 
		 * @return true or false
		 */
		public boolean hasNext() {

			return (firstNodeTask != null);
		}

		/**
		 * Returns next task in task list
		 * 
		 * @return nextTask
		 */
		public Task next() {

			if (!hasNext()) {
				throw new NoSuchElementException("This element is not exist");
			}

			Task nextTask = firstNodeTask.task;
			firstNodeTask = firstNodeTask.next;
			return nextTask;
		}

	}

	/**
	 * Clones Linked Task List
	 * 
	 * @return clonedTaskList
	 * @throws CloneNotSupportedException
	 */
	public Object clone() throws CloneNotSupportedException {

		LinkedTaskList clonedTaskList = new LinkedTaskList();

		clonedTaskList.first = clonedTaskList.last = null;

		for (Node x = first; x != null; x = x.next)
			try {
				clonedTaskList.add(x.task);
			} catch (NullTaskException e) {

				e.printStackTrace();
			}

		return clonedTaskList;

	}

	/**
	 * Returns unique code for object
	 * 
	 * @return result
	 **/
	@Override
	public int hashCode() {

		final int prime = 43;
		int result = 4;
		@SuppressWarnings("deprecation")
		int r = this.getTask(this.index).getTime().getDay();
		result = index > 5 ? prime * result * 3 : prime * result * 2;
		result = prime * result + index + r;
		result = prime * result + size;
		return result;

	}

	/**
	 * Returns true if both objects are equals
	 * 
	 * @param obj
	 * @return true or false
	 */
	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaskList other = (TaskList) obj;

		int in = 0;
		for (Task l : other) {
			if (!l.equals(this.getTask(in))) {
				return false;
			}
			in++;
		}

		return true;
	}

}