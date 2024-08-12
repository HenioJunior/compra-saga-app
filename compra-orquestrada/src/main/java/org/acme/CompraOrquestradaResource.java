package org.acme;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("compra-orquestrada")

public class CompraOrquestradaResource {

    @Inject
    private  PedidoService pedidoService;

    @Inject
    private CreditoService creditoService;

    @GET
    @Path("teste")
    @Produces(MediaType.TEXT_PLAIN)
    public Response saga() {
        //credito = 100
        Long id = 0L;

        comprar(++id, 20);
        comprar(++id, 30);
        comprar(++id, 30);
        comprar(++id, 25);

        return Response.ok().build();
    }

    public void comprar(Long id, int valor) {
        pedidoService.newPedido(id);
        try {
            creditoService.newPedidoValor(id, valor);
            System.out.println("Pedido " + id + " registrado no valor de " + valor);
        } catch(IllegalStateException e) {
            pedidoService.cancelPedido(id);
            System.err.println("Pedido " + id + " estornado no valor de " + valor);
        }
    }
}
