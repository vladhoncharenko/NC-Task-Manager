import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Class designed for working with task lists
 * 
 * @author Vlad Honcharenko
 * @version 1.0
 */

public class Tasks implements DateFormat, Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates Task List, that will execute from fromS to toS in future
	 * 
	 * @param tasks
	 * @param fromS
	 * @param toS
	 * @return incomingTaskList
	 * @throws IOException
	 * @throws ParseException
	 */
	@SuppressWarnings("deprecation")
	public static Iterable<Task> incoming(Iterable<Task> tasks, String fromS, String toS)
			throws IOException, ParseException {

		Iterable<Task> incomingTaskList = new LinkedTaskList();

		Date from = format.parse(fromS);
		Date to = format.parse(toS);

		for (int i = 0; i < ((TaskList) tasks).index(); i++) {
			beginOfTheCycle: if (!((TaskList) tasks).getTask(i).isRepeated()) {
				if ((from.before(((TaskList) tasks).getTask(i).getTime())
						|| from.equals(((TaskList) tasks).getTask(i).getTime())
								&& (((TaskList) tasks).getTask(i).getTime().before(to)
										|| ((TaskList) tasks).getTask(i).getTime().equals(to)))) {
					try {
						((TaskList) incomingTaskList).add(((TaskList) tasks).getTask(i));

					} catch (NullTaskException e) {
						System.err.println("\n" + e.toString());
					}

				}
			} else {
				for (Date Time = ((TaskList) tasks).getTask(i).getStartTime(); (Time
						.before(((TaskList) tasks).getTask(i).getEndTime())
						|| Time.equals(((TaskList) tasks).getTask(i).getEndTime())); Time
								.setMinutes(Time.getMinutes() + ((TaskList) tasks).getTask(i).getRepeatInterval())) {
					if ((from.before(Time) || from.equals(Time)) && (Time.before(to) || Time.equals(to))) {

						try {
							((TaskList) incomingTaskList).add(((TaskList) tasks).getTask(i));

						} catch (NullTaskException e) {
							System.err.println("\n" + e.toString());
						}
						break beginOfTheCycle;
					}
				}

			}

		}

		return incomingTaskList;

	}

	/**
	 * Creates unique tasks calendar, that will execute from fromS to toS in
	 * future
	 * 
	 * @param tasks
	 * @param fromS
	 * @param toS
	 * @return null
	 * @throws IOException
	 * @throws ParseException
	 */
	public static SortedMap<Date, Set<Task>> calendar(Iterable<Task> tasks, String fromS, String toS)
			throws IOException, ParseException {

		Map<Date, Task> newMap = new TreeMap<Date, Task>();

		LinkedTaskList newLinkedTaskList = (LinkedTaskList) incoming(tasks, fromS, toS);

		for (int i = 0; i < ((TaskList) tasks).index(); i++) {
			newMap.put(newLinkedTaskList.getTask(i).getTime(), newLinkedTaskList.getTask(i));

		}

		Set<Entry<Date, Task>> newSet = newMap.entrySet();

		for (Entry<Date, Task> obj : newSet) {
			System.out.print(obj.getKey() + ": ");
			System.out.println(obj.getValue().getTitle());
		}

		return null;

	}

}
