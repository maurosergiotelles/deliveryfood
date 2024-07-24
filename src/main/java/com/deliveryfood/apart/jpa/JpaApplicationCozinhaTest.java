package com.deliveryfood.apart.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;

import com.deliveryfood.DeliveryFoodApplication;
import com.deliveryfood.apart.infrastructure.repository.CozinhaRepository;
import com.deliveryfood.domain.model.Cozinha;

@Profile("apart")
public class JpaApplicationCozinhaTest {

	ApplicationContext applicationContext = new SpringApplicationBuilder(DeliveryFoodApplication.class)
			.web(WebApplicationType.NONE).run();

	CozinhaRepository cadastroCozinha = applicationContext.getBean(CozinhaRepository.class);

	public static void main(String[] args) {
		JpaApplicationCozinhaTest testJpa = new JpaApplicationCozinhaTest();
		testJpa.listarCozinhas();

		testJpa.adicionarCozinha();
		testJpa.listarCozinhas();

		testJpa.buscaPorId(187L);
		testJpa.atualizar();
		testJpa.remover();
	}

	public void listarCozinhas() {

		List<Cozinha> cozinhas = cadastroCozinha.todas();

		cozinhas.forEach(this::print);
	}

	public void print(Cozinha cozinha) {
		System.out.printf("%d - %s\n", cozinha.getId(), cozinha.getNome());
	}

	public void adicionarCozinha() {
		Cozinha cozinha = new Cozinha();
		cozinha.setNome("Japonesa");
		cadastroCozinha.adicionar(cozinha);
	}

	public void buscaPorId(Long id) {
		Cozinha cozinha = cadastroCozinha.findById(id);
		print(cozinha);
	}

	public void atualizar() {
		Cozinha cozinhaEncontrada = cadastroCozinha.findById(187L);
		cozinhaEncontrada.setNome("zimbabuense ou zimbabuanooooooooooooooooooo");
		Cozinha cozinhaAtualizada = cadastroCozinha.adicionar(cozinhaEncontrada);
		print(cozinhaAtualizada);
	}

	public void remover() {
		cadastroCozinha.remover(187L);
		Cozinha cozinhaTeste = cadastroCozinha.findById(187L);
		System.out.println("Cozinha removida: " + cozinhaTeste);
	}
}
