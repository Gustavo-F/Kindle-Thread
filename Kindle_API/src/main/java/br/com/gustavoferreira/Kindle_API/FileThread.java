package br.com.gustavoferreira.Kindle_API;

import br.com.gustavoferreira.Kindle_API.db.UserInFile;

public class FileThread implements Runnable {

	@Override
	public void run() {
		UserInFile userInFile = new UserInFile();
		
		try {
			while(true) {
				userInFile.check();
				Thread.sleep(4200);
			}
		}catch(Exception e) {
			System.out.println(e);
		}
	}
}
