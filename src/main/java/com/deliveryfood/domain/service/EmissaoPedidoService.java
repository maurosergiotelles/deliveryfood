package com.deliveryfood.domain.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deliveryfood.api.model.PedidoModel;
import com.deliveryfood.api.model.PedidoResumoModel;
import com.deliveryfood.domain.exception.EntidadeNaoEncontradaException;
import com.deliveryfood.domain.model.Cidade;
import com.deliveryfood.domain.model.FormaPagamento;
import com.deliveryfood.domain.model.Pedido;
import com.deliveryfood.domain.model.Produto;
import com.deliveryfood.domain.model.Restaurante;
import com.deliveryfood.domain.model.Usuario;
import com.deliveryfood.domain.repository.PedidoRepository;

@Service
public class EmissaoPedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CadastroRestauranteService cadastroRestaurante;

	@Autowired
	private CadastroCidadeService cadastroCidade;

	@Autowired
	private CadastroUsuarioService cadastroUsuario;

	@Autowired
	private CadastroProdutoService cadastroProduto;

	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamento;

	public Pedido buscarOuFalhar(String codigoPedido) {
		return pedidoRepository.findByCodigo(codigoPedido).orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Pedido com id %s não encontrado", codigoPedido)));
	}

	public List<PedidoResumoModel> findAll() {
		List<Pedido> pedidos = pedidoRepository.findAll();

		return pedidos.stream().map(pedido -> modelMapper.map(pedido, PedidoResumoModel.class)).collect(Collectors.toList());
	}

	public PedidoModel findById(String codigoPedido) {
		Pedido pedido = buscarOuFalhar(codigoPedido);
		return modelMapper.map(pedido, PedidoModel.class);
	}

	@Transactional
	public Pedido emtir(Pedido pedido) {
		validarPedido(pedido);
		validarItens(pedido);

		pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
		pedido.calcularValorTotal();

		return pedidoRepository.save(pedido);

	}

	private void validarPedido(Pedido pedido) {
		pedido.setCliente(new Usuario());
		pedido.getCliente().setId(1L);

		Cidade cidade = cadastroCidade.buscarOuFalhar(pedido.getEnderecoEntrega().getCidade().getId());
		Usuario cliente = cadastroUsuario.encontraOuFalhar(pedido.getCliente().getId());
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(pedido.getRestaurante().getId());
		FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(pedido.getFormaPagamento().getId());

		pedido.getEnderecoEntrega().setCidade(cidade);
		pedido.setCliente(cliente);
		pedido.setRestaurante(restaurante);
		pedido.setFormaPagamento(formaPagamento);

		if (restaurante.naoAceitaFormaPagamento(formaPagamento)) {
			throw new EntidadeNaoEncontradaException(String.format("Forma de pagamento '%s' não é aceita por este restaurante.", formaPagamento.getDescricao()));
		}
	}

	private void validarItens(Pedido pedido) {
		pedido.getItens().forEach(item -> {
			Produto produto = cadastroProduto.buscarOuFalhar(pedido.getRestaurante().getId(), item.getProduto().getId());

			item.setPedido(pedido);
			item.setProduto(produto);
			item.setPrecoUnitario(produto.getPreco());

		});
	}
}
