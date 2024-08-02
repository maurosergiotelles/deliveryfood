package com.deliveryfood;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;

import com.deliveryfood.domain.model.Cozinha;
import com.deliveryfood.domain.repository.CozinhaRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
class DeliveryFoodApplicationTests {

//	@Autowired
//	private static DatabaseCleaner databaseCleaner;

//	@Autowired
//	private MockMvc mockMvc;
	@Autowired
	private CozinhaRepository cozinhaRepository;

	@BeforeAll
	public static void setup() {
//		databaseCleaner.clearTables();
	}

	@Test
	void contextLoads() {
		Assertions.assertTrue(true);
		List<Cozinha> all = cozinhaRepository.findAll();
		Assertions.assertTrue(all.size() == 0);

//		ResultActions perform = mockMvc.perform(null);

	}

}
