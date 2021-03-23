package br.com.gustavoferreira.Kindle_API.entities;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class JuridicalPerson extends Person{

	private String name;
	@Id
	private String cnpj;
	
	public JuridicalPerson() {
		
	}

	public JuridicalPerson(String name, String cnpj) {
		super();
		this.name = name;
		this.cnpj = cnpj;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCnpj() {
		return cnpj;
	}

	public Boolean setCnpj(String cnpj) {
		if(cnpj.length() != 14)
			return false;
		
		this.cnpj = cnpj;
		return true;
	}
}
