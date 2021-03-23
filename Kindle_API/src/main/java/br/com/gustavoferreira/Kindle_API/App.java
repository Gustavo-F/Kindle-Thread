package br.com.gustavoferreira.Kindle_API;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Stage stage;
	private static Thread connection;

    @Override
    public void start(Stage newStage) {
        stage = newStage;
        stage.setScene(FXMLUtil.loadScene("login"));
        changeResizable();
        stage.setTitle("Login");
        stage.show();
        
        connection.start();
    }

    public static void setRoot(String fxml) {
        stage.setScene(FXMLUtil.loadScene(fxml));
    }

    public static void changeResizable() {
		if (stage.isResizable())
			stage.setResizable(false);
		else
			stage.setResizable(true);
	}

	public static void setConnection(Thread connection) {
		App.connection = connection;
	}
}