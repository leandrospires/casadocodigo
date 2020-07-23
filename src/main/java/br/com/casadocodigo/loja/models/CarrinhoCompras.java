package br.com.casadocodigo.loja.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

public class CarrinhoCompras {
	
	private Set<CarrinhoItem> itens = new HashSet<>();
	
    public void add(CarrinhoItem item) {
    	
        itens.add(item);
        
    }

	public List<CarrinhoItem> getItens() {

		return new ArrayList<CarrinhoItem> (itens);
		
	}
	
}
