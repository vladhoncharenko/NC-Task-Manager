import java.io.IOException;

public class Main {
	public static void main(String args[]) {

		ArrayTaskList firstTaskList = new ArrayTaskList();

		Task firstTask = null;
		Task secondTask = null;
		Task taskOne = null;
		Task taskTwo = null;
		Task taskThree = null;
		Task taskFour = null;
		Task task5 = null;
		Task task6 = null;
		Task task7 = null;

		try {

			firstTask = new Task("First Task", 17);
			secondTask = new Task("Second Task", 10, 22, 2);
			taskOne = new Task("First ", 18);
			taskTwo = new Task("Second", 8);
			taskThree = new Task("Third ", 9);
			taskFour = new Task("Fourth ", 5);
			task5 = new Task("5-th Task", 5, 18, 1);
			task6 = new Task("6-th Task", 4, 20, 5);
			task7 = new Task("7-th Task", 1, 4, 1);

		} catch (IOException e4) {
			System.err.println("\n" + e4.toString());
		}

		firstTask.setActive(true);
		secondTask.setActive(true);

		try {

			firstTaskList.add(taskOne);
			firstTaskList.add(taskTwo);
			firstTaskList.add(taskThree);
			firstTaskList.add(taskFour);
			firstTaskList.add(task5);
			firstTaskList.add(task6);
			firstTaskList.add(task7);

		} catch (NullTaskException e1) {
			System.err.println("\n" + e1.toString());
		}

		System.out.println("Name of first task is " + firstTask.getTitle());
		System.out.println("Time of first task is " + firstTask.getTime());
		System.out.println("\n" + "Name of the second task is " + secondTask.getTitle());
		System.out.println("Start time of first task is " + secondTask.getStartTime());
		System.out.println("End time of first task is " + secondTask.getEndTime());
		System.out.println("Interval time of first task is " + secondTask.getRepeatInterval());

		try {
			System.out.println("\n" + "Next time of execution " + firstTask.nextTimeAfter(18));
		} catch (IOException e4) {
			System.err.println("\n" + e4.toString());
		}

		System.out.println("\n" + "In array Tasks are " + (firstTaskList.size()) + " objects");

		try {
			firstTaskList.remove(taskTwo);
		} catch (NullTaskException e2) {
			System.err.println("\n" + e2.toString());
		}

		System.out.println("\n" + "In array Tasks are " + (firstTaskList.size()) + " objects" + "\n");

		for (int i = 0; i < firstTaskList.size(); i++) {
			System.out.println(firstTaskList.getTask(i).getTitle());
		}

		System.out.println("##########");

		try {
			for (int i = 0; i <= 2; i++) {
				System.out.println(firstTaskList.incomingTask(6, 15).getTask(i).getTitle());
			}
		} catch (IOException e) {
			System.err.println("\n" + e.toString());
		}

		System.out.println("###########");

		System.out.println(task7.getRepeatInterval());

		try {
			System.out.println(firstTaskList.getTask(2).getTitle());
		} catch (ArrayIndexOutOfBoundsException e) {

			System.err.println("\n" + e.toString());

		}

		try {

			firstTaskList.setNewValue(45, taskOne);

		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("\n" + e.toString());
		} catch (NullTaskException ex) {
			System.err.println("\n" + ex.toString());
		}

		System.out.println("_________ LINKED LIST _________");

		LinkedTaskList newList = new LinkedTaskList();

		try {

			newList.add(taskOne);
			newList.add(taskTwo);
			newList.add(taskThree);
			newList.add(taskFour);
			newList.add(task5);
			newList.add(task6);
			newList.add(task7);

		} catch (NullTaskException e1) {
			System.err.println("\n" + e1.toString());
		}

		newList.displayList();

		System.out.println(newList.size());

		newList.remove(taskTwo);
		System.out.println(newList.size());

		try {
			newList.setNewValue(47, task6);

		} catch (IndexOutOfBoundsException e) {
			System.err.println("\n" + e.toString());
		} catch (NullTaskException ex) {
			System.err.println("\n" + ex.toString());
		}

		try {
			System.out.println(newList.getTask(55).getTitle());
		} catch (Exception e) {
			System.err.println("\n" + e.toString());
		}

		newList.displayList();

		System.out.println("Incoming Tasks:");
		try {
			((LinkedTaskList) newList.incomingTask(19, 22)).displayList();

		} catch (IOException e) {

			System.err.println("\n" + e.toString());

		}

	}

}