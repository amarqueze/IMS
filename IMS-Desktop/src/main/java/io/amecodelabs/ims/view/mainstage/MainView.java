package io.amecodelabs.ims.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainView extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent sceneGraph = FXMLLoader.load(getClass().getResource("/view/StageProvider.fxml"));
		Scene scene = new Scene(sceneGraph);
		primaryStage.setScene(scene);
		primaryStage.setTitle("IMS");
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.show();
	}
	
	public static void run(String[] args) {
		launch(args);
	}

}
