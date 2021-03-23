package br.com.gustavoferreira.Kindle_API.db;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.com.gustavoferreira.Kindle_API.entities.User;

public class UserInFile {

	public void check() {
		String fileLocation = "./local-users.json";
		List<String> fileLines = new ArrayList<>();

		try {
			File file = new File(fileLocation);
			if (file.exists()) {
				Scanner scanner = new Scanner(file);
				while (scanner.hasNextLine())
					fileLines.add(scanner.nextLine());

				scanner.close();
			} else {
				System.err.println("O arquivo \"" + fileLocation + "\" não foi encontrado.");
			}

		} catch (Exception e) {
			System.err.println("Erro ao abrir o arquivo \"" + fileLocation + "\".");
		}
		
		List<User> users = UtilDB.consumeAPI(fileLines);
		for(User u : users) {
			User user = new UserDAO().get(u.getUsername());

			if(user != null) {
				user.setPassword(u.getPassword());
				new UserDAO().persist(user);
			}else {
				new UserDAO().persist(u);
			}
		}
	}

}
