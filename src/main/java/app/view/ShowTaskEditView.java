package app.view;

import app.controller.TaskEditorController;
import app.model.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.IOException;
public class ShowTaskEditView {
    final static Logger logger = Logger.getLogger(ShowTaskEditView.class);
    public static boolean showTaskEditDialog(Task task) {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ShowTaskEditView.class.getResource("/view/TaskEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Task");
            dialogStage.initModality(Modality.WINDOW_MODAL);

            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            TaskEditorController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setTask(task);

            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            logger.error("In showTaskEditDialog()-", e);
            return false;
        }
    }
}
