package com.deliveryfood.domain.listener;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.deliveryfood.domain.event.PedidoConfirmadoEvent;

@Component
public class EmiteNotaFiscal {

	@TransactionalEventListener
	public void emitirNotaFiscalAposConfirmarEvento(PedidoConfirmadoEvent event) {
		System.out.println(">>>>Emitindo nota fiscal do pedido");
	}
}
