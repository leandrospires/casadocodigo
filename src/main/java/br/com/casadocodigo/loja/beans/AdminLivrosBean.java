package br.com.casadocodigo.loja.beans;

import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
	@Inject
	private FacesContext fc;
	
	@Transactional
	public String salvar() {
		
		System.out.println(livro);
		
		livroDao.salvar(livro);
		
		fc.getExternalContext().getFlash().setKeepMessages(true);
		fc.addMessage(null, new FacesMessage("Livro cadastro com Sucesso!"));
		
		System.out.println("Livro: " + livro);
		
		return "/livros/lista?faces-redirect=true";
	}
	
	public String navegar() {
		
		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
		String pagina= params.get("pagina");
		
		pagina = pagina + "?faces-redirect=true";
		
		return pagina;
	}
	
	public List<Autor> getAutores() {
		return autorDao.listar();
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

}
