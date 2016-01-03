package app.util;

import app.model.Task;

import java.util.Comparator;

public class TaskComparator implements Comparator<Task> {

    public int compare(Task o1, Task o2) {
        return o1.getExecutionDate().compareTo(o2.getExecutionDate());
    }
}
