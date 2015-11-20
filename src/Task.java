import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class Task implements DateFormat {

	private String title;
	private Date time;
	private Date start;
	private Date end;
	private int interval;
	private boolean active;
	private boolean repeated;
	Date currentDate = new Date();
	static final long ONE_MINUTE_IN_MILLIS = 60000;

	public Task(String titleConstructor, String timeConstructor) throws IOException, ParseException {

		title = titleConstructor;
		time = format.parse(timeConstructor);
		repeated = false;

		if (time.before(currentDate))
			throw new IOException("Time can not be negative");
	}

	public Task(String titleConstructor, String startConstructor, String endConstructor, int intervalConstructor)
			throws IOException, ParseException {

		title = titleConstructor;
		start = format.parse(startConstructor);
		end = format.parse(endConstructor);
		interval = intervalConstructor;
		repeated = true;

		if (start.before(currentDate) || end.before(currentDate))
			throw new IOException("Time can not be negative");
		if (intervalConstructor <= 0)
			throw new IOException("Interval can not be 0 or negative");
	}

	public Task() {

	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String Title) {
		this.title = Title;
	}

	public boolean isActive() {
		return this.active;
	}

	public void setActive(boolean Active) {
		this.active = Active;
	}

	public boolean isRepeated() {
		return this.repeated;
	}

	public Date getTime() {
		return !isRepeated() ? this.time : this.start;
	}

	public void setTime(String Time) throws IOException, ParseException {

		this.time = format.parse(Time);

		if (isRepeated()) {
			this.repeated = false;
		}
		if (time.before(currentDate))
			throw new IOException("Time can not be negative");
	}

	public Date getStartTime() {
		return isRepeated() ? this.start : this.time;
	}

	public Date getEndTime() {
		return isRepeated() ? this.end : this.time;
	}

	public int getRepeatInterval() {
		return isRepeated() ? this.interval : 0;
	}

	public void setTime(String Start, String End, int Interval) throws IOException, ParseException {

		this.start = format.parse(Start);
		this.end = format.parse(End);
		this.interval = Interval;

		if (!isRepeated()) {
			this.repeated = true;

		}
		if (start.before(currentDate) || end.before(currentDate))
			throw new IOException("Time can not be negative");
		if (Interval <= 0)
			throw new IOException("Interval can not be 0 or negative");

	}

	Calendar date = Calendar.getInstance();
	long t = date.getTimeInMillis();
	Date afterAddingTenMins = new Date(t + (10 * ONE_MINUTE_IN_MILLIS));

	@SuppressWarnings("deprecation")
	public Date nextTimeAfter(String currentS) throws IOException, ParseException {
		Date current = format.parse(currentS);
		if (current.before(currentDate))
			throw new IOException("Interval can not be 0 or negative");

		if (isActive() && !isRepeated() && (current.before(this.time) || current.equals(this.time))) {
			return this.time;
		} else {

			if (isActive() && isRepeated()) {

				for (Date Time = this.start; (Time.before(this.end) || Time.equals(this.end)); Time
						.setMinutes(Time.getMinutes() + this.interval)) {

					if (current.before(this.time) || current.equals(this.time)) {
						return Time;
					}
				}

			}
			return null;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (active ? 1231 : 1237);
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result + interval;
		result = prime * result + (repeated ? 1231 : 1237);
		result = prime * result + ((start == null) ? 0 : start.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		Task other = (Task) obj;

		if (!this.repeated == other.repeated)
			return false;
		if (active != other.active)
			return false;

		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;

		if (!this.repeated) {

			if (time == null) {
				if (other.time != null)
					return false;
			} else if (!time.equals(other.time))
				return false;

			return true;
		} else {

			if (end == null) {
				if (other.end != null)
					return false;
			} else if (!end.equals(other.end))
				return false;
			if (interval != other.interval)
				return false;
			if (repeated != other.repeated)
				return false;
			if (start == null) {
				if (other.start != null)
					return false;
			} else if (!start.equals(other.start))
				return false;

			return true;
		}
	}

	@Override
	public String toString() {
		if (this.isRepeated()) {
			System.out.println("Task " + title + " starts at " + start + ", ends at " + end + ", with interval "
					+ interval + " minutes, active=" + active);
		} else
			System.out.println("Task " + title + " starts at " + time + " active=" + active);
		return null;
	}

	public Task clone() {
		Task newClonedTask = new Task();
		if (this.isRepeated()) {
			newClonedTask.setTitle(this.title);
			try {
				newClonedTask.setTime(this.start, this.end, this.interval);
			} catch (IOException e) {
				e.printStackTrace();
			}

			return newClonedTask;
		} else
			newClonedTask.setTitle(this.title);
		try {
			newClonedTask.setTime(this.time);
		} catch (IOException e) {

			e.printStackTrace();
		}
		return newClonedTask;
	}

	private void setTime(Date time2) throws IOException {

		this.time = time2;

		if (isRepeated()) {
			this.repeated = false;
		}
		if (time.before(currentDate))
			throw new IOException("Time can not be negative");
	}

	private void setTime(Date start2, Date end2, int interval2) throws IOException {

		this.start = start2;
		this.end = end2;
		this.interval = interval2;

		if (!isRepeated()) {
			this.repeated = true;

		}
		if (start.before(currentDate) || end.before(currentDate))
			throw new IOException("Time can not be negative");
		if (interval2 <= 0)
			throw new IOException("Interval can not be 0 or negative");

	}

}