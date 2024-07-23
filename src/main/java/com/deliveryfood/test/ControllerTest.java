package com.deliveryfood.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Profile("test")
@RestController
@RequestMapping("/base2")
public class ControllerTest {

	@Autowired
	private EmailUtil emailUtil;

	@GetMapping("/test")
	@ResponseBody
	public String getTeste() {
		emailUtil.enviar();

		return "teste.....>>>>...fjdsaklçf djsaklfds ajdkfl....";

	}

}