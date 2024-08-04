package com.deliveryfood.domain.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deliveryfood.api.model.FormaPagamentoModel;
import com.deliveryfood.api.model.input.FormaPagamentoInput;
import com.deliveryfood.domain.exception.EntidadeEmUsoException;
import com.deliveryfood.domain.exception.EntidadeNaoEncontradaException;
import com.deliveryfood.domain.model.FormaPagamento;
import com.deliveryfood.domain.repository.FormaPagamentoRepository;

@Service
public class CadastroFormaPagamentoService {

	private static final String FORMA_DE_PAGAMENTO_NÃO_ENCONTRADA_COM_O_ID_D = "Forma de Pagamento não encontrada com o id %d";

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;

	public FormaPagamentoModel findById(Long id) {
		FormaPagamento formaPagamento = buscarOuFalhar(id);

		return modelMapper.map(formaPagamento, FormaPagamentoModel.class);
	}

	public List<FormaPagamentoModel> findAll() {
		List<FormaPagamento> formasPagamento = formaPagamentoRepository.findAll();
		return formasPagamento.stream().map(formaPagamento -> modelMapper.map(formaPagamento, FormaPagamentoModel.class)).collect(Collectors.toList());
	}

	@Transactional
	public FormaPagamentoModel adicionar(FormaPagamentoInput formaPagamentoInput) {
		FormaPagamento formaPagamento = modelMapper.map(formaPagamentoInput, FormaPagamento.class);

		FormaPagamento formaPagamentoSalva = formaPagamentoRepository.save(formaPagamento);
		return modelMapper.map(formaPagamentoSalva, FormaPagamentoModel.class);
	}

	@Transactional
	public FormaPagamentoModel atualizar(Long id, FormaPagamentoInput formaPagamentoInput) {
		FormaPagamento formaPagamento = buscarOuFalhar(id);

		modelMapper.map(formaPagamentoInput, formaPagamento);

		return modelMapper.map(formaPagamento, FormaPagamentoModel.class);
	}

	public FormaPagamento buscarOuFalhar(Long id) {
		return formaPagamentoRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException(String.format(FORMA_DE_PAGAMENTO_NÃO_ENCONTRADA_COM_O_ID_D, id)));
	}

	@Transactional
	public void deletar(Long id) {
		try {
			if (!formaPagamentoRepository.existsById(id)) {
				throw new EntidadeNaoEncontradaException(String.format(FORMA_DE_PAGAMENTO_NÃO_ENCONTRADA_COM_O_ID_D, id));
			}

			formaPagamentoRepository.deleteById(id);
			formaPagamentoRepository.flush();
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("Forma Pagamento com o código %s está em uso", id));
		}
	}

}
