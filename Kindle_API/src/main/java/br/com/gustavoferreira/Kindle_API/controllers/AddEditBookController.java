package br.com.gustavoferreira.Kindle_API.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import br.com.gustavoferreira.Kindle_API.AlertUtil;
import br.com.gustavoferreira.Kindle_API.db.BookDAO;
import br.com.gustavoferreira.Kindle_API.db.GenreDAO;
import br.com.gustavoferreira.Kindle_API.db.PublisherDAO;
import br.com.gustavoferreira.Kindle_API.db.WriterDAO;
import br.com.gustavoferreira.Kindle_API.entities.Book;
import br.com.gustavoferreira.Kindle_API.entities.Genre;
import br.com.gustavoferreira.Kindle_API.entities.Publisher;
import br.com.gustavoferreira.Kindle_API.entities.Writer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;

public class AddEditBookController implements Initializable {
	private static Book book;

	@FXML
	private TextField txtTitle;
	@FXML
	private TextField txtPages;
	@FXML
	private TableView<Writer> writersBookTable;
	@FXML
	private TableColumn<Writer, String> clmWriterBookName;
	@FXML
	private TableColumn<Writer, String> clmWriterBookSurname;
	@FXML
	private ListView<String> genresBookList;
	@FXML
	private ComboBox<String> cmbPublisher;
	@FXML
	private TableView<Writer> allWritersTable;
	@FXML
	private TableColumn<Writer, String> clmWriterName;
	@FXML
	private TableColumn<Writer, String> clmWriterSurname;
	@FXML
	private ListView<String> allGenresList;
	@FXML
	private Button btnAddWriter;
	@FXML
	private Button btnAddGenre;
	@FXML
	private Button btnRemoveWriter;
	@FXML
	private Button btnRemoveGenre;
	@FXML
	private Button btnSaveBook;
	@FXML
	private Button btnCancel;

	public static void setBook(Book b) {
		book = b;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (book != null) {
			txtTitle.setText(book.getTitle());
			txtPages.setText(Integer.toString(book.getPages()));
		}

		initWritersBookTable();
		initGenresBookList();
		initPublishers();
		initAllWritersTable();
		initAllGenresList();
	}

	private void initPublishers() {
		List<String> publishers = new ArrayList<>();
		for (Publisher p : new PublisherDAO().getAll()) {
			String aux = "";
			aux = aux.concat(p.getName() + ", " + p.getCnpj());
			publishers.add(aux);
		}

		cmbPublisher.setItems(FXCollections.observableArrayList(publishers));

		if (book != null) {
			cmbPublisher.setValue(book.getPublisher().getName() + ", " + book.getPublisher().getCnpj());
		}
	}

	private void initWritersBookTable() {
		List<Writer> books = new ArrayList<>();

		if (book != null)
			for (Writer w : book.getWriters())
				books.add(w);

		writersBookTable.setItems(FXCollections.observableArrayList(books));

		clmWriterBookName.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Writer, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Writer, String> param) {
						return new SimpleStringProperty(param.getValue().getName());
					}
				});

		clmWriterBookSurname.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Writer, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Writer, String> param) {
						return new SimpleStringProperty(param.getValue().getSurname());
					}
				});
	}

	private void initGenresBookList() {
		List<String> genres = new ArrayList<>();

		if (book != null)
			for (Genre g : book.getGenres())
				genres.add(g.getGenre());

		genresBookList.setItems(FXCollections.observableArrayList(genres));
	}

	private void initAllWritersTable() {
		List<Writer> writers = new ArrayList<>();
		writers = new WriterDAO().getAll();
		
		for(int i = 0; i < writers.size(); i++)
			if(writersBookTable.getItems().contains(writers.get(i)))
				writers.remove(i);
		
		allWritersTable.setItems(FXCollections.observableArrayList(writers));

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
	}

	private void initAllGenresList() {
		List<String> genres = new ArrayList<>();
		for (Genre g : new GenreDAO().getAll()) {
			if(!genresBookList.getItems().contains(g.getGenre()))
				genres.add(g.getGenre());
		}
		
		allGenresList.setItems(FXCollections.observableArrayList(genres));
	}

	@FXML
	private void addWriter() {
		Writer writer = allWritersTable.getSelectionModel().getSelectedItem();
		if (writer == null)
			return;
		
		writersBookTable.getItems().add(writer);
		allWritersTable.getItems().remove(writer);
	}
	
	@FXML
	private void addGenre() {
		String genre = allGenresList.getSelectionModel().getSelectedItem();
		if(genre == null)
			return;
		
		genresBookList.getItems().add(genre);
		allGenresList.getItems().remove(genre);
	}	
	
	@FXML
	private void removeWriter() {
		Writer writer = writersBookTable.getSelectionModel().getSelectedItem();
		if(writer == null)
			return;
		
		writersBookTable.getItems().remove(writer);
		allWritersTable.getItems().add(writer);
	}
	
	@FXML
	private void removeGenre() {
		String genre = genresBookList.getSelectionModel().getSelectedItem();
		if(genre == null)
			return;
		
		genresBookList.getItems().remove(genre);
		allGenresList.getItems().add(genre);
	}

	@FXML
	private void saveBook() {
		Alert alert = null;

		if (txtTitle.getText().isBlank()) {
			alert = AlertUtil.info("Aviso!", "Título em branco.", "Informe o título do livro!");
			alert.showAndWait();
			return;
		}

		if (txtPages.getText().isBlank()) {
			alert = AlertUtil.info("Aviso!", "Páginas em branco.", "Informe o números de páginas do livro!");
			alert.showAndWait();
			return;
		}

		if (cmbPublisher.getSelectionModel().getSelectedItem() == null) {
			alert = AlertUtil.info("Aviso!", "Editora em branco.", "Selecione uma editora!");
			alert.showAndWait();
			return;
		}

		if (book == null) {
			book = new Book();
		}

		book.setTitle(txtTitle.getText());
		book.setPages(Integer.parseInt(txtPages.getText()));
		book.setWriters(writersBookTable.getItems());

		List<Genre> genres = new ArrayList<>();
		for (int i = 0; i < genresBookList.getItems().size(); i++) {
			genres.add(new GenreDAO().get(genresBookList.getItems().get(i)));
		}

		book.setGenres(genres);

		String publisherAux = cmbPublisher.getSelectionModel().getSelectedItem().split(",")[1];
		publisherAux = publisherAux.trim();
		book.setPublisher(new PublisherDAO().get(publisherAux));

		new BookDAO().persist(book);

		close();
	}

	@FXML
	private void close() {
		Stage stage = (Stage) btnSaveBook.getScene().getWindow();
		stage.close();
	}

}
