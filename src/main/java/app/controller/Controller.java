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
import javafx.concurrent.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;


public class Controller implements DateFormat {


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
            writeData();

        } else {

            Alert alert = new Alert(AlertType.WARNING);

            alert.setTitle("No Selection");
            alert.setHeaderText("No Task Selected");
            alert.setContentText("Please select a task in the table.");

            alert.showAndWait();
        }
    }

    @FXML
    private void initialize() throws IOException, ParseException {
        initData();

        startScheduledExecutorService();


        taskName.setCellValueFactory(new PropertyValueFactory<Task, String>("title"));
        taskTime.setCellValueFactory(new PropertyValueFactory<Task, String>("time"));

        taskTable.setItems(userData);

        taskTable.getSelectionModel().selectedItemProperty().addListener(new
                                                                                 ChangeListener<Task>() {
                                                                                     public void changed(ObservableValue<?
                                                                                             extends Task> observable, Task oldValue, Task newValue) {
                                                                                         showTaskDetails(newValue);
                                                                                     }
                                                                                 });


    }

    protected ObservableList<Task> setCal() throws IOException, ParseException {
        userDataCal.clear();
        ArrayTaskList ud = new ArrayTaskList();
        InputStream is = null;
        try {
            is = new DataInputStream(new FileInputStream("data.bin"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        TaskIO.read(ud, is);

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        String forTime = format.format(c.getTime());
        c.add(Calendar.DATE, 1);
        String toTime = format.format(c.getTime());
        LinkedTaskList ud2 = (LinkedTaskList) Tasks.incoming(ud, forTime, toTime);

        for (Task l : ud2) {
            System.out.println(l.getTitle());
            userDataCal.add(l);
        }
        Collections.sort(userDataCal, new TaskComparator());
        return userDataCal;
    }


    private void startScheduledExecutorService() {

        final ScheduledExecutorService scheduler= Executors.newScheduledThreadPool(1);

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
                                        notifyLabel.setText("Next Task is " + setCal().get(0).getTitle() + " at " + setCal().get(0).getExecutionDate());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                        }
                    }

                },0,1,TimeUnit.SECONDS);
    }


    private void showTaskDetails(Task task) {
        if (task != null) {

            taskNameLabel.setText(task.getTitle());
            taskStartDateLabel.setText(DateUtil.format(task.getStartTime()));
            taskEndDateLabel.setText(DateUtil.format(task.getEndTime()));
            taskIntervalLabel.setText(TimeInterval.TimeToInterval(task));
            taskActiveLabel.setText(Boolean.toString(task.isActive()));

        } else {

            taskNameLabel.setText("");
            taskStartDateLabel.setText("");
            taskEndDateLabel.setText("");
            taskIntervalLabel.setText("");
            taskActiveLabel.setText("");


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
        TaskIO.read(ud, is);

        for (Task l : ud) {
            userData.add(l);
        }


    }

    @FXML
    private void handleNewPerson() {
        Task tempTask = new Task();
        boolean okClicked = ShowTaskEditView.showTaskEditDialog(tempTask);
        if (okClicked) {
            getTaskData().add(tempTask);
            writeData();


        }
    }

    @FXML
    private void handleCalendar() {
        CalendarView.showCalendarDialog();
    }

    @FXML
    private void handleEditPerson() {
        Task selectedPerson = taskTable.getSelectionModel().getSelectedItem();
        int i = taskTable.getSelectionModel().getFocusedIndex();
        if (selectedPerson != null) {

            boolean okClicked = ShowTaskEditView.showTaskEditDialog(selectedPerson);

            if (okClicked) {
                Task selectedTask = taskTable.getSelectionModel().getSelectedItem();
                showTaskDetails(selectedPerson);
                writeData();

                getTaskData().removeAll(getTaskData());

                try {
                    initData();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                showTaskDetails(selectedTask);

                taskTable.getSelectionModel().select(i);
            }

        } else {

            Alert alert = new Alert(AlertType.WARNING);

            alert.setTitle("No Selection");
            alert.setHeaderText("No Task Selected");
            alert.setContentText("Please select a Task in the table.");

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
        TaskIO.write(ud, os);
    }


}