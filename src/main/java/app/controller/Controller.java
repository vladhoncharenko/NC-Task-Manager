package app.controller;

import java.io.*;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import app.model.*;
import app.model.Task;
import app.util.DateUtil;
import app.util.TaskComparator;
import app.util.TimeInterval;
import app.view.CalendarView;
import app.view.ShowTaskEditView;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.apache.log4j.Logger;

public class Controller implements DateFormat {

    final static Logger logger = Logger.getLogger(Controller.class);

    private ObservableList<Task> userData = FXCollections.observableArrayList();
    private ObservableList<Task> userDataCal = FXCollections.observableArrayList();
    @FXML
    private TableView<Task> taskTable;
    @FXML
    private TableColumn<Task, String> taskName;
    @FXML
    private TableColumn<Task, String> taskTime;
    @FXML
    private Label taskNameLabel;
    @FXML
    private Label taskStartDateLabel;
    @FXML
    private Label taskEndDateLabel;
    @FXML
    private Label taskIntervalLabel;
    @FXML
    private Label taskActiveLabel;
    @FXML
    private Label notifyLabel;
    @FXML
    private Label intervalLabel;
    @FXML
    private Label endTimeLabel;
    @FXML
    private Label chooseLabel;
    @FXML
    private Label taskNLabel;
    @FXML
    private Label startLabel;
    @FXML
    private Label activeLabel;

    public ObservableList<Task> getTaskData() {
        return userData;
    }


    /**
     * Called when the user clicks on the delete button.
     */
    @FXML
    private void handleDeleteTask() {

        int selectedIndex = taskTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            taskTable.getItems().remove(selectedIndex);
            logger.info("Task was deleted");
            writeData();
            logger.info("Data were written in handleDeleteTask()");

        } else {

            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Task Selected");
            alert.setContentText("Please select a task in the table.");
            logger.warn("Task deleting error");
            alert.showAndWait();
        }
    }

    @FXML
    private void initialize() throws IOException, ParseException {

        taskNameLabel.setVisible(false);
        taskStartDateLabel.setVisible(false);
        taskEndDateLabel.setVisible(false);
        taskIntervalLabel.setVisible(false);
        taskActiveLabel.setVisible(false);
        intervalLabel.setVisible(false);
        endTimeLabel.setVisible(false);
        taskEndDateLabel.setVisible(false);
        intervalLabel.setVisible(false);
        endTimeLabel.setVisible(false);
        taskNLabel.setVisible(false);
        startLabel.setVisible(false);
        activeLabel.setVisible(false);

        logger.info("Initializing...");
        initData();
        logger.info("Data were initialized");
        startScheduledExecutorService();

        taskName.setCellValueFactory(new PropertyValueFactory<Task, String>("title"));
        taskTime.setCellValueFactory(new PropertyValueFactory<Task, String>("time"));

        taskTable.setItems(userData);

        taskTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Task>() {
            public void changed(ObservableValue<? extends Task> observable, Task oldValue, Task newValue) {
                showTaskDetails(newValue);

            }
        });

    }

    protected ObservableList<Task> setCalendar() throws IOException, ParseException {
        userDataCal.clear();
        ArrayTaskList ud = new ArrayTaskList();
        InputStream is = null;
        try {
            is = new DataInputStream(new FileInputStream("data.bin"));
        } catch (FileNotFoundException e) {
            //TODO заполнить все error
            logger.error("", e);
        }
        TaskIO.read(ud, is);

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        String forTime = format.format(c.getTime());
        c.add(Calendar.DATE, 1);
        String toTime = format.format(c.getTime());
        LinkedTaskList ud2 = (LinkedTaskList) Tasks.incoming(ud, forTime, toTime);

        for (Task l : ud2) {
            userDataCal.add(l);
        }
        Collections.sort(userDataCal, new TaskComparator());
        logger.info("Notification was updated");
        return userDataCal;
    }

    //300 Mb RAM
    private void startScheduledExecutorService() {
        logger.info("startScheduledExecutorService started");
        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        scheduler.scheduleAtFixedRate(new Runnable() {
            int counter = 0;

            @Override
            public void run() {
                counter++;
                if (counter <= 100000) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (setCalendar().size()!=0) {
                                    notifyLabel.setText("Next Task is " + setCalendar().get(0).getTitle() + " at " + setCalendar().get(0).getExecutionDate());

                                } else {
                                    notifyLabel.setText("There are no tasks in 7 days to do");

                                }
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            } catch (ParseException e2) {
                                e2.printStackTrace();
                            }
                        }
                    });

                }
            }

        }, 0, 1, TimeUnit.SECONDS);
    }

    private void showTaskDetails(Task task) {

        if (task != null) {
            if (!task.isRepeated()) {
                intervalLabel.setVisible(false);
                endTimeLabel.setVisible(false);
                taskEndDateLabel.setVisible(false);
            } else {
                intervalLabel.setVisible(true);
                endTimeLabel.setVisible(true);
                taskEndDateLabel.setVisible(true);
            }
            taskNameLabel.setVisible(true);
            taskStartDateLabel.setVisible(true);
            taskActiveLabel.setVisible(true);
            taskNLabel.setVisible(true);
            startLabel.setVisible(true);
            activeLabel.setVisible(true);
            taskIntervalLabel.setVisible(true);

            taskNameLabel.setText(task.getTitle());
            taskStartDateLabel.setText(DateUtil.format(task.getStartTime()));
            taskEndDateLabel.setText(DateUtil.format(task.getEndTime()));
            taskIntervalLabel.setText(TimeInterval.TimeToInterval(task));
            taskActiveLabel.setText((task.isActive()) ? "Yes" : "No");
            logger.info("Task Details were showed");
            chooseLabel.setVisible(false);
        }else{

            taskNameLabel.setVisible(false);
            taskStartDateLabel.setVisible(false);
            taskEndDateLabel.setVisible(false);
            taskIntervalLabel.setVisible(false);
            taskActiveLabel.setVisible(false);
            intervalLabel.setVisible(false);
            endTimeLabel.setVisible(false);
            taskEndDateLabel.setVisible(false);
            intervalLabel.setVisible(false);
            endTimeLabel.setVisible(false);
            taskNLabel.setVisible(false);
            startLabel.setVisible(false);
            activeLabel.setVisible(false);
            chooseLabel.setVisible(true);
        }

    }

    private void initData() throws IOException, ParseException {
        ArrayTaskList ud = new ArrayTaskList();
        InputStream is = null;

        try {
            is = new DataInputStream(new FileInputStream("data.bin"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        logger.info("New inputStream created");
        TaskIO.read(ud, is);
        logger.info("Data has been read");
        for (Task l : ud) {
            userData.add(l);
        }

    }

    @FXML
    private void handleNewTask() {
        Task tempTask = new Task();
        boolean okClicked = ShowTaskEditView.showTaskEditDialog(tempTask);
        if (okClicked) {
            if(TaskEditorController.isOk) {
                getTaskData().add(tempTask);
                logger.info("New Task was added to list");
                writeData();
                logger.info("Data were written in handleNewTask()");
                TaskEditorController.isOk=false;
            }
        }
    }

    @FXML
    private void handleCalendar() {
        logger.info("Showing calendar dialog window...");
        CalendarView.showCalendarDialog();
    }
    @FXML
    private void onNotifyClicked() {

        try {
            showTaskDetails(setCalendar().get(0));
            taskTable.getSelectionModel().select(setCalendar().get(0));
        } catch (IOException e1) {
            logger.error("onNotifyClicked-", e1);
        } catch (ParseException e2) {
            logger.error("onNotifyClicked-", e2);
        }


    }

    @FXML
    private void handleEditTask() {
        Task selectedTask = taskTable.getSelectionModel().getSelectedItem();
        int i = taskTable.getSelectionModel().getFocusedIndex();
        if (selectedTask != null) {

            boolean okClicked = ShowTaskEditView.showTaskEditDialog(selectedTask);

            if (okClicked) {
                Task selectedTask2 = taskTable.getSelectionModel().getSelectedItem();
                showTaskDetails(selectedTask);
                writeData();

                getTaskData().removeAll(getTaskData());
                logger.info("All tasks were removed");

                try {
                    initData();
                    logger.info("Data were reinitialized");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                showTaskDetails(selectedTask2);

                taskTable.getSelectionModel().select(i);
            }

        } else {

            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Task Selected");
            alert.setContentText("Please select a Task in the table.");
            logger.warn("No Task Selected");
            alert.showAndWait();
        }

    }


    private void writeData() {
        ArrayTaskList ud = new ArrayTaskList();
        for (Task l : getTaskData()) {
            try {
                ud.add(l);
            } catch (NullTaskException e) {
                e.printStackTrace();
            }
        }

        OutputStream os = null;
        try {
            os = new DataOutputStream(new FileOutputStream("data.bin"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        logger.info("New DataOutputStream was opened");
        TaskIO.write(ud, os);
        logger.info("Data were written in binary file");
    }


}