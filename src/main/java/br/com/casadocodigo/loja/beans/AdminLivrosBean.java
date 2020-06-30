package br.com.casadocodigo.loja.beans;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.casadocodigo.loja.daos.AutorDao;
import br.com.casadocodigo.loja.daos.LivroDao;
import br.com.casadocodigo.loja.models.Autor;
import br.com.casadocodigo.loja.models.Livro;

// CDI
@Named
@RequestScoped
public class AdminLivrosBean {
	
	private Livro livro = new Livro();
	
	// contexto e injeção de dependência
	@Inject
	private LivroDao livroDao;
	@Inject
	private AutorDao autorDao;
	
	private List<Integer> autoresId = new ArrayList<>();

	
	
	@Transactional
	public String salvar() {
		for (Integer autorId: autoresId) {
			livro.getAutores().add(new Autor(autorId));
		}
		
		System.out.println("Autores: " + autoresId.toString());
		
		livroDao.salvar(livro);
		System.out.println("Livro: " + livro);
		
		return "/livros/lista?faces-redirect=true";
		//this.livro = new Livro();
		//this.autoresId = new ArrayList<>();
	}
	
	public List<Autor> getAutores() {
		//return Arrays.asList(new Autor(1, "Autor 1"), new Autor (2, "Autor 2"));
		return autorDao.listar();
	}
	

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public List<Integer> getAutoresId() {
		return autoresId;
	}

	public void setAutoresId(List<Integer> autoresId) {
		this.autoresId = autoresId;
	}

}
