import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

abstract public class TaskList implements Iterable<Task>, Cloneable {

	public class Node {
		public Task task;
		public Node next;
		public Node previous;

		public Node(Node previousConstructor, Task elementConstructor, Node nextConstructor) {
			task = elementConstructor;
			next = nextConstructor;
			previous = previousConstructor;
		}

		public Task getData() {
			return task;
		}

		public Node getNext() {
			return next;
		}

		public Node getPrevious() {
			return next;
		}

		public void displayLink() {
			System.out.print(task.getTitle() + "\n");

		}

	}

	public Node first;
	public Node last;
	public int size = 0;
	public int index = 0;
	Task[] basicArray;
	SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");

	public void add(Task task) throws NullTaskException {
	}

	public int size() {
		return size;
	}

	public boolean remove(Task task) throws NullTaskException {
		return true;
	}

	public Task getTask(int index) {
		return null;
	}

	@Override
	public int hashCode() {
		if (this instanceof ArrayTaskList) {
			final int prime = 31;
			int result = 1;
			result = prime * result + Arrays.hashCode(basicArray);
			result = prime * result + index;
			result = prime * result + size;
			return result;
		} else {
			final int prime = 43;
			int result = 4;
			@SuppressWarnings("deprecation")
			int r = this.getTask(this.index).getTime().getDay();
			result = index > 5 ? prime * result * 3 : prime * result * 2;
			result = prime * result + index + r;
			result = prime * result + size;
			return result;

		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this instanceof ArrayTaskList) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			TaskList other = (TaskList) obj;
			if (!Arrays.equals(basicArray, other.basicArray))
				return false;

			if (format == null) {
				if (other.format != null)
					return false;
			} else if (!format.equals(other.format))
				return false;
			if (index != other.index)
				return false;

			if (size != other.size)
				return false;

			return true;
		} else {

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

	public Object clone() throws CloneNotSupportedException {

		if (this instanceof ArrayTaskList) {

			ArrayTaskList clonedArray = new ArrayTaskList();
			for (Task l : this) {
				try {
					clonedArray.add(l);
				} catch (NullTaskException e) {
					e.printStackTrace();
				}
			}

			return clonedArray;

		} else {

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
	}

	public int index() {

		return index;
	}

}