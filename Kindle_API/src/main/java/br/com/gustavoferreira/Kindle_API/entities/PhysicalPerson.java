package br.com.gustavoferreira.Kindle_API.entities;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class PhysicalPerson extends Person{

	private String name;
	private String surname;
	private String cpf;
	
	public PhysicalPerson() {
		
	}

	public PhysicalPerson(String name, String surname, String cpf) {
		super();
		this.name = name;
		this.surname = surname;
		this.cpf = cpf;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getCpf() {
		return cpf;
	}

	public Boolean setCpf(String cpf) {
		if(cpf.length() != 11)
			return false;
		
		this.cpf = cpf;
		return true;
	}
}
