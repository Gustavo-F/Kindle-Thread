package br.com.gustavoferreira.Kindle_API.controllers;

import java.util.ArrayList;
import java.util.List;

import br.com.gustavoferreira.Kindle_API.App;
import br.com.gustavoferreira.Kindle_API.db.BookDAO;
import br.com.gustavoferreira.Kindle_API.db.UserDAO;
import br.com.gustavoferreira.Kindle_API.entities.Book;
import br.com.gustavoferreira.Kindle_API.entities.User;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class ClientMainController {

	private static User user;
	@FXML
	private MenuItem closeItem;
	@FXML
	private MenuItem exitItem;
	@FXML
	private TableView<Book> allBooksTable;
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
	private TableView<Book> libraryTable;
	@FXML
	private TableColumn<Book, String> clmLibraryBookTitle;
	@FXML
	private TableColumn<Book, Integer> clmLibraryBookPages;
	@FXML
	private TableColumn<Book, String> clmLibraryBookWriters;
	@FXML
	private TableColumn<Book, String> clmLibraryBookGenres;
	@FXML
	private TableColumn<Book, String> clmLibraryBookPublisher;

	@FXML
	private void updateAllBooksTable() {
		List<Book> books = new ArrayList<>();
		books = new BookDAO().getAll();
		allBooksTable.setItems(FXCollections.observableArrayList(books));

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
	private void updateLibraryTable() {
		user = new UserDAO().get(user.getUsername());
		libraryTable.setItems(FXCollections.observableArrayList(user.getBooks()));

		clmLibraryBookTitle.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
						return new SimpleStringProperty(param.getValue().getTitle());
					}
				});

		clmLibraryBookPages.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Book, Integer>, ObservableValue<Integer>>() {

					@Override
					public ObservableValue<Integer> call(CellDataFeatures<Book, Integer> param) {
						return new SimpleIntegerProperty(param.getValue().getPages()).asObject();
					}
				});

		clmLibraryBookWriters.setCellValueFactory(
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

		clmLibraryBookGenres.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
						String genres = "";
						for (int i = 0; i < param.getValue().getGenres().size(); i++)
							genres = genres.concat(param.getValue().getGenres().get(i).getGenre() + "\n");

						return new SimpleStringProperty(genres);
					}
				});

		clmLibraryBookPublisher.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
						return new SimpleStringProperty(param.getValue().getPublisher().getName());
					}
				});
	}

	@FXML
	private void add() {
		Book book = allBooksTable.getSelectionModel().getSelectedItem();
		if(book != null) {
			if(user.getBooks() == null)
				user.setBooks(new ArrayList<>());
			
			user.getBooks().add(book);
			new UserDAO().persist(user);
			updateLibraryTable();
		}
	}
	
	@FXML
	private void remove() {
		Book book = libraryTable.getSelectionModel().getSelectedItem();
		if(book != null) {
			user.getBooks().remove(book);
			new UserDAO().persist(user);
			updateLibraryTable();
		}
	}
	
	@FXML
	private void close() {
		Platform.exit();
	}

	@FXML
	private void exit() {
		App.changeResizable();
		App.setRoot("login");
	}

	public static void setUser(User u) {
		user = u;
	}
}
