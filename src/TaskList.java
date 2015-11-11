import java.io.IOException;


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

	public void add(Task task) throws NullTaskException {
	}

	public int size() {
		return size;
	}

	public boolean remove(Task task) throws NullTaskException {
		return true;
	}

	public void setNewValue(int index, Task task) throws NullTaskException {
	}

	public Task getTask(int index) {
		return null;
	}

	public TaskList incomingTask(int from, int to) throws IOException {

		if (this instanceof ArrayTaskList) {

			ArrayTaskList incomingTaskList = new ArrayTaskList();

			for (int i = 0; i < index; i++) {
				beginOfTheCycle: if (!this.getTask(i).isRepeated()) {
					if (this.getTask(i).getTime() >= from && this.getTask(i).getTime() <= to) {
						try {
							incomingTaskList.add(getTask(i));

						} catch (NullTaskException e) {
							System.err.println("\n" + e.toString());
						}

					}
				} else {
					for (int Time = this.getTask(i).getStartTime(); Time <= this.getTask(i).getEndTime(); Time += this
							.getTask(i).getRepeatInterval()) {
						if (Time >= from && Time <= to) {
							try {
								incomingTaskList.add(getTask(i));

							} catch (NullTaskException e) {
								System.err.println("\n" + e.toString());
							}
							break beginOfTheCycle;
						}
					}

				}

			}
			return incomingTaskList;
		} else if (this instanceof LinkedTaskList) {
			index = 1;
			Node current = first;
			LinkedTaskList incomingLinkedTaskList = new LinkedTaskList();

			while (current != null) {

				beginOfTheCycle: if (!this.getTask(index).isRepeated()) {
					if (this.getTask(index).getTime() >= from && this.getTask(index).getTime() <= to) {
						try {
							incomingLinkedTaskList.add(getTask(index));

						} catch (NullTaskException e) {
							System.err.println("\n" + e.toString());
						}

					}
				} else {
					for (int Time = this.getTask(index).getStartTime(); Time <= this.getTask(index)
							.getEndTime(); Time += this.getTask(index).getRepeatInterval()) {
						if (Time >= from && Time <= to) {
							try {
								incomingLinkedTaskList.add(getTask(index));

							} catch (NullTaskException e) {
								System.err.println("\n" + e.toString());
							}

							break beginOfTheCycle;
						}
					}

				}
				index++;
				current = current.next;
			}
			return incomingLinkedTaskList;

		} else
			throw new IOException("Enter a valid data!");

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((first == null) ? 0 : first.hashCode());
		result = prime * result + index;
		result = prime * result + ((last == null) ? 0 : last.hashCode());
		result = prime * result + size;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaskList other = (TaskList) obj;
		if (first == null) {
			if (other.first != null)
				return false;
		} else if (!first.equals(other.first))
			return false;
		if (index != other.index)
			return false;
		if (last == null) {
			if (other.last != null)
				return false;
		} else if (!last.equals(other.last))
			return false;
		if (size != other.size)
			return false;
		return true;
	}

	public Object clone() throws CloneNotSupportedException {

		if (this instanceof ArrayTaskList) {

			ArrayTaskList clonedArray = (ArrayTaskList) super.clone();
			/*
			 * ArrayTaskList clonedArray = new ArrayTaskList(); this.basicArray
			 * = Arrays.copyOf(this.basicArray, this.size);
			 */
			return clonedArray;

		} else {

			LinkedTaskList clonedTaskList = (LinkedTaskList) super.clone();
			/*
			 * LinkedTaskList clonedTaskList = new LinkedTaskList();
			 * clonedTaskList.first = clonedTaskList.last = null;
			 * 
			 * for (Node x = first; x != null; x = x.next) try {
			 * clonedTaskList.add(x.task); } catch (NullTaskException e) {
			 * 
			 * e.printStackTrace(); }
			 */

			return clonedTaskList;
		}
	}

}
