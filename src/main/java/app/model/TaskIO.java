package app.model;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.text.ParseException;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class, that allow read/write Task Lists
 * 
 * @author Vlad Honcharenko
 * @version 1.0
 */

public class TaskIO {

	/**
	 * Writes Task List in stream in binary form
	 * 
	 * @param tasks
	 * @param outStream
	 */
	public static void write(TaskList tasks, OutputStream outStream) {
		Calendar cal = Calendar.getInstance();
		DataOutputStream dataOutputStream = new DataOutputStream(outStream);
		try {

			dataOutputStream.writeByte(tasks.size());

			for (Task l : tasks) {
				dataOutputStream.writeByte(l.getTitle().length());
				dataOutputStream.writeUTF(l.getTitle());
				if (l.isActive()) {
					dataOutputStream.writeByte(1);
				} else {
					dataOutputStream.writeByte(0);
				}

				if (l.isRepeated()) {
					dataOutputStream.writeBoolean(true);
					dataOutputStream.writeInt(l.getRepeatInterval());

					cal.setTime(l.getStartTime());

					dataOutputStream.writeShort(cal.get(Calendar.DAY_OF_MONTH));
					dataOutputStream.writeShort(cal.get(Calendar.MONTH) + 1);
					dataOutputStream.writeShort(cal.get(Calendar.YEAR));
					dataOutputStream.writeShort(cal.get(Calendar.HOUR_OF_DAY));
					dataOutputStream.writeShort(cal.get(Calendar.MINUTE));
					dataOutputStream.writeShort(cal.get(Calendar.SECOND));
					dataOutputStream.writeShort(cal.get(Calendar.MILLISECOND));

					cal.setTime(l.getEndTime());

					dataOutputStream.writeShort(cal.get(Calendar.DAY_OF_MONTH));
					dataOutputStream.writeShort(cal.get(Calendar.MONTH) + 1);
					dataOutputStream.writeShort(cal.get(Calendar.YEAR));
					dataOutputStream.writeShort(cal.get(Calendar.HOUR_OF_DAY));
					dataOutputStream.writeShort(cal.get(Calendar.MINUTE));
					dataOutputStream.writeShort(cal.get(Calendar.SECOND));
					dataOutputStream.writeShort(cal.get(Calendar.MILLISECOND));
				} else {
					dataOutputStream.writeBoolean(false);

					cal.setTime(l.getTime());

					dataOutputStream.writeShort(cal.get(Calendar.DAY_OF_MONTH));
					dataOutputStream.writeShort(cal.get(Calendar.MONTH) + 1);
					dataOutputStream.writeShort(cal.get(Calendar.YEAR));
					dataOutputStream.writeShort(cal.get(Calendar.HOUR_OF_DAY));
					dataOutputStream.writeShort(cal.get(Calendar.MINUTE));
					dataOutputStream.writeShort(cal.get(Calendar.SECOND));
					dataOutputStream.writeShort(cal.get(Calendar.MILLISECOND));

				}
			}
		} catch (IOException e) {
			System.err.println("\n" + "Binary Write:" + e.toString());

		} finally {

			try {
				dataOutputStream.flush();
			} catch (IOException e2) {
				System.err.println("\n" + "Binary Write flush():" + e2.toString());
			}

			try {
				dataOutputStream.close();
			} catch (IOException e3) {
				System.err.println("\n" + "Binary Write close():" + e3.toString());
			}

		}
	}

	/**
	 * Reads Task List from stream in binary form
	 * 
	 * @param tasks
	 * @param inStream
	 */
	public static void read(TaskList tasks, InputStream inStream) {

		DataInputStream dataInputStream = new DataInputStream(inStream);
		try {
			int t = dataInputStream.readByte();
			for (int i = 0; i < t; i++) {
				dataInputStream.readByte();
				Task l = new Task();
				l.setTitle(dataInputStream.readUTF());

				if (dataInputStream.readByte() == 1) {
					l.setActive(true);
				} else {
					l.setActive(false);
				}

				try {
					if (dataInputStream.readBoolean()) {
						l.setRepeatInterval(dataInputStream.readInt());

						l.setTime(
								dataInputStream.readShort() + "-" + dataInputStream.readShort() + "-"
										+ dataInputStream.readShort() + " " + dataInputStream.readShort() + ":"
										+ dataInputStream.readShort() + ":" + dataInputStream.readShort() + "."
										+ dataInputStream.readShort(),
								dataInputStream.readShort() + "-" + dataInputStream.readShort() + "-"
										+ dataInputStream.readShort() + " " + dataInputStream.readShort() + ":"
										+ dataInputStream.readShort() + ":" + dataInputStream.readShort() + "."
										+ dataInputStream.readShort());

					} else {

						l.setTime(dataInputStream.readShort() + "-" + dataInputStream.readShort() + "-"
								+ dataInputStream.readShort() + " " + dataInputStream.readShort() + ":"
								+ dataInputStream.readShort() + ":" + dataInputStream.readShort() + "."
								+ dataInputStream.readShort());
					}
				} catch (ParseException e) {

					System.err.println("\n" + "Binary Read Parse:" + e.toString());
				}

				try {
					tasks.add(l);
				} catch (NullTaskException e1) {

					System.err.println("\n" + "Binary Read Add Task:" + e1.toString());
				}
			}
		} catch (IOException e2) {
			System.err.println("\n" + "Binary Read:" + e2.toString());

		} finally {
			try {
				dataInputStream.close();
			} catch (IOException e3) {
				System.err.println("\n" + "Binary Read close():" + e3.toString());
			}
		}
		tasks.toString();
	}

	/**
	 * Writes Task List in file in binary form
	 * 
	 * @param tasks
	 * @param file
	 */
	public static void writeBinary(TaskList tasks, File file) {

		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(file);
		} catch (FileNotFoundException e1) {
			System.err.println("\n" + "Binary File Write:" + e1.toString());
		}

		try {
			write(tasks, fileOutputStream);

		} finally {
			try {
				fileOutputStream.flush();
			} catch (IOException e) {
				System.err.println("\n" + "Binary File Write flush():" + e.toString());
			}
			try {
				fileOutputStream.close();
			} catch (IOException e2) {
				System.err.println("\n" + "Binary File Write close():" + e2.toString());
			}
		}
	}

	/**
	 * Reads Task List from file in binary form
	 * 
	 * @param tasks
	 * @param file
	 */
	public static void readBinary(TaskList tasks, File file) {

		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			System.err.println("\n" + "Binary File Read:" + e1.toString());
		}

		try {
			read(tasks, fileInputStream);
		} finally {
			try {
				fileInputStream.close();
			} catch (IOException e) {
				System.err.println("\n" + "Binary File Read close():" + e.toString());
			}
		}

	}

	/**
	 * Writes Task List in stream in text form
	 * 
	 * @param tasks
	 * @param out
	 */
	public static void write(TaskList tasks, Writer out) {

		BufferedWriter bufferedWriter = null;
		int taskCounter = 0;
		int lastTaskIndex = tasks.size() - 1;

		try {

			bufferedWriter = new BufferedWriter(out);

			for (Task h : tasks) {

				String newTitle = h.getTitle().replaceAll("\"", "\"\"");

				if (!h.isRepeated()) {

					StringBuffer dateString = new StringBuffer();
					dateString.append(DateFormat.format.format(h.getTime()));
					bufferedWriter.write(String.format("\"%s\" at [%s]", newTitle, dateString.toString()));

				} else {

					StringBuffer startDateString = new StringBuffer();
					startDateString.append(DateFormat.format.format(h.getStartTime()));
					StringBuffer endDateString = new StringBuffer();
					endDateString.append(DateFormat.format.format(h.getEndTime()));

					StringBuilder stringInterval = new StringBuilder();

					int interval = h.getRepeatInterval();
					int[] timeAmount = new int[4];
					int[] timeCoeff = { 86400, 3600, 60, 1 };
					String[] time = { " day", " hour", " minute", " second" };
					for (int i = 0; i < timeAmount.length; i++) {
						timeAmount[i] = interval / timeCoeff[i];
						interval = interval % timeCoeff[i];
					}
					for (int i = 0; i < timeAmount.length; i++) {
						if (timeAmount[i] != 0) {
							stringInterval.append(timeAmount[i]);
							stringInterval.append(time[i]);
							if (timeAmount[i] > 1) {
								stringInterval.append('s');

							}
							if (i < timeAmount.length - 1) {
								stringInterval.append(' ');
							}
						}
					}

					bufferedWriter.write(String.format("\"%s\" from [%s] to [%s] every [%s]", newTitle,
							startDateString.toString(), endDateString.toString(), stringInterval.toString()));

				}

				if (h.isActive()) {
				} else {
					bufferedWriter.write(" inactive");
				}
				bufferedWriter.write(taskCounter != lastTaskIndex ? ";" : ".");

				bufferedWriter.write(System.lineSeparator());
				taskCounter++;
			}
		} catch (IOException e) {
			System.err.println("\n" + "Text Write:" + e.toString());

		} finally {
			try {
				bufferedWriter.flush();
			} catch (IOException e1) {
				System.err.println("\n" + "Text Write flush():" + e1.toString());
			}
			try {
				bufferedWriter.close();
			} catch (IOException e2) {
				System.err.println("\n" + "Text Write close():" + e2.toString());
			}
		}
	}

	/**
	 * Reads Task List from stream in text form
	 * 
	 * @param tasks
	 * @param in
	 */
	public static void read(TaskList tasks, Reader in) {

		BufferedReader bufferedReader = new BufferedReader(in);

		String oneTask;
		String taskTitle = null;
		String time = null;
		String startTime = null;
		String endTime = null;
		boolean active;
		boolean repeated = false;
		int interval = 0;
		int[] timeCoeff = { 86400, 3600, 60, 1 };

		Pattern taskParserPattern = Pattern.compile(
				"^\"(.+)\"(( at (\\[.+\\]))|( from (\\[.+\\]) to (\\[.+\\]) every (\\[.+\\])))( inactive)?[;.]$");
		Pattern taskIntervalPattern = Pattern
				.compile("^\\[(([0-9]+) days?)? (([0-9]+) hours?)? (([0-9]+) minutes?)? (([0-9]+) seconds?)?\\]$");

		try {
			if (bufferedReader.ready()) {
				while ((oneTask = bufferedReader.readLine()) != null) {
					Task newTask = null;
					Matcher taskMatcher = taskParserPattern.matcher(oneTask);
					if (taskMatcher.matches()) {

						taskTitle = (taskMatcher.group(1)).replaceAll("\"\"", "\"");

						if (taskMatcher.group(2).contains("from")) {
							repeated = true;

							startTime = ((taskMatcher.group(6)).replace("[", "")).replace("]", "");
							endTime = ((taskMatcher.group(7)).replace("[", "")).replace("]", "");

							interval = 0;

							Matcher intervalMatcher = taskIntervalPattern.matcher(taskMatcher.group(8));
							if (intervalMatcher.matches()) {

								interval += timeCoeff[0]
										* (intervalMatcher.group(2) != null ? Integer.parseInt(intervalMatcher.group(2))
												: 0)
										+ timeCoeff[1] * (intervalMatcher.group(4) != null
												? Integer.parseInt(intervalMatcher.group(4)) : 0)
										+ timeCoeff[2] * (intervalMatcher.group(6) != null
												? Integer.parseInt(intervalMatcher.group(6)) : 0)
										+ timeCoeff[3] * (intervalMatcher.group(8) != null
												? Integer.parseInt(intervalMatcher.group(8)) : 0);
							}

							newTask = new Task(taskTitle, startTime, endTime, interval);

						} else if (taskMatcher.group(2).contains("at")) {

							time = ((taskMatcher.group(4)).replace("[", "")).replace("]", "");
							newTask = new Task(taskTitle, time);
						}

					}

					active = (taskMatcher.group(9) == null);
					newTask.setActive(active);

					try {
						tasks.add(newTask);
					} catch (NullTaskException e) {
						System.err.println("\n" + "Text Read Add Task:" + e.toString());
					}

					if (oneTask.charAt(oneTask.length() - 1) == '.') {
						break;
					}
				}
			}
		} catch (NumberFormatException e1) {
			System.err.println("\n" + "Text Read Format:" + e1.toString());
		} catch (IOException e2) {
			System.err.println("\n" + "Text Read IO:" + e2.toString());
		} catch (ParseException e3) {
			System.err.println("\n" + "Text Read Parse:" + e3.toString());
		} finally {
			try {
				bufferedReader.close();
			} catch (IOException e3) {
				System.err.println("\n" + "Text Read close():" + e3.toString());
			}
		}
		tasks.toString();

	}

	/**
	 * Writes Task List in file in text form
	 * 
	 * @param tasks
	 * @param file
	 * @throws IOException
	 */
	public static void writeText(TaskList tasks, File file) throws IOException {
		FileWriter fileWriter = null;

		try {
			fileWriter = new FileWriter(file);

		} catch (FileNotFoundException e1) {
			System.err.println("\n" + "Text File Write:" + e1.toString());
		}

		try {
			write(tasks, fileWriter);

		} finally {
			try {
				fileWriter.close();
			} catch (IOException e2) {
				System.err.println("\n" + "Text File Write close():" + e2.toString());
			}
		}
	}

	/**
	 * Writes Task List from file in text form
	 * 
	 * @param tasks
	 * @param file
	 * @throws IOException
	 */
	public static void readText(TaskList tasks, File file) throws IOException {

		FileReader fileReader = null;
		try {
			fileReader = new FileReader(file);
		} catch (FileNotFoundException e1) {
			System.err.println("\n" + "Text File Read:" + e1.toString());
		}

		try {
			read(tasks, fileReader);
		} finally {
			try {
				fileReader.close();
			} catch (IOException e) {
				System.err.println("\n" + "Text File Read close():" + e.toString());
			}
		}

	}

}
