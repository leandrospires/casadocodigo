package br.com.casadocodigo.loja.beans;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import br.com.casadocodigo.loja.daos.LivroDao;
import br.com.casadocodigo.loja.models.Livro;

@Model
public class HomeBean {

	@Inject
	private LivroDao dao;
	
	public List<Livro> ultimosLancamentos() {
		System.out.println("HomeBean.ultimosLancamentos");
		return dao.ultimosLancamentos();
	}
	
	public List<Livro> demaisLivros() {
		System.out.println("HomeBean.demaisLivros");
		return dao.demaisLivros();
	}
	
}
