package com.deliveryfood.domain.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.deliveryfood.domain.event.PedidoConfirmadoEvent;
import com.deliveryfood.domain.model.Pedido;
import com.deliveryfood.domain.service.EnvioEmailService;
import com.deliveryfood.domain.service.EnvioEmailService.Mensagem;

@Component
public class NotificacaoClientePedidoConfirmadoListener {
	@Autowired
	private EnvioEmailService envioEmailService;

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void aoConfirmarPedido(PedidoConfirmadoEvent event) {
		Pedido pedido = event.getPedido();

		Mensagem mensagem = Mensagem.builder()

				.assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado")

				.corpo("pedido-confirmado.html")

				.variavel("pedido", pedido)

				.destinatario(pedido.getCliente().getEmail()).build();

		envioEmailService.enviar(mensagem);

	}
}
