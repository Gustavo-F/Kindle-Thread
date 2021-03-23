package br.com.gustavoferreira.Kindle_API;

import br.com.gustavoferreira.Kindle_API.db.UtilDB;
import javafx.application.Application;

public class Main {

	public static void main(String[] args) {
		Thread connection = new Thread(new FileThread());
		App.setConnection(connection);
		
		UtilDB.initDB();
		Application.launch(App.class);
		
		connection.interrupt();
	}
}
