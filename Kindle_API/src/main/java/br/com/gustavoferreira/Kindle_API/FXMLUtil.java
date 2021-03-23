package br.com.gustavoferreira.Kindle_API;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

public class FXMLUtil {
	
	private static FXMLLoader load;

	public static Scene loadScene(String fxml) {

		try {
			load = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
			Scene scene = new Scene(load.load());
			return scene;
		} catch (IOException eIO) {
			AlertUtil.error("Erro", "Erro ao carregar componente",
					"Erro ao tentar carregar a janela: " + fxml + ".fxml", eIO);
			return null;
		} catch (IllegalStateException eIllegalState) {
			Alert alert = AlertUtil.error("Erro", "Erro - Arquivo inexistente!",
					"Erro ao tentar carregar a janela: " + fxml + ".fxml", eIllegalState);
			alert.showAndWait();
			return null;
		}
	}
}
