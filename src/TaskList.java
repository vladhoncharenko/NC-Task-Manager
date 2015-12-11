import java.io.Serializable;

abstract public class TaskList implements Iterable<Task>, Cloneable, DateFormat,Serializable {

	
	private static final long serialVersionUID = 1L;
	public int size = 0;
	public int index = 0;

	public void add(Task task) throws NullTaskException {
	}

	public int size() {
		return this.size;

	}

	public int index() {
		return this.index;
	}

	public boolean remove(Task task) throws NullTaskException {
		return true;
	}

	public Task getTask(int index) {
		return null;
	}

	public int hashCode() {

		return 0;

	}

	public boolean equals(Object obj) {

		return true;
	}

	public Object clone() throws CloneNotSupportedException {

		return null;
	}
}