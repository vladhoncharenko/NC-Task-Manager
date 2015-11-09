
public class LinkedTaskList extends TaskList {

	public LinkedTaskList() {

	}

	@Override
	public void add(Task task) throws NullTaskException {
		if (task == null)
			throw new NullTaskException("Task can not be null");

		final Node lastNode = last;
		final Node newNode = new Node(lastNode, task, null);
		last = newNode;
		if (lastNode == null)
			first = newNode;
		else
			lastNode.next = newNode;
		size++;

	}

	public int size() {
		return size;
	}

	@Override
	public boolean remove(Task task) {
		if (task == null) {
			for (Node x = first; x != null; x = x.next) {
				if (x.task == null) {
					unlink(x);
					return true;
				}
			}
		} else {
			for (Node x = first; x != null; x = x.next) {
				if (task.equals(x.task)) {
					unlink(x);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void setNewValue(int index, Task task) throws NullTaskException {

		if (index < 0 || index > size)
			throw new IndexOutOfBoundsException("Enter a valid task index, that exist's in this list");
		if (task == null)
			throw new NullTaskException("Task can not be null");

		Node x = getNode(index);
		x.task = task;

	}

	@Override
	public Task getTask(int index) {
		if (index < 0 || index > size)
			throw new IndexOutOfBoundsException("Enter a valid task index, that exist's in this list");
		index--;
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

	Node getNode(int index) {
		if (index < 0 || index > size)
			throw new IndexOutOfBoundsException("Enter a valid task index, that exist's in this list");
		index--;
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

	public void displayList() {
		Node current = first;
		while (current != null) {
			current.displayLink();
			current = current.next;

		}
	}

}
