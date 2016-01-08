package app.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import app.util.DateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import app.model.Task;
import org.apache.log4j.Logger;


public class TaskEditorController {
    final static Logger logger = Logger.getLogger(TaskEditorController.class);
    @FXML
    private TextField taskNameField;
    @FXML
    private TextField taskStartDateField;
    @FXML
    private TextField taskEndDateField;
    @FXML
    private TextField taskIntervalField;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private ChoiceBox<String> intervalCB;
    @FXML
    private CheckBox taskActiveCB;
    protected static boolean isOk = false;
    ObservableList<String> listCB = FXCollections.observableArrayList("Day(s)", "Hour(s)", "Minute(s)", "Second(s)");
    final int[] coeff = new int[]{86400, 3600, 60, 1};
    private Stage dialogStage;
    private Task task;
    private boolean okClicked = false;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        intervalCB.setItems(listCB);
        intervalCB.setValue("Day(s)");
        logger.info("Data were initialized");
    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Sets the task to be edited in the dialog.
     *
     * @param task
     */

    public void setTask(Task task) {
        this.task = task;
        if (task.getTitle() != null) {
            taskNameField.setText(task.getTitle());
        }
        taskNameField.setPromptText("Task Title");

        if (task.getStartTime() != null) {
            taskStartDateField.setText(DateUtil.formatDateToTime(task.getStartTime()));
            try {
                startDatePicker.setValue(DateUtil.formatLocaleDateToDate(task.getStartTime()));
            } catch (ParseException e) {
                logger.error("In setTask()-", e);
            }
        }
        taskStartDateField.setPromptText("HH:mm:ss.SSS");
        startDatePicker.setPromptText("Select start date");

        if (task.getEndTime() != null) {
            taskEndDateField.setText(DateUtil.formatDateToTime(task.getEndTime()));
            try {
                endDatePicker.setValue(DateUtil.formatLocaleDateToDate(task.getEndTime()));
            } catch (ParseException e) {
                logger.error("In setTask()-", e);
            }
        }
        taskEndDateField.setPromptText("HH:mm:ss.SSS");
        endDatePicker.setPromptText("Select end date");

        String intervalValue = "";

        if (task.getJavaFxTimeCoeff().equals("Day(s)")) {
            intervalValue = String.valueOf(task.getRepeatInterval() / coeff[0]);
            intervalCB.setValue("Day(s)");

        } else if (task.getJavaFxTimeCoeff().equals("Hour(s)")) {
            intervalValue = String.valueOf(task.getRepeatInterval() / coeff[1]);
            intervalCB.setValue("Hour(s)");
        } else if (task.getJavaFxTimeCoeff().equals("Minute(s)")) {
            intervalValue = String.valueOf(task.getRepeatInterval() / coeff[2]);
            intervalCB.setValue("Minute(s)");
        } else if (task.getJavaFxTimeCoeff().equals("Second(s)")) {
            intervalValue = String.valueOf(task.getRepeatInterval() / coeff[3]);
            intervalCB.setValue("Second(s)");
        }

        taskIntervalField.setText(intervalValue);
        taskActiveCB.setSelected(task.isActive());
        logger.info("Task was set");
    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks ok.
     *
     * @throws ParseException
     * @throws IOException
     */
    @FXML
    private void handleOk() throws IOException, ParseException {
        boolean ok = false;

        if (isInputValid()) {
            if (!((taskIntervalField.getText()).equals("")) && (Integer.parseInt(taskIntervalField.getText()) > 0)) {
                if ((endDatePicker.getValue() != null) && (taskEndDateField.getText() != null)) {
                    if (!(DateUtil.formatDate(startDatePicker.getValue()) + " " + taskStartDateField.getText()).equals(DateUtil.formatDate(endDatePicker.getValue())
                            + " " + taskEndDateField.getText())) {
                        task.setTitle(taskNameField.getText());

                        int intervalValue;
                        task.setJavaFxTimeCoeff(intervalCB.getValue());

                        if (intervalCB.getValue().equals("Day(s)")) {
                            intervalValue = Integer.parseInt(taskIntervalField.getText()) * coeff[0];

                        } else if (intervalCB.getValue().equals("Hour(s)")) {
                            intervalValue = Integer.parseInt(taskIntervalField.getText()) * coeff[1];
                        } else if (intervalCB.getValue().equals("Minute(s)")) {
                            intervalValue = Integer.parseInt(taskIntervalField.getText()) * coeff[2];
                        } else {
                            intervalValue = Integer.parseInt(taskIntervalField.getText()) * coeff[3];
                        }

                        task.setTime((DateUtil.formatDate(startDatePicker.getValue()) + " " + taskStartDateField.getText()),
                                (DateUtil.formatDate(endDatePicker.getValue()) + " " + taskEndDateField.getText()), intervalValue);
                        task.setActive(taskActiveCB.isSelected());
                        ok = true;
                        isOk = true;
                    } else {
                        task.setTitle(taskNameField.getText());
                        task.setTime(DateUtil.formatDate(startDatePicker.getValue()) + " " + taskStartDateField.getText());
                        task.setActive(taskActiveCB.isSelected());
                        ok = true;
                        isOk = true;
                    }
                } else {
                    task.setTitle(taskNameField.getText());
                    task.setTime(DateUtil.formatDate(startDatePicker.getValue()) + " " + taskStartDateField.getText());
                    task.setActive(taskActiveCB.isSelected());
                    ok = true;
                    isOk = true;
                }

            } else {
                task.setTitle(taskNameField.getText());
                task.setTime(DateUtil.formatDate(startDatePicker.getValue()) + " " + taskStartDateField.getText());
                task.setActive(taskActiveCB.isSelected());
                ok = true;
                isOk = true;
            }

        }

        okClicked = true;
        logger.info("Information was written in a task");
        if (ok) {
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        Date c = new Date();
        if (taskNameField.getText() == null || taskNameField.getText().length() == 0) {
            errorMessage += "Enter a valid Task Name!\n";
        }
        if (taskStartDateField.getText() == null || taskStartDateField.getText().length() == 0) {

            errorMessage += "Enter Task Start Time!";

        }
        if (startDatePicker.getValue() == null) {
            errorMessage += "Choose a valid Task Start Date!\n";
        }
        if (startDatePicker.getValue() != null) {
            if (DateUtil.formatLDtoDate(startDatePicker.getValue().plusDays(1)).before(c)) {
                errorMessage += "Date can not be in the past!\n";
            }
        }
        if (endDatePicker.getValue() != null) {

            if (startDatePicker.getValue().isAfter(endDatePicker.getValue())) {
                errorMessage += "Start Date can not be after End Date!\n";
            }

        }

        if ((taskIntervalField.getText().length() != 0)) {

            if (!taskIntervalField.getText().matches("^[+-]?\\d+$")) {
                errorMessage += "In Interval Field must be only numbers!\n";

            }

        }
        if (taskStartDateField.getText().length() != 0) {
            if (!taskStartDateField.getText().matches("([0-2][0-9]:[0-5][0-9]:[0-5][0-9]\\.[0-9][0-9][0-9])")) {
                errorMessage += "In Task Start Time field must be only numbers HH:mm:ss.SSS!";
            }
        }
        if (taskEndDateField.getText().length() != 0) {

            if (!taskStartDateField.getText().matches("([0-2][0-9]:[0-5][0-9]:[0-5][0-9]\\.[0-9][0-9][0-9])")) {
                errorMessage += "In Task End Time field must be only numbers, with pattern  HH:mm:ss.SSS!\n";
            }

        }
        if (errorMessage.length() == 0) {
            return true;
        } else {

            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please enter a valid data!");
            alert.setContentText(errorMessage);
            logger.warn("Data is not valid!" + errorMessage);
            alert.showAndWait();

            return false;
        }
    }

}