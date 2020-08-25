package br.com.casadocodigo.loja.beans;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import javax.transaction.Transactional;

import br.com.casadocodigo.loja.daos.AutorDao;
import br.com.casadocodigo.loja.daos.LivroDao;
import br.com.casadocodigo.loja.infra.FileServer;
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
	
	private Part capaLivro;
	
	@Transactional
	public String salvar() throws IOException {
		
		System.out.println("AdminLivrosBean.salvar: Listando Inf do Livro");
		System.out.println(livro);
		
		livroDao.salvar(livro);
		
		System.out.println("AdminLivrosBean.salvar: chamando classe FileServer");
		FileServer fileServer = new FileServer();
		
		System.out.println("AdminLivrosBean.salvar: chama método setCapaPath");
		livro.setCapaPath(fileServer.write(capaLivro, "livros"));
		
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

	public Part getCapaLivro() {
		return capaLivro;
	}

	public void setCapaLivro(Part capaLivro) {
		this.capaLivro = capaLivro;
	}

}
