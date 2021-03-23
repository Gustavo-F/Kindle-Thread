package br.com.gustavoferreira.Kindle_API.entities;

import javax.persistence.Entity;

@Entity
public class Publisher extends JuridicalPerson{

	public Publisher() {
		super();
	}

	public Publisher(String name, String cnpj) {
		super(name, cnpj);
	}
}
