package com.deliveryfood.api.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.deliveryfood.api.model.RestauranteModel;
import com.deliveryfood.api.model.input.RestauranteInput;
import com.deliveryfood.api.model.view.RestauranteView;
import com.deliveryfood.domain.model.Restaurante;
import com.deliveryfood.domain.service.CadastroRestauranteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	CadastroRestauranteService cadastroRestaurante;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public MappingJacksonValue getAll(@RequestParam(required = false) String projecao) {

		List<RestauranteModel> pedidosModel = cadastroRestaurante.findAll();

		MappingJacksonValue pedidosWrapper = new MappingJacksonValue(pedidosModel);
		Class<?> serializationView = null;

		if ("apenasnome".equals(projecao)) {
			serializationView = RestauranteView.ApenasNome.class;
		} else if ("resumo".equals(projecao)) {
			serializationView = RestauranteView.Resumo.class;
		} else if ("completo".equals(projecao)) {
			serializationView = null;
		}

		pedidosWrapper.setSerializationView(serializationView);

		return pedidosWrapper;
	}

	/*
	 * @GetMapping
	 * 
	 * @ResponseStatus(HttpStatus.OK) public List<RestauranteModel> getAll() {
	 * return cadastroRestaurante.findAll(); }
	 * 
	 * @GetMapping(params = { "projecao=resumo" })
	 * 
	 * @ResponseStatus(HttpStatus.OK)
	 * 
	 * @JsonView(RestauranteView.Resumo.class) public List<RestauranteModel>
	 * getAllResumo() { return cadastroRestaurante.findAll(); }
	 * 
	 * @GetMapping(params = { "projecao=apenasnome" })
	 * 
	 * @ResponseStatus(HttpStatus.OK)
	 * 
	 * @JsonView(value = RestauranteView.ApenasNome.class) public
	 * List<RestauranteModel> getAllResumoApenasNome() { return
	 * cadastroRestaurante.findAll(); }
	 */
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public RestauranteModel getPorId(@PathVariable Long id) {
		return cadastroRestaurante.findById(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteModel adicionar(@RequestBody @Valid RestauranteInput restauranteInput) {
		return cadastroRestaurante.adicionar(restauranteInput);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public RestauranteModel atualizar(@PathVariable Long id, @RequestBody @Valid RestauranteInput restaurante) {
		return cadastroRestaurante.atualizar(id, restaurante);
	}

	@GetMapping("/por-taxa-frete")
	@ResponseStatus(HttpStatus.OK)
	public List<Restaurante> porTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal) {
		return cadastroRestaurante.findByBetweenTaxaInicialETaxaFinal(taxaInicial, taxaFinal);
	}

	@GetMapping("/por-nome-cozinhaId")
	@ResponseStatus(HttpStatus.OK)
	public List<Restaurante> porNomeECozinhaId(String nome, Long cozinhaId) {
		return cadastroRestaurante.buscaPorNomeECozinhaId(nome, cozinhaId);
	}

	@GetMapping("/por-nome-taxaFrete")
	@ResponseStatus(HttpStatus.OK)
	public List<Restaurante> porNomeETaxaFrete(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
		return cadastroRestaurante.porNomeETaxaFrete(nome, taxaFreteInicial, taxaFreteFinal);
	}

	@GetMapping("/com-frete-gratis")
	@ResponseStatus(HttpStatus.OK)
	public List<Restaurante> comFreteGratis(String nome) {
		return cadastroRestaurante.findComFreteGratis(nome);
	}

	@PutMapping("/{restauranteId}/ativar")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable Long restauranteId) {
		cadastroRestaurante.ativar(restauranteId);
	}

	@DeleteMapping("/{restauranteId}/ativar")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativar(@PathVariable Long restauranteId) {
		cadastroRestaurante.inativar(restauranteId);
	}

	@PutMapping("/{restauranteId}/abertura")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void abrir(@PathVariable Long restauranteId) {
		cadastroRestaurante.abrir(restauranteId);
	}

	@PutMapping("/{restauranteId}/fechamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void fechar(@PathVariable Long restauranteId) {
		cadastroRestaurante.fechar(restauranteId);
	}

	@PutMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativarMultiplos(@RequestBody List<Long> restauranteIds) {
		cadastroRestaurante.ativar(restauranteIds);
	}

	@DeleteMapping("/inativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativarMultiplos(@RequestBody List<Long> restauranteIds) {
		cadastroRestaurante.desativar(restauranteIds);

	}

}
