package br.com.gustavoferreira.Kindle_API.controllers;

import br.com.gustavoferreira.Kindle_API.AlertUtil;
import br.com.gustavoferreira.Kindle_API.db.UserDAO;
import br.com.gustavoferreira.Kindle_API.entities.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterController {

	@FXML
	private TextField txtName;
	@FXML
	private TextField txtSurname;
	@FXML
	private TextField txtCPF;
	@FXML
	private TextField txtEmail;
	@FXML
	private TextField txtPhone;
	@FXML
	private TextField txtUsername;
	@FXML
	private PasswordField txtPassword;
	@FXML
	CheckBox checkAccessLevel;
	@FXML
	private Button btnRegister;
	@FXML
	private Button btnCancel;

	@FXML
	private void register() {
		try {
			Alert alert = null;

			if (txtName.getText().isBlank()) {
				alert = AlertUtil.info("Aviso!", "Nome em branco.", "Digite seu nome.");
				alert.showAndWait();
				return;
			}

			if (txtSurname.getText().isBlank()) {
				alert = AlertUtil.info("Aviso!", "Nome sobrenome branco.", "Digite seu sobrenome.");
				alert.showAndWait();
				return;
			}

			if (txtCPF.getText().isBlank()) {
				alert = AlertUtil.info("Aviso!", "CPF em branco.", "Digite seu CPF.");
				alert.showAndWait();
				return;
			}

			if (txtCPF.getText().length() != 11) {
				alert = AlertUtil.info("Aviso!", "CPF inválido!", "CPF deve possuir 11 caracteres!");
				alert.showAndWait();
				return;
			}

			if (txtEmail.getText().isBlank()) {
				alert = AlertUtil.info("Aviso!", "Email em branco.", "Digite seu email.");
				alert.showAndWait();
				return;
			}

			if (txtPhone.getText().isBlank()) {
				alert = AlertUtil.info("Aviso!", "Telefone em branco.", "Digite seu telefone.");
				alert.showAndWait();
				return;
			}

			if (txtUsername.getText().isBlank()) {
				alert = AlertUtil.info("Aviso!", "Nome de usuário em branco.", "Digite seu nome de usuário.");
				alert.showAndWait();
				return;
			}

			User user = new UserDAO().get(txtUsername.getText());
			if (user != null) {
				alert = AlertUtil.info("Aviso!", "Nome de usuário indisponível!",
						txtUsername.getText() + " já esta em uso!");
				alert.showAndWait();
				return;
			}

			user = new User();

			user.setName(txtName.getText());
			user.setSurname(txtSurname.getText());
			user.setCpf(txtCPF.getText());
			user.setEmail(txtEmail.getText());
			user.setPhone(txtPhone.getText());
			user.setUsername(txtUsername.getText());
			user.setPassword(txtPassword.getText());

			if (checkAccessLevel.isSelected())
				user.setAccessLevel(1);
			else
				user.setAccessLevel(0);

			new UserDAO().persist(user);
			
			alert = AlertUtil.info("Aviso!", "Cadastro de usuário.", "Usuário cadastrado com sucesso!");
			alert.showAndWait();

			close();
		} catch (Exception e) {
			AlertUtil.error("Erro!", "Erro de cadastro!", "Erro ao cadastrar novo usuário!", e);
		}
	}

	@FXML
	private void close() {
		Stage stage = (Stage) btnRegister.getScene().getWindow();
		stage.close();
	}
}
