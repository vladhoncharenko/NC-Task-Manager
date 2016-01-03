package app.controller;

import java.io.*;
import java.text.ParseException;

import app.model.*;
import app.util.DateUtil;
import app.util.TimeInterval;
import app.view.CalendarView;
import app.view.ShowTaskEditView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


public class Controller {


    private ObservableList<Task> userData = FXCollections.observableArrayList();


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