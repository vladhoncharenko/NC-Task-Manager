import java.io.IOException;
import java.text.ParseException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class Tasks implements DateFormat {

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

	public static SortedMap<Date, Set<Task>> calendar(Iterable<Task> tasks, String fromS, String toS)
			throws IOException, ParseException {

		Map<Date, Task> NMap = new TreeMap<Date, Task>();

		LinkedTaskList newL = (LinkedTaskList) incoming(tasks, fromS, toS);

		for (int i = 0; i < ((TaskList) tasks).index(); i++) {
			NMap.put(newL.getTask(i).getTime(), newL.getTask(i));

		}

		Set<Entry<Date, Task>> set = NMap.entrySet();

		for (Entry<Date, Task> me : set) {
			System.out.print(me.getKey() + ": ");
			System.out.println(me.getValue().getTitle());
		}

		return null;

	}

}
