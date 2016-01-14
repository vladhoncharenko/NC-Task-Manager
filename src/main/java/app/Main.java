package app;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class Main extends Application {
	final static Logger logger = Logger.getLogger(Main.class);
	@Override
	public void start(Stage primaryStage) throws Exception {

		Parent root = FXMLLoader.load(getClass().getResource("/view/main.fxml"));
		primaryStage.setTitle("NC Task Manager");
		primaryStage.setScene(new Scene(root));
		primaryStage.show();

		//BackUP
		primaryStage.setOnCloseRequest(we -> {
			ClassLoader classLoader = Main.class.getClassLoader();
			File fileFrom = new File(classLoader.getResource("data/dataFile.bin").getFile());
			File fileTo = new File(classLoader.getResource("data/backup/dataFile.bin").getFile());
			try {
				Files.copy(fileFrom.toPath(),fileTo.toPath(), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			}
			logger.info("Backup was made");
			Platform.exit();
            System.exit(0);
        });
	}

	public static void main(String[] args) {
		launch(args);
	}

}
