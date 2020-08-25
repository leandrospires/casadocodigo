package br.com.casadocodigo.loja.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SystemRole {
	
	@Id
	private Integer id;
	
	private String name;
	
	@Deprecated
	public SystemRole() {}
	
	public SystemRole(String name) {
		this.setName(name);
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
