package org.acme;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class CreditoService {

    private int creditoTotal;
    private Map<Long, Integer> pedidoValor = new HashMap<>();

    public CreditoService() {
        this.creditoTotal = 100;
    }

    public void newPedidoValor(Long pedidoId, int valor) {
        if(valor > creditoTotal) {
            throw new IllegalStateException("Saldo insuficiente");
        }

        creditoTotal -= valor;
        pedidoValor.put(pedidoId, valor);
    }

    public void cancelPedidoValor(Long id) {
        creditoTotal += pedidoValor.get(id);
        pedidoValor.remove(id);
    }
}
