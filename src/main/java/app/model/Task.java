package app.model;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * Class, that describes Task
 *
 * @author Vlad Honcharenko
 * @version 1.0
 */

public class Task implements DateFormat, Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    final static Logger logger = Logger.getLogger(Task.class);
    private String title;
    private Date time;
    private Date start;
    private Date end;
    private int interval;
    private boolean active = true;
    private boolean repeated;
    private String javaFxTimeCoeff = "Empty";
    private Date executionDate;
    Date currentDate = new Date();


    public String getJavaFxTimeCoeff() {
        return javaFxTimeCoeff;
    }

    public void setJavaFxTimeCoeff(String javaFxTimeCoeff) {
        this.javaFxTimeCoeff = javaFxTimeCoeff;
    }

    public Date getExecutionDate() {
        return executionDate;
    }

    public void setExecutionDate(Date executionDate) {
        this.executionDate = executionDate;
    }

    /**
     * Not repeated task constructor
     *
     * @param titleConstructor
     * @param timeConstructor
     * @throws IOException
     * @throws ParseException
     */

    public Task(String titleConstructor, String timeConstructor) throws IOException, ParseException {

        title = titleConstructor;
        time = format.parse(timeConstructor);
        repeated = false;
        active = true;
        executionDate = format.parse(timeConstructor);

    }

    /**
     * Repeated task constructor
     *
     * @param titleConstructor
     * @param startConstructor
     * @param endConstructor
     * @param intervalConstructor
     * @throws IOException
     * @throws ParseException
     */

    public Task(String titleConstructor, String startConstructor, String endConstructor, int intervalConstructor)
            throws IOException, ParseException {

        title = titleConstructor;
        start = format.parse(startConstructor);
        end = format.parse(endConstructor);
        interval = intervalConstructor;
        repeated = true;
        active = true;


        if (intervalConstructor <= 0)
            throw new IOException("Interval can not be 0 or negative");
    }

    /**
     * Standard constructor
     */
    public Task() {

    }

    /**
     * Returns task title
     *
     * @return title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Sets task title
     *
     * @param Title
     */
    public void setTitle(String Title) {
        this.title = Title;
    }

    /**
     * Returns true if task is active, returns false if task is not active
     *
     * @return this.active
     */
    public boolean isActive() {
        return this.active;
    }

    /**
     * Sets true if task is active, returns false if task is not active
     *
     * @param Active
     */
    public void setActive(boolean Active) {
        this.active = Active;
    }

    /**
     * Returns true if task is repeated, returns false if task is not repeated
     *
     * @return this.repeated
     */
    public boolean isRepeated() {
        return this.repeated;
    }

    /**
     * If task is repeated returns start time of task, if task is not repeated
     * returns time of task execution
     *
     * @return !isRepeated() ? this.time : this.start
     */
    public Date getTime() {
        return !isRepeated() ? this.time : this.start;
    }

    /**
     * Sets not repeated task time, string types
     *
     * @param Time
     * @throws IOException
     * @throws ParseException
     */
    public void setTime(String Time) throws IOException, ParseException {

        this.time = format.parse(Time);

        if (isRepeated()) {
            this.repeated = false;
        }
        if (!isRepeated()) {
            executionDate = format.parse(Time);
        }

    }

    /**
     * If task is repeated returns start time of task, if task is not repeated
     * returns time of task execution
     *
     * @return this.start : this.time
     */
    public Date getStartTime() {
        return isRepeated() ? this.start : this.time;
    }

    /**
     * If task is repeated returns end time of task, if task is not repeated
     * returns time of task execution
     *
     * @return isRepeated() ? this.end : this.time
     */
    public Date getEndTime() {
        return isRepeated() ? this.end : this.time;
    }

    /**
     * Returns repeat interval
     *
     * @return isRepeated() ? this.interval : 0
     */
    public int getRepeatInterval() {
        return isRepeated() ? this.interval : 0;
    }

    /**
     * Sets repeat interval
     *
     * @param Interval
     */
    public void setRepeatInterval(int Interval) {
        this.interval = Interval;
    }

    /**
     * Sets repeated task time
     *
     * @param Start
     * @param End
     * @param Interval
     * @throws IOException
     * @throws ParseException
     */
    public void setTime(String Start, String End, int Interval) throws IOException, ParseException {

        this.start = format.parse(Start);
        this.end = format.parse(End);
        this.interval = Interval;

        if (!isRepeated()) {
            this.repeated = true;

        }

        if (Interval <= 0)
            throw new IOException("Interval can not be 0 or negative");

    }

    /**
     * Sets repeated task start and end time, String types
     *
     * @param Start
     * @param End
     * @throws IOException
     * @throws ParseException
     */
    public void setTime(String Start, String End) throws IOException, ParseException {

        this.start = format.parse(Start);
        this.end = format.parse(End);

        if (!isRepeated()) {
            this.repeated = true;

        }


    }

    /**
     * Returns next task time execution after some date
     *
     * @param currentS
     * @return Time
     * @throws IOException
     * @throws ParseException
     */
    @SuppressWarnings("deprecation")
    public Date nextTimeAfter(String currentS) throws IOException, ParseException {
        Date current = format.parse(currentS);
        if (current.before(currentDate))
            throw new IOException("Time can not be before this date");

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


    public boolean equals(Task otherTask) {
        boolean isEquals = false;
        if (this.isRepeated()) {
            if (this.title.equals(otherTask.title)
                    && this.active == otherTask.active
                    && this.start.equals(otherTask.start)
                    && this.end.equals(otherTask.end)
                    && this.interval == otherTask.interval) {
                isEquals = true;
            }
        } else {

            if (this.title.equals(otherTask.title)
                    && this.active == otherTask.active
                    && this.time.equals(otherTask.time)) {
                isEquals = true;
            }
        }
        return isEquals;
    }

    /**
     * Outputs Task
     *
     * @return null
     */
    @Override
    public String toString() {
        if (this.isRepeated()) {
            return ("Task " + title + " starts at " + start + ", ends at " + end + ", with interval "
                    + interval + " minutes, active=" + active);
        } else
            return ("Task " + title + " starts at " + time + " active=" + active);

    }


    public Task clone() {
        Task newClonedTask = new Task();
        if (this.isRepeated()) {
            newClonedTask.setTitle(this.title);
            try {
                newClonedTask.setTime(this.start, this.end, this.interval);
            } catch (IOException e) {
                logger.error("In clone()-", e);
            }

            return newClonedTask;
        } else
            newClonedTask.setTitle(this.title);
        try {
            newClonedTask.setTime(this.time);
        } catch (IOException e) {
            logger.error("In clone()-", e);
        }
        return newClonedTask;
    }

    /**
     * Sets not repeated task time,Date type
     *
     * @param time2
     * @throws IOException
     */
    private void setTime(Date time2) throws IOException {

        this.time = time2;

        if (isRepeated()) {
            this.repeated = false;
        }

    }

    /**
     * Sets repeated task time, Date type
     *
     * @param start2
     * @param end2
     * @param interval2
     * @throws IOException
     */
    private void setTime(Date start2, Date end2, int interval2) throws IOException {

        this.start = start2;
        this.end = end2;
        this.interval = interval2;

        if (!isRepeated()) {
            this.repeated = true;

        }

        if (interval2 <= 0)
            throw new IOException("Interval can not be 0 or negative");

    }

}