package br.com.casadocodigo.loja.service;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import br.com.casadocodigo.loja.daos.CompraDao;
import br.com.casadocodigo.loja.infra.MailSender;
import br.com.casadocodigo.loja.models.Compra;

@MessageDriven(activationConfig = {
	    @ActivationConfigProperty(
	        propertyName="destinationLookup",
	        propertyValue="java:/jms/topics/CarrinhoComprasTopico"),
	    @ActivationConfigProperty(
	        propertyName="destinationType",
	        propertyValue="javax.jms.Topic")
})
public class EnviaEmailCompra implements MessageListener{

	@Inject
	private MailSender mailSender;
	
	@Inject
	private CompraDao compraDao;	
	
	
	public void onMessage(Message message) {
		
		try {
			TextMessage textMessagem = (TextMessage) message;
			
			Compra compra;
			
			compra = compraDao.buscaPorUuid(textMessagem.getText());
			
			String messageBody = "Sua compra foi efetivada com Sucesso! Pedido N: " + compra.getUuid();
			
			mailSender.send("compras@casadocodigo.com.br",
					compra.getUsuario().getEmail(),
					"Nova Compra na CDC",
					messageBody);
			
		} catch (JMSException e) {
			e.printStackTrace();
		}
		
	}

}
