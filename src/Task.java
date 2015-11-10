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

}
