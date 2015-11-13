import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

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

	public void setNewValue(int index, Task task) throws NullTaskException {
	}

	public Task getTask(int index) {
		return null;
	}

	@SuppressWarnings("deprecation")
	public TaskList incomingTask(String fromS, String toS) throws IOException, ParseException {

		Date from = format.parse(fromS);
		Date to = format.parse(toS);

		if (this instanceof ArrayTaskList) {

			ArrayTaskList incomingTaskList = new ArrayTaskList();

			for (int i = 0; i < index; i++) {
				beginOfTheCycle: if (!this.getTask(i).isRepeated()) {
					if ((from.before(this.getTask(i).getTime()) || from.equals(this.getTask(i).getTime())
							&& (this.getTask(i).getTime().before(to) || this.getTask(i).getTime().equals(to)))) {
						try {
							incomingTaskList.add(getTask(i));

						} catch (NullTaskException e) {
							System.err.println("\n" + e.toString());
						}

					}
				} else {
					for (Date Time = this.getTask(i).getStartTime(); (Time.before(this.getTask(i).getEndTime())
							|| Time.equals(this.getTask(i).getEndTime())); Time
									.setMinutes(Time.getMinutes() + this.getTask(i).getRepeatInterval())) {
						if ((from.before(Time) || from.equals(Time)) && (Time.before(to) || Time.equals(to))) {

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
					if ((from.before(this.getTask(index).getTime())
							|| from.equals(this.getTask(index).getTime()) && (this.getTask(index).getTime().before(to)
									|| this.getTask(index).getTime().equals(to)))) {
						try {
							incomingLinkedTaskList.add(getTask(index));

						} catch (NullTaskException e) {
							System.err.println("\n" + e.toString());
						}

					}
				} else {
					for (Date Time = this.getTask(index).getStartTime(); (Time.before(this.getTask(index).getEndTime())
							|| Time.equals(this.getTask(index).getEndTime())); Time
									.setMinutes(Time.getMinutes() + this.getTask(index).getRepeatInterval())) {

						if ((from.before(Time) || from.equals(Time)) && (Time.before(to) || Time.equals(to))) {
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
		result = prime * result + Arrays.hashCode(basicArray);
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
		if (!Arrays.equals(basicArray, other.basicArray))
			return false;
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
