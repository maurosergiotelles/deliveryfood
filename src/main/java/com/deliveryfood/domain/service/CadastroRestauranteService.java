package com.deliveryfood.domain.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deliveryfood.api.model.FormaPagamentoModel;
import com.deliveryfood.api.model.ProdutoModel;
import com.deliveryfood.api.model.RestauranteModel;
import com.deliveryfood.api.model.UsuarioModel;
import com.deliveryfood.api.model.input.RestauranteInput;
import com.deliveryfood.domain.exception.EntidadeChaveEstrangeiraNaoEncontradaException;
import com.deliveryfood.domain.exception.EntidadeNaoEncontradaException;
import com.deliveryfood.domain.model.Cidade;
import com.deliveryfood.domain.model.Cozinha;
import com.deliveryfood.domain.model.FormaPagamento;
import com.deliveryfood.domain.model.Produto;
import com.deliveryfood.domain.model.Restaurante;
import com.deliveryfood.domain.model.Usuario;
import com.deliveryfood.domain.repository.CidadeRepository;
import com.deliveryfood.domain.repository.CozinhaRepository;
import com.deliveryfood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	@Lazy
	private CadastroProdutoService cadastroProduto;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamento;

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private CadastroUsuarioService cadastroUsuario;

	public List<RestauranteModel> findAll() {
		List<Restaurante> restaurantes = restauranteRepository.findAllRestaurante();

		return restaurantes.stream().map(restaurante -> modelMapper.map(restaurante, RestauranteModel.class)).collect(Collectors.toList());
	}

	public RestauranteModel findById(Long id) {
		Restaurante restaurante = buscarOuFalhar(id);
		return modelMapper.map(restaurante, RestauranteModel.class);
	}

	@Transactional
	public RestauranteModel adicionar(RestauranteInput restauranteInput) {
		Cozinha cozinha = cozinhaExisteOuNaoEncontrada(restauranteInput.getCozinha().getId());

		Long cidadeId = restauranteInput.getEndereco().getCidade().getId();

		Cidade cidade = cidadeExisteOuNaoEncontrada(cidadeId);

		Restaurante restaurante = modelMapper.map(restauranteInput, Restaurante.class);

		restaurante.setCozinha(cozinha);
		restaurante.getEndereco().setCidade(cidade);

		Restaurante restauranteSaved = restauranteRepository.save(restaurante);

		return modelMapper.map(restauranteSaved, RestauranteModel.class);
	}

	private Cozinha cozinhaExisteOuNaoEncontrada(Long cozinhaId) {
		Optional<Cozinha> cozinhaOptional = cozinhaRepository.findById(cozinhaId);
		return cozinhaOptional.orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Cozinha com o id %d não encontrada", cozinhaId)));
	}

	@Transactional
	public void ativar(Long restauranteId) {
		Restaurante restaurante = this.buscarOuFalhar(restauranteId);
		restaurante.ativar();
	}

	@Transactional
	public void inativar(Long restauranteId) {
		Restaurante restaurante = this.buscarOuFalhar(restauranteId);
		restaurante.inativar();
	}

	@Transactional
	public RestauranteModel atualizar(Long restauranteId, RestauranteInput restauranteInput) {
		try {
			Cozinha cozinha = cozinhaExisteOuNaoEncontrada(restauranteInput.getCozinha().getId());

			Long cidadeId = restauranteInput.getEndereco().getCidade().getId();

			Cidade cidade = cidadeExisteOuNaoEncontrada(cidadeId);

			Restaurante restaurante = buscarOuFalhar(restauranteId);

			restaurante.setCozinha(cozinha);

			restaurante.getEndereco().setCidade(cidade);

			modelMapper.map(restauranteInput, restaurante);

			return modelMapper.map(restaurante, RestauranteModel.class);
		} catch (Exception e) {

			throw new EntidadeChaveEstrangeiraNaoEncontradaException(e.getMessage());
		}
	}

	private Cidade cidadeExisteOuNaoEncontrada(Long cidadeId) {
		Optional<Cidade> optional = cidadeRepository.findById(cidadeId);
		Cidade cidade = optional.orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Cidade como código %d não existe", cidadeId)));
		return cidade;
	}

	public Restaurante buscarOuFalhar(Long restauranteId) {
		Optional<Restaurante> optionalRestaurante = restauranteRepository.findById(restauranteId);
		return optionalRestaurante.orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Restaurante com o código %d não encontrado", restauranteId)));
	}

	public List<Restaurante> findByBetweenTaxaInicialETaxaFinal(BigDecimal taxaInicial, BigDecimal taxaFinal) {
		return restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal);
	}

	public List<Restaurante> buscaPorNomeECozinhaId(String nome, Long cozinhaId) {
		return restauranteRepository.buscaPorNomeECozinhaId(nome, cozinhaId);
	}

	public List<Restaurante> porNomeETaxaFrete(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
		return restauranteRepository.find(nome, taxaFreteInicial, taxaFreteFinal);
	}

	public List<Restaurante> findComFreteGratis(String nome) {
		return restauranteRepository.findComFreteGratis(nome);
	}

	@Transactional
	public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);
		restaurante.desassociarFormaPagamento(formaPagamento);
	}

	@Transactional
	public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);
		restaurante.associarFormaPagamento(formaPagamento);
	}

	@Transactional
	public void fechar(Long restauranteId) {
		Restaurante restaurante = this.buscarOuFalhar(restauranteId);
		restaurante.fechar();
	}

	@Transactional
	public void abrir(Long restauranteId) {
		Restaurante restaurante = this.buscarOuFalhar(restauranteId);
		restaurante.abrir();
	}

	public Set<FormaPagamentoModel> findByIdByFormasPagamento(Long restauranteId) {
		Restaurante restaurante = this.buscarOuFalhar(restauranteId);
		Set<FormaPagamento> formasPagamento = restaurante.getFormasPagamento();
		return formasPagamento.stream().map(formaPagamento -> modelMapper.map(formaPagamento, FormaPagamentoModel.class)).collect(Collectors.toSet());
	}

	public ProdutoModel findByIdByProduto(Long restauranteId, Long produtoId) {
		Produto produto = cadastroProduto.buscarOuFalhar(restauranteId, produtoId);
		return modelMapper.map(produto, ProdutoModel.class);
	}

	public List<ProdutoModel> findProdutosBy(Long restauranteId) {
		Restaurante restaurante = this.buscarOuFalhar(restauranteId);
		return restaurante.getProdutos().stream().map(produto -> modelMapper.map(produto, ProdutoModel.class)).collect(Collectors.toList());
	}

	@Transactional
	public void desassociarResponsavel(Long restauranteId, Long usuarioId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);

		Usuario usuario = cadastroUsuario.encontraOuFalhar(usuarioId);

		restaurante.removerResponsavel(usuario);
	}

	@Transactional
	public void associarResponsavel(Long restauranteId, Long usuarioId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);

		Usuario usuario = cadastroUsuario.encontraOuFalhar(usuarioId);

		restaurante.adicionarResponsavel(usuario);
	}

	public Set<UsuarioModel> findResponsaveis(Long restauranteId) {
		Restaurante restaurante = this.buscarOuFalhar(restauranteId);
		Set<Usuario> responsaveis = restaurante.getResponsaveis();
		return responsaveis.stream().map(responsavel -> modelMapper.map(responsavel, UsuarioModel.class)).collect(Collectors.toSet());
	}

	@Transactional
	public void ativar(List<Long> restauranteIds) {
		try {
			restauranteIds.forEach(this::ativar);
		} catch (Exception e) {
			throw new EntidadeNaoEncontradaException("Restaurante não encontrado");
		}
	}

	@Transactional
	public void desativar(List<Long> restauranteIds) {
		try {
			restauranteIds.forEach(this::inativar);
		} catch (Exception e) {
			throw new EntidadeNaoEncontradaException("Restaurante não encontrado");
		}

	}

	public List<ProdutoModel> findAtivosByRestaurante(Long restauranteId) {
		return cadastroProduto.findAtivosByRestaurante(restauranteId);
	}

}