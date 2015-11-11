import java.io.IOException;

public class Task {

	private String title;
	private int time;
	private int start;
	private int end;
	private int interval;
	private boolean active;
	private boolean repeated;

	public Task(String titleConstructor, int timeConstructor) throws IOException {

		if (timeConstructor < 0)
			throw new IOException("Time can not be negative");

		title = titleConstructor;
		time = timeConstructor;
		repeated = false;
	}

	public Task(String titleConstructor, int startConstructor, int endConstructor, int intervalConstructor)
			throws IOException {

		if (startConstructor < 0 || endConstructor < 0)
			throw new IOException("Time can not be negative");
		if (intervalConstructor <= 0)
			throw new IOException("Interval can not be 0 or negative");

		title = titleConstructor;
		start = startConstructor;
		end = endConstructor;
		interval = intervalConstructor;
		repeated = true;
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

	public int getTime() {
		return !isRepeated() ? this.time : this.start;
	}

	public void setTime(int Time) throws IOException {

		if (Time < 0)
			throw new IOException("Time can not be negative");

		this.time = Time;

		if (isRepeated()) {
			this.repeated = false;
		}
	}

	public int getStartTime() {
		return isRepeated() ? this.start : this.end - this.start;
	}

	public int getEndTime() {
		return isRepeated() ? this.end : this.end - this.start;
	}

	public int getRepeatInterval() {
		return isRepeated() ? this.interval : 0;
	}

	public void setTime(int Start, int End, int Interval) throws IOException {

		if (Start < 0 || End < 0)
			throw new IOException("Time can not be negative");
		if (Interval <= 0)
			throw new IOException("Interval can not be 0 or negative");

		this.start = Start;
		this.end = End;
		this.interval = Interval;

		if (!isRepeated()) {
			this.repeated = true;
		}

	}

	public int nextTimeAfter(int current) throws IOException {

		if (current < 0)
			throw new IOException("Interval can not be 0 or negative");

		if (isActive() && !isRepeated() && current <= this.time) {
			return this.time;
		} else {

			if (isActive() && isRepeated()) {
				for (int Time = this.start; Time <= this.end; Time += this.interval) {
					if (current <= Time) {
						return Time;
					}
				}

			}
			return -1;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (active ? 1231 : 1237);
		result = prime * result + end;
		result = prime * result + interval;
		result = prime * result + (repeated ? 1231 : 1237);
		result = prime * result + start;
		result = prime * result + time;
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
		if (active != other.active)
			return false;
		if (end != other.end)
			return false;
		if (interval != other.interval)
			return false;
		if (repeated != other.repeated)
			return false;
		if (start != other.start)
			return false;
		if (time != other.time)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public String toString() {
		if (this.isRepeated()) {
			System.out.println("Task " + title + " starts at " + start + ", ends at " + end + ", with interval "
					+ interval + " hour/s, active=" + active);
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

}
