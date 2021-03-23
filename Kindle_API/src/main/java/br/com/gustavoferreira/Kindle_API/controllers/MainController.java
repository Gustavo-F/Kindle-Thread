package br.com.gustavoferreira.Kindle_API.controllers;

import java.util.ArrayList;
import java.util.List;

import br.com.gustavoferreira.Kindle_API.AlertUtil;
import br.com.gustavoferreira.Kindle_API.App;
import br.com.gustavoferreira.Kindle_API.FXMLUtil;
import br.com.gustavoferreira.Kindle_API.db.BookDAO;
import br.com.gustavoferreira.Kindle_API.db.GenreDAO;
import br.com.gustavoferreira.Kindle_API.db.PublisherDAO;
import br.com.gustavoferreira.Kindle_API.db.WriterDAO;
import br.com.gustavoferreira.Kindle_API.entities.Book;
import br.com.gustavoferreira.Kindle_API.entities.Genre;
import br.com.gustavoferreira.Kindle_API.entities.Publisher;
import br.com.gustavoferreira.Kindle_API.entities.Writer;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ListView.EditEvent;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.Callback;

public class MainController {

	// MenuBar
	@FXML
	private MenuItem btnClose;
	@FXML
	private MenuItem btnExit;

	// Book
	@FXML
	private TableView<Book> bookTable;
	@FXML
	private TableColumn<Book, String> clmBookTitle;
	@FXML
	private TableColumn<Book, Integer> clmBookPages;
	@FXML
	private TableColumn<Book, String> clmBookWriters;
	@FXML
	private TableColumn<Book, String> clmBookGenres;
	@FXML
	private TableColumn<Book, String> clmBookPublisher;
	@FXML
	private Button btnAddBook;
	@FXML
	private Button btnEditBook;
	@FXML
	private Button btnRemoveBook;

	// Writer
	@FXML
	private TableView<Writer> writerTable;
	@FXML
	private TableColumn<Writer, Integer> clmWriterID;
	@FXML
	private TableColumn<Writer, String> clmWriterName;
	@FXML
	private TableColumn<Writer, String> clmWriterSurname;
	@FXML
	private TableColumn<Writer, String> clmWriterEmail;
	@FXML
	private Button btnAddWriter;
	@FXML
	private Button btnRemoveWriter;
	@FXML
	private TextField txtWriterName;
	@FXML
	private TextField txtWriterSurname;
	@FXML
	private TextField txtWriterEmail;

	// Publisher
	@FXML
	private TableView<Publisher> publisherTable;
	@FXML
	private TableColumn<Publisher, String> clmPublisherName;
	@FXML
	private TableColumn<Publisher, String> clmPublisherEmail;
	@FXML
	private TableColumn<Publisher, String> clmPublisherPhone;
	@FXML
	private TableColumn<Publisher, String> clmPublisherCNPJ;
	@FXML
	private Button btnAddPublisher;
	@FXML
	private Button btnRemovePublisher;
	@FXML
	private TextField txtPublisherName;
	@FXML
	private TextField txtPublisherEmail;
	@FXML
	private TextField txtPublisherPhone;
	@FXML
	private TextField txtPublisherCNPJ;

	// Genre
	@FXML
	private ListView<String> genreList;
	@FXML
	private TextField txtGenre;
	@FXML
	private Button btnAddGenre;
	@FXML
	private Button btnRemoveGenre;

	// Book methods
	@FXML
	private void updateBookTable() {
		bookTable.getItems().clear();

		List<Book> books = new ArrayList<>();
		books = new BookDAO().getAll();
		bookTable.setItems(FXCollections.observableArrayList(books));

		clmBookTitle.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
						return new SimpleStringProperty(param.getValue().getTitle());
					}
				});

		clmBookPages.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Book, Integer>, ObservableValue<Integer>>() {

					@Override
					public ObservableValue<Integer> call(CellDataFeatures<Book, Integer> param) {
						return new SimpleIntegerProperty(param.getValue().getPages()).asObject();
					}
				});

		clmBookWriters.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
						String writers = "";

						for (int i = 0; i < param.getValue().getWriters().size(); i++) {
							writers = writers.concat(param.getValue().getWriters().get(i).getName() + " "
									+ param.getValue().getWriters().get(i).getSurname() + "\n");
						}

						return new SimpleStringProperty(writers);
					}
				});

		clmBookGenres.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
						String genres = "";
						for (int i = 0; i < param.getValue().getGenres().size(); i++)
							genres = genres.concat(param.getValue().getGenres().get(i).getGenre() + "\n");

						return new SimpleStringProperty(genres);
					}
				});

		clmBookPublisher.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
						return new SimpleStringProperty(param.getValue().getPublisher().getName());
					}
				});
	}

	@FXML
	private void addBook() {
		Stage stage = new Stage();
		stage.setScene(FXMLUtil.loadScene("add_edit_book"));
		stage.setTitle("Adicionar Livro");
		stage.show();
	}

	@FXML
	private void editBook() {
		Book book = bookTable.getSelectionModel().getSelectedItem();
		if (book == null)
			return;

		AddEditBookController.setBook(book);

		Stage stage = new Stage();
		stage.setScene(FXMLUtil.loadScene("add_edit_book"));
		stage.setTitle("Editar Livro");
		stage.show();
	}

	@FXML
	private void removeBook() {
		Book book = bookTable.getSelectionModel().getSelectedItem();
		if (book != null) {
			new BookDAO().remove(book);
			updateBookTable();
		}
	}

	// Writer methods

	@FXML
	private void updateWriterTable() {
		List<Writer> writers = new ArrayList<>();
		writers = new WriterDAO().getAll();
		writerTable.setItems(FXCollections.observableArrayList(writers));

		clmWriterID.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Writer, Integer>, ObservableValue<Integer>>() {

					@Override
					public ObservableValue<Integer> call(CellDataFeatures<Writer, Integer> param) {
						return new SimpleIntegerProperty(param.getValue().getId()).asObject();
					}
				});

		clmWriterName.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Writer, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Writer, String> param) {
						return new SimpleStringProperty(param.getValue().getName());
					}
				});

		clmWriterSurname.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Writer, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Writer, String> param) {
						return new SimpleStringProperty(param.getValue().getSurname());
					}
				});

		clmWriterEmail.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Writer, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Writer, String> param) {
						return new SimpleStringProperty(param.getValue().getEmail());
					}
				});

		clmWriterName.setCellFactory(TextFieldTableCell.forTableColumn());
		clmWriterName.setOnEditCommit(e -> {
			String newName = e.getNewValue();
			Writer writer = e.getRowValue();
			writer.setName(newName);

			new WriterDAO().persist(writer);
			updateWriterTable();
		});

		clmWriterSurname.setCellFactory(TextFieldTableCell.forTableColumn());
		clmWriterSurname.setOnEditCommit(e -> {
			String newSurname = e.getNewValue();
			Writer writer = e.getRowValue();
			writer.setSurname(newSurname);

			new WriterDAO().persist(writer);
			updateWriterTable();
		});

		clmWriterEmail.setCellFactory(TextFieldTableCell.forTableColumn());
		clmWriterEmail.setOnEditCommit(e -> {
			String newEmail = e.getNewValue();
			Writer writer = e.getRowValue();
			writer.setEmail(newEmail);

			new WriterDAO().persist(writer);
			updateWriterTable();
		});
	}

	@FXML
	private void addWriter() {
		Alert alert = null;

		if (txtWriterName.getText().isBlank()) {
			alert = AlertUtil.info("Aviso!", "Nome do escritor em branco.", "Informe o nome do escritor!");
			alert.showAndWait();
			return;
		}

		if (txtWriterSurname.getText().isBlank()) {
			alert = AlertUtil.info("Aviso!", "Sobrenome do escritor em branco.", "Informe o sobrenome do escritor!");
			alert.showAndWait();
			return;
		}

		if (txtWriterEmail.getText().isBlank()) {
			alert = AlertUtil.info("Aviso!", "Email do escritor em branco.", "Informe o email do escritor!");
			alert.showAndWait();
			return;
		}

		Writer writer = new Writer();
		writer.setName(txtWriterName.getText());
		writer.setSurname(txtWriterSurname.getText());
		writer.setEmail(txtWriterEmail.getText());

		new WriterDAO().persist(writer);

		txtWriterName.setText("");
		txtWriterSurname.setText("");
		txtWriterEmail.setText("");

		updateWriterTable();
	}

	@FXML
	private void removeWriter() {
		Writer writer = writerTable.getSelectionModel().getSelectedItem();
		if (writer != null) {
			new WriterDAO().remove(writer);
			updateWriterTable();
		}
	}

	// Publisher methods

	@FXML
	private void updatePublisherTable() {
		List<Publisher> publishers = new ArrayList<>();
		publishers = new PublisherDAO().getAll();
		publisherTable.setItems(FXCollections.observableArrayList(publishers));

		clmPublisherName.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Publisher, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Publisher, String> param) {
						return new SimpleStringProperty(param.getValue().getName());
					}
				});

		clmPublisherEmail.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Publisher, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Publisher, String> param) {
						return new SimpleStringProperty(param.getValue().getEmail());
					}
				});

		clmPublisherPhone.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Publisher, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Publisher, String> param) {
						return new SimpleStringProperty(param.getValue().getPhone());
					}
				});

		clmPublisherCNPJ.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Publisher, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Publisher, String> param) {
						return new SimpleStringProperty(param.getValue().getCnpj());
					}
				});

		clmPublisherName.setCellFactory(TextFieldTableCell.forTableColumn());
		clmPublisherName.setOnEditCommit(e -> {
			String newName = e.getNewValue();
			Publisher publisher = e.getRowValue();
			publisher.setName(newName);

			new PublisherDAO().persist(publisher);
			updatePublisherTable();
		});

		clmPublisherEmail.setCellFactory(TextFieldTableCell.forTableColumn());
		clmPublisherEmail.setOnEditCommit(e -> {
			String newEmail = e.getNewValue();
			Publisher publisher = e.getRowValue();
			publisher.setEmail(newEmail);

			new PublisherDAO().persist(publisher);
			updatePublisherTable();
		});

		clmPublisherPhone.setCellFactory(TextFieldTableCell.forTableColumn());
		clmPublisherPhone.setOnEditCommit(e -> {
			String newPhone = e.getNewValue();
			Publisher publisher = e.getRowValue();
			publisher.setPhone(newPhone);

			new PublisherDAO().persist(publisher);
			updatePublisherTable();
		});
	}

	@FXML
	private void addPublisher() {
		Alert alert = null;

		if (txtPublisherName.getText().isBlank()) {
			alert = AlertUtil.info("Aviso!", "Nome de editora em branco.", "Informe o nome da editora!");
			alert.showAndWait();
			return;
		}

		if (txtPublisherEmail.getText().isBlank()) {
			alert = AlertUtil.info("Aviso!", "Email de editora em branco.", "Informe o email da editora!");
			alert.showAndWait();
			return;
		}

		if (txtPublisherPhone.getText().isBlank()) {
			alert = AlertUtil.info("Aviso!", "Telefone de editora em branco.", "Informe o telefone da editora!");
			alert.showAndWait();
			return;
		}

		if (txtPublisherCNPJ.getText().isBlank()) {
			alert = AlertUtil.info("Aviso!", "CNPJ de editora em branco.", "Informe o CNPJ da editora!");
			alert.showAndWait();
			return;
		}

		Publisher publisher = new Publisher();
		publisher.setName(txtPublisherName.getText());
		publisher.setEmail(txtPublisherEmail.getText());
		publisher.setPhone(txtPublisherPhone.getText());
		publisher.setCnpj(txtPublisherCNPJ.getText());

		new PublisherDAO().persist(publisher);

		txtPublisherName.setText("");
		txtPublisherEmail.setText("");
		txtPublisherPhone.setText("");
		txtPublisherCNPJ.setText("");

		updatePublisherTable();
	}

	@FXML
	private void removePublisher() {
		Publisher publisher = publisherTable.getSelectionModel().getSelectedItem();
		if (publisher != null) {
			new PublisherDAO().remove(publisher);
			updatePublisherTable();
		}
	}

	// Genre methods

	@FXML
	private void updateGenreList() {
		List<String> genres = new ArrayList<>();
		List<Genre> aux = new GenreDAO().getAll();

		if (aux == null) {
			return;
		}

		for (Genre genre : aux)
			genres.add(genre.getGenre());

		genreList.setItems(FXCollections.observableArrayList(genres));
		genreList.setCellFactory(TextFieldListCell.forListView());
		genreList.setEditable(true);
		genreList.setOnEditCommit(editGenre());
	}

	@FXML
	private void addGenre() {
		if (txtGenre.getText().isBlank())
			return;
		else if (genreList.getItems().contains(txtGenre.getText())) {
			txtGenre.setText("");
			return;
		}

		Genre genre = new Genre(txtGenre.getText());
		new GenreDAO().persist(genre);
		txtGenre.setText("");

		updateGenreList();
	}

	@FXML
	private void removeGenre() {
		String genre = genreList.getSelectionModel().getSelectedItem();
		if (genre != null) {
			Genre genreToRemove = new GenreDAO().get(genre);
			new GenreDAO().remove(genreToRemove);
			updateGenreList();
		}
	}

	private EventHandler<EditEvent<String>> editGenre(){
		return new EventHandler<ListView.EditEvent<String>>() {

			@Override
			public void handle(EditEvent<String> event) {
				String oldGenre = genreList.getItems().get(event.getIndex());
				String newGenre = event.getNewValue();
				
				GenreDAO.updateGenre(newGenre, oldGenre);
				updateGenreList();
			}
		};
	}
	
	// MenuBar methods

	@FXML
	private void close() {
		Platform.exit();
	}

	@FXML
	private void exit() {
		App.setRoot("login");
		App.changeResizable();
	}
}
