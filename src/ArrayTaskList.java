
import java.util.Arrays;

public class ArrayTaskList extends TaskList {

	private Task[] basicArray;

	public ArrayTaskList() {
		basicArray = new Task[10];
	}

	@Override
	public void add(Task task) throws NullTaskException {
		if (task == null)
			throw new NullTaskException("Task can not be null"); // Task object can't refer to null

		if (index == basicArray.length) {
			increaseArray();
		}
		basicArray[index] = task;
		size++;
		index++;
	}

	public int size() {
		return this.size;
	}

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

	@Override
	public void setNewValue(int index, Task task) throws NullTaskException {

		if (index <= -1 || index > this.index)
			throw new ArrayIndexOutOfBoundsException(
					"Enter a valid task index, that exist's in array, in method setNewValue");
		if (task == null)
			throw new NullTaskException("Task can not be null");

		basicArray[index] = task;
	}

	@Override
	public Task getTask(int index) {
		if (index <= -1 || index > this.index)
			throw new ArrayIndexOutOfBoundsException(
					"Enter a valid task index, that exist's in array, in method getTask ");

		return basicArray[index];
	}

	public void increaseArray() {

		int newSize = basicArray.length + 10;
		basicArray = Arrays.copyOf(basicArray, newSize);

	}

}