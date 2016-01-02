
package app.model;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.text.ParseException;
import java.util.Date;

/**
 * Main Class
 * 
 * @author Vlad Honcharenko
 * @version 1.0
 */

public class Main implements DateFormat {
	@SuppressWarnings("deprecation")
	public static void main(String args[]) throws ParseException, NullTaskException, IOException {

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

			firstTask = new Task("First A", "10-12-2016 19:59:00.000");
			secondTask = new Task("Second", "11-12-2016 01:07:00.000", "11-12-2016 19:47:00.000", 250);

			taskOne = new Task("First", "13-12-2016 22:30:00.000");
			taskTwo = new Task("Second", "11-12-2016 15:07:00.000");
			taskThree = new Task("Third", "10-12-2016 9:00:34.404");
			taskFour = new Task("Fourth", "06-12-2016 10:40:00.341");
			task5 = new Task("5 \"Task\" -th", "05-12-2016 10:47:00.000", "23-12-2016 8:48:00.000", 100);
			task6 = new Task("6-th \"easy\" task", "12-12-2016 00:47:00.050", "22-12-2016 12:42:00.000", 325660);
			task7 = new Task("7-th", "05-12-2016 10:47:00.000", "09-12-2016 23:47:00.005", 140000);

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
			System.out.println("\n" + "Next time of execution " + firstTask.nextTimeAfter("11-12-2016 10:40:00.000"));
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

		System.out.println("###########");

		System.out.println(task7.getRepeatInterval());

		try {
			System.out.println(firstTaskList.getTask(2).getTitle());
		} catch (ArrayIndexOutOfBoundsException e) {

			System.err.println("\n" + e.toString());

		}

		System.out.println("_________ LINKED LIST _________");

		LinkedTaskList newList = new LinkedTaskList();
		LinkedTaskList newList333 = new LinkedTaskList();

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

		try {

			newList333.add(taskThree);
			newList333.add(taskFour);
			newList333.add(taskOne);
			newList333.add(taskTwo);

			newList333.add(task5);

			newList333.add(task7);
			newList333.add(task6);

		} catch (NullTaskException e1) {
			System.err.println("\n" + e1.toString());
		}
		newList.toString();

		System.out.println(newList.size());

		newList.remove(taskThree);
		System.out.println(newList.size());

		try {
			System.out.println(newList.getTask(5).getTitle());
		} catch (Exception e) {
			System.err.println("\n" + e.toString());
		}

		newList.toString();

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

		Task task6clone = task6.clone();
		newList.add(task6clone);
		System.out.println("Task 6 Clone");
		task6clone.toString();
		System.out.println("Original Task 6");
		task6.toString();

		System.out.println("Task 6 = Task 7?=" + task6.equals(task7));
		System.out.println("Task 6 = Task 6 clone?=" + task6.equals(task6clone));
		System.out.println("Task 6's Hash Code = Task 7's Hash Code?=" + (task6.hashCode() == task7.hashCode()));
		System.out.println(
				"Task 6's Hash Code = Task 6 clone's Hash Code?=" + (task6.hashCode() == task6clone.hashCode()));

		try {
			task6clone.setTime("01-12-2017 10:40:00.00");
		} catch (IOException e1) {

			e1.printStackTrace();
		}

		System.out.println("Task 6 = modified Task 6 clone?=" + task6.equals(task6clone));

		System.out.println("LinkedTaskList Clone");

		newList.toString();

		LinkedTaskList newList2 = null;
		try {
			newList2 = (LinkedTaskList) newList.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		System.out.println("newList HC " + newList.hashCode());
		System.out.println("newList2 HC " + newList2.hashCode());
		newList2.toString();
		System.out.println("newList = newList2?=" + newList2.equals(newList));
		System.out.println("newList33333 = newList=" + newList333.equals(newList));
		newList2.remove(task5);
		System.out.println("newList2 HC " + newList2.hashCode());

		System.out.println("newList = newList2?=" + newList2.equals(newList));

		System.out.println("ArrayTaskList Clone");

		firstTaskList.toString();

		ArrayTaskList firstTaskList2 = null;
		try {
			firstTaskList2 = (ArrayTaskList) firstTaskList.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		System.out.println("firstTaskList HC " + firstTaskList.hashCode());
		System.out.println("firstTaskList2 HC " + firstTaskList2.hashCode());
		System.out.println("firstTaskList = firstTaskList2?=" + firstTaskList.equals(firstTaskList2));

		System.out.println("firstTaskList = firstTaskList2?=" + firstTaskList.equals(firstTaskList2));

		System.out.println("*******");
		firstTaskList2.toString();

		System.out
				.println("HashCode of ArrayTaskList " + firstTaskList.hashCode() + "\n" + "Hash Code of LinkedTaskList "
						+ newList.hashCode() + "\n" + "Hash Code of taskOne " + task7.hashCode());

		System.out.println("Task 4 to string:");
		taskFour.toString();

		System.out.println("Task 7 to string:");
		task7.toString();

		String Timef = ("13-10-2015 10:40:00.00.000");
		Date Time = format.parse(Timef);
		Time.setMinutes(Time.getMinutes() + 800);
		System.out.println("+86" + Time.toString());

		System.out.println("Incoming Tasks____________:");
		System.out.println("ARRAY:");

		try {

			(Tasks.incoming(firstTaskList, "24-11-2016 10:40:00.00", "25-12-2016 10:40:00.000")).toString();

		} catch (IOException e) {
			System.err.println("\n" + e.toString());
		}

		System.out.println("\n" + "LL");
		try {
			(Tasks.incoming(newList, "24-11-2016 10:40:00.00", "25-12-2016 10:40:00.000")).toString();

		} catch (IOException e) {

			System.err.println("\n" + e.toString());

		}
		System.out.println("\n");
		System.out.println("Arr index:" + firstTaskList.index());
		System.out.println("Linked index:" + newList.index());

		System.out.println("Arr s:" + firstTaskList.size());
		System.out.println("Linked s" + newList.size());
		try {
			firstTaskList.remove(task5);
		} catch (NullTaskException e) {

			e.printStackTrace();
		}

		newList.remove(task6clone);
		newList.remove(taskFour);
		newList.remove(taskTwo);
		newList2.remove(task6clone);

		System.out.println("Arr index:" + firstTaskList.index());
		System.out.println("Linked index:" + newList.index());

		System.out.println("Arr s:" + firstTaskList.size());
		System.out.println("Linked s" + newList.size());

		System.out.println("LL Calendar");
		try {
			Tasks.calendar(newList, "25-11-2016 10:40:00.000", "20-12-2016 10:40:00.000");
		} catch (IOException e) {

			e.printStackTrace();
		}

		System.out.println("Aray Calendar");

		try {
			Tasks.calendar(firstTaskList, "25-11-2016 10:40:00.000", "20-12-2016 10:40:00.000");
		} catch (IOException e) {
			e.printStackTrace();
		}
		taskFour.setActive(false);

		System.out.println("____INPUT/OUTPUT____");
		System.out.println("____Binary IO Stream____");

		LinkedTaskList LTL = new LinkedTaskList();
		LinkedTaskList LTL2 = new LinkedTaskList();
		System.out.println("List before:");
		firstTaskList.toString();

		OutputStream os = new DataOutputStream(new FileOutputStream("data.bin"));
		TaskIO.write(firstTaskList, os);

		System.out.println("List after:");
		InputStream is = new DataInputStream(new FileInputStream("data.bin"));
		TaskIO.read(LTL, is);

		System.out.println("____Binary File IO____");

		ClassLoader classLoader = Main.class.getClassLoader();
		File taskBinaryFile = new File(classLoader.getResource("data/data.bin").getFile());



		System.out.println("List before");
		newList.toString();

		TaskIO.writeBinary(newList, taskBinaryFile);
		System.out.println("List after");
		TaskIO.readBinary(LTL2, taskBinaryFile);

		System.out.println("____Text IO Stream____");

		System.out.println("List before");
		firstTaskList.toString();

		LinkedTaskList LTL3 = new LinkedTaskList();

		Writer bw = new BufferedWriter(new FileWriter("data.txt"));
		TaskIO.write(firstTaskList, bw);

		System.out.println("List after");
		Reader inputStreamReader = new BufferedReader(new FileReader("data.txt"));
		TaskIO.read(LTL3, inputStreamReader);

		System.out.println("____Text File IO____");

		LinkedTaskList ATL2 = new LinkedTaskList();


		File  taskTextFile = new File(classLoader.getResource("data/tasks.txt").getFile());


		System.out.println("List before:");
		newList2.toString();

		TaskIO.writeText(newList2, taskTextFile);

		System.out.println("List after:");
		TaskIO.readText(ATL2, taskTextFile);
		
	

	}

}