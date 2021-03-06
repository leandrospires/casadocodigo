package br.com.casadocodigo.loja.service;

import java.net.URI;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
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

	private static ExecutorService executor = Executors.newFixedThreadPool(50);
	// 50: pool disponível

	@Inject
	private PagamentoGateway pagamentoGateway;

	@Inject
	private JMSContext jmsContext;
	
	@Inject
	private FacesContext facesContext;

	@Inject
	private CompraDao compraDao;

	@Resource(name = "java:/jms/topics/CarrinhoComprasTopico")
	private Destination destination;
	
	private String serverStr;

	// @Suspended: contexto assíncrono e libera a thread principal

	@POST
	public void pagar(@Suspended final AsyncResponse ar, 
						@QueryParam("uuid") String uuid,
						@QueryParam("serverStr") String serverStrRec)
	{
		Compra compra = compraDao.buscaPorUuid(uuid);

		String contextPath = context.getContextPath();
		
		serverStr = serverStrRec;
		serverStr = "http://" + serverStr + ":8080";
		
		System.out.println("PagamentoService.pagar: " + serverStr);
		
		JMSProducer producer = jmsContext.createProducer();
		//   -> Lambda - execução em segundo plano
		executor.submit(() -> {
			try {
				producer.send(destination, compra.getUuid());

				URI responseUri = UriBuilder.fromPath(serverStr + contextPath + "/index.xhtml")
						.queryParam("msg", "Compra Realizada comSuceso").build();
				Response response = Response.seeOther(responseUri).build();
				ar.resume(response);
			} catch (Exception e) {
				ar.resume(new WebApplicationException(e));
			}
		});

	}

}
