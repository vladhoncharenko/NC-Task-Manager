
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayTaskList extends TaskList {

	public ArrayTaskList() {
		basicArray = new Task[10];
	}

	@Override
	public void add(Task task) throws NullTaskException {
		if (task == null)
			throw new NullTaskException("Task can not be null");

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

	@Override
	public String toString() {
		for (int i = 0; i < index; i++) {
			System.out.println(this.getTask(i).getTitle());
		}
		return null;
	}

	@Override
	public Iterator<Task> iterator() {

		return new ArrayListIterator();
	}

	private class ArrayListIterator implements Iterator<Task> {

		int cursor;

		public boolean hasNext() {
			return cursor != size;
		}

		public Task next() {

			int i = cursor;
			if (i >= size)
				throw new NoSuchElementException("This element is not exist");
			Task[] elementData = basicArray;
			if (i >= elementData.length)
				throw new ConcurrentModificationException();
			cursor = i + 1;
			return elementData[i];
		}

	}

}