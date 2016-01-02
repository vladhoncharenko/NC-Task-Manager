package app.util;

import app.model.Task;


public class TimeInterval {

    public static String TimeToInterval(Task task) {
        StringBuilder stringInterval = new StringBuilder();
        int interval = task.getRepeatInterval();
        int[] timeAmount = new int[4];
        int[] timeCoeff = {86400, 3600, 60, 1};
        String[] time = {" day", " hour", " minute", " second"};
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
        return stringInterval.toString();
    }

}
