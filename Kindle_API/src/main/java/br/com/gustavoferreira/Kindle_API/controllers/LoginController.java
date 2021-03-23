package br.com.gustavoferreira.Kindle_API.controllers;

import br.com.gustavoferreira.Kindle_API.AlertUtil;
import br.com.gustavoferreira.Kindle_API.App;
import br.com.gustavoferreira.Kindle_API.FXMLUtil;
import br.com.gustavoferreira.Kindle_API.db.UserDAO;
import br.com.gustavoferreira.Kindle_API.entities.User;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

	@FXML
	private TextField txtUserName;
	@FXML
	private PasswordField txtPassword;
	@FXML
	private Button btnLogin;
	@FXML
	private Button btnRegister;
	@FXML
	private Button btnExit;

	@FXML
	private void login() {
		try {
			Alert alert = null;

			if (txtUserName.getText().isBlank()) {
				alert = AlertUtil.info("Aviso!", "Nome de usuário em branco!", "Digite o nome de usuário.");
				alert.showAndWait();
				return;
			}

			if (txtPassword.getText().isBlank()) {
				alert = AlertUtil.info("Aviso!", "Senha em branco!", "Digite sua senha.");
				alert.showAndWait();
				return;
			}

			User user = new UserDAO().get(txtUserName.getText());

			if (user == null) {
				alert = AlertUtil.info("Aviso!", "Erro de login.", "Usuário ou senha incorreto(s)!");
				alert.showAndWait();
				return;
			}

			if (!user.getPassword().contentEquals(txtPassword.getText())) {
				alert = AlertUtil.info("Aviso!", "Erro de login.", "Usuário ou senha incorreto(s)!");
				alert.showAndWait();
				return;
			}

			App.changeResizable();

			if (user.getAccessLevel() == 1)
				App.setRoot("main");
			else {
				ClientMainController.setUser(user);
				App.setRoot("client_main");
			}
		} catch (Exception e) {
			AlertUtil.error("", "", "", e);
		}
	}

	@FXML
	private void register() {
		Stage stage = new Stage();
		stage.setScene(FXMLUtil.loadScene("register"));
		stage.setTitle("Cadastrar usuário");
		stage.setResizable(false);
		stage.show();
	}

	@FXML
	private void exit() {
		Platform.exit();
	}
}
