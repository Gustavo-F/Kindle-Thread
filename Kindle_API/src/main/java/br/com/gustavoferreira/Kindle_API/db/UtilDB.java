package br.com.gustavoferreira.Kindle_API.db;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.gustavoferreira.Kindle_API.AlertUtil;
import br.com.gustavoferreira.Kindle_API.entities.Genre;
import br.com.gustavoferreira.Kindle_API.entities.Publisher;
import br.com.gustavoferreira.Kindle_API.entities.User;
import br.com.gustavoferreira.Kindle_API.entities.Writer;
import javafx.scene.control.Alert;

public class UtilDB {

	private static EntityManagerFactory entityManagerFactory;
	private static EntityManager entityManager;

	private static EntityManagerFactory getEntityManagerFactory() {
		if (entityManagerFactory == null)
			entityManagerFactory = Persistence.createEntityManagerFactory("kindle");
		return entityManagerFactory;
	}

	public static EntityManager getEntityManager() {
		if (entityManager == null)
			entityManager = getEntityManagerFactory().createEntityManager();
		return entityManager;
	}

	public static void closeConn() {
		if (entityManager != null)
			entityManager.close();

		if (entityManagerFactory != null)
			entityManagerFactory.close();
	}

	public static List<String> consultAPI() {
		List<String> result = new ArrayList<>();

		try {
			URL url = new URL("http://www.lucasbueno.com.br/steam.json");
			URLConnection uc = url.openConnection();
			InputStreamReader input = new InputStreamReader(uc.getInputStream());
			BufferedReader in = new BufferedReader(input);
			String inputLine;

			while ((inputLine = in.readLine()) != null)
				result.add(inputLine);

			in.close();
		} catch (Exception e) {
			Alert alert = AlertUtil.error("Erro", "Erro ao consumir a API!", "Erro ao consumir a API!", e);
			alert.showAndWait();
		}

		return result;
	}

	public static List<User> consumeAPI(List<String> users) {
		List<User> result = new ArrayList<>();

		for (int lineIndex = 0; lineIndex < users.size(); lineIndex++) {
			String line = users.get(lineIndex);

			if (line.contains("username")) {
				String username = processJSONLine(line);
				lineIndex++;
				line = users.get(lineIndex);
				String password = processJSONLine(line);
				User user = new User(username, password, 1);
				result.add(user);
			}
		}

		return result;
	}

	private static String processJSONLine(String line) {
		String[] dividedLine = line.split(":");
		String result = dividedLine[1];

		result = result.replace(",", " ");
		result = result.replace("\"", " ");
		result = result.trim();

		return result;
	}

	public static void initDB() {
		for(User u : consumeAPI(consultAPI()))
			new UserDAO().persist(u);
		
		Writer writer = new Writer();
		writer.setName("Gustavo");
		writer.setSurname("Ferreira");
		writer.setEmail("gustavo@mail.com");

		new WriterDAO().persist(writer);

		User user = new User();
		user.setAccessLevel(0);
		user.setUsername("gustavo");
		user.setPassword("123");
		user.setEmail("gustavo@mail.com");
		user.setName("Gustavo");
		user.setSurname("Ferreira");
		user.setCpf("78945612322");
		user.setPhone("94991038846");

		new UserDAO().persist(user);

		Publisher publisher = new Publisher();
		publisher.setName("Veja");
		publisher.setEmail("contato@veja.com.br");
		publisher.setCnpj("78965412336974");
		publisher.setPhone("1199445566");

		new PublisherDAO().persist(publisher);

		Genre genre = new Genre("Ação");
		new GenreDAO().persist(genre);
		genre = new Genre("Romance");
		new GenreDAO().persist(genre);
		genre = new Genre("Ficção");
		new GenreDAO().persist(genre);
	}
}
