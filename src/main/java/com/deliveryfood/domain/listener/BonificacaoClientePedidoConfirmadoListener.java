package com.deliveryfood.domain.listener;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.deliveryfood.domain.event.PedidoConfirmadoEvent;

@Component
public class BonificacaoClientePedidoConfirmadoListener {

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void aoConfirmarPedido(PedidoConfirmadoEvent event) {
		System.out.println(">>>Calculando pontos para cliente " + event.getPedido().getCliente().getNome());
	}
}
