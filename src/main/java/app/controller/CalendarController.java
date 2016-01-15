package app.controller;

import app.model.*;
import app.util.DateUtil;
import app.util.TaskComparator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.*;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class CalendarController implements DateFormat {

    final static Logger logger = Logger.getLogger(CalendarController.class);
    @FXML
    private TableView<Task> taskTable;
    @FXML
    private TableColumn<Task, String> taskName;
    @FXML
    private Label taskNameLabel;
    @FXML
    private Label taskExecutionTime;
    @FXML
    private Label taskActiveLabel;
    @FXML
    private Button closeButton;
    @FXML
    private Label chooseLabel;
    @FXML
    private Label taskNLabel;
    @FXML
    private Label nextExecutionLabel;
    @FXML
    private Label actveLabel;

    private ObservableList<Task> userData2 = FXCollections.observableArrayList();
    public ObservableList<Task> getTaskData() {
        return userData2;
    }
       private ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    private File dataFile=new File(classLoader.getResource("data/dataFile.bin").getFile());

    @FXML
    private void initialize() throws IOException, ParseException {
        taskNLabel.setVisible(false);
        nextExecutionLabel.setVisible(false);
        actveLabel.setVisible(false);
        initData();
        logger.info("Data were initialized");
        taskName.setCellValueFactory(new PropertyValueFactory<Task, String>("title"));

        taskTable.setItems(userData2);

        taskTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Task>() {
            public void changed(ObservableValue<? extends Task> observable, Task oldValue, Task newValue) {
                showTaskDetails(newValue);
            }
        });

    }

    private void showTaskDetails(Task task) {
        if (task != null) {
            taskNLabel.setVisible(true);
            nextExecutionLabel.setVisible(true);
            actveLabel.setVisible(true);
            taskNameLabel.setText(task.getTitle());

            if (task.isRepeated()) {
                taskExecutionTime.setText((DateUtil.format(task.getExecutionDate())));
            } else {
                taskExecutionTime.setText(DateUtil.format(task.getStartTime()));
            }
            taskActiveLabel.setText((task.isActive()) ? "Yes" : "No");
            chooseLabel.setVisible(false);

        } else {
            taskNLabel.setVisible(false);
            nextExecutionLabel.setVisible(false);
            actveLabel.setVisible(false);

            taskNameLabel.setText("");
            taskExecutionTime.setText("");

            taskActiveLabel.setText("");

        }
        logger.info("Task was set");
    }

    private void initData() throws IOException, ParseException {

        ArrayTaskList ud = new ArrayTaskList();
        TaskIO.readBinary(ud, dataFile);

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        String forTime = format.format(c.getTime());
        c.add(Calendar.DATE, 7);
        String toTime = format.format(c.getTime());
        LinkedTaskList ud2 = (LinkedTaskList) Tasks.incoming(ud, forTime, toTime);

        for (Task l : ud2) {
            userData2.add(l);
        }
        Collections.sort(userData2, new TaskComparator());

    }

    @FXML
    private void onBackButton() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
        logger.info("Calendar window was closed");
    }


}