package app.controller;

import java.io.IOException;
import java.text.ParseException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import app.Main;
import app.model.Task;
import app.util.DateUtil;

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

		// taskName.setCellValueFactory(data -> data.getValue().getTitle());

		taskTable.setItems(userData);

		/*taskTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showTaskDetails(newValue));*/


		 taskTable.getSelectionModel().selectedItemProperty().addListener(new
		  ChangeListener<Task>() { public void changed(ObservableValue<?
                    extends Task> observable, Task oldValue, Task newValue) {
		  showTaskDetails(newValue); } });


	}

	private void showTaskDetails(Task task) {
		if (task != null) {

			taskNameLabel.setText(task.getTitle());
			taskStartDateLabel.setText(DateUtil.format(task.getStartTime()));
			taskEndDateLabel.setText(DateUtil.format(task.getEndTime()));
			taskIntervalLabel.setText(Integer.toString(task.getRepeatInterval()));
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
		userData.add(new Task("Second", "11-12-2016 15:07:00.000"));
		userData.add(new Task("Fourth", "06-12-2016 10:40:00.341"));
		userData.add(new Task("5 \"Task\" -th", "05-12-2016 10:47:00.000", "23-12-2016 8:48:00.000", 100));
		userData.add(new Task("6-th \"easy\" task", "12-12-2016 00:47:00.050", "22-12-2016 12:42:00.000", 325660));
		userData.add(new Task("7-th", "05-12-2016 10:47:00.000", "09-12-2016 23:47:00.005", 140000));
	}

	@FXML
	private void handleNewPerson() {
		Task tempTask = new Task();
		boolean okClicked = Main.showPersonEditDialog(tempTask);
		if (okClicked) {
			getTaskData().add(tempTask);
		}
	}

	@FXML
	private void handleEditPerson() {
		Task selectedPerson = taskTable.getSelectionModel().getSelectedItem();

		if (selectedPerson != null) {
			boolean okClicked = Main.showPersonEditDialog(selectedPerson);
			if (okClicked) {
				showTaskDetails(selectedPerson);
			}

		} else {

			Alert alert = new Alert(AlertType.WARNING);

			alert.setTitle("No Selection");
			alert.setHeaderText("No Task Selected");
			alert.setContentText("Please select a Task in the table.");

			alert.showAndWait();
		}
	}
}