import java.io.IOException;

abstract public class TaskList {

	public static class Node {
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

}
