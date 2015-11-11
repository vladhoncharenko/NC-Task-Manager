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

			firstTask = new Task("First", 17);
			secondTask = new Task("Second", 10, 22, 2);
			taskOne = new Task("First ", 18);
			taskTwo = new Task("Second", 8);
			taskThree = new Task("Third ", 9);
			taskFour = new Task("Fourth ", 5);
			task5 = new Task("5-th", 5, 18, 1);
			task6 = new Task("6-th ", 4, 20, 5);
			task7 = new Task("7-th", 1, 4, 1);

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

		newList.toString();

		System.out.println(newList.size());

		newList.remove(taskThree);
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

		newList.toString();

		System.out.println("Incoming Tasks:");
		try {
			((LinkedTaskList) newList.incomingTask(19, 22)).toString();

		} catch (IOException e) {

			System.err.println("\n" + e.toString());

		}

		System.out.println("Linked List ITERATOR");

		// for (Task h : newList) {
		for (Task l : newList) {
			System.out.println(l.getTitle());
		}
		// }

		System.out.println("Array List ITERATOR");
		// for (Task h : newList) {
		for (Task l : firstTaskList) {
			System.out.println(l.getTitle());
		}
		// }

		
		Task task6clone=task6.clone();
		System.out.println("Task 6 Clone");
		task6clone.toString();
		System.out.println("Original Task 6");
		task6.toString();
		
		System.out.println("Task 6 = Task 7?="+task6.equals(task7));
		System.out.println("Task 6 = Task 6 clone?="+task6.equals(task6clone));
		System.out.println("Task 6's Hash Code = Task 7's Hash Code?="+ (task6.hashCode()==task7.hashCode()));
		System.out.println("Task 6's Hash Code = Task 6 clone's Hash Code?="+(task6.hashCode()==task6clone.hashCode()));
		
		
		try {
			task6clone.setTime(7);
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
		
		System.out.println("Task 6 = modified Task 6 clone?="+task6.equals(task6clone));
		
		
		
		
		
		
		
		System.out.println("LinkedTaskList Clone");

		newList.toString();

		LinkedTaskList newList2 = null;
		try {
			newList2 = (LinkedTaskList) newList.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		System.out.println("newList HC "+newList.hashCode());
		System.out.println("newList2 HC "+newList2.hashCode());
		System.out.println("newList = newList2?="+newList.equals(newList2));
		System.out.println("*******");
		newList2.toString();

		
		
		
		
		
		System.out.println("ArrayTaskList Clone");

		firstTaskList.toString();

		ArrayTaskList firstTaskList2 = null;
		try {
			firstTaskList2 = (ArrayTaskList) firstTaskList.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		System.out.println("firstTaskList HC "+firstTaskList.hashCode());
		System.out.println("firstTaskList2 HC "+firstTaskList2.hashCode());
		System.out.println("firstTaskList = firstTaskList2?="+firstTaskList.equals(firstTaskList2));
		System.out.println("*******");
		firstTaskList2.toString();

		System.out.println("HashCode of ArrayTaskList " + firstTaskList.hashCode() + "\n" + "Hash Code of LinkedTaskList "
						+ newList.hashCode() + "\n" + "Hash Code of taskOne " + task7.hashCode());

		System.out.println("Task 4 to string:");
		taskFour.toString();

		System.out.println("Task 7 to string:");
		task7.toString();

	}

}