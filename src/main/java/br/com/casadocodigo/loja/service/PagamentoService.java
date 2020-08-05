package br.com.casadocodigo.loja.service;

import java.net.URI;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import br.com.casadocodigo.loja.daos.CompraDao;
import br.com.casadocodigo.loja.models.Compra;

@Path("/pagamento")
public class PagamentoService {

	@Context
	private ServletContext context;
	
	@Inject
	private CompraDao compraDao;
	
	private static ExecutorService executor = Executors.newFixedThreadPool(50);
	// 50: pool disponível
	
	@Inject
	private PagamentoGateway pagamentoGateway;
	
	//@Suspended: contexto assíncrono e libera a thread principal
	
	
	@POST
	public void pagar(@Suspended final AsyncResponse ar, 
						@QueryParam("uuid") String uuid) {
		
		String contextPath =context.getContextPath();
		
		//   -> Lambda
		executor.submit(() -> {
			
			try {
				Compra compra = compraDao.buscaPorUuid(uuid);
				pagamentoGateway.pagar( compra.getTotal() );

				URI responseUri = UriBuilder.fromPath("http://localhost:8080" +
						contextPath + "/index.xhtml")
					.queryParam("msg", "Compra Realizada comSuceso")
					.build();
			
				Response response = Response.seeOther(responseUri).build();				
				ar.resume(response);				

			} catch (Exception e) {

				ar.resume(new WebApplicationException(e));
				
			}

		});

		
	}
	
}
