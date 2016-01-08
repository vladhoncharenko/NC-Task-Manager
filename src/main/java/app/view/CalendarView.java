package app.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

public class CalendarView {
    final static Logger logger = Logger.getLogger(CalendarView.class);
    public static boolean showCalendarDialog() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(CalendarView.class.getResource("/view/Calendar.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Calendar for a week");
            stage.setScene(new Scene(root1));
            stage.show();
            return true;
        } catch(Exception e) {
            logger.error("In showCalendarDialog()-", e);
            return false;
        }
    }
}


