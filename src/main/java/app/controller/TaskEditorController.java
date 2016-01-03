package app.controller;

import java.io.IOException;
import java.text.ParseException;
import app.util.DateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import app.model.Task;


public class TaskEditorController {

    @FXML
    private TextField taskNameField;
    @FXML
    private TextField taskStartDateField;
    @FXML
    private TextField taskEndDateField;
    @FXML
    private TextField taskIntervalField;
    @FXML
    private TextField taskActiveField;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private ChoiceBox<String> intervalCB;

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
     * Sets the person to be edited in the dialog.
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
                e.printStackTrace();
            }
        }
        taskStartDateField.setPromptText("HH:mm:ss.SSS");
        startDatePicker.setPromptText("Select start date");


        if (task.getEndTime() != null) {
            taskEndDateField.setText(DateUtil.formatDateToTime(task.getEndTime()));
            try {
                endDatePicker.setValue(DateUtil.formatLocaleDateToDate(task.getEndTime()));
            } catch (ParseException e) {
                e.printStackTrace();
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


        taskActiveField.setText(Boolean.toString(task.isActive()));

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
        if (isInputValid()) {
            if (!((taskIntervalField.getText()).equals("")) && (Integer.parseInt(taskIntervalField.getText()) > 0)) {
                if ((endDatePicker.getValue() != null) && (taskEndDateField.getText() != null)) {
                    if (!(DateUtil.formatDate(startDatePicker.getValue()) + " " + taskStartDateField.getText()).equals(DateUtil.formatDate(endDatePicker.getValue()) + " " + taskEndDateField.getText())) {
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

                        task.setTime((DateUtil.formatDate(startDatePicker.getValue()) + " " + taskStartDateField.getText()), (DateUtil.formatDate(endDatePicker.getValue()) + " " + taskEndDateField.getText()),
                                intervalValue);
                        task.setActive(taskActiveField.getText().equals("true"));
                    } else {
                        task.setTitle(taskNameField.getText());
                        task.setTime(DateUtil.formatDate(startDatePicker.getValue()) + " " + taskStartDateField.getText());
                        task.setActive(taskActiveField.getText().equals("true"));
                    }
                } else {
                    task.setTitle(taskNameField.getText());
                    task.setTime(DateUtil.formatDate(startDatePicker.getValue()) + " " + taskStartDateField.getText());
                    task.setActive(taskActiveField.getText().equals("true"));
                }

            } else {
                task.setTitle(taskNameField.getText());
                task.setTime(DateUtil.formatDate(startDatePicker.getValue()) + " " + taskStartDateField.getText());
                task.setActive(taskActiveField.getText().equals("true"));
            }

        }

        okClicked = true;
        dialogStage.close();
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

        if (taskNameField.getText() == null || taskNameField.getText().length() == 0) {
            errorMessage += "Enter a valid Task Name!\n";
        }
        if (taskStartDateField.getText() == null || taskStartDateField.getText().length() == 0) {
            errorMessage += "Enter a valid Task Start Time!\n";
        }


        if (taskActiveField.getText() == null || taskActiveField.getText().length() == 0) {
            errorMessage += "Enter true or false only!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {

            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please enter a valid data!");
            alert.setContentText(errorMessage);
            alert.showAndWait();

            return false;
        }
    }


}