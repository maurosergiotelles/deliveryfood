package com.deliveryfood.apart.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;

import com.deliveryfood.DeliveryFoodApplication;
import com.deliveryfood.apart.infrastructure.repository.RestauranteRepository;
import com.deliveryfood.domain.model.Restaurante;

@Profile("apart")
public class JpaApplicationRestauranteTest {

	ApplicationContext applicationContext = new SpringApplicationBuilder(DeliveryFoodApplication.class)
			.web(WebApplicationType.NONE).run();

	RestauranteRepository cadastroRestaurante = applicationContext.getBean(RestauranteRepository.class);

	public static void main(String[] args) {
		JpaApplicationRestauranteTest testJpa = new JpaApplicationRestauranteTest();
		testJpa.listarRestaurantes();
		testJpa.adicionarRestaurante();

		testJpa.buscaPorId(2L);
		testJpa.atualizar();
		testJpa.remover();

	}

	public void listarRestaurantes() {

		List<Restaurante> restaurantes = cadastroRestaurante.todas();
		for (Restaurante restaurante : restaurantes) {
			print(restaurante);
			restaurantes.forEach(this::print);
		}

	}

	public void print(Restaurante restaurante) {
		System.out.printf("%d - %s - Cozinha: %s\n", restaurante.getId(), restaurante.getNome(),
				restaurante.getCozinha().getNome());
	}

	public void adicionarRestaurante() {
		Restaurante restaurante = new Restaurante();
		restaurante.setNome("Restaurante Japonesa");
		cadastroRestaurante.adicionar(restaurante);
	}

	public void buscaPorId(Long id) {
		Restaurante Restaurante = cadastroRestaurante.findById(id);
		print(Restaurante);
	}

	public void atualizar() {
		Restaurante RestauranteEncontrada = cadastroRestaurante.findById(2L);
		RestauranteEncontrada.setNome("restaurante e ou zimbabuanooooooooooooooooooo");
		Restaurante RestauranteAtualizada = cadastroRestaurante.adicionar(RestauranteEncontrada);
		print(RestauranteAtualizada);
	}

	public void remover() {
		cadastroRestaurante.remover(2L);
		Restaurante restauranteTeste = cadastroRestaurante.findById(2L);
		System.out.println("Restaurante removida: " + restauranteTeste);
	}
}
