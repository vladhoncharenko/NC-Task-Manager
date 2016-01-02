package app.controller;

import java.io.IOException;
import java.text.ParseException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import app.model.Task;
import app.util.DateUtil;

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

	private Stage dialogStage;
	private Task task;
	private boolean okClicked = false;

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
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
			taskStartDateField.setText(DateUtil.format(task.getStartTime()));
		}
		taskStartDateField.setPromptText("dd-MM-yyyy HH:mm:ss.SSS");

		if (task.getStartTime() != null) {
			taskEndDateField.setText(DateUtil.format(task.getEndTime()));
		}
		taskEndDateField.setPromptText("dd-MM-yyyy HH:mm:ss.SSS");

		taskIntervalField.setText(Integer.toString(task.getRepeatInterval()));
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
			if (task.isRepeated()) {
				task.setTitle(taskNameField.getText());
				task.setTime(taskStartDateField.getText(), taskEndDateField.getText(),
						Integer.parseInt(taskIntervalField.getText()));
				task.setActive(taskActiveField.getText().equals("true") ? true : false);
			} else {
				task.setTitle(taskNameField.getText());
				task.setTime(taskStartDateField.getText());
				task.setActive(taskActiveField.getText().equals("true") ? true : false);
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
		if (taskEndDateField.getText() == null || taskEndDateField.getText().length() == 0) {
			errorMessage += "Enter a valid Task End Time!\n";
		}

		if (taskIntervalField.getText() == null || taskIntervalField.getText().length() == 0) {
			errorMessage += "Enter a valid Task Interval!\n";
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