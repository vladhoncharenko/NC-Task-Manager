package app.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CalendarView {

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
            e.printStackTrace();
            return false;
        }
    }
}


